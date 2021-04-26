## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:21.0.0-java11 AS build
COPY ./web /tmp/my-project
USER root
RUN chown -R quarkus /tmp/my-project
USER quarkus
# we skip tests here because tests use Testcontainers. Tests are executed in a previous step (outside docker). See github actions witin this project.
RUN mvn -f /tmp/my-project/pom.xml -Pnative clean package -DskipTests

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.3
WORKDIR /usr/src/app/target/
COPY --from=build /tmp/my-project/target/*-runner /usr/src/app/target/application
RUN chmod 775 /usr/src/app/target
EXPOSE 8080
CMD ["./application", "-XX:+PrintGC", "-XX:+PrintGCTimeStamps", "-XX:+VerboseGC", "+XX:+PrintHeapShape", "-Xmx128m", "-Dquarkus.http.host=0.0.0.0"]
