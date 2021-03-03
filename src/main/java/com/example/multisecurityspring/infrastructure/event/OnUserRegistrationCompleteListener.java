package com.example.multisecurityspring.infrastructure.event;

import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.domain.service.EmailVerificationTokenService;
import com.example.multisecurityspring.domain.service.MailService;
import com.example.multisecurityspring.infrastructure.exception.MailSendException;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
@Slf4j(topic = "ON_USER_REGISTRATION_COMPLETE_LISTENER")
public class OnUserRegistrationCompleteListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final MailService mailService;

    @Autowired
    public OnUserRegistrationCompleteListener(EmailVerificationTokenService emailVerificationTokenService, MailService mailService) {
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
    }

    @Override
    @Async
    public void onApplicationEvent(OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent) {
        sendEmailVerification(onUserRegistrationCompleteEvent);
    }

    private void sendEmailVerification(OnUserRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = emailVerificationTokenService.generateNewToken();
        emailVerificationTokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", token).toUriString();

        try {
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error(e.getMessage());
            throw new MailSendException("api.mail.fail", recipientAddress, "Email Verification");
        }
    }
}
