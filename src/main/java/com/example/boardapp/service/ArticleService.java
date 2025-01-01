package com.example.boardapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.boardapp.domain.ArticleDto;
import com.example.boardapp.domain.ArticleFileDto;
import com.example.boardapp.mapper.ArticleFileMapper;
import com.example.boardapp.mapper.ArticleMapper;
import com.example.boardapp.util.Criteria;
import com.example.boardapp.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleMapper articleMapper;

    private final ArticleFileMapper articleFileMapper;

        
    private final CustomFileUtil customFileUtil;


    public List<ArticleDto> retrieveArticleList() {
        return articleMapper.selectArticleList();
    }


    @Transactional
    public int createArticle(ArticleDto articleDto, List<MultipartFile> multipartFiles) {
     
        articleMapper.insertArticle(articleDto);

        List<ArticleFileDto> articleFiles = customFileUtil.uploadFile(multipartFiles);

        if (articleFiles != null) {

            for (ArticleFileDto articleFileDto : articleFiles) {
                articleFileDto.setArticleId(articleDto.getId());                
            }

        }

        articleFileMapper.insertArticleFile(articleFiles);

        return articleDto.getId();
    }



    public ArticleDto retrieveArticleById(int id) {
        return articleMapper.selectArticleById(id);
    }

    public void modifyArticle(ArticleDto articleDto) {
        articleMapper.updateArticle(articleDto);
    }
    
    public List<ArticleDto> searchArticleList(Criteria criteria) {
        return articleMapper.findArticleList(criteria);
    }

}
