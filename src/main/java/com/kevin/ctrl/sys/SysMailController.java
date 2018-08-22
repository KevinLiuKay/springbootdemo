package com.kevin.ctrl.sys;

import com.kevin.common.utils.JsonResult;
import com.kevin.service.sys.ISysMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

@RestController
@RequestMapping("/sysMail")
@Api(value = "mailManage", tags = "mailManage")
public class SysMailController {
    @Autowired
    private ISysMailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping(value="/sendSimpleMail" , method = RequestMethod.POST)
    @ApiOperation(value = "发送简单的邮件", notes = "发送简单的邮件", code = 200, produces = "application/json")
    public JsonResult sendSimpleMail(
            @ApiParam(name = "to",value = "收件人", required = true) @RequestParam(name = "to", required = true) String to,
            @ApiParam(name = "subject",value = "主题", required = true) @RequestParam(name = "subject", required = true) String subject,
            @ApiParam(name = "content",value = "内容", required = true) @RequestParam(name = "content", required = true) String content
            )
    {
        JsonResult jsonResult = new JsonResult();
        mailService.sendSimpleMail(to, subject, content);
        jsonResult.setMessage("发送邮件成功");
        jsonResult.setStatus(true);
        return jsonResult;
    }

    @RequestMapping(value="/sendHtmlMail" , method = RequestMethod.POST)
    @ApiOperation(value = "发送html的邮件", notes = "发送html的邮件", code = 200, produces = "application/json")
    public JsonResult  sendHtmlMail(
            @ApiParam(name = "to",value = "收件人", required = true) @RequestParam(name = "to", required = true) String to,
            @ApiParam(name = "subject",value = "主题", required = true) @RequestParam(name = "subject", required = true) String subject,
            @ApiParam(name = "content",value = "内容", required = true) @RequestParam(name = "content", required = true) String content)
    {
        JsonResult jsonResult = new JsonResult();
        mailService.sendHtmlMail(to, subject, content);
        jsonResult.setMessage("发送邮件成功");
        jsonResult.setStatus(true);
        return jsonResult;

    }

    @RequestMapping(value="/sendAttachmentsMail" , method = RequestMethod.POST)
    @ApiOperation(value = "发送带附件的邮件", notes = "发送带附件的邮件", code = 200, produces = "application/json")
    public JsonResult  sendAttachmentsMail(
            @ApiParam(name = "to",value = "收件人", required = true) @RequestParam(name = "to", required = true) String to,
            @ApiParam(name = "subject",value = "主题", required = true) @RequestParam(name = "subject", required = true) String subject,
            @ApiParam(name = "content",value = "内容", required = true) @RequestParam(name = "content", required = true) String content,
            @ApiParam(name = "filePath ",value = "文件", required = true) @RequestParam(name = "filePath ", required = true) String filePath )
    {
        JsonResult jsonResult = new JsonResult();
        mailService.sendAttachmentsMail(to, subject, content, filePath);
         //String filePath="C:\\Users\\lzk\\Desktop\\pom.xml";
        jsonResult.setMessage("发送邮件成功");
        jsonResult.setStatus(true);
        return jsonResult;
    }
    @RequestMapping(value="/sendInlineMail" , method = RequestMethod.POST)
    @ApiOperation(value = "发送带静态资源的的邮件", notes = "发送带静态资源的的邮件", code = 200, produces = "application/json")
    public JsonResult  sendInlineMail(
            @ApiParam(name = "to",value = "收件人", required = true) @RequestParam(name = "to", required = true) String to,
            @ApiParam(name = "subject",value = "主题", required = true) @RequestParam(name = "subject", required = true) String subject,
            @ApiParam(name = "content",value = "内容", required = true) @RequestParam(name = "content", required = true) String content)
    {
        JsonResult jsonResult = new JsonResult();
        mailService.sendInlineMail(to, subject, content);
        jsonResult.setMessage("发送邮件成功");
        jsonResult.setStatus(true);
        return jsonResult;
    }

    @RequestMapping(value="/sendTemplateMail" , method = RequestMethod.POST)
    @ApiOperation(value = "发送模板邮件", notes = "发送模板邮件", code = 200, produces = "application/json")
    public JsonResult sendTemplateMail(
            @ApiParam(name = "userName",value = "用户名", required = true) @RequestParam(name = "userName", required = true) String userName,
            @ApiParam(name = "web",value = "网络", required = true) @RequestParam(name = "web", required = true) String web,
            @ApiParam(name = "company",value = "公司", required = true) @RequestParam(name = "company", required = true) String company,
            @ApiParam(name = "product",value = "产品", required = true) @RequestParam(name = "product", required = true) String product,
            @ApiParam(name = "to",value = "收件人", required = true) @RequestParam(name = "to", required = true) String to,
            @ApiParam(name = "subject",value = "主题", required = true) @RequestParam(name = "subject", required = true) String subject){
        JsonResult jsonResult = new JsonResult();
        mailService.sendTemplateMail(userName, web, company,product,to,subject);
        jsonResult.setMessage("发送邮件成功");
        jsonResult.setStatus(true);
        return jsonResult;

    }

}
