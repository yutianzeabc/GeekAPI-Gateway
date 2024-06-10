package cc.geektip.gateway.core.authorization.auth;

import cc.geektip.gateway.core.authorization.GatewayAuthorizingToken;
import cc.geektip.gateway.core.authorization.IAuth;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;


/**
 * @description: 鉴权服务
 * @author: Fish
 * @date: 2024/5/27
 */
@Slf4j
public class AuthService implements IAuth {
    private final Subject subject;

    public AuthService() {
        // 1. 获取 SecurityManager 工厂，此处使用 shiro.ini 配置文件初始化 SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2. 得到 SecurityManager 实例 并绑定给 SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 3. 得到 Subject 及 Token
        this.subject = SecurityUtils.getSubject();
    }

    @Override
    public boolean validate(String id, String token) {
        try {
            // 身份验证
            subject.login(new GatewayAuthorizingToken(id, token));
            // 返回结果
            return subject.isAuthenticated();
        } catch (AuthenticationException e) {
            // 身份验证失败
            log.error("Authentication failed: {}", e.getMessage());
            return false;
        } finally {
            // 退出
            subject.logout();
        }
    }
}
