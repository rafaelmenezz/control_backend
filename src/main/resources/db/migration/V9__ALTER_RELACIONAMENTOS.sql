alter table pessoa_contacts add constraint UK_jjkd4s522ck1ueotmtxaono1o unique (contacts_id_contacts);
alter table refresh_token add constraint UK_f95ixxe7pa48ryn1awmh2evt7 unique (user_id);
alter table requisicoes_patrimonies add constraint UK_lkyi5xmuw161e2nyhv5dolo46 unique (patrimonies_id);
alter table usuario add constraint UK_astdalhsb9elvp59ql14fs8ku unique (nr_cpf);
alter table alocacoes add constraint FKshe11nv3ceb4shmpo9rvgi8bs foreign key (id_departamento) references departamentos (id_departamento);