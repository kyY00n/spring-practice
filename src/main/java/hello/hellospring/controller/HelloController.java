package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello");
        return "hello"; // 템플릿 파일의 이름 즉 hello.html에 데이터 넣고 렌더링하겠다는 뜻
        // 그럼 스프링부트가 자동으로 resources/templates/hello.html을 찾는다.
    }
    // 컨트롤러에서 리턴값으로 문자를 반환하면 viewResolver가 화면을 찾아서 처리한다.

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", defaultValue = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template"; //hello-template.html로 model을 보내준다.
    }

    @GetMapping("hello-string")
    @ResponseBody // 내가 지금 반환하는 값을 response body에 직접 넣을거야.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello { // static 클래스로 선언하면 밖에서 쓸 수 있음.
        private String name; // getter setter 단축키: cmd+n

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
