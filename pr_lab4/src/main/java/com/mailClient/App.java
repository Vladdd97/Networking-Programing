package com.mailClient;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static final String USERNAME = "vladbantus02@gmail.com";
    public static final String PASSWORD = "***";
    public static final String TO = "vladislav.bantus@faf.utm.md";

    public static void main(String[] args) {


        System.out.println("\n" +
                "Enter 1 for reading messages" +
                "\nEnter 2 for sending message" +
                "\n\n\n\tYour choice: ");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();


        executeOption(choice);

    }

    public static void executeOption(int choice){

        Session session;
        switch (choice) {
            case 1:

                // 3. Creating mail session.
                session = Session.getDefaultInstance(getPOP3Properties(), createAuthenticatorObject());
                try {
                    EmailUtil.readEmails(session, USERNAME, PASSWORD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                // 3. Creating mail session.
                session = Session.getDefaultInstance(getSMTPProperties(), createAuthenticatorObject());
                Scanner in = new Scanner(System.in);

                System.out.println("Enter the subject:");
                String subject = in.nextLine();

                System.out.println("Enter the content:");
                String content = in.nextLine();
                EmailUtil.sendImageEmail(session, TO, subject, content,
                        "D:\\Programs\\_about\\selfDevelopment\\pr_lab4\\src\\main\\resources\\images\\image.jpg");
                break;

            default:
                System.out.println("wrong input");
        }

    }

    public static Properties getPOP3Properties() {
        // 1. Setup properties for the mail session.
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

    public static Properties getSMTPProperties() {
        // 1. Setup properties for the mail session.
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

        // 2. Creates a javax.mail.Authenticator object.
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        return auth;
    }


}
