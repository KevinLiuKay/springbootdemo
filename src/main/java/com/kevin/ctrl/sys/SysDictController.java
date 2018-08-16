package com.kevin.ctrl.sys;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.enums.sys.DictTypeEnum;
import com.kevin.model.SysDict;
import com.kevin.model.ext.sys.DictTypeExt;
import com.kevin.service.sys.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author lzk
 */
@RestController
@RequestMapping("/dict")
@Api(value = "dictionaryManage", tags = "dictionaryManage")
public class SysDictController {
	
	private static Logger logger = LoggerFactory.getLogger(SysDictController.class);
	
	@Resource
	private ISysDictService dictService;

	@RequestMapping(value ="/dictTypeList", method = RequestMethod.POST)
	@ApiOperation(value = "查询字典类型", notes = "查询字典类型", code = 200, produces = "application/json")
	public JsonResult dictTypeList() {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		List<DictTypeExt> dictTypeList = new ArrayList<>();
		for (DictTypeEnum dictTypeEnum : DictTypeEnum.values()) {
			DictTypeExt dictTypeExt = new DictTypeExt();
			dictTypeExt.setId(dictTypeEnum.getId());
			dictTypeExt.setName(dictTypeEnum.getName());
			dictTypeList.add(dictTypeExt);
		}
		jsonResult.setStatus(true);
		jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
		jsonResult.setModel(dictTypeList);
		return jsonResult;
	}


	@RequestMapping(value="/dictList", method = RequestMethod.POST)
	@ApiOperation(value = "根据字典类型Id查询字典", notes = "根据字典类型Id查询字典", code = 200, produces = "application/json")
	public JsonResult dictList(SysDict sysDict,
			@ApiParam(name = "pageNum", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
			@ApiParam(name = "pageSize", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		String dictTypeId=  sysDict.getDictTypeId();
		if(StringUtils.isNotBlank(dictTypeId)){
			PageHelper.startPage(pageNum, pageSize);
			List<SysDict> sysDictList = dictService.queryList(sysDict, GlobalConstant.SORT_KEY_CREATE_TIME_DESC);
			PageInfo<SysDict> pageInfo = new PageInfo<SysDict>(sysDictList);
			jsonResult.setStatus(true);
			jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
			jsonResult.setModel(pageInfo);
		}
		return jsonResult;
	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存字典", notes = "保存字典", code = 200, produces = "application/json")
	public JsonResult save(SysDict sysDict) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		int result = dictService.save(sysDict);
		if(GlobalConstant.ZERO != result){
			jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
			jsonResult.setStatus(true);
			return jsonResult;
		}
		return jsonResult;
	}

	@RequestMapping(value="/modifyState", method=RequestMethod.POST)
	@ApiOperation(value = "使用/禁用字典", notes = "使用/禁用字典", code = 200, produces = "application/json")
	public JsonResult modifyState(String dictId, String recordState) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		if(dictId != null){
			SysDict sysDict = new SysDict();
			sysDict.setDictId(dictId);
			sysDict.setRecordState(recordState);
			int result = dictService.save(sysDict);
			if(GlobalConstant.ZERO != result){
				jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
				jsonResult.setStatus(true);
				return jsonResult;
			}
		}
		return jsonResult;
	}

	@RequestMapping(value="/checkUnique", method = RequestMethod.POST)
	@ApiOperation(value = "检查唯一性", notes = "检查唯一性", code = 200, produces = "application/json")
	public JsonResult checkUnique(SysDict sysDict) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(false);
		List<SysDict> sysDictList = dictService.checkUnique(sysDict);
		if(sysDictList != null && !sysDictList.isEmpty()){
			jsonResult.setStatus(false);
			return jsonResult;
		}
		jsonResult.setStatus(true);
		return jsonResult;
	}

}
