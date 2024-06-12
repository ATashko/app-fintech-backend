package com.msuser.msuser.service.impl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String userMail, String username) {

        String subject = "Activación de la cuenta";
        String from = "noreply@yourdomain.com";

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Para activar su cuenta, por favor haz clic aquí: ")
                .append("http://localhost:3333/enable/").append(username);

        sendEmail(userMail, subject, from, messageBuilder.toString());
    }

    public void sendDepositVerificationEmail(String userMail, String userId) {

        String subject = "Activación de la cuenta";
        String from = "noreply@yourdomain.com";

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Tu deposito ha sido recibido, para confirmar, haz click aquí: ")
                .append("http://localhost:3333/enable/").append(userId);

        sendEmail(userMail, subject, from, messageBuilder.toString());
    }

    private void sendEmail(String to, String subject, String from, String message) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(message, false);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
