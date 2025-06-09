# Demonstração Prática: Acessando Segredos Armazenados em um Password Vault

## Introdução

Nesta demonstração, mostrarei como uma aplicação pode acessar com segurança um segredo armazenado em um password vault, simulando um cenário real onde uma aplicação precisa acessar uma chave API sem expô-la diretamente aos desenvolvedores ou usuários finais. Utilizaremos o HashiCorp Vault, uma solução popular para gerenciamento de segredos, e Docker para criar um ambiente isolado sem necessidade de privilégios administrativos no sistema principal.

## Pré-requisitos

- Docker instalado na máquina
- Terminal/linha de comando
- Conexão com a internet para baixar as imagens Docker
- "bin/sh ou bash"

## Passo a Passo

### 1. Configurar o ambiente com Docker

Primeiro, vamos criar um contêiner Docker com o HashiCorp Vault:

```bash
# Baixar a imagem oficial do Vault
docker pull hashicorp/vault

# Executar o Vault em modo desenvolvimento (apenas para demonstração)
docker run --cap-add=IPC_LOCK -d --name=dev-vault -p 8200:8200 hashicorp/vault server -dev
```

### 2. Acessar a interface do Vault

O Vault estará disponível em http://localhost:8200. Você pode ver o token de root (para desenvolvimento) na saída do comando anterior ou com:

```bash
docker logs dev-vault | grep "Root Token:"
```

Copie o token que aparece após `Root Token:`.
<img style="text-align: center" src="https://github.com/user-attachments/assets/4a0c74bf-2f65-45eb-afe5-7e45cbb08377">


### 3. Armazenar uma chave API no Vault

Vamos usar a CLI do Vault dentro do contêiner para armazenar nossa chave API:

```bash
# Acessar o shell do contêiner
docker exec -it dev-vault /bin/sh

# Configurar variável de ambiente com o token
export VAULT_ADDR='http://127.0.0.1:8200'
export VAULT_TOKEN='PLACEHOLDER'

# Armazenar uma chave API secreta (ex: a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6")
vault kv put secret/api-keys/producao key="a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6"
```

### 4. Criar uma política de acesso

Ainda dentro do contêiner, vamos criar uma política que permite apenas leitura do segredo:

```bash
# Criar arquivo de política
cat > api-policy.hcl <<EOF
path "secret/data/api-keys/producao" {
  capabilities = ["read"]
}
EOF

# Aplicar a política
vault policy write api-reader api-policy.hcl
```

### 5. Gerar um token com permissões limitadas

```bash
# Criar token com a política api-reader
vault token create -policy="api-reader" -ttl=24h
```

Anote o token gerado - este será usado pela aplicação.

### 6. Simular uma aplicação acessando o segredo

Vamos utilizar o curl para simular um request de outra aplicação.

```bash
# Fora do container, no host Linux:
curl -s --header "X-Vault-Token: PLACEHOLDER" http://localhost:8200/v1/secret/data/api-keys/producao
```

## Síntese dos Resultados

Nesta demonstração conseguimos:

1. Configurar rapidamente um ambiente de Vault usando Docker, sem necessidade de privilégios administrativos
2. Armazenar com segurança uma chave API no Vault
3. Criar políticas granulares de acesso
4. Gerar tokens com permissões específicas (princípio do menor privilégio)
5. Simular como uma aplicação pode acessar segredos sem tê-los hardcoded no código

Os principais benefícios observados:

- Segurança: A chave API nunca é exposta diretamente no código ou para desenvolvedores
- Rastreabilidade: O Vault mantém logs de quem acessou qual segredo e quando
- Gerenciamento centralizado: As chaves podem ser rotacionadas sem alterar o código da aplicação
- Controle de acesso: Políticas granulares limitam o acesso apenas ao necessário

Esta abordagem é superior à prática comum de armazenar segredos em variáveis de ambiente ou arquivos de configuração, pois oferece maior segurança e controle.
