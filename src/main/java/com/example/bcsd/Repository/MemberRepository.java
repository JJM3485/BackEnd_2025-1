package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Member;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong();

    public Member save(Member member) {
        if (member.getId() == null) {
            long newId = idCount.incrementAndGet();
            member.setId(newId);
        }
        members.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return members.get(id);
    }

    @PostConstruct
    public void initData() {
        Member member1 = new Member("회원0", "user@test.com", "1234");
        save(member1);

        Member member2 = new Member("회원1", "user1@test.com", "5678");
        save(member2);

    }
}