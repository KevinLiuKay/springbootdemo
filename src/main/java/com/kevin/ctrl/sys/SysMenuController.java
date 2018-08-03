package com.kevin.ctrl.sys;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysMenu;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.service.sys.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author lzk
 */
@RestController
@RequestMapping("/menu")
@Api(value = "menuManage", tags = "menuManage")
public class SysMenuController {
    private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "查询菜单树", notes = "查询菜单树", code = 200, produces = "application/json")
    @RequestMapping(value = "/queryMenuTree", method = RequestMethod.POST)
    public JsonResult queryMenuTree(SysMenu sysMenu){
        JsonResult jsonResult = new JsonResult();
        try {
            List<SysMenuExt> menuTree = sysMenuService.queryMenuTree(sysMenu);
            jsonResult.setStatus(true);
            jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
            jsonResult.setModel(menuTree);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

}
