package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {

        // optional의 메서드 isPresent를 사용한다.
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // 옵셔널 안에 값이 있으면.. 콜백 실행
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        memberRepository.save(member);
        return member.getId();
    }

    // 전체회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // id로 회원 조회
    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 이름으로 회원 조회
    public Optional<Member> findMemberByName(String name) { return memberRepository.findByName(name); }
}
