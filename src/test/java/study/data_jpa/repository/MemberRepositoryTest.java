package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Member;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
//        Member member = new Member("userA");
//        Member savedMember = memberRepository.save(member);
//
//        Member findMember = memberRepository.findById(savedMember.getId()).get();
//
//        Assertions.assertThat(findMember).isEqualTo(member);
    }

}