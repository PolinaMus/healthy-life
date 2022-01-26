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
class ArticleCRUDTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformCRUD() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/articles/getAll")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                             "articles": [
                                               {
                                                 "id": 1,
                                                 "name": "How to heal acne?",
                                                 "category": "beauty",
                                                 "text": "If you're suffering from acne, you're not alone. Acne is a common skin condition that happens when oil and dead skin cells clog the skin’s pores.",
                                                 "firstItemId": 2,
                                                 "secondItemId": 3,
                                                 "thirdItemId": 4,
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 2,
                                                 "name": "Health Reset: The Best Supplements to Support Sleep, Skin, and Stress",
                                                 "category": "wellness",
                                                 "text": "Though seemingly unrelated, sleeping habits, skin health, and stress levels stay interconnected in more ways than one. If you aren’t sleeping well, your stress hormone cortisol may be elevated. Inversely, if you are stressed, your cortisol levels may be increased, causing difficulty falling and staying asleep.",
                                                 "firstItemId": 1,
                                                 "secondItemId": 5,
                                                 "thirdItemId": 7,
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 3,
                                                 "name": "3 Best Supplements to Consider for Muscular Hypertrophy",
                                                 "category": "fitness",
                                                 "text": "Lifters and athletes who hit the gym hard every week will generally have a few goals in mind for their training sessions. These goals usually revolve around strength, muscular hypertrophy, and other athletic and sport endeavors.",
                                                 "firstItemId": 5,
                                                 "secondItemId": 7,
                                                 "thirdItemId": 12,
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 4,
                                                 "name": "How to Balance Healthy Holiday Eating According to a Dietician",
                                                 "category": "nutrition",
                                                 "text": "Get your oven ready: holiday cooking is upon us. Fall and winter are packed with holidays and are arguably the most wonderful time of the year.",
                                                 "firstItemId": 10,
                                                 "secondItemId": 11,
                                                 "thirdItemId": 12,
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 5,
                                                 "name": "5 Tips To Help You Manage Your Cholesterol Levels",
                                                 "category": "conditions",
                                                 "text": "Cardiovascular disease (CVD) is the leading cause of death globally, with a yearly mortality rate of nearly 18 million people. There are many risk factors for CVD, one of them including high cholesterol levels.",
                                                 "firstItemId": 6,
                                                 "secondItemId": 9,
                                                 "thirdItemId": 12,
                                                 "image": "noimage.png"
                                               }
                                             ]
                                           }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/article/getById")
                                .param("id", String.valueOf(1))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "article": {
                                            "id": 1,
                                            "name": "How to heal acne?",
                                            "category": "beauty",
                                            "text": "If you're suffering from acne, you're not alone. Acne is a common skin condition that happens when oil and dead skin cells clog the skin’s pores.",
                                            "firstItemId": 2,
                                            "secondItemId": 3,
                                            "thirdItemId": 4,
                                            "image": "noimage.png"
                                          }
                                        }
                                """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/article/getById")
                                .param("id", String.valueOf(999))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNotFound()
                );

//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/article/save")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(
//                                        // language=JSON
//                                        """
//                                                {
//                                                "id": 0,
//                                                "name": "What’s in Your Multivitamin? Here Are 10 Ingredients To Look Out For",
//                                                "category": "wellness",
//                                                "text": "There is no question that the entire human body functions best when it has a steady supply of high-quality nutrition. A deficiency of any nutrient has profound effects on the human system because the body is a complex system dependent upon the proper functioning of many interdependent systems. ",
//                                                "first_item_id": 2,
//                                                "second_item_id": 5,
//                                                "third_item_id": 10
//                                                }
//                                                """
//                                )
//                )
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isOk(),
//                        MockMvcResultMatchers.content().json(
//                                // language=JSON
//                                """
//                                        {
//                                           "article": {
//                                           "id": 6,
//                                           "name": "What’s in Your Multivitamin? Here Are 10 Ingredients To Look Out For",
//                                           "category": "wellness",
//                                           "text": "There is no question that the entire human body functions best when it has a steady supply of high-quality nutrition. A deficiency of any nutrient has profound effects on the human system because the body is a complex system dependent upon the proper functioning of many interdependent systems. ",
//                                           "first_item_id": 2,
//                                           "second_item_id": 5,
//                                           "third_item_id": 10,
//                                           "image": "noimage.png"
//                                           }
//                                        }
//                                        """
//                        )
//                );
    }
}
