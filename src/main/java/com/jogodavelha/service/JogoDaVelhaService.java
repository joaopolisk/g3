package com.jogodavelha.service;

import com.jogodavelha.model.GameState;
import com.jogodavelha.model.Jogada;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class JogoDaVelhaService {
    private GameState gameState;
    private Random random = new Random();

    public JogoDaVelhaService() {
        this.gameState = new GameState();
    }

    public GameState iniciarJogo(char caractereUsuario, char caractereComputador) {
        gameState = new GameState();
        gameState.setJogadorUsuario(caractereUsuario);
        gameState.setJogadorComputador(caractereComputador);
        
        // Sorteia quem começa
        boolean usuarioComeca = random.nextBoolean();
        gameState.setJogadorAtual(usuarioComeca ? caractereUsuario : caractereComputador);
        
        // Se computador começa, faz a primeira jogada
        if (!usuarioComeca) {
            fazerJogadaComputador();
        }
        
        return gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameState fazerJogadaUsuario(Jogada jogada) {
        if (!gameState.getStatus().equals("JOGANDO")) {
            return gameState;
        }

        int linha = jogada.getLinha();
        int coluna = jogada.getColuna();

        // Valida jogada
        if (linha < 0 || linha >= 3 || coluna < 0 || coluna >= 3) {
            throw new IllegalArgumentException("Posição inválida");
        }

        if (gameState.getTabuleiro()[linha][coluna] != ' ') {
            throw new IllegalArgumentException("Posição já ocupada");
        }

        // Faz a jogada
        gameState.getTabuleiro()[linha][coluna] = gameState.getJogadorUsuario();

        // Verifica vitória do usuário
        if (teveGanhador(gameState.getJogadorUsuario())) {
            gameState.setStatus("VITORIA_USUARIO");
            gameState.setVitoriasUsuario(gameState.getVitoriasUsuario() + 1);
            return gameState;
        }

        // Verifica empate
        if (teveEmpate()) {
            gameState.setStatus("EMPATE");
            gameState.setEmpates(gameState.getEmpates() + 1);
            return gameState;
        }

        // Vez do computador
        fazerJogadaComputador();

        return gameState;
    }

    private void fazerJogadaComputador() {
        if (!gameState.getStatus().equals("JOGANDO")) {
            return;
        }

        // Procura posição livre
        int[] jogada = obterJogadaComputador();
        if (jogada[0] == -1) {
            return; // Sem jogadas disponíveis
        }

        gameState.getTabuleiro()[jogada[0]][jogada[1]] = gameState.getJogadorComputador();

        // Verifica vitória do computador
        if (teveGanhador(gameState.getJogadorComputador())) {
            gameState.setStatus("VITORIA_COMPUTADOR");
            gameState.setVitoriasComputador(gameState.getVitoriasComputador() + 1);
            return;
        }

        // Verifica empate
        if (teveEmpate()) {
            gameState.setStatus("EMPATE");
            gameState.setEmpates(gameState.getEmpates() + 1);
        }
    }

    private int[] obterJogadaComputador() {
        // Coleta posições livres
        java.util.List<int[]> posicoesLivres = new java.util.ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState.getTabuleiro()[i][j] == ' ') {
                    posicoesLivres.add(new int[]{i, j});
                }
            }
        }

        if (posicoesLivres.isEmpty()) {
            return new int[]{-1, -1};
        }

        // Sorteia uma posição livre
        return posicoesLivres.get(random.nextInt(posicoesLivres.size()));
    }

    private boolean teveGanhador(char jogador) {
        return teveGanhadorLinha(jogador) || 
               teveGanhadorColuna(jogador) || 
               teveGanhadorDiagonalPrincipal(jogador) || 
               teveGanhadorDiagonalSecundaria(jogador);
    }

    private boolean teveGanhadorLinha(char jogador) {
        for (int i = 0; i < 3; i++) {
            if (gameState.getTabuleiro()[i][0] == jogador &&
                gameState.getTabuleiro()[i][1] == jogador &&
                gameState.getTabuleiro()[i][2] == jogador) {
                return true;
            }
        }
        return false;
    }

    private boolean teveGanhadorColuna(char jogador) {
        for (int j = 0; j < 3; j++) {
            if (gameState.getTabuleiro()[0][j] == jogador &&
                gameState.getTabuleiro()[1][j] == jogador &&
                gameState.getTabuleiro()[2][j] == jogador) {
                return true;
            }
        }
        return false;
    }

    private boolean teveGanhadorDiagonalPrincipal(char jogador) {
        return gameState.getTabuleiro()[0][0] == jogador &&
               gameState.getTabuleiro()[1][1] == jogador &&
               gameState.getTabuleiro()[2][2] == jogador;
    }

    private boolean teveGanhadorDiagonalSecundaria(char jogador) {
        return gameState.getTabuleiro()[0][2] == jogador &&
               gameState.getTabuleiro()[1][1] == jogador &&
               gameState.getTabuleiro()[2][0] == jogador;
    }

    private boolean teveEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState.getTabuleiro()[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public GameState reiniciarJogo() {
        char usuarioChar = gameState.getJogadorUsuario();
        char computadorChar = gameState.getJogadorComputador();
        int vitoriasU = gameState.getVitoriasUsuario();
        int vitoriasC = gameState.getVitoriasComputador();
        int emp = gameState.getEmpates();
        
        gameState = new GameState();
        gameState.setJogadorUsuario(usuarioChar);
        gameState.setJogadorComputador(computadorChar);
        gameState.setVitoriasUsuario(vitoriasU);
        gameState.setVitoriasComputador(vitoriasC);
        gameState.setEmpates(emp);
        
        // Sorteia quem começa
        boolean usuarioComeca = random.nextBoolean();
        gameState.setJogadorAtual(usuarioComeca ? usuarioChar : computadorChar);
        
        // Se computador começa, faz a primeira jogada
        if (!usuarioComeca) {
            fazerJogadaComputador();
        }
        
        return gameState;
    }
}
