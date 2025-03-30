package io.github.zhc.dev.common.core.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageInfo;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;

import java.util.List;

public class BaseController {
    /**
     * 封装结果
     *
     * @param rows 影响行数
     * @return 结果
     */
    public R<Void> toR(int rows) {
        return rows > 0 ? R.ok() : R.fail();
    }

    /**
     * 封装结果
     *
     * @param result 结果
     * @return 结果
     */
    public R<Void> toR(boolean result) {
        return result ? R.ok() : R.fail();
    }

    /**
     * 封装分页数据
     *
     * @param list 查询到的数据
     * @return 数据表
     */
    public TableData getTableData(List<?> list) {
        return CollectionUtil.isEmpty(list) ? TableData.empty() : TableData.success(list, new PageInfo<>(list).getTotal());
    }
}