/**
 * 
 */
package dao;

import java.util.List;

import foot.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Competition;

/**
 * 
 */
public class CompetitionDao implements IDAO<Competition>{
	
	private final EntityManager entityManager = JPAUtils.getInstance().getEntityManager();

    @Override
    public void create(Competition entity) {
        EntityTransaction transaction = null;
        Competition competitionExesting = findByName(entity.getNom());
        try {
        	if (competitionExesting == null) { 
        		transaction = entityManager.getTransaction();
        		transaction.begin();
        		entityManager.persist(entity);
        		transaction.commit();
        		}
        	else {
                System.out.println("La competition existe déjà : " + competitionExesting.getNom());
            }
        	
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans un environnement de production
        }
    }

    @Override
    public void delete(Competition entity) {
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

    // Trouver une compétition par son nom
    public Competition findByName(String competitionName) {
        TypedQuery<Competition> query = entityManager.createQuery(
                "SELECT c FROM Competition c WHERE c.nom = :competitionName", Competition.class);
        query.setParameter("competitionName", competitionName);

        List<Competition> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    
    @Override
    public Competition findById(Long id) {
        return entityManager.find(Competition.class, id);
    }

    @Override
    public List<Competition> findAll() {
        TypedQuery<Competition> query = entityManager.createQuery("SELECT c FROM Competition c", Competition.class);
        return query.getResultList();
    }


    // Trouver les compétitions remportées par une équipe
    public List<Competition> findCompetitionsWonByTeam(String nomEquipe) {
        TypedQuery<Competition> query = entityManager.createQuery("SELECT c FROM Competition c WHERE c.equipeGagnante.nom = :nomEquipe", Competition.class);
        query.setParameter("nomEquipe", nomEquipe);
        return query.getResultList();
    }
}
