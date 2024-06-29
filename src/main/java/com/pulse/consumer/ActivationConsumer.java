package com.pulse.consumer;

import com.pulse.dto.activation.Activation;
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
public class ActivationConsumer {

    private final EmailService emailService;

    @Value("${frontend.confirmation-url}")
    private String confirmationUrl;

    @RabbitListener(queues = {"${activation.queue}"})
    public void consumeActivation(@Payload Activation activation) throws MessagingException {
        emailService.sendEmail(
                activation.getEmail(),
                EmailTemplateName.CONFIRM_ACCOUNT,
                "Account confirmation",
                Map.of(
                        "fullName", activation.getFullName(),
                        "confirmationCode", activation.getConfirmationCode(),
                        "confirmationUrl", confirmationUrl
                )

        );
    }
}
