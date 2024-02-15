package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;

import lombok.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.beans.ConstructorProperties;

@Controller
public class ConverterController {
    @GetMapping("/converter-view")
    public String convertView(Model model) {
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
        return "converter-view";
    }

    @GetMapping("/converter/edit")
    public String converterForm(Model model) {
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        Form form = new Form(ipPort);
        model.addAttribute("form", form);
        return "converter-form";
    }

    @PostMapping("/converter/edit")
    public String converterEdit(@ModelAttribute Form form, Model model) {
        IpPort ipPort = form.getIpPort();
        model.addAttribute("ipPort", ipPort);
        return "converter-view";
    }

    @Getter
    // @Data -> 해당 어노테이션만 사용하면 에러남
    // ModelAttribute를 바인딩 하기 위해 Form의 생성자를 사용하는데 해당 생성자의 ipPort라는 파라미터를 알 수 없음
    // @ConstructorProperties로 파라미터 이름을 명시해주거나,
    // @Setter, @NoArgsConstructor를 통해 기본 생성자로 바인딩 하도록 해주어야 함
    static class Form {
        private IpPort ipPort;

        @ConstructorProperties({"ipPort"})
        public Form(IpPort ipPort) {
            this.ipPort = ipPort;
        }
    }
}
