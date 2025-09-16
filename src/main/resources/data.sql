CREATE TABLE IF NOT EXISTS conta (
  id INT PRIMARY KEY AUTO_INCREMENT,
  cartao VARCHAR(255),
  saldo DOUBLE
);

insert into conta (cartao, saldo) values ('44441234', 500.00);
insert into conta (cartao, saldo) values ('33333444', 1100.00);
insert into conta (cartao, saldo) values ('12333444', 30.00);

CREATE TABLE IF NOT EXISTS transacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cartao VARCHAR(255),
    codigo BIGINT,
    valor DOUBLE,
    dataHora TIMESTAMP,
    estabelecimento VARCHAR(255),
    tipoPagamento VARCHAR(255),
    parcelas INT,
    status VARCHAR(255)
);

INSERT INTO transacao (cartao, codigo, valor, data_hora, estabelecimento, tipo_pagamento, parcelas, status) 
VALUES ('12333444', 13333, 150.75, CURRENT_TIMESTAMP, 'Pet Shop mundo cão', 'AVISTA', 3, 'AUTORIZADO');
INSERT INTO transacao (cartao, codigo, valor, data_hora, estabelecimento, tipo_pagamento, parcelas, status) 
VALUES ('12333444', 1444, 299.99, CURRENT_TIMESTAMP, 'Mercado Testt', 'AVISTA', 1, 'NEGADO');
INSERT INTO transacao (cartao, codigo, valor, data_hora, estabelecimento, tipo_pagamento, parcelas, status) 
VALUES ('33333444', 1555, 59.90, CURRENT_TIMESTAMP, 'Shoppiing', 'PARCELADO LOJA', 0, 'CANCELADO');
INSERT INTO transacao (cartao, codigo, valor, data_hora, estabelecimento, tipo_pagamento, parcelas, status) 
VALUES ('44441234', 16666, 1200.00, CURRENT_TIMESTAMP, 'Loja Test', 'PARCELADO EMISSOR', 12, 'AUTORIZADO');
