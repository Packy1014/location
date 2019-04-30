FROM fabric8/java-alpine-openjdk8-jdk
VOLUME ["/tmp"]
ADD target/location-1.0.1.jar location-1.0.1.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "location-1.0.1.jar"]
EXPOSE 8080