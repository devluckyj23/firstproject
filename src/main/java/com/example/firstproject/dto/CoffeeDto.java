package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 기본 생성자 자동 생성
@ToString
public class CoffeeDto {
    private Long id;
    private String name; // 커피 이름 받을 필드
    private String price; // 커피 가격 받을 필드

    public Coffee toEntity() {
        return new Coffee(id,name,price);
    }
}


//    기본 생성자 -> @AllArgsConstructor 로 대체
//    public CoffeeDto(String name, String price) {
//        this.name = name;
//        this.price = price;
//    }


//   To String으로 실행되는지 확인하기 -> @ToString으로 대체
//    @Override
//    public String toString() {
//        return "CoffeeDto{" +
//                "name='" + name + '\'' +
//                ", price='" + price + '\'' +
//                '}';
//    }

