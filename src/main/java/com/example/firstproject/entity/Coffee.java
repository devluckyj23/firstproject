package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자
@ToString // 실행메서드 toString()
@NoArgsConstructor //기본생성자
@Getter
public class Coffee {
    @Id // 엔티티의 대표값 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 숫자 자동 생성 기능 추가
    private Long id; // 대표값
    @Column
    private String name;
    @Column
    private String price;

    //patch 메서드를 통해서 name이 null이 아닌 기존 데이터가 있다면 현재 새로 입력한 name으로 한다라는 갱신 메서드
    public void patch(Coffee coffee) {

        if(coffee.name != null)
            this.name = coffee.name;
        if(coffee.price != null)
            this.price = coffee.price;
    }

//    public Long getId() {
//        return id;
//    }




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



//    @Override
//    public String toString() {
//        return "Coffee{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price='" + price + '\'' +
//                '}';
//    }

