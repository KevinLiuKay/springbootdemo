package com.kevin.ctrl.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.Assessment;
import com.kevin.service.WeChat.IAssessmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/assessment")
@Api(value = "assessmentManage", tags = "assessmentManage")
public class AssessmentController {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);
    @Autowired
    private IAssessmentService assessmentService;

    @ApiOperation(value = "查询考核项目（可按条件查询）", notes = "查询考核项目（可按条件查询）", code = 200, produces = "application/json")
    @RequestMapping(value = "/assessmentList", method = {RequestMethod.POST,RequestMethod.GET})
    public JsonResult assessmentList(Assessment assessment,
                               @ApiParam(name = "pageNum",value = "当前页", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @ApiParam(name = "pageSize",value = "每页的条数", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        JsonResult jsonResult = new JsonResult();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Assessment> list = assessmentService.queryList(assessment,"sort_num asc");
            PageInfo<Assessment> pageInfo = new PageInfo<Assessment>(list);
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
}
