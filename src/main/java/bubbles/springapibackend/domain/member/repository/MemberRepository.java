package bubbles.springapibackend.domain.member.repository;

import bubbles.springapibackend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findByFkUserIdUser(Integer userId);
}