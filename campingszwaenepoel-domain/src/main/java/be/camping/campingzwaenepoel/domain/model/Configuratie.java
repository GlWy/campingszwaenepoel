package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Configuration entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "configuratie")
@NamedQuery(name = Configuratie.Queries.FindConfigurationByName.NAME, query = Configuratie.Queries.FindConfigurationByName.QUERY)
public class Configuratie implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "NAAM", nullable = false)
	private String naam;
	@Column(name = "WAARDE", nullable = false)
	private String waarde;

	// Constructors

	/** default constructor */
	public Configuratie() {
	}

	/** full constructor */
	public Configuratie(String naam, String waarde) {
		this.naam = naam;
		this.waarde = waarde;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getWaarde() {
		return this.waarde;
	}

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}

	public interface Queries {

		class FindConfigurationByName {
			public static final String NAME = "Configuration.FindConfigurationByName";
			public static final String CONFIGURATIE_NAAM = "configurationKey";
			static final String QUERY = "select c from Configuratie c where c.naam = :" + CONFIGURATIE_NAAM;
		}
	}
}