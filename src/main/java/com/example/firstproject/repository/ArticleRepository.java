package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    // CrudRepository 상속 받는데 JPA에서 제공되는 레파지터리 인터페이스를 활용한다.(엔티티를 생성,조회,수정,삭제 할 수 있음)

    //CrudRepository의 메서드를 오버라이딩해준다.
    // findAll() 메서드의 반환값은 기본적으로 <Article>의 iterable타입이므로, ArrayList로 바꿔준다.
    @Override
    ArrayList<Article> findAll();
}
