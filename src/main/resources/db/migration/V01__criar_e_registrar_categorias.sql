CREATE SEQUENCE categoria_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.categoria_id_seq
  OWNER TO postgres;
  
CREATE TABLE categoria
(
  codigo integer PRIMARY KEY DEFAULT nextval('categoria_id_seq'),
  nome VARCHAR(50)NOT NULL  
);
INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Supermercado');
INSERT INTO categoria (nome) values ('Farmácia');
INSERT INTO categoria (nome) values ('Outros');

