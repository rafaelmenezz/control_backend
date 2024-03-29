UPDATE patrimonios
SET dt_aquisicao='2023-11-01 00:00:00', dt_nota_fiscal='2023-11-01 00:00:00', fl_fixo=0, ds_patrimonio='Balde de pedreiro de 15L para pegar areia, cimento, brita e outros', nm_patrimonio='Balde de pedreiro de 15L', nr_nota_fiscal=13466, nr_serie='1346', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=32.0, fornecedor_id=6
WHERE id_patrimonio=25;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Maquita para utilizar no corte
', nm_patrimonio='Maquita', nr_nota_fiscal=1011, nr_serie='123456', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=989.0, fornecedor_id=2
WHERE id_patrimonio=1;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Furadeira de Alto Impacto Black Denker 
', nm_patrimonio='Furadeira Black Denker', nr_nota_fiscal=1013, nr_serie='9792', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=351.0, fornecedor_id=2
WHERE id_patrimonio=3;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Betoneira com capacidade de 100 litros', nm_patrimonio='Betoneira 100L', nr_nota_fiscal=1014, nr_serie='46743', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=1665.0, fornecedor_id=2
WHERE id_patrimonio=4;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Pa de Corte', nm_patrimonio='Pa de Corte', nr_nota_fiscal=2010, nr_serie='749463', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=199.98, fornecedor_id=3
WHERE id_patrimonio=5;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Pa para coleta de areia, argamassa e cimento', nm_patrimonio='Pa de Mexer Massa', nr_nota_fiscal=2011, nr_serie='34646', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=188.01, fornecedor_id=3
WHERE id_patrimonio=6;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Perfuradora de poco profundo', nm_patrimonio='Perfuradora de poco', nr_nota_fiscal=34126, nr_serie='487749', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=7989.98, fornecedor_id=6
WHERE id_patrimonio=11;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Martelo Super Power ', nm_patrimonio='Martelo Super Power', nr_nota_fiscal=31446, nr_serie='5646', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=285.0, fornecedor_id=6
WHERE id_patrimonio=13;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Carrinho de mao com capacidade para carregar coisas', nm_patrimonio='Carrinho de Mao', nr_nota_fiscal=463166, nr_serie='67981', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=890.5, fornecedor_id=6
WHERE id_patrimonio=15;

UPDATE patrimonios
SET dt_aquisicao='2023-10-31 00:00:00', dt_nota_fiscal='2023-10-31 00:00:00', fl_fixo=0, ds_patrimonio='Chave de Fenda Iluminati para apertar as coisas', nm_patrimonio='Chave de Fenda Iluminati', nr_nota_fiscal=6433, nr_serie='4642', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=59.0, fornecedor_id=6
WHERE id_patrimonio=16;

UPDATE patrimonios
SET dt_aquisicao='2023-11-01 00:00:00', dt_nota_fiscal='2023-11-01 00:00:00', fl_fixo=0, ds_patrimonio='Balde de pedreiro de 15L para pegar areia, cimento, brita e outros', nm_patrimonio='Balde de pedreiro de 15L', nr_nota_fiscal=13466, nr_serie='1346', tp_situacao='Registrado', tp_status='Ativo', vl_aquisicao=32.0, fornecedor_id=6
WHERE id_patrimonio=25;

UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-18 00:00:00', dt_previsao_retirada='2023-11-08 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=1, requisicao_id=1
WHERE id=1;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-20 00:00:00', dt_previsao_retirada='2023-11-08 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=4, requisicao_id=1
WHERE id=2;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-20 00:00:00', dt_previsao_retirada='2023-11-08 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=13, requisicao_id=1
WHERE id=3;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-15 00:00:00', dt_previsao_retirada='2023-11-05 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=16, requisicao_id=2
WHERE id=4;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-16 00:00:00', dt_previsao_retirada='2023-11-05 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=3, requisicao_id=2
WHERE id=5;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-22 00:00:00', dt_previsao_retirada='2023-11-08 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=2, requisicao_id=3
WHERE id=6;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-22 00:00:00', dt_previsao_retirada='2023-11-08 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=5, requisicao_id=3
WHERE id=7;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-21 00:00:00', dt_previsao_retirada='2023-11-08 00:00:00', dt_retirada='2023-11-16 00:00:00', status='Ativo', patrimonio_id=14, requisicao_id=3
WHERE id=8;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-19 00:00:00', dt_previsao_retirada='2023-11-14 00:00:00', dt_retirada=NULL, status='Ativo', patrimonio_id=6, requisicao_id=4
WHERE id=9;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-21 00:00:00', dt_previsao_retirada='2023-11-14 00:00:00', dt_retirada=NULL, status='Ativo', patrimonio_id=11, requisicao_id=4
WHERE id=10;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-20 00:00:00', dt_previsao_retirada='2023-11-14 00:00:00', dt_retirada=NULL, status='Ativo', patrimonio_id=15, requisicao_id=4
WHERE id=11;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-21 00:00:00', dt_previsao_retirada='2023-11-09 00:00:00', dt_retirada='2023-11-18 00:00:00', status='Ativo', patrimonio_id=23, requisicao_id=5
WHERE id=12;
UPDATE requisicoes_patrimonio
SET dt_devolucao=NULL, dt_previsao_devolucao='2023-11-22 00:00:00', dt_previsao_retirada='2023-11-06 00:00:00', dt_retirada='2023-11-18 00:00:00', status='Ativo', patrimonio_id=25, requisicao_id=6
WHERE id=13;


