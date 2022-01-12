package com.company.rowmapper;

import com.company.model.ArticleModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleRowMapper implements RowMapper<ArticleModel> {
    @Override
    public ArticleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ArticleModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getString("text"),
                rs.getLong("first_item_id"),
                rs.getLong("second_item_id"),
                rs.getLong("third_item_id"),
                rs.getString("image")
        );
    }
}
