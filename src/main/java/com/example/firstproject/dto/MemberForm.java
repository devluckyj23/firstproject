package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {
    // 입력 폼에서 제목과 내용 2가지를 전송할 예정이니 dto에서도 필드 2개가 필요하다
    private Long id;
    private String email;
    private String password;
//    //전송 받은 제목과 내용을 필드에 저장하는 생성자 추가
//    public MemberForm(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }


//    //위에서 데이터를 잘 받았는지 확인할 toString() 메서드 추가
//    @Override
//    public String toString() {
//        return "MemberForm{" +
//                "email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }

    //toEntity 메서드
    public Member toEntity() {
        return new Member(id,email,password);
    }
}
