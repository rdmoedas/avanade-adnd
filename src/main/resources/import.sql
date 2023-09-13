-- This file is used to import data into the database

-- Inserting data into the character table
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('HERO', 'Warrior', 20, 7, 5, 6, 1, 12);
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('HERO', 'Barbarian', 21, 10, 2, 5, 2, 8);
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('HERO', 'Knight', 26, 6, 8, 3, 2, 6);
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('MONSTER', 'Orc', 42, 7, 1, 2, 3, 4);
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('MONSTER', 'Giant', 34, 10, 4, 4, 2, 6);
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('MONSTER', 'Werewolf', 34, 7, 4, 7, 2, 4);
INSERT INTO character ("type", "category", "hit_points", "strength",  "defense", "agility", "dice_amount", "dice_sides") VALUES ('MONSTER', 'Slime', 30, 2, 2, 2, 2, 4);

-- Inserting data into the player_character table
INSERT INTO player_character (id, name, player_name, character_id) VALUES ('acc19955-ceca-420b-ae2f-fbcf7d9dcaec', 'Battlehammer', 'Rodolfo', 1);
INSERT INTO player_character (id, name, player_name, character_id) VALUES ('b2e56e55-f4e8-4c7b-95ab-8c88c56da4f8', 'Wulfgar', 'Carol', 2);
INSERT INTO player_character (id, name, player_name, character_id) VALUES ('9602bec5-46bb-4ca6-b8e4-28bc01a1a009', 'Big Bad Wolf', 'Fables', 6);