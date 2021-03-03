FROM clojure:lein-2.8.0-alpine

LABEL description "A movie web API."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/api/

COPY . .

RUN lein ring uberjar

EXPOSE 3000

CMD ["java", "-jar", "target/api.jar"]
