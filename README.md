# Spring Data Examples

## Build and Deploy

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

## Usage

Call application endpoints:
```sh
curl -X POST "http://localhost:8080/api/data/set?key=mykey&value=hello_redis" && echo
curl "http://localhost:8080/api/data/get?key=mykey" && echo
curl -X DELETE "http://localhost:8080/api/data/delete?key=mykey" && echo
```

Migration from Spring Data Redis to Valkey:
```sh
./migrate-to-valkey.sh spring-demo

./migrate-to-valkey.sh spring-boot-demo
```
