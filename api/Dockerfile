FROM clojure:lein-2.8.0

LABEL description "A movie web API."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/api/

COPY . .

RUN lein uberjar

EXPOSE 3000

CMD ["java", "-jar", "target/uberjar/api-1.0.0-standalone.jar"]
