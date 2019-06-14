#FROM java:8  
FROM adoptopenjdk/openjdk8-openj9:latest
COPY . /
WORKDIR / 
RUN ln -s /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/jli/libjli.so /usr/local/lib/
RUN ln -s /usr/lib/jvm/java-8-openjdk-amd64/jre/ /usr/jre
RUN cd src && javac -cp ../lib/gson-2.6.2.jar:../lib/mysql-connector-java-5.1.47.jar:../lib/junit-jupiter-api-5.4.0.jar $(find . -name '*.java') -nowarn
RUN cd test && javac -cp ../src/:../lib/junit-jupiter-api-5.4.0.jar:../lib/mysql-connector-java-5.1.47.jar $(find . -name '*.java') -nowarn
CMD ["java", "-jar", "-Xnojit", "-Xnoaot", "-XX:CompressedClassSpaceSize=16m", "-XX:ReservedCodeCacheSize=16m", "-XX:-UseCompiler", "-XX:+UseSerialGC", "-XX:-UsePerfData", "lib/junit-platform-console-standalone-1.4.0.jar", "-cp","test/:src/:lib/junit-jupiter-api-5.4.0.jar:lib/mysql-connector-java-5.1.47.jar","--scan-class-path"]
