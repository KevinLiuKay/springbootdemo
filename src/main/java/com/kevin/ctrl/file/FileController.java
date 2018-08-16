package com.kevin.ctrl.file;

import com.github.pagehelper.PageHelper;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.ExportUtil;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysUser;
import com.kevin.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/file")
@Api(value = "fileManage", tags = "fileManage")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    protected static Object[] USER_PARAMS = {"userName","userAcc","userGender","userPhone","userAddr"};
    protected static String[] USER_PARAMNAMES = new String[]{"用户名","用户账号","性别","手机号","地址"};
    protected static String USER_NAME = "用户列表";
    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation(value = "单个附件上传", notes = "单个附件上传", code = 200, produces = "application/json")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload( @RequestParam(name = "file", required = false) MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "D://testFileUpload//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @ApiOperation(value = "多个附件上传", notes = "多个附件上传", code = 200, produces = "application/json")
    @RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
    public String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        if(files != null && !files.isEmpty()){
            for (int i = 0; i < files.size(); ++i) {
                file = files.get(i);
                String filePath = "D://testFileUpload//";
                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        stream = new BufferedOutputStream(new FileOutputStream(
                                new File(filePath + file.getOriginalFilename())));//设置文件路径及名字
                        stream.write(bytes);// 写入
                        stream.close();

                    } catch (Exception e) {
                        stream = null;
                        return "第 " + i + " 个文件上传失败 ==> " + e.getMessage();
                    }
                } else {
                    return "第 " + i + " 个文件上传失败因为文件为空";
                }
            }
            return "上传成功";
        }
        return "上传附件为空";
    }

    @RequestMapping(value = "/excelModelDownload",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "springbootExcel模板下载", notes = "springbootExcel模板下载", code = 200, produces = "application/json")
    public JsonResult modelDownload(HttpServletRequest request, HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        jsonResult.setMessage(GlobalConstant.UPLOAD_FAIL);
        try {
            response.setContentType("application/octet-stream;charset=iso-8859-1");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", new String("springbootExcel.xls".getBytes("gb2312"), "iso-8859-1" )));
            InputStream myStream = this.getClass().getResourceAsStream("/static/download/springbootExcel.xls");
            IOUtils.copy(myStream, response.getOutputStream());
            // 客户端不缓存
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.getOutputStream().flush();
            response.getOutputStream().close();
            jsonResult.setStatus(true);
            jsonResult.setMessage(GlobalConstant.UPLOAD_SUCCESSED);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMessage(GlobalConstant.UPLOAD_FAIL);
        }
        return jsonResult;
    }

    @RequestMapping(value = "/importUser" ,method = RequestMethod.POST)
    @ApiOperation(value = "导入用户表", notes = "导入用户表", code = 200, produces = "application/json")
    public JsonResult importUser(
            @ApiParam(name = "file", required = false) @RequestParam("file") MultipartFile file) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        if (file != null) {
            try {
                jsonResult.setStatus(true);
                jsonResult.setModel(sysUserService.batchInsertUserByExcel(file));
            } catch (Exception e) {
                logger.debug("-----> Exception : " + e.getMessage());
                jsonResult.setStatus(false);
                jsonResult.setMessage(GlobalConstant.EXCEPTION_MSG + "_" + e.getMessage());
            }
        }
        return jsonResult;
    }
    @RequestMapping(value = "/exportUserListExcel",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出用户表信息Excel", notes = "导出用户表信息Excel", code = 200, produces = "application/json")
    public void exportUserListExcel(SysUser sysUser,
                           @ApiParam(name = "pageNum",value = "当前页", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(name = "pageSize",value = "每页的条数", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        //获取数据
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> userList = sysUserService.queryList(sysUser,"create_time asc");
        ExportUtil.BookResult result = ExportUtil.exportDataInObj(userList, USER_PARAMS,USER_PARAMNAMES);
        result.getErrorInfo();
        HttpServletResponse response = HttpServletContext.getResponse();
        ExportUtil.exportExcel(response, result.getWorkBook(), USER_NAME);
    }


}
