package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import be.camping.campingzwaenepoel.common.enums.TabBlad;

/**
 * Faktuur entity. @author Glenn Wybo
 */
@Entity
@Table(name = "OpslagGegeven")
public class OpslagGegeven implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "NAAM", nullable = false)
	private String naam;
	@Column(name = "TABBLAD", nullable = false, length = 13)
	@Enumerated(EnumType.STRING)
	private TabBlad tabblad;
	@Column(name = "DATUM", nullable = false, length = 15)
	private Date datum;

	// Constructors

	/** default constructor */
	public OpslagGegeven() {
	}

	/** full constructor */
	public OpslagGegeven(String naam, TabBlad tabblad, Date datum) {
		this.naam = naam;
		this.tabblad = tabblad;
		this.datum = datum;
	}

	// Property accessors

	public String getNaam() {
		return naam;
	}

	public TabBlad getTabblad() {
		return tabblad;
	}

	public Date getDatum() {
		return datum;
	}
}