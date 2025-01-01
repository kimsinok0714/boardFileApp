package com.example.boardapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boardapp.domain.ArticleFileDto;

@Mapper
public interface ArticleFileMapper {

    void insertArticleFile(List<ArticleFileDto> files);

}
