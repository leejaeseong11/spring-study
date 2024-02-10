package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }

    // 스프링부트를 사용하면 자동으로 등록됨
    //	@Bean
    //	public MessageSource messageSource() {
    //		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    //		messageSource.setBasenames("messages", "errors");
    //		messageSource.setDefaultEncoding("utf-8");
    //		return messageSource;
    //	}
}
