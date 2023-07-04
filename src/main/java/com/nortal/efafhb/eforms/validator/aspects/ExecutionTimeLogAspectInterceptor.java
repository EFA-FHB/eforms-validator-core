package com.nortal.efafhb.eforms.validator.aspects;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import lombok.extern.jbosslog.JBossLog;

/** Interceptor for logging the execution time of methods. */
@ExecutionTimeLogAspect
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@JBossLog
public class ExecutionTimeLogAspectInterceptor {

  /**
   * Logs the invocation of a method and measures the execution time.
   *
   * @param context the invocation context
   * @return the result of the method invocation
   * @throws Exception if an error occurs during method invocation
   */
  @AroundInvoke
  Object logInvocation(InvocationContext context) throws Exception {
    long start = System.currentTimeMillis();
    Object proceed = context.proceed();

    log.debugf(
        "CLASS: %s, METHOD: %s, EXECUTION TIME: %sms",
        context.getTarget().getClass().getSimpleName(),
        context.getMethod().getName(),
        System.currentTimeMillis() - start);

    return proceed;
  }
}
