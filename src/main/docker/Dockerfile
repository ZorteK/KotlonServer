FROM openjdk:11-jre

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser --disabled-password --gecos "" user
WORKDIR /home/user

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown user:user entrypoint.sh
USER user

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080

ADD *.jar app.jar

