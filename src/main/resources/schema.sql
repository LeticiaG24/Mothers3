CREATE DATABASE IF NOT EXISTS mothers3;
USE mothers3;

CREATE TABLE mae(
	id_mae INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(15),
    dataAniversario DATE NOT NULL
);

CREATE TABLE encontro(
	id_encontro INT PRIMARY KEY AUTO_INCREMENT,
    data_encontro DATE NOT NULL,
    andamento VARCHAR(20)
);

CREATE TABLE servico(
	id_servico INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) UNIQUE,
    descricao VARCHAR(200)
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

INSERT INTO servico (nome, descricao)
VALUES ('Música', 'Responsável por preparar e conduzir os momentos musicais do encontro, criando um ambiente de oração, acolhimento e espiritualidade por meio de cantos apropriados.'),
 ('Recepção das mães', 'Equipe encarregada de receber as mães na chegada, oferecendo um sorriso, acolhimento e orientações iniciais, garantindo que todas se sintam bem-vindas.'), 
 ('Acolhida', 'Momento dedicado a integrar as mães, apresentar a proposta do encontro e criar um clima de união, escuta e fraternidade entre todas as participantes.'), 
 ('Terço', 'Condução da oração do Santo Terço, guiando as mães em um momento de fé, reflexão e intercessão.'), 
 ('Formação', 'Espaço destinado ao ensino e à partilha de conteúdos formativos espirituais, bíblicos ou temáticos, fortalecendo a caminhada de fé das mães.'), 
 ('Momento oracional', 'Tempo reservado para oração espontânea ou guiada, buscando aprofundar a experiência espiritual, entregar intenções e fortalecer a conexão com Deus.'), 
 ('Proclamação da vitória', 'Momento em que as mães são convidadas a testemunhar graças alcançadas e proclamar bênçãos recebidas, fortalecendo a fé e inspirando o grupo.'), 
 ('Sorteio das flores', 'Atividade recreativa e simbólica em que flores são sorteadas entre as participantes, proporcionando um gesto de carinho, alegria e confraternização.'), 
 ('Encerramento', 'Finalização do encontro com avisos, agradecimentos e uma breve oração, concluindo o momento de forma organizada e acolhedora.'), 
 ('Arrumação capela', 'Serviço dedicado à organização e limpeza da capela após o encontro, deixando o espaço em ordem e com respeito ao ambiente sagrado.'), 
 ('Queima dos pedidos', 'Momento simbólico em que os pedidos escritos pelas mães são apresentados a Deus e queimados, representando entrega, fé e confiança na providência divina.'),
 ('Compra das flores', 'Prepara com delicadeza os detalhes do encontro');