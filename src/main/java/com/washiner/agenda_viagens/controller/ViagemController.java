package com.washiner.agenda_viagens.controller;

import com.washiner.agenda_viagens.domain.entity.ViagemModel;
import com.washiner.agenda_viagens.service.ViagemService;
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
    public ResponseEntity<List<ViagemModel>> listar(){
        return ResponseEntity.ok(viagemService.listarService());
    }

    //listar por id
    @GetMapping("/{id}")
    public ResponseEntity<ViagemModel> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(viagemService.buscarPorID(id));
    }

    //criar novo
    @PostMapping
    public ResponseEntity<ViagemModel> criar(@RequestBody ViagemModel viagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.criar(viagem));
    }

    // atualizar
    @PutMapping("/{id}")
    public ResponseEntity<ViagemModel> atualizar(@PathVariable Long id, @RequestBody ViagemModel viagem){
        return ResponseEntity.ok(viagemService.atualizar(id, viagem));
    }

    //deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        viagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
