FROM openjdk:8
ADD target/onlineTest.jar onlineTest.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "onlineTest.jar"]