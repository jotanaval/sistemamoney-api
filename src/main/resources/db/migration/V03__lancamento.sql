CREATE SEQUENCE lancamento_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.lancamento_id_seq
  OWNER TO postgres;
  
CREATE TABLE lancamento
(
  codigo integer PRIMARY KEY DEFAULT nextval('lancamento_id_seq'),
    descricao VARCHAR(50)NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(10,2)NOT NULL,
    observacao VARCHAR(100)NOT NULL,
    tipo VARCHAR(20)NOT NULL,
    codigo_categoria INTEGER NOT NULL,
    codigo_pessoa INTEGER NOT NULL,
    FOREIGN KEY (codigo_categoria)REFERENCES categoria(codigo),
    FOREIGN KEY (codigo_pessoa)REFERENCES pessoa(codigo)
    
);
