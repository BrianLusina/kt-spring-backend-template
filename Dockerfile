FROM openjdk:11.0.7-jre

ARG JAR_FILE
ADD ${JAR_FILE} service.jar
EXPOSE 7070
ENTRYPOINT ["java", \
            "-Dspring.profiles.active=live", \
            "-jar", \
            "service.jar"]