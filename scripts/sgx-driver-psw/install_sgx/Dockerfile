FROM ubuntu:18.04
WORKDIR /app
RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get -y install sudo && \
    apt-get -y install wget && \
    apt-get -y install openssh-client && \
    apt-get -y install psmisc
COPY . /app
COPY startup.sh /root/startup.sh
RUN chmod 755 /root/startup.sh
CMD /root/startup.sh