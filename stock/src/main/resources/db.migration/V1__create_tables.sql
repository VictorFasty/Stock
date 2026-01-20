-- Tabela de Clientes (OAuth2/Config)
CREATE TABLE clients (
    id UUID PRIMARY KEY,
    client_id VARCHAR(255) NOT NULL,
    client_secret VARCHAR(255),
    redirect_uri TEXT,
    scope VARCHAR(500)
);

-- Tabela de Empresas
CREATE TABLE tb_enterprises (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabela de Produtos (Unificada com base na sua classe ProductModel)
CREATE TABLE tb_products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    quantity INTEGER,
    price DOUBLE PRECISION,
    quantidade_minima INTEGER NOT NULL,


    tb_enterprise BIGINT NOT NULL,

    data_publicacao TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP,

    CONSTRAINT fk_product_enterprise
        FOREIGN KEY (tb_enterprise)
        REFERENCES tb_enterprises (id)
);

-- Tabela de Usuários
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);