package com.mm.interceptor;

import com.mm.common.util.ValidationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 审计日志记录
 *
 * @author tanwenhai@gusoftware.com
 */
@Aspect
@Component
@Slf4j
public class HttpInterceptor implements ApplicationContextAware {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private ApplicationContext application;

    @Autowired
    HttpServletRequest request;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }

    @Pointcut("execution(* com.mm.module..controller..*(..))")
    public static void webPointcut() {
        // NONE
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public static void requestMapping() {
        // NONE
    }

    @Around("webPointcut() && requestMapping()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        checkValid(method, pjp.getArgs());

        Object result = pjp.proceed();

        return result;
    }

    public void checkValid(Method method, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Errors) {
                ValidationUtils.check((Errors)arg);
            }
        }
    }
}
