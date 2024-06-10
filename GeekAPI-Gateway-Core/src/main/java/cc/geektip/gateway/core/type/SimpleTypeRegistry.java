package cc.geektip.gateway.core.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 简单类型注册表
 * @author: Fish
 * @date: 2024/5/26
 */
public class SimpleTypeRegistry {

    private static final Set<String> SIMPLE_TYPE_SET = new HashSet<>();

    static {
        SIMPLE_TYPE_SET.add(String.class.getName());
        SIMPLE_TYPE_SET.add(Byte.class.getName());
        SIMPLE_TYPE_SET.add(Short.class.getName());
        SIMPLE_TYPE_SET.add(Character.class.getName());
        SIMPLE_TYPE_SET.add(Integer.class.getName());
        SIMPLE_TYPE_SET.add(Long.class.getName());
        SIMPLE_TYPE_SET.add(Float.class.getName());
        SIMPLE_TYPE_SET.add(Double.class.getName());
        SIMPLE_TYPE_SET.add(Boolean.class.getName());
        SIMPLE_TYPE_SET.add(Date.class.getName());
        SIMPLE_TYPE_SET.add(Class.class.getName());
        SIMPLE_TYPE_SET.add(BigInteger.class.getName());
        SIMPLE_TYPE_SET.add(BigDecimal.class.getName());
    }

    private SimpleTypeRegistry() {
        // Prevent Instantiation
    }

    /**
     * 简单类型检查
     * @param clazz 类名
     * @return 是否为简单类型
     */
    public static boolean isSimpleType(String clazz) {
        return SIMPLE_TYPE_SET.contains(clazz);
    }

}
