package com.example.bcsd.Service;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.Member;
import com.example.bcsd.Exception.AllException;
import com.example.bcsd.Repository.ArticleRepository;
import com.example.bcsd.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Member createMember(Member member) {
        if (member.getName() == null || member.getEmail() == null || member.getPassword() == null) {
            throw new AllException(HttpStatus.BAD_REQUEST, "필수 값이 누락되었습니다.");
        }

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new AllException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
        return member;
    }

    @Transactional
    public Member updateMember(Long id, Member member) {
        Member existMember = memberRepository.findById(id);
        if (existMember == null) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }

        if (member.getEmail() != null && !member.getEmail().equals(existMember.getEmail())) {
            if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
                throw new AllException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
            }
        }

        memberRepository.update(id, member);
        return member;
    }

    @Transactional
    public void deleteMember(Long id) {
        Member existMember = memberRepository.findById(id);
        if (existMember == null) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }

        List<Article> allArticles = articleRepository.findAll();
        boolean hasArticle = allArticles.stream()
                .anyMatch(article -> article.getAuthorId().equals(id));

        if (hasArticle) {
            throw new AllException(HttpStatus.BAD_REQUEST, "작성한 게시글이 존재하여 삭제할 수 없습니다.");
        }

        memberRepository.delete(id);
    }
}