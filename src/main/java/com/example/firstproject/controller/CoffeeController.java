package com.example.firstproject.controller;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/coffees/new")
    public String newCoffeeForm(){
        return"coffees/new";
    }

    @PostMapping("/coffees/create")
    public String createCoffee(CoffeeDto coffeeDto){
        System.out.println(coffeeDto.toString());
        //1. dto -> entity
         Coffee coffee = coffeeDto.toEntity();
        //2. entity 를 db에 저장
        Coffee saved = coffeeRepository.save(coffee);
        System.out.println(saved.toString());
        return"";
    }

}

//연습내용
// CRUD 순서
// (1)
// Get으로 사이트페이지를 보여준다. ( mustache 페이지를 만든다.) ** return 값과 파일면 일치.

// (2)
// Post로 데이터 받아온다. mustache에서 form 태그 사용 * form의 action과 PostMapping 주소 일치.
// dto 만들기 = 기본생성자와 toString 포함
// PostMapping에 dto 파일 불러오기 (포함시키기) 예시 -> public String createCoffee(CoffeeDto coffeeDto){

// (3)
// dto -> entity로 변환. controller에 변환식 추가하기.
// CoffeeDto에 toEntity() 메서드 생성
// entity레파지터리의 Coffee에 @Entity 내용 입력 생성자 및 toString 포함

// (4)
// coffeeRepository 생성
// controller에서 엔티티를 db에 저장하는 코드 작성
// Repository 선언 할 때, Autowired 잊지않기!!

// (5) 롬북사용, 로깅 사용
// dto, entity controller 리팩토링 (적용)


