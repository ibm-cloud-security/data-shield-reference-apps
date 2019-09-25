FROM python:3-stretch
RUN apt-get update -y
RUN apt-get -y --purge remove mysql-common libmariadbclient-dev
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt
ADD em-ca.crt /usr/local/share/ca-certificates/em-ca.crt
RUN update-ca-certificates
ENTRYPOINT ["python"]
CMD ["client.py"]