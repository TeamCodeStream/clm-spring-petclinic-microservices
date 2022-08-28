FROM amazoncorretto:11 as builder
WORKDIR application
ARG ARTIFACT_NAME
COPY ${ARTIFACT_NAME}.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# Download dockerize and cache that layer
FROM amazoncorretto:11 as dockerizerizer
WORKDIR application
ARG DOCKERIZE_VERSION
RUN yum -y install wget tar gzip && \
    yum clean all && \
    rm -rf /var/cache/yum
RUN wget -O dockerize.tar.gz https://github.com/jwilder/dockerize/releases/download/${DOCKERIZE_VERSION}/dockerize-alpine-linux-amd64-${DOCKERIZE_VERSION}.tar.gz
RUN tar xzf dockerize.tar.gz
RUN chmod +x dockerize

FROM amazoncorretto:11

WORKDIR application

# Dockerize
COPY --from=dockerizerizer application/dockerize ./

ARG EXPOSED_PORT
EXPOSE ${EXPOSED_PORT}

ENV SPRING_PROFILES_ACTIVE docker

COPY ["newrelic/", "./newrelic"]

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-javaagent:newrelic/newrelic.jar", "org.springframework.boot.loader.JarLauncher"]
