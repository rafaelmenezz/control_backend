alter table contato add constraint FK2h6l5bma9cyyjy8ytvky10n1c foreign key (id_pessoa) references pessoa (id);
alter table departamentos add constraint FKatm85a14repnat1xesfkue323 foreign key (id_usuario) references usuario (id);
alter table departamentos_allocations add constraint FKq8ruw7twnu95r78xowrek02ey foreign key (allocations_id) references alocacoes (id);
alter table departamentos_allocations add constraint FKp683pys3dfm0anq10jleumgk8 foreign key (department_id_departamento) references departamentos (id_departamento);
alter table fornecedor add constraint FK4fumwc92qrfurlo1g6jhos1sq foreign key (id) references pessoa (id);