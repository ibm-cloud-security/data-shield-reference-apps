FROM python:3-stretch
RUN apt-get update -y
RUN apt-get -y --purge remove mysql-common libmariadbclient-dev
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt
ADD enclave-manager-ca.crt /usr/local/share/ca-certificates/enclave-manager-ca.crt
RUN update-ca-certificates
ENTRYPOINT ["python"]
CMD ["client.py"]