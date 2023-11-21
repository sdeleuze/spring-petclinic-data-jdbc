# Spring PetClinic Sample Application built with Spring Data JDBC

This branch is intended to experiment with AppCDS and efficient deployment of Spring Boot applications.

## Pre-requisites

Use a Java 21 distribution with a prebuilt CDS archive to run the application. Here you can use:
```bash
sdk env install
sdk env
```

## Build and run locally

Test and compare locally various ways of running Spring applications from the slow to the faster:
```bash
./jarexe.sh
./exploded.sh
./appcds.sh 
./appcds-aot.sh
```

Notice you can pass `-b` parameter to only build the application without running it, for example using `./exploded.sh -b`.

## Data points

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

On Azure Container Apps with 1 CPU 2G RAM:
- Executable JAR: `Started PetClinicApplication in 8.015 seconds (process running for 9.159)`
- Exploded: `Started PetClinicApplication in 6.305 seconds (process running for 6.874)`
  - 21% decrease from executable JAR
- AppCDS: `Started PetClinicApplication in 4.415 seconds (process running for 4.767)`
  - 44% decrease from executable JAR
  - 29% decrease from exploded
- AppCDS + Spring AOT: `Started PetClinicApplication in 3.989 seconds (process running for 4.315)`
  - 50% decrease from executable JAR
  - 36% decrease from exploded
  - 9% decrease from AppCDS

On Azure Container Apps with 2 CPU 4G RAM:
- Executable JAR: `Started PetClinicApplication in 4.3 seconds (process running for 4.876)`
- Exploded: `Started PetClinicApplication in 3.253 seconds (process running for 3.643)`
  - 24% decrease from executable JAR
- AppCDS: `Started PetClinicApplication in 2.268 seconds (process running for 2.483)`
  - 47% decrease from executable JAR
  - 30% decrease from exploded
- AppCDS + Spring AOT: `Started PetClinicApplication in 2.0 seconds (process running for 2.219)`
  - 53% decrease from executable JAR
  - 38% decrease from exploded
  - 11% decrease from AppCDS

## Build and run container images

Create the container image with one of those scripts (can be pretty long):
```bash
./create-jarexe-container-image.sh
./create-exploded-container-image.sh
./create-appcds-container-image.sh
./create-appcds-aot-container-image.sh
```

Then check locally they are running as expected with one of those scripts:
```bash
./run-jaxexe-container.sh
./run-exploded-container.sh
./run-appcds-container.sh
./run-appcds-aot-container.sh
```

You can then tag and deploy the container images to your platform.