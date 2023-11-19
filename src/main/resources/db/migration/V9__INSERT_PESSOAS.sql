INSERT INTO `pessoa` VALUES 
('Fornecedor',2,'Cassol Center Lar','Ativo',NULL),
('Fornecedor',3,'Compensados Fernandes','Ativo',NULL),
('Fornecedor',4,'Positivo Distribuidora de Computadores','Ativo',NULL),
('Fornecedor',5,'Escritolandia Materiais de Escritorio','Ativo',NULL),
('Fornecedor',6,'Casas da Agua Materiais de Construcao','Ativo',NULL),
('Fornecedor',7,'Casas Bahia Comercio Atacadista','Ativo',NULL),
('Fornecedor',8,'Dell Computadores do Brasil','Ativo',NULL),
('User',10,'Cara Foda','Ativo','CPF'),
('User',12,'Cara Meia Boca','Ativo','CPF'),
('Fornecedor',13,'Casas das Mangueiras e Hidráulica','Ativo',NULL);

INSERT INTO `usuario` VALUES 
(NULL,'$2a$10$SHEKHzjvdGcwfQIap8zrC.r/H9Nb8QWA8wIMBk57SM9u4vRLIwum2','222.222.222-22','2130',_binary '\0','Gestor',10),
(NULL,'$2a$10$lV780mvf1N0kfud3bqqrF.M1SX7iHq529NutKVsETZRgtqyFu5bwG','333.333.333-33','2129',_binary '\0','Requisitante',12);

INSERT INTO `fornecedor` VALUES 
('11.111.111/1111-11',2),
('33.333.333/3333-33',3),
('44.444.444/4444-44',4),
('55.555.555/5555-44',5),
('22.222.222/2222-22',6),
('66.666.666/6666-66',7),
('45.454.545/4545-45',8),
('77.777.777/7777-77',13);

INSERT INTO `contato` VALUES 
(3,'joseedston@gmail.com','E-mail',10),
(4,'gcp.tcs.senac2023@gmail.com','E-mail',12);