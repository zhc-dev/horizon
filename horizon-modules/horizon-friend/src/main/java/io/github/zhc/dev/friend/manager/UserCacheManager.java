package io.github.zhc.dev.friend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.friend.mapper.user.UserMapper;
import io.github.zhc.dev.friend.model.entity.user.User;
import io.github.zhc.dev.friend.model.vo.user.UserVO;
import io.github.zhc.dev.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhc-dev
 * @data 2025/4/11 20:43
 */
@Component
public class UserCacheManager {
    @Resource
    private RedisService redisService;

    @Resource
    private UserMapper userMapper;

    public UserVO getUserById(Long userId) {
        String userKey = getUserKey(userId);
        UserVO userVO = redisService.getCacheObject(userKey, UserVO.class);
        if (userVO != null) {
            redisService.expire(userKey, CacheConstants.USER_EXPIRATION_TIME, TimeUnit.MINUTES);
            return userVO;
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getUserId,
                User::getNickName,
                User::getHeadImage,
                User::getSex,
                User::getEmail,
                User::getPhone,
                User::getWechat,
                User::getIntroduce,
                User::getSchoolName,
                User::getMajorName,
                User::getStatus).eq(User::getUserId, userId));
        if (user == null) {
            return null;
        }
        refreshUser(user);
        userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    public void refreshUser(User user) {
        //刷新用户缓存
        String userKey = getUserKey(user.getUserId());
        redisService.setCacheObject(userKey, user);
        //设置用户缓存有效期为10分钟
        redisService.expire(userKey, CacheConstants.USER_EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    //u:d:用户id
    private String getUserKey(Long userId) {
        return CacheConstants.USER_DETAIL_KEY_PREFIX + userId;
    }
}
