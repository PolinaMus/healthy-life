package com.company.rowmapper;

import com.company.model.SaleItemModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SaleItemRowMapper implements RowMapper<SaleItemModel> {

    @Override
    public SaleItemModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SaleItemModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("qty")
        );
    }
}
