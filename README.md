Nome:
Enrico Andrade D'Amico 557706
Carolina Estevam Rodgerio 554975
Lucas Thales dos Santos 558886

# API de Gerenciamento de Motos - Mottu

## Visão Geral
API REST desenvolvida em Spring Boot como atividade escolar para gerenciar motos nos estabelecimentos da Mottu. Gerencia entidades como Doca, DocaMoto, Estabelecimento, Funcionário, Moto e Rastreador.

## Funcionalidades
- Gerenciamento de docas, motos, funcionários, estabelecimentos e rastreadores.
- Acompanhamento de status e localização das motos via rastreadores e psicionamento dentro das docas.
- Estrutura hierárquica de estabelecimentos (matriz/filial).
- Documentação da API via Swagger.

## Tecnologias Utilizadas
- **Framework**: Spring Boot
- **Banco de Dados**: H2 (em memória, configurado em `application-devh2.yml`)
- **Documentação da API**: Swagger
- **Versão do Java**: Java 17 (ou compatível)

**Instruções de Configuração**
1. **Clonar o Repositório**:
   ```bash
   git clone <url-do-repositório>
   cd <diretório-do-repositório>
   ```

2. **Configuração**:
   - O arquivo `application.yml` define o perfil ativo (`devh2`).
   - O banco H2 é configurado em `application-devh2.yml`.
   - Instale as dependências do Maven:
     ```bash
     mvn install
     ```

3. **Executar a Aplicação**:
   - Inicie a aplicação:
     ```bash
     mvn spring-boot:run
     ```
   - A API estará disponível em `http://localhost:8080/mottu/`.

4. **Acessar o Swagger**:
   - Acesse a documentação da API em:
     ```
     http://localhost:8080/mottu/swagger-ui.html
     ```

## Estrutura do Projeto
- **Entidades**:
  - `Doca`: Representa uma doca no estabelecimento.
  - `DocaMoto`: Associa motos a docas, com status e situação.
  - `Estabelecimento`: Gerencia estabelecimentos (matriz ou filial).
  - `Funcionario`: Dados dos funcionários vinculados a estabelecimentos.
  - `Moto`: Informações das motos, incluindo placa, modelo e rastreador.
  - `Rastreador`: Dados de rastreamento, como IMEI e coordenadas.

## Observações
- O banco H2 é inicializado automaticamente no perfil `devh2`.
- Para configurações personalizadas, edite `application-devh2.yml`.
```
