package cc.geektip.gateway.center;

import cc.geektip.gateway.center.application.IConfigManageService;
import cc.geektip.gateway.center.application.IRegisterManageService;
import cc.geektip.gateway.center.domain.manage.aggregates.ApplicationSystemRichInfo;
import cc.geektip.gateway.center.domain.manage.model.vo.GatewayServerVO;
import cc.geektip.gateway.center.domain.message.Publisher;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationSystemVO;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description: API 网关中心测试类
 * @author: Fish
 * @date: 2024/5/28
 */
@Slf4j
@SpringBootTest
public class ApiTest {

    @Resource
    private IConfigManageService configManageService;

    @Resource
    private IRegisterManageService registerManageService;
    
    @Test
    public void test_queryGatewayServerList() {
        List<GatewayServerVO> gatewayServerVOS = configManageService.queryGatewayServerList();
        log.info("测试结果：{}", JSON.toJSONString(gatewayServerVOS));
    }

    @Test
    public void test_registerGatewayServerNode() {
        configManageService.registerGatewayServerNode("10001", "api-gateway-g1", "电商支付网关", "127.0.0.196");
        configManageService.registerGatewayServerNode("10001", "api-gateway-g2", "电商支付网关", "127.0.0.197");
        configManageService.registerGatewayServerNode("10001", "api-gateway-g3", "电商配送网关", "127.0.0.198");
    }

    @Test
    public void test_registerApplication() {
        ApplicationSystemVO applicationSystemVO = new ApplicationSystemVO();
        applicationSystemVO.setSystemId("api-gateway-test");
        applicationSystemVO.setSystemName("网关测试系统");
        applicationSystemVO.setSystemType("RPC");
        applicationSystemVO.setSystemRegistry("127.0.0.1");
        registerManageService.registerApplication(applicationSystemVO);
    }

    @Test
    public void test_registerApplicationInterface() {
        ApplicationInterfaceVO applicationInterfaceVO = new ApplicationInterfaceVO();
        applicationInterfaceVO.setSystemId("api-gateway-test");
        applicationInterfaceVO.setInterfaceId("cc.geektip.gateway.rpc.IActivityBooth");
        applicationInterfaceVO.setInterfaceName("活动平台");
        applicationInterfaceVO.setInterfaceVersion("v1.0.0");
        registerManageService.registerApplicationInterface(applicationInterfaceVO);
    }

    @Test
    public void test_registerApplicationInterfaceMethod() {
        ApplicationInterfaceMethodVO applicationInterfaceVO01 = new ApplicationInterfaceMethodVO();
        applicationInterfaceVO01.setSystemId("api-gateway-test");
        applicationInterfaceVO01.setInterfaceId("cc.geektip.gateway.rpc.IActivityBooth");
        applicationInterfaceVO01.setMethodId("sayHi");
        applicationInterfaceVO01.setMethodName("测试方法");
        applicationInterfaceVO01.setParameterType("java.lang.String");
        applicationInterfaceVO01.setUri("/wg/activity/sayHi");
        applicationInterfaceVO01.setHttpCommandType("GET");
        applicationInterfaceVO01.setAuth(0);
        registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO01);

        ApplicationInterfaceMethodVO applicationInterfaceVO02 = new ApplicationInterfaceMethodVO();
        applicationInterfaceVO02.setSystemId("api-gateway-test");
        applicationInterfaceVO02.setInterfaceId("cc.geektip.gateway.rpc.IActivityBooth");
        applicationInterfaceVO02.setMethodId("insert");
        applicationInterfaceVO02.setMethodName("插入方法");
        applicationInterfaceVO02.setParameterType("cc.geektip.gateway.rpc.dto.XReq");
        applicationInterfaceVO02.setUri("/wg/activity/insert");
        applicationInterfaceVO02.setHttpCommandType("POST");
        applicationInterfaceVO02.setAuth(1);
        registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO02);
    }

    @Test
    public void test_queryApplicationSystemRichInfo(){
        ApplicationSystemRichInfo result = configManageService.queryApplicationSystemRichInfo("api-gateway-g4", "api-gateway-test-provider");
        log.info("测试结果：{}", JSON.toJSONString(result));
    }

    @Resource
    private Publisher publisher;

    @Test
    public void test_messages() throws InterruptedException {
        publisher.pushMessage("api-gateway-g4", "api-gateway-test-provider");
        Thread.sleep(50000);
    }

}
