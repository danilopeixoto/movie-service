FROM clojure:openjdk-17-lein-2.9.5-alpine

LABEL description "A movie web API."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/api/

COPY . .

RUN lein datomic start \
  && lein datomic initialize \
  && lein ring uberjar

CMD ["java", "-jar", "target/api.jar"]
