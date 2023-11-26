package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity// 엔티티 선언 (이 클래스를 기반으로 db에 테이블을 생성한다)
public class Member {
    @Id // 행번호 // 엔티티의 대푯값 지정
    @GeneratedValue // 행번호 자동 //대푯값 자동 생성 기능 추가(숫자가 자동으로 매겨짐
    // 고유 번호이기에 같은 제목과 내용이 있어도 구분가능하게 해준다.

    private Long id; // 대푯값
    @Column
    private String email; // email 필드 선언, DB 데이블의 title 열과 연결된다.
    @Column
    private String password; // password 필드 선언, DB 테이블의 content 열과 연결된다.

    //Article 생성자 추가
    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    // toString() 메서드 추가
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
