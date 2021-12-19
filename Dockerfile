FROM openjdk:17-jdk-alpine AS BUILD

WORKDIR /api

COPY . .

RUN ./mvnw dependency:resolve
RUN ./mvnw install -DskipTests


FROM openjdk:17-jdk-alpine AS RUN

COPY --from=BUILD api/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]