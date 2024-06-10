package cc.geektip.gateway.core.bind;

import cc.geektip.gateway.core.executor.result.SessionResult;

import java.util.Map;

/**
 * @description: 统一泛化调用接口
 * @author: Fish
 * @date: 2024/5/24
 */
public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);

}
