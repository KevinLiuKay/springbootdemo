package com.kevin.service.sys;

import org.springframework.web.multipart.MultipartFile;

/**
 * 定义邮件发送的接口
 */
public interface ISysMailService {
    public void sendSimpleMail(String to, String subject, String content);
    public void sendHtmlMail(String to, String subject, String content);
    public void sendAttachmentsMail(String to, String subject,String content, String filePath);
    public void sendInlineMail(String to, String subject,String content);
    public void sendTemplateMail(String userName, String web, String company, String product, String to, String subject);
}
