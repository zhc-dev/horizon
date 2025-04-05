package io.github.zhc.dev.system.manager;

import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.system.model.entity.user.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:44
 */
@Component
public class UserCacheManager {
    @Resource
    private RedisService redisService;

    public void updateStatus(Long userId, Integer status) {
        //刷新用户缓存
        String userKey = getUserKey(userId);
        User user = redisService.getCacheObject(userKey, User.class);
        if (user == null) {
            return;
        }
        user.setStatus(status);
        redisService.setCacheObject(userKey, user);
        //设置用户缓存有效期为10分钟
        redisService.expire(userKey, CacheConstants.USER_EXP, TimeUnit.MINUTES);
    }

    //u:d:用户id
    private String getUserKey(Long userId) {
        return CacheConstants.USER_DETAIL + userId;
    }
}
