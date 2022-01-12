package com.company.rowmapper;

import com.company.model.ItemFrequencyModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemFrequencyRowMapper implements RowMapper<ItemFrequencyModel> {
    @Override
    public ItemFrequencyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ItemFrequencyModel(
                rs.getLong("id"),
                rs.getInt("count"),
                rs.getString("name")
        );
    }
}
