package com.kevin.ctrl.sys;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.utils.JsonResult;
import com.kevin.ctrl.pub.CommonController;
import com.kevin.model.SysLog;
import com.kevin.model.ext.sys.SysLogExt;
import com.kevin.service.sys.ISysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lzk
 */
@RestController
@RequestMapping("/sysLog")
@Api(value = "logManage", tags = "logManage")
public class SysLogController extends CommonController {
	private static Logger logger = LoggerFactory.getLogger(SysLogController.class);
	@Autowired
	private ISysLogService logService;

	/**
	 * 查询登陆日志信息
	 * @param sysLog
	 * @return
	 */
	@RequestMapping( value ="/logList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult logList(SysLog sysLog,
								   @ApiParam(name = "pageNum", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
								   @ApiParam(name = "pageSize", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		JsonResult jsonResult = new JsonResult();

		PageHelper.startPage(pageNum, pageSize);
		List<SysLogExt> logList = logService.querySysLogExtList(sysLog);
		PageInfo<SysLogExt> pageInfo = new PageInfo<SysLogExt>(logList);
		jsonResult.setStatus(true);
		jsonResult.setModel(pageInfo);
		return jsonResult;
	}

}
