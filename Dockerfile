FROM amazoncorretto:21-alpine3.18

VOLUME /tmp

ENV JAVA_APP_DIR=/apps
ENV TZ=America/Sao_Paulo
ENV SPRING_PROFILES_ACTIVE="docker"
ENV JAVA_MAX_MEM_RATIO="0"
ENV JAVA_OPTIONS="-Duser.country=BR -Duser.language=pt -Djdk.tls.client.protocols=TLSv1.2"
WORKDIR $JAVA_APP_DIR

COPY build/libs/freeman-1.0.0.jar freeman.jar



ENTRYPOINT ["java","-jar","freeman.jar"]

EXPOSE 8080