package com.jerry.framework.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * spring邮件整合
 * @Class Name SpringMailSender
 * @Author likang
 * @Create In 2015-2-12
 */
public class SpringMailSender {
	
	public static String from = null;
	public static String host = null;
	public static String username = null;
	public static String pwd = null;
	public static JavaMailSenderImpl sender = new JavaMailSenderImpl();
	
	static {
        host = "mail.easou.com";
		username = "support@staff.easou.com";
		from = "support@staff.easou.com";
		pwd = "easou8888";
		sender.setDefaultEncoding("UTF-8");
		sender.setHost(host);
		sender.setUsername(username);
		sender.setPassword(pwd);
	}
	
	
	public static void sendSimpleMail(String[] receiver,String title,String content) {
		SimpleMailMessage mail = new SimpleMailMessage();
	     try {
	        mail.setTo(receiver);
	        mail.setFrom(from);
	        mail.setSubject(title);
	        mail.setText(content);
	        sender.send(mail);
	     } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void sendSimpleMail(String receiver,String title,String content) {
		SimpleMailMessage mail = new SimpleMailMessage();
	     try {
	        mail.setTo(receiver);
	        mail.setFrom(from);
	        mail.setSubject(title);
	        mail.setText(content);
	        sender.send(mail);
	     } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void sendHtmlMail(String[] receiver,String title,String content) {
		 try {
			  MimeMessage msg = sender.createMimeMessage();
			  MimeMessageHelper mmh = new MimeMessageHelper(msg, true, "UTF-8");
			  mmh.setTo(receiver);
			  mmh.setFrom(from);
			  mmh.setSubject(title);
			  mmh.setText("<html><body>"+content+"</body></html>", true);
			  sender.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void sendHtmlMail(String receiver,String title,String content) {
	  try {
			  MimeMessage msg = sender.createMimeMessage();
			  MimeMessageHelper mmh = new MimeMessageHelper(msg, true, "UTF-8");
			  mmh.setTo(receiver);
			  mmh.setFrom(from);
			  mmh.setSubject(title);
			  mmh.setText("<html><body>"+content+"</body></html>", true);
			  sender.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
