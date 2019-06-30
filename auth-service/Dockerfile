FROM anapsix/alpine-java
MAINTAINER Ivana Marin <ivanamarin67@gmail.com>
ADD target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "/auth-service.jar"]
EXPOSE 8086
