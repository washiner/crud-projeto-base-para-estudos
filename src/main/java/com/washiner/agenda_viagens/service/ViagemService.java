package com.washiner.agenda_viagens.service;

import com.washiner.agenda_viagens.domain.dto.ViagemRequest;
import com.washiner.agenda_viagens.domain.dto.ViagemResponse;
import com.washiner.agenda_viagens.domain.entity.ViagemModel;
import com.washiner.agenda_viagens.infra.exceptions.RecursoNaoEncontradoException;
import com.washiner.agenda_viagens.mapper.ViagemMapper;
import com.washiner.agenda_viagens.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ViagemService {

   private final ViagemRepository viagemRepository;
   private final ViagemMapper viagemMapper;

   //listar viagens

    public List<ViagemResponse> listarService(){
        return viagemRepository.findAll()
                .stream()
                .map(viagemMapper::toResponse).toList();
    }

    //buscar por id
    public ViagemResponse buscarPorID(Long id){
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Viagem não encontrado"));
        return viagemMapper.toResponse(viagem);
    }

    //criar novo

    public ViagemResponse criar(ViagemRequest request){
        ViagemModel viagem = viagemMapper.toModel(request);
        ViagemModel salvo = viagemRepository.save(viagem);
        return viagemMapper.toResponse(salvo);
    }

    //atualizar
    public ViagemResponse atualizar(Long id, ViagemRequest request){
        ViagemModel viagemBD = viagemRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Viagem não encontrada"));
        viagemBD.setDestino(request.destino());
        viagemBD.setPais(request.pais());
        viagemBD.setDataPartida(request.dataPartida());
        viagemBD.setDataRetorno(request.dataRetorno());
        viagemBD.setStatus(request.status());

        ViagemModel atualizado = viagemRepository.save(viagemBD);
        return viagemMapper.toResponse(atualizado);
    }

    public void deletar(Long id) {
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Viagem nao encontrada"));
        viagemRepository.delete(viagem);
    }
}
