package cc.geektip.gateway.core.authorization;

/**
 * @description:
 * @author: Fish
 * @date: 2024/5/27
 */
public interface IAuth {

    /**
     * 身份验证
     * @param id 用户ID
     * @param token 用户Token
     * @return 是否验证通过
     */
    boolean validate(String id, String token);

}
