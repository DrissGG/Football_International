/**
 * 
 */
package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 
 */
@Entity
public class Buteur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int minuteButMarque;
	private Boolean penalty;
	private Boolean butContreSansCamp;
	
	@ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "joueur_id")
    private Joueur joueur;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

	/** Constructors
	 * 
	 */
	public Buteur() {
		super();
	}

	/** Constructors
	 * @param id
	 * @param minuteButMarque
	 * @param penalty
	 * @param butContreSansCamp
	 */
	public Buteur(Long id, int minuteButMarque, Boolean penalty, Boolean butContreSansCamp) {
		super();
		this.id = id;
		this.minuteButMarque = minuteButMarque;
		this.penalty = penalty;
		this.butContreSansCamp = butContreSansCamp;
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
	 * @return the minuteButMarque
	 */
	public int getMinuteButMarque() {
		return minuteButMarque;
	}

	/** Setters
	 * @param minuteButMarque the minuteButMarque to set
	 */
	public void setMinuteButMarque(int minuteButMarque) {
		this.minuteButMarque = minuteButMarque;
	}

	/** Getters
	 * @return the penalty
	 */
	public Boolean getPenalty() {
		return penalty;
	}

	/** Setters
	 * @param penalty the penalty to set
	 */
	public void setPenalty(Boolean penalty) {
		this.penalty = penalty;
	}

	/** Getters
	 * @return the butContreSansCamp
	 */
	public Boolean getButContreSansCamp() {
		return butContreSansCamp;
	}

	/** Setters
	 * @param butContreSansCamp the butContreSansCamp to set
	 */
	public void setButContreSansCamp(Boolean butContreSansCamp) {
		this.butContreSansCamp = butContreSansCamp;
	}

	/** Getters
	 * @return the equipe
	 */
	public Equipe getEquipe() {
		return equipe;
	}

	/** Setters
	 * @param equipe the equipe to set
	 */
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	/** Getters
	 * @return the joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/** Setters
	 * @param joueur the joueur to set
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	/** Getters
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/** Setters
	 * @param match the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
	}
    
    
}
