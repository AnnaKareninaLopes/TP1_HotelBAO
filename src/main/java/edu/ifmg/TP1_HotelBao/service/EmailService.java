package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.dtos.EmailDTO;
import edu.ifmg.TP1_HotelBao.service.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${EMAIL_USERNAME}")
    private String emailFrom;

    private JavaMailSender mailSender;

    public void sendMail(EmailDTO emailDTO){

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailDTO.getTo());
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getBody());
            mailSender.send(message);

        }catch (MailSendException e){
            throw new EmailException(e.getMessage());
        }

    }

}
