package com.example.boardapp.mapper;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.example.boardapp.domain.ArticleDto;


// MyBatis 테스트가 기본적으로 임베디드 데이터베이스(H2Databae)를 사용하려는 동작을 방지한다. (중요)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@ActiveProfiles("test")


public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;


    @Test
    public void selectArticleList() {

        // Given (테스트 데이터 준비)
     
        // When (테스트 실행)
        List<ArticleDto> articles = articleMapper.selectArticleList();

        // Then (결과 검증)
        assertThat(articles).isNotNull(); // 결과가 null이 아님을 확인
        assertThat(articles.size()).isGreaterThan(0); // 최소 한 개의 데이터를 반환함을 확인


        // articles.stream().forEach(article -> {

        //    System.out.println("id : " + article.getId());
        //    System.out.println("title : " + article.getTitle());
        //    System.out.println("contents : " + article.getContents());
        //    System.out.println("writer : " + article.getWriter());
        //    System.out.println("regDate : " + article.getRegDate());         
        // });
    
       
    }
}
