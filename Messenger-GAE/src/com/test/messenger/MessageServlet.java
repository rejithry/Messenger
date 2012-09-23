package com.test.messenger;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class MessageServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		String message = req.getParameter("message");
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = message;

        HttpSession ses = req.getSession(false);  
        if(ses == null) {
    		resp.getWriter().println("You are not logged in");
    		resp.getWriter().println("<br><a href=\"login.html\">Click here to login</a>");  
        }
        else{
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("rejith.ry@gmail.com", "Rejith Raghavan"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("rejith.ry@gmail.com", "Mr. Rejith"));
            msg.setSubject("New message from " + ses.getValue("name"));
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        }
		resp.getWriter().println("Message sent");
		resp.getWriter().println("<br><a href=\"message.html\">Click here to send another message</a>");  
		resp.getWriter().println("<br><a href=\"Logout\">Click here to logout</a>"); 
	}
	}
}
