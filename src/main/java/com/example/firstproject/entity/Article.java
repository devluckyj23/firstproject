package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Entity // 엔티티 선언 (이 클래스를 기반으로 db에 테이블을 생성한다)
@Getter
public class Article {
    @Id // 엔티티의 대푯값 지정
    @GeneratedValue //대푯값 자동 생성 기능 추가(숫자가 자동으로 매겨짐// 고유 번호이기에 같은 제목과 내용이 있어도 구분가능하게 해준다.
    private Long id; // 대푯값
    @Column // title 필드 선언, DB 데이블의 title 열과 연결된다.
    private String title;
    @Column // content 필드 선언, DB 테이블의 content 열과 연결된다.
    private String content;


    //기본 생성자 :entity에는 반드시 기본 생성자가 필요하다. 리플렉션API를 사용하기때문에
    //1. 기본 생성자가 필요한 이유
    //Spring Data JPA 에서 Entity에 기본 생성자가 필요한 이유는 동적으로 객체 생성 시 Reflection API를 활용하기 때문이다.
    //JPA는 DB 값을 객체 필드에 주입할 때 기본 생성자로 객체를 생성한 후 Reflection API를 사용하여 값을 매핑한다.
    //기본 생성자가 없다면 Reflection은 해당 객체를 생성 할 수 없기 때문에 JPA의 Entity에는 기본 생성자가 필요하다.
    // 리플렉션API? 자바는 기본적으로 static 영역에 코드가 저장되는데
    // 구체적인 클래스 타입을 알지 못해도 클래스 이름을 통해 static 영역에서 그 클래스의 정보(메서드, 타입, 변수 등등)에 접근할 수 있게 해준다.
    //다만 Reflection API가 생성자의 인자 정보는 가져올 수 없다.
    //때문에 기본 생성자가 있어야 객체를 생성할 수 있고 생성된 객체를 통해서 Reflection API는 필드 값 등을 넣어줄 수 있다.

    public Article() {}

//   getter 사용 방법 1
//    public Long getId() { // !주의:  타입을Long으로 바꿔줘야함.
//        return id;
//    }
//  getter 사용 방법 2  lombok 사용
}
