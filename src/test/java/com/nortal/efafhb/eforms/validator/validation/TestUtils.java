package com.nortal.efafhb.eforms.validator.validation;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestUtils {

  public static final String DATE_PATTERN = "yyyy-MM-dd+02:00";
  public static final String DATE_PATTERN_Z = "yyyy-MM-dd'Z'";

  public static String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(getEformsAbsolutePath(fileName));
  }

  public static Path getEformsAbsolutePath(String fileName) {
    return Path.of(String.format("src/test/resources/eforms/%s", fileName)).toAbsolutePath();
  }

  public static String replaceDateTagsToCurrentDate(String eFormsFileUri, DateTags... xmlTags)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document document = dBuilder.parse(eFormsFileUri);
    document.getDocumentElement().normalize();

    for (DateTags xmlTag : xmlTags) {
      changeDateTagValue(xmlTag, document);
    }

    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(document), new StreamResult(writer));
    return writer.getBuffer().toString();
  }

  public static void changeDateTagValue(DateTags xmlTag, Document document) {
    NodeList tagNameList = document.getElementsByTagName(xmlTag.xmlTag);

    for (int i = 0; i < tagNameList.getLength(); i++) {
      Element element = (Element) tagNameList.item(i);
      if (element.getParentNode().getNodeName().equals(xmlTag.parentXmlTag)) {
        element
            .getFirstChild()
            .setNodeValue(getNewDateTimeTagValue(xmlTag.minusDays, xmlTag.datePattern));
        if (!xmlTag.changeAll) {
          break;
        }
      }
    }
  }

  public static String getNewDateTimeTagValue(long minusDays, String pattern) {
    return LocalDate.now().minusDays(minusDays).format(DateTimeFormatter.ofPattern(pattern));
  }

  public static class DateTags {

    String xmlTag;
    String parentXmlTag;
    long minusDays;
    boolean changeAll;
    String datePattern;

    public DateTags(String xmlTag, String parentXmlTag, long minusDays) {
      this(xmlTag, parentXmlTag, minusDays, false, DATE_PATTERN);
    }

    public DateTags(
        String xmlTag, String parentXmlTag, long minusDays, boolean changeAll, String datePattern) {

      this.xmlTag = xmlTag;
      this.parentXmlTag = parentXmlTag;
      this.minusDays = minusDays;
      this.changeAll = changeAll;
      this.datePattern = datePattern;
    }
  }
}
