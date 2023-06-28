package com.nortal.efafhb.eforms.validator.validation.schematron;

import com.helger.schematron.svrl.AbstractSVRLMessage;
import com.helger.schematron.svrl.jaxb.FailedAssert;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public class SchematronFailedAssert extends AbstractSVRLMessage {

  private final String flagValue;

  public SchematronFailedAssert(@Nonnull FailedAssert failedAssert) {
    this(
        failedAssert,
        var0 -> SchematronHelper.getBeautifiedLocation(Objects.requireNonNull(var0.getLocation())));
  }

  public SchematronFailedAssert(
      @Nonnull FailedAssert failedAssert,
      @Nonnull Function<? super FailedAssert, String> getBeautifiedLocation) {
    super(
        null,
        failedAssert.getId(),
        SchematronHelper.getAsString(SchematronHelper.getText(failedAssert)),
        getBeautifiedLocation.apply(failedAssert),
        failedAssert.getTest(),
        failedAssert.getRole(),
        null);
    this.flagValue = failedAssert.getFlag();
  }

  @Nonnull
  public final String getFlagValue() {
    return this.flagValue;
  }
}
