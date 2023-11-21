/**
 * 
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import foot.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Buteur;

/**
 * 
 */
public class ButeurDao implements IDAO<Buteur>{

	 private final EntityManager entityManager = JPAUtils.getInstance().getEntityManager();

	    @Override
	    public void create(Buteur entity) {
	        EntityTransaction transaction = null;
	        try {
	        	Buteur existingButeur = findExistingButeur(entity);
	            if (existingButeur == null) {
	                // Aucun doublon trouvé, ajouter le nouveau buteur
	            	transaction = entityManager.getTransaction();
		            transaction.begin();
		            entityManager.persist(entity);
		            transaction.commit();
	            } else {
	                // Un doublon a été trouvé, vous pouvez choisir de ne rien faire ou gérer cela d'une autre manière
	                System.out.println("Le buteur existe déjà dans la base de données.");
	            }
	            
	        } catch (Exception e) {
	            if (transaction != null && transaction.isActive()) {
	                transaction.rollback();
	            }
	            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans un environnement de production
	        }
	    }
	    
	    // Méthode pour trouver un buteur existant avec les mêmes propriétés
	    private Buteur findExistingButeur(Buteur entity) {
	        TypedQuery<Buteur> query = entityManager.createQuery(
	            "SELECT b FROM Buteur b WHERE b.joueur = :joueur AND b.match = :match", Buteur.class);
	        query.setParameter("joueur", entity.getJoueur());
	        query.setParameter("match", entity.getMatch());

	        List<Buteur> resultList = query.getResultList();
	        return resultList.isEmpty() ? null : resultList.get(0);
	    }
	    
	    public Buteur findByName(String playerName) {
	        TypedQuery<Buteur> query = entityManager.createQuery(
	            "SELECT b FROM Buteur b WHERE b.joueur.nom = :playerName", Buteur.class);
	        query.setParameter("playerName", playerName);

	        List<Buteur> resultList = query.getResultList();
	        return resultList.isEmpty() ? null : resultList.get(0);
	    }

	    @Override
	    public void delete(Buteur entity) {
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
	    public Buteur findById(Long id) {
	        return entityManager.find(Buteur.class, id);
	    }

	    @Override
	    public List<Buteur> findAll() {
	        TypedQuery<Buteur> query = entityManager.createQuery("SELECT b FROM Buteur b", Buteur.class);
	        return query.getResultList();
	    }

	    public List<Buteur> findTopScorers(int n) {
	        String query = "SELECT b FROM Buteur b ORDER BY b.minuteButMarque DESC";
	        TypedQuery<Buteur> typedQuery = entityManager.createQuery(query, Buteur.class);
	        typedQuery.setMaxResults(n);
	        return typedQuery.getResultList();
	    }

	    public List<Buteur> findTopScorersByCompetition(String competitionName, int n) {
	        String query = "SELECT b FROM Buteur b " +
	                       "JOIN b.match m JOIN m.competition c " +
	                       "WHERE c.nom = :competitionName " +
	                       "ORDER BY b.minuteButMarque DESC";
	        
	        TypedQuery<Buteur> typedQuery = entityManager.createQuery(query, Buteur.class);
	        typedQuery.setParameter("competitionName", competitionName);
	        typedQuery.setMaxResults(n);

	        return typedQuery.getResultList();
	    }

	    public List<Buteur> findTopScorersByTeam(String teamName, int n) {
	        String query = "SELECT b FROM Buteur b " +
	                       "JOIN b.match m " +
	                       "WHERE m.equipeLocal.nom = :teamName OR m.equipeVisiteur.nom = :teamName " +
	                       "ORDER BY b.minuteButMarque DESC";

	        TypedQuery<Buteur> typedQuery = entityManager.createQuery(query, Buteur.class);
	        typedQuery.setParameter("teamName", teamName);
	        typedQuery.setMaxResults(n);

	        return typedQuery.getResultList();
	    }
}
