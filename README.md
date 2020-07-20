Este projeto tem como objetivo a criação de um CRUD.

Desenvolvido com:
 * JAVA 11
 * Spring boot
 * Maven 
 * Mysql
 * Docker
 
 O volume do banco de dados está setado na pasta raiz do projeto com nome "docker".
 
 Para testar a API, adicionei dentro da pasta "Collection" as chamadas para o serviço, basta importar no POSTMAN.
 
 Para incializar o projeto será necessario:
  - Docker
  - Java 11
  
 Baixar o projeto e na pasta raiz executar os comandos:
 
 mvn clean install
 
 docker-compose up --build --force-recreate
 
