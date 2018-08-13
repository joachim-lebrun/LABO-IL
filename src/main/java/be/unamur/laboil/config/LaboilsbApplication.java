package be.unamur.laboil.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
/**
 * @author Joachim Lebrun on 08-08-18
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableWebMvc
@ComponentScan("be.unamur.laboil")
public class LaboilsbApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboilsbApplication.class, args);
    }

}
