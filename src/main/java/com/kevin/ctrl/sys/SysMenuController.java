package com.kevin.ctrl.sys;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysMenu;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.service.sys.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/saveMenu" , method = RequestMethod.POST)
    @ApiOperation(value = "保存菜单", notes = "保存菜单", code = 200, produces = "application/json")
    public JsonResult saveMenu(SysMenu menu) {
        JsonResult jsonResult = new JsonResult();
        try {
            //区分一级菜单
            if(StringUtils.isBlank(menu.getMenuParentId())) {
                menu.setMenuParentId(GlobalConstant.STRING_ZERO);
            }
            int result = sysMenuService.save(menu);
            if (GlobalConstant.ZERO != result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;

    }

    @RequestMapping(value = "/deleteMenu" , method = RequestMethod.POST)
    @ApiOperation(value = "删除菜单", notes = "删除菜单", code = 200, produces = "application/json")
    public JsonResult deleteMenu(@ApiParam(name = "menuId", required = true) @RequestParam(name = "menuId", required = true) String menuId) {
        JsonResult jsonResult = new JsonResult();
        try{
            int result = sysMenuService.logicallyDeleteById(menuId);
            if (GlobalConstant.ZERO != result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.DELETE_SUCCESSED);
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }
}
