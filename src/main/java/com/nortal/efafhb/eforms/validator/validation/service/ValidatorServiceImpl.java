package com.nortal.efafhb.eforms.validator.validation.service;

import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EFORMS_SDK_VERSION_DELIMITER;
import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.VALIDATION_ENTRY_XSD_TYPE;
import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.XSD_VALIDATION_FAILED_CODE;

import com.nortal.efafhb.eforms.validator.aspects.ExecutionTimeLogAspect;
import com.nortal.efafhb.eforms.validator.common.Constants;
import com.nortal.efafhb.eforms.validator.enums.NoticeSchema;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ValidatorApplicationException;
import com.nortal.efafhb.eforms.validator.validation.FormsValidator;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelEntryDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

@JBossLog
@ApplicationScoped
public class ValidatorServiceImpl implements ValidatorService {

  BusinessDocumentValidator businessDocumentValidator;
  ValidationConfig validationConfig;
  FormsValidator formsValidator;

  public ValidatorServiceImpl(
      BusinessDocumentValidator businessDocumentValidator,
      ValidationConfig validationConfig,
      FormsValidator formsValidator) {
    this.businessDocumentValidator = businessDocumentValidator;
    this.validationConfig = validationConfig;
    this.formsValidator = formsValidator;
  }

  @ExecutionTimeLogAspect
  public ValidationModelDTO validate(ValidationRequestDTO validationRequestDTO) {
    ValidationModelDTO validationModelDTO = null;
    SupportedType sdkType = getSupportedSdkType(validationRequestDTO);

    if (validationRequestDTO.isXsdValidation()) {
      validationModelDTO = validateXSD(validationRequestDTO, sdkType);
      if (Boolean.FALSE.equals(validationModelDTO.getValid())) {
        return validationModelDTO;
      }
    }

    if (validationRequestDTO.isSchematronValidation()) {
      validationModelDTO = validateSchematron(validationRequestDTO, sdkType);
    }

    return validationModelDTO;
  }

  private ValidationModelDTO validateXSD(
      ValidationRequestDTO validationRequestDTO, SupportedType sdkType) {
    String requestedEformsVersion =
        StringUtils.joinWith(
            EFORMS_SDK_VERSION_DELIMITER,
            sdkType.getStandardizedName(),
            validationRequestDTO.getVersion());
    String validatedEformsVersion =
        StringUtils.joinWith(
            EFORMS_SDK_VERSION_DELIMITER,
            sdkType.getStandardizedName(),
            SupportedVersion.versionFromSDK(requestedEformsVersion).getValue());
    try {
      verifySchemaValid(validationRequestDTO.getEforms(), requestedEformsVersion, sdkType);
      return getValidXSDValidationModel(validatedEformsVersion);
    } catch (SAXException e) {
      return getInvalidXSDValidationModel(validatedEformsVersion, e.getMessage());
    }
  }

  private ValidationModelDTO validateSchematron(
      ValidationRequestDTO validationRequestDTO, SupportedType sdkType) {
    String requestedEformsVersion =
        StringUtils.joinWith(
            EFORMS_SDK_VERSION_DELIMITER,
            sdkType.getStandardizedName(),
            validationRequestDTO.getVersion());
    return convertToValidationModel(
        formsValidator.validate(
            sdkType,
            new String(validationRequestDTO.getEforms(), StandardCharsets.UTF_8),
            SupportedVersion.versionFromSDK(requestedEformsVersion)));
  }

  @ExecutionTimeLogAspect
  public BusinessDocumentValidationModelDTO validateBusinessDocument(
      BusinessDocumentValidationRequestDTO validationRequestDTO) {
    return convertToBusinessDocumentValidationModel(
        businessDocumentValidator.validate(
            new String(validationRequestDTO.getBusinessDocument(), StandardCharsets.UTF_8)));
  }

  private SupportedType getSupportedSdkType(ValidationRequestDTO validationRequestDTO) {
    SupportedType sdkType = SupportedType.findByValueIgnoreCase(validationRequestDTO.getSdkType());
    String requestedEformsVersion =
        StringUtils.joinWith(
            EFORMS_SDK_VERSION_DELIMITER,
            sdkType.getStandardizedName(),
            validationRequestDTO.getVersion());
    boolean isSupported =
        validationConfig.supportedEFormsVersions().contains(requestedEformsVersion);
    if (!isSupported) {
      throw new ValidatorApplicationException(ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE);
    }
    return sdkType;
  }

  private ValidationModelDTO convertToValidationModel(ValidationResult schematronResult) {
    return ValidationModelDTO.builder()
        .warnings(getWarnings(schematronResult))
        .errors(getErrors(schematronResult))
        .validatedEformsVersion(schematronResult.getSdkValidationVersion())
        .valid(schematronResult.getErrors().isEmpty())
        .build();
  }

  private BusinessDocumentValidationModelDTO convertToBusinessDocumentValidationModel(
      ValidationResult schematronResult) {
    return BusinessDocumentValidationModelDTO.builder()
        .warnings(getWarnings(schematronResult))
        .errors(getErrors(schematronResult))
        .valid(schematronResult.getErrors().isEmpty())
        .build();
  }

  private Set<ValidationModelEntryDTO> getErrors(ValidationResult schematronResult) {
    return schematronResult.getErrors().stream()
        .map(buildValidationDetails())
        .collect(Collectors.toSet());
  }

  private Set<ValidationModelEntryDTO> getWarnings(ValidationResult schematronResult) {
    return schematronResult.getWarnings().stream()
        .map(buildValidationDetails())
        .collect(Collectors.toSet());
  }

  private Function<ValidationEntry, ValidationModelEntryDTO> buildValidationDetails() {
    return validationEntry ->
        ValidationModelEntryDTO.builder()
            .path(validationEntry.getPath())
            .rule(validationEntry.getRule())
            .ruleContent(validationEntry.getTest())
            .description(validationEntry.getDescription())
            .type(validationEntry.getType())
            .build();
  }

  private void verifySchemaValid(
      byte[] eForms, String requestedEformsVersion, SupportedType sdkType) throws SAXException {
    try {
      InputStream notice = new ByteArrayInputStream(eForms);
      String noticeTypeName = EFormsParser.init(notice).parseRootTag();
      javax.xml.validation.Validator validator =
          getXSDValidator(requestedEformsVersion, sdkType, noticeTypeName);
      validator.validate(new StreamSource(toReader(eForms)));
    } catch (IllegalArgumentException e) {
      throw new ValidatorApplicationException(ErrorCode.MALFORMED_XML);
    } catch (IOException e) {
      log.warnf("Exception while writing eForms document to file. Reason: %s", e.getMessage());
      throw new IllegalArgumentException("Exception while writing eForms document to file");
    }
  }

  private Validator getXSDValidator(
      String requestedEformsVersion, SupportedType sdkType, String noticeTypeName) {
    String schemaPath =
        NoticeSchema.calculateSchemaPath(requestedEformsVersion, sdkType, noticeTypeName);
    log.debug(
        String.format(
            "Validating eForms xml document based on schema stored in location: %s", schemaPath));
    URL resource = ValidatorServiceImpl.class.getClassLoader().getResource(schemaPath);
    if (resource == null) {
      throw new ValidatorApplicationException(ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE);
    }
    try {
      return SchemaFactory.newDefaultInstance().newSchema(resource).newValidator();
    } catch (SAXException e) {
      throw new IllegalArgumentException("Exception while parsing schema resource.");
    }
  }

  private static ValidationModelDTO getInvalidXSDValidationModel(
      String validatedEformsVersion, String errorMessage) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));
    ValidationModelEntryDTO error =
        ValidationModelEntryDTO.builder()
            .type(VALIDATION_ENTRY_XSD_TYPE)
            .description(
                String.format(resourceBundle.getString(XSD_VALIDATION_FAILED_CODE), errorMessage))
            .build();
    return ValidationModelDTO.builder()
        .errors(new HashSet<>(Collections.singleton(error)))
        .warnings(Collections.emptySet())
        .validatedEformsVersion(validatedEformsVersion)
        .valid(false)
        .build();
  }

  private static ValidationModelDTO getValidXSDValidationModel(String validatedEformsVersion) {
    return ValidationModelDTO.builder()
        .errors(Collections.emptySet())
        .warnings(Collections.emptySet())
        .validatedEformsVersion(validatedEformsVersion)
        .valid(true)
        .build();
  }

  private Reader toReader(final byte[] eForms) {
    return new InputStreamReader(new ByteArrayInputStream(eForms), StandardCharsets.UTF_8);
  }
}
