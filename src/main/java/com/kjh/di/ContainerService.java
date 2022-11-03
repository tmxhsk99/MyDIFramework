package com.kjh.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerService {

    /**
     * 파라미터로 전달한 객체 타입을 생성 및 필드를 주입하여 반환한다.
     *
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T getObject(Class<T> classType) {
        //전달 받은 객체 타입을 생성한다.
        T instance = createInstance(classType);
        //전달받은 객체 타입 의 필드를 확인한다.
        Arrays.stream(classType.getDeclaredFields())
                .forEach(f -> {
                    //inject 어노테이션이 있으면
                    if(f.getAnnotation(Inject.class) != null){
                        //해당 객체 인스턴스를 만든다.
                        Object fieldInstance = createInstance(f.getType());
                        //private도 접근 가능하도록 만든다..
                        f.setAccessible(true);
                        try {
                            f.set(instance,fieldInstance);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        return instance;
    }

    /**
     * 리플렉션을 활용한 인스턴스 생성
     *
     * @param classType
     * @param <T>
     * @return
     */
    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
