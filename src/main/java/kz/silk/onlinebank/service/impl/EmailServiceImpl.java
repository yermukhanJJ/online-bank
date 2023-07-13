package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.config.properties.EmailProperties;
import kz.silk.onlinebank.domain.enums.EmailTemplates;
import kz.silk.onlinebank.domain.exceptions.InternalErrorException;
import kz.silk.onlinebank.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
@Service(value = EmailServiceImpl.SERVICE_VALUE)
public class EmailServiceImpl implements EmailService {

    public static final String SERVICE_VALUE = "EmailServiceImpl";

    private final JavaMailSender sender;
    private final EmailProperties emailProperties;

    @Override
    public void send(@NonNull String receiver,
                     @NonNull String subject,
                     @NonNull String text,
                     boolean html) throws InternalErrorException {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(text, html);
            helper.setFrom(emailProperties.getEmailFrom());
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new InternalErrorException("Failed to send email");
        }
    }

    @Override
    public String formText(@NonNull EmailTemplates template, Object... args) throws IllegalArgumentException, IOException {
        if (template.getArgsCount() != args.length) {
            log.error("Required number of arguments is {}", template.getArgsCount());
            throw new IllegalArgumentException("Required number of arguments is " + template.getArgsCount());
        }
        try {
            String content = FileUtils.readFileToString(
                    new File(emailProperties.getTemplatesLocation() + template.getFileName()),
                    StandardCharsets.UTF_8
            );
            return String.format(content, args);
        } catch (IOException e) {
            log.error("Unable to read template file {}", template.getFileName());
            throw e;
        }
    }
}
