/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import static org.javasoft.peasoft.constants.PeaResource.CONTENT_HTML;

/**
 *
 * @author ayojava
 */
@Slf4j
public class EmailService {

    private final String senderEmail;

    private final String senderName;

    private final Session mailSession;

    private Message mimeMsg;

    public EmailService(String senderEmail, String senderName, Session mailSession) {
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.mailSession = mailSession;
    }

    public boolean sendHtmlMessage(String emailSubject, String messageBody, String recipientName, String recipientEmail) {
        mimeMsg = new MimeMessage(mailSession);

        try {
            mimeMsg.setContent(messageBody, CONTENT_HTML);
            mimeMsg.setSubject(emailSubject);
            InternetAddress fromAddress = new InternetAddress(senderEmail, senderName);
            mimeMsg.setFrom(fromAddress);

            InternetAddress toAddress = new InternetAddress(recipientEmail, recipientName);
            mimeMsg.addRecipient(Message.RecipientType.TO, toAddress);
            mimeMsg.setSentDate(new Date());
            Transport.send(mimeMsg);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error(" Error while sending email :: ", ex);
            return false;
        }
        return true;
    }
    //if true , then delete the object from the database 

    public boolean sendHtmlMessageWithAttachment(String emailSubject, String messageBody, String recipientName, String recipientEmail,
            String[] pathToFileAttachment) {
        mimeMsg = new MimeMessage(mailSession);

        try {
            mimeMsg.setContent(messageBody, CONTENT_HTML);
            mimeMsg.setSubject(emailSubject);
            InternetAddress fromAddress = new InternetAddress(senderEmail, senderName);
            mimeMsg.setFrom(fromAddress);

            InternetAddress toAddress = new InternetAddress(recipientEmail, recipientName);
            mimeMsg.addRecipient(Message.RecipientType.TO, toAddress);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageBody, CONTENT_HTML);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (pathToFileAttachment != null && pathToFileAttachment.length > 0) {
                addAttachments(pathToFileAttachment, multipart);
            }

            mimeMsg.setContent(multipart);
            mimeMsg.setSentDate(new Date());
            Transport.send(mimeMsg);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error(" Error while sending email :: ", ex);
            return false;
        }
        return true;
    }

    private void addAttachments(String[] pathToFileAttachment, Multipart multipart) throws MessagingException, AddressException {
        for (int i = 0; i <= pathToFileAttachment.length - 1; i++) {
            String path = pathToFileAttachment[i];
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(path);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            File f = new File(path);
            if (f.exists()) {
                attachmentBodyPart.setFileName(f.getName());
                multipart.addBodyPart(attachmentBodyPart);
            }
        }
    }
}
