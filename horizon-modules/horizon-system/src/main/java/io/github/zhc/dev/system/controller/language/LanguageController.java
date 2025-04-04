package io.github.zhc.dev.system.controller.language;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.service.language.LanguageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhc.dev
 * @date 2025/4/4 17:22
 */
@RestController
@RequestMapping("/language")
public class LanguageController extends BaseController {
    @Resource
    private LanguageService languageService;

    /**
     * 查询编程语言列表
     * @return 语言列表
     */
    @GetMapping("/list")
    public TableData list() {
        return getTableData(languageService.list());
    }
}
