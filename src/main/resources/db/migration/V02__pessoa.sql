CREATE SEQUENCE pessoa_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.pessoa_id_seq
  OWNER TO postgres;
  
CREATE TABLE pessoa
(
  codigo integer PRIMARY KEY DEFAULT nextval('pessoa_id_seq'),
  nome VARCHAR(255)NOT NULL,
  ativo BOOLEAN,
  logradouro VARCHAR(255),
  numero VARCHAR(255),
  complemento VARCHAR(255),
  bairro VARCHAR(255),
  cep VARCHAR(255),
  cidade VARCHAR(255),
  estado VARCHAR(255)    
);

