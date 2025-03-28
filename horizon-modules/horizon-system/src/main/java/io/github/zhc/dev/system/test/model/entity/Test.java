package io.github.zhc.dev.system.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 测试表
 *
 * @author zhc.dev
 * @date 2025/3/25 22:04
 */
@TableName("tb_test")
@Getter
@Setter
public class Test implements Serializable {
    @TableId
    private Long testId;
    private String title;
    private String content;

    public Test() {
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
