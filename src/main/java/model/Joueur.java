/**
 * 
 */
package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

/**
 * 
 */
@Entity
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "joueur")
    private List<Buteur> buteurs;
    
    @ManyToMany
    @JoinTable(
            name = "equipe_joueur",
            joinColumns = @JoinColumn(name = "joueur_id"),
            inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private List<Equipe> equipes;

	/** Constructors
	 * 
	 */
	public Joueur() {
		super();
	}

	/** Constructors
	 * @param id
	 * @param nom
	 */
	public Joueur(Long id, String nom) {
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
	 * @return the buteurs
	 */
	public List<Buteur> getButeurs() {
		return buteurs;
	}

	/** Setters
	 * @param buteurs the buteurs to set
	 */
	public void setButeurs(List<Buteur> buteurs) {
		this.buteurs = buteurs;
	}

	/** Getters
	 * @return the equipes
	 */
	public List<Equipe> getEquipes() {
		return equipes;
	}

	/** Setters
	 * @param equipes the equipes to set
	 */
	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

}
