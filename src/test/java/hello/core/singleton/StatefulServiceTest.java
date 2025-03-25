package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A 사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB: B 사용자 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);

        System.out.println("userAPrice = " + userAPrice);
        System.out.println("userAPrice = " + userBPrice);

        // ThreadA: 사용자 A가 주문한 금액은 20000원이 아니라 10000원이 나온다.
        // ThreadA와 ThreadB가 같은 인스턴스를 사용하기 때문에 발생하는 문제
        // ThreadA가 사용자 A의 주문 금액을 조회하려고 했는데, ThreadB가 사용자 B의 주문 금액을 조회하려고 했기 때문에 발생하는 문제
        // ThreadA와 ThreadB가 같은 인스턴스를 사용하면서 공유 필드를 변경하면서 발생하는 문제
        // 이런 이유로 상태를 유지하지 않는 stateless한 빈으로 설계해야 한다.
        // statefulService1, statefulService2는 같은 인스턴스이기 때문에 같은 값을 조회한다.


    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}