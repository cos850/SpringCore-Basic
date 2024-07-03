ComponentScan
---

`@Component` 가 지정된 클래스를 찾아서 자동으로 등록해주는 역할이다.

`@controller`, `@Service`, `@Repository` 또한 포함한다. <br />

> [!TIP] 어노테이션 간에 상속 기능은 Java에서 지원하지 않음. 이것은 Spring에서 지원하는 기능이다.
> - `@Controller`: Spring MVC 컨트롤러로 인식
> - `@Repository`: Spring 데이터 접근 계층으로 인식, 데이터 계층의 예외를 Spring 예외로 변환해준다!
> - `@Service`: 별다른 기능은 없지만 개발자들끼리 여기에서 핵심 비즈니스 로직을 처리하는구나~ 하고 인식하는데 도움을 준다.

<br />

---
### SpringBoot의 ComponentScan

`@SpringBootApplication` 내부를 확인해보면 이미 ComponentScan을 기본으로 제공한다.

```java
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
  // ...생략
}
```

- `excludeFilters`: 빈으로 등록하지 않을 조건을 지정한다.
- `includeFilters`: 빈으로 등록할 조건을 지정한다.
  - FilterType : `includeFilters`, `excludeFilters`에서 사용되는 `FilterType`은 5가지 옵션이 있다.
    - `ANNOTATION`: 기본값, 어노테이션을 인식하여 동작
    - `ASSIGNABLE_TYPE`: 지정한 타입과 자식 타입을 인식해서 동작 (특정 클래스를 지정할 때 사용할 수 있다)
    - `ASPECTJ`: AspectJ 패턴 사용
    - `REGEX`: 정규 표현식
    - `CUSTOM`: `TypeFilter` 인터페이스를 구현해서 처리 (코드 형식으로 조건을 정할 수 있다)
- `basePackages`
  - `@Component`를 찾을 위치를 지정한다. 
  - 지정한 위치 하위의 모든 파일이 대상이 된다.
  - 지정하지 않으면 기본으로 `@ComponentScan`이 지정된 패키지로 설정된다.

<br />

---
### Custom Include/ExcludeFilters
`excludeFilters`, `includeFilters`를 마음대로 변경할 수 있다! <br />
참고 코드: [exclude/includeFilter test](..%2Fsrc%2Ftest%2Fjava%2Fhello%2Fcore%2Fscan%2Ffilter)

위의 참고 코드를 요약해서 보면
1. 빈으로 등록할 어노테이션을 만든다.
2. 빈에서 제외할 어노테이션을 만든다.
3. 1-2에서 만든 어노테이션을 원하는 클래스에 적용한다.
4. `ComponentScan`의 `includeFilters`, `excludeFilters`에 지정한다.


```java
// 1. 빈으로 등록할 어노테이션을 만든다.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}


// 2. 빈에서 제외할 어노테이션을 만든다.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}


// 3. 1-2에서 만든 어노테이션을 원하는 클래스에 적용한다.
@MyIncludeComponent
public class BeanA {
}

@MyExcludeComponent
public class BeanB {
}


// 4. `ComponentScan`의 `includeFilters`, `excludeFilters`에 지정한다.
public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
    BeanA beanA = ac.getBean(BeanA.class);
    assertThat(beanA).isNotNull();

    assertThatThrownBy(() -> ac.getBean(BeanB.class))
            .isInstanceOf(NoSuchBeanDefinitionException.class);
  }

  @Configuration
  @ComponentScan(
          includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
          excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
  )
  static class ComponentFilterAppConfig { }
}
```







