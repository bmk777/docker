FROM python:3.8
COPY . /src/python
WORKDIR /src/python

ENV GOOGLE_APPLICATION_CREDENTIALS=keyfile.json

RUN pip install --upgrade google-api-python-client
RUN pip install google-cloud
RUN pip install google-cloud-vision
RUN pip install google.cloud.bigquery
RUN pip install google.cloud.storage
RUN pip install --upgrade google-cloud-dataproc


CMD ["python", "run.py"]