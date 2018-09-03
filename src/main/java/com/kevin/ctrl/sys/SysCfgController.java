package com.kevin.ctrl.sys;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.listener.ServletContextListenerImpl;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysCfg;
import com.kevin.model.SysRole;
import com.kevin.service.sys.ISysCfgService;
import com.kevin.service.sys.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lzk
 */
@RestController
@RequestMapping("/sysCfg")
@Api(value = "sysConfigurationManage", tags = "sysConfigurationManage")
public class SysCfgController  {
	
	private static Logger logger = LoggerFactory.getLogger(SysCfgController.class);
	
	@Autowired
	private ISysCfgService cfgService;
    @Autowired
    private ISysRoleService roleService;

	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	@ApiOperation(value = "查询系统配置", notes = "查询系统配置", code = 200, produces = "application/json")
	public JsonResult queryList(){
		SysCfg cfg = new SysCfg();
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		List<SysCfg> sysCfgList = cfgService.queryList(cfg, null);
		Map<String, String> sysCfgMap = new HashMap<String, String>();
		Map<String, String> sysCfgDescMap = new HashMap<String, String>();
		for(SysCfg sysCfg:sysCfgList ){
			if(StringUtils.isNotBlank(sysCfg.getCfgDesc())){
				sysCfgDescMap.put(sysCfg.getCfgCode(), sysCfg.getCfgDesc());
			}
			sysCfgMap.put(sysCfg.getCfgCode(), sysCfg.getCfgValue());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sysCfgDescMap",sysCfgDescMap);
		resultMap.put("sysCfgMap",sysCfgMap);
		//加载角色
		List<SysRole> roleList = roleService.queryList(new SysRole(), null);
		resultMap.put("roleList", roleList);
		jsonResult.setStatus(true);
		jsonResult.setModel(resultMap);
		return jsonResult;
	}

	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "保存系统配置", notes = "保存系统配置", code = 200, produces = "application/json")
	public JsonResult save(HttpServletRequest request, String list){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		jsonResult.setMessage(GlobalConstant.SAVE_FAIL);
		//把JSON格式的字符串转为List
		List<SysCfg> cfglist = new Gson().fromJson(list, new TypeToken<List<SysCfg>>(){}.getType());
		int result = cfgService.saveSysCfg(cfglist);
		if(GlobalConstant.ZERO != result) {
			jsonResult.setStatus(true);
			jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
		}
		return jsonResult;
	}

	@RequestMapping(value="/doRefresh" , method={RequestMethod.POST})
	@ApiOperation(value = "刷新服务器", notes = "刷新服务器", code = 200, produces = "application/json")
	public JsonResult doRefresh(HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		///ServletRequest的getServletContext方法是Servlet3.0添加的
		//return InitializedContext.refresh(request.getServletContext());
		String result = ServletContextListenerImpl.refresh(request.getSession().getServletContext());
		if(GlobalConstant.OPERATE_SUCCESSED.equals(result)) {
			jsonResult.setStatus(true);
			jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
		}
		return jsonResult;
	}
}