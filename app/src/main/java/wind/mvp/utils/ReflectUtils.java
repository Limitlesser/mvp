package wind.mvp.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 */
@SuppressWarnings("unchecked")
public class ReflectUtils {

    /**
     * 获得超类的参数类型，取第一个参数类型
     *
     * @param clazz 超类类型
     */
    @SuppressWarnings("rawtypes")
    public static Class<?> getClassGenericType(final Class<?> clazz) {
        return getClassGenericType(clazz, 0);
    }

    /**
     * 根据索引获得超类的参数类型
     *
     * @param clazz 超类类型
     * @param index 索引
     */
    @SuppressWarnings("rawtypes")
    public static Class getClassGenericType(final Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static <T> T newInstance(Class<T> cls, Class<?>[] parameterTypes, Object[] args) {
        try {
            Constructor<T> constructor = cls.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("create instance failed");
    }
}  