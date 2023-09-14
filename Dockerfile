FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java17-debian11
COPY --from=build /usr/src/app/target/adnd-0.0.1-SNAPSHOT.jar /usr/app/adnd-0.0.1-SNAPSHOT.jar
EXPOSE 9080
ENTRYPOINT ["java","-jar","/usr/app/adnd-0.0.1-SNAPSHOT.jar"]