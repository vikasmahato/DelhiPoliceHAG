package com.delhipolice.mediclaim.aspects;

import com.delhipolice.mediclaim.services.EmailServiceImpl;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ExceptionInterceptor {
    @Autowired
    EmailServiceImpl emailService;

    @Value( "${admin-email}" )
    private String to;

    @AfterThrowing(pointcut = "execution(* com.delhipolice.mediclaim..*.*(..)) && !execution(* com.delhipolice.mediclaim.services.EmailServiceImpl.*(..))", throwing = "e")
    public void interceptException(Exception e) {
        emailService.sendSimpleMessage(to, e.getMessage(), e.getStackTrace()[0].toString());
    }
}
