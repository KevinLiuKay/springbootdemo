package com.kevin;

import com.kevin.service.sys.ISysMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ISysMailServiceTest {
    @Autowired
    private ISysMailService mailService;
    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数
    /**
     * 发送简单邮件时发生异常！
     * org.springframework.mail.MailAuthenticationException: Authentication failed;
     * nested exception is javax.mail.AuthenticationFailedException: 535 Error: authentication failed
     *
     * application.properties文件中的password是授权码，而不是真的登录密码
     *
     *  com.sun.mail.util.MailConnectException: Couldn't connect to host, port: smtp.163.com , 25; timeout -1;
     *  连不上
     *  spring.mail.host=smtp.163.com 后面多了一个空格
     */
//    @Test
//    public void testSendSimpleMail() throws Exception {
//        MailService.sendSimpleMail("2823598901@qq.com","test simple mail"," hello this is simple mail");
//    }
//    @Test
//    public void testsendHtmlMail() {
//        MimeMessage message = null;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("2823598901@qq.com");
//            helper.setTo("2823598901@qq.com");
//            helper.setSubject("标题：发送Html内容");
//
//            StringBuffer sb = new StringBuffer();
//            sb.append("<h1>大标题-h1</h1>")
//                    .append("<p style='color:#F00'>红色字</p>")
//                    .append("<p style='text-align:right'>右对齐</p>");
//            helper.setText(sb.toString(), true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mailSender.send(message);
//    }
//    @Test
//    public void sendAttachmentsMail() {
//        MimeMessage message = null;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(Sender);
//            helper.setTo(Sender);
//            helper.setSubject("主题：带附件的邮件");
//            helper.setText("带附件的邮件内容");
//            //注意项目路径问题，自动补用项目路径
//            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
//            //加入邮件
//            helper.addAttachment("图片.jpg", file);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        mailSender.send(message);
//    }

    @Test
    public void testsendInlineMail() {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(Sender);
            helper.setSubject("主题：带静态资源的邮件");
            //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
            helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);

            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/assets/image/bd_logo1.png"));
            helper.addInline("picture",file);
        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }
    /**
     * @Description:发送模板邮件
     * @author:zoey
     * @date:2018年3月16日
     */
    @Test
    public void testsendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("user", "xmx");
        context.setVariable("web", "夏梦雪");
        context.setVariable("company", "测试公司");
        context.setVariable("product","梦想产品");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail("2823598901@qq.com","主题：这是模板邮件",emailContent);
    }
}
