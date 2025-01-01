package com.example.boardapp.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.boardapp.domain.ArticleFileDto;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;


@Slf4j
@Component
public class CustomFileUtil {

    @Value("${com.example.boardapp.upload.path}")
    private String uploadPath;


    // 빈 생성과 의존성 주입이 완료된 후 (초기화 작업이 완료된 후) 애플리케이션이 시작될 때 한 번만 호출됩니다.
    @PostConstruct  
    public void init() {
        File tempDir = new File(uploadPath);
        
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        uploadPath = tempDir.getAbsolutePath();

        log.info("File UploadPath : {}", uploadPath);       // /home/user1/boardapp/upload   

    }



    public List<ArticleFileDto> uploadFile(List<MultipartFile> multipartFiles) throws RuntimeException {

        if (multipartFiles == null || multipartFiles.size() == 0) {
            return null;
        }

        List<ArticleFileDto> articleFiles = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            // 32자리 16진수 : 고유한 식별자를 생성
            String filename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, filename);

            try {

                Files.copy(multipartFile.getInputStream(), savePath);

                String contentType = multipartFile.getContentType();

                if (contentType != null && contentType.startsWith("image")) {                    
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + filename);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbnailPath.toFile(), 200, 200);
                }

                ArticleFileDto articleFileDto = new ArticleFileDto();
                articleFileDto.setFilename(filename);
                articleFileDto.setFilepath(uploadPath);
                articleFileDto.setSize((int)multipartFile.getSize());
                
                articleFiles.add(articleFileDto);

            } catch (IOException e) {
                throw new RuntimeException();
            }

        });


        return articleFiles;
    }
    


    // public List<String> saveFiles(List<MultipartFile> multipartFiles) throws RuntimeException {

    //     if (multipartFiles == null || multipartFiles.size() == 0) {
    //         return null;
    //     }

    //     List<String> uploadFileNames = new ArrayList<>();

    //     multipartFiles.stream().forEach(file -> {
    //         // UUID : 32자리
    //         String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
    //         // 파일 시스템 경로 : 파일 입출력 작업을 위해 사용된다.
    //         Path savedPath = Paths.get(uploadPath, savedFileName);
            
    //         try {
                
    //             Files.copy(file.getInputStream(), savedPath);
                
    //             String contentType = file.getContentType();

    //             log.info("ContentType : {} ", contentType);

    //             if (contentType != null && contentType.startsWith("image")) {
    //                 Path thumbnailPath = Paths.get(uploadPath, "s_" + savedFileName);
    //                 Thumbnailator.createThumbnail(savedPath.toFile(), thumbnailPath.toFile(), 200, 200);
    //             }

    //             uploadFileNames.add(savedFileName);

    //         } catch (Exception e) {
    //             throw new RuntimeException();
    //         }

    //     });


    //     return uploadFileNames;
        
    // }    

    // 파일 다운로드 메소드

    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resouce = new FileSystemResource(uploadPath + File.separator + fileName);
        
        if (!resouce.isReadable())  {
            resouce = new FileSystemResource(uploadPath + File.separator + "default.jpg");
        }


        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("content-type", Files.probeContentType(resouce.getFile().toPath()));

        } catch(Exception ex) {
            throw new RuntimeException();  // 파일 타입을 판별하지 못하거나 예외가 발생한 경우
        }

        // return new ResponseEntity<>(resouce, headers, HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body(resouce);

    }


    // 파일 삭제
    public void deleteFile(List<String> fileNames) {
        
        if (fileNames == null || fileNames.size() == 0) {
            return;
        }

        fileNames.forEach(filename -> {

            String thumbnailFilename = "s_" + filename;

            Path thumbnailPath = Paths.get(uploadPath, thumbnailFilename);
            Path filePath = Paths.get(uploadPath, filename);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);

            } catch (Exception e) {
                throw new RuntimeException();
            }

        });
    }



}
