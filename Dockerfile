FROM openjdk:17

WORKDIR /app

EXPOSE 9090

COPY target/*.jar /app/talents-app-email-service.jar

CMD ["java", "-jar", "talents-app-email-service.jar"]