alter table alocacoes_patrimonios add constraint UK_eoqymnfx15hon7wllrhfta7ii unique (patrimonios_id);
alter table baixas_patrimonio add constraint UK_d6ma2okgd7scjfq31gw3jnvxd unique (patrimonio_id);
alter table contato add constraint UK_dtiorue48pwcjh5yrpck0m25 unique (ds_contato);
alter table departamentos_allocations add constraint UK_7xdd57u8iprufgodavspp0ygf unique (allocations_id);
alter table fornecedor_patrimonies add constraint UK_edi3l4qmqdfr66cpmbpaa6tae unique (patrimonies_id_patrimonio);





