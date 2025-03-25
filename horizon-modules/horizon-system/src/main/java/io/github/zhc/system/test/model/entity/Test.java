package io.github.zhc.system.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 测试表
 *
 * @author zhc.dev
 * @date 2025/3/25 22:04
 */
@TableName("tb_test")
public class Test {
    private Long testId;
    private String title;
    private String content;

    public Test() {
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
