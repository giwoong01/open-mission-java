# 오픈 미션 주제

“스프링 코어”를 직접 개발하여 2주차 미션이었던 자동차 경주 과제에 적용하기

---

스프링 프레임워크의 핵심인 스프링 코어(IoC/DI)의 동작 원리를 분석하고, 이를 직접 구현합니다.
직접 만든 프레임워크를 사용하여 기존의 '자동차 경주 게임'을 리팩토링함으로써, 제어의 역전(IoC)과 의존성 주입(DI)의 효과를 검증했습니다.

---

## 프로젝트 배경 및 목적

### 배경

우아한테크코스 프리코스 과정을 통해 객체지향 원칙(SRP, OCP 등)을 학습했습니다. 객체의 역할을 분리할수록 코드는 유연해졌지만,
객체를 생성하고 연결하는(new) 진입점 코드는 점점 복잡해졌습니다. 이 과정에서 "의존성 관리를 누가 해야 하는가?"에 대한 고민이 생겼고, 평소 사용하던 스프링 프레임워크가 이 문제를 어떻게 해결하는지 근본적인 궁금증을 가지게
되었습니다.

### 목적

- "스프링을 쓰는 개발자"에서 "스프링을 이해하는 개발자"로 성장하기
- 스프링 부트의 구동 원리를 디버거로 역추적하여 분석하기
- 리플렉션을 활용해 IoC 컨테이너(AppContext)를 직접 구현하기
- 프레임워크 수준에서의 다형성 지원 원리 체득하기

---

## 핵심 구현 내용

### 커스텀 어노테이션

스프링의 핵심 어노테이션을 정의합니다.

- `@Component`: IoC 컨테이너의 스캔 대상이 되는 클래스에 부여
- `@Autowired`: 의존성 주입이 필요한 필드에 부여

### IoC 컨테이너 (AppContext)

스프링의 ApplicationContext 역할을 수행하는 핵심 엔진입니다.

1. Component Scan: 지정된 패키지 하위의 모든 `.class` 파일을 탐색하여 `@Component`가 붙은 클래스를 찾습니다. (리플렉션 활용)
2. Bean Instantiation: 찾아낸 클래스의 기본 생성자를 호출하여 인스턴스를 생성하고 싱글톤 저장소에 등록합니다.
3. Dependency Injection: 생성된 Bean들의 필드를 검사하여 `@Autowired`가 붙은 경우, 알맞은 의존성을 찾아 주입합니다.

### 다형성 지원

초기 구현시 인터페이스 타입으로 의존성을 주입받지 못하는 문제가 있었습니다. 이를 해결하기 위해 Bean 조회 로직에 `Class.isAssignableFrom()` 을 도입하여,
인터페이스 타입으로 요청해도 해당 인터페이스를 구현한 구현체 Bean을 찾아 주입하도록 개선했습니다.

> Class.isAssignableFrom()이란?
>
> 어떤 클래스가 다른 클래스로부터 상속되었거나 인터페이스를 구현했는지 확인하는 메소드입니다.

---

## 프로젝트 구조

```
src
├── annotations                             # [Framework] 커스텀 어노테이션
│   ├── Autowired.java
│   └── Component.java
├── context                                 # [Framework] IoC 컨테이너 엔진
│   └── AppContext.java                     # 스캔, 생성, 주입 로직의 핵심
└── racingcar
    ├── Application.java
    ├── controller
    │   └── RacingcarController.java 
    ├── domain
    │   └── power
    │   │   ├── PowerGenerator.java         # 인터페이스
    │   │   └── RandomPowerGenerator.java   # 구현체 (@Component)
    │   ├── Car.java
    │   ├── Cars.java
    │   ├── Name.java
    │   ├── Position.java
    │   └── TryCount.java
    ├── util
    │   ├── Console.java
    │   └── Randoms.java
    └── view
        ├── InputView.java
        └── OutputView.java
```

## 실행 방법

이 프로젝트는 외부 라이브러리 없이 순수 Java로 구현되어 있습니다.

1. 프로젝트를 Clone 합니다.
2. src/racingcar/Application.java 파일을 엽니다.
3. main 메소드를 실행합니다.

```java
public static void main(String[] args) {
    try {
        String basePackage = "racingcar";

        AppContext appContext = new AppContext(basePackage);

        RacingcarController racingcarController = appContext.getBean(RacingcarController.class);
        racingcarController.run();
    } finally {
        Console.close();
    }
}
```

## 적용 결과

### 이전

개발자가 main 함수에서 모든 객체를 직접 생성하고 연결해야 했습니다.

```java
import racingcar.domain.power.PowerGenerator;

public static void main(String[] args) {
    RacingcarController racingcarController = new RacingcarController(
            new InputView(),
            new OutputView(),
            new PowerGenerator()
    );
    racingcarController.run();
}
```

### 이후

객체의 생성과 조립 책임이 AppContext로 이관되었습니다. 비즈니스 로직에서 new 키워드가 사라졌으며, 인터페이스 기반의 느슨한 결합이 가능해졌습니다.

```java
import annotations.Autowired;
import annotations.Component;
import racingcar.domain.power.PowerGenerator;

@Component
public class RacingcarController {
    @Autowired
    private PowerGenerator powerGenerator;
}
```

