package io.github.zhc.dev.mybatis.handeler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.utils.ThreadLocalUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zhc.dev
 * @date 2025/3/28 18:04
 */
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", Long.class, ThreadLocalUtil.get(Constants.USER_ID, Long.class));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", ThreadLocalUtil.get(Constants.USER_ID, Long.class), metaObject);
    }
}
