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


## Add ValidationService as a service
```
import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
 
  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response validate(@MultipartForm @Valid ValidatorRequestDTO validatorRequestDTO) {
    ValidationRequestDTO validationRequestDTO = convertToValidationRequestDTO(validatorRequestDTO);
    return Response.ok(validatorService.validate(validationRequestDTO)).build();
  }
```
DTO usage
```
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
 
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ValidatorRequestDTO {
 
  @NotNull(message = "Version must not be null")
  @FormParam("eformsVersion")
  private @Pattern(regexp = "^[\\d]\\.[\\d]$", message = "BAD_EFORM_VERSION_FORMAT") String version;
 
  @NotNull(message = "Sdk type must not be null.")
  @FormParam("sdkType")
  private String sdkType;
 
  @NotNull(message = "EForms must not be null")
  @FormParam("eforms")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private byte[] eforms;
}
```
# EForms Validator - Core use as a RESTful API

## Service Description

EForms Validator - Core is a service that provides a RESTful API endpoint for validating e-forms using a validator service. It accepts input in the form of a multipart form data with necessary parameters and returns validation results in JSON format.


## Installation and Setup

To get started with EForms Validator - Core, follow these steps:

1. Clone the repository.
2. Build the project and package it into a deployable format (e.g., WAR file).
```
./gradlew build
./gradlew shadowJar
```
3. Deploy the built package to your preferred application server.

## Running the Service

Ensure that the service is correctly deployed on your application server. The service should be accessible via the specified endpoint URL.
```agsl
java -jar eforms-validator-core-1.0.0-runner.jar
```
## Endpoints

### Validate E-Forms

Endpoint URL: `/v1/validation`

Method: `POST`

Consumes: `multipart/form-data`

Produces: `application/json`

#### Request Parameters

The request should be a multipart form data containing the following parameters:

- `sdkType`: The type of the SDK used for e-forms (String).
- `version`: The version of the SDK used (String).
- `eforms`: The e-forms data to be validated (e.g., JSON representation) (String).

#### Response

Upon successful validation, the service will respond with a JSON object containing the validation results. The response will have an HTTP status code of 200 (OK). The format of the response will be specified by the `ValidatorService` used.

#### Example Usage

```http
POST /v1/validation HTTP/1.1
Host: example.com
Content-Type: multipart/form-data;
Content-Length: <length>

Content-Disposition: form-data; name="eforms"

{"field1": "value1", "field2": "value2", ...}
```

```json
{
  "result": "success",
  "message": "Validation successful.",
  "data": {
    "field1": "value1",
    "field2": "value2"
  }
}
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

After you ran any tests, run a sonar check using:
```shell script
./gradlew sonarqube
```

Check the sonar report launching `http://localhost:9000`.
Lookup credentials to access the sonar dashboard can be found in `gradle.properties`.


## CI/CD
The application is built on Github using [Github-Actions](https://github.com/EFA-FHB/gdk-eforms-validator/actions).
Detailed information on available workflows and actions can be found in the [.github directory](README_GITHUB.md)

## Contributing

We welcome contributions to improve and enhance the functionality of EForms Validator - Core. If you encounter any issues or have suggestions for improvement, please feel free to create a pull request or raise an issue.

## License

EForms Validator - Core is licensed under the MIT License. You are free to use, modify, and distribute the code, subject to the terms of the license.
