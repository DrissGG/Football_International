/**
 * 
 */
package dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import foot.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Equipe;
import model.Match;

/**
 * 
 */
public class MatchDao implements IDAO<Match> {
	private final EntityManager entityManager = JPAUtils.getInstance().getEntityManager();

	@Override
	public void create(Match entity) {
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
			// Gérer les exceptions de manière appropriée dans un environnement de
			// production
			e.printStackTrace();
		}

	}

	public Match findExistingMatch(Date dateMatch, Equipe equipeLocal, Equipe equipeVisiteur) {
		EntityManager entityManager = JPAUtils.getInstance().getEntityManager();

		// Utilisez une requête JPQL pour rechercher un match existant
		String jpql = "SELECT m FROM Match m WHERE m.dateMatch = :dateMatch "
				+ "AND m.equipeLocal = :equipeLocal AND m.equipeVisiteur = :equipeVisiteur";

		TypedQuery<Match> query = entityManager.createQuery(jpql, Match.class);
		query.setParameter("dateMatch", dateMatch);
		query.setParameter("equipeLocal", equipeLocal);
		query.setParameter("equipeVisiteur", equipeVisiteur);

		List<Match> matches = query.getResultList();

		// Si un match existant est trouvé, retournez-le
		if (!matches.isEmpty()) {
			return matches.get(0);
		}

		// Aucun match existant trouvé
		return null;
	}

	@Override
	public void delete(Match entity) {
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
			e.printStackTrace(); // Gérer les exceptions de manière appropriée dans un environnement de
									// production
		}

	}

	public List<Match> findMatchesByTeamName(String teamName) {
		TypedQuery<Match> query = entityManager.createQuery(
				"SELECT m FROM Match m WHERE m.equipeLocal.nom = :teamName OR m.equipeVisiteur.nom = :teamName",
				Match.class);
		query.setParameter("teamName", teamName);
		return query.getResultList();
	}

	@Override
	public Match findById(Long id) {
		return entityManager.find(Match.class, id);
	}

	@Override
	public List<Match> findAll() {
		TypedQuery<Match> query = entityManager.createQuery("SELECT m FROM Match m", Match.class);
		return query.getResultList();
	}

	public List<Match> findByTeams(String equipeLocal, String equipeVisiteur, String date) {
		TypedQuery<Match> query = entityManager.createQuery(
				"SELECT m FROM Match m WHERE m.equipeLocal = :equipeLocal AND m.equipeVisiteur = :equipeVisiteur",
				Match.class);
		query.setParameter("equipeLocal", equipeLocal);
		query.setParameter("equipeVisiteur", equipeVisiteur);
		return query.getResultList();
	}

	public List<Match> findMatchesByTeamsAndDate(String equipeLocal, String equipeVisiteur, String dateString) {
	    // Convertir la chaîne de date en LocalDate
	    LocalDate date = LocalDate.parse(dateString);

	    // Convertir LocalDate en java.sql.Date
	    java.sql.Date sqlDate = java.sql.Date.valueOf(date);

	    TypedQuery<Match> query = entityManager.createQuery(
	            "SELECT m FROM Match m " +
	                    "WHERE m.equipeLocal.nom = :equipeLocal " +
	                    "AND m.equipeVisiteur.nom = :equipeVisiteur " +
	                    "AND m.dateMatch = :date",
	            Match.class);

	    query.setParameter("equipeLocal", equipeLocal);
	    query.setParameter("equipeVisiteur", equipeVisiteur);
	    query.setParameter("date", sqlDate);

	    return query.getResultList();
	}


	// Trouver les matchs d'une compétition donnée
	public List<Match> findByTournament(String competition) {
		TypedQuery<Match> query = entityManager.createQuery("SELECT m FROM Match m WHERE m.competition = :competition",
				Match.class);
		query.setParameter("competition", competition);
		return query.getResultList();
	}

	/**
	 * @param existingMatch
	 */
	public void update(Match existingMatch) {
		// TODO Auto-generated method stub

	}
	
	public List<Match> findMatchResultsBetweenTeams(String equipeLocalNom, String equipeVisiteurNom) {
	    String query = "SELECT m FROM Match m " +
	                   "WHERE (m.equipeLocal.nom = :equipeLocalNom AND m.equipeVisiteur.nom = :equipeVisiteurNom) " +
	                   "OR (m.equipeLocal.nom = :equipeVisiteurNom AND m.equipeVisiteur.nom = :equipeLocalNom) " +
	                   "ORDER BY m.dateMatch";

	    TypedQuery<Match> typedQuery = entityManager.createQuery(query, Match.class);
	    typedQuery.setParameter("equipeLocalNom", equipeLocalNom);
	    typedQuery.setParameter("equipeVisiteurNom", equipeVisiteurNom);

	    return typedQuery.getResultList();
	}


}
