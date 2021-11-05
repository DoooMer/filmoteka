FROM openjdk:15.0-slim

COPY /target/filmoteka-1.1.1.jar /usr/src/app/filmoteka.jar
WORKDIR /usr/src/app
CMD ["java", "-jar", "filmoteka.jar"]