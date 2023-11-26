package com.example.firstproject.repository;

import com.example.firstproject.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface MemberRepository extends CrudRepository<Member,Long> {
    // CrudRepository 상속 받는데 JPA에서 제공되는 레파지터리 인터페이스를 활용한다.(엔티티를 생성,조회,수정,삭제 할 수 있음)


    @Override
    ArrayList<Member> findAll();
}
