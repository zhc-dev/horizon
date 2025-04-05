package io.github.zhc.dev.system.model.vo.language;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/4 17:27
 */
@Getter
@Setter
public class LanguageVO {
    private Long languageId;
    private String name;
    private Integer isEnabled;
}
