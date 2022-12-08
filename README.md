# Spring PetClinic Sample Application built with Spring Data JDBC
![Build Maven](https://github.com/spring-petclinic/spring-petclinic-data-jdbc/workflows/Build%20Maven/badge.svg)

This is a branch of the official [Spring PetClinic](https://github.com/spring-projects/spring-petclinic) application with domain & persistence layer built with [Spring Data JDBC](https://projects.spring.io/spring-data-jdbc/) instead of [Spring Data JPA](https://projects.spring.io/spring-data-jpa/).

Check original project [readme](https://github.com/spring-projects/spring-petclinic/blob/master/readme.md) for introduction the project, how to run, and how to contribute.

## Build & run

Before running the application locally, run:
```
docker-compose up
```

To build and run locally as a native executable with h2 database:
```
./mvnw -Pnative clean native:compile
target/image-service
```

To build a native container image for deployment in the Cloud, customize the configuration i `src/main/resources/application-default.properties` and run:
```
./mvn -Pnative clean spring-boot:build-image
```

## Understanding the Spring Petclinic application with a few diagrams

[See the presentation here](http://fr.slideshare.net/AntoineRey/spring-framework-petclinic-sample-application)

## Interesting Spring Petclinic forks

The Spring Petclinic master branch in the main [spring-projects](https://github.com/spring-projects/spring-petclinic)
GitHub org is the "canonical" implementation, currently based on Spring Boot and Thymeleaf.

This [spring-petclinic-data-jdbc](https://github.com/spring-petclinic/spring-petclinic-data-jdbc) project is one of the [several forks](https://spring-petclinic.github.io/docs/forks.html) 
hosted in a special GitHub org: [spring-petclinic](https://github.com/spring-petclinic).
If you have a special interest in a different technology stack
that could be used to implement the Pet Clinic then please join the community there.
