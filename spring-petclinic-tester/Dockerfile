# syntax=docker/dockerfile:1
FROM amazoncorretto:17 as base

# Install OS packages
WORKDIR /app

FROM amazoncorretto:17 as build
WORKDIR /src
COPY src ./src 
COPY gradle ./gradle
COPY build.gradle gradlew ./
RUN --mount=type=cache,target=/root/.gradle \
      ./gradlew build --console=plain --info --no-daemon --no-watch-fs

FROM base AS final
WORKDIR /app
COPY --from=build /src/build/libs/src.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
