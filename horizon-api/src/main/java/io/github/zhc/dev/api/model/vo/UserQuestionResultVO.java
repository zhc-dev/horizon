package io.github.zhc.dev.api.model.vo;

import io.github.zhc.dev.api.model.entity.UserExeResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/20 00:12
 */
@Getter
@Setter
public class UserQuestionResultVO {
    //是够通过标识
    private Integer pass; // 0  未通过  1 通过

    private String exeMessage; //异常信息

    private List<UserExeResult> userExeResultList;

    // @JsonIgnore
    private Integer score;
}
