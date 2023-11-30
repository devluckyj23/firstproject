package com.example.firstproject.controller;


import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MemberController { //스프링 부트가 미리 생성해 놓은 레파지토리 객체 주입(DI) 이것을 의존성 주입이라고한다. (Dipendency Injection)
    //등록페이지
    @GetMapping("/signup")
    public String newMember(){
        return"/members/join";
    }

    @Autowired
    private MemberRepository memberRepository;  //memmberRepository 객체 선언
    
    @PostMapping("/join")
    public String joinMember(MemberForm form){ //폼 데이터를 dto로 받기

        log.info(form.toString());
        //System.out.println(form.toString());

        //1. dto를 엔티티로 변환
         Member member = form.toEntity();
        log.info(member.toString());
         //System.out.println(member.toString());

        // -> form객체의 toEntity()메서드를 호출해서 그 반환 값을 Article 타입의 article 엔티티에 저장한다.
        // -> 이를 위해서는 엔티티 클래스 부터 만들어야한다. Article 클래스가 바로 엔티티 클래스


        //2.레파지토리로 엔티티를 db에 저장
        Member saved = memberRepository.save(member);//member 엔티티를 저장해 saved 객체에 반환한다.
        // 여기서 다른 스프링부트가 다른점은 자바 사용할 때, 방금처럼 articleRepository를 만들고 그안의 save메서드를 활용하려면,
        // 먼저 객체를 만들었어야한다. new를 사용해서.
        // 하지만 스프링부트는 자동으로 객체를 만들어 주기때문에 new로 객체를 만드는 과정이 생략가능하다.
        // @Autowired를 사용하면 자동 생성된 객체를 가져다 연결할 수 있다.

        //System.out.println(saved.toString());
        log.info(saved.toString());

        return"redirect:/members/" + saved.getId();
    }
    //특정id조회
    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        //1. id를 조회해 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        //2. 모델에 데이터 등록하기
        model.addAttribute("member",memberEntity);
        //3. 뷰 페이지 반환하기
        return"members/show";
    }
    
    //목록페이지
    @GetMapping("/members")
    public String index(Model model){
        ArrayList<Member> memberEntityList = memberRepository.findAll();
        model.addAttribute("memberList",memberEntityList);

        return"members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String memEdit(@PathVariable Long id,Model model){
        Member memberEntity= memberRepository.findById(id).orElse(null);
        model.addAttribute("member",memberEntity);
        return"members/memEdit";
    }
    @PostMapping("/members/update")
    public String memUpdate(MemberForm form){
        log.info(form.toString());

        // dto 변환
        Member memberEntity = form.toEntity();
        // db 저장
        // 1. 기존 데이터 가져오기

        // 2. if 조건문
            if(memberEntity.getId() != null){
                Member saved = memberRepository.save(memberEntity);
            }
        //
        return"redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제를 요청했음.");
        //1.삭제할 데이터 찾기
       Member member= memberRepository.findById(id).orElse(null);
        //2.데이터 삭제하기
        if(member != null){
            memberRepository.delete(member); // delete 대상은 member id뿐만아니라 다른 데이터도 같이 삭제니까!
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다!");

        }
        //3.리다이렉트시키기
        return "redirect:/members";
    }

}