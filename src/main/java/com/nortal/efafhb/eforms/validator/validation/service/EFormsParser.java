package com.nortal.efafhb.eforms.validator.validation.service;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

/** Utility to parse single element values from an eForms-XML Document. */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class EFormsParser {

  private static final Logger log = Logger.getLogger(EFormsParser.class);
  private final XML xml;

  /**
   * @param eforms the eForms XML input stream
   * @return new instance of the {@link EFormsParser}
   * @throws IllegalArgumentException if XML document cannot be initialized
   */
  public static EFormsParser init(InputStream eforms) {
    try {
      XML xml = new XMLDocument(eforms);
      return new EFormsParser(xml);
    } catch (IllegalArgumentException | IOException e) {
      log.error(
          "Exception while initializing XML from the eForms input stream: "
              + e.getLocalizedMessage());
      throw new IllegalArgumentException(
          "Exception while initializing XML from the eForms input stream");
    }
  }

  /**
   * @return the name of the root tag in notice.xml, representing the notice type (CAN/CN/PIN).
   *     {@link IllegalArgumentException} will be thrown if the root tag name cannot be found.
   */
  public String parseRootTag() {
    String rootTagName = parseXPathOrNull("local-name(/*)");
    if (Objects.isNull(rootTagName)) {
      log.warn("Exception while parsing eforms - root tag not found");
      throw new IllegalArgumentException("Exception while parsing eforms - root tag not found");
    }
    return rootTagName;
  }

  public String parseXPathOrNull(final String xPath) {
    return xml.xpath(xPath).stream().findFirst().orElse(null);
  }
}
