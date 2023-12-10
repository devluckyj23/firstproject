package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;
    //get
    //전체조회
    @GetMapping("/api/coffees")
    public List<Coffee> list(){
        return coffeeRepository.findAll();
    }

    // id를 불러와 확인하는것이므로 pathvaluable 선언!
    @GetMapping("/api/coffees/{id}")
    public Coffee detail(@PathVariable Long id){
        return coffeeRepository.findById(id).orElse(null);
    }

    //post
    // dto -> toEntity , db저장
    @PostMapping("/api/coffees")
    public Coffee newCoffee(@RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        return coffeeRepository.save(coffee);
    }
    //patch
    // 메서드 타입을 ResponseEntity
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id,@RequestBody CoffeeDto coffeeDto){
        //1. dto -> 엔티티변환
        Coffee coffee = coffeeDto.toEntity();
        log.info("id:{},price:{}",id,coffee.toString()); // id와 coffee 데이터 로깅
        //2. 타깃 조회하기
        Coffee target= coffeeRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
            // 400, 잘못된 요청 응답!
            if(target == null || id != coffee.getId()){
                log.info("잘못된 요청! id:{},coffee:{}",id,coffee.toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        //4. 업데이트 및 정상 응답(200)하기
            target.patch(coffee);
        // target에는 기존데이터, coffee에는 수정할 데이터가 있다.따라서 기존데이터에 새 데이터를 붙여줘야 수정할때 기존데이터가 지워지지 않는다.
        //Article updated = articleRepository.save(article); 해당 코드는 입력하지 않은 컬럼의 데이터가 null로 기존데이터가 지워진다.

        //수정 내용 ( 위에서 기존 target에 새로작성한coffee의 데이터를 갱신했으므로 target이 이제 최종 데이터임 )
        // DB에 최종 저장
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated); //정상응답으로 updated의 데이터를 반환
    }
    //delete
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){

    //1. 대상 찾기
      Coffee coffee = coffeeRepository.findById(id).orElse(null);
    //2. 잘못된 요청 처리하기
        // 찾은 데이터 coffee가 null이라면, BAD_REQUEST - null을 반환한다.
        if(coffee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    //3. 대상 삭제하기
        coffeeRepository.delete(coffee);
        // 4. 정상 리턴
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
