package com.tc.training.jewelleryapplication.email;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendEmail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("priyanshugupta212@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        javaMailSender.send(message);
//
//        System.out.println("Mail Sent Successfully...");
//    }
//
//
//}


//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.MessagingException;
//import java.io.IOException;
//import java.util.Map;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    @Qualifier("freeMarkerConfigurationFactoryBean") // Specify the correct bean name here
//    private Configuration freemarkerConfig;
//
//
//    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> model)
//            throws MessagingException, IOException, TemplateException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//
//        Template template = freemarkerConfig.getTemplate(templateName + ".html");
//        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//
//        messageHelper.setTo(to);
//        messageHelper.setSubject(subject);
//        messageHelper.setText(htmlContent, true);
//
//        javaMailSender.send(mimeMessage);
//    }
//}











import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private SendGrid sendGrid;

    public void sendHtmlEmail(String recipientEmail, String username, String password) throws Exception {
        Email from = new Email("anshul.prajapati@thoughtclan.com");
        Email to = new Email(recipientEmail);
        String subject = "Welcome to Precious Jewellery Website!";
        String htmlContent = generateHTMLContent(username, password);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);

        if (response.getStatusCode() != 202) {
            throw new Exception("Email sending failed. Status code: " + response.getStatusCode());
        }
    }

    private String generateHTMLContent(String username, String password) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f2f2f2;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h2 {\n" +
                "            color: #333;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #666;\n" +
                "            margin-bottom: 15px;\n" +
                "        }\n" +
                "        .cta-button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #007bff;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h2>Welcome to Precious Jewellery Website!</h2>\n" +
                "    <p>Dear " + username + ",</p>\n" +
                "    <p>Thank you for signing up. We are thrilled to have you as a member of our jewellery community.</p>\n" +
                "    <p>Your login details:</p>\n" +
                "    <p><strong>Username:</strong> " + username + "</p>\n" +
                "    <p><strong>Password:</strong> " + password + "</p>\n" +
                "    <p>Feel free to browse our collections and add your favorite pieces to your cart. If you have any questions or need assistance, our dedicated support team is here to help.</p>\n" +
                "    <p>Ready to start your jewellery journey? Just click the button below to log in and explore.</p>\n" +
                "    <a class=\"cta-button\" href=\"https://your-jewelry-platform.com/login\">Log In</a>\n" +
                "    <p>We look forward to seeing you shine!</p>\n" +
                "    <p>Best regards,</p>\n" +
                "    <p>The Precious Jewellery Team</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}



