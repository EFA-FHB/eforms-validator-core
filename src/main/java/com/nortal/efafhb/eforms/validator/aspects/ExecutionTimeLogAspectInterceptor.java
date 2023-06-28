package com.nortal.efafhb.eforms.validator.aspects;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import lombok.extern.jbosslog.JBossLog;

@ExecutionTimeLogAspect
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@JBossLog
public class ExecutionTimeLogAspectInterceptor {

  @AroundInvoke
  Object logInvocation(InvocationContext context) throws Exception {
    long start = System.currentTimeMillis();
    Object ret = context.proceed();

    log.debugf(
        "CLASS: %s, METHOD: %s, EXECUTION TIME: %sms",
        context.getTarget().getClass().getSimpleName(),
        context.getMethod().getName(),
        System.currentTimeMillis() - start);

    return ret;
  }
}
