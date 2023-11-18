alter table obras add constraint FKku2cx7ftmqbok7dm9w7rsknv5 foreign key (usuario_id) references usuario (id);
alter table patrimonios add constraint FKtqbhxnpm5txggffuysy411vyr foreign key (fornecedor_id) references fornecedor (id);
alter table pessoa_contacts add constraint FK9mmqw94f83viud8f5vecafitn foreign key (contacts_id_contacts) references contato (id_contacts);
alter table pessoa_contacts add constraint FKmsirnrwrra8e1w7bielsova0v foreign key (pessoa_id) references pessoa (id);
alter table refresh_token add constraint FKenvguqmt5g4sywplefd88osg foreign key (user_id) references usuario (id);