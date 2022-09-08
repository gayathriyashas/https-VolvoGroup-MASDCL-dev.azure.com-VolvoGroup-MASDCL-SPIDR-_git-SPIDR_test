package com.volvo.project.components.outlook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.Thread;


import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;

import org.apache.commons.codec.binary.Base64;
import com.testautomationguru.utility.PDFUtil;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

public class OutlookEmail1 {
    private static final Logger log = LoggerFactory.getLogger(OutlookEmail1.class);

    public void checkEmails(String userEmailId, String password) throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.partialfetch", "false");
        props.put("mail.mime.base64.ignoreerrors", "true");
        props.setProperty("mail.imap.ssl.trust", "*");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);

        Session mailSession = Session.getInstance(props);
        mailSession.setDebug(true);
        Store store = mailSession.getStore("imap");
        store.connect("outlook.office365.com",userEmailId,password);
        //store.connect("smtp.live.com",userEmailId,password);
        Thread.sleep(5000);
        //Thread.sleep(5000);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        System.out.println("Total Message:" + folder.getMessageCount());
        System.out.println("Unread Message:" + folder.getUnreadMessageCount());

        //messages = folder.getMessages();

        Message[] messages = folder.getMessages();
        System.out.println("messages.length---" + messages.length);

        for (Message mail : messages) {
            if (!mail.isSet(Flags.Flag.SEEN)) {

                System.out.println("***************************************************");
                System.out.println("MESSAGE : \n");

                System.out.println("Subject: " + mail.getSubject());
                System.out.println("From: " + mail.getFrom()[0]);
                System.out.println("To: " + mail.getAllRecipients()[0]);
                System.out.println("Date: " + mail.getReceivedDate());
                System.out.println("Size: " + mail.getSize());
                System.out.println("Flags: " + mail.getFlags());
                System.out.println("ContentType: " + mail.getContentType());
                System.out.println("Body: \n" + getEmailBody(mail));
                System.out.println("Has Attachments: " + hasAttachments(mail));
            }
        }
       // folder.close();
    }

    public boolean hasAttachments(Message email) throws Exception {

        // suppose 'message' is an object of type Message
        String contentType = email.getContentType();
        System.out.println(contentType);

        if (contentType.toLowerCase().contains("multipart/mixed")) {
            // this message must contain attachment
            Multipart multiPart = (Multipart) email.getContent();

            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    System.out.println("Attached filename is:" + part.getFileName());

                    MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                    String fileName = mimeBodyPart.getFileName();

                    String destFilePath = System.getProperty("user.dir") + "\\Resources\\";

                    File fileToSave = new File(fileName);
                    mimeBodyPart.saveFile(destFilePath + fileToSave);

                    // download the pdf file in the resource folder to be read by PDFUTIL api.

                    PDFUtil pdfUtil = new PDFUtil();
                    String pdfContent = pdfUtil.getText(destFilePath + fileToSave);

                    System.out.println("******---------------********");
                    System.out.println("\n");
                    System.out.println("Started reading the pdfContent of the attachment:==");


                    System.out.println(pdfContent);

                    System.out.println("\n");
                    System.out.println("******---------------********");

                    Path fileToDeletePath = Paths.get(destFilePath + fileToSave);
                    Files.delete(fileToDeletePath);
                }
            }

            return true;
        }

        return false;
    }

    public String getEmailBody(Message email) throws IOException, MessagingException {

        String line, emailContentEncoded;
        StringBuffer bufferEmailContentEncoded = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));
        while ((line = reader.readLine()) != null) {
            bufferEmailContentEncoded.append(line);
        }

        System.out.println("**************************************************");

        System.out.println(bufferEmailContentEncoded);

        System.out.println("**************************************************");

        emailContentEncoded = bufferEmailContentEncoded.toString();

        if (email.getContentType().toLowerCase().contains("multipart/related")) {

            emailContentEncoded = emailContentEncoded.substring(emailContentEncoded.indexOf("base64") + 6);
            emailContentEncoded = emailContentEncoded.substring(0, emailContentEncoded.indexOf("Content-Type") - 1);

            System.out.println(emailContentEncoded);

            String emailContentDecoded = new String(new Base64().decode(emailContentEncoded.toString().getBytes()));
            return emailContentDecoded;
        }

        return emailContentEncoded;

    }
}
