-- population de la table `Users` :
INSERT INTO `Users` (`email`, `password`)
VALUES ('test1@mail.fr', 'password1234');

INSERT INTO `Users` (`email`, `password`, `username`)
VALUES ('test2@mail.fr', '12password34', 'User2');

INSERT INTO `Users` (`email`, `password`, `balance`, `bank_account_number`)
VALUES ('test.3@mail.fr', '333password', 10000, '0138297465BIC');

INSERT INTO `Users` (`email`, `password`, `username`, `balance`, `bank_account_number`)
VALUES ('test44@mail.fr', 'p4ssword44', 'User4', 29283098, '02473655757AAA');

-- population de la table `Connections` :
INSERT INTO `Connections` (`initiator_id`, `target_id`) VALUES (1, 2);
INSERT INTO `Connections` (`initiator_id`, `target_id`) VALUES (3, 4);
INSERT INTO `Connections` (`initiator_id`, `target_id`) VALUES (2, 3);
INSERT INTO `Connections` (`initiator_id`, `target_id`) VALUES (4, 1);
INSERT INTO `Connections` (`initiator_id`, `target_id`) VALUES (1, 4);
INSERT INTO `Connections` (`initiator_id`, `target_id`) VALUES (3, 1);

-- population de la table `Transactions` :
INSERT INTO `Transactions` (`type`, `amount`, `description`, `sender_id`, `receiver_id`)
VALUES ('user', 3736, 'Achat de voiture d\'occasion', 1, 2);

INSERT INTO `Transactions` (`type`, `amount`, `description`, `sender_id`, `receiver_id`)
VALUES ('bank', 200, 'Retrait d\'argent', 3, 3);

INSERT INTO `Transactions` (`type`, `amount`, `sender_id`, `receiver_id`)
VALUES ('user', 10, 4, 2);

INSERT INTO `Transactions` (`type`, `amount`, `description`, `sender_id`, `receiver_id`)
VALUES ('user', 150, 'Cadeau d\'anniversaire', 3, 1);
