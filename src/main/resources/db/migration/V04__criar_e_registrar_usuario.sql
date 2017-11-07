

CREATE SEQUENCE usuario_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.usuario_id_seq
  OWNER TO postgres;
  
CREATE TABLE usuario
(
  codigo integer PRIMARY KEY DEFAULT nextval('usuario_id_seq'),
  nome VARCHAR(255)NOT NULL,
  email VARCHAR(50) NOT NULL,
   senha VARCHAR(150)NOT NULL    
);


CREATE SEQUENCE permissao_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.permissao_id_seq
  OWNER TO postgres;

CREATE TABLE permissao(
    codigo integer PRIMARY KEY DEFAULT nextval('permissao_id_seq'),
    descricao VARCHAR(50) NOT NULL
);


CREATE TABLE usuario_permissao
(
    codigo_usuario integer NOT NULL,
    codigo_permissao integer NOT NULL,     
    FOREIGN KEY (codigo_usuario)REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_permissao)REFERENCES permissao(codigo),
    PRIMARY KEY (codigo_usuario, codigo_permissao)
    
);
