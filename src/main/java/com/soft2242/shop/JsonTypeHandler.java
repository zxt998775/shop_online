package com.soft2242.shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft2242.shop.vo.SpecsVO;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JsonTypeHandler extends BaseTypeHandler<List<SpecsVO>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<SpecsVO> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting List to JSON string", e);
        }
    }

    @Override
    public List<SpecsVO> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String json = rs.getString(columnName);
            return parseJsonArray(json);
        } catch (IOException e) {
            throw new SQLException("Error converting JSON string to List<SpecsVO>", e);
        }
    }

    @Override
    public List<SpecsVO> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String json = rs.getString(columnIndex);
            return parseJsonArray(json);
        } catch (IOException e) {
            throw new SQLException("Error converting JSON string to List<SpecsVO>", e);
        }
    }

    @Override
    public List<SpecsVO> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String json = cs.getString(columnIndex);
            return parseJsonArray(json);
        } catch (IOException e) {
            throw new SQLException("Error converting JSON string to List<SpecsVO>", e);
        }
    }

    private List<SpecsVO> parseJsonArray(String json) throws IOException {
        // 将 JSON 数组转换为 List<SpecsVO>
        return objectMapper.readValue(json, new TypeReference<List<SpecsVO>>() {});
    }
}
