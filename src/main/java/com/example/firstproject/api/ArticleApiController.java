package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log찍어보기위해 어노테이션 추가
@RestController
public class ArticleApiController {

    // 컨트롤러,서비스,리파지터리의 역할을 분업한다.

    @Autowired
    private ArticleService articleService; //서비스 객체 주입



// REST Controller 에서 컨트롤러와 서비스의 역할을 같이하게 하는 코드 //
//    @Autowired // 게시글 레파지터리 주입
//    private ArticleRepository articleRepository;
//
    // 전체 데이터 조회 (전체)
    //GET
    @GetMapping("/api/articles") // URL 요청 접수
    public List<Article> index(){ //index메서드 정의
        return articleService.index();
    }

    // 단일 데이터 조회 (상세)
    @GetMapping("/api/articles/{id}") // URL 요청 접수
    public Article show(@PathVariable Long id){ //index메서드 정의
        return articleService.show(id);
    }

    //POST
    @PostMapping("/api/articles") // URL 요청 접수
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {// 반환형이 Article인 create() 메서드 정의하고, 수정할 데이터를 dto매개변수로 받아온다.
        Article created = articleService.create(dto); //dto를 DB에서 활용할 수 있도록, entity로 변환해 created 변수에 넣는다.
        // return (created != null) ? good : bad; // 조건이 참이면 good, 거짓이면 bad // 삼항연산자 사용 ResponseEntity 형식으로 변경해서 적용
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
//    // REST API에서 데이터를 생성할 때는 JSON 데이터를 받아와야 하므로 단순히 매개변수로 dto를 쓴다고 해서 받아 올 수 없다!!!
//    //@RequestBody 어노테이션을 추가해줌으로써, 본문(body)에 실어 보내는 데이터를 create()메서드의 매개변수로 받아올 수 있다.
//
//
//
    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { // 반환형을 ResponseEntity<T>로 수정
        Article updated = articleService.update(id,dto); // 서비스를 통해 게시글 수정
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    //ResponseEntity<T> 는 REST컨트롤러의 반환형, 즉, REST API의 응답을 위해 사용하는 클래스이다. 상태코드를 실어 보낼수있다.
//
//    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
     Article deleted = articleService.delete(id);//서비스를 통해 게시글 삭제
        return (deleted != null) ?
             ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
             ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
