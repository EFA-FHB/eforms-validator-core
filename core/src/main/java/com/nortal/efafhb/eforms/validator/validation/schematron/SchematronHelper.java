package com.nortal.efafhb.eforms.validator.validation.schematron;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.schematron.svrl.ISVRLLocationBeautifier;
import com.helger.schematron.svrl.SVRLLocationBeautifierRegistry;
import com.helger.schematron.svrl.jaxb.FailedAssert;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.schematron.svrl.jaxb.Text;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SchematronHelper {

  private SchematronHelper() {}

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList<SchematronFailedAssert> getAllFailedAssertions(
      @Nullable SchematronOutputType schematronOutputType) {
    CommonsArrayList<SchematronFailedAssert> failedAsserts = new CommonsArrayList<>();
    if (schematronOutputType != null) {

      for (Object failedAssert :
          schematronOutputType.getActivePatternAndFiredRuleAndFailedAssert()) {
        if (failedAssert instanceof FailedAssert fa) {
          failedAsserts.add(new SchematronFailedAssert(fa));
        }
      }
    }
    return failedAsserts;
  }

  @Nonnull
  public static String getBeautifiedLocation(@Nonnull String var0) {
    return getBeautifiedLocation(var0, SVRLLocationBeautifierRegistry::getBeautifiedLocation);
  }

  @Nonnull
  public static String getBeautifiedLocation(
      @Nonnull String location, @Nonnull ISVRLLocationBeautifier locationBeautifier) {
    ValueEnforcer.notNull(location, "Location");
    ValueEnforcer.notNull(locationBeautifier, "LocationBeautifier");
    String locationBeautified = location;
    Matcher matcher =
        RegExHelper.getMatcher(
            "\\Q*:\\E([a-zA-Z0-9_]+)\\Q[namespace-uri()='\\E([^']+)\\Q']\\E", location);

    while (matcher.find()) {
      String localName = matcher.group(1);
      String namespaceUri = matcher.group(2);
      String nameBeautified = locationBeautifier.getReplacementText(namespaceUri, localName);
      if (nameBeautified != null) {
        locationBeautified =
            StringHelper.replaceAll(locationBeautified, matcher.group(), nameBeautified);
      }
    }
    return locationBeautified;
  }

  @Nullable
  public static String getAsString(@Nullable Text text) {
    if (text == null) {
      return null;
    } else {
      StringBuilder sb = new StringBuilder();
      for (Object textContent : text.getContent()) {
        sb.append(textContent);
      }
      return sb.toString();
    }
  }

  @Nonnull
  public static Text getText(@Nonnull FailedAssert var0) {
    List<Object> diagnosticReferenceOrPropertyReferenceOrText =
        var0.getDiagnosticReferenceOrPropertyReferenceOrText();
    Predicate<Object> isText = Text.class::isInstance;
    return Objects.requireNonNull(
        CollectionHelper.findFirstMapped(
            diagnosticReferenceOrPropertyReferenceOrText, isText, Text.class::cast));
  }
}