package com.hexun.boot;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@RequestMapping("/send")
	@ResponseBody
	public String send() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(username);
		message.setTo("YY@qq.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
		return "{\"result\":\"OK\"}";
    }
	
	@RequestMapping("/send2")
	@ResponseBody
	public String send2() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(username);
		helper.setTo("YY@qq.com");
		helper.setSubject("主题：嵌入静态资源");
		helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);
		FileSystemResource file = new FileSystemResource(new File("beaut.jpg"));
		helper.addInline("weixin", file);
		mailSender.send(mimeMessage);
		return "{\"result\":\"OK\"}";
    }
	@RequestMapping("/send3")
	@ResponseBody
	public String sendTemplateMail() throws MessagingException{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(username);
		helper.setTo("YY@qq.com");
		helper.setSubject("主题：模板邮件");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", "didi");
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "template.vm", "UTF-8", model);
		helper.setText(text, true);
		mailSender.send(mimeMessage);
		return "{\"result\":\"OK\"}";
	}
}
