package com.kevin.ctrl.aop;

import com.github.pagehelper.PageInfo;
import com.kevin.aop.RequestLimit;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysUser;
import com.kevin.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/timerAndTimer")
@Api(value = "timeAndTimerManage", tags = "timeAndTimerManage")
public class RequestLimitController {
    private static final Logger logger = LoggerFactory.getLogger(RequestLimitController.class);

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation(value = "3秒防刷新测试", notes = "3秒防刷新测试", code = 200, produces = "application/json")
    @RequestMapping(value = "/timeAndTimerTask", method = RequestMethod.POST)
    @RequestLimit(count = 3)
    public JsonResult timeAndTimerTask(SysUser sysUser, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try {
            List<SysUser> userList = sysUserService.queryList(sysUser,"create_time asc");
            PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(userList);
            jsonResult.setStatus(true);
            jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
            jsonResult.setModel(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value="/hello", method = RequestMethod.POST)
    @RequestLimit(count = 3)
    @ApiOperation(value = "3秒防刷新测试2", notes = "3秒防刷新测试2", code = 200, produces = "application/json")
    public String hello(HttpServletRequest request) {
        return "Hello World";
    }
}
