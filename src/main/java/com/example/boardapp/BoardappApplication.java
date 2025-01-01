package com.example.boardapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.boardapp.mapper")
public class BoardappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardappApplication.class, args);
	}

}
