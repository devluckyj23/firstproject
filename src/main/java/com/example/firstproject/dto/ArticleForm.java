package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //lombok을 사용하기위한 어노테이션 중 하나 ; (생성자 코드 간소화)
// 이 어노테이션을 사용하면 클래스 안의 모든 필드,즉 title과 content를 매개변수로 하는 생성자가 자동으로 만들어짐
@ToString // (ToString 코드 간소화)
public class ArticleForm {
    // 입력 폼에서 제목과 내용 2가지를 전송할 예정이니 dto에서도 필드 2개가 필요하다
    
    private String title; //제목을 받을 필드
    private String content; // 내용을 받을 필드




    //
    public Article toEntity() {
       return new Article(null,title,content);
    }
}
