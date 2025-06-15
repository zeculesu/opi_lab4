FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM quay.io/wildfly/wildfly:28.0.0.Final-jdk17
COPY --from=build /app/target/app.war /opt/jboss/wildfly/standalone/deployments/app.war

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]