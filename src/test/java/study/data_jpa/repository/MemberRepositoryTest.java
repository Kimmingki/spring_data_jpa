package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.MemberDTO;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Rollback(value = false)
@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() {
//        Member member = new Member("userA");
//        Member savedMember = memberRepository.save(member);
//
//        Member findMember = memberRepository.findById(savedMember.getId()).get();
//
//        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Team teamA = new Team("Team A");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 20, teamA);
        Member member2 = new Member("member2", 28, teamA);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 전체 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all).hasSize(2);

        // 카운팅 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(findMember1);
        memberRepository.delete(findMember2);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDTO() {
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member m1 = new Member("AAA", 10, teamA);
        Member m2 = new Member("BBB", 20, teamA);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<MemberDTO> result = memberRepository.findMemberDTO();
        for (MemberDTO memberDTO : result) {
            System.out.println("memberDTO.toString() = " + memberDTO.toString());
        }
    }

    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 20));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 20));
        memberRepository.save(new Member("member5", 20));

        int age = 20;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username");

        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        Page<MemberDTO> toMap = page.map(m -> new MemberDTO(m.getId(), m.getUsername(), m.getTeam().getName()));

        List<Member> content = page.getContent();

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 20));
        memberRepository.save(new Member("member2", 22));
        memberRepository.save(new Member("member3", 19));
        memberRepository.save(new Member("member4", 24));

        // 벌크 연산 이후에는 영속성 컨텍스의 데이터를 flush 후 clear 해야함
        int resultCount = memberRepository.bulkAgePlus(20);
//        em.flush();
//        em.clear();

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        memberRepository.save(new Member("member1", 20, teamA));
        memberRepository.save(new Member("member2", 22, teamB));

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }
}