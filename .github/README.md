# GDK EForms Validator

## Purpose

Providing validation for eForms-XML documents via service.

## Tech stack

- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Quarkus Framework](https://quarkus.io/guides/)
- [Gradle](https://gradle.org/)
- [Kubernetes](https://kubernetes.io/)

## Build
The project is build the Gradle.
A gradle wrapper is part of this project you do not need to install it
locally on your machine.

The application can be built using:
```shell script
./gradlew build
```

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

```shell script
docker-compose up -d sonar
```

After you ran any tests, run a sonar check using:
```shell script
./gradlew :app:sonarqube
```

Check the sonar report launching `http://localhost:9000`.
Lookup credentials to access the sonar dashboard can be found in `gradle.properties`.


## CI/CD
The application is built on Github using [Github-Actions](https://github.com/EFA-FHB/gdk-eforms-validator/actions).
Detailed information on available workflows and actions can be found in the [.github directory](README_GITHUB.md)