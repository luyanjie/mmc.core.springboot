package com.maochong.helloword.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type= Executor.class,
                method = "update",
                args = {MappedStatement.class,Object.class})})
public class LessonPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取相关内容，在执行前处理
        // getArgs 参数对应上面对应的@Signature 的args
        System.out.println("Interceptor.....");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
        System.out.println(String.format("plugin out put sql = %s param = %s",boundSql.getSql(),boundSql.getParameterObject()));
        String methodName = invocation.getMethod().getName();

//        if (methodName.equals("query")) {
//            Object parameter = invocation.getArgs()[1];
//            RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
//        }
//        else if(methodName.equals("update")){
//            Object parameter = invocation.getArgs()[1];
//        }
        // 执行
        return invocation.proceed();
    }

    /**
     * 使用当前的这个拦截器实现对目标对象的代理（内部实现时Java的动态代理）
     * org.apache.ibatis.plugin.Plugin#wrap(java.lang.Object, org.apache.ibatis.plugin.Interceptor)
     * */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 这里用来接收 下面配置传入的property参数
//        <plugin interceptor="com.maochong.mybatis.plugins.LessonPlugin">
//            <property name="" value=""></property>
//        </plugin>

        System.out.println("获取配置的参数aaa："+properties.getProperty("aaa"));
    }
}
