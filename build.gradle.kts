import javax.swing.Spring

plugins {
    java
    war
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm")
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}



dependencies {

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.+")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    //Permet à JAVA de se connecter à une base SQL
    runtimeOnly("com.h2database:h2")

//JPA Framework Java qui génère du SQL
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    //Spring security
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation ("org.springframework.boot:spring-boot-starter-webflux")
    implementation ("org.springframework.cloud:spring-cloud-starter-loadbalancer")


    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

extra["springCloudVersion"] = "2024.0.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}