/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
public class Delete {
    
    private String mailSender;
    private String password;
    private String mailServer;
    
    public Delete(){
        mailSender="adedapo.conde@peafoundation.org"; //brainchallenge2017@peafoundation.org
        password="brainchallenge$1";//richfaces$1
        mailServer="mail.peafoundation.org";
    }
    
    private void sendMessage(boolean isHtml, String message, String subject, String recipient) throws MessagingException {

        Session mailSession = getMailSession(true);

        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setSentDate(new Date());
        mimeMessage.setSubject(subject);
        mimeMessage.setFrom(new InternetAddress(mailSender));

        mimeMessage.setContent(message, (isHtml) ? "text/html" : "text/plain");
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        Transport transport = mailSession.getTransport();
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
    
    private Properties builMailProperties(String debug) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enabled", "false");
        props.setProperty("mail.smtp.host", mailServer);
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.starttls.enable", "true");
        //props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.debug", debug);
        return props;
    }

    private Session getMailSession(boolean authenticate) {
        Session session = null;
        if (authenticate) {

            session = Session.getInstance(builMailProperties("true"),
                    new Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailSender, password);
                        }
                    });
        } else {
            session = Session.getInstance(builMailProperties("true"), null);
        }
        return session;
    }
    
    
  
     public static void main(String [] args){
//        String val="/home/ayojava/programs/wildfly-9.0/standalone/tmp/vfs/temp/temp64a9645cacc1341e/content-24fb6dad413ab749";
//        int position = StringUtils.indexOf(val, "wildfly");
//        System.out.println("====" + StringUtils.substring(val, 0,position));
          String val[]=StringUtils.split("1701|2348023704657|39c7b51e-c068-4343-97ae-7c5b276219e5", "|");
          System.out.println("==== " + val[0] );
          System.out.println("==== " + val[1] );
          System.out.println("==== " + val[2] );
          
          String val2[]=StringUtils.split("BALANCE:58", ":");
          System.out.println("==== " + val2[0] );
          System.out.println("==== " + val2[1] );
          
        
//        System.out.println("====" + StringUtils.substring("08023991517" ,1));
//        System.out.println("====" + StringUtils.right("08023991517" ,10));
//        Delete del = new Delete();
//        try {
//            del.sendMessage(true, "Testing My Mail", "Will This Work", "ayojava@gmail.com");
//        } catch (MessagingException ex) {
//            System.out.println("Exception :: " + ex);
//        }
     }
}
