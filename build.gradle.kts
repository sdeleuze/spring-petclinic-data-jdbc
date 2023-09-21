plugins {
	java
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.graalvm.buildtools.native") version "0.9.27"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.github.ben-manes.caffeine:caffeine")
	//runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	testImplementation("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
