alter table fornecedor_patrimonies add constraint FKj8q46t93o4n4x9y9b6t27no4g foreign key (patrimonies_id_patrimonio) references patrimonios (id_patrimonio);
alter table fornecedor_patrimonies add constraint FKsx7skbukc6jnvmq7456vstdf3 foreign key (fornecedor_id) references fornecedor (id);
alter table garantias add constraint FKdbyp6opvphq6vccsfmx9wmk5e foreign key (id_patrimonio) references patrimonios (id_patrimonio);
alter table manutencoes add constraint FKkvos30j8a1v6ueslutcdrernh foreign key (fornecedor_id) references fornecedor (id);
alter table manutencoes add constraint FK8gecug2q7ftdymbvpxwlesq03 foreign key (patrimonio_id) references patrimonios (id_patrimonio);