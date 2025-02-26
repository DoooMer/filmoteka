FROM eclipse-temurin:17-jre-alpine

COPY /target/*.jar /usr/src/app/filmoteka.jar

WORKDIR /usr/src/app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.jpa.hibernate.ddl-auto=verify", "filmoteka.jar"]