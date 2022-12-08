package carrental.carrentalweb.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.File;

// Mads
@Service
public class MailerService {
    private final JavaMailSender emailSender;
    private final SimpleMailMessage simpleMailMessage;
    private final MimeMessageHelper mimeMessageHelper;
    public MailerService(JavaMailSender emailSender, SimpleMailMessage simpleMailMessage, MimeMessageHelper mimeMessageHelper) {
        this.emailSender = emailSender;
        this.simpleMailMessage = simpleMailMessage;
        this.mimeMessageHelper = mimeMessageHelper;
    }
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
