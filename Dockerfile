FROM openjdk:11
RUN addgroup --system spring-group && adduser --system spring-user --ingroup spring-group
USER spring-user:spring-group
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx512m","-Dserver.port=${PORT}","-jar","/app.jar"]