package com.nortal.efafhb.eforms.validator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.schematron.ISchematronResource;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.xml.transform.StringStreamSource;
import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ValidatorApplicationException;
import com.nortal.efafhb.eforms.validator.validation.schematron.SchematronHelper;
import com.nortal.efafhb.eforms.validator.validation.service.BusinessDocumentValidator;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BusinessDocumentValidatorTest {

  @Mock private ISchematronResource schematronResource;
  @InjectMocks private BusinessDocumentValidator businessDocumentValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testValidate_ValidDocument_ReturnsValidValidationResult() throws Exception {
    String validDocument = "valid.xml";

    SchematronOutputType schematronOutputType = mock(SchematronOutputType.class);
    when(schematronResource.applySchematronValidationToSVRL(any(StringStreamSource.class)))
        .thenReturn(schematronOutputType);
    when(SchematronHelper.getAllFailedAssertions(schematronOutputType))
        .thenReturn(new CommonsArrayList<>());

    ValidationResult result = businessDocumentValidator.validate(validDocument);

    assertNotNull(result);
    assertTrue(result.getErrors().isEmpty());
  }

  @Test
  void testValidate_ValidDocument_ReturnsErrorValidationResult() throws Exception {
    String validDocument = "valid.xml";

    SchematronOutputType schematronOutputType = mock(SchematronOutputType.class);
    when(schematronResource.applySchematronValidationToSVRL(any(StringStreamSource.class)))
        .thenReturn(schematronOutputType);
    when(SchematronHelper.getAllFailedAssertions(schematronOutputType))
        .thenReturn(new CommonsArrayList<>());

    businessDocumentValidator.loadNative();

    ValidatorApplicationException thrown =
        assertThrows(
            ValidatorApplicationException.class,
            () -> businessDocumentValidator.validate(validDocument),
            "Expected doThing() to throw, but it didn't");

    assertEquals(ErrorCode.MALFORMED_XML, thrown.getErrorCode());
  }
}
