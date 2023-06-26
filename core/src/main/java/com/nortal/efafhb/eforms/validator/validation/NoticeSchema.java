package com.nortal.efafhb.eforms.validator.validation;

import java.util.stream.Stream;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum NoticeSchema {
  CONTRACT_NOTICE("ContractNotice", "UBL-ContractNotice-2.3.xsd"),
  CONTRACT_AWARD_NOTICE("ContractAwardNotice", "UBL-ContractAwardNotice-2.3.xsd"),
  PRIOR_INFORMATION_NOTICE("PriorInformationNotice", "UBL-PriorInformationNotice-2.3.xsd");

  private static final String SCHEMA_PATH_PATTERN = "schema/%s/%s/maindoc/%s";
  private static final String EFORMS_VERSION_0_1 = "0.1";

  private final String noticeTypeName;
  private final String schemaName;

  NoticeSchema(String noticeTypeName, String schemaName) {
    this.noticeTypeName = noticeTypeName;
    this.schemaName = schemaName;
  }

  /**
   * @param noticeTypeName - the name of the notice type (e.g. ContractNotice)
   * @return the name of the XSD schema file for the given notice type name (as in root tag of the
   *     eForms.xml)
   */
  public static String getSchemaNameFromNoticeTypeName(String noticeTypeName) {
    if (StringUtils.isBlank(noticeTypeName)) {
      return null;
    }
    return Stream.of(values())
        .filter(n -> n.getNoticeTypeName().equals(noticeTypeName))
        .findAny()
        .map(NoticeSchema::getSchemaName)
        .orElse(null);
  }

  /**
   * @param requestedEformsVersion - the sdk/de version of the eForms (i.e. 1.5.1)
   * @param sdkType - type of sdk for eForms (national/european)
   * @param noticeTypeName - the name of the notice type
   * @return the path to the XSD schema file in resources, based on given sdkType (DE/EU), eForms
   *     version and the notice type name
   */
  public static String calculateSchemaPath(
      String requestedEformsVersion, SupportedType sdkType, String noticeTypeName) {
    return SCHEMA_PATH_PATTERN.formatted(
        sdkType.name().toLowerCase(),
        EFormsSupportedVersion.getSupportedVersion(requestedEformsVersion),
        getSchemaNameFromNoticeTypeName(noticeTypeName));
  }
}
