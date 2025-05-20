package br.com.mottu.configuration;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class HibernateFilterConfig {

    @PersistenceContext
    private EntityManager entityManager;

    public void habilitarFiltroDeletados() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
    }
}
