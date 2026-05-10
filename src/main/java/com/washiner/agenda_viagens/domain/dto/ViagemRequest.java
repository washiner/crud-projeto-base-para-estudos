package com.washiner.agenda_viagens.domain.dto;

import com.washiner.agenda_viagens.domain.enums.StatusViagem;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViagemRequest(

        @NotBlank(message = "digite o destino da viagem")
        String destino,

        @NotBlank(message = "Precisa digitar o país a ser visitado")
        String pais,

        @Future(message = "Digite a data de partida")
        @NotNull
        LocalDate dataPartida,

        @NotNull
        @Future(message = "a data precisa ser no futuro afinal e quando voce retorna")
        LocalDate dataRetorno,

        @NotNull(message = "campo nao pode ficar em branco")
        StatusViagem status,

        @NotBlank(message = "nao esta esquecendo de por o cpf?")
        String cpf
) {
}