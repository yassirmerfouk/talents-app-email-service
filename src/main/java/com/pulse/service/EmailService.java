package com.pulse.service;

import com.pulse.enumeration.EmailTemplateName;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {

    void sendEmail(
            String to,
            EmailTemplateName emailTemplateName,
            String subject,
            Map<String, Object> properties
    ) throws MessagingException;
}
