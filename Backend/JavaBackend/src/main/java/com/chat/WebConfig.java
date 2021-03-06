package com.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // server: http://larryjason.com
    // local: http://localhost:8002

    registry.addMapping("/**")
      .allowedOrigins("http://larryjason.com", "http://www.larryjason.com");
  }
}
