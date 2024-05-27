FROM openjdk:17-alpine

ADD target/BFIVersionBack-*.jar /BFIVersionBack.jar

CMD ["java", "-jar", "/BFIVersionBack.jar"]