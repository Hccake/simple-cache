package com.hccake.simpleredis;

import com.hccake.simpleredis.config.HierarchicalFactoryPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Copyright (C), 上海秦苍信息科技有限公司
 * <p>
 * <类描述>
 *
 * @author wub
 * @version EnableHierarchicalAnnotation, v1.0 2019/9/5 19:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HierarchicalFactoryPostProcessor.class)
public @interface EnableHierarchicalAnnotation {
}
