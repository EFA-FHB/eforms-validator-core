# eForms Validator - Core

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=coverage&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=bugs&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=security_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=sqale_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=code_smells&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![CI/CD](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml/badge.svg)](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml)

[![en](https://img.shields.io/badge/lang-en-blue.svg)](./README.md)
[![de](https://img.shields.io/badge/lang-de-green.svg)](README.de.md)


## Purpose

Providing combined offline validation of eforms-EU and eForms-DE schematron (.sch) rules. Additionally schema (.xsd) validation is included and some eForms-EU rule errors are left out via blacklist.

General process of validation:

 1. eForms-EU schematron of matching version are validated 
 2. Triggered errors from blacklisted rules are left out
 3. eForms-DE schematron of matchinng version are validated
 4. Combined validation result (valid / not-valid) including eforms-EU and eforms-DE errors and warnings is returned

The current Blacklist can be found here: [excluded_rules.txt](src/main/resources/schematron/de/excluded_rules.txt)

## Tech stack

- [Java 11](https://openjdk.java.net/projects/jdk/11/)
- [Quarkus Framework](https://quarkus.io/guides/)
- [Gradle](https://gradle.org/)

## Supported Versions

- eForms-DE
  - 1.0.1
  - 1.1.0

- eForms-SDK
  - 0.0.1
  - 1.0.0
  - 1.5.1

## Hardware

Hardware requirements are heavily dependend on how many schematron versions are loaded concurrently. The below mentioned numbers are only valid for only eforms-de 1.0.1.

RAM:

- usual usage: 1.5GB
- recommended: 2GB

## Building
The project is built by gradle and a gradle wrapper is part of this project, you do not need to install it locally on your machine.
### Build Thin Jar
This includes the application itself and all direct dependencies of the application. To run it locally, Java still needs to be installed in your environment. The build command is the following:
```shell script
./gradlew build
```

### Build Fat Jar
The fat jar includes the application itself and  **all** needet dependencies. Java does not need to be installed in your local environment to run it. The build command is the following:
```shell script
./gradlew shadowJar
```

# Use as dependency
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


# Add ValidationService as a service
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
# Use RESTful API

Additionally to service or dependency, this eForms Validator also provides a RESTful API endpointe. It accepts input in the form of a multipart form data with necessary parameters and returns validation results in JSON format.

## Running the Service

Ensure that the service is correctly deployed on your application server. The service should be accessible via the specified endpoint URL.
```agsl
java -jar eforms-validator-core-********.jar
```
## Endpoints

### Validate eForms

Endpoint URL: `/v1/eforms-validation`

Method: `POST`

Consumes: `multipart/form-data`

Produces: `application/json`

#### Request Parameters

The request should be a multipart form data containing the following parameters:

- `sdkType`: The type of the SDK used for eForms (String). 
  - possible values: `eforms-de`
- `version`: The version of the eForms Standard used (String).
  - possible values: `1.0`, in the future: `1.1`, `1.2`, ...
- `eforms`: The eForms data to be validated (XML file) (String($binary)).

#### Response

Upon successful validation, the service will respond with a JSON object containing the validation results. The response will have an HTTP status code of 200 (OK). The format of the response will be specified by the `ValidatorService` used.

#### Example

```json
{
  "valid": false,
  "validatedEformsVersion": "eforms-de-1.0.1",
  "warnings": [],
  "errors": [
    {
      "type": "SCHEMATRON",
      "description": null,
      "rule": "[SR-DE-1 ]The value eforms-de-1.1 of cbc:CustomizationID must be equal to the current version (eforms-de-1.0) of the eForms-DE Standard.",
      "ruleContent": "text() = concat('eforms-de-', '1.0')",
      "path": "/can:ContractAwardNotice/cbc:CustomizationID"
    }
  ]
}
```

# Development

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
Detailed information on available workflows and actions can be found in the [.github directory](.github/README_GITHUB.md)

## Contributing

We welcome contributions to improve and enhance the functionality of eForms Validator - Core. If you encounter any issues or have suggestions for improvement, please feel free to create a pull request or raise an issue.

## License

eForms Validator - Core is licensed under the Apache License, Version 2.0. You are free to use, modify, and distribute the code, subject to the terms of the license.
