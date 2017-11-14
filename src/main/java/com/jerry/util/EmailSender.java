package com.jerry.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * <p>邮件发送工具
 * @author wushuo
 * @version Id EmailSend.java
 * @since 2012-3-5     
 */
public class EmailSender {
	
    private static final String charset = "GBK";
    private static final String defaultMimetype = "text/plain";


    /**
     * 发送文件
     * @param receiver 接收人
     * @param subject  主题
     * @param mailContent 邮件内容
     * @param mimetype 类型
     */
    public static void send(String receiver, String subject, String mailContent, String mimetype) {
    	send(new String[]{receiver}, subject, mailContent, mimetype);
    	
    } 

    /**
     * 发送邮件
     * @param receivers 收件人
     * @param subject 标题
     * @param mailContent 邮件内容
     * @param mimetype 内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
     */
    public static void send(String[] receivers, String subject, String mailContent, String mimetype) {
    	send(receivers, subject, mailContent, null, mimetype);
    }
    
    /**
     * 发送邮件
     * @param receivers 收件人
     * @param subject 标题
     * @param mailContent 邮件内容
     * @param attachements 附件
     * @param mimetype 内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
     */
    public static void send(String[] receivers, String subject, String mailContent, File[] attachements, String mimetype) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.easou.com");//mail服务器地址
        props.put("mail.smtp.auth", "true");//需要校验
        props.put("mail.smtp.localhost", "127.0.0.1");
        SimpleDateFormat formcat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startSendTime = 0L;
        //默认Session在热部署重启时有问题
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getProperties("email"),getProperties("emailPassword"));//登录用户名/密码
            }
        });
       // session.setDebug(true);
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(getProperties("email")));//发件人邮件

            InternetAddress[] toAddress = new InternetAddress[receivers.length];
            for (int i=0; i<receivers.length; i++) {
                toAddress[i] = new InternetAddress(receivers[i]);
            }
            mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);//收件人邮件
            //mimeMessage.setRecipients(Message.RecipientType.CC, toAddress);//抄送

            mimeMessage.setSubject(subject, charset);
            
            Multipart multipart = new MimeMultipart();
            //正文
            MimeBodyPart body = new MimeBodyPart();
           // body.setText(message, charset);不支持html
            body.setContent(mailContent, (mimetype!=null && !"".equals(mimetype) ? mimetype : defaultMimetype)+ ";charset="+ charset);
            multipart.addBodyPart(body);//发件内容
            //附件
            if(attachements!=null){
	            for (File attachement : attachements) {
	                MimeBodyPart attache = new MimeBodyPart();
	               //ByteArrayDataSource bads = new ByteArrayDataSource(byte[],"application/x-any");
	                attache.setDataHandler(new DataHandler(new FileDataSource(attachement)));
	                String fileName = getLastName(attachement.getName());
	                attache.setFileName(MimeUtility.encodeText(fileName, charset, null));
	                multipart.addBodyPart(attache);
	            }
            }
            mimeMessage.setContent(multipart);
            mimeMessage.setSentDate(new Date());//formcat.parse("2010-5-23")
            StringBuilder receiversBuilder = new StringBuilder();
            for(String receiver : receivers){
                receiversBuilder.append(receiver);
                receiversBuilder.append(";");
            }
            startSendTime = System.nanoTime();
            Transport.send(mimeMessage);
        } catch (Exception e) {
        	/**处理人员离职后，邮件地址不存在整体发送失败的问题  by likang begin*/
        	if (e instanceof SendFailedException) {
        		//无效的地址
        		Address[] addresses = ((SendFailedException) e).getInvalidAddresses();
        		List<String> addressList = new ArrayList(Arrays.asList(receivers));
        		//遍历删除无效地址
        		for (int i = 0; i < addressList.size(); i++) {
					for (int j = 0; j < addresses.length; j++) {
						//删除无效的mail地址
						if (addresses[j] != null && addresses[j].toString().equals(addressList.get(i).toString())) {
                            addressList.remove(i);
                        }
					}
				}
        		//将所有有效的地址再次发送
        		if (!addressList.isEmpty()) {
        			String[] newAddress = new String[addressList.size()];
                    send(addressList.toArray(newAddress), subject, mailContent, attachements, mimetype);
				}
            	/**处理人员离职后，邮件地址不存在整体发送失败的问题  by likang end*/
        		e.printStackTrace();
			}
            e.printStackTrace();
        }
    }
    
    private static String getLastName(String fileName) {
        int pos = fileName.lastIndexOf("\\");
        if (pos > -1) {
            fileName = fileName.substring(pos + 1);
        }
        pos = fileName.lastIndexOf("/");
        if (pos > -1) {
            fileName = fileName.substring(pos + 1);
        }
        return fileName;
    }
    
	public static String getProperties(String parameterName) {
		Properties prop = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
		   try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String value =  prop.getProperty(parameterName);
		return value;
	}
	
    public static void main(String[] args) throws Exception {
    	String[] a = {"a@a.com","apollo_li@staff.easou.com"};
		EmailSender.send(a, "1", "1", null);
    	
    }
}
