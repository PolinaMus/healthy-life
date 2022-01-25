package com.company.manager;

import com.company.dto.SaleGetAllResponseDTO;
import com.company.dto.SaleGetByIdResponseDTO;
import com.company.dto.SaleRegisterRequestDTO;
import com.company.dto.SaleRegisterResponseDTO;
import com.company.exception.*;
import com.company.model.SaleItemModel;
import com.company.model.SaleModel;
import com.company.rowmapper.SaleItemRowMapper;
import com.company.rowmapper.SaleRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SaleManager {
    private final NamedParameterJdbcTemplate template;
    private final SaleItemRowMapper saleItemRowMapper;
    private final SaleRowMapper saleRowMapper;

    @Transactional
    public SaleRegisterResponseDTO register(SaleRegisterRequestDTO requestDTO) {
        try {
            final SaleItemModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, price, qty FROM items
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", requestDTO.getItemId()),
                    saleItemRowMapper
            );

            if (item.getQty() < requestDTO.getQty()) {
                throw new ItemQtyNotEnoughException("item with id " + item.getId() + " has qty " + item.getQty() + " but requested " + requestDTO.getQty());
            }

            if (item.getPrice() != requestDTO.getPrice()) {
                throw new ItemPriceChangedException("item with id " + item.getId() + " has price " + item.getPrice() + " but requested " + requestDTO.getPrice());
            }

            final int affected = template.update(
                    // language=PostgreSQL
                    """
                            UPDATE items SET qty = qty - :saleQty WHERE id = :itemId AND removed = FALSE
                            """,
                    Map.of(
                            "itemId", requestDTO.getItemId(),
                            "saleQty", requestDTO.getQty()
                    )
            );

            if (affected == 0) {
                throw new SaleRegistrationException("can't update qty with value " + requestDTO.getQty() + " for item with id " + requestDTO.getItemId());
            }

            final SaleModel sale = template.queryForObject(
                    // language=PostgreSQL
                    """
                            INSERT INTO sales (item_id, name, price, qty) VALUES (:itemId, :name, :price, :qty)
                            RETURNING id, item_id, name, price, qty
                            """,
                    Map.of(
                            "itemId", requestDTO.getItemId(),
                            "name", item.getName(),
                            "price", requestDTO.getPrice(),
                            "qty", requestDTO.getQty()
                    ),
                    saleRowMapper
            );

            return new SaleRegisterResponseDTO(new SaleRegisterResponseDTO.Sale(
                    sale.getId(),
                    sale.getItemId(),
                    sale.getName(),
                    sale.getPrice(),
                    sale.getQty()
            ));

        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException(e);
        }
    }

    public SaleGetAllResponseDTO getAll() {
        final List<SaleItemModel> sales = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, price, qty FROM sales
                        ORDER BY id
                        LIMIT 500
                        """,
                saleItemRowMapper
        );

        final SaleGetAllResponseDTO responseDTO = new SaleGetAllResponseDTO(new ArrayList<>(sales.size()));
        for (SaleItemModel sale : sales) {
            responseDTO.getSales().add(new SaleGetAllResponseDTO.Sale(
                    sale.getId(),
                    sale.getName(),
                    sale.getPrice(),
                    sale.getQty()
            ));
        }
        return responseDTO;
    }

    public SaleGetByIdResponseDTO getById(long id) {
        try {
            final SaleItemModel sale = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, price, qty FROM sales
                            WHERE id = :id
                            """,
                    Map.of("id", id),
                    saleItemRowMapper
            );

            final SaleGetByIdResponseDTO responseDTO = new SaleGetByIdResponseDTO(new SaleGetByIdResponseDTO.Sale(
                    sale.getId(),
                    sale.getName(),
                    sale.getPrice(),
                    sale.getQty()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new SaleNotFoundException(e);
        }
    }
}
