package carrental.carrentalweb.config;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailConfig {

    @Value("${car-rental.email.username}")
    private String username;

    @Value("${car-rental.email.password}")
    private String password;
    
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = mailSender.getJavaMailProperties();
        
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        mailSender.setHost("smtp-mail.outlook.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        return mailSender;
    }

    @Bean
    public SimpleMailMessage createSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        return message;
    }

    @Bean
    public MimeMessageHelper createMailWithAttachment() throws MessagingException {
        MimeMessage message = getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(username);
        return helper;
    }
}
