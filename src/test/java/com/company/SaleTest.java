package com.company;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class SaleTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformSale() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sales/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                "itemId": 9,
                                                "price": 1900,
                                                "qty": 10
                                                }
                                                """
                                )
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                        "sale": {
                                        "id": 1,
                                        "itemId": 9,
                                        "name": "omega 3",
                                        "price": 1900,
                                        "qty": 10
                                        }
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/items/getById")
                                .param("id", String.valueOf(9))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "item": {
                                            "id": 9,
                                            "name": "omega 3",
                                            "price": 1900,
                                            "qty": 490,
                                            "image": "noimage.png"
                                          }
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sales/getAll")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "sales": [
                                            {
                                              "id": 1,
                                              "name": "omega 3",
                                              "price": 1900,
                                              "qty": 10
                                            },
                                            {
                                              "id": 2,
                                              "name": "vitamin a",
                                              "price": 900,
                                              "qty": 2
                                            },
                                            {
                                              "id": 3,
                                              "name": "magnesium",
                                              "price": 1200,
                                              "qty": 5
                                            }
                                          ]
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sales/getById")
                                .param("id", String.valueOf(2))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "sale": {
                                            "id": 2,
                                            "name": "vitamin a",
                                            "price": 900,
                                            "qty": 2
                                          }
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sales/getById")
                                .param("id", String.valueOf(999))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }
}
