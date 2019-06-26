FROM anapsix/alpine-java
MAINTAINER Ivana Marin <ivanamarin67@gmail.com>
ADD target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
EXPOSE 8761
