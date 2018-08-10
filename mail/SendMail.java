package mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail implements mailDefault {

	public boolean sendingMail(String to, String msg, String sub){

		Properties props = new Properties();
		String host = "smtp.gmail.com";
		props.setProperty("mail.host", host);
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		
		String from = "adproject200@gmail.com";
		String username = "adproject200@gmail.com";
		String password = "12345678987654321admin";
		
		 Authenticator auth = new Authenticator(){

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
			
		};
		
		Session session = Session.getInstance(props,
			      new javax.mail.Authenticator() {
			         protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(username, password);
			         }
			      });
		
		
		try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject(sub);

	         // Now set the actual message
	         message.setText(msg);

	         // Send message
	         Transport.send(message);

	         return true;

	      } catch (MessagingException e) {
	            return false;
	      }
	}
}
