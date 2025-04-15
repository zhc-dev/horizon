package io.github.zhc.dev.friend.service.impl.user;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.constants.HttpConstants;
import io.github.zhc.dev.common.core.model.entity.LoginUserVO;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.common.core.model.enums.UserRole;
import io.github.zhc.dev.common.core.model.enums.UserStatus;
import io.github.zhc.dev.common.core.utils.ThreadLocalUtil;
import io.github.zhc.dev.friend.manager.UserCacheManager;
import io.github.zhc.dev.friend.mapper.user.UserMapper;
import io.github.zhc.dev.friend.model.dto.user.UserRequest;
import io.github.zhc.dev.friend.model.entity.user.User;
import io.github.zhc.dev.friend.model.vo.user.UserVO;
import io.github.zhc.dev.friend.service.user.UserService;
import io.github.zhc.dev.message.service.EmailService;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.security.service.TokenService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author zhc.dev
 * @date 2025/4/5 23:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private EmailService emailService;

    @Resource
    private RedisService redisService;

    @Value("${mail.code.expiration:5}")
    private Long emailCodeExpire;

    @Value("${mail.code.send.limit:50}")
    private Long emailCodeSendLimit;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenService tokenService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${file.oss.download.url}")
    private String downloadUrl;

    @Resource
    private UserCacheManager userCacheManager;

    @Override
    public boolean sendCode(UserRequest userRequest) {
        if (!checkEmail(userRequest.getEmail())) throw new ServiceException(ResultCode.FAILED_USER_EMAIL);

        String code = RandomUtil.randomNumbers(6);
        String emailKey = getEmailCodeKey(userRequest.getEmail());
        String emailTimesKey = getEmailCodeTimesKey(userRequest.getEmail());

        // 60秒内频繁发送验证码
        Long expire = redisService.getExpire(emailKey, TimeUnit.SECONDS);
        if (expire != null && (emailCodeExpire * 60 - expire) < 60)
            throw new ServiceException(ResultCode.FAILED_FREQUENT);
        // 限制每天发送次数
        Long sendTimes = redisService.getCacheObject(emailTimesKey, Long.class);
        if (sendTimes != null && sendTimes >= emailCodeSendLimit)
            throw new ServiceException(ResultCode.FAILED_TIMES_LIMIT);

        redisService.setCacheObject(emailKey, code, emailCodeExpire, TimeUnit.MINUTES);
        boolean res = emailService.sendVerificationCode(code, userRequest.getEmail());

        if (!res) throw new ServiceException(ResultCode.FAILED_SEND_CODE);
        redisService.increment(emailTimesKey);

        if (sendTimes == null) {// 首次发送,设置计数器有效时间为当天
            long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
            redisService.expire(emailTimesKey, seconds, TimeUnit.SECONDS);
        }
        return res;
    }

    @Override
    public String codeLogin(String email, String code) {
        checkCode(email, code);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user == null) {  //新用户
            //注册逻辑
            user = new User();
            user.setEmail(email);
            user.setStatus(UserStatus.Normal.getValue());
            user.setCreateBy(Constants.SYSTEM_USER_ID);
            userMapper.insert(user);
        }
        return tokenService.createToken(user.getUserId(), secret, UserRole.ORDINARY.getValue(), user.getNickName(), user.getHeadImage());
    }

    @Override
    public boolean logout(String token) {
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, StrUtil.EMPTY);
        }
        return tokenService.deleteLoginUser(token, secret);
    }

    @Override
    public R<LoginUserVO> info(String token) {
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, StrUtil.EMPTY);
        }

        LoginUserVO loginUser = tokenService.getLoginUser(token, secret);
        if (loginUser == null) return R.fail();

        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setNickName(loginUser.getNickName());
        loginUserVO.setHeadImage(loginUser.getHeadImage());
        return R.ok(loginUserVO);
    }

    @Override
    public UserVO detail() {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        if (userId == null) {
            throw new ServiceException(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        UserVO userVO = userCacheManager.getUserById(userId);
        if (userVO == null) {
            throw new ServiceException(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        if (StrUtil.isNotEmpty(userVO.getHeadImage())) {
            userVO.setHeadImage(downloadUrl + userVO.getHeadImage());
        }
        return userVO;
    }


    /**
     * 校验手机号
     *
     * @param email 手机号
     * @return 是否有效
     */
    public static boolean checkEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        if (email == null) return false;
        return pattern.matcher(email).matches();
    }

    /**
     * 获取邮箱验证码缓存key
     *
     * @param email 邮箱
     * @return 缓存key
     */
    private String getEmailCodeKey(String email) {
        return CacheConstants.EMAIL_CODE_KEY_PREFIX + email;
    }

    /**
     * 获取邮箱验证码发送次数缓存key
     *
     * @param email 邮箱
     * @return 缓存key
     */
    private String getEmailCodeTimesKey(String email) {
        return CacheConstants.EMAIL_CODE_TIMES_KEY_PREFIX + email;
    }

    /**
     * 校验验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    private void checkCode(String email, String code) {
        String emailCodeKey = getEmailCodeKey(email);
        String cacheCode = redisService.getCacheObject(emailCodeKey, String.class);
        if (StrUtil.isEmpty(cacheCode)) {
            throw new ServiceException(ResultCode.FAILED_INVALID_CODE);
        }
        if (!cacheCode.equals(code)) {
            throw new ServiceException(ResultCode.FAILED_ERROR_CODE);
        }
        //验证码比对成功
        redisService.deleteObject(emailCodeKey);
    }
}
