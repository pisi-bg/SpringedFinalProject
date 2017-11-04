package com.example.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.model.pojo.User;

public class EmailSender {
	private static String host = "smtp.gmail.com";
	private static String port = "465";
	private static String from = "pisi.bg.shop@gmail.com";
	private static String pass = "pisi.bg1";

	private static String subject = "www.pisi.bg !!!";

	public static void toPromotion(String email, long productId) {
		Properties props = new Properties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		Message msg = new MimeMessage(session);
		try {

			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			msg.setSubject("Нови изкушаващи промоций в www.pisi.bg !!!");

			String emailText = "<strong>Здравей, имаме нова отстъпка на любим твой продукт. Тук можеш да разгледаш:</strong>"
					+ "<a href=\"http://localhost:8080/ProjectPisi/products/productdetail/productId/" + productId
					+ "\"> Продукт" + "</a><br>"
					+ "<strong>Екипът на pisi.bg ти пожелава приятно пазаруваме в нашият сайт.</strong> <br><br>"
					+ "<img src=\"http://media.pennlive.com/midstate_impact/photo/dog-paw-genericjpg-829eca230b8dc4f1.jpg\" width=\"100px\" heigth=\"auto\" ></a>";
			msg.setContent(emailText, "text/html; charset=utf-8");

			Transport transport = session.getTransport("smtps");
			transport.connect(host, from, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void passwordTo(User u) {
		Properties props = new Properties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		Message msg = new MimeMessage(session);
		try {

			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
			msg.setSubject(subject);

			String emailText = String.format(
					"<h2>Здравей, приятелю на домашните любимци!</h2><br>"
							+ "Неприятно е да си забравиш паролата, затова от екипа на pisi.bg проявяваме разбиране и предоставяме тази информация за теб.<br>"
							+ "Твоите данни са:<br><br>" + "<strong>email:</strong> %s <br>"
							+ "<strong>password:</strong> %s<br><br>"
							+ "<strong>Ето линк, където да се логнеш с новата си парола, след което можеш да я смениш: http://localhost:8080/ProjectPisi/user/login </strong><br>"
							+ "<h3>Екипът на нашия сайт, ти пожелава приятно пазаруване!</h3>"
							+ "<a href=\"http://localhost:8080/ProjectPisi/index\">"
							+ " <img src=\"http://media.pennlive.com/midstate_impact/photo/dog-paw-genericjpg-829eca230b8dc4f1.jpg\" width=\"100px\" heigth=\"auto\" >"
							+ "<h4 style=\"text-decoration: none;\">HI FIVE!!!</h4>" + "</a>",
					u.getEmail(), u.getPassword());

			msg.setContent(emailText, "text/html; charset=utf-8");

			Transport transport = session.getTransport("smtps");
			transport.connect(host, from, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void contactUs(User u, String subject, String describe) {
		Properties props = new Properties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		Message msg = new MimeMessage(session);
		try {

			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress("pisi.bg.shop@gmail.com"));
			msg.setSubject(subject);

			String emailText = String.format("Потребител на име " + u.getFirstName() + " и мейл " + u.getEmail()
					+ " се свърза с нас с въпрос: <br>" + describe);

			msg.setContent(emailText, "text/html; charset=utf-8");

			Transport transport = session.getTransport("smtps");
			transport.connect(host, from, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}