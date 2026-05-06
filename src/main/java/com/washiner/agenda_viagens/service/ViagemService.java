package com.washiner.agenda_viagens.service;

import com.washiner.agenda_viagens.domain.dto.ViagemRequest;
import com.washiner.agenda_viagens.domain.dto.ViagemResponse;
import com.washiner.agenda_viagens.domain.entity.ViagemModel;
import com.washiner.agenda_viagens.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ViagemService {

   private final ViagemRepository viagemRepository;

   //listar viagens

    public List<ViagemResponse> listarService(){
        return viagemRepository.findAll()
                .stream()
                .map(viagemModel -> new ViagemResponse(
                        viagemModel.getId(),
                        viagemModel.getDestino(),
                        viagemModel.getPais(),
                        viagemModel.getDataPartida(),
                        viagemModel.getDataRetorno(),
                        viagemModel.getStatus()
                )).toList();
    }

    //buscar por id
    public ViagemResponse buscarPorID(Long id){
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        return new ViagemResponse(
                viagem.getId(),
                viagem.getDestino(),
                viagem.getPais(),
                viagem.getDataPartida(),
                viagem.getDataRetorno(),
                viagem.getStatus()
        );
    }

    //criar novo

    public ViagemResponse criar(ViagemRequest request){
        ViagemModel viagemBd = new ViagemModel();
        viagemBd.setDestino(request.destino());
        viagemBd.setPais(request.pais());
        viagemBd.setDataPartida(request.dataPartida());
        viagemBd.setDataRetorno(request.dataRetorno());
        viagemBd.setStatus(request.status());
        viagemBd.setCpf(request.cpf());

        ViagemModel salvo = viagemRepository.save(viagemBd);


        return new ViagemResponse(
                salvo.getId(),
                salvo.getDestino(),
                salvo.getPais(),
                salvo.getDataPartida(),
                salvo.getDataRetorno(),
                salvo.getStatus()
        );
    }

    //atualizar
    public ViagemResponse atualizar(Long id, ViagemRequest request){
        ViagemModel viagemBD = viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        viagemBD.setDestino(request.destino());
        viagemBD.setPais(request.pais());
        viagemBD.setDataPartida(request.dataPartida());
        viagemBD.setDataRetorno(request.dataRetorno());
        viagemBD.setStatus(request.status());

        ViagemModel salvo = viagemRepository.save(viagemBD);
        return new ViagemResponse(
          salvo.getId(),
          salvo.getDestino(),
          salvo.getPais(),
          salvo.getDataPartida(),
          salvo.getDataRetorno(),
          salvo.getStatus()
        );


    }

    public void deletar(Long id) {
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        viagemRepository.delete(viagem);
    }
}
