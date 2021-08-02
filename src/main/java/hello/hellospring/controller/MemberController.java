package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/new")
    @ResponseBody
    public Response memberApi(@RequestParam("name") String name) {
        Member newMember = new Member();
        newMember.setName(name);
        try {
            memberService.join(newMember);
            return new Response(200, "회원가입 성공", newMember);
        } catch (IllegalStateException e) {
            return new Response(500, e.getMessage());
        }

    }

    @GetMapping("members")
    @ResponseBody
    public Response membersApi() {
        return new Response(200, "전체 회원 조회 성공", memberService.findMembers());
    }

    private class Response {
        public int statusCode;
        public String responseMessage;
        public Member memberData;
        public List<Member> membersData;

        public Response(int statusCode, String rm) {
            this.statusCode = statusCode;
            this.responseMessage = rm;
        }

        public Response(int statusCode, String rm, Member m) {
            this.statusCode = statusCode;
            this.responseMessage = rm;
            this.memberData = m;
        }

        public Response(int statusCode, String rm, List<Member> m) {
            this.statusCode = statusCode;
            this.responseMessage = rm;
            this.membersData = m;
        }
    }
}
