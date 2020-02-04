FROM ubuntu:latest
WORKDIR /app
RUN apt-get update -y && \
    apt-get upgrade -y
COPY . /app
COPY startup.sh /root/startup.sh
RUN chmod 755 /root/startup.sh
CMD /root/startup.sh