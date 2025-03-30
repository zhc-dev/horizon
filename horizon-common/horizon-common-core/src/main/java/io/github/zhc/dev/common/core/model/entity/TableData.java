package io.github.zhc.dev.common.core.model.entity;

import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author zhc.dev
 * @date 2025/3/30 22:49
 */
@Getter
@Setter
public class TableData {
    @Schema(description = "总记录数")
    private long total;

    @Schema(description = "列表数据")
    private List<?> rows;

    @Schema(description = "消息状态码")
    private int code;

    @Schema(description = "消息内容")
    private String msg;

    /**
     * 表格数据对象
     */
    public TableData() {
    }

    /**
     * 未查出任何数据时调用
     *
     * @return 空数据表格数据对象
     */
    public static TableData empty() {
        TableData rspData = new TableData();
        rspData.setCode(ResultCode.SUCCESS.getCode());
        rspData.setRows(new ArrayList<>());
        rspData.setMsg(ResultCode.SUCCESS.getMsg());
        rspData.setTotal(0);
        return rspData;
    }

    /**
     * 查出数据时调用
     *
     * @param list  查出的数据
     * @param total 数据总数
     * @return 表格数据对象
     */
    public static TableData success(List<?> list, long total) {
        TableData rspData = new TableData();
        rspData.setCode(ResultCode.SUCCESS.getCode());
        rspData.setRows(list);
        rspData.setMsg(ResultCode.SUCCESS.getMsg());
        rspData.setTotal(total);
        return rspData;
    }
}