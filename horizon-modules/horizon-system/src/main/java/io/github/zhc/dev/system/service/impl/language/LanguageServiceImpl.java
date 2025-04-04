package io.github.zhc.dev.system.service.impl.language;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.system.mapper.language.LanguageMapper;
import io.github.zhc.dev.system.model.entity.language.Language;
import io.github.zhc.dev.system.model.vo.language.LanguageVO;
import io.github.zhc.dev.system.service.language.LanguageService;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/4 17:25
 */
@Service
public class LanguageServiceImpl implements LanguageService {
    @Resource
    private LanguageMapper languageMapper;

    @Override
    public List<LanguageVO> list() {
        List<Language> languages = languageMapper.selectList(new LambdaQueryWrapper<>());
        return BeanUtil.copyToList(languages, LanguageVO.class);
    }
}
