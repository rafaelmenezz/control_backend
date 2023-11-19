create table pessoa (type varchar(31) not null, id bigint not null auto_increment, nm_name varchar(255), tp_status varchar(255), tp_documento varchar(255), primary key (id));
create table pessoa_contacts (pessoa_id bigint not null, contacts_id_contato bigint not null);
create table contato (id_contato bigint not null auto_increment, ds_contato varchar(100) not null, tipo_contato varchar(255) not null, pessoa_id bigint, primary key (id_contato));
create table usuario (ft_foto varchar(255), nm_senha varchar(255), nr_cpf varchar(255), nr_matricula varchar(255), primeiro_acesso bit, tp_usuario varchar(15) not null, id bigint not null, primary key (id));
create table fornecedor (nr_cnpj varchar(255), id bigint not null, primary key (id));
create table fornecedor_patrimonies (fornecedor_id bigint not null, patrimonies_id_patrimonio bigint not null);