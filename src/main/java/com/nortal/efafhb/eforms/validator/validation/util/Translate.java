package com.nortal.efafhb.eforms.validator.validation.util;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.jcabi.xml.XMLDocument;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.io.IOUtils;

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
    validationConfig
        .supportedEFormsVersions()
        .forEach(
            version -> {
              SupportedVersion supportedVersion = SupportedVersion.versionFromSDK(version);
              SupportedType supportedType = SupportedType.typeFromSDK(version);
              String supportedTypeName = supportedType.name().toLowerCase();
              String translationFile = readFile(supportedTypeName, supportedVersion.getValue());
              if (isNotEmpty(translationFile)) {
                getTranslations(supportedType)
                    .put(supportedVersion, new XMLDocument(translationFile));
                getTranslationsCashed(supportedType).put(supportedVersion, new HashMap<>());
              }
            });
  }

  /**
   * Translate error/warning messages using eforms sdk translation files
   *
   * @param version version of eforms
   * @param content rule to be translated
   * @return translated rule
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

  private void assertTranslations() {
    if (!translationsLoaded.get()) {
      loadTranslations();
      translationsLoaded.set(true);
    }
  }

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

  private EnumMap<SupportedVersion, XMLDocument> getTranslations(SupportedType supportedType) {
    return switch (supportedType) {
      case DE -> translationsDe;
      case EU -> translationsEu;
    };
  }

  private EnumMap<SupportedVersion, HashMap<String, String>> getTranslationsCashed(
      SupportedType supportedType) {
    return switch (supportedType) {
      case DE -> translationsCashedDe;
      case EU -> translationsCashedEu;
    };
  }
}
