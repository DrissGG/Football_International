/**
 * 
 */
package dao;

import java.util.List;

import foot.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Equipe;
import model.Match;

/**
 * 
 */
public class EquipeDao implements IDAO<Equipe>{
	private final EntityManager entityManager = JPAUtils.getInstance().getEntityManager();
	

	@Override
	public void create(Equipe entity) {
		EntityTransaction transaction = null;
		Equipe equipeExistant = findByName(entity.getNom());
		try {
			if (equipeExistant == null) {
	            transaction = entityManager.getTransaction();
	            transaction.begin();

	            if (entity.getId() == null) {
	                entityManager.persist(entity);
	            } else {
	                System.out.println("L'équipe existe déjà avec l'ID : " + entity.getId());
	            }

	            transaction.commit();
	        } else {
	            System.out.println("L'équipe existe déjà : " + equipeExistant.getNom());
	        }
			
		} catch (Exception e) {
			if(transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			// Gérer les exceptions de manière appropriée dans un environnement de production
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void delete(Equipe entity) {
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
	
	 // Méthode pour trouver une équipe par son nom
    public Equipe findByName(String teamName) {
        Query query = entityManager.createQuery("SELECT e FROM Equipe e WHERE e.nom = :teamName");
        query.setParameter("teamName", teamName);

        List<Equipe> result = query.getResultList();

        if (!result.isEmpty()) {
            return result.get(0); // Retourne la première équipe avec ce nom
        } else {
            return null; // Aucune équipe trouvée avec ce nom
        }
    }

	@Override
	public Equipe findById(Long id) {
		 return entityManager.find(Equipe.class, id);
	}

	@Override
	public List<Equipe> findAll() {
		TypedQuery<Equipe> query = entityManager.createQuery("SELECT e FROM Equipe e", Equipe.class);
        return query.getResultList();
	}
    
	
	 public List<Equipe> findTopWinningTeams(int n) {
	        String query = "SELECT e FROM Equipe e " +
	                       "ORDER BY calculateWinPercentage(e) DESC";

	        TypedQuery<Equipe> typedQuery = entityManager.createQuery(query, Equipe.class);
	        typedQuery.setMaxResults(n);

	        return typedQuery.getResultList();
	    }

	 private double calculateWinPercentage(Equipe equipe) {
		    int totalMatches = equipe.getMatchesLocaux().size() + equipe.getMatchesVisiteurs().size();
		    if (totalMatches == 0) {
		        return 0.0;
		    }

		    int totalWins = 0;
		    for (Match matchLocal : equipe.getMatchesLocaux()) {
		        if (isTeamWinner(equipe, matchLocal.getScoreFinalLocal(), matchLocal.getScoreFinalVisiteur())) {
		            totalWins++;
		        }
		    }

		    for (Match matchVisiteur : equipe.getMatchesVisiteurs()) {
		        if (isTeamWinner(equipe, matchVisiteur.getScoreFinalVisiteur(), matchVisiteur.getScoreFinalLocal())) {
		            totalWins++;
		        }
		    }

		    return ((double) totalWins / totalMatches) * 100.0;
		}

		private boolean isTeamWinner(Equipe equipe, int teamScore, int opponentScore) {
		    return (teamScore > opponentScore) || ((teamScore == opponentScore) && (Math.random() > 0.5));
		}

	
}
