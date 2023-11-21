/**
 * 
 */
package model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 
 */
@Entity
public class TirauBut {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dateTir;
	
	@OneToOne
	@JoinColumn(name="match_id")
	private Match match;
	
	@ManyToOne
    @JoinColumn(name = "vainqueur_id")
    private Equipe vainqueur;

    @ManyToOne
    @JoinColumn(name = "first_id")
    private Equipe firstTirAuBut;
	/** Constructors
	 * 
	 */
	public TirauBut() {
		super();
	}

	/** Constructors
	 * @param id
	 * @param dateTir
	 * @param match
	 */
	public TirauBut(Long id, Date dateTir, Match match) {
		super();
		this.id = id;
		this.dateTir = dateTir;
		this.match = match;
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
	 * @return the dateTir
	 */
	public Date getDateTir() {
		return dateTir;
	}

	/** Setters
	 * @param dateTir the dateTir to set
	 */
	public void setDateTir(Date dateTir) {
		this.dateTir = dateTir;
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

	/** Getters
	 * @return the vainqueur
	 */
	public Equipe getVainqueur() {
		return vainqueur;
	}

	/** Setters
	 * @param vainqueur the vainqueur to set
	 */
	public void setVainqueur(Equipe vainqueur) {
		this.vainqueur = vainqueur;
	}

	/** Getters
	 * @return the firstTirAuBut
	 */
	public Equipe getFirstTirAuBut() {
		return firstTirAuBut;
	}

	/** Setters
	 * @param firstTirAuBut the firstTirAuBut to set
	 */
	public void setFirstTirAuBut(Equipe firstTirAuBut) {
		this.firstTirAuBut = firstTirAuBut;
	}

	
    
    
	
}
