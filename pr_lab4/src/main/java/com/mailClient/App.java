package com.mailClient;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Hello world!
 */
public class App {

    public static final String USERNAME = "***";
    public static final String PASSWORD = "***";
    public static final String TO = "vladislav.bantus@faf.utm.md";

    public static void main(String[] args) {
//        System.out.println( "Hello World!" );
//        try {
//            ReadEmail.getEmails();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //SSLEmail.sendEmailViaSSl();

        Session session = Session.getDefaultInstance(getPOP3Properties(), createAuthenticatorObject());
        System.out.println("Session created");
        try {
            EmailUtil.readEmails(session,USERNAME,PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties getPOP3Properties() {
        Properties props = new Properties();
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.fallback", "false");
        props.put("mail.pop3.socketFactory.port", "995");
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.host", "pop.gmail.com");
        props.put("mail.pop3.user", USERNAME);
        props.put("mail.store.protocol", "pop3");

        return props;
    }

    public static Properties getSMTPProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        return props;
    }

    public static Authenticator createAuthenticatorObject() {

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        return auth;
    }


}
