alter table pessoa_contacts add constraint FKm89eqgux0thvmlx267edv8o21 foreign key (contacts_id_contato) references contato (id_contato);
INSERT INTO pessoa (`type`,nm_name,tp_status,tp_documento, id) VALUES ('User','Administrador','Ativo','CPF', 1);
INSERT INTO usuario (ft_foto,nm_senha,nr_cpf,nr_matricula,primeiro_acesso,tp_usuario,id) VALUES ('1191120231708.jpg','$2a$10$9K.I7ZcE1zi5rpr4.MEZ8OIM4XNMPtyKK/9yjHttCSt80HcoiwGmG','111.111.111-11','2121',0,'Admin',1);
INSERT INTO contato (id_contato,ds_contato,tipo_contato,pessoa_id) VALUES
(1,'joseedston@teste.com','E-mail',1),
(2,'48 99999 9999','Telefone',1);
INSERT INTO pessoa_contacts (pessoa_id,contacts_id_contato) VALUES
(1,1),
(1,2);