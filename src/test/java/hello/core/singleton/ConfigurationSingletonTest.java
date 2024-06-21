package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("스프링 컨테이너의 설정 파일과 싱글톤 확인")
    void configurationSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberRepository memberServiceRepository = ac.getBean(MemberServiceImpl.class).getMemberRepository();
        MemberRepository orderServiceRepository = ac.getBean(OrderServiceImpl.class).getMemberRepository();
        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        
        // Java Code를 보면 MemberRepository가 3번 생성되어서 다른 객체여야 할 것 같은데, 
        // 실제로 메모리 참조값을 보면 동일하다. (메서드 호출을 확인해보면 실제로 AppConfig.memberRepository()를 단 한번만 호출한다)
        // => 스프링 컨테이너가 싱글톤을 확실히 보장해준다는 것을 알 수 있음
        Assertions.assertThat(memberServiceRepository).isSameAs(orderServiceRepository);
        Assertions.assertThat(orderServiceRepository).isSameAs(memberRepository);
    }
    
    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // 출력해보면 객체의 값이 기존과 다르게 되어 있는 것을 알 수 있다.
        // => class hello.core.AppConfig$$SpringCGLIB$$0
        // @Configuration을 붙이면 CGLIB 기술을 사용해서 AppConfig 클래스를 상속한 새로운 클래스가 Bean에 등록된다.
        // 그 클래스는 바이트 코드를 조작해서 작성되었을 것
        // 이 기술 덕분에 이미 스프링 빈이 존재하면 빈을 반환하고, 없으면 스프링 빈으로 등록한 뒤 반환하는 코드가 동적으로 만들어진다.
        // 덕분에 싱글톤이 보장된다.
        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
