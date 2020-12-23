package com.GST.GSTWeb.GSTServiceProcessors;

import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	private static String mail_port = "587";
	private static String mail_host = "smtp.gmail.com";
	private static String mail_from = "gst.grandslamtennis@gmail.com";
	private static String mail_password = "kiqaiwbhbetlujsu";
	private static Properties emailProperties;
	private static Session mailSession;
	private static MimeMessage emailMessage;
	
	public static boolean send_email(String receiver_address, String email_subject, String msg_to_send) {
		
		boolean email_result;
		
		try {	
			setMailServerProperties();
			createEmailMessage(receiver_address, email_subject, msg_to_send);
			if(sendEmail())	email_result = true;
			else email_result = false;
		
		} catch(Exception e) {
			e.printStackTrace();
			email_result = false;
		}
		
		return email_result;
	}
	
	public static void setMailServerProperties() {

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", mail_port);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		//emailProperties.put("mail.debug", "true");
		//emailProperties.put("mail.smtp.ssl.enable", "true");
	}
	
	public static void createEmailMessage(String toEmails, String email_subj, String email_body){
		try{
			mailSession = Session.getDefaultInstance(emailProperties, null);
			
			emailMessage = new MimeMessage(mailSession);
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails));
			emailMessage.setSubject(email_subj);
			//emailMessage.setContent(email_body, "text/html");//for a html email
			emailMessage.setText(email_body);// for a text email
						
		} catch(Exception e){
			e.printStackTrace();
		}

	}

	private static boolean sendEmail(){
		
		try{
			Transport transport = mailSession.getTransport("smtp");
			
			transport.connect(mail_host, mail_from, mail_password);		
			transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
			transport.close();
			
			return true;
			
		} catch(Exception e){
			e.printStackTrace();			
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}



























