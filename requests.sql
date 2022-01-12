SELECT id, name, price AS total
FROM items
WHERE price BETWEEN 500 AND 1100;

SELECT first_item_id, count(*) count
FROM articles
GROUP BY first_item_id
ORDER BY count DESC
LIMIT 5;

SELECT second_item_id, count(*)
FROM articles
GROUP BY second_item_id;

SELECT third_item_id, count(*)
FROM articles
GROUP BY third_item_id;

SELECT a.name article_name, a.first_item_id, i.name vitamin_name
FROM items i
         INNER JOIN articles a ON a.first_item_id = i.id;

SELECT a.category,
       i1.name first_vitamin_name,
       i2.name second_vitamin_name,
       i3.name third_vitamin_name
FROM articles a
         LEFT JOIN items i1 on i1.id = a.first_item_id
         LEFT JOIN items i2 on i2.id = a.second_item_id
         LEFT JOIN items i3 on i3.id = a.third_item_id;

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


















