package com.washiner.agenda_viagens.domain.entity;

import com.washiner.agenda_viagens.domain.enums.StatusViagem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "viagem")
public class ViagemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private String pais;

    @Column(name = "data_partida", nullable = false)
    private LocalDate dataPartida;

    @Column(name = "data_retorno", nullable = false)
    private LocalDate dataRetorno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusViagem status;

    @Column(nullable = false)
    private String cpf;

}
