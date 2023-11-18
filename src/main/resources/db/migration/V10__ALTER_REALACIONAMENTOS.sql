alter table alocacoes_patrimonio add constraint FKbmejlcnbv37ilt2r039m3ilha foreign key (alocacao_id) references alocacoes (id);
alter table alocacoes_patrimonio add constraint FKpr1c1y341s6npc9vrbd7ny4ey foreign key (patrimonio_id) references patrimonios (id_patrimonio);
alter table alocacoes_patrimonios add constraint FK54vsp3i0ilo4rjwb7lwk7lnxg foreign key (patrimonios_id) references alocacoes_patrimonio (id);
alter table alocacoes_patrimonios add constraint FKga00lcj90hmy9dmceqhqkpctp foreign key (allocation_id) references alocacoes (id);
alter table baixas_patrimonio add constraint FKrm1yxglvjnjcukfwtjnl8v467 foreign key (patrimonio_id) references patrimonios (id_patrimonio);