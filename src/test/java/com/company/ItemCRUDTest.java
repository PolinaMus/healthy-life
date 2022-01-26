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
class ItemCRUDTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformCRUD() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/items/getAll")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                        "items": [
                                            {
                                              "id": 1,
                                              "name": "vitamin d",
                                              "price": 500,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 2,
                                              "name": "vitamin c",
                                              "price": 650,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 3,
                                              "name": "vitamin a",
                                              "price": 900,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 4,
                                              "name": "vitamin e",
                                              "price": 1100,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 5,
                                              "name": "b complex",
                                              "price": 1300,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 6,
                                              "name": "magnesium",
                                              "price": 1200,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 7,
                                              "name": "calcium",
                                              "price": 750,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 8,
                                              "name": "collagen",
                                              "price": 1500,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 9,
                                              "name": "omega 3",
                                              "price": 1900,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 10,
                                              "name": "probiotics",
                                              "price": 1500,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 11,
                                              "name": "multivitamin",
                                              "price": 2000,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 12,
                                              "name": "enzymes",
                                              "price": 1700,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            },
                                            {
                                              "id": 13,
                                              "name": "protein",
                                              "price": 1350,
                                              "qty": 500,
                                              "image": "noimage.png"
                                            }
                                          ]
                                          }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/items/getById")
                                .param("id", String.valueOf(2))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "item": {
                                            "id": 2,
                                            "name": "vitamin c",
                                            "price": 650,
                                            "qty": 500,
                                            "image": "noimage.png"
                                          }
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/items/getById")
                                .param("id", String.valueOf(999))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNotFound()
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/items/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                    "id": 0,
                                                    "name": "Spirulina",
                                                    "price": 1000,
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
                                          "item": {
                                            "id": 14,
                                            "name": "Spirulina",
                                            "price": 1000,
                                            "qty": 10,
                                            "image": "noimage.png"
                                          }
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/items/getPopular")
                )
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "items": [
                                            {
                                              "id": 12,
                                              "count": 3,
                                              "name": "enzymes"
                                            },
                                            {
                                              "id": 6,
                                              "count": 1,
                                              "name": "magnesium"
                                            },
                                            {
                                              "id": 2,
                                              "count": 1,
                                              "name": "vitamin c"
                                            },
                                            {
                                              "id": 1,
                                              "count": 1,
                                              "name": "vitamin d"
                                            },
                                            {
                                              "id": 10,
                                              "count": 1,
                                              "name": "probiotics"
                                            }
                                          ]
                                        }
                                        """
                        )
                );
    }
}
