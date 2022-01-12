package com.company.manager;

import com.company.dto.*;
import com.company.exception.ItemNotFoundException;
import com.company.model.ItemFrequencyModel;
import com.company.model.ItemModel;
import com.company.rowmapper.ItemFrequencyRowMapper;
import com.company.rowmapper.ItemRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ItemManager {
    private final NamedParameterJdbcTemplate template;
    private final ItemRowMapper itemRowMapper;
    private final ItemFrequencyRowMapper itemFrequencyRowMapper;
    private final String defaultImage = "noimage.png";

    public ItemGetAllResponseDTO getAll() {
        final List<ItemModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, price, qty, image FROM items
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                itemRowMapper
        );

        final ItemGetAllResponseDTO responseDTO = new ItemGetAllResponseDTO(new ArrayList<>(items.size()));
        for (ItemModel item : items) {
            responseDTO.getItems().add(new ItemGetAllResponseDTO.Item(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getImage()
            ));
        }

        return responseDTO;
    }

    public ItemGetByIdResponseDTO getById(long id) {
        try {

            final ItemModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, price, qty,image FROM items
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", id),
                    itemRowMapper
            );
            final ItemGetByIdResponseDTO responseDTO = new ItemGetByIdResponseDTO(new ItemGetByIdResponseDTO.Item(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException(e);
        }
    }

    public ItemSaveResponseDTO save(ItemSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE items SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ItemNotFoundException("item with id " + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE items SET removed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ItemNotFoundException("item with id " + id + " restored");
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }

    private ItemSaveResponseDTO create(ItemSaveRequestDTO requestDTO) {
        final ItemModel item = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO items (name, price, qty, image) VALUES (:name, :price, :qty, :image)
                        RETURNING id, name, price,qty, image
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "price", requestDTO.getPrice(),
                        "qty", requestDTO.getQty(),
                        "image", getImage(requestDTO.getImage())
                ),
                itemRowMapper
        );
        final ItemSaveResponseDTO responseDTO = new ItemSaveResponseDTO(new ItemSaveResponseDTO.Item(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getQty(),
                item.getImage()
        ));
        return responseDTO;
    }

    private ItemSaveResponseDTO update(ItemSaveRequestDTO requestDTO) {
        try {
            final ItemModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE items SET name = :name, price = :price, qty = :qty, image = :image
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, name, price,qty, image
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "name", requestDTO.getName(),
                            "price", requestDTO.getPrice(),
                            "qty", requestDTO.getQty(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    itemRowMapper
            );

            final ItemSaveResponseDTO responseDTO = new ItemSaveResponseDTO(new ItemSaveResponseDTO.Item(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException(e);
        }
    }

    public ItemGetPopularResponseDTO getPopular() {
        final List<ItemFrequencyModel> items = template.query(
                // language=PostgreSQL
                """
                        WITH item_freq AS (
                            SELECT first_item_id item_id, count(*) count
                            FROM articles
                            GROUP BY first_item_id
                            UNION ALL
                            SELECT second_item_id item_id, count(*) count
                            FROM articles
                            GROUP BY second_item_id
                            UNION ALL
                            SELECT third_item_id item_id, count(*) count
                            FROM articles
                            GROUP BY third_item_id
                        )
                        SELECT if.item_id id, if.count, i.name FROM item_freq if
                        LEFT JOIN items i ON if.item_id = i.id
                        ORDER BY if.count DESC
                        LIMIT 5;
                        """,
                itemFrequencyRowMapper
        );
        final ItemGetPopularResponseDTO responseDTO = new ItemGetPopularResponseDTO(new ArrayList<>(items.size()));
        for (ItemFrequencyModel item : items) {
            responseDTO.getItems().add(new ItemGetPopularResponseDTO.Item(
                    item.getId(),
                    item.getCount(),
                    item.getName()
            ));
        }
        return responseDTO;
    }
}


