FROM eclipse-temurin:17-jdk-jammy

ENV APP_PORT 8080

ENV QUEUE_NAME_ERROR transactions-error
ENV QUEUE_CLOUDWATCH cloudwatch-logs
ENV EXCHANGE_NAME transactions-exchange
ENV ROUTING_KEY_ERROR transactions.routing.error
ENV ROUTING_KEY_LOGS transactions.routing.logs

ENV RABBIT_USER guest
ENV RABBIT_PASS guest
ENV RABBIT_HOST myrabbitmq
ENV RABBIT_PORT 5672
ENV RABBIT_PORT_GUI 15672
ENV RABBIT_URI amqp://$RABBIT_USER:$RABBIT_PASS@$RABBIT_HOST:$RABBIT_PORT
ENV RABBIT_URI_CLOUD amqps://osywzdyt:RMqVWFEMarfGsqJXrlYMFHk_pihFpYEz@chimpanzee.rmq.cloudamqp.com/osywzdyt

ENV MONGO_HOST mymongodb
ENV MONGO_PORT 27017
ENV MONGO_DB dbbank
ENV MONGO_USER user
ENV MONGO_PASS pass
ENV MONGO_AUTHUSER admin

WORKDIR /app

COPY ./target/*.jar app.jar

EXPOSE $APP_PORT

CMD ["java", "-jar", "/app/app.jar"]
