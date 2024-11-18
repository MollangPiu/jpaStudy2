package kr.soft.campus;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Area;
import kr.soft.campus.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDB {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitService initService;

    @PostConstruct
    private void init() {
        logger.info("init ★★★★★★★");
        initService.areaInsert();
        initService.memberInsert();
    }

    @Component
    static class InitService {

        @Autowired
        private EntityManager em;


        @Transactional
        public void areaInsert() {

            Area area1 = new Area();
            area1.setAreaName("서울");
            em.persist(area1);

            Area area2 = new Area();
            area2.setAreaName("부산");
            em.persist(area2);

            Area area3 = new Area();
            area3.setAreaName("인천");
            em.persist(area3);

            Area area4 = new Area();
            area4.setAreaName("대구");
            em.persist(area4);

            Area area5 = new Area();
            area5.setAreaName("울산");
            em.persist(area5);

            Area area6 = new Area();
            area6.setAreaName("대전");
            em.persist(area6);
        }

        @Transactional
        public void memberInsert() {
            Member member1 = new Member();
            member1.setUserId("hong");
            member1.setUserName("홍길동");
            member1.setUserPw("1234");
            member1.setArea(em.find(Area.class, 1));
            em.persist(member1);

            Member member2 = new Member();
            member2.setUserId("super");
            member2.setUserPw("1234");
            member2.setUserName("superMan");
            member2.setArea(em.find(Area.class, 2));
            em.persist(member2);

            Member member3 = new Member();
            member3.setUserId("james");
            member3.setUserPw("1234");
            member3.setUserName("jamesBoy");
            member3.setArea(em.find(Area.class, 2));
            em.persist(member3);

            Member member4 = new Member();
            member4.setUserId("dol12");
            member4.setUserPw("1234");
            member4.setUserName("dola Uni");
            member4.setArea(em.find(Area.class, 3));
            em.persist(member4);
        }
    }
}
