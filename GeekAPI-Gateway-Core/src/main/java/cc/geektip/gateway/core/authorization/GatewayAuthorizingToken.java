package cc.geektip.gateway.core.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description: 网关鉴权 Token
 * @author: Fish
 * @date: 2024/5/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayAuthorizingToken implements AuthenticationToken, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String uId;

    // JSON WEB TOKEN
    private String jwt;

    @Override
    public Object getPrincipal() {
        return uId;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

}
