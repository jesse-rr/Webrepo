# Manual de Utilizacaoção - Fila do Pão 
_OBS: O sistema foi desenvolvido em Java, utilizando estruturas de dados customizadas (sem usar APIs prontas do Java) e um arquivo de configuração em CSV para definir as classes de atendimento._

### Funcionalidades Implementadas

### Estruturas de Dados Utilizadas
A ListaSequencial foi a estrutura unica e principal neste projeto devido às seguintes características:

Inserção (adiciona, insere)
    - Adiciona novos clientes à fila de atendimento
Remoção (remove, remove_ultimo)
    - Remove clientes atendidos da fila
Busca (procura, obtem)
    - Localiza clientes ou classes de atendimento
Vantagens para este Projeto:
    - Simplicidade: Adequada para o tamanho esperado da fila
    - Suficiência: Atende aos requisitos de prioridade baseada em:
        - Ordem de chegada
        - Tempo máximo de espera
    - Volume: O volume de dados (pequeno), permite estrutura de dados menos sofisticadas

<details>
    <summary>Diagrama</summary>
    
```mermaid
classDiagram
    class ListaSequencial~T~ {
        -T[] area
        -int len
        -final int defcap
        +ListaSequencial()
        +expande(int len)
        +expande()
        +esta_vazia() boolean
        +capacidade() int
        +adiciona(T elemento)
        +insere(int indice, T elemento)
        +remove(int indice)
        +remove_ultimo()
        +procura(T valor) int
        +obtem(int indice) T
        +substitui(int indice, T valor)
        +comprimento() int
        +limpa()
        +ordena()
        -bubbleSort()
        -selectionSort()
        -mergeSort(T[] array, int left, int right)
        -merge(T[] array, int left, int middle, int right)
    }

    class App {
        -final String CONFIG_FILE
        -GerenciadorAtendimento gerenciadorAtendimento
        +App()
        +classes() ListaSequencial~ClasseAtendimento~
        +adiciona_cliente(String classe) String
        +proxima_senha() String
    }

    class ClasseAtendimento {
        -char codigo
        -String descricao
        -int tempo_maximo_espera
        -int prioridade
        +ClasseAtendimento(char, String, int, int)
    }

    class Cliente {
        -String senha
        -long tempoDeEntrada
        -ClasseAtendimento classe
        +Cliente(int, long, ClasseAtendimento, int)
    }

    class GerenciadorAtendimento {
        -ListaSequencial~ClasseAtendimento~ l_classes
        -ListaSequencial~Cliente~ l_clientes
        -int[] contadores
        +GerenciadorAtendimento()
        +processarArquivo(InputStream config)
        +classes() ListaSequencial~ClasseAtendimento~
        +adiciona_cliente(String classe) String
        +proxima_senha() String
    }

    App --> GerenciadorAtendimento
    GerenciadorAtendimento --> ListaSequencial~ClasseAtendimento~
    GerenciadorAtendimento --> ListaSequencial~Cliente~
    GerenciadorAtendimento --> ClasseAtendimento
    GerenciadorAtendimento --> Cliente
    Cliente --> ClasseAtendimento
```
</details>
