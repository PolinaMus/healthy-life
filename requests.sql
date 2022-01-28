-- Select 5 Vitamins where price ranging from 500 to 1500
SELECT id, name vitamin, price AS total
FROM items
WHERE price BETWEEN 500 AND 1500
ORDER BY price ASC
LIMIT 5;

-- The amount of Vitamins from the first position of the selection
WITH item_freq AS (
    SELECT first_item_id item_id, count(*) count
    FROM articles
    GROUP BY first_item_id
)
SELECT if.count, if.item_id first_item_id, i.name vitamin
FROM item_freq if
         LEFT JOIN items i ON if.item_id = i.id
ORDER BY if.count DESC;

-- The amount of Vitamins from the second position of the selection (5 vitamins)
WITH item_freq AS (
    SELECT second_item_id item_id, count(*) count
    FROM articles
    GROUP BY second_item_id
)
SELECT if.count, if.item_id second_item_id, i.name vitamin
FROM item_freq if
         LEFT JOIN items i ON if.item_id = i.id
ORDER BY if.count DESC
LIMIT 5;

-- The amount of Vitamins from the third position of the selection
WITH item_freq AS (
    SELECT third_item_id item_id, count(*) count
    FROM articles
    GROUP BY third_item_id
)
SELECT if.count, if.item_id third_item_id, i.name vitamin
FROM item_freq if
         LEFT JOIN items i ON if.item_id = i.id
ORDER BY if.count DESC;

-- Articles and selection of Vitamins from the first position
SELECT a.name article, a.first_item_id, i.name vitamin
FROM items i
         INNER JOIN articles a ON a.first_item_id = i.id;

-- Selection of articles, article categories and vitamins
SELECT a.name article,
       a.category,
       i1.name first_vitamin_name,
       i2.name second_vitamin_name,
       i3.name third_vitamin_name
FROM articles a
         LEFT JOIN items i1 on i1.id = a.first_item_id
         LEFT JOIN items i2 on i2.id = a.second_item_id
         LEFT JOIN items i3 on i3.id = a.third_item_id;

-- Top 5 vitamins
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
SELECT if.item_id id, i.name vitamin, if.count
FROM item_freq if
         LEFT JOIN items i ON if.item_id = i.id
ORDER BY if.count DESC
LIMIT 5;


















