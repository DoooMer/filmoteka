FROM openjdk:15.0-slim

COPY /target/filmoteka-1.0.0.jar /usr/src/app/filmoteka.jar
WORKDIR /usr/src/app
CMD ["java", "-jar", "filmoteka.jar"]