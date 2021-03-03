FROM openjdk:17-buster

LABEL description "A database server."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

ENV DATOMIC_VERSION 0.9.5703

WORKDIR /usr/bin/database/

COPY docker-entrypoint.sh .

RUN chmod +x docker-entrypoint.sh

RUN wget https://my.datomic.com/downloads/free/${DATOMIC_VERSION} -O datomic.zip \
  && unzip datomic.zip \
  && rm datomic.zip \
  && mv datomic-free-${DATOMIC_VERSION}/* .

RUN cp config/samples/free-transactor-template.properties transactor.properties

RUN sed "s/host=localhost/host=0.0.0.0/" -i transactor.properties
RUN sed "s/# storage-access=local/storage-access=remote/" -i transactor.properties

EXPOSE 4334 4335 4336

ENTRYPOINT ["./docker-entrypoint.sh"]
CMD ["bin/transactor", "transactor.properties"]
