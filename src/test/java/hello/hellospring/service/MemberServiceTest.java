package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memoryMemberRepository;
    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();

        // 리포지토리가 private 멤버변수여서 이렇게 생성할때 넣어준다!
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() {
        Member member = new Member();
        member.setName("hello");

        Long saveId = memberService.join(member);

        Member findMember = memberService.findMemberById(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void duplicateMemberException() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        // 서비스 수준에서의 테스트는 join으로 멤버를 추가한다.
        Member member1 = new Member();
        member1.setName("윤가영");
        Member member2 = new Member();
        member2.setName("잉짜용");

        memberService.join(member1);
        memberService.join(member2);

        Assertions.assertThat(memberService.findMembers().size()).isEqualTo(2);
    }

    @Test
    void findMemberById() {
        Member member = new Member();
        member.setName("윤가영");

        memberService.join(member);
        Member foundMember = memberService.findMemberById(member.getId()).get();

        Assertions.assertThat(foundMember).isEqualTo(member);
    }

    @Test
    void findMemberByname() {
        Member member = new Member();
        member.setName("윤가영");

        memberService.join(member);
        Member foundMember = memberService.findMemberByName(member.getName()).get();

        Assertions.assertThat(foundMember).isEqualTo(member);
    }
}