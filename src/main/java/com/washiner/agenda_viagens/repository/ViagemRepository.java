package com.washiner.agenda_viagens.repository;

import com.washiner.agenda_viagens.domain.entity.ViagemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViagemRepository extends JpaRepository<ViagemModel, Long> {
}
