package com.maochong.mybatis.common.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 重写类型处理器或创建自己的类型处理器来处理不支持的或非标准的类型
 * @MappedJdbcTypes(value = JdbcType.VARCHAR,includeNullJdbcType = true)  就是覆盖默认的 JdbcType.VARCHAR TypeHandler
 * @author jokin
 * */
@MappedJdbcTypes(value = JdbcType.VARCHAR,includeNullJdbcType = true)
public class ExampleTypeHandler  extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException
    {
        ps.setString(i,parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        return rs.getString(columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException
    {
        return rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        return cs.getString(columnIndex);
    }
}