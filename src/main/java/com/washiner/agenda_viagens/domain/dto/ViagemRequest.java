package com.washiner.agenda_viagens.domain.dto;

import com.washiner.agenda_viagens.domain.enums.StatusViagem;

import java.time.LocalDate;

public record ViagemRequest(
        String destino,
        String pais,
        LocalDate dataPartida,
        LocalDate dataRetorno,
        StatusViagem status,
        String cpf
) {
}