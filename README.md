# Spring Data Examples

These examples show how to use Spring Data Redis (using either Spring or Spring Boot).

## Build and Deploy

Assumes Redis or Valkey are already running on port 6379.

Deploy one example at a time as they both use the same port, press `CTRL`+`C` to stop.

Spring Demo:
```sh
cd spring-demo
mvn clean jetty:run
```

Spring Boot Demo:
```sh
cd spring-boot-demo
mvn clean spring-boot:run
```

Complex Demo (Console App):
```sh
cd complex-demo
mvn clean compile exec:java

# Or with Gradle
./gradlew run
```

## Usage

Call application endpoints:
```sh
curl -X POST "http://localhost:8080/api/data/set?key=mykey&value=hello_redis"
curl "http://localhost:8080/api/data/get?key=mykey"
curl -X DELETE "http://localhost:8080/api/data/delete?key=mykey"
```

Migration from Spring Data Redis to Valkey:
```sh
./migrate-to-valkey.sh spring-demo

./migrate-to-valkey.sh spring-boot-demo
```
