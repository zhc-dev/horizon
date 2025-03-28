package io.github.zhc.dev.system.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.system.test.mapper.TestMapper;
import io.github.zhc.dev.system.test.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/3/25 22:04
 */
@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;

    @Override
    public List<?> list() {
        return testMapper.selectList(new LambdaQueryWrapper<>());
    }
}
