package com.maochong.mybatis.common.typehandler;

import com.maochong.mybatis.entity.typehandler.Content;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContentTypeHandler extends BaseTypeHandler<Content> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Content parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.toString());
    }

    @Override
    public Content getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new Content(rs.getString(columnName));
    }

    @Override
    public Content getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Content(rs.getString(columnIndex));
    }

    @Override
    public Content getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new Content(cs.getString(columnIndex));
    }
}
