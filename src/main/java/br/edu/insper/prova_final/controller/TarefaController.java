package br.edu.insper.prova_final.controller;

import br.edu.insper.prova_final.controller.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaService.listarTarefas();
    }


    @PostMapping
    public Tarefa criarTarefa(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody Tarefa tarefa) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        tarefa.setEmail(email);
        return tarefaService.criarTarefa(tarefa);
    }


    @DeleteMapping("/{id}")
    public void deletarTarefa(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String id) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        tarefaService.deletarTarefa(id);
    }
}
