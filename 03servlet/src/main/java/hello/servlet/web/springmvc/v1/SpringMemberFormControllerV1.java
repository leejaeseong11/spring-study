package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller //@Component + @RequestMapping
//@Component //컴포넌트 스캔의 대상이 됨, 이를 제거하고 직접 빈으로 등록해도 됨
//@RequestMapping
public class
SpringMemberFormControllerV1 {
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
