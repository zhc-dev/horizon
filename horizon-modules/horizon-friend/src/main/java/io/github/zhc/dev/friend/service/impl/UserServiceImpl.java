package io.github.zhc.dev.friend.service.impl;

import cn.hutool.core.util.RandomUtil;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.friend.model.dto.UserRequest;
import io.github.zhc.dev.friend.service.UserService;
import io.github.zhc.dev.security.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhc.dev
 * @date 2025/4/5 23:18
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean sendCode(UserRequest userRequest) {
        if (!checkPhone(userRequest.getPhone())) throw new ServiceException(ResultCode.FAILED_USER_PHONE);
        String code = RandomUtil.randomNumbers(6);
        // TODO 发送验证码
        return false;
    }

    /**
     * 校验手机号
     *
     * @param phone 手机号
     * @return 是否有效
     */
    public static boolean checkPhone(String phone) {
        Pattern regex = Pattern.compile("^1[2|3|4|5|6|7|8|9][0-9]\\d{8}$");
        Matcher m = regex.matcher(phone);
        return m.matches();
    }
}
