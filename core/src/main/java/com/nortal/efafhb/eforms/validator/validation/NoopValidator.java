package com.nortal.efafhb.eforms.validator.validation;

import com.nortal.efafhb.eforms.validator.validation.output.ValidationResult;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
@Startup
@IfBuildProperty(name = "eforms-validator.engine", stringValue = "noop")
class NoopValidator implements Validator {

  @PostConstruct
  void init() {
    log.warn("NoopSchematronValidator instantiated...");
  }

  @Override
  public ValidationResult validate(
      SupportedType supportedType, String eforms, SupportedVersion eformsVersion) {
    log.warn("Providing noop validation result");
    return new ValidationResult();
  }
}
