출처: [김영한의 스프링 핵심 원리 - 기본편](https://www.inflearn.com/course/스프링-핵심-원리-기본편)

빈 충돌
---

### 자동 빈 등록 충돌
자동 빈 등록을 사용해서(`@ComponentScan`, `@Component`) 동일한 이름의 빈을 두 개 이상 등록하는 경우 스프링이 오류를 발생시킨다.

**오류 메세지 예시**
<br />
MemberService, OrderService에 "service"로 빈 이름을 동일하게 등록한 경우 발생하는 오류 메세지!
``` 
Caused by: org.springframework.context.annotation.ConflictingBeanDefinitionException: Annotation-specified bean name 'service' for bean class [hello.core.member.MemberServiceImpl] conflicts with existing, non-compatible bean definition of same name and class [hello.core.order.OrderServiceImpl]
```

### 수동과 자동 빈 등록 충돌
자동으로 등록된 빈과 수동으로 등록된 빈의 이름이 동일할 경우, 수동으로 직접 등록한 빈이 자동으로 등록된 빈을 덮어써 버린다 ! 
<br /> 
(수동 빈이 우선권을 가짐)

**덮어쓴 메세지 예시**
```
DEBUG DefaultListableBeanFactory -- Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing [Generic bean: class [hello.core.member.MemoryMemberRepository]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in file [C:\HR\projects\kimyounghan\spring\core\out\production\classes\hello\core\member\MemoryMemberRepository.class]] with [Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=autoAppConfig; factoryMethodName=memoryMemberRepository; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AutoAppConfig]
```

> [!NOTE] 스프링은 자동으로 오버라이드 하도록 설정이 되어있는데, <br /> 
> 최근 스프링 부트에서 이 설정을 disabled 함!! <br />
> 허용하려면 `spring.main.allow-bean-definition-overriding=true` 로 설정하면 된다. <br /> 
> **하지만, 충돌이 난 경우 오류를 내는 것처럼 명확하게 해주는게 대부분의 상황에서 낫다. <br /> 
> 어설픈 우선순위를 주다가 추후 버그로 이어질 수 있기 때문!!**


