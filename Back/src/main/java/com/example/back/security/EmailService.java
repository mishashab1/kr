package com.example.back.security;

import com.example.back.security.types.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendCode(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Verification code");
            helper.setFrom("kat944166@gmail.com");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email");
        }
    }
}
