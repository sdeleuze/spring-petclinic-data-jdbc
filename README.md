# Spring PetClinic Sample Application built with Spring Data JDBC

This branch is intended to experiment with AppCDS and efficient deployment of Spring Boot applications.

## Pre-requisites

Install Spring snapshots:
```bash
sdk use java 17.0.8.1-librca
./install-snapshots.sh
```

Switch to Java 21 to run the application.
```bash
sdk env install
sdk env
```

## Build and run locally

Test and compare locally various ways of running Spring applications from the slow to the faster:
```bash
./executable-jar.sh
./exploded.sh
./appcds.sh 
./appcds-aot.sh
```

On my MacBook M2 Pro:
- Executable JAR: `Started PetClinicApplication in 1.534 seconds (process running for 1.763)`
- Exploded: `Started PetClinicApplication in 1.118 seconds (process running for 1.245)`
  - 27% decrease from executable JAR
- AppCDS: `Started PetClinicApplication in 0.718 seconds (process running for 0.826)`
  - 53% decrease from executable JAR
  - 35% decrease from exploded
- AppCDS + Spring AOT: `Started PetClinicApplication in 0.639 seconds (process running for 0.747)`
  - 58% decrease from executable JAR
  - 42% decrease from exploded
  - 11% decrease from AppCDS

## Build and run container images

TODO