/**
 * 
 */
package dao;

import java.util.List;

import jakarta.persistence.Query;

import foot.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Equipe;

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
    
	
}
