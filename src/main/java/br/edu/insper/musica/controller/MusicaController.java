package br.edu.insper.musica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @GetMapping("/musica")
    public List<Musica> getMusicasPrivate(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");

        return musicaService.list(email, roles);
    }

    @PostMapping("/musica")
    public Musica saveMusica(@AuthenticationPrincipal Jwt jwt, @RequestBody Musica musica) {

        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        musica.setEmail(email);

        return musicaService.save(musica);
    }

    @DeleteMapping("/musica/{id}")
    public void deleteMusica(@AuthenticationPrincipal Jwt jwt,
                               @PathVariable String id) {

        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        musicaService.delete(id);
    }

}
