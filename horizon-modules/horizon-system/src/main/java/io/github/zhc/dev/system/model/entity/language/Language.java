package io.github.zhc.dev.system.model.entity.language;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/2 22:02
 */
@Getter
@Setter
@TableName("tb_language")
public class Language extends BaseEntity<BaseEntity> {
    @TableId(type = IdType.ASSIGN_ID)
    private Long languageId;
    private String name;
    private Integer isEnabled;
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;
}