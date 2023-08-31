package com.tc.training.jewelleryapplication.email;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("priyanshugupta212@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);

        System.out.println("Mail Sent Successfully...");
    }
}


//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private Configuration freemarkerConfig; // Autowire Freemarker configuration
//
//    public void sendEmail(String to, String subject, String templateName, Map<String, Object> model) throws MessagingException, IOException, TemplateException, jakarta.mail.MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//
//        // Load the template content
//        Template template = freemarkerConfig.getTemplate(templateName);
//        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//
//        messageHelper.setTo(to);
//        messageHelper.setSubject(subject);
//        messageHelper.setText(htmlContent, true); // Set HTML content
//
//        javaMailSender.send(mimeMessage);
//    }
//}
