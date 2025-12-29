import java.util.Random;
import java.util.Scanner;
 

public class App {
    // Estes caracteres são aceitos como caracteres para representarem
    // os jogadores. Utizado para evitar caracteres que não combinem com
    // o desenho do tabuleiro.
    final static String CARACTERES_IDENTIFICADORES_ACEITOS = "XOUC"; //U -> usuário, C -> Computador

    // Tamanho do tabuleiro 3x3. Para o primeiro nivel de dificuldade
    // considere que este valor não será alterado. 
    // Depois que você conseguir implementar o raciocionio para o tabuleiro 3x3
    // tente ajustar o código para funcionar para qualquer tamanho de tabuleiro
    final static int TAMANHO_TABULEIRO = 3;

    static char[][] tabuleiro = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        
        inicializarTabuleiro();

        // Definimos aqui qual é o caractere que cada jogador irá utilizar no jogo.
        //TODO 01: chame as funções obterCaractereUsuario() e obterCaractereComputador
        //para definir quais caracteres da lista de caracteres aceitos que o jogador
        //quer configurar para ele e para o computador.
        char caractereUsuario = obterCaractereUsuario();
        char caractereComputador = obterCaractereComputador(caractereUsuario);

        // Esta variavel é utilizada para definir se o usuário começa a jogar ou não.
        // Valor true, usuario começa jogando, valor false computador começa.
        //TODO 02: obtenha o valor booleano sorteado
        boolean vezUsuarioJogar = true;

        boolean jogoContinua;

        do {
            // controla se o jogo terminou
            jogoContinua = true;
            exibirTabuleiro();

            if (vezUsuarioJogar){
                processarVezUsuario(caractereUsuario);
               
                //TODO 03: Execute a chamada processar vez do usuario

                // Verifica se o usuario venceu
                //TODO 04: Este if deve executar apenas se teve ganhador 
                if ( teveGanhador(caractereUsuario) /* Verificar se está correto. TODO: esreva aqui a chamada para teveGanhador verificar se o usuário ganhou*/ ) {
                    
                    exibirTabuleiro();
                    exibirVitoriaUsuario();
                    jogoContinua = false;
                }

                // define que na proxima execucao do laco o jogador nao joga, ou seja, será a vez do computador
                vezUsuarioJogar = false;
            } else {
                processarVezComputador(caractereComputador);
                    
                //TODO 05: Execute a chamada processar vez do computador

                // Verifica se o computador venceu
                //TODO 06: Este if deve executar apenas se teve ganhador
                if ( teveGanhador(caractereComputador)/*esreva aqui a chamada para teve ganhador*/ ) {

                    System.out.println("O computador ganhou!!!");

                    //TODO 07: Exiba que o computador ganhou
                    jogoContinua = false;
                }

                //TODO 08: defina qual o vaor a variavel abaixo deve possuir para que a proxima execucao do laco seja a vez do usuário
                vezUsuarioJogar = true;
            }
        
            //TODO 09: Este if deve executar apenas se o jogo continua E 
            //ocorreu tempate. Utilize o metodo teveEmpate()
            if ( teveEmpate() ) {
                exibirTabuleiro();
                exibirEmpate();
                jogoContinua = false;
            }
        } while (jogoContinua);

        teclado.close();
    }


 
    static void inicializarTabuleiro() {
    // TODO 10: Inicializa o tabuleiro com espaços em branco
    // Percorre todas as linhas do tabuleiro
         for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++) {
    // Percorre todas as colunas do tabuleiro
            for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++) {
   // Inicializa cada posição com espaço em branco.
            tabuleiro[linha][coluna] = ' ';
        }
    }
}


    /*
     * Descrição: Utilizado para obter no início do jogo qual o caractere que o
     * usuário quer utilizar para representar ele próprio. Este método recebe o
     * teclado para permitir que o usuário digite o caractere desejado. Faça a
     * leitura do caractere desejado pelo usuário, através do teclado, realize
     * as validações para não aceitar caracteres que não estejam definidos pela
     * constante CARACTERES_IDENTIFICADORES_ACEITOS, e retorne o caractere lido
     * através do return.
     * Nível de complexidade: 4 de 10
     */
    static char obterCaractereUsuario() {
        while (true) {
            System.out.println("Escolha seu caractere entre: " + CARACTERES_IDENTIFICADORES_ACEITOS + " (digite um só caractere)");
            String linha = teclado.nextLine();
            if (linha == null) {
                continue;
            }
            linha = linha.trim().toUpperCase();
            if (linha.length() == 0) {
                System.out.println("Entrada vazia. Tente novamente.");
                continue;
            }
            char ch = linha.charAt(0);
            if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(ch) != -1) {
                return ch;
            } else {
                System.out.println("Caractere inválido. Use apenas: " + CARACTERES_IDENTIFICADORES_ACEITOS);
            }
        }

    }

    /*
     * Descrição: Utilizado para obter no início do jogo qual o caractere que o
     * usuário quer utilizar para representar o computador. Este método recebe o
     * teclado e recebe o caractere que foi configurado para o usuário, pois o
     * usuário e o computador não podem jogar com o mesmo caractere. Por exemplo,
     * se o usuário configurou para ele o caractere X ele não pode escolher o X
     * como o caractere também para o computador. Neste método apenas os seguintes
     * caracteres definidos pela constante CARACTERES_IDENTIFICADORES_ACEITOS devem
     * ser aceitos. Lembre-se que o caractere armazenado em caractereUsuario não
     * pode ser aceito. Após realizar a leitura do caractere pelo teclado e
     * validá-lo, faça o return deste caractere.
     * Nível de complexidade: 4 de 10
     */
    static char obterCaractereComputador(char caractereUsuario) {
        while (true) {
            System.out.println("Escolha o caractere para o computador entre: " + CARACTERES_IDENTIFICADORES_ACEITOS + " (não pode ser '" + caractereUsuario + "')");
            String linha = teclado.nextLine();
            if (linha == null) {
                continue;
            }
            linha = linha.trim().toUpperCase();
            if (linha.length() == 0) {
                System.out.println("Entrada vazia. Tente novamente.");
                continue;
            }
            char ch = linha.charAt(0);
            if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(ch) == -1) {
                System.out.println("Caractere inválido. Use apenas: " + CARACTERES_IDENTIFICADORES_ACEITOS);
                continue;
            }
            if (ch == Character.toUpperCase(caractereUsuario)) {
                System.out.println("Esse caractere já está sendo usado pelo usuário. Escolha outro.");
                continue;
            }
            return ch;
        }
    }

 
    static boolean jogadaValida(String posicoesLivres, int linha, int coluna) {

    // TODO 13: Valida se a jogada está nas posições livres
    // Primeiro valida se os índices estão dentro dos limites do tabuleiro
        if (linha < 0 || linha >= TAMANHO_TABULEIRO || coluna < 0 || coluna >= TAMANHO_TABULEIRO) {
            return false;
    }

    // Converte a jogada para o formato "xy"
    // Ex: linha 1, coluna 2 vira "12"
    String posicao = "" + linha + coluna;

    // Verifica se essa posição existe na lista de posições livres
    // Se existir, a jogada é válida
        return posicoesLivres.contains(posicao);
}


    static int[] obterJogadaUsuario(String posicoesLivres, Scanner teclado) {
    // TODO 14: Ler linha/coluna, validar e converter índice
    // Loop infinito até o usuário digitar uma jogada válida
        while (true) {

        // Formato esperado da entrada
        System.out.print("Digite linha e coluna (ex: 1 1): ");
        String entrada = teclado.nextLine();

        // Divide a entrada usando espaço como separador
        String[] partes = entrada.trim().split(" ");

        // Valida se o usuário digitou exatamente dois valores
        if (partes.length != 2) {
            System.out.println("Entrada inválida. Use dois números.");
            continue;
        }

        try {
            // Converte os valores digitados para inteiros e ajusta para índice (0-based)
            int linha = Integer.parseInt(partes[0]) - 1;
            int coluna = Integer.parseInt(partes[1]) - 1;

            // Verifica se a jogada é válida (posição livre e dentro do tabuleiro)
            if (jogadaValida(posicoesLivres, linha, coluna)) {

                // Retorna a jogada no formato de vetor [linha, coluna]
                return new int[]{linha, coluna};

            } else {
                System.out.println("Jogada inválida ou posição ocupada.");
            }

        } catch (NumberFormatException e) {
            // Captura erro caso o usuário digite algo que não seja número
            System.out.println("Digite apenas números.");
        }
    }
}


    /*
     * Descrição: Utilizado para obter do computador a linha e a coluna sorteada.
     * Para isto o método utiliza as posições livres que ele recebeu como parametro.
     * Como as posições livres estão no formato de string, uma sugestão é conveter a
     * lista de pares linhacoluna que estão separados por ; em um vetor de String.
     * Pense em utilizar o método split. A conversão para um vetor de string será
     * útil para o próximo passo que é sortear uma posição livre.
     * Para sortear uma das posições no vetor de posições livres, utilize o método
     * random.nextInt() para sortear um número que esteja no intervalo de 0 até a
     * quantidade de posições no vetor de posições livres. Pesquise pelo método
     * random.nextInt() na internet para entender como ele funciona.
     * Após o random sortear um número, utilize este número como o valor da posição
     * do índice para acessar a jogada dentro do vetor de jogadas livres.
     * Ao realizar este procedimento você terá uma jogada no formato xy onde x é
     * a linha livre e y a coluna livre. Como o método obterJogadaComputador
     * precisa devolver um vetor de inteiro é necessário converter esta string para
     * um vetor de inteiro. Utilize para isto o método
     * converterJogadaStringParaVetorInt(). Após a conversão, devolva o vetor de
     * inteiro através do comando return. Para o nível de complexidade inicial,
     * com esta implementação o computador não terá "inteligência" para se defender
     * e nem para tentar ganhar.
     * Nível de complexidade: 6 de 10
     */
    static int[] obterJogadaComputador(String posicoesLivres, Scanner teclado) {
        if (posicoesLivres == null || posicoesLivres.isEmpty()) {
            return new int[]{-1, -1};
        }
        String[] jogadas = posicoesLivres.split(";");
        Random random = new Random();
        while (true) {
            int idx = random.nextInt(jogadas.length);
            String candidato = jogadas[idx];
            if (candidato != null) {
                candidato = candidato.trim();
                if (candidato.length() >= 2) {
                    return converterJogadaStringParaVetorInt(candidato);
                }
            }
        }
    }

    /*
     * Descrição: Utilizado para converter uma jogada no formato xy (linha/coluna)
     * de string para um vetor de int. Para isto, este método recebe a jogada no
     * formato string e deve colocar o valor de x dentro do índice 0 do vetor de
     * inteiro e deve colocar o valor de y dentro do índice 1 do vetor de inteiro.
     * Após a construção do vetor de inteiro retorne este vetor com o comando
     * return.
     * Nível de complexidade: 4 de 10
     */
    static int[] converterJogadaStringParaVetorInt(String jogada) {
        if (jogada == null || jogada.length() < 2) {
            return new int[]{-1, -1};
        }
        jogada = jogada.trim();
        char a = jogada.charAt(0);
        char b = jogada.charAt(1);
        if (!Character.isDigit(a) || !Character.isDigit(b)) {
            return new int[]{-1, -1};
        }
        int linha = Character.getNumericValue(a);
        int coluna = Character.getNumericValue(b);
        return new int[]{linha, coluna};
    }

    /*
     * Descrição: Utilizado para realizar as ações necessárias para processar a vez
     * do usuário jogar. Este método deve exibir uma mensagem que é a vez do usuário
     * jogar. Este método é encarregado de obter a jogada do usuário através do
     * método obterJogadaUsuario, depois realizar a atualização do tabuleiro através
     * do método atualizaTabuleiro. Lembre-se que para chamar o método obterJogadaUsuario
     * é necessário saber quais posições estão livres
     * Nível de complexidade: 5 de 10
     */
    static void processarVezUsuario(char caractereUsuario) {
        //TODO 17: Implementar método conforme explicação
    }

    /*
     * Descrição: Utilizado para realizar as ações necessárias para processar a vez
     * do computador jogar. Este método é encarregado de obter a jogada do
     * computador através do método obterJogadaComputador, depois realizar a
     * atualização do tabuleiro através do método atualizaTabuleiro. 
     * Lembre-se que para chamar o método obterJogadaUsuario
     * é necessário saber quais posições estão livres 
     * Nível de complexidade: 5 de 10 se o computador for jogar aleatoriamente
     * Nível de complexidade: 8 de 10 se o computador for jogar sempre para se defender
     * Nível de complexidade: 10 de 10 se o computador for jogar para ganhar
     */
    static void processarVezComputador(char caractereComputador) {
        //TODO 18: Implementar método conforme explicação
    }

    /*
     * Descrição: Utilizado para identificar a lista de posições livres no
     * tabuleiro. Esta lista é uma string no formato xy. Onde x é a linha e y a
     * coluna. Se existir mais de uma posição livre, teremos uma lista de valores xy
     * separados por ; exemplo: 00;01;20; Neste exemplo as posições linha 0 e
     * coluna 0; linha 0 e coluna 1; linha 2 e coluna 0 estão livres.
     * Lembre-se que os índices nas matrizes iniciam em 0. Para o primeiro nível
     * de complexidade considere um tabuleiro apenas de tamanho 3x3, 3 linhas e 3
     * colunas. Depois de montar a string retorne a mesma através do comando return
     * Nível de complexidade: 5 de 10
     */
    static String retornarPosicoesLivres() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] == ' ') {
                    sb.append(i).append(j).append(';');
                }
            }
        }
        return sb.toString();
    }


    /*
     * Descrição: Utilizado para verificar se o jogador identificado por
     * caractereJogador ganhou o jogo. No jogo da velha um usuário ganha
     * quando ele completa uma linha ou uma coluna ou uma diagonal. Assim
     * este método verifica todas as possibilidades. No entanto, este método
     * utiliza outros métodos para auxiliar nesta verificação. Para identificar
     * se o usuário em questão ganhou na linha, é invocado o método
     * teveGanhadorLinha(), para identificar na coluna é invocado o método
     * teveGanhadorColuna(), para identificar na diagonal principal é invocado
     * o método teveGanhadorDiagonalPrincipal() e para identificar na diagonal
     * secundária é utilizado o método teveGanhadorDiagonalSecundaria(). Se
     * o pelo menos um destes métodos retornar verdadeiro, o método teveGanhador
     * retorna true, caso contrário retorna false
     * Nível de complexidade: 4 de 10 se o tabuleiro for fixo 3x3
     * Nível de complexidade: 8 de 10 se o tabuleiro dinâmico 
     */
    static boolean teveGanhador(char caractereJogador) {
        //TODO 20: Implementar método conforme explicação
        // Se qualquer uma das condições retornar true, houve vencedor
        return teveGanhadorLinha(caractereJogador) || teveGanhadorColuna(caractereJogador)
        || teveGanhadorDiagonalPrincipal(caractereJogador) || teveGanhadorDiagonalSecundaria(caractereJogador);
}


    /*
     * Descrição: Todos os métodos abaixo, teveGanhador... funcionam da mesma forma.
     * Recebem como parametro o tabuleiro e o caractereJogador. Cada um dos métodos
     * verificam no tabuleiro se o caractere do jogador está presente em todas as
     * posições, ou seja, o método teveGanhadorLinha verifica em todas as posicoes
     * de uma determinada linha se elas estão preenchidas com o caractere informado
     * no caractereJogador. Se estiver presente retorna true, caso contrário retorna
     * false.
     * Nível de complexidade: 4 de 10 se o tabuleiro for fixo 3x3
     * Nível de complexidade: 8 de 10 se o tabuleiro dinâmico 
     */
    static boolean teveGanhadorLinha(char caractereJogador) {
        //TODO 21: Implementar método conforme explicação
    }

    static boolean teveGanhadorColuna(char caractereJogador) {
        //TODO 22: Implementar método conforme explicação
    }

    static boolean teveGanhadorDiagonalPrincipal( char caractereJogador) {
        //TODO 23: Implementar método conforme explicação
    }

    static boolean teveGanhadorDiagonalSecundaria(char caractereJogador) {
        //TODO 24: Implementar método conforme explicação
    }

    /*
     * Descrição: Utilizado para limpar a console, para que seja exibido apenas o
     * conteúdo atual do jogo. Dica: Pesquisa na internet por "Como limpar console
     * no java ProcessBuilder"
     * Nível de complexidade: 3 de 10
     */
    static void limparTela() {
        //TODO 25: Implementar método conforme explicação,  Lucas
    
            try {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } catch (Exception e) {
                System.out.println("Erro ao limpar a tela: " + e.getMessage());
            }
        }


    /*
     * Descrição: Utilizado para imprimir o tabuleiro o conteúdo do tabuleiro na
     * tela. Recebe o tabuleiro como parametro e imprime o conteúdo de cada posição
     * do tabuleiro na tela. Imprimi o conteúdo no formato de uma grade. Para o
     * primeiro nível de complexidade considere um tabuleiro apenas de tamanho 3x3,
     * 3 linhas e 3 colunas.
     * Nível de complexidade: 4 de 10
     */
    static void exibirTabuleiro() {
        //TODO 26: Implementar método conforme explicação
        // execute no início deste método a chamada ao método limparTela
        // para garantir que seja exibido o tabuleiro sem nenhum conteúdo antes dele.
    }

    /*
     * Descrição: Utilizado para atualizar o tabuleiro com o caractere que
     * identifica o jogador. Este método recebe o tabuleiro, um vetor jogada com
     * duas posicoes. jogada[0] representa a linha escolhida pelo jogador. jogada[1]
     * representa a coluna escolhida pelo jogador. Os valores armazenados no vetor
     * já deve estar no formato de índice, ou seja, se jogada[0] contiver o valor
     * 1 e jogada[1] contiver o valor 2, significa que o índice/linha 1 e
     * índice/coluna 2 da matriz devem ser atualizados com o caractere informado na
     * variável caractereJogador. Depois de atualizar o tabuleiro, o mesmo deve ser
     * retornado através do comando return
     * Nível de complexidade: 3 de 10
     */
    static void atualizaTabuleiro(int[] jogada, char caractereJogador) {
    //TODO 27: Implementar método conforme explicação
          tabuleiro[jogada[0]][jogada[1]] = caractereJogador;
    // jogada[0] representa a linha escolhida
    // jogada[1] representa a coluna escolhida
    // Atualiza diretamente a posição do tabuleiro

    }

    /*
     * Descrição: Utilizado para exibir a frase: O computador venceu!, e uma ART
     * ASCII do computador feliz. Este método é utilizado quando é identificado que
     * o computador venceu a partida. Lembre-se que para imprimir uma contrabara \ é
     * necessário duas contra barras \\
     * Nível Complexidade: 2 de 10
     */
    static void exibirVitoriaComputador() {
        //TODO 28: Implementar método conforme explicação, Lucas
        System.out.println("O computador venceu!");
        System.out.println();
        System.out.println(" \\O/ ");
        System.out.println("  |  ");
        System.out.println(" / \\ ");

    }

    /*
     * Descrição: Utilizado para exibir a frase: O usuário venceu!, e uma ARTE ASCII
     * do usuário feliz. Este método é utilizado quando é identificado que o usuário
     * venceu a partida. Lembre-se que para imprimir uma contrabara \ é necessário
     * duas contra barras \\
     * Nível Complexidade: 2 de 10
     */
    static void exibirVitoriaUsuario() {
        //TODO 29: Implementar método conforme explicação, Lucas
        System.out.println("O usuário venceu!");
        System.out.println();
        System.out.println(" \\O/ ");               
        System.out.println("  |  ");
        System.out.println(" / \\ ");

    }

    /*
     * Descrição: Utilizado para exibir a frase: Ocorreu empate!, e uma ARTE ASCII
     * do placar 0 X 0. Este método é utilizado quando é identificado que ocorreu
     * empate. Lembre-se que para imprimir uma contrabara \ é necessário duas contra
     * barras \\
     * Nível Complexidade: 2 de 10
     */
    static void exibirEmpate() {
        //TODO 30: Implementar método conforme explicação, Lucas
        System.out.println("Ocorreu empate!");
        System.out.println(" -------");
        System.out.println("  0 X 0  ");
    }

    /*
     * Descrição: Utilizado para analisar se ocorreu empate no jogo. Para o primeiro
     * nível de deficuldade, basta verificar se todas as posições do tabuleiro não
     * estão preenchidas com o caractere ' '. Não se preocupe se teve ganhador, não
     * é responsabilidade deste método esta análise. Sugestão: pense em utilizar a
     * função retornarPosicoesLivres. Retorne true se teve empate ou false
     * Nível de complexidade: 3 de 10
     */
    static boolean teveEmpate() {
        //TODO 31: Implementar método conforme explicação, Lucas
        String posicoesLivres = retornarPosicoesLivres();
        return posicoesLivres.isEmpty();

    }

    /*
     * Descrição: Utilizado para realizar o sorteio de um valor booleano. Este
     * método deve sortear um valor entre true ou false. Este valor será
     * utilizado para identificar quem começa a jogar. Dica: pesquise sobre
     * o método random.nextBoolean() na internet. Após ralizar o sorteio o 
     * método deve retornar o valor sorteado.
     * Nível de complexidade: 3 de 10
     */
    static boolean sortearValorBooleano() {
        //TODO 32: Implementar método conforme explicação, Lucas
        Random random = new Random();
        return random.nextBoolean();
    }


}

