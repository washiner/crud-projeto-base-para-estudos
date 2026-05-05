package com.washiner.agenda_viagens.service;

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

    public List<ViagemModel> listarService(){
        return viagemRepository.findAll();
    }

    //buscar por id
    public ViagemModel buscarPorID(Long id){
        return viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
    }

    //criar novo

    public ViagemModel criar(ViagemModel viagem){
        return viagemRepository.save(viagem);
    }

    //atualizar
    public ViagemModel atualizar(Long id, ViagemModel viagem){
        ViagemModel viagemBD = viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        viagemBD.setDestino(viagem.getDestino());
        viagemBD.setPais(viagem.getPais());
        viagemBD.setDataPartida(viagem.getDataPartida());
        viagemBD.setDataRetorno(viagem.getDataRetorno());
        viagemBD.setStatus(viagem.getStatus());
        return viagemRepository.save(viagemBD);

    }

    public void deletar(Long id) {
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        viagemRepository.delete(viagem);
    }
}
