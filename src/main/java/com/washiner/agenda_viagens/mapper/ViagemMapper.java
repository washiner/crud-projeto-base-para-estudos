package com.washiner.agenda_viagens.mapper;

import com.washiner.agenda_viagens.domain.dto.ViagemRequest;
import com.washiner.agenda_viagens.domain.dto.ViagemResponse;
import com.washiner.agenda_viagens.domain.entity.ViagemModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViagemMapper {
    ViagemResponse toResponse(ViagemModel viagemModel);
    ViagemModel toModel(ViagemRequest request);
}

