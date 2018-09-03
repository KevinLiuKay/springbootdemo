package com.kevin.service.pub.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.common.utils.PictureUtil;
import com.kevin.exception.CommonException;
import com.kevin.model.PubFile;
import com.kevin.service.pub.IPubFileService;
import com.kevin.service.pub.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class UploadFileServiceImpl implements IUploadFileService {
    private static Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);
    @Autowired
    private IPubFileService pubFileService;

    /**
     * String getContentType()//获取文件MIME类型
     * InputStream getInputStream()//获取文件流
     * String getName() //获取表单中文件组件的名字
     * String getOriginalFilename() //获取上传文件的原名
     * long getSize()  //获取文件的字节大小，单位byte
     * boolean isEmpty() //是否为空
     * void transferTo(File dest) //保存到一个目标文件中。
     * @param file
     * @return
     */
    @Override
    public JsonResult uploadSinglePicture(MultipartFile file) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        if (file.isEmpty()) {
            jsonResult.setMessage("上传文件为空,请重新上传");
            return jsonResult;
        }
        PubFile pubFile = new PubFile();
        //获取文件类型
        String fileType = file.getContentType();
        logger.info("上传的文件MIME类型为：" + fileType);
        // 获取文件名
        String fileName = file.getOriginalFilename();
        pubFile.setFileName(fileName);
        logger.info("上传的文件名为：" + fileName);
        //获取文件大小
        long fileSize = file.getSize();
        logger.info("上传的文件大小为：" + fileSize);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        pubFile.setFileSuffix(suffixName);
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "D://testFileUpload//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            pubFile.setFilePath(filePath + fileName);
            int result = pubFileService.save(pubFile);
            if(GlobalConstant.ZERO != result){
                jsonResult.setStatus(true);
                jsonResult.setMessage("上传成功");
                String imgUrl = "data:image/jpeg;base64,"+ PictureUtil.imageToBase64Str(filePath + fileName).replaceAll("\r\n", "");
                pubFile.setFilePath(imgUrl);
                jsonResult.setModel(pubFile);
                return jsonResult;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonResult.setMessage("上传失败");
        return jsonResult;
    }
}
