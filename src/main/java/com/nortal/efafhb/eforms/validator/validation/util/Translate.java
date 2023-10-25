package com.nortal.efafhb.eforms.validator.validation.util;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.jcabi.xml.XMLDocument;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.io.IOUtils;

/** Utility class for translating error/warning messages using eforms sdk translation files. */
@Singleton
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@JBossLog
public class Translate {

  private static final String SCHEMATRON_TRANSLATIONS_LOC =
      "schematron/%s/translations/%s/rule_en.xml";

  private static final EnumMap<SupportedVersion, XMLDocument> translationsEu =
      new EnumMap<>(SupportedVersion.class);
  private static final EnumMap<SupportedVersion, XMLDocument> translationsDe =
      new EnumMap<>(SupportedVersion.class);
  private static final EnumMap<SupportedVersion, HashMap<String, String>> translationsCashedEu =
      new EnumMap<>(SupportedVersion.class);
  private static final EnumMap<SupportedVersion, HashMap<String, String>> translationsCashedDe =
      new EnumMap<>(SupportedVersion.class);
  private static final String TRANSLATE_XPATH_PATTERN =
      "//*[local-name()='entry'][@key='%s']/text()";
  private final AtomicBoolean translationsLoaded = new AtomicBoolean();

  @Inject ValidationConfig validationConfig;

  private void loadTranslations() {
    validationConfig.supportedEFormsVersions().forEach(this::processTranslation);
  }

  private void processTranslation(String version) {
    SupportedVersion supportedVersion = SupportedVersion.versionFromSDK(version);
    SupportedType supportedType = SupportedType.typeFromSDK(version);
    String supportedTypeName = supportedType.name().toLowerCase();

    String translationFile = readFile(supportedTypeName, supportedVersion.getValue());
    if (isNotEmpty(translationFile)) {
      getTranslations(supportedType).put(supportedVersion, new XMLDocument(translationFile));
      getTranslationsCashed(supportedType).put(supportedVersion, new HashMap<>());
    }
  }

  /**
   * Translate error/warning messages using eforms sdk translation files.
   *
   * @param version the version of eforms
   * @param supportedType the supported type of eforms
   * @param content the rule to be translated
   * @return the translated rule
   */
  public String translate(SupportedVersion version, SupportedType supportedType, String content) {
    assertTranslations();
    XMLDocument xml = getTranslations(supportedType).get(version);
    if (xml == null) {
      return null;
    }
    if (getTranslationsCashed(supportedType).get(version).containsKey(content)) {
      return getTranslationsCashed(supportedType).get(version).get(content);
    }
    String normalizedContent = content.replace("'", "\"");
    String pathPattern = String.format(TRANSLATE_XPATH_PATTERN, normalizedContent);
    String foundTranslate = null;
    try {
      foundTranslate = xml.xpath(pathPattern).stream().findFirst().orElse(null);
    } catch (IllegalArgumentException e) {
      log.warn(
          String.format(
              "The rule content:[ %s ] makes the pathPattern used when searching "
                  + "for translation an invalid XPath expression",
              normalizedContent));
    }
    getTranslationsCashed(supportedType).get(version).put(content, foundTranslate);
    return foundTranslate;
  }

  /** Checks if translations are loaded and loads them if necessary. */
  private void assertTranslations() {
    if (!translationsLoaded.get()) {
      loadTranslations();
      translationsLoaded.set(true);
    }
  }

  /**
   * Reads the translation file for the given SDK type name and version.
   *
   * @param sdkTypeName the SDK type name
   * @param version the version of the translation file
   * @return the contents of the translation file as a string, or {@code null} if the file could not
   *     be read
   */
  private String readFile(String sdkTypeName, String version) {
    String location = String.format(SCHEMATRON_TRANSLATIONS_LOC, sdkTypeName, version);
    InputStream inputStream = null;
    try {
      inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.warnf("Error while reading in file %s", location, e);
      return null;
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
  }

  /**
   * Retrieves the translations map for the given supported type.
   *
   * @param supportedType the supported type
   * @return the translations map for the supported type
   */
  private EnumMap<SupportedVersion, XMLDocument> getTranslations(SupportedType supportedType) {
    switch (supportedType) {
      case DE:
        return translationsDe;
      case EU:
        return translationsEu;
      default:
        throw new IllegalArgumentException("Unsupported type: " + supportedType);
    }
  }

  /**
   * Retrieves the cached translations map for the given supported type.
   *
   * @param supportedType the supported type
   * @return the cached translations map for the supported type
   */
  private EnumMap<SupportedVersion, HashMap<String, String>> getTranslationsCashed(
      SupportedType supportedType) {
    switch (supportedType) {
      case DE:
        return translationsCashedDe;
      case EU:
        return translationsCashedEu;
      default:
        throw new IllegalArgumentException("Unsupported type: " + supportedType);
    }
  }
}
