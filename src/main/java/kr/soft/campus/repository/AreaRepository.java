package kr.soft.campus.repository;

import jakarta.persistence.EntityManager;
import kr.soft.campus.domain.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AreaRepository {

    @Autowired
    private EntityManager em;

    public Area findById(int id) {
        return em.find(Area.class, id);
    }
}
