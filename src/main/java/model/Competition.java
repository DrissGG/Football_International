/**
 * 
 */
package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * 
 */
@Entity
public class Competition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	@OneToMany(mappedBy = "competition")
	private List<Match> matches;

	/** Constructors
	 * 
	 */
	public Competition() {
		super();
	}

	/** Constructors
	 * @param id
	 * @param nom
	 */
	public Competition(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	/** Getters
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/** Setters
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/** Getters
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setters
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getters
	 * @return the matches
	 */
	public List<Match> getMatches() {
		return matches;
	}

	/** Setters
	 * @param matches the matches to set
	 */
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	
	
}
