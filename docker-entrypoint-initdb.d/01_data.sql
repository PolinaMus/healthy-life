INSERT INTO items(name, price, qty, image)
VALUES ('vitamin d', 500, 500, 'noimage.png'),
       ('vitamin c', 650, 500, 'noimage.png'),
       ('vitamin a', 900, 500, 'noimage.png'),
       ('vitamin e', 1100, 500, 'noimage.png'),
       ('b complex', 1300, 500, 'noimage.png'),
       ('magnesium', 1200, 500, 'noimage.png'),
       ('calcium', 750, 500, 'noimage.png'),
       ('collagen', 1500, 500, 'noimage.png'),
       ('omega 3', 1900, 500, 'noimage.png'),
       ('probiotics', 1500, 500, 'noimage.png'),
       ('multivitamin', 2000, 500, 'noimage.png'),
       ('enzymes', 1700, 500, 'noimage.png'),
       ('protein', 1350, 500, 'noimage.png')
;

INSERT INTO articles(name, category, text, first_item_id, second_item_id, third_item_id, image)
VALUES ('How to heal acne?',
        'beauty', 'If you''re suffering from acne, you''re not alone. ' ||
                  'Acne is a common skin condition that happens when oil and dead skin cells clog the skin’s pores.',
        2, 3, 4, 'noimage.png'),
       ('Health Reset: The Best Supplements to Support Sleep, Skin, and Stress', 'wellness',
        'Though seemingly unrelated, sleeping habits, skin health, and stress levels stay interconnected in more ways than one. ' ||
        'If you aren’t sleeping well, your stress hormone cortisol may be elevated. Inversely, if you are stressed, ' ||
        'your cortisol levels may be increased, causing difficulty falling and staying asleep.',
        1, 5, 7, 'noimage.png'),
       ('3 Best Supplements to Consider for Muscular Hypertrophy', 'fitness',
        'Lifters and athletes who hit the gym hard every week will generally have a few goals in mind for their training sessions. ' ||
        'These goals usually revolve around strength, muscular hypertrophy, and other athletic and sport endeavors.',
        5, 7, 12, 'noimage.png'),
       ('How to Balance Healthy Holiday Eating According to a Dietician', 'nutrition',
        'Get your oven ready: holiday cooking is upon us. ' ||
        'Fall and winter are packed with holidays and are arguably the most wonderful time of the year.',
        10, 11, 12, 'noimage.png'),
       ('5 Tips To Help You Manage Your Cholesterol Levels', 'conditions',
        'Cardiovascular disease (CVD) is the leading cause of death globally, with a yearly mortality rate of nearly 18 million people. ' ||
        'There are many risk factors for CVD, one of them including high cholesterol levels.',
        6, 9, 12, 'noimage.png'),
       ('How to Shop for Vegan Beauty Products',
        'beauty',
        'A product being labeled vegan means that it was created without the use of ingredients from animals.',
        8, 13, 5, 'noimage.png'),
       ('A Dermatologist Breaks Down the Best Diet for Your Skin Type',
        'nutrition',
        'You’ve heard it before–you are what you eat. .',
        2, 5, 1, 'noimage.png')
;
