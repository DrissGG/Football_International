/**
 * 
 */
package model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

/**
 * 
 */
@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "equipeLocal")
    private List<Match> matchesLocaux;

    @OneToMany(mappedBy = "equipeVisiteur")
    private List<Match> matchesVisiteurs;
    
    @OneToMany(mappedBy = "firstTirAuBut")
    private List<TirauBut> premierTirauBut;
    
    @OneToMany(mappedBy = "vainqueur")
    private List<TirauBut> tirsAuButGagnes;
    
    @OneToMany(mappedBy = "equipe")
    private List<Buteur> buteurs;
    
    @ManyToMany(mappedBy = "equipes")
    private List<Joueur> joueurs;
    

	/** Constructors
	 * @param id
	 * @param nom
	 */
	public Equipe(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}
    

	/** Constructors
	 * 
	 */
	public Equipe() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the matchesLocaux
	 */
	public List<Match> getMatchesLocaux() {
		return matchesLocaux;
	}

	/** Setters
	 * @param matchesLocaux the matchesLocaux to set
	 */
	public void setMatchesLocaux(List<Match> matchesLocaux) {
		this.matchesLocaux = matchesLocaux;
	}

	/** Getters
	 * @return the matchesVisiteurs
	 */
	public List<Match> getMatchesVisiteurs() {
		return matchesVisiteurs;
	}

	/** Setters
	 * @param matchesVisiteurs the matchesVisiteurs to set
	 */
	public void setMatchesVisiteurs(List<Match> matchesVisiteurs) {
		this.matchesVisiteurs = matchesVisiteurs;
	}

	/** Getters
	 * @return the premierTirauBut
	 */
	public List<TirauBut> getPremierTirauBut() {
		return premierTirauBut;
	}

	/** Setters
	 * @param premierTirauBut the premierTirauBut to set
	 */
	public void setPremierTirauBut(List<TirauBut> premierTirauBut) {
		this.premierTirauBut = premierTirauBut;
	}

	/** Getters
	 * @return the tirsAuButGagnes
	 */
	public List<TirauBut> getTirsAuButGagnes() {
		return tirsAuButGagnes;
	}

	/** Setters
	 * @param tirsAuButGagnes the tirsAuButGagnes to set
	 */
	public void setTirsAuButGagnes(List<TirauBut> tirsAuButGagnes) {
		this.tirsAuButGagnes = tirsAuButGagnes;
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
	 * @return the joueurs
	 */
	public List<Joueur> getJoueurs() {
		return joueurs;
	}


	/** Setters
	 * @param joueurs the joueurs to set
	 */
	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}


	@Override
	public String toString() {
		return "Equipe [id=" + id + ", nom=" + nom + "]";
	}

    
    
    
}
