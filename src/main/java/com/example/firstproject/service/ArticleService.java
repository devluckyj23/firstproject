package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service // 서비스 객체 생성
public class ArticleService {


    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        // id값은 db에서 자동생성되므로 생성할때 굳이 create()에서 만들 필요가 없음. id가 존재한다면 (null이 아니라면) null을 반환한다.
        if(article.getId() != null){
            return null;
        }

        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. dto -> 엔티티변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id,article.toString()); //log찍기
        //2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target == null || id != article.getId()){ // 잘못된 요청인지 판별
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}",id,article.toString()); //로그찍기
            return null; // 응답은 컨트롤러가 하므로 여기서는 null을 반환한다.
        }
        //4. 업데이트 및 정상 응답(200)하기
        target.patch(article); // 기존 데이터에 새 데이터를 붙인다.
        // target에는 기존데이터, article에는 수정할 데이터가 있다.따라서 기존데이터에 새 데이터를 붙여줘야 수정할때 기존데이터가 지워지지 않는다.
        //Article updated = articleRepository.save(article); 해당 코드는 입력하지 않은 컬럼의 데이터가 null로 기존데이터가 지워진다.
        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
        return updated; // 수정데이터 반환
    }

    public Article delete(Long id) {
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if(target == null){
            return null;
        }
        //3. 대상 삭제하기
        articleRepository.delete(target);
        //return ResponseEntity.status(HttpStatus.OK).body(null); // 정상응답
        return target; //DB에서 삭제한 대상을 컨트롤러에 반환
    }
}
