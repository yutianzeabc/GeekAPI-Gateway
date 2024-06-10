package cc.geektip.gateway.center;

import cc.geektip.gateway.center.domain.loadbalance.model.vo.RouteObjectVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @description:
 * @author: Fish
 * @date: 2024/6/9
 */
@SpringBootTest
public class JsonTest {
    @Test
    public void test() throws Exception {
        String json = "{\n" +
                "    \"group\": \"group2\",\n" +
                "    \"handle\": [\n" +
                "        {\n" +
                "            \"handler\": \"subroute\",\n" +
                "            \"routes\": [\n" +
                "                {\n" +
                "                    \"handle\": [\n" +
                "                        {\n" +
                "                            \"handler\": \"rewrite\",\n" +
                "                            \"strip_path_prefix\": \"/10002\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"handler\": \"reverse_proxy\",\n" +
                "                            \"load_balancing\": {\n" +
                "                                \"selection_policy\": {\n" +
                "                                    \"policy\": \"least_conn\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"upstreams\": [\n" +
                "                                {\n" +
                "                                    \"dial\": \"192.168.1.102:9001\"\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"dial\": \"192.168.1.102:9002\"\n" +
                "                                }\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"match\": [\n" +
                "        {\n" +
                "            \"path\": [\n" +
                "                \"/api01/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        RouteObjectVO routeObject = objectMapper.readValue(json, RouteObjectVO.class);

        File file = new File("test.json");
        objectMapper.writeValue(file, routeObject);
    }
}
