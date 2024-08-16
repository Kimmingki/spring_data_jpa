package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;

    /**
     * 회원 저장
     * @param member 회원 객체
     * @return
     */
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    /**
     * 회원 삭제
     * @param member 회원 객체
     */
    public void delete(Member member) {
        em.remove(member);
    }

    /**
     * 회원 전체 조회
     * @return
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    /**
     * 특정 회원 조회
     * @param id 회원 ID
     * @return
     */
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    /**
     * 특정 회원 조회 (옵셔널)
     * @param id
     * @return
     */
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    /**
     * 회원 수 조회
     * @return
     */
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
    }
}
