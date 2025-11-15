package context;

import java.util.HashMap;
import java.util.Map;

public class AppContext {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public AppContext() {
        try {
            // TODO: @Component 스캔 (구현 예정)

            // TODO: Bean 인스턴스 생성 및 등록 (구현 예정)

            // TODO: @Autowired 의존성 주입 (구현 예정)
        } catch (Exception e) {
            throw new RuntimeException("AppContext 초기화 실패", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object instance = beans.get(clazz);

        if (instance == null) {
            throw new RuntimeException(clazz.getName() + " 타입의 Bean을 찾을 수 없습니다.");
        }

        return (T) instance;
    }

}
