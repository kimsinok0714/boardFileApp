package com.example.boardapp.runner;

import java.io.File;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


// CommandLineRunner or ApplicationRunner : Spring Boot에서 제공하는 인터페이스로, 
// 애플리케이션이 시작된 직후 (컨테이너 초기화된 후) 특정 로직을 실행하고자 할 때 사용됩니다. 이를 통해 초기화 작업이나 특정 비즈니스 로직을 애플리케이션 시작 시 수행할 수 있습니다.

@Slf4j
@Component
public class DirectoryInitializer implements ApplicationRunner {    

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        File uploadDir = new File("/tmp/uploads");

        if (!uploadDir.exists()) {

            boolean created = uploadDir.mkdir();

            if (created) {
                log.info("Upload directory created : {}", uploadDir.getAbsolutePath());          
            } else {
                log.error("Failed to create upload directory");
            }

        }
        
    }

    

}
