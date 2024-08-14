package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Member;

@Transactional
@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
//        Member member = new Member("userA");
//        Member savedMember = memberJpaRepository.save(member);
//
//        Member findMember = memberJpaRepository.find(savedMember.getId());
//
//        Assertions.assertThat(findMember).isEqualTo(member);
    }
}