package cc.geektip.gateway.core.authorization;

import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @description: 网关鉴权领域
 * @author: Fish
 * @date: 2024/5/27
 */
public class GatewayAuthorizingRealm extends AuthorizingRealm {

    @Override
    public Class<?> getAuthenticationTokenClass() {
        return GatewayAuthorizingToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 暂时不做授权处理
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try {
            // 验证解析是否报错
            JWTClaimsSet claims = JwtUtil.decode(((GatewayAuthorizingToken) token).getJwt());
            // 验证签发人是否匹配
            if (!token.getPrincipal().equals(claims.getSubject())) throw new AuthenticationException("Subject mismatch");
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), this.getName());
    }

}
