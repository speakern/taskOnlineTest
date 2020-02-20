INSERT INTO Queries (text, type, right_answer) VALUES
  ('Какого цвета апельсин?', 1, 1),
  ('У кого есть хобот? ответ в им. падеже', 2, 0),
  ('Что такое yandex.ru?', 1, 0),
  ('Сколько часов в сутках? написать число', 2, 0),
  ('Из чего делают творог?', 1, 3);

INSERT INTO Answers (text, query_num, query_id) VALUES
  ('красный', 0, 1),
  ('оранжевый', 1, 1),
  ('зеленый', 2, 1),
  ('синий', 3, 1),
  ('слон', 0, 2),
  ('поисковик', 0, 3),
  ('машина', 1, 3),
  ('утюг', 2, 3),
  ('утюг2', 3, 3),
  ('24', 0, 4),
  ('мясо', 0, 5),
  ('мука', 1, 5),
  ('квас', 2, 5),
  ('молоко', 3, 5);

INSERT INTO users(username,password,enabled) VALUES
  ('admin','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true),  --два вопроса правильно, Хуже него: 3, лучше 3, также 0
  ('u','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true), --все вопросы правильно, Хуже него: 5, лучше 0, также 1
  ('u2','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true), --все вопросы правильно,  Хуже него: 5,  лучше 0, также 1
  ('u3','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true),  -- все вопросы, 3 правильно, Хуже него: 4, лучше 2, также 0
  ('u4','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true),  -- два вопроса, один правильно,  Хуже него: 1, лучше 4, также 1
  ('u5','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true), -- два вопроса, один правильно,  Хуже него: 1, лучше 4, также 1
  ('u6','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true),  -- два вопроса неправильно,  Хуже него: 0, лучше 6, также 0
  ('u7','$2a$10$vMuE.6z9raagf1dT3.kf0uAOgsN9bU/x8GzW3tGCKaJC0nlRTt/1q',true);  -- не проходил тестирование,  Хуже него: 0, лучше 6, также 0

INSERT INTO authorities(user_id,authority) VALUES
  (1,'ROLE_ADMIN'),
  (1,'ROLE_USER'),
  (2,'ROLE_USER'),
  (3,'ROLE_USER'),
  (4,'ROLE_USER'),
  (5,'ROLE_USER'),
  (6,'ROLE_USER'),
  (7,'ROLE_USER'),
  (8,'ROLE_USER');

INSERT INTO user_answers(user_id, query_id, answer, is_correct) VALUES
  (1, 1, 'оранжевый', 1),
  (1, 2, 'слон', 1),
  (2, 1, 'оранжевый', 1),
  (2, 2, 'слон', 1),
  (2, 3, 'поисковик', 1),
  (2, 4, '24', 1),
  (2, 5, 'молоко', 1),
  (3, 1, 'оранжевый', 1),
  (3, 2, 'слон', 1),
  (3, 3, 'поисковик', 1),
  (3, 4, '24', 1),
  (3, 5, 'молоко', 1),
  (4, 1, 'оранжевый', 1),
  (4, 2, '12', 0),
  (4, 3, 'поисковик', 1),
  (4, 4, 'aasas', 0),
  (4, 5, 'молоко', 1),
  (5, 1, 'оранжевый', 1),
  (5, 2, '12', 0),
  (6, 4, 'оранжевый', 0),
  (6, 5, 'поисковик', 1),
  (7, 2, 'жевый', 0),
  (7, 4, 'aasas', 0);

