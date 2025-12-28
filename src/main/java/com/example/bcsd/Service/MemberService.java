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
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            throw new AllException(HttpStatus.BAD_REQUEST, "이름은 필수값입니다.");
        }

        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            throw new AllException(HttpStatus.BAD_REQUEST, "이메일은 필수값입니다.");
        }

        if (member.getPassword() == null || member.getPassword().trim().isEmpty()) {
            throw new AllException(HttpStatus.BAD_REQUEST, "비밀번호는 필수값입니다.");
        }

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new AllException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));
    }

    @Transactional
    public Member updateMember(Long id, Member member) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));

        if (member.getEmail() != null && !member.getEmail().trim().isEmpty() && !member.getEmail().equals(existMember.getEmail())) {
            if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
                throw new AllException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
            }
            existMember.setEmail(member.getEmail());
        }

        if (member.getName() != null && !member.getName().trim().isEmpty()) {
            existMember.setName(member.getName());
        }

        if (member.getPassword() != null && !member.getPassword().trim().isEmpty()) {
            existMember.setPassword(member.getPassword());
        }

        return existMember;
    }

    @Transactional
    public void deleteMember(Long id) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));

        List<Article> allArticles = articleRepository.findAll();
        boolean hasArticle = allArticles.stream()
                .anyMatch(article -> article.getAuthor().getId().equals(id));

        if (hasArticle) {
            throw new AllException(HttpStatus.BAD_REQUEST, "작성한 게시글이 존재하여 삭제할 수 없습니다.");
        }

        memberRepository.delete(existMember);
    }
}
