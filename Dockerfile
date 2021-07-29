FROM openjdk:16
ADD target/assignment3hibernate-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]