package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource datasource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.datasource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memoryMemberRepository()); // 생성자에 리포지토리가 필요함.
    }

    @Bean
    public MemberRepository memoryMemberRepository() {
        // return new MemoryMemberRepository();
        return new JdbcMemberRepository(datasource);
    }
}
