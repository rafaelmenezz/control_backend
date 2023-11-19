INSERT INTO `obras` VALUES 
(1,NULL,NULL,'2023-11-13 00:00:00.000000','2024-06-12 00:00:00.000000','Ponte do Imaruim','Palhoça','Faculdade Senac Palhoca','Universidade','Rua João Pereira dos Santos','Pavimento Superior Senac','SC','88130-475','41.141.414/1414-14',13,1),
(2,NULL,NULL,'2023-11-05 00:00:00.000000','2024-11-05 00:00:00.000000','Serraria','São José','Residencial Jardins Sao Jose II','','Rua César Zuchinalli','Reforma Jardins Sao Jose','SC','88115-210','64.646.446/4646-46',12,1),
(3,NULL,NULL,'2023-11-08 00:00:00.000000','2024-07-15 00:00:00.000000','São Sebastião','Palhoça','Residencial Iris','','Rua Ernesto Albino','Apto Residencial Iris','SC','88136-015','64.646.978/5997-87',197,1),
(4,NULL,NULL,'2023-11-14 00:00:00.000000','2025-11-14 00:00:00.000000','Pagani','Palhoça','Shopping Palhoca Incorporadora','','Avenida Atílio Pedro Pagani','Construcao Shopping Palhoca','SC','88132-149','44.346.946/9796-49',12,1),
(5,NULL,NULL,'2023-01-12 00:00:00','2023-03-27 00:00:00','Jardim Atlântico','Florianópolis','Ruth Rocha','Mini Mercado Rocha','Rua Nossa Senhora do Rosário','Reforma Loja 20','SC','88095-250','087.470.629-75',513,25),
(6,NULL,NULL,'2023-02-20 00:00:00','2023-04-05 00:00:00','Abraão','Florianópolis','Marcelo Mendes','Schimit Contabilida','Rua João Meirelles','Pavimentação Garagem','SC','88085-200','79.263.620/0001-73',648,26),
(7, NULL,NULL,'2023-03-03 00:00:00','2023-05-19 00:00:00','Ponta de Baixo','São José','Maria das Flores Bittencourt','Restaurante Bom Pescado','Rua Ilha Terceira','Construção 2° Piso Restaurante','SC','88104-238','24.532.953/0001-69',115,27);


INSERT INTO `requisicoes` VALUES (1,1),(2,2),(3,3),(5,3),(4,4),(6,4);

INSERT INTO `requisicoes_patrimonio` VALUES 
(1,NULL, NULL,'2023-11-08 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',1,1),
(2,NULL, NULL,'2023-11-08 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',4,1),
(3,NULL, NULL,'2023-11-08 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',13,1),
(4,NULL, NULL,'2023-11-05 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',16,2),
(5,NULL, NULL,'2023-11-05 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',3,2),
(6,NULL, NULL,'2023-11-08 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',2,3),
(7,NULL, NULL,'2023-11-08 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',5,3),
(8,NULL, NULL,'2023-11-08 00:00:00.000000','2023-11-16 00:00:00.000000','Ativo',14,3),
(9,NULL, NULL,'2023-11-14 00:00:00.000000',NULL,'Ativo',6,4),
(10,NULL, NULL,'2023-11-14 00:00:00.000000',NULL,'Ativo',11,4),
(11,NULL, NULL,'2023-11-14 00:00:00.000000',NULL,'Ativo',15,4),
(12,NULL, NULL,'2023-11-09 00:00:00.000000','2023-11-18 00:00:00.000000','Ativo',23,5),
(13,NULL, NULL,'2023-11-06 00:00:00.000000','2023-11-18 00:00:00.000000','Ativo',25,6);

INSERT INTO `requisicoes_patrimonies` VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(3,6),(3,7),(3,8),(4,9),(4,10),(4,11),(5,12),(6,13);