# EForms Validator - Core: Verwendung als Abhängigkeit oder Service

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=coverage&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=bugs&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=security_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=sqale_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=code_smells&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)

[![CI/CD](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml/badge.svg)](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml)

## Zweck

EForms Validator - Core stellt eine Validierung für eForms-XML-Dokumente über einen Service bereit.

## Technologie-Stack

- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Quarkus Framework](https://quarkus.io/guides/)
- [Gradle](https://gradle.org/)

## Build
Das Projekt wird mit Gradle gebaut.
Ein Gradle-Wrapper ist Teil dieses Projekts, daher müssen Sie ihn nicht lokal auf Ihrem Rechner installieren.

Die Anwendung kann mit folgendem Befehl gebaut werden:
```shell script
./gradlew build
```

## Erstellen eines Fat Jars
Das Projekt kann mithilfe des Gradle Shadow-Plugins erstellt werden.

Die Anwendung kann mit folgendem Befehl gebaut werden:
```shell script
./gradlew shadowJar
```

## Verwendung als Abhängigkeit
Fügen Sie das erforderliche Repository zur `build.gradle`-Datei hinzu:
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

Konfigurieren Sie die fehlenden `gradle.properties`:
```shell script
github.validator.username=validator-repo
github.validator.path=EFA-FHB/*
```

Generieren Sie einen Github-Token und fügen Sie ihn zu `gradle.properties` hinzu:
https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic

## ValidationService als Service hinzufügen
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
Verwendung des DTOs:
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
 
  @NotNull(message = "Version darf nicht null sein")
  @FormParam("eformsVersion")
  private @Pattern(regexp = "^[\\d]\\.[\\d]$", message = "BAD_EFORM_VERSION_FORMAT") String version;
 
  @NotNull(message = "SDK-Typ darf nicht null sein.")
  @FormParam("sdkType")
  private String sdkType;
 
  @NotNull(message = "eForms dürfen nicht null sein")
  @FormParam("eforms")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private byte[] eforms;
}
```
# EForms Validator - Core: Verwendung als RESTful API

## Servicebeschreibung

EForms Validator - Core ist ein Service, der einen RESTful API-Endpunkt für die Validierung von eForms über einen Validator-Service bereitstellt. Er akzeptiert Eingaben in Form von Multipart-Formulardaten mit den erforderlichen Parametern und liefert die Validierungsergebnisse im JSON-Format zurück.

## Installation und Einrichtung

Um mit EForms Validator - Core zu beginnen, befolgen Sie diese Schritte:

1. Klonen Sie das Repository.
2. Bauen Sie das Projekt und verpacken Sie es in ein bereitstellbares Format (z. B. WAR-Datei).
```
./gradlew build
./gradlew shadowJar
```
3. Deployen Sie das erstellte Paket auf Ihrem bevorzugten Application Server.

## Ausführen des Services

Stellen Sie sicher, dass der Service ordnungsgemäß auf Ihrem Application Server bereitgestellt ist. Der Service sollte über die angegebene Endpunkt-URL zugänglich sein.

```agsl
java -jar eforms-validator-core-1.0.0-runner.jar
```

## Endpunkte

### E-Forms validieren

Endpunkt-URL: `/v1/validation`

Methode: `POST`

Consumes: `multipart/form-data`

Produces:

 `application/json`

#### Anfragemerkmale

Die Anfrage sollte Multipart-Formulardaten enthalten, die die folgenden Parameter enthalten:

- `sdkType`: Der Typ des für eForms verwendeten SDK (String).
- `version`: Die Version des verwendeten SDK (String).
- `eforms`: Die zu validierenden eForms-Daten (z. B. JSON-Darstellung) (String).

#### Antwort

Bei erfolgreicher Validierung antwortet der Service mit einem JSON-Objekt, das die Validierungsergebnisse enthält. Die Antwort hat den HTTP-Statuscode 200 (OK). Das Format der Antwort wird durch den verwendeten `ValidatorService` festgelegt.

#### Beispielverwendung

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
    "field2": "value2",
    ...
  }
}
```

## Entwicklung

Wenn Sie die Anwendung im Entwicklungsmodus ausführen, können Sie den Quellcode bearbeiten, und alle Änderungen werden sofort in der laufenden Instanz angezeigt, sodass Sie sie nicht neu starten müssen.

Sie können die Anwendung im Dev-Modus ausführen, der das Live-Coding ermöglicht, mit folgendem Befehl:

```shell script
./gradlew quarkusDev
```

Im Dev-Modus wendet Quarkus automatisch das in `schema`-Module verfasste Liquibase-Changelog an und stellt Ihnen eine einsatzbereite Datenbank zur Verfügung.

## Unit-Tests
Bitte schreiben Sie Unit-Tests mit *Junit 5*.
API-Tests werden mit *RestAssured* überprüft.

Wenn Sie Ihre Tests mit *@QuarkusTest* annotieren, startet Gradle die Anwendung im Dev-Modus, sobald Sie die Tests starten, entweder über Ihre IDE oder über die Befehlszeile.
Weitere Informationen zur Testdurchführung von Quarkus-Anwendungen finden Sie im [Quarkus-Handbuch](https://quarkus.io/guides/getting-started-testing).

Sie können die Unit-Test-Suite mit folgendem Befehl starten:

```shell script
./gradlew :app:test
```

## Code Coverage
Die Code-Abdeckung wird mit [Jacoco](https://github.com/jacoco/jacoco) gemessen und an [Sonar](https://www.sonarqube.org/) übermittelt.
Um die Code-Abdeckung während der Entwicklung im Auge zu behalten, können Sie eine lokale Sonar-Instanz starten.

Nachdem Sie Tests ausgeführt haben, führen Sie mit folgendem Befehl eine Sonar-Überprüfung durch:

```shell script
./gradlew sonarqube
```

Überprüfen Sie den Sonar-Bericht unter `http://localhost:9000`.
Die Anmeldeinformationen für den Zugriff auf das Sonar-Dashboard finden Sie in `gradle.properties`.

## CI/CD
Die Anwendung wird auf Github mit [Github-Actions](https://github.com/EFA-FHB/gdk-eforms-validator/actions) gebaut.
Detaillierte Informationen zu verfügbaren Workflows und Aktionen finden Sie im Verzeichnis [.github](README_GITHUB.md).

## Mitwirken

Wir freuen uns über Beiträge, um die Funktionalität von EForms Validator - Core zu verbessern und zu erweitern. Wenn Sie auf Probleme stoßen oder Verbesserungsvorschläge haben, können Sie gerne einen Pull-Request erstellen oder ein Problem melden.

## Lizenz

EForms Validator - Core steht unter der MIT-Lizenz. Sie können den Code frei verwenden, ändern und verteilen, unterliegen jedoch den Bedingungen der Lizenz.
