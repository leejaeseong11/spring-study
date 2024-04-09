package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 프록시
@SpringBootTest // 기본은 CGLIB 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {
    @Autowired MemberService memberService;

    // JDK 동적 프록시는 구체 클래스의 존재를 모르기 때문에 의존관계 주입이 불가능함
    @Autowired MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
