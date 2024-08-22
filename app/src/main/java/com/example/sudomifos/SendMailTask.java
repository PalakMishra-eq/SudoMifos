package com.example.sudomifos;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTask extends AsyncTask<String, Void, Void> {

    private Context context;

    public SendMailTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String recipientEmail = params[0];
        String subject = params[1];
        String messageBody = params[2];

        final String username = "palakmishra170101@gmail.com"; // Your email address
        final String password = "@Ayu5678"; // Your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            Log.d("SendMailTask", "Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
