const API_URL = 'http://localhost:8080/api/jogo';

let gameState = null;
let jogando = false;


document.addEventListener('DOMContentLoaded', () => {
    
    document.getElementById('caractereUsuario').addEventListener('change', validarCaracteres);
    document.getElementById('caractereComputador').addEventListener('change', validarCaracteres);
});

function validarCaracteres() {
    const usuario = document.getElementById('caractereUsuario').value;
    const computador = document.getElementById('caractereComputador').value;
    
    if (usuario === computador) {
        const opcoes = ['X', 'O', 'U', 'C'];
        const novoCaractere = opcoes.find(c => c !== usuario);
        document.getElementById('caractereComputador').value = novoCaractere;
    }
}

async function iniciarJogo() {
    const caractereUsuario = document.getElementById('caractereUsuario').value;
    const caractereComputador = document.getElementById('caractereComputador').value;

    if (caractereUsuario === caractereComputador) {
        alert('Os caracteres devem ser diferentes!');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/iniciar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                caractereUsuario,
                caractereComputador
            })
        });

        gameState = await response.json();
        jogando = true;
        
        mostrarTela('game-screen');
        atualizarInterface();
    } catch (error) {
        console.error('Erro ao iniciar jogo:', error);
        alert('Erro ao conectar com o servidor. Certifique-se de que a API está rodando na porta 8080.');
    }
}

function mostrarTela(telaId) {
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });
    document.getElementById(telaId).classList.add('active');
}

function atualizarInterface() {
    if (!gameState) return;

    
    document.getElementById('vitoriasUsuario').textContent = gameState.vitoriasUsuario;
    document.getElementById('vitoriasComputador').textContent = gameState.vitoriasComputador;
    document.getElementById('empates').textContent = gameState.empates;

   
    const statusMsg = document.getElementById('status-message');
    switch (gameState.status) {
        case 'JOGANDO':
            statusMsg.textContent = 'Sua vez!';
            statusMsg.style.color = '#333';
            break;
        case 'VITORIA_USUARIO':
            statusMsg.textContent = '🎉 Você venceu!';
            statusMsg.style.color = '#4caf50';
            mostrarResultado('Você Venceu! 🎉', 'Parabéns pela vitória!');
            jogando = false;
            break;
        case 'VITORIA_COMPUTADOR':
            statusMsg.textContent = '😔 Computador venceu!';
            statusMsg.style.color = '#f44336';
            mostrarResultado('Computador Venceu! 🤖', 'Que tal uma revanche?');
            jogando = false;
            break;
        case 'EMPATE':
            statusMsg.textContent = '🤝 Empate!';
            statusMsg.style.color = '#ff9800';
            mostrarResultado('Empate! 🤝', 'Partida empatada!');
            jogando = false;
            break;
    }

    
    renderizarTabuleiro();
}

function renderizarTabuleiro() {
    const tabuleiroDiv = document.getElementById('tabuleiro');
    tabuleiroDiv.innerHTML = '';

    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            const celula = document.createElement('div');
            celula.className = 'celula';
            
            const valor = gameState.tabuleiro[i][j];
            if (valor !== ' ') {
                celula.textContent = valor;
                celula.classList.add('ocupada');
                
                if (valor === gameState.jogadorUsuario) {
                    celula.classList.add('usuario');
                } else {
                    celula.classList.add('computador');
                }
            } else if (jogando) {
                celula.addEventListener('click', () => fazerJogada(i, j));
            }

            tabuleiroDiv.appendChild(celula);
        }
    }
}

async function fazerJogada(linha, coluna) {
    if (!jogando || gameState.status !== 'JOGANDO') {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/jogar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ linha, coluna })
        });

        if (!response.ok) {
            const erro = await response.json();
            alert(erro.erro || 'Jogada inválida!');
            return;
        }

        gameState = await response.json();
        atualizarInterface();
    } catch (error) {
        console.error('Erro ao fazer jogada:', error);
        alert('Erro ao fazer jogada!');
    }
}

async function reiniciarPartida() {
    try {
        const response = await fetch(`${API_URL}/reiniciar`, {
            method: 'POST'
        });

        gameState = await response.json();
        jogando = true;
        fecharModal();
        atualizarInterface();
    } catch (error) {
        console.error('Erro ao reiniciar partida:', error);
        alert('Erro ao reiniciar partida!');
    }
}

function voltarConfig() {
    if (confirm('Deseja realmente voltar? O jogo atual será perdido.')) {
        gameState = null;
        jogando = false;
        mostrarTela('config-screen');
    }
}

function mostrarResultado(titulo, mensagem) {
    setTimeout(() => {
        document.getElementById('resultado-titulo').textContent = titulo;
        document.getElementById('resultado-mensagem').textContent = mensagem;
        document.getElementById('resultado-modal').classList.add('active');
    }, 500);
}

function fecharModal() {
    document.getElementById('resultado-modal').classList.remove('active');
}

// Fecha modal ao clicar fora
window.onclick = function(event) {
    const modal = document.getElementById('resultado-modal');
    if (event.target === modal) {
        fecharModal();
    }
}
