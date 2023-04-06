FROM tomcat:10.1.7

COPY todo-ui/target/todo-ui*.war /usr/local/tomcat/webapps/todo.war