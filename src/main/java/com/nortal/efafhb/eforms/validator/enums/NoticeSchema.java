package com.nortal.efafhb.eforms.validator.enums;

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

  /**
   * Constructs a new NoticeSchema enum with the given notice type name and schema name.
   *
   * @param noticeTypeName the name of the notice type
   * @param schemaName     the name of the XSD schema file
   */
  NoticeSchema(String noticeTypeName, String schemaName) {
    this.noticeTypeName = noticeTypeName;
    this.schemaName = schemaName;
  }

  /**
   * Retrieves the schema name from the notice type name.
   *
   * @param noticeTypeName the name of the notice type (e.g., "ContractNotice")
   * @return the name of the XSD schema file for the given notice type name (as in the root tag of the eForms.xml),
   *         or null if not found
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
   * Calculates the path to the XSD schema file in the resources, based on the specified SDK type, eForms version,
   * and notice type name.
   *
   * @param requestedEformsVersion the SDK/DE version of the eForms (e.g., "1.5.1")
   * @param sdkType                the type of SDK for eForms (national/european)
   * @param noticeTypeName         the name of the notice type
   * @return the path to the XSD schema file in the resources, based on the given SDK type, eForms version,
   *         and notice type name
   */
  public static String calculateSchemaPath(
      String requestedEformsVersion, SupportedType sdkType, String noticeTypeName) {
    return SCHEMA_PATH_PATTERN.formatted(
        sdkType.name().toLowerCase(),
        EFormSupportedVersion.getSupportedVersion(requestedEformsVersion),
        getSchemaNameFromNoticeTypeName(noticeTypeName));
  }
}
