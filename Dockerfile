FROM amazoncorretto:17-alpine-jdk

COPY C:/Users/user/IdeaProjects/Spring/Enregistrement_de_heures_de_travail/build/libs
/Enregistrement_de_heures_de_travail-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar"]