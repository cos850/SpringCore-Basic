package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("싱글톤 서비스의 공유 자원 문제")
    void statefulServiceSingleton() {
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // threadA: A사용자가 1000원 주문
        statefulService1.order("userA", 1000);

        // threadB: B사용자가 2000원 주문
        statefulService2.order("userB", 2000);

        // threadA: 사용자A 주문 금액 조회
        int userAPrice = statefulService1.getPrice();

        Assertions.assertThat(userAPrice).isEqualTo(2000);
    }
    
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}