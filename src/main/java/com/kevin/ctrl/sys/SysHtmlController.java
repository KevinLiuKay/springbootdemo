package com.kevin.ctrl.sys;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysHtml;
import com.kevin.model.SysUser;
import com.kevin.service.sys.ISysHtmlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysHtml")
@Api(value = "htmlManage", tags = "htmlManage")
public class SysHtmlController {
    private static final Logger logger = LoggerFactory.getLogger(SysHtmlController.class);
    @Autowired
    private ISysHtmlService sysHtmlService;

    @ApiOperation(value = "保存Html", notes = "保存Html", code = 200, produces = "application/json")
    @RequestMapping(value = "/saveHtml", method = RequestMethod.POST)
    public JsonResult saveHtml(SysHtml sysHtml){
        JsonResult jsonResult = new JsonResult();
        try {
            int result = sysHtmlService.saveSysHtml(sysHtml);
            if (GlobalConstant.ZERO !=  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }
}
