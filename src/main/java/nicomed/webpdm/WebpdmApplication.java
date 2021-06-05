package nicomed.webpdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@SpringBootApplication
public class WebpdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebpdmApplication.class, args);
    }


//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
////        resolver.setTemplateEngine(templateEngine());
//        resolver.setCharacterEncoding("UTF-8");
//        return resolver;
//    }
}
