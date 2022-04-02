-- population de la table `Users` :

	-- USER 1
INSERT INTO `Users` (`email`, `salt`, `salted_hash`)
VALUES ('test1@mail.fr', SUBSTR(SHA2(RAND(), 256), 1, 12), SHA2(CONCAT('password1234', `salt`), 256));

	-- USER 2
INSERT INTO `Users` (`email`, `username`, `salt`, `salted_hash`)
VALUES ('test2@mail.fr', 'User2', SUBSTR(SHA2(RAND(), 256), 1, 12), SHA2(CONCAT('password1234', `salt`), 256));

	-- USER 3
INSERT INTO `Users` (`email`, `balance`, `bank_account_number`, `salt`, `salted_hash`)
VALUES ('test.3@mail.fr', 10000, '0138297465BIC', SUBSTR(SHA2(RAND(), 256), 1, 12), SHA2(CONCAT('333password', `salt`), 256));

	-- USER 4
INSERT INTO `Users` (`email`, `username`, `balance`, `bank_account_number`, `salt`, `salted_hash`)
VALUES ('test44@mail.fr', 'User4', 29283098, '02473655757AAA', SUBSTR(SHA2(RAND(), 256), 1, 12), SHA2(CONCAT('p4ssword44', `salt`), 256));

-- population de la table `Connections` :
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (1, 2);
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (3, 4);
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (2, 3);
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (3, 1);
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (1, 4);

	-- les insertions suivantes doivent échouer dès la première tentative
		-- Cas n°1 : les valeurs exactent existent déjà
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (1, 2);

		-- Cas n°2 : les valeurs inverses existent déjà
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (4, 1);

		-- Cas n°3 : les valeurs sont identiques
INSERT INTO `Connections` (`user_id1`, `user_id2`) VALUES (1, 1);

-- population de la table `Transactions` :
INSERT INTO `Transactions` (`type`, `amount`, `description`, `sender_id`, `receiver_id`)
VALUES ('user', 3736, 'Achat de voiture d\'occasion', 1, 2);

INSERT INTO `Transactions` (`type`, `amount`, `description`, `sender_id`, `receiver_id`)
VALUES ('fromBank', 200, 'Retrait d\'argent', 3, 3);

INSERT INTO `Transactions` (`type`, `amount`, `sender_id`, `receiver_id`)
VALUES ('user', 10, 4, 2);

INSERT INTO `Transactions` (`type`, `amount`, `description`, `sender_id`, `receiver_id`)
VALUES ('user', 150, 'Cadeau d\'anniversaire', 3, 1);
