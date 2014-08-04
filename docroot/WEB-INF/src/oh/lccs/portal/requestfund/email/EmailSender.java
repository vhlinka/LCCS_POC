package oh.lccs.portal.requestfund.email;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import oh.lccs.portal.requestfund.common.PropertiesLoader;

public class EmailSender {
	
	private static final String FROM = "noreply@lccs.com";
	
	public boolean sendEmail(String toAddress, String subject,String message){
		boolean status = false;
		Session session = Session.getInstance(PropertiesLoader.getPropertiesInstance());
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(FROM));
			InternetAddress[] address = {new InternetAddress(toAddress)};
			msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(message);
            Transport.send(msg);
            status = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return status;
		
	}

}
