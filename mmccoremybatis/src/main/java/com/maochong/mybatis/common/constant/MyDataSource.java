package com.maochong.mybatis.common.constant;

import com.maochong.mybatis.common.enums.DataSourceType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 数据源选择--自定义注解
 * DataSource
 * @author jokin
 * @date 2018年2月27日 上午11:23:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE,METHOD})
public @interface MyDataSource {
    DataSourceType value() default DataSourceType.Master;	//默认主表
}
