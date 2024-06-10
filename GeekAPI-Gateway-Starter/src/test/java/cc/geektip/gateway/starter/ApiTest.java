package cc.geektip.gateway.starter;

import cc.geektip.gateway.starter.common.Result;
import cc.geektip.gateway.starter.domain.model.aggregates.ApplicationSystemRichInfo;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
class ApiTest {

    @Test
    public void test_register_gateway() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", "10001");
        paramMap.put("gatewayId", "api-gateway-g4");
        paramMap.put("gatewayName", "电商配送网关");
        paramMap.put("gatewayAddress", "127.0.0.1");

        String resultStr = HttpUtil.post("http://localhost:8001/wg/admin/config/registerGateway", paramMap, 500);
        log.info(resultStr);

        Result result = JSON.parseObject(resultStr, Result.class);
        log.info(result.getCode());
    }

    @Test
    public void test_pullApplicationSystemRichInfo() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gatewayId", "api-gateway-g4");
        String resultStr = HttpUtil.post("http://localhost:8001/wg/admin/config/queryApplicationSystemRichInfo", paramMap, 500);
        Result<ApplicationSystemRichInfo> result = JSON.parseObject(resultStr, new TypeReference<>() {
        });
        log.info(JSON.toJSONString(result.getData()));
    }

}