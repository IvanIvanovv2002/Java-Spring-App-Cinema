package com.example.cinemaapp.Services.EmailServices;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailReservation(String userEmail) {
        MimeMessage message = mimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("stu34227@uft-plovdiv.bg");
            messageHelper.setTo(userEmail);
            messageHelper.setSubject("Email for reservation");
            messageHelper.setText("Ticket is booked !");
            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailUpdatedAccount(String userEmail) {
        MimeMessage message = mimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("stu34227@uft-plovdiv.bg");
            messageHelper.setTo(userEmail);
            messageHelper.setSubject("Email for updated account.");
            messageHelper.setText("You account password was updated recently,if that wasn't you, feel free to contact our support.");
            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessageToSupport(String emailSentBy,String messageToSend) {
        MimeMessage message = mimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom(emailSentBy);
            messageHelper.setTo("stu34227@uft-plovdiv.bg");
            messageHelper.setSubject("Message to support");
            messageHelper.setText("Email sent by: " + emailSentBy + " text: " + messageToSend);
            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private MimeMessage mimeMessage() {
        return mailSender.createMimeMessage();
    }
}
