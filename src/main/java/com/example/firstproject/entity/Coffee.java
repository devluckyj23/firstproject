package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자
@ToString // 실행메서드 toString()
@NoArgsConstructor //기본생성자
public class Coffee {
    @Id // 엔티티의 대표값 설정
    @GeneratedValue // 숫자 자동 생성 기능 추가
    private Long id; // 대표값
    @Column
    private String name;
    @Column
    private String price;

    public Long getId() {
        return id;
    }

    // 기본 생성자
//    public Coffee() {
//
//    }

//    public Coffee(Long id, String name, String price) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//    }


}
//    @Override
//    public String toString() {
//        return "Coffee{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price='" + price + '\'' +
//                '}';
//    }

