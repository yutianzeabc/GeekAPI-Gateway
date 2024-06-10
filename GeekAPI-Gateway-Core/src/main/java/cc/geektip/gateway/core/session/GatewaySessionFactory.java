package cc.geektip.gateway.core.session;

/**
 * @description:
 * @author: Fish
 * @date: 2024/5/25
 */
public interface GatewaySessionFactory {
    /**
     * 打开网关会话
     * @param uri uri
     * @return 网关会话
     */
    GatewaySession openSession(String uri);

}
