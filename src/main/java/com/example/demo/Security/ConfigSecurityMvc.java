package com.example.demo.Security;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class ConfigSecurityMvc extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("/login");
        registry.addViewController("/ajouterEmployer").setViewName("AjouterEmployer");
        registry.addViewController("/tache").setViewName("calcleSemaine");
        registry.addViewController("/test").setViewName("tester");


    }
}
