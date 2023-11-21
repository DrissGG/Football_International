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
import model.Joueur;

/**
 * 
 */
public class JoueurDao implements IDAO<Joueur>{
	private final EntityManager entityManager = JPAUtils.getInstance().getEntityManager();

    @Override
    public void create(Joueur entity) {
        EntityTransaction transaction = null;
        try {
        	Joueur existingPlayer = findByName(entity.getNom());
        	if(existingPlayer == null) {
        		transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.persist(entity);
                transaction.commit();
        	}else {
                System.out.println("Le joueur existe déjà : " + existingPlayer.getNom());
            }
            
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans un environnement de production
        }
    }
    
    // Méthode pour trouver un joueur par son nom
    public Joueur findByName(String playerName) {
        Query query = entityManager.createQuery("SELECT j FROM Joueur j WHERE j.nom = :playerName");
        query.setParameter("playerName", playerName);

        List<Joueur> result = query.getResultList();

        if (!result.isEmpty()) {
            return result.get(0); // Retourne le premier joueur avec ce nom
        } else {
            return null; // Aucun joueur trouvé avec ce nom
        }
    }
    @Override
    public void delete(Joueur entity) {
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
    public Joueur findById(Long id) {
        return entityManager.find(Joueur.class, id);
    }

    @Override
    public List<Joueur> findAll() {
        TypedQuery<Joueur> query = entityManager.createQuery("SELECT j FROM Joueur j", Joueur.class);
        return query.getResultList();
    }

    // Trouver les joueurs d'une équipe
    public List<Joueur> findByEquipe(String equipe) {
        TypedQuery<Joueur> query = entityManager.createQuery("SELECT j FROM Joueur j WHERE j.equipe.nom = :equipe", Joueur.class);
        query.setParameter("equipe", equipe);
        return query.getResultList();
    }

}
