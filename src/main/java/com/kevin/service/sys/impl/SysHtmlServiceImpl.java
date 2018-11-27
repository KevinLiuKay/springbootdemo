package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysHtmlMapper;
import com.kevin.model.SysHtml;
import com.kevin.service.sys.ISysHtmlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kevin.service.Sequence;

import java.io.*;
import java.util.Calendar;
import java.util.List;
@Service
@Transactional
public class SysHtmlServiceImpl implements ISysHtmlService {
    @Value("${TEST_HTML_TEMPLATE_URL}")
    private String testHtmlTemplateUrl = "/springbootdemo/static/template/wx_template"; //html模板

    @Value("${TEST_UPLOAD_HTML_URL}")
    private String testUploadHtmlUrl = "/springbootdemo/static/savehtml/"; //html上传地址

    @Autowired
    private Sequence sequence;
    @Autowired
    private SysHtmlMapper sysHtmlMapper;
    @Override
    public int save(SysHtml record) {
        return 0;
    }

    @Override
    public int logicallyDeleteById(String id) {
        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public List<SysHtml> queryList(SysHtml sysHtml, String orderByClause) {
        return null;
    }

    @Override
    public SysHtml getById(String id) {
        return null;
    }

    @Override
    public int saveSysHtml(SysHtml sysHtml) {
        // 内容
        String content = sysHtml.getContent();
        // 是否有guid判断是新增还是编辑
        String guid = sysHtml.getGuid();
        String userId = "";
        if(null != HttpServletContext.getCurrentUser()){
            userId =  HttpServletContext.getCurrentUser().getUserId();
        }
        sysHtml.setRecordState(GlobalConstant.Y);
        // 上传模板
        String htmlTemplateUrl = "";
        // 上传路径
        String uploadHtmlUrl = "";
        htmlTemplateUrl  = testHtmlTemplateUrl;
        uploadHtmlUrl = testUploadHtmlUrl;
        try {
            // 内容生成静态html
            String title = "<h1 class='test_class'>"+sysHtml.getName()+"</h1>";
            // 读取模板文件
            FileInputStream fileinputstream = new FileInputStream(htmlTemplateUrl);
            int length = fileinputstream.available();
            byte bytes[] = new byte[length];
            fileinputstream.read(bytes);
            fileinputstream.close();
            String templateContent = new String(bytes);
            templateContent = templateContent.replaceAll("###title###", title);
            templateContent = templateContent.replaceAll("###content###", content);
            String pathUrl = string2File(templateContent, uploadHtmlUrl + sequence.getSequenceStr("key") + ".html");
            sysHtml.setUrl(pathUrl.substring(pathUrl.indexOf("static")-1));

            // 新增or编辑
            if(StringUtils.isNotBlank(guid)){
                sysHtml.setGuid(guid);
                sysHtml.setUpdateUserId(userId);
                sysHtml.setUpdateTime(Calendar.getInstance().getTime());
                throwException(sysHtmlMapper.updateByPrimaryKeyWithBLOBs(sysHtml),"更新html失败");
            }else{
                sysHtml.setGuid(UUIDUtil.getUUID());
                sysHtml.setCreateUserId(userId);
                sysHtml.setCreateTime(Calendar.getInstance().getTime());
                throwException(sysHtmlMapper.insert(sysHtml),"保存html失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return  GlobalConstant.ZERO;
        }
        return  GlobalConstant.ONE;
    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     * @param res 原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static String string2File(String res, String filePath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            //字符缓冲区
            char buf[] = new char[1024];
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }
    public void throwException(int result,String name){
        if (result == 0) {
            throw new RuntimeException(name);
        }
    }
}
