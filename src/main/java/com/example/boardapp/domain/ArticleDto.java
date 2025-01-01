package com.example.boardapp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private int id;
    private String title;
    private String contents;
    private String writer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 날짜 및 시간 형식 지정
    private LocalDateTime regDate;  // LocalDate

    private List<ArticleFileDto> files = new ArrayList<>();

    private UserDto user;
    
}
