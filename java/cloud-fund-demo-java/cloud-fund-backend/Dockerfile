#FROM maven:latest
#FROM adoptopenjdk/maven-openjdk8
FROM adoptopenjdk/openjdk8-openj9:latest
COPY . /
WORKDIR /
RUN ln -s /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/jli/libjli.so /usr/local/lib/
RUN ln -s /usr/lib/jvm/java-8-openjdk-amd64/jre/ /usr/jre
RUN ./mvnw clean package
EXPOSE 8080
CMD ["java", "-jar", "-Xnojit", "-Xnoaot", "target/cloud-fund-backend-0.1.0.jar"]