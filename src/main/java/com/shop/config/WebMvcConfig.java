package com.shop.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    // 어플리케이션 .프로퍼티스에서 설정한 uploadPath프로퍼티 값을 읽어옵니다.
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
        // 웹 브라우저에 입력하는 URL에 /images로 시작하는 경우 uploadpath에 설정한 폴더 기준응로 파일을 읽어오도록 설정
                .addResourceLocations(uploadPath);
        // 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정
    }

}