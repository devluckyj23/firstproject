package com.example.firstproject.controller;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // 등록페이지
    @GetMapping("/coffees/new")
    public String newCoffeeForm(){
        return"coffees/new";
    }

    // 데이터 가져오기
    @PostMapping("/coffees/create")
    public String createCoffee(CoffeeDto coffeeDto){
        //System.out.println(coffeeDto.toString());
        log.info(coffeeDto.toString());
        //1. dto -> entity
         Coffee coffee = coffeeDto.toEntity();
         log.info(coffee.toString());
        //2. entity 를 db에 저장
        Coffee saved = coffeeRepository.save(coffee);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return"redirect:/coffees/" + saved.getId();
    }
    // 저장한 데이터 조회
    @GetMapping("/coffees/{id}")
    public String detail(@PathVariable Long id, Model model){
        log.info("id = "+ id);
     // id를 조회해 데이터 가져오기 ( Coffee 타입의 coffeeEntity 변수에 저장한다.
     Coffee coffeeEntity  = coffeeRepository.findById(id).orElse(null);
     // orElse(null) : id값이 없으면 null을 반환하라는 뜻.
     // repository가 coffee타입이 아니므로 오류 optional<Coffee> 타입이다. orElse()를  optional<Coffee> 대신 사용가능하다.

        // coffeeEntity 변수에 저장된 데이터를 모델에 coffee라는 이름으로 저장
        model.addAttribute("coffee",coffeeEntity);

        return"coffees/detail";
    }

    //전체 목록 조회하기 : List
    @GetMapping("/coffees")
    public String list(Model model){
        // 모든 데이터 가져오기
        // 데이터 타입 불일치 해결방법 
        // 1) 캐스팅(형변환) (List<Coffee>)coffeeRepository.findAll();
        // 2) findAll() 메서드가 반환하는 타입으로 변경 : Iterable<Coffee>로 업캐스트
        // 3) ArrayList이용
      List<Coffee> coffeeEntityList = coffeeRepository.findAll();
      log.info(coffeeEntityList.toString());
      // 모델에 등록
        model.addAttribute("coffeeList",coffeeEntityList);

        // 뷰페이지 설정

        return "coffees/list";
    }

    // 수정하기
    @GetMapping("/coffees/{id}/edit")
    public String edit(@PathVariable Long id,Model model){

        // 수정할 데이터 가져오기
        Coffee coffeeEntity = coffeeRepository.findById(id).orElse(null);
        log.info(coffeeEntity.toString());
        //model에 등록
        model.addAttribute("coffee",coffeeEntity);
        //뷰 페이지 설정
        return"coffees/edit";
    }

    @PostMapping("/coffees/update")
    public String update(CoffeeDto coffeeDto){
        log.info(coffeeDto.toString());
        // dto -> entity 변환
        Coffee coffeeEntity = coffeeDto.toEntity();
        log.info(coffeeEntity.toString());
        //entity db에 저장
        // db에서 기존 데이터 가져오기 : target 
        Coffee target = coffeeRepository.findById(coffeeEntity.getId()).orElse(null);
        // 기존 데이터 갱신하기
        if(target != null){
            coffeeRepository.save(coffeeEntity);
        }
        // 기존 데이터 target이 null이 아니면 coffeeRepository 에 coffeeEntity (갱신할 데이터)를 저장해라.
        // 수정 결과 리다이렉트
        return"redirect:/coffees/"+coffeeEntity.getId();
        // id 부분이 엔티티에 따라 매번 바뀌어야하므로 coffeeEntity의 getId()메서드 호출
    }

    @GetMapping("coffees/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청이 들어왔습니다!!!");

        // 삭제 대상 가져오기
       Coffee target = coffeeRepository.findById(id).orElse(null);
        log.info(target.toString());
        //대상엔티티 삭제하기
        // 만약 target이 null이 아니라면 target을 삭제하겠음.
        if( target != null){
            coffeeRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!!");
            // 메시지는 coffees 즉, 전체 목록 뷰 : list로 넘어간다. 그러나 header 부분을 따로 빼 놓았으므로 header.mustache에 msg 추가
        }
        //결과 페이지로 리다이렉트

        return "redirect:/coffees";
    }
}

//연습내용
// CRUD 순서
// (1)
// Get으로 사이트페이지를 보여준다. ( mustache 페이지를 만든다.) ** return 값과 파일명 일치.

// (2)
// Post로 데이터 받아온다. mustache에서 form 태그 사용 * form의 action과 PostMapping 주소 일치.
// dto 만들기 = 기본생성자와 toString 포함
// PostMapping에 dto 파일 불러오기 (포함시키기) 예시 -> public String createCoffee(CoffeeDto coffeeDto){

// (3)
// dto -> entity로 변환. controller에 변환식 추가하기.
// CoffeeDto에 toEntity() 메서드 생성 : @Entity
// entity패키지의 Coffee에 @Entity 내용 입력 생성자 및 toString 포함

// (4)
// coffeeRepository 생성
// controller에서 엔티티를 db에 저장하는 코드 작성
// Repository 선언 할 때, Autowired 잊지않기!!

// (5) 롬북사용, 로깅 사용 리팩토링 (적용)
// dto, entity : 기본생성자,tostring
// controller : log 찍기

//====================== (1) ~ (5) 내용 ==================//
//등록 후 데이터가 db에 저장되며 개발도구 콘솔창에 입력값이 찍힌다.//
//=======================================================//

// (6)
// 입력한 단일 데이터 조회 detail
// get 작성
// repository에서 id값을 가져와 변수에 저장한다.
// 저장된 변수를 모델에 등록한다.

// (7)
// detail 뷰 페이지를 만든다.
// header와 footer , 부트스트랩에서 table 코드 가져온 뒤,
// coffee의 데이터를 사용할 범위를 지정해준다 {{#coffee}}{{/coffee}} 변수값 변경
// 오류가 있다면 기본생성자 확인 entity : @NoArgsConstructor (롬북사용)

// (8)
// 데이터 전체 목록 조회하기 : list
// get으로 모든 데이터 가져오기
// List<Coffee> 타입으로 가져와야한다 but 현재 iterable 타입이고, repository에 List타입이 없으므로 추가,변경해준다.
// (상위) iterable > Collection > List (하위)
// model 등록해준다.
// return값 입력

// (9)
// 링크 넣기
// 목록-> 등록
// 등록 -> 목록
// 등록 => 상세페이지 (리다이렉트)
// 상세페이지 -> 목록
// 목록에서 name을 눌렀을때 상세페이지로 이동

// (10)
// 수정하기
// 상세페이지에 수정 버튼 만든다 (a태그)
// get으로 edit 메서드 생성
// 수정할 데이터 가져오기
// model에 등록
// edit.mustache 만들기 (new.mustache 참조)
// 각 항목에 value값 주기 (데이터 범위 재설정)

// (11)
// 수정 데이터 db 갱신하기 위해 데이터를 보내야한다. post
// 뷰 edit 수정 및 추가 input 태그에 id값을 가져온다.name value /hidden
// post메서드 정의 하고 dto 가져오기
// 이제 뷰에서도 id 데이터를 가지고오므로 dto에 id 추가 controller에서 log찍기
// db에서 기존 데이터 가져오기
// 기존데이터 target  -> 갱신하기 if문
// 수정 결과 페이지로 리다이렉트하기

//=============================================== (6) ~ (11) ===================================================//
// 단일 조회하기, 전체목록조회하기 , detail , list, edit 뷰 만들기, 각 뷰 링크로 연결하기, edit에 id값 추가, 데이터 갱신(수정)//
//==============================================================================================================//

// (12)
// 데이터 삭제하기
// detail에 delete 버틈 추가 a태그
// 삭제 컨트롤러(메서드)만들기
// 삭제할 데이터 찾기
// 데이터 삭제하기
//삭제 완료 메시지 남기기 RedirectAttributes
// 확인 메시지를 list에서 확인해야하나 header부분에 넣는게 좋을 듯 하므로 header부분에 div와 button 태그 추가
// div == 메시지 창 , button 닫기 버튼







