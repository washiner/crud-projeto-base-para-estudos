package com.washiner.agenda_viagens.controller;

import com.washiner.agenda_viagens.domain.dto.ViagemRequest;
import com.washiner.agenda_viagens.domain.dto.ViagemResponse;
import com.washiner.agenda_viagens.domain.entity.ViagemModel;
import com.washiner.agenda_viagens.service.ViagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagem")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService viagemService;

    //listar todos

    @GetMapping
    public ResponseEntity<List<ViagemResponse>> listar(){
        return ResponseEntity.ok(viagemService.listarService());
    }

    //listar por id
    @GetMapping("/{id}")
    public ResponseEntity<ViagemResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(viagemService.buscarPorID(id));
    }

    //criar novo //agora com dto
    @PostMapping
    public ResponseEntity<ViagemResponse> criar(@Valid @RequestBody ViagemRequest viagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.criar(viagem));
    }

    // atualizar
    @PutMapping("/{id}")
    public ResponseEntity<ViagemResponse> atualizar(@Valid @PathVariable Long id, @RequestBody ViagemRequest viagem){
        return ResponseEntity.ok(viagemService.atualizar(id, viagem));
    }

    //deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        viagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
