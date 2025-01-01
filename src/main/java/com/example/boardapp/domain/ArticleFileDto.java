package com.example.boardapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFileDto {

    private int id;
    private String filename;
    private String filepath;
    private int size;
    private int articleId;
    
}
