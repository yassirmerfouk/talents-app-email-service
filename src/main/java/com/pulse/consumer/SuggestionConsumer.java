package com.pulse.consumer;

import com.pulse.dto.suggestion.Suggestion;
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
public class SuggestionConsumer {

    private final EmailService emailService;

    @Value("${frontend.job-url}")
    private String jobUrl;

    @RabbitListener(queues = {"${suggestions.queue}"})
    public void consumeSuggestion(@Payload Suggestion suggestion) throws MessagingException {
        System.out.println(suggestion.getEmail());
        emailService.sendEmail(
                suggestion.getEmail(),
                EmailTemplateName.SUGGESTIONS,
                "Jobs suggestions",
                Map.of(
                        "fullName", suggestion.getFirstName() + " " + suggestion.getLastName(),
                        "suggestedJobs", suggestion.getSuggestedJobs(),
                        "jobUrl", jobUrl
                )

        );
    }
}
