# General info
Simple (1 page) web portal for todo-list: create, edit, delete tasks.

# Technologies
* Java version: 17
* Stack: Apache Maven, Hibernate, Spring MVC, Thymeleaf, Bootstrap.
* Database:  MySQL, H2 (for tests)
* Web Server: Tomcat 10

# Docker
1. To run docker container execute in terminal:

- 1.1. Build project with maven:
>mvn clean:package -P prod

* 1.2. Run docker container:
>docker compose up 

2. After the successful start of the application in docker open in browser:
http://localhost:8080/todo/
