# Laboratory work nr.4 (PR)  
## Task: create a program in which you will use POP3 and SMTP protocols 
  
## About SMTP and POP3 protocols

**POP** and  **SMTP**  are the two protocols which take care of the **email flow** (send and receive respectively) on the internet.

SMTP (“Simple Mail Transfer Protocol”) is used for sending and delivering from a client to a server via port 25: it’s the  **outgoing server**. On the contrary, POP (“Post Office Protocol”) allows the user to pick up the message and download it into his own inbox: it’s the  **incoming server**


## Task Implementation  
  
I used SMTP protocol for sending emails from client to server.  
***EmailUtil.java*** class, **sendImageEmail()** method.
```java
    public static void sendImageEmail(Session session, String toEmail, String subject, String 
    body, String filename) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            try {
                msg.setFrom(new InternetAddress(REPLY, PERSONAL));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            msg.setReplyTo(InternetAddress.parse(REPLY, false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);

            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>" +
                "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            //Set the multipart message to the email message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with image!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
```

I used POP3 protocols to download emails from server.  
***EmailUtil.java*** class, **readEmails()** method.
```java
    public static void readEmails(Session session, String username, String password) throws 
    Exception {

        // 4. Get the POP3 store provider and connect to the store.
        Store store = session.getStore("pop3");
        store.connect("pop.gmail.com", username, password);

        // 5. Get folder and open the INBOX folder in the store.
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // 6. Retrieve the messages from the folder.
        Message[] messages = inbox.getMessages();
        for (Message message: messages) {
            //message.writeTo(System.out);
            Address[] fromAddress = message.getFrom();
            String from = fromAddress[0].toString();
            String subject = message.getSubject();
            String toList = parseAddresses(message
                .getRecipients(Message.RecipientType.TO));
            String ccList = parseAddresses(message
                .getRecipients(Message.RecipientType.CC));
            String sentDate = message.getSentDate().toString();


            String messageContent = getTextFromMimeMultipart((MimeMultipart) 
            message.getContent());
            // print out details of each message
            System.out.println("\n==============================");
            System.out.println("\t From: " + from);
            System.out.println("\t To: " + toList);
            System.out.println("\t CC: " + ccList);
            System.out.println("\t Subject: " + subject);
            System.out.println("\t Sent Date: " + sentDate);
            System.out.println("\t Message: " + messageContent);
            System.out.println("\n==============================\n");

        }


        // 7. Close folder and close store.
        inbox.close(false);
        store.close();
    }
```

