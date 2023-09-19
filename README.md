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
./gradlew nativeCompile
build/native/nativeCompile/petclinic
```

To build a native container image for deployment in the Cloud, customize the configuration i `src/main/resources/application-default.properties` and run:
```
./gradlew bootBuildImage
```

## Benchmarking

It is recommended to benchmark workloads on containers running with maximum memory and CPU set. `java` and `native-image` memory management is hardly comparable, so no custom memory option like `-Xmx` should be set, just use the defaults. 

| Instance            | GraalVM CE   | GraalVM EE  | JVM         |
|---------------------|--------------|-------------|-------------|
| 1 vCPU / 0,5 G RAM  | data points  | data points | data points |
| 2 vCPU / 1 G RAM    | data points  | data points | data points |
| 4 vCPU / 2 G RAM    | data points  | data points | data points |

Data points measured are typically:
 - startup time in ms
 - Throughput in requests/s
 - Latency in ms 

Evolution of the peak performance (typically constant with native) can be also interesting to measure.

PGO is usually too much constraints to setup and results can vary a lot depending on the sample requests, so it is not a first class use case and not something we measure.

HTTP requests on http://localhost:8080/vets.html are typically used:
 - They involve caching by default, can be interesting to measure with or without the `@Chacheable` annotation on `VetRepository.findAll()` to see the impact
 - `VetController.vetToVetDto(Collection<Vet>)` is implemented with Java streams, could be interesting to measure an alternative implementation with a `for` loop instead.

### JVM

When benchmarking the JVM version, to get proper results, it is important to:
 - Unpack the executable JAR [as documented here](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images.efficient-images.unpacking)
 - To not use the GraalVM LabsJDK which has a track of record of providing different results, latest [Liberica JDK](https://bell-sw.com/libericajdk/) 17 is recommended
