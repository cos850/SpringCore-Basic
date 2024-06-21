


### 싱글톤 패턴이란?

<aside>
💡 클래스의 인스턴스가 단 1개만 생성되는 디자인 패턴

</aside>
<br />

클래스의 인스턴스가 2개 이상 생성되지 않도록 해야한다.

- 외부에서 인스턴스를 생성할 수 없도록 생성자를 private 로 선언한다
- 내부에서 인스턴스를 딱 1개만 생성하고, 외부에서 요청하면 동일한 인스턴스를 반환한다.

<br /><br />

- **싱글톤 예시1 - 어플리케이션이 실행될 때 생성**

```java
public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    // 외부에서 요청 시 초기화해둔 동일한 인스턴스 반환
    public static SingletonService getInstance() {
        return instance;
    }
    
    // 외부에서 생성하지 못하도록 막음
    private SingletonService() {}
    
    public void logic() {
        System.out.println("싱글톤 서비스를 호출하였습니다.");
    }
    
}
```
<br /><br />

- **싱글톤 예시2 - 실제로 해당 클래스를 처음 참조할 때 인스턴스 생성**

```java
package hello.core.singleton;

public class SingletonService {

    private static SingletonService instance;

    // 외부에서 처음 실제로 참조할 때 인스턴스를 생성하여 반환
    public static SingletonService getInstance() {
        if(instance == null) {
            instance = new SingletonService();
        }

        return instance;
    }

    // 외부에서 생성하지 못하도록 막음
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 서비스를 호출하였습니다.");
    }

}

```

### 장점

- 객체의 인스턴스가 단 하나만 메모리에 올라가기 때문에 메모리를 낭비하지 않는다
- 이미 만들어져있는 인스턴스를 참조만 하기 때문에 매번 새로 만드는 것 보다 속도가 빠르다
- 다른 객체와 공유할 데이터가 있는 경우 쉽게 공유할 수 있다 (동시성 문제가 생길 수 있음)
  
<br />

### 단점

- 클라이언트가 구체 클래스에 의존한다. (DIP 위반)
- private 생성자이므로 자식 클래스를 만들기 어렵다.
- Multi Thread에서 동시성 문제가 생길 수 있다.
    - 스프링 빈은 항상 무상태로 설계해야 한다.
    - 상수, 지역변수 등을 이용해야 하며, 클래스 레벨에서 필드를 선언할 경우 ThreadLocal 등을 사용해야 한다.