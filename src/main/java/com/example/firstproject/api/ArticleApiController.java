package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log찍어보기위해 어노테이션 추가
@RestController
public class ArticleApiController {

    @Autowired // 게시글 레파지터리 주입
    private ArticleRepository articleRepository;
    //GET
    @GetMapping("/api/articles") // URL 요청 접수
    public List<Article> index(){ //index메서드 정의
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}") // URL 요청 접수
    public Article show(@PathVariable Long id){ //index메서드 정의
        return articleRepository.findById(id).orElse(null);
    }
    //POST
    @PostMapping("/api/articles") // URL 요청 접수
    public Article create(@RequestBody ArticleForm dto) {// 반환형이 Article인 create() 메서드 정의하고, 수정할 데이터를 dto매개변수로 받아온다.
        Article article = dto.toEntity(); //dto를 DB에서 활용할 수 있도록, entity로 변환해 article 변수에 넣는다.
        return articleRepository.save(article); // articleRepository를 통해 DB에 저장 (save())한 후 반환한다.
    }
    // REST API에서 데이터를 생성할 때는 JSON 데이터를 받아와야 하므로 단순히 매개변수로 dto를 쓴다고 해서 받아 올 수 없다!!!
    //@RequestBody 어노테이션을 추가해줌으로써, 본문(body)에 실어 보내는 데이터를 create()메서드의 매개변수로 받아올 수 있다.



    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){ // 반환형을 ResponseEntity<T>로 수정
        //1. dto -> 엔티티변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id,article.toString()); //log찍기
        //2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target ==null || id != article.getId()){ // 잘못된 요청인지 판별
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}",id,article.toString()); //로그찍기
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //HttpStatus : HTTP 상태코드 관리 클래스
        }
        //4. 업데이트 및 정상 응답(200)하기
        target.patch(article); // 기존 데이터에 새 데이터를 붙인다.
        // target에는 기존데이터, article에는 수정할 데이터가 있다.따라서 기존데이터에 새 데이터를 붙여줘야 수정할때 기존데이터가 지워지지 않는다.
        //Article updated = articleRepository.save(article); 해당 코드는 입력하지 않은 컬럼의 데이터가 null로 기존데이터가 지워진다.
        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated); // 정상응답
    }
    //ResponseEntity<T> 는 REST컨트롤러의 반환형, 즉, REST API의 응답을 위해 사용하는 클래스이다. 상태코드를 실어 보낼수있다.



    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //HttpStatus : HTTP 상태코드 관리 클래스
        }
        //3. 대상 삭제하기
        articleRepository.delete(target);
        //return ResponseEntity.status(HttpStatus.OK).body(null); // 정상응답
        return ResponseEntity.status(HttpStatus.OK).build(); //  build():  body가 없는 ResponseEntity 객체를 생성한다.
    }

}
