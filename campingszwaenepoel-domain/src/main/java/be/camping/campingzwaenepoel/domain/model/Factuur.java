package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Faktuur entity. @author Glenn Wybo
 */
@Entity
@Table(name = "factuur")
public class Factuur implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "JAAR", nullable = false)
	private int jaar;
	@Column(name = "BETAALD")
	private boolean betaald = false;
/*    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FACTUUR_ID", nullable = false)
    private Set<FactuurDetail> factuurDetails = new HashSet<FactuurDetail>();*/

	// Constructors

	/** default constructor */
	public Factuur() {
	}

	/** full constructor */
	public Factuur(int jaar, boolean betaald) {
		this.jaar = jaar;
		this.betaald = betaald;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJaar() {
		return jaar;
	}

	public void setJaar(int jaar) {
		this.jaar = jaar;
	}

	public boolean isBetaald() {
		return betaald;
	}

	public void setBetaald(boolean betaald) {
		this.betaald = betaald;
	}

/*	public Set<FactuurDetail> getFactuurDetails() {
		return factuurDetails;
	}

	public void setFactuurDetails(Set<FactuurDetail> factuurDetails) {
		this.factuurDetails = factuurDetails;
	}
	
	public void addFactuurDetail (FactuurDetail factuurDetail) {
		getFactuurDetails().add(factuurDetail);
	}*/

}