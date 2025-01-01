package com.example.boardapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.boardapp.controller.formatter.LocalDateTimeFormatter;

import lombok.extern.slf4j.Slf4j;



// WebMvcConfigurer : Spring MVC 설정을 커스터마이징할 때 사용하는 인터페이스
@Configuration
@Slf4j
public class CustomServletConfig implements WebMvcConfigurer { 
    @Override
    public void addFormatters(FormatterRegistry registry) {   // FormatterRegistry : 데이터를 특정 형식으로 변환하는데 사용

        log.info("{}", "addFormatter");
        
        registry.addFormatter(new LocalDateTimeFormatter());    // 사용자 정의 포매터 : LocalDateTimeFormatter   
    }
   
}
