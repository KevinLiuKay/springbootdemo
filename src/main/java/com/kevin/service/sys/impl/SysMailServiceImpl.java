package com.kevin.service.sys.impl;

import com.kevin.service.sys.ISysMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysMailServiceImpl implements ISysMailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;  //自动注入的Bean
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String sender;//读取配置文件中的参数

    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e.getMessage());
        }

    }

    /**
     * 发送Html邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);   //true表示需要创建一个multipart message
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功!");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！"+e.getMessage());
        }

    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("带附件的邮件已经发送!");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！"+e.getMessage());
        }
    }

    @Override
    public void sendInlineMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
            StringBuffer sb = new StringBuffer();
            sb.append("<html><body>")
                    .append(content)
                    .append("图片:<img src='cid:picture' /></body></html>");
            helper.setText(sb.toString(), true);
            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/assets/image/bd_logo1.png"));
            helper.addInline("picture",file);
            mailSender.send(message);
            logger.info("带静态资源的邮件已经发送!");
        } catch (MessagingException e) {
            logger.error("发送带静态资源的的邮件时发生异常!"+e.getMessage());
        }

    }

    @Override
    public void sendTemplateMail(String userName, String web, String company, String product, String to, String subject) {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("user", userName);
        context.setVariable("web", web);
        context.setVariable("company", company);
        context.setVariable("product",product);
        String emailContent = templateEngine.process("emailTemplate", context);
        sendHtmlMail(to, subject, emailContent);
    }
}
