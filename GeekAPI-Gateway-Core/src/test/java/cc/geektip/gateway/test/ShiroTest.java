package cc.geektip.gateway.test;

import cc.geektip.gateway.core.authorization.IAuth;
import cc.geektip.gateway.core.authorization.JwtUtil;
import cc.geektip.gateway.core.authorization.auth.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Shiro 测试类
 * @author: Fish
 * @date: 2024/5/26
 */
@Slf4j
public class ShiroTest {


    @Test
    public void test_auth_service() {
        IAuth auth = new AuthService();
        boolean validate = auth.validate("fish", "1yJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmaXNoIiwiZXhwIjoxNzE3NDAxNjYyLCJpYXQiOjE3MTY3OTY4NjIsImtleSI6IkZpc2gifQ.RnRj5njjlF1O42o65hIcP4EePHzN8WnJeLu2sk8ly-w");
        System.out.println(validate ? "验证成功" : "验证失败");
    }

    @Test
    public void test_jwt() throws JOSEException, ParseException {
        String issuer = "fish";
        long ttlMillis = 7 * 24 * 60 * 60 * 1000L;
        Map<String, Object> claims = new HashMap<>();
        claims.put("key", "Fish");

        // 编码
        String token = JwtUtil.encode(issuer, ttlMillis, claims);
        System.out.println(token);

        // 解码
        var parser = JwtUtil.decode(token);
        System.out.println(parser.getSubject());
    }

    @Test
    public void test_shiro() {
        // 1. 获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:test-shiro.ini");

        // 2. 得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        // 3. 得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();

        // 4. 默认提供的验证方式；UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken("fish", "123");

        try {
            //5.1、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5.2、身份验证失败
            System.out.println("身份验证失败");
        }

        System.out.println(subject.isAuthenticated() ? "验证成功" : "验证失败");

        // 6. 退出
        subject.logout();
    }

}
