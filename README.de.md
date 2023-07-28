# eForms Validator - Core

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=coverage&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=bugs&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=security_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=sqale_rating&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=EFA-FHB_eforms-validator-core&metric=code_smells&token=b0d391e76c7ec6ffe551f1f7cd57a960fa0a17d5)](https://sonarcloud.io/summary/new_code?id=EFA-FHB_eforms-validator-core)
[![CI/CD](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml/badge.svg)](https://github.com/EFA-FHB/eforms-validator-core/actions/workflows/publish-java-gradle.yml)

[![en](https://img.shields.io/badge/lang-en-blue.svg)](README.md)
[![de](https://img.shields.io/badge/lang-de-green.svg)](./README.de.md)

## Zweck

Der eForms Validator - Core bietet eine kombinierte Offline-Validierung von eforms-EU- und eForms-DE-Schematron (.sch)-Regeln. Zusätzlich wird die Schema (.xsd)-Validierung durchgeführt, und einige eForms-EU-Regelfehler werden über eine Blacklist ausgeschlossen.

Allgemeiner Ablauf der Validierung:

1. Die eForms-EU-Schematron der entsprechenden Version wird validiert.
2. Ausgelöste Fehler von den Regeln der Schwarzen Liste werden ausgeschlossen.
3. Die eForms-DE-Schematron der entsprechenden Version wird validiert.
4. Das kombinierte Validierungsergebnis (gültig / ungültig) einschließlich der eForms-EU- und eForms-DE-Fehler und Warnungen wird zurückgegeben.

Die aktuelle Schwarze Liste kann hier gefunden werden: [excluded_rules.txt](src/main/resources/schematron/de/excluded_rules.txt).

## Technologie-Stack

- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Quarkus Framework](https://quarkus.io/guides/)
- [Gradle](https://gradle.org/)

## Unterstützte Versionen

- eForms-DE
  - 1.0.1

- eForms-SDK
  - 0.0.1
  - 1.0.0
  - 1.5.1
  
## Hardware-Anforderungen

Die Hardware-Anforderungen hängen stark davon ab, wie viele Schematron-Versionen gleichzeitig geladen werden. Die unten genannten Zahlen gelten nur für eforms-de 1.0.1.

RAM:

- Normaler Gebrauch: 1,5 GB
- Empfohlen: 2 GB

## Erstellung (Build)

Das Projekt wird mit Gradle erstellt, und ein Gradle-Wrapper ist Teil dieses Projekts. Sie müssen ihn nicht lokal auf Ihrem Rechner installieren.

### Erstellung eines schlanken JAR

Dies enthält die Anwendung selbst und alle direkten Abhängigkeiten der Anwendung. Um es lokal auszuführen, muss Java immer noch in Ihrer Umgebung installiert sein. Der Build-Befehl lautet wie folgt:

```shell script
./gradlew build
```

### Erstellung eines Fat JAR

Das Fat JAR enthält die Anwendung selbst und **alle** benötigten Abhängigkeiten. Um es auszuführen, muss Java nicht in Ihrer lokalen Umgebung installiert sein. Der Build-Befehl lautet wie folgt:

```shell script
./gradlew shadowJar
```

# Verwendung als Abhängigkeit

Fügen Sie das erforderliche Repository zu build.gradle hinzu:

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

Konfigurieren Sie fehlende gradle.properties:

```shell script
github.validator.username=validator-repo
github.validator.path=EFA-FHB/*
```

Generieren Sie ein GitHub-Token und fügen Sie es zu gradle.properties hinzu:

https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic

# ValidatorService als Service hinzufügen

```java
import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
 
@POST
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public Response validate(@MultipartForm @Valid ValidatorRequestDTO validatorRequestDTO) {
    ValidationRequestDTO validationRequestDTO = convertToValidationRequestDTO(validatorRequestDTO);
    return Response.ok(validatorService.validate(validationRequestDTO)).build();
}
```

Verwenden Sie DTO:

```java
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
 
    @NotNull(message = "Version darf nicht leer sein")
    @FormParam("eformsVersion")
    private @Pattern(regexp = "^[\\d]\\.[\\d]$", message = "Ungültiges eForms-Version-Format") String version;
 
    @NotNull(message = "SDK-Typ darf nicht leer sein.")
    @FormParam("sdkType")
    private String sdkType;
 
    @NotNull(message = "eForms darf nicht leer sein")
    @FormParam("eforms")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] eforms;
}
```

# Verwendung der RESTful API

Zusätzlich zu einem Service oder einer Abhängigkeit bietet der eForms Validator auch eine RESTful-API-Endpunkt an. Er akzeptiert Eingaben in Form von multipart/form-data mit den erforderlichen Parametern und gibt die Validierungsergebnisse im JSON-Format zurück.

## Ausführung des Dienstes

Stellen Sie sicher, dass der Dienst korrekt auf Ihrem Anwendungsserver bereitgestellt ist. Der Dienst sollte über die angegebene

 Endpunkt-URL zugänglich sein.

```agsl
java -jar eforms-validator-core-********.jar
```

## Endpunkte

### eForms validieren

Endpunkt-URL: `/v1/eforms-validation`

Methode: `POST`

Consumes: `multipart/form-data`

Produces: `application/json`

#### Request-Parameter

Die Anfrage sollte ein multipart/form-data sein und die folgenden Parameter enthalten:

- `sdkType`: Der Typ des für eForms verwendeten SDKs (String). 
  - mögliche Werte: `eforms-de`
- `version`: Die Version des verwendeten eForms Standards (String).
  - mögliche Werte: `1.0`, in der Zukunft: `1.1`, `1.2`, ...
- `eforms`: Die zu validierenden eForms (XML-Datei) (String($binary)).

#### Antwort

Bei erfolgreicher Validierung antwortet der Dienst mit einem JSON-Objekt, das die Validierungsergebnisse enthält. Die Antwort hat den HTTP-Statuscode 200 (OK). Das Format der Antwort wird durch den verwendeten `ValidatorService` festgelegt.

#### Beispiel

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

# Entwicklung

Wenn Sie die Anwendung im Dev-Modus ausführen, können Sie den Quellcode bearbeiten und alle Änderungen werden sofort in der laufenden Instanz wirksam, sodass Sie sie nicht neu starten müssen.

Sie können die Anwendung im Dev-Modus ausführen, der das Live-Coding ermöglicht:

```shell script
./gradlew quarkusDev
```

Im Dev-Modus wendet Quarkus das Liquibase-Changelog an, das im Modul "schema" erstellt wurde, automatisch an und stellt eine vorbereitete Datenbank bereit.

## Unit-Tests

Bitte schreiben Sie Unit-Tests mit *Junit 5*. API-Tests werden mit *RestAssured* überprüft.

Wenn Sie Ihre Tests mit *@QuarkusTest* annotieren, startet Gradle die Anwendung im Dev-Modus, sobald Sie die Tests auslösen, entweder über Ihre IDE oder von der Befehlszeile aus. Konsultieren Sie den [Quarkus-Leitfaden](https://quarkus.io/guides/getting-started-testing) für detaillierte Informationen zum Testen von Quarkus-Anwendungen.

Sie können die Testsuite für Unit-Tests mit folgendem Befehl ausführen:

```shell script
./gradlew :app:test
```

## Code-Abdeckung

Die Code-Abdeckung wird mit [Jacoco](https://github.com/jacoco/jacoco) gemessen und an [Sonar](https://www.sonarqube.org/) übermittelt. Um die Code-Abdeckung während der Entwicklung im Auge zu behalten, können Sie eine lokale Sonar-Instanz starten.

Nachdem Sie Tests ausgeführt haben, führen Sie eine Sonar-Überprüfung mit folgendem Befehl durch:

```shell script
./gradlew sonarqube
```

Überprüfen Sie den Sonar-Bericht auf `http://localhost:9000`.
Anmeldeinformationen zum Zugriff auf das Sonar-Dashboard finden Sie in `gradle.properties`.

## CI/CD

Die Anwendung wird auf Github mit [Github-Actions](https://github.com/EFA-FHB/gdk-eforms-validator/actions) erstellt.
Detaillierte Informationen zu verfügbaren Workflows und Aktionen finden Sie im [.github-Verzeichnis](.github/README_GITHUB.md).

## Mitwirken

Wir begrüßen Beiträge, um die Funktionalität des eForms Validator - Core zu verbessern und zu erweitern. Wenn Sie auf Probleme stoßen oder Verbesserungsvorschläge haben, erstellen Sie bitte einen Pull-Request oder melden Sie ein Issue.

## Lizenz

Der eForms Validator - Core steht unter der Apache License, Version 2.0. Sie können den Code frei verwenden, ändern und verteilen, unter Beachtung der Bedingungen der Lizenz.