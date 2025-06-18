-- TABELA: autores
CREATE TABLE autores (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         nacionalidade VARCHAR(100),
                         qtd_livros_escritos INT DEFAULT 0
);

-- TABELA: generos
CREATE TABLE generos (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL
);

-- TABELA: idiomas
CREATE TABLE idiomas (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL
);

-- TABELA: tipos
CREATE TABLE tipos (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL
);

-- TABELA: editoras
CREATE TABLE editoras (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          cnpj VARCHAR(20),
                          email VARCHAR(255),
                          telefone VARCHAR(20)
);

-- TABELA: livros
CREATE TABLE livros (
                        id BIGSERIAL PRIMARY KEY,
                        titulo VARCHAR(255) NOT NULL,
                        autor_id BIGINT NOT NULL,
                        capa_url VARCHAR(1024),
                        ano INT,
                        sinopse TEXT,
                        genero_id BIGINT NOT NULL,
                        editora_id BIGINT NOT NULL,
                        tipo_id BIGINT NOT NULL,
                        idioma_id BIGINT NOT NULL,
                        preco NUMERIC(10,2) NOT NULL,
                        qtd INT DEFAULT NULL,

                        CONSTRAINT fk_livro_autor FOREIGN KEY (autor_id) REFERENCES autores(id),
                        CONSTRAINT fk_livro_genero FOREIGN KEY (genero_id) REFERENCES generos(id),
                        CONSTRAINT fk_livro_editora FOREIGN KEY (editora_id) REFERENCES editoras(id),
                        CONSTRAINT fk_livro_tipo FOREIGN KEY (tipo_id) REFERENCES tipos(id),
                        CONSTRAINT fk_livro_idioma FOREIGN KEY (idioma_id) REFERENCES idiomas(id)
);

-- Índices para performance
CREATE INDEX idx_livro_titulo ON livros(titulo);
CREATE INDEX idx_livro_genero ON livros(genero_id);
CREATE INDEX idx_livro_autor ON livros(autor_id);
CREATE INDEX idx_livro_editora ON livros(editora_id);
CREATE INDEX idx_livro_tipo ON livros(tipo_id);
CREATE INDEX idx_livro_idioma ON livros(idioma_id);

-- Busca textual (PostgreSQL usa GIN para texto)
-- CREATE INDEX ft_sinopse ON livros USING GIN (to_tsvector('portuguese', sinopse));