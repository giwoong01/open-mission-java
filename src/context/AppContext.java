package context;

import annotations.Autowired;
import annotations.Component;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AppContext {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public AppContext(String basePackage) {
        try {
            // @Component 스캔
            Set<Class<?>> componentClasses = findComponentClasses(basePackage);

            // Bean 인스턴스 생성 및 등록
            instantiateBeans(componentClasses);

            // @Autowired 의존성 주입
            injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException("AppContext 초기화 실패", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object instance = findBean(clazz);

        if (instance == null) {
            instance = beans.get(clazz);
        }

        if (instance == null) {
            throw new RuntimeException(clazz.getName() + " 타입의 Bean을 찾을 수 없습니다.");
        }

        return (T) instance;
    }

    private Set<Class<?>> findComponentClasses(String basePackage) throws Exception {
        Set<Class<?>> componentClasses = new HashSet<>();

        String path = basePackage.replace('.', '/');

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        URL resourceUrl = contextClassLoader.getResource(path);

        if (resourceUrl == null) {
            throw new RuntimeException("패키지가 존재하지 않습니다. -" + basePackage);
        }

        File baseDir = new File((resourceUrl.toURI()));

        scanClasses(baseDir, basePackage, componentClasses);

        return componentClasses;
    }

    private void scanClasses(File directory, String packageName, Set<Class<?>> componentClasses) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanClasses(file, packageName + "." + file.getName(), componentClasses);
            } else if (file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                String fullClassName = packageName + "." + className;

                Class<?> clazz = Class.forName(fullClassName);

                if (clazz.isAnnotationPresent(Component.class)) {
                    componentClasses.add(clazz);
                }
            }
        }
    }

    private void instantiateBeans(Set<Class<?>> componentClasses) {
        for (Class<?> clazz : componentClasses) {
            if (clazz.isInterface()) {
                continue;
            }

            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object instance = constructor.newInstance();

                beans.put(clazz, instance);
                registerInterfaces(clazz, instance);
            } catch (Exception e) {
                throw new RuntimeException(clazz.getName() + " Bean 생성 실패", e);
            }
        }
    }

    private void injectDependencies() {
        for (Class<?> clazzKey : beans.keySet()) {
            Object beanInstance = beans.get(clazzKey);
            Class<?> clazz = beanInstance.getClass();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> fieldType = field.getType();

                    Object dependencyInstance = findBean(fieldType);

                    if (dependencyInstance == null) {
                        throw new RuntimeException(fieldType.getName() + " 타입의 Bean을 찾을 수 없습니다.");
                    }

                    try {
                        field.setAccessible(true);
                        field.set(beanInstance, dependencyInstance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(clazz.getName() + " Bean 의존성 주입 실패", e);
                    }
                }
            }
        }
    }

    private void registerInterfaces(Class<?> clazz, Object instance) {
        for (Class<?> i : clazz.getInterfaces()) {
            if (beans.containsKey(i) && beans.get(i) != instance) {
                throw new IllegalStateException(
                        i.getName() + " 타입에 대해 여러 개의 Bean이 존재합니다: " +
                                beans.get(i).getClass().getName() + ", " + clazz.getName()
                );
            }
            beans.put(i, instance);
        }
    }

    private Object findBean(Class<?> type) {
        Object bean = beans.get(type);
        if (bean != null) {
            return bean;
        }

        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            if (type.isAssignableFrom(entry.getKey()) && !entry.getKey().isInterface()) {
                return entry.getValue();
            }
        }

        return null;
    }
    
}
