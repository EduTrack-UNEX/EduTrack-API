# 🚀 Guia de Instalação e Configuração Local

Siga os passos abaixo para configurar e executar o **EduTrack-API** em seu ambiente de desenvolvimento local.

## 🛠️ 1. Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em sua máquina. As versões listadas são recomendações para garantir a compatibilidade.

* **Java Development Kit (JDK)**: `21` ou superior.
* **Apache Maven**: `3.5.x` ou superior.
* **Git**: Para clonar o repositório.

Você pode verificar as versões com os seguintes comandos no seu terminal:
```bash
java --version
```

```bash
mvn --version
```

```bash
git --version
```

### 🖥️ 2. Instalação

Com os pré-requisitos atendidos, siga este passo a passo:

1. **Clone o repositório:**
   Abra seu terminal, navegue até o diretório onde deseja salvar o projeto e execute o comando abaixo para baixar o
   código-fonte.

```bash
git clone https://github.com/EduTrack-UNEX/EduTrack-API.git
```

2. **Acesse o diretório do projeto:**

```bash
cd EduTrack-API
```

3. **Instale as dependências:**
   Este comando irá baixar todas as bibliotecas e pacotes necessários para o projeto funcionar:

```bash
mvn install
```

### 🔑 3. Configuração das Variáveis de Ambiente

As configurações da aplicação são gerenciadas no arquivo `application.yml`
localizado em src/main/resources/.

### ▶️ 4. Executando a Aplicação

Após a instalação e configuração, você está pronto para iniciar o servidor de desenvolvimento.

1. **Inicie o servidor:**
    Com o plugin do Spring Boot para executar a aplicação diretamente do Maven:

```bash
    mvn spring-boot:run
```

2. **Acesse a API:**
    Seu servidor estará acessível em http://localhost:8080. Para verificar se a aplicação está de pé,
    acesse a URL de saúde do Actuator no seu navegador: http://localhost:8080/actuator/health.
    Uma resposta com ```{"status":"UP"}``` indica que o servidor está funcionando corretamente.

Pronto! O ambiente de desenvolvimento do **EduTrack-API** está configurado e rodando na sua máquina.

## 📜 Scripts do Projeto

### Comandos úteis

* **Inicia a aplicação Spring Boot:**
 ```bash
    mvn spring-boot:run
 ```

* **Compila e empacota a aplicação em um arquivo .jar na pasta target/:**
 ```bash
    mvn package
 ```

* **Limpa os artefatos de compilação da pasta target/:**
 ```bash
    mvn clean
 ```

* **Executa todos os testes unitários e de integração do projeto:**
 ```bash
    mvn test
 ```

