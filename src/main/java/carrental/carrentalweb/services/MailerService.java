package carrental.carrentalweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

// Mads
@Service
public class MailerService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    SimpleMailMessage simpleMailMessage;

    @Autowired
    MimeMessageHelper mimeMessageHelper;

    public void send(String to, String subject, String text) {
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        emailSender.send(simpleMailMessage);
    }

    public void send(String to, String subject, String text, File file) throws MessagingException {
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        FileSystemResource resource = new FileSystemResource(file);
        mimeMessageHelper.addAttachment(resource.getFilename(), resource);
        emailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
