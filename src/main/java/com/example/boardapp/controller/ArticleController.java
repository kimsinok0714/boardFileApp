package com.example.boardapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.boardapp.domain.ArticleDto;

import com.example.boardapp.service.ArticleService;
import com.example.boardapp.util.Criteria;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PutMapping;




@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class ArticleController {

    private final ArticleService articleService;
    



    @GetMapping("/articles")
    public ResponseEntity<ArticleDto> getArticleList() {

        return new ResponseEntity(articleService.retrieveArticleList(), HttpStatus.OK);
   
    }
    

    // File과 Dto를 같이 받기 위해서는 @RequestPart를 사용한다.
    @PostMapping("/articles")
    public ResponseEntity<Map> postArticle(@RequestPart(value = "articleDto") ArticleDto articleDto,
                                           @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {

            int articleId = articleService.createArticle(articleDto, multipartFiles);
                     
            Map<String, Integer> map = new HashMap<>();
            map.put("id", articleId);


            return new ResponseEntity<>(map, HttpStatus.CREATED);
    }   


    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getMethodName(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(articleService.retrieveArticleById(id), HttpStatus.OK);
    }
    
    
    @PutMapping("/articles/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable("id") int id, @RequestBody ArticleDto articleDto) {

        articleDto.setId(id);
        articleService.modifyArticle(articleDto);
        
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }


    @GetMapping("/search")
    public ResponseEntity<List<ArticleDto>> search(
            @RequestParam(required = false, defaultValue = "") String keyfield, 
            @RequestParam(required = false, defaultValue = "") String keyword) {
            articleService.searchArticleList(new Criteria(keyfield, keyword));

            return new ResponseEntity<>( articleService.searchArticleList(new Criteria(keyfield, keyword)), HttpStatus.OK);
    }
    


    // 파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadImage (@PathVariable("filename") String fileName) {
        
        return null;

    }
    

    


}
