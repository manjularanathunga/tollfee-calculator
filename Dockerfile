FROM openjdk:11
EXPOSE 8085
ADD target/tollfee-v1.jar tollfee-v1.jar
ENTRYPOINT ["java", "-jar", "/tollfee-v1.jar"]

