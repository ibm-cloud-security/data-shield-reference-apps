FROM python:3-stretch
RUN apt-get update -y
RUN apt-get -y --purge remove mysql-common libmariadbclient-dev
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt
EXPOSE 5002
ENTRYPOINT ["python"]
CMD ["server.py"]