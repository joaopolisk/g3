# Jogo da Velha - Web API

Jogo da velha moderno com interface web e API REST em Java Spring Boot.

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Executando o Projeto

1. **Compile e execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

2. **Ou compile e execute o JAR:**
   ```bash
   mvn clean package
   java -jar target/jogo-da-velha-api-1.0.0.jar
   ```

3. **Acesse o jogo no navegador:**
   ```
   http://localhost:8080
   ```

## 📡 API Endpoints

### Iniciar Jogo
```http
POST /api/jogo/iniciar
Content-Type: application/json

{
  "caractereUsuario": "X",
  "caractereComputador": "O"
}
```

### Fazer Jogada
```http
POST /api/jogo/jogar
Content-Type: application/json

{
  "linha": 0,
  "coluna": 1
}
```

### Reiniciar Partida
```http
POST /api/jogo/reiniciar
```

### Obter Estado do Jogo
```http
GET /api/jogo/estado
```

## 🎮 Funcionalidades

- ✅ Interface web moderna e responsiva
- ✅ API REST completa
- ✅ Escolha de símbolos (X, O, U, C)
- ✅ Placar de vitórias
- ✅ Computador joga automaticamente
- ✅ Sorteia quem começa
- ✅ Animações e feedback visual
- ✅ Modal de resultado

## 🛠️ Tecnologias

- **Backend:** Java 17, Spring Boot 3.2.1
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla)
- **Build:** Maven

## 📂 Estrutura do Projeto

```
g3/
├── src/
│   ├── main/
│   │   ├── java/com/jogodavelha/
│   │   │   ├── JogoDaVelhaApplication.java
│   │   │   ├── controller/
│   │   │   │   └── JogoDaVelhaController.java
│   │   │   ├── service/
│   │   │   │   └── JogoDaVelhaService.java
│   │   │   └── model/
│   │   │       ├── GameState.java
│   │   │       └── Jogada.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── index.html
│   │           ├── styles.css
│   │           └── script.js
├── pom.xml
└── App.java (versão CLI original)
```

## 📝 Notas

- A porta padrão é 8080
- O CORS está habilitado para desenvolvimento
- A versão CLI original ainda está disponível em `App.java`
