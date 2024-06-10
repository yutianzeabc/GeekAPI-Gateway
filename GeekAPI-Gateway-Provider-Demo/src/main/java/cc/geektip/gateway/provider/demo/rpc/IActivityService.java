package cc.geektip.gateway.provider.demo.rpc;

import cc.geektip.gateway.provider.demo.rpc.dto.XReq;

/**
 * @description: 服务提供者Demo服务接口
 * @author: Fish
 * @date: 2024/6/10
 */
public interface IActivityService {

        String sayHi(String str);
        String insert(XReq req);
        String test(String str, XReq req);

}
