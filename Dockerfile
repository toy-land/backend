FROM openjdk:11

ADD ./build/libs/toy-land-0.0.1-SNAPSHOT.jar /deploy/jar/back.jar

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod" ,"/deploy/jar/back.jar"]
