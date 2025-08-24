```mermaid
flowchart TD
    MORELLI[Morelli<br/><small>Classe Principal</small>]
    ATORJOGADOR[AtorJogador<br/><small>Controlador</small>]
    TELAJOGADOR[TelaJogador<br/><small>Interface Gráfica JFrame</small>]
    
    TABULEIRO[Tabuleiro<br/><small>Lógica do Jogo</small>]
    JOGADAMORELLI[JogadaMorelli<br/><small>Transferência de Dados</small>]
    TIPOJOGADA[TipoJogada<br/><small>Enumeração</small>]
    FAIXA[Faixa<br/><small>Anel do Tabuleiro</small>]
    POSICAO[Posicao<br/><small>Célula do Tabuleiro</small>]
    JOGADOR[Jogador<br/><small>Dados do Jogador</small>]
    COR[Cor<br/><small>Enumeração de Cores</small>]
    AJUDA[Ajuda<br/><small>Sistema de Ajuda</small>]
    IMAGEM[ImagemDeTabuleiro<br/><small>Representação Visual</small>]
    
    RESBUNDLE[ResourceBundle<br/><small>Internacionalização</small>]
    NETGAMES[NetGames<br/><small>Cliente Multiplayer</small>]
    NETGAMESFW[NetGames Server Framework<br/><small>Dependência Externa</small>]
    
    MORELLI --> ATORJOGADOR
    ATORJOGADOR --> TELAJOGADOR
    ATORJOGADOR --> TABULEIRO
    ATORJOGADOR --> NETGAMES
    TELAJOGADOR --> RESBUNDLE
    
    TABULEIRO --> FAIXA
    FAIXA --> POSICAO
    TABULEIRO --> JOGADOR
    TABULEIRO --> AJUDA
    
    JOGADAMORELLI --> TIPOJOGADA
    JOGADAMORELLI --> FAIXA
    
    FAIXA --> COR
    
    NETGAMES -.-> NETGAMESFW
    
    subgraph CamadaDeApresentação
        TELAJOGADOR
        RESBUNDLE
        IMAGEM
    end
    
    subgraph CamadaDeControle
        ATORJOGADOR
        NETGAMES
    end
    
    subgraph CamadaDeModeloEntidades
        TABULEIRO
        JOGADAMORELLI
        TIPOJOGADA
        FAIXA
        POSICAO
        JOGADOR
        COR
        AJUDA
    end
    
    subgraph DependênciasExternas
        NETGAMESFW
    end
    
    class MORELLI,ATORJOGADOR,TELAJOGADOR main
    class TABULEIRO,JOGADAMORELLI,TIPOJOGADA,FAIXA,POSICAO,JOGADOR,COR,AJUDA,IMAGEM entity
    class RESBUNDLE,NETGAMES support
    class NETGAMESFW external
```
