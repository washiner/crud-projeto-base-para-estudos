CREATE TABLE viagem
(
    id           BIGSERIAL PRIMARY KEY,
    destino      VARCHAR(100) NOT NULL,
    pais         VARCHAR(100) NOT NULL,
    data_partida DATE         NOT NULL,
    data_retorno DATE         NOT NULL,
    status       VARCHAR(50)  NOT NULL,
    cpf          VARCHAR(20) NOT NULL
);