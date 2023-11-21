/**
 * 
 */
package dao;

import java.util.List;

import foot.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.TirauBut;

/**
 * 
 */
public class TirauButDao implements IDAO<TirauBut>{
	private final EntityManager entityManager = JPAUtils.getInstance().getEntityManager();

    @Override
    public void create(TirauBut entity) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans un environnement de production
        }
    }

    @Override
    public void delete(TirauBut entity) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans un environnement de production
        }
    }

    @Override
    public TirauBut findById(Long id) {
        return entityManager.find(TirauBut.class, id);
    }

    @Override
    public List<TirauBut> findAll() {
        TypedQuery<TirauBut> query = entityManager.createQuery("SELECT t FROM TirauBut t", TirauBut.class);
        return query.getResultList();
    }


    // Trouver les tirs au but d'un match
    public TirauBut findByMatch(Long matchId) {
        TypedQuery<TirauBut> query = entityManager.createQuery("SELECT t FROM TirauBut t WHERE t.match.id = :matchId", TirauBut.class);
        query.setParameter("matchId", matchId);
        return query.getSingleResult(); // Si vous vous attendez à un seul résultat
    }

}
