FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-cache-manager.jar
ENTRYPOINT ["java","-jar","ms-cache-manager.jar"]