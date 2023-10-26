#!/usr/bin/env bash
set -e

echo "Optimized run with the dynamic shared archive"
cd build/libs && java -cp "spring-petclinic-1.0.0-SNAPSHOT-plain.jar:BOOT-INF/lib/caffeine-3.1.8.jar:BOOT-INF/lib/h2-2.2.224.jar:BOOT-INF/lib/spring-boot-actuator-autoconfigure-3.2.0-RC1.jar:BOOT-INF/lib/micrometer-jakarta9-1.12.0-RC1.jar:BOOT-INF/lib/spring-webmvc-6.1.0-RC1.jar:BOOT-INF/lib/spring-web-6.1.0-RC1.jar:BOOT-INF/lib/micrometer-core-1.12.0-RC1.jar:BOOT-INF/lib/spring-context-support-6.1.0-RC1.jar:BOOT-INF/lib/spring-data-jdbc-3.2.0-RC1.jar:BOOT-INF/lib/spring-boot-autoconfigure-3.2.0-RC1.jar:BOOT-INF/lib/spring-boot-actuator-3.2.0-RC1.jar:BOOT-INF/lib/spring-boot-3.2.0-RC1.jar:BOOT-INF/lib/spring-data-relational-3.2.0-RC1.jar:BOOT-INF/lib/spring-context-6.1.0-RC1.jar:BOOT-INF/lib/micrometer-observation-1.12.0-RC1.jar:BOOT-INF/lib/thymeleaf-spring6-3.1.2.RELEASE.jar:BOOT-INF/lib/tomcat-embed-el-10.1.15.jar:BOOT-INF/lib/hibernate-validator-8.0.1.Final.jar:BOOT-INF/lib/checker-qual-3.37.0.jar:BOOT-INF/lib/error_prone_annotations-2.21.1.jar:BOOT-INF/lib/jakarta.annotation-api-2.1.1.jar:BOOT-INF/lib/spring-jdbc-6.1.0-RC1.jar:BOOT-INF/lib/spring-data-commons-3.2.0-RC1.jar:BOOT-INF/lib/spring-tx-6.1.0-RC1.jar:BOOT-INF/lib/spring-aop-6.1.0-RC1.jar:BOOT-INF/lib/spring-beans-6.1.0-RC1.jar:BOOT-INF/lib/spring-expression-6.1.0-RC1.jar:BOOT-INF/lib/spring-core-6.1.0-RC1.jar:BOOT-INF/lib/snakeyaml-2.2.jar:BOOT-INF/lib/jackson-datatype-jdk8-2.15.3.jar:BOOT-INF/lib/jackson-module-parameter-names-2.15.3.jar:BOOT-INF/lib/jackson-annotations-2.15.3.jar:BOOT-INF/lib/jackson-core-2.15.3.jar:BOOT-INF/lib/jackson-datatype-jsr310-2.15.3.jar:BOOT-INF/lib/jackson-databind-2.15.3.jar:BOOT-INF/lib/micrometer-commons-1.12.0-RC1.jar:BOOT-INF/lib/HikariCP-5.0.1.jar:BOOT-INF/lib/thymeleaf-3.1.2.RELEASE.jar:BOOT-INF/lib/logback-classic-1.4.11.jar:BOOT-INF/lib/log4j-to-slf4j-2.21.0.jar:BOOT-INF/lib/jul-to-slf4j-2.0.9.jar:BOOT-INF/lib/slf4j-api-2.0.9.jar:BOOT-INF/lib/jakarta.validation-api-3.0.2.jar:BOOT-INF/lib/jboss-logging-3.5.3.Final.jar:BOOT-INF/lib/classmate-1.6.0.jar:BOOT-INF/lib/tomcat-embed-websocket-10.1.15.jar:BOOT-INF/lib/tomcat-embed-core-10.1.15.jar:BOOT-INF/lib/spring-jcl-6.1.0-RC1.jar:BOOT-INF/lib/HdrHistogram-2.1.12.jar:BOOT-INF/lib/LatencyUtils-2.0.3.jar:BOOT-INF/lib/jsqlparser-4.6.jar:BOOT-INF/lib/attoparser-2.0.7.RELEASE.jar:BOOT-INF/lib/unbescape-1.1.6.RELEASE.jar:BOOT-INF/lib/logback-core-1.4.11.jar:BOOT-INF/lib/log4j-api-2.21.0.jar" -XX:SharedArchiveFile=spring-petclinic.jsa org.springframework.samples.petclinic.PetClinicApplication