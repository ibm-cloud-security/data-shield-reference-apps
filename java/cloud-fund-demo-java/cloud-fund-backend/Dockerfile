FROM adoptopenjdk/openjdk8-openj9:latest
COPY . /
WORKDIR /
RUN ./mvnw clean package
EXPOSE 8080
CMD ["java", "-jar", "-Xnojit", "-Xnoaot", "target/cloud-fund-backend-0.1.0.jar"]
