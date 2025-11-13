CREATE DATABASE IF NOT EXISTS mothers3;
USE mothers3;

CREATE TABLE mae(
	id_mae INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(14),
    dataAniversario DATE NOT NULL
);

CREATE TABLE encontro(
	id_encontro INT PRIMARY KEY AUTO_INCREMENT,
    data_encontro DATE NOT NULL,
    id_mae INT NOT NULL,
    FOREIGN KEY (id_mae) REFERENCES mae(id_mae),
    descricao VARCHAR(200),
    andamento VARCHAR(20)
);

CREATE TABLE servico(
	id_servico INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE rel(
	PRIMARY KEY (id_mae, id_encontro, id_servico),
	id_mae INT NOT NULL,
    id_encontro INT NOT NULL,
    id_servico INT NOT NULL,
    FOREIGN KEY (id_mae) REFERENCES mae(id_mae),
    FOREIGN KEY (id_encontro) REFERENCES encontro(id_encontro),
    FOREIGN KEY (id_servico) REFERENCES servico(id_servico)
);