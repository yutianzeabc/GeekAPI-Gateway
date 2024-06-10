package cc.geektip.gateway.core.authorization;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @description: JWT 工具类
 * @author: Fish
 * @date: 2024/5/27
 */
public class JwtUtil {

    private static final String signingKey = "*%$^GeekTip!0123456789!GeekTip^$%*";

    /**
     * 生成 JWT Token 字符串
     * @param subject   签发对象
     * @param ttlMillis 有效期
     * @param claims    额外信息
     * @return Token
     */
    public static String encode(String subject, long ttlMillis, Map<String, Object> claims) throws JOSEException {
        // 签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建 JWT Claims
        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issueTime(now);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            claimsBuilder.expirationTime(exp);
        }

        if (claims != null) {
            claims.forEach(claimsBuilder::claim);
        }

        JWTClaimsSet claimsSet = claimsBuilder.build();

        // 创建 JWS 头部
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // 创建签名的 JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // 创建 HMAC 签名器
        JWSSigner signer = new MACSigner(signingKey);

        // 签名
        signedJWT.sign(signer);

        // 生成序列化的 JWT
        return signedJWT.serialize();
    }

    /**
     * 解析 JWT Token
     * @param token JWT Token
     * @return Claims
     */
    public static JWTClaimsSet decode(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        // 创建 HMAC 验证器
        JWSVerifier verifier = new MACVerifier(signingKey);

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("JWT Verification Failed");
        }

        return signedJWT.getJWTClaimsSet();
    }

}
