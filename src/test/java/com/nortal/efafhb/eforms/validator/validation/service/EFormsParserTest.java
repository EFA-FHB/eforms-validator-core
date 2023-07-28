package com.nortal.efafhb.eforms.validator.validation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.jcabi.xml.XML;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EFormsParserTest {

  @Mock private XML xml;

  @InjectMocks private EFormsParser eFormsParser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testParseRootTag_InvalidXML_ThrowsIllegalArgumentException() {
    when(xml.xpath("/element[2]/child[3]")).thenReturn(null);

    assertThrows(IllegalArgumentException.class, () -> eFormsParser.parseRootTag());
  }

  @Test
  void testParseXPathOrNull_ValidXPath_ReturnsValue() {
    String xPath = "/root/test";
    String value = "<root></root>";
    when(xml.xpath(xPath)).thenReturn(List.of("<root></root>"));

    String result = eFormsParser.parseXPathOrNull(xPath);

    assertEquals(value, result);
  }
}
