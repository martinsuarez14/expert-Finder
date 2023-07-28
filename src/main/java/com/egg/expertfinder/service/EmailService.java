package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Email;
import com.egg.expertfinder.repository.EmailRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private final JavaMailSender javaMailSender;
    
    @Autowired
    private EmailRepository emailRepository;
    
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;
    
    public Email sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return emailRepository.save(new Email(to, subject, body));
    }
    
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }
}
