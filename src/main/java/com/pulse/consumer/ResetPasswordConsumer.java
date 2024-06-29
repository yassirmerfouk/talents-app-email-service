package com.pulse.consumer;

import com.pulse.dto.password.ResetPassword;
import com.pulse.enumeration.EmailTemplateName;
import com.pulse.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResetPasswordConsumer {

    private final EmailService emailService;

    @Value("${frontend.reset-password-url}")
    private String passwordResetUrl;

    @RabbitListener(queues = {"${reset-password.queue}"})
    public void consumeResetPassword(@Payload ResetPassword resetPassword) throws MessagingException {
        emailService.sendEmail(
                resetPassword.getEmail(),
                EmailTemplateName.RESET_PASSWORD,
                "Password reset",
                Map.of(
                        "fullName", resetPassword.getFullName(),
                        "resetPasswordUrl", passwordResetUrl + resetPassword.getToken() + "?email=" + resetPassword.getEmail()
                )
        );
    }
}
