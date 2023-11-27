package com.example.firstproject.controller;

import ch.qos.logback.core.joran.spi.ConsoleTarget;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j //로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    //등록 페이지
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return"articles/new";
    }

    @Autowired //스프링 부트가 미리 생성해 놓은 레파지토리 객체 주입(DI) 이것을 의존성 주입이라고한다. (Dipendency Injection)
    private ArticleRepository articleRepository; //articleRepository 객체 선언

    // 등록 확인 페이지
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {  //폼 데이터를 dto로 받기
        log.info(form.toString());
        //System.out.println(form.toString());        // dto에 폼 데이터가 잘 담겼는지 확인
        //1.dto를 엔티티로 변환
        // -> form객체의 toEntity()메서드를 호출해서 그 반환 값을 Article 타입의 article 엔티티에 저장한다.
        // -> 이를 위해서는 엔티티 클래스 부터 만들어야한다. Article 클래스가 바로 엔티티 클래스

        Article article = form.toEntity();
            log.info(article.toString());
        //System.out.println(article.toString()); //DTO가 엔티티로 잘 변환되는지 확인


        //2.리파지토리로 엔티티를 db에 저장
        Article saved = articleRepository.save(article); //article 엔티티를 저장해 saved 객체에 반환한다.
        // 여기서 다른 스프링부트가 다른점은 자바 사용할 때, 방금처럼 articleRepository를 만들고 그안의 save메서드를 활용하려면,
        // 먼저 객체를 만들었어야한다. new를 사용해서.
        // 하지만 스프링부트는 자동으로 객체를 만들어 주기때문에 new로 객체를 만드는 과정이 생략가능하다.
        // @Autowired를 사용하면 자동 생성된 객체를 가져다 연결할 수 있다.

        log.info(saved.toString());
        //System.out.println(saved.toString()); //article이 db에 잘 저장되는지 확인 출력


        return "redirect:/articles/"+saved.getId();
        //id에 페이지를 열고 싶으면 (article/1 ...) saved객체에 article을 저장하였으므로,
        // saved.getId()를 호출하면 saved 객체의 id값을 가져올수 있다.
        // 위와 같이 return에 getId값을 가져오고싶으면 getter가 정의되어있어야한다.
    }
        //상세페이지
        @GetMapping("/articles/{id}")
        public String show(@PathVariable Long id, Model model){
        // @PathVariable 는 URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션이다. //여기서는 id를 매개변수로 가져옴.
        log.info("id = " + id); // id를 잘 받았는지 확인하는 로그 찍기

            //데이터 조회해서 출력
            // 1. id를 조회해서 데이터 가져오기
                    // db에서 데이터를 가져오는 주체는 리파지터리이다.
                    // findById는 crudRepository에서 제공하는 메서드로 특정 엔티티의 id값을 기준으로 데이터를 찾아 Optional타입으로 반환한다.

            // Optional<Article> articleEntity = articleRepository.findById(id);
            Article articleEntity = articleRepository.findById(id).orElse(null);

            // 2. 가져온 데이터를 모델에 등록하기
            //model형식 model.addAttribute(String name,Object value);
            model.addAttribute("article",articleEntity); //article이라는 이름으로 articleEntity 객체를 등록한다.
            // 3. 뷰 페이지 반환하기
            return"articles/show";
        }
        //목록페이지
        @GetMapping("/articles")
        public String index(Model model){

        //1. DB에서 모든 Article 데이터 가져오기
            ArrayList<Article> articleEntityList = articleRepository.findAll();
            // List타입과 iterable 타입이라 서로 다르므로 오류 ,
            // 3가지 방법이 있다. 캐스팅(형변환) 시켜주면 된다. iterable>collection>List 
            // 1.다운캐스팅,2.업캐스팅, 3. ArrayList사용
        //2. 가져온 Article 묶음을 모델에 등록하기
            model.addAttribute("articleList",articleEntityList);
        //3. 사용자에게 보여 줄 뷰 페이지 설정하기

        return"articles/index";
        }

        @GetMapping("/articles/{id}/edit")
        public String edit(@PathVariable Long id, Model model){
            // 수정할 기존 데이터 가져오기
          Article articleEntity  = articleRepository.findById(id).orElse(null);
          model.addAttribute("article",articleEntity);
            //뷰페이지 설정하기
          return"articles/edit";
        }

        @PostMapping("/articles/update")
        public String update(ArticleForm form){ //매개변수로 DTO 받아오기
        // - 이 때, mustache에서 input 태그로 id값도 받아오기때문에 dto에 추가해야한다. ArticleForm
        log.info(form.toString());
        //dto -> 엔티티
            Article articleEntity = form.toEntity();
            log.info(articleEntity.toString());
        // 엔티티 db저장
            //1.db에서 기존 데이터 가져오기
         Article target  = articleRepository.findById(articleEntity.getId()).orElse(null);
            //2.기존 데이터값 갱신
           if(target != null){
               articleRepository.save(articleEntity);
           }
        // 수정 결과 페이지로 리다이렉트
        return"redirect:/articles/"+articleEntity.getId();
        }
}


