INSERT INTO Vote (id, title, description) VALUES
  (1, 'Where to go?', 'Tell me your favorite destination'),
  (2, 'What programming language?', 'Which programming language do you prefer?'),
  (3, 'Which dessert?', 'Whats your favourite dessert?');

INSERT INTO Choice (vote_id, id, count, title) VALUES
    (1, 11, 3, 'London'),
    (1, 12, 6, 'Berlin'),
    (1, 13, 9, 'Paris'),
    (2, 21, 23, 'Java'),
    (2, 22, 17, 'C#'),
    (2, 23, 3, 'Kotlin'),
    (3, 31, 7, 'Crème Brûlée'),
    (3, 32, 2, 'Icecream'),
    (3, 33, 0, 'Tiramisu');
