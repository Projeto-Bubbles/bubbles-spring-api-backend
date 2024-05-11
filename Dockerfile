FROM openjdk

WORKDIR /app

COPY ./target/spring-api-backend-0.0.1-SNAPSHOT.jar /app

CMD ["/bin/bash"]