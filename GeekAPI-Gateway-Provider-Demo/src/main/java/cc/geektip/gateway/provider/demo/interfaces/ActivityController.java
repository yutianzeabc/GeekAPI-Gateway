package cc.geektip.gateway.provider.demo.interfaces;

import cc.geektip.gateway.provider.demo.common.Result;
import cc.geektip.gateway.provider.demo.domain.activity.service.ActivityService;
import cc.geektip.gateway.provider.demo.rpc.dto.XReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 活动服务HTTP控制器（用于直连测试）
 * @author: Fish
 * @date: 2024/6/12
 */

@RestController
@RequestMapping("/direct/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @GetMapping("/sayHi")
    public Result sayHi(@RequestParam String str) {
        String ret = activityService.sayHi(str);
        return Result.buildSuccess(ret);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody XReq req) {
        String ret = activityService.insert(req);
        return Result.buildSuccess(ret);
    }

    @PostMapping("/test")
    public Result test(@RequestParam String str, @RequestBody XReq req) {
        String ret = activityService.test(str, req);
        return Result.buildSuccess(ret);
    }

}
