package br.edu.insper.prova_final.controller;

import br.edu.insper.prova_final.controller.Tarefa;
import br.edu.insper.prova_final.controller.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }


    public void deletarTarefa(String id) {
        if (!tarefaRepository.existsById(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada com id: " + id);
        }
        tarefaRepository.deleteById(id);
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }
}
