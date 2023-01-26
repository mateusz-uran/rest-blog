FROM openjdk:18-ea-11-jdk-alpine as builder
WORKDIR extracted
ADD target/*.jar backend-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar backend-0.0.1-SNAPSHOT.jar extract

FROM openjdk:18-ea-11-jdk-alpine
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]