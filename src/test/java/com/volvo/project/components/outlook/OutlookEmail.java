package com.volvo.project.components.outlook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.mail.*;

/**
 * Connects to email server with credentials provided to read from a given
 * folder of the email application
 *
 */


public class OutlookEmail {
    private static final Logger log = LoggerFactory.getLogger(OutlookEmail.class);

    public void checkEmails(String userEmailId, String password) throws Exception {
        Properties props = System.getProperties();
        /*props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.partialfetch", "false");
        props.put("mail.mime.base64.ignoreerrors", "true"); */



        Session mailSession = Session.getInstance(props,null);
        mailSession.setDebug(true);
        Store store = mailSession.getStore("imap");
        //store.connect("outlook.office365.com", userEmailId, password);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.live.com", 587, userEmailId, password);
        //transport.sendMessage(msg, msg.getAllRecipients());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        System.out.println("Total Message:" + folder.getMessageCount());
        System.out.println("Unread Message:" + folder.getUnreadMessageCount());

        //messages = folder.getMessages();
        Message[] messages = folder.getMessages();
        System.out.println("messages.length---" + messages.length);

        for (Message mail : messages) {

            System.out.println("*********************************");
            System.out.println("MESSAGE : \n");

            System.out.println("Subject: " + mail.getSubject());
            System.out.println("From: " + mail.getFrom()[0]);
            System.out.println("To: " + mail.getAllRecipients()[0]);
            System.out.println("Date: " + mail.getReceivedDate());
            System.out.println("Size: " + mail.getSize());
            System.out.println("Flags: " + mail.getFlags());
            System.out.println("ContentType: " + mail.getContentType());
           // System.out.println("Body: \n" + getEmailBody(mail));
            System.out.println("Text: " + mail.getContent().toString());
            System.out.println("*******************************");

        }
    }

}
