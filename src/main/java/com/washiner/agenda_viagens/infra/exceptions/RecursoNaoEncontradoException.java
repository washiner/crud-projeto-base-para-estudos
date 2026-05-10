package com.washiner.agenda_viagens.infra.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException{
    public RecursoNaoEncontradoException(String menssagem){
        super(menssagem);
    }
}
