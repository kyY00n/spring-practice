package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 동시성문제가 있을 수 있어서 공유될 수 있는 변수는 concurrentHashMap을 사용해야한다.
    private static long sequence = 0L;
    // 시퀀스는 0, 1, .... 이렇게 멤버별 아이디 생성할때 쓸 애인데 얘도 동시성문제때문에 그냥 쓰면 안되고 어쩌구를 써야한다고 했다. 근데 잘 못들음

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member); //해시맵에 새로운 매핑 추가하기
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null도 잘 감싸서 반환해줌.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // 그냥 자동으로 Optional로 반환이 되는거임? 그렇다고 하긴했는데 이해 안됨.
    }

    @Override
    public List<Member> findAll() { // 실무에서 List를 되게 많이 씀 for문으로 다루기 좋대.
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
