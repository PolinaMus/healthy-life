package com.company.rowmapper;

import com.company.model.ItemModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemRowMapper implements RowMapper<ItemModel> {
    @Override
    public ItemModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ItemModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("qty"),
                rs.getString("image")
        );
    }
}
