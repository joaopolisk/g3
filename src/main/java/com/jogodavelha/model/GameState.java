package com.jogodavelha.model;

public class GameState {
    private char[][] tabuleiro;
    private char jogadorAtual;
    private char jogadorUsuario;
    private char jogadorComputador;
    private String status; 
    private int vitoriasUsuario;
    private int vitoriasComputador;
    private int empates;

    public GameState() {
        this.tabuleiro = new char[3][3];
        inicializarTabuleiro();
        this.status = "JOGANDO";
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    
    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(char[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public char getJogadorAtual() {
        return jogadorAtual;
    }

    public void setJogadorAtual(char jogadorAtual) {
        this.jogadorAtual = jogadorAtual;
    }

    public char getJogadorUsuario() {
        return jogadorUsuario;
    }

    public void setJogadorUsuario(char jogadorUsuario) {
        this.jogadorUsuario = jogadorUsuario;
    }

    public char getJogadorComputador() {
        return jogadorComputador;
    }

    public void setJogadorComputador(char jogadorComputador) {
        this.jogadorComputador = jogadorComputador;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVitoriasUsuario() {
        return vitoriasUsuario;
    }

    public void setVitoriasUsuario(int vitoriasUsuario) {
        this.vitoriasUsuario = vitoriasUsuario;
    }

    public int getVitoriasComputador() {
        return vitoriasComputador;
    }

    public void setVitoriasComputador(int vitoriasComputador) {
        this.vitoriasComputador = vitoriasComputador;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }
}
