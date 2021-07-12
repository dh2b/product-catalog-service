# Product Catalog Service

This service intends to provide a way for Philips to manage and maintain a product catalog through a REST API-based solution.

### Application Documentation

* [Swagger](http://localhost:8085/api/product-catalog-service/swagger-ui.html) [Only when application is running]

### Application Requirements

To run this application, make sure the following dependencies are configured in your local environment:

* [Java Development Toolkit 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [String Boot - 2.3.4.RELEASE](https://spring.io/projects/spring-boot)
* [Gradle - 6.6.1](https://gradle.org/)
* [MongoDB](https://www.mongodb.com/download-center#community)
* [Kafka](http://kafka.apache.org/downloads.html)
* [ZooKeeper](https://zookeeper.apache.org/releases.html)

In the case of running the application from IDE, do not forget to enable annotation processing for Lombok.

#### Important: 

* It's mandatory to have MongoDB running at default port `27017` in your local environment.
* It's also desirable to have Kafka running in your local environment. If you don't, please set property  `enabled` to `false` on the [application-local.yml](https://github.com/dh2b/product-catalog-service/blob/main/src/main/resources/application-local.yml)

```yaml
spring:
  kafka:
    bootstrapAddress: localhost:9092
    topic: productEventTopic
    enabled: false
```

### Running the application (Windows OS)

Clone GitHub repository: :

```
git clone https://github.com/dh2b/product-catalog-service.git
```

Run the following command from root application folder:
```
gradlew build bootRun
```

The application should be running at port [8085](http://localhost:8085/).

### Starting ZooKeeper

Run the following command from installed zookeeper root folder: 
```
zkserver
```

### Starting Kafka

Run the following command from installed kafka root folder::
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

Run the following command from [installed-kafka-version]\bin\windows folder if you want to see kafka published events:
```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic votingEventTopic
```

### Bibliography 

For more references and explanations, please access the following documentation:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-kafka)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#production-ready)