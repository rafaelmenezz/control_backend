INSERT INTO `departamentos` VALUES 
(1,'Recebimento','Ativo',1),
(2,'Controladoria','Ativo',1),
(3,'Financeiro','Ativo',1),
(4,'RH','Ativo',1),
(5,'Informatica','Ativo',1),
(6,'Cozinha','Ativo',18),
(7,'Administração','Ativo',17),
(8, 'Contabilidade','Ativo',19),
(9, 'Engenharia','Ativo',14),
(10,'Marketing','Ativo',20),
(11,'Serviço Gerais','Ativo',15);


INSERT INTO `alocacoes` VALUES 
(3,2),
(6,2),
(4,3),
(5,4),
(1,5),
(2,5);

INSERT INTO `alocacoes_patrimonio` VALUES 
(1,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',1,18),
(2,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',2,17),
(3,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',3,12),
(4,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',4,9),
(5,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',4,10),
(6,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',4,7),
(7,'2023-11-16 00:00:00.000000',NULL,NULL,'Ativo',5,8),
(8,'2023-11-18 00:00:00.000000',NULL,NULL,'Ativo',6,24);


INSERT INTO `alocacoes_patrimonios` VALUES 
(1,1),
(2,2),
(3,3),
(4,4),
(4,5),
(4,6),
(5,7),
(6,8);