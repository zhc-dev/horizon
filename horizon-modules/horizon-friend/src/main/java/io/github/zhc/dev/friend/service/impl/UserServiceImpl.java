package io.github.zhc.dev.friend.service.impl;

import cn.hutool.core.util.RandomUtil;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.friend.model.dto.UserRequest;
import io.github.zhc.dev.friend.service.UserService;
import io.github.zhc.dev.message.service.EmailService;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.security.exception.ServiceException;
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

    @Value("${mail.code.send.limit:3}")
    private Long emailCodeSendLimit;


    @Override
    public boolean sendCode(UserRequest userRequest) {
        if (!checkEmail(userRequest.getEmail())) throw new ServiceException(ResultCode.FAILED_USER_EMAIL);
        String code = RandomUtil.randomNumbers(6);
        String emailKey = getEmailCodeKey(userRequest.getEmail());
        String emailTimesKey = getEmailCodeTimesKey(userRequest.getEmail());
        Long expire = redisService.getExpire(emailKey, TimeUnit.SECONDS);
        // 60秒内频繁发送验证码
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

    private String getEmailCodeKey(String email) {
        return CacheConstants.EMAIL_CODE_KEY + email;
    }

    private String getEmailCodeTimesKey(String email) {
        return CacheConstants.EMAIL_CODE_TIMES_KEY + email;
    }
}
