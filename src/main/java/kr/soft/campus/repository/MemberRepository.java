package kr.soft.campus.repository;

import jakarta.persistence.EntityManager;
import kr.soft.campus.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @Autowired
    private EntityManager em;

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Member findByUserId(String userId) {
        return em.createNamedQuery("Member.findByUserId", Member.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public List<Member> findAllWithArea() {
        return em.createQuery("select m from Member m"
                + " join fetch m.area o", Member.class)
                .getResultList();
    }


}
