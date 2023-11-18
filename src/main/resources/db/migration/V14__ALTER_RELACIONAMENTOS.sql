alter table requisicoes add constraint FK8f6sipbvd538ray7puba0mmnq foreign key (obra_id) references obras (id_obra);
alter table requisicoes_patrimonies add constraint FKs24q82eg2t8odk3ad3atbbp2x foreign key (patrimonies_id) references requisicoes_patrimonio (id);
alter table requisicoes_patrimonies add constraint FKljeul9o2vxkdcm39bme1gkhfj foreign key (requests_id) references requisicoes (id);
alter table requisicoes_patrimonio add constraint FKks61lbfjwwfnuqmkmxq28xst1 foreign key (patrimonio_id) references patrimonios (id_patrimonio);
alter table requisicoes_patrimonio add constraint FKf86ah8bqrpr6jn9ivdgevmgqf foreign key (requisicao_id) references requisicoes (id);
alter table usuario add constraint FKnndxavgf8rogrx6jdmxbgm6s3 foreign key (id) references pessoa (id);