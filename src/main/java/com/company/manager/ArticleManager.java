package com.company.manager;

import com.company.dto.ArticleGetAllResponseDTO;
import com.company.dto.ArticleGetByIdResponseDTO;
import com.company.dto.ArticleSaveRequestDTO;
import com.company.dto.ArticleSaveResponseDTO;
import com.company.exception.ArticleNotFoundException;
import com.company.exception.ItemNotFoundException;
import com.company.model.ArticleModel;
import com.company.rowmapper.ArticleRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ArticleManager {
    private final NamedParameterJdbcTemplate template;
    private final ArticleRowMapper articleRowMapper;
    private final String defaultImage = "noimage.png";

    public ArticleGetAllResponseDTO getAll() {
        final List<ArticleModel> articles = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, category, text, first_item_id, second_item_id, third_item_id, image FROM articles
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                articleRowMapper
        );

        final ArticleGetAllResponseDTO responseDTO = new ArticleGetAllResponseDTO(new ArrayList<>(articles.size()));
        for (ArticleModel article : articles) {
            responseDTO.getArticles().add(new ArticleGetAllResponseDTO.Article(
                    article.getId(),
                    article.getName(),
                    article.getCategory(),
                    article.getText(),
                    article.getFirstItemId(),
                    article.getSecondItemId(),
                    article.getThirdItemId(),
                    article.getImage()
            ));
        }
        return responseDTO;
    }

    public ArticleGetByIdResponseDTO getById(long id) {
        try {
            final ArticleModel article = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, category, text, first_item_id, second_item_id, third_item_id, image FROM articles
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", id),
                    articleRowMapper
            );

            final ArticleGetByIdResponseDTO responseDTO = new ArticleGetByIdResponseDTO(new ArticleGetByIdResponseDTO.Article(
                    article.getId(),
                    article.getName(),
                    article.getCategory(),
                    article.getText(),
                    article.getFirstItemId(),
                    article.getSecondItemId(),
                    article.getThirdItemId(),
                    article.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException(e);
        }
    }

    public ArticleSaveResponseDTO save(ArticleSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE articles SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ArticleNotFoundException("article with id " + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE articles SET removed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ItemNotFoundException("item with id " + id + " restored");
        }
    }

    private ArticleSaveResponseDTO create(ArticleSaveRequestDTO requestDTO) {
        final ArticleModel article = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO articles (name, category, text, first_item_id,
                        second_item_id, third_item_id, image) VALUES (:name, :category, :text, :firstItemId,
                        :secondItemId, :thirdItemId, :image)
                        RETURNING id, name, category, text, first_item_id, second_item_id, third_item_id, image
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "category", requestDTO.getCategory(),
                        "text", requestDTO.getText(),
                        "firstItemId", requestDTO.getFirstItemId(),
                        "secondItemId", requestDTO.getSecondItemId(),
                        "thirdItemId", requestDTO.getThirdItemId(),
                        "image", getImage(requestDTO.getImage())
                ),
                articleRowMapper
        );

        final ArticleSaveResponseDTO responseDTO = new ArticleSaveResponseDTO(new ArticleSaveResponseDTO.Article(
                article.getId(),
                article.getName(),
                article.getCategory(),
                article.getText(),
                article.getFirstItemId(),
                article.getSecondItemId(),
                article.getThirdItemId(),
                article.getImage()
        ));

        return responseDTO;
    }

    private ArticleSaveResponseDTO update(ArticleSaveRequestDTO requestDTO) {
        try {
            final ArticleModel article = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE articles SET name= :name, category = :category, text = :text, first_item_id = :firstItemId,
                            second_item_id = :secondItemId, third_item_id = :thirdItemId, image = :image
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, name, category, text, first_item_id, second_item_id, third_item_id, image
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "name", requestDTO.getName(),
                            "category", requestDTO.getCategory(),
                            "text", requestDTO.getText(),
                            "firstItemId", requestDTO.getFirstItemId(),
                            "secondItemId", requestDTO.getSecondItemId(),
                            "thirdItemId", requestDTO.getThirdItemId(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    articleRowMapper
            );

            final ArticleSaveResponseDTO responseDTO = new ArticleSaveResponseDTO(new ArticleSaveResponseDTO.Article(
                    article.getId(),
                    article.getName(),
                    article.getCategory(),
                    article.getText(),
                    article.getFirstItemId(),
                    article.getSecondItemId(),
                    article.getThirdItemId(),
                    article.getImage()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }
}
