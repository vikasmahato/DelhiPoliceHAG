package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class ApplicationStatusNotifier {

    @Autowired
    EmailService emailService;

    @Autowired
    public SimpleMailMessage templateTestMessage;

    @EventListener
    public void notify(ContextRefreshedEvent event) throws IOException {

        String[] args = {"vikasmahato0@gmail.com"};
        String text = String.format(Objects.requireNonNull(templateTestMessage.getText()), (Object) args);
       // emailService.sendSimpleMessage("vikasmahato0@gmail.com", "Test Email", text);
    }
}
