package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@ToString
public class Coffee {
    @Id // 엔티티의 대표값 설정
    @GeneratedValue // 숫자 자동 생성 기능 추가
    private Long id; // 대표값
    @Column
    private String name;
    @Column
    private String price;

    public Coffee() {

    }

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

