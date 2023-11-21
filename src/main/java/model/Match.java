/**
 * 
 */
package model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 
 */
@Entity
@Table(name="matches")
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dateMatch;
	
	private int scoreFinalLocal;
	private int scoreFinalVisiteur;
	private String city;
	private String pays;
	

	private boolean neutre;
	
	@ManyToOne
	@JoinColumn(name = "equipeLocal_id")
    private Equipe equipeLocal;

   
	@ManyToOne
    @JoinColumn(name = "equipeVisiteur_id")
    private Equipe equipeVisiteur;
    
    @OneToOne(mappedBy = "match")
    private TirauBut tirauBut;
    
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
    
    /** Constructors
   	 * 
   	 */
   	public Match() {
   		super();
   	}

	/** Constructors
	 * @param id
	 * @param dateMatch
	 * @param scoreFinalLocal
	 * @param scoreFinalVisiteur
	 * @param city
	 * @param neutre
	 */
	public Match(Long id, Date dateMatch, int scoreFinalLocal, int scoreFinalVisiteur, String city, boolean neutre, String pays) {
		super();
		this.id = id;
		this.dateMatch = dateMatch;
		this.scoreFinalLocal = scoreFinalLocal;
		this.scoreFinalVisiteur = scoreFinalVisiteur;
		this.city = city;
		this.pays = pays;
		this.neutre = neutre;
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
	 * @return the dateMatch
	 */
	public Date getDateMatch() {
		return dateMatch;
	}

	/** Setters
	 * @param dateMatch the dateMatch to set
	 */
	public void setDateMatch(LocalDate localDate) {
		this.dateMatch = Date.valueOf(localDate);
	}

	/** Getters
	 * @return the scoreFinalLocal
	 */
	public int getScoreFinalLocal() {
		return scoreFinalLocal;
	}

	/** Setters
	 * @param scoreFinalLocal the scoreFinalLocal to set
	 */
	public void setScoreFinalLocal(int scoreFinalLocal) {
		this.scoreFinalLocal = scoreFinalLocal;
	}

	/** Getters
	 * @return the scoreFinalVisiteur
	 */
	public int getScoreFinalVisiteur() {
		return scoreFinalVisiteur;
	}

	/** Setters
	 * @param scoreFinalVisiteur the scoreFinalVisiteur to set
	 */
	public void setScoreFinalVisiteur(int scoreFinalVisiteur) {
		this.scoreFinalVisiteur = scoreFinalVisiteur;
	}

	/** Getters
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/** Setters
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/** Getters
	 * @return the neutre
	 */
	public boolean isNeutre() {
		return neutre;
	}

	/** Setters
	 * @param neutre the neutre to set
	 */
	public void setNeutre(boolean neutre) {
		this.neutre = neutre;
	}

	/** Getters
	 * @return the equipeLocal
	 */
	public Equipe getEquipeLocal() {
		return equipeLocal;
	}

	/** Setters
	 * @param equipeLocal the equipeLocal to set
	 */
	public void setEquipeLocal(Equipe equipeLocal) {
		this.equipeLocal = equipeLocal;
	}

	/** Getters
	 * @return the equipeVisiteur
	 */
	public Equipe getEquipeVisiteur() {
		return equipeVisiteur;
	}

	/** Setters
	 * @param equipeVisiteur the equipeVisiteur to set
	 */
	public void setEquipeVisiteur(Equipe equipeVisiteur) {
		this.equipeVisiteur = equipeVisiteur;
	}

	/** Getters
	 * @return the tirauBut
	 */
	public TirauBut getTirauBut() {
		return tirauBut;
	}

	/** Setters
	 * @param tirauBut the tirauBut to set
	 */
	public void setTirauBut(TirauBut tirauBut) {
		this.tirauBut = tirauBut;
	}

	/** Getters
	 * @return the competition
	 */
	public Competition getCompetition() {
		return competition;
	}

	/** Setters
	 * @param competition the competition to set
	 */
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	/** Getters
	 * @return the pays
	 */
	public String getPays() {
		return pays;
	}

	/** Setters
	 * @param pays the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", dateMatch=" + dateMatch + ", scoreFinalLocal=" + scoreFinalLocal
				+ ", scoreFinalVisiteur=" + scoreFinalVisiteur + ", city=" + city + ", pays=" + pays + ", neutre="
				+ neutre + ", equipeLocal=" + equipeLocal + ", equipeVisiteur=" + equipeVisiteur + ", tirauBut="
				+ tirauBut + ", competition=" + competition + "]";
	}
    
	
	
}
