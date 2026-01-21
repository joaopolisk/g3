package com.jogodavelha.controller;

import com.jogodavelha.model.GameState;
import com.jogodavelha.model.Jogada;
import com.jogodavelha.service.JogoDaVelhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/jogo")
@CrossOrigin(origins = "*") 
public class JogoDaVelhaController {

    @Autowired
    private JogoDaVelhaService jogoService;

    @PostMapping("/iniciar")
    public ResponseEntity<GameState> iniciarJogo(@RequestBody Map<String, String> config) {
        char caractereUsuario = config.get("caractereUsuario").charAt(0);
        char caractereComputador = config.get("caractereComputador").charAt(0);
        
        GameState gameState = jogoService.iniciarJogo(caractereUsuario, caractereComputador);
        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/estado")
    public ResponseEntity<GameState> getEstado() {
        return ResponseEntity.ok(jogoService.getGameState());
    }

    @PostMapping("/jogar")
    public ResponseEntity<?> fazerJogada(@RequestBody Jogada jogada) {
        try {
            GameState gameState = jogoService.fazerJogadaUsuario(jogada);
            return ResponseEntity.ok(gameState);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/reiniciar")
    public ResponseEntity<GameState> reiniciarJogo() {
        GameState gameState = jogoService.reiniciarJogo();
        return ResponseEntity.ok(gameState);
    }
}
