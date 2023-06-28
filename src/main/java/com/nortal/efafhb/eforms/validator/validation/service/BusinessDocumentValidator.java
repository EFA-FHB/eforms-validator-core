package com.nortal.efafhb.eforms.validator.validation.service;

import com.helger.schematron.ISchematronResource;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.xml.transform.StringStreamSource;
import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ValidatorApplicationException;
import com.nortal.efafhb.eforms.validator.validation.schematron.SchematronHelper;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
public class BusinessDocumentValidator {

  private static final String SCHEMATRON_LOCATION = "schematron/peppol/PEPPOL-T015.sch";

  private ISchematronResource schematronResource;

  @Inject ValidatorUtil validatorUtil;

  @PostConstruct
  void init() {
    loadNative();
  }

  void loadNative() {
    log.info("loading phax native schematron validator for business document...");

    log.debugf("loading schematron resource: %s", SCHEMATRON_LOCATION);
    schematronResource =
        SchematronResourcePure.fromClassPath(SCHEMATRON_LOCATION, this.getClass().getClassLoader());
    if (!schematronResource.isValidSchematron()) {
      throw new IllegalArgumentException("Invalid Schematron!");
    }
    log.info("loading phax native schematron validator for business document completed!");
  }

  public ValidationResult validate(String businessDocument) {
    List<SchematronOutputType> schematronOutputs = new ArrayList<>();
    try {
      var v =
          schematronResource.applySchematronValidationToSVRL(
              new StringStreamSource(businessDocument));
      schematronOutputs.add(v);
      return createValidationResult(schematronOutputs);
    } catch (Exception e) {
      log.warn("Exception occurred while reading source ", e);
      throw new ValidatorApplicationException(ErrorCode.MALFORMED_XML);
    }
  }

  private ValidationResult createValidationResult(List<SchematronOutputType> schematronOutputs) {
    ValidationResult validationResult = new ValidationResult();
    addFailedAssertsToValidationResult(schematronOutputs, validationResult);
    return validationResult;
  }

  private void addFailedAssertsToValidationResult(
      List<SchematronOutputType> schematronOutputs, ValidationResult validationResult) {

    schematronOutputs.forEach(
        so ->
            SchematronHelper.getAllFailedAssertions(so)
                .forEach(
                    schematronFailedAssert ->
                        validationResult.addValidationToReport(
                            ReportType.SCHEMATRON,
                            validatorUtil.getSchematronErrorLevel(
                                schematronFailedAssert.getRole(),
                                schematronFailedAssert.getFlagValue()),
                            validatorUtil.createBusinessDocumentValidationEntry(
                                schematronFailedAssert.getText(),
                                schematronFailedAssert.getTest(),
                                schematronFailedAssert.getLocation()))));
  }
}
