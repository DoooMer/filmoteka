FROM openjdk:15.0-slim

COPY /target/*.jar /usr/src/app/filmoteka.jar

WORKDIR /usr/src/app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.jpa.hibernate.ddl-auto=update", "filmoteka.jar"]