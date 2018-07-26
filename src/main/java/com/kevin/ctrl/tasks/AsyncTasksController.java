package com.kevin.ctrl.tasks;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.service.tasks.AsyncTasks;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asyncTasks")
@Api(value = "asyncTasksManage", tags = "asyncTasksManage")
public class AsyncTasksController {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTasksController.class);

    @Autowired
    private AsyncTasks asyncTasks;

    @ApiOperation(value = "查看异步任务", notes = "查看异步任务", code = 200, produces = "application/json")
    @RequestMapping(value="/asyncTaskTest" , method = RequestMethod.POST)
    public JsonResult asyncTaskTest(){
        JsonResult jsonResult = new JsonResult();
        try {
            System.out.println("开始执行Controller任务");
            long start = System.currentTimeMillis();
            asyncTasks.doTaskOne();
            asyncTasks.doTaskTwo();
            asyncTasks.doTaaskThree();
            long end = System.currentTimeMillis();
            System.out.println("完成Controller任务，耗时：" + (end - start) + "毫秒");
            jsonResult.setStatus(true);
            jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }

        return jsonResult;
    }
}
