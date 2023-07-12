# EForms Validator - Core

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=coverage&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=bugs&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=security_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=sqale_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=code_smells&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)

[![CI/CD](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml/badge.svg)](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml)

## Purpose

Providing validation for eForms-XML documents via service.

## Tech stack

- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Quarkus Framework](https://quarkus.io/guides/)
- [Gradle](https://gradle.org/)

## Build
The project is build the Gradle.
A gradle wrapper is part of this project you do not need to install it
locally on your machine.

The application can be built using:
```shell script
./gradlew build
```

## Build Fat Jar
The project can be build using gradle shadow plugin.

The application can be built using:
```shell script
./gradlew shadowJar
```

## Use as dependency
Add necessary repository to build.gradle
```shell script
    repositories {
        mavenCentral()
        mavenLocal()

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${property("github.validator.path")}")
            credentials {
                username = findProperty("gpr.user") ?: findProperty("github.validator.username")
                password = findProperty("efa-fhb.github.token") ?: System.getenv("GH_PACKAGES_TOKEN")
            }
        }
    }
    
    dependencies {
        implementation 'com.nortal.efafhb.eforms.validator:eforms-validator-core:1.0.0'
    }

```

Configure missing gradle.properties
```shell script
github.validator.username=validator-repo
github.validator.path=EFA-FHB/*
```

Generate github token and add it to gradle.properties
https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic



## Development

If you run the application in dev mode, you edit the source code and
any changes made will immediately be reflected in the running instance,
means you don't have to restart it.

```
You can run the application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

In Dev-Mode, Quarkus will apply the Liquibase changelog authored in the `schema`-Module
automatically, giving you a bootstrapped database ready to use.

## Unit-Tests
Please write Unit-Tests using *Junit 5*.
API-Tests are asserted using *RestAssured*

If you annotate your tests with *@QuarkusTest*, Gradle will launch the application in Dev-Mode
once you trigger the tests, either via your IDE or from CLI.
Consult the [Quarkus-Guide](https://quarkus.io/guides/getting-started-testing) for detailed information regarding testing quarkus applications.

You can trigger the unit test suite using:

```shell script
./gradlew :app:test
```

## Code Coverage
Code coverage is measured with [Jacoco](https://github.com/jacoco/jacoco) and sent to [Sonar](https://www.sonarqube.org/).
To keep track of code coverage while developing you can spin up a local sonar instance.

After you ran any tests, run a sonar check using:
```shell script
./gradlew sonarqube
```

Check the sonar report launching `http://localhost:9000`.
Lookup credentials to access the sonar dashboard can be found in `gradle.properties`.


## CI/CD
The application is built on Github using [Github-Actions](https://github.com/EFA-FHB/gdk-eforms-validator/actions).
Detailed information on available workflows and actions can be found in the [.github directory](README_GITHUB.md)
