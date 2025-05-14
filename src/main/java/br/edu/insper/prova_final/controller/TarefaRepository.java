package br.edu.insper.prova_final.controller;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {
    List<Tarefa> findByEmail(String email);
}
