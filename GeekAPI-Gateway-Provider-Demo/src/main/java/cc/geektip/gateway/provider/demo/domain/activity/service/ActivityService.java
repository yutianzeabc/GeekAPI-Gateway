package cc.geektip.gateway.provider.demo.domain.activity.service;

import cc.geektip.gateway.provider.demo.rpc.IActivityService;
import cc.geektip.gateway.provider.demo.rpc.dto.XReq;
import cc.geektip.gateway.sdk.annotation.ApiProducerClazz;
import cc.geektip.gateway.sdk.annotation.ApiProducerMethod;
import com.alibaba.fastjson2.JSON;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description: 服务提供者Demo服务接口实现
 * @author: Fish
 * @date: 2024/6/10
 */
@DubboService(version = "1.0.0")
@ApiProducerClazz(interfaceName = "活动服务", interfaceVersion = "1.0.0")
public class ActivityService implements IActivityService {

    @Override
    @ApiProducerMethod(methodName = "探活方法", uri = "/wg/activity/sayHi", httpCommandType = "GET", auth = 0)
    public String sayHi(String str) {
        return "Hello, " + str + "by GeekAPI-Gateway-Provider-Demo!";
    }

    @Override
    @ApiProducerMethod(methodName = "插入方法", uri = "/wg/activity/insert", httpCommandType = "POST", auth = 1)
    public String insert(XReq req) {
        return "OK, insert: " + JSON.toJSONString(req) + "by GeekAPI-Gateway-Provider-Demo!";
    }

    @Override
    @ApiProducerMethod(methodName = "测试方法", uri = "/wg/activity/test", httpCommandType = "POST", auth = 0)
    public String test(String str, XReq req) {
        return "OK, test: " + str + JSON.toJSONString(req) + "by GeekAPI-Gateway-Provider-Demo!";
    }

}
