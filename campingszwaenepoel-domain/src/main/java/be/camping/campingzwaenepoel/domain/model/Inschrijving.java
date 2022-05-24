package be.camping.campingzwaenepoel.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;

/**
 * Inschrijving entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "inschrijving")
@NamedQueries({
        @NamedQuery(name = Inschrijving.Queries.GetLaatsteInschrijvingByComputerName.NAME, query = Inschrijving.Queries.GetLaatsteInschrijvingByComputerName.QUERY),
        @NamedQuery(name = Inschrijving.Queries.GetLaatsteInschrijving.NAME, query = Inschrijving.Queries.GetLaatsteInschrijving.QUERY),
        @NamedQuery(name = Inschrijving.Queries.FindLaatsteFichenummer.NAME, query = Inschrijving.Queries.FindLaatsteFichenummer.QUERY),
        @NamedQuery(name = Inschrijving.Queries.FindInschrijvingenVoorStandplaatsAndSoortHuurder.NAME, query = Inschrijving.Queries.FindInschrijvingenVoorStandplaatsAndSoortHuurder.QUERY),
        @NamedQuery(name = Inschrijving.Queries.FindInschrijvingenVoorStandplaats.NAME, query = Inschrijving.Queries.FindInschrijvingenVoorStandplaats.QUERY),
        @NamedQuery(name = Inschrijving.Queries.FindInschrijvingenVoorBadge.NAME, query = Inschrijving.Queries.FindInschrijvingenVoorBadge.QUERY)
})
public class Inschrijving implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_VAN", length = 10)
	private Date dateVan;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_TOT", length = 10)
	private Date dateTot;
	@Enumerated(EnumType.STRING)
	@Column(name = "SOORTHUURDER", nullable = false, length = 9)
	private SoortHuurderEnum soorthuurder;
	@Column(name = "OPMERKING", length = 16777215)
	private String opmerking;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_INSCHRIJVING_ID", nullable = false)
    private Set<InschrijvingPersoon> inschrijvingPersonen = new HashSet<InschrijvingPersoon>();
	@Column(name="FK_STANDPLAATS_ID", nullable=false, insertable=false, updatable=false)
	private Integer standplaatsId;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_BADGE_ID")
	private Badge badge;
	@Column(name = "FICHENUMMER", length = 11)
	private int fichenummer;
	@Column(name = "COMPUTER", length = 45)
	private String computer;
	
	// Constructors

	/** default constructor */
	public Inschrijving() {}

	/** minimal constructor */
	public Inschrijving(Date dateVan, Date dateTot,	SoortHuurderEnum soorthuurder) {
		this.dateVan = dateVan;
		this.dateTot = dateTot;
		this.soorthuurder = soorthuurder;
	}

	/** full constructor */
	public Inschrijving(Date dateVan, Date dateTot,	SoortHuurderEnum soorthuurder, String opmerking) {
		this.dateVan = dateVan;
		this.dateTot = dateTot;
		this.soorthuurder = soorthuurder;
		this.opmerking = opmerking;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateVan() {
		return this.dateVan;
	}

	public void setDateVan(Date dateVan) {
		this.dateVan = dateVan;
	}

	public Date getDateTot() {
		return this.dateTot;
	}

	public void setDateTot(Date dateTot) {
		this.dateTot = dateTot;
	}

	public SoortHuurderEnum getSoorthuurder() {
		return this.soorthuurder;
	}

	public void setSoorthuurder(SoortHuurderEnum soorthuurder) {
		this.soorthuurder = soorthuurder;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Integer getStandplaatsId() {
		return standplaatsId;
	}

	public void setStandplaatsId(Integer standplaatsId) {
		this.standplaatsId = standplaatsId;
	}

	public Set<InschrijvingPersoon> getInschrijvingPersonen() {
		return inschrijvingPersonen;
	}

	public void setInschrijvingPersonen(Set<InschrijvingPersoon> inschrijvingPersonen) {
		this.inschrijvingPersonen = inschrijvingPersonen;
	}

	public void addInschrijvingPersoon(InschrijvingPersoon inschrijvingPersoon) {
		getInschrijvingPersonen().add(inschrijvingPersoon);
	}

	public Badge getBadge() {
		return this.badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public int getFichenummer() {
		return fichenummer;
	}

	public void setFichenummer(int fichenummer) {
		this.fichenummer = fichenummer;
	}

	public String getComputer() {
		return computer;
	}

	public void setComputer(String computer) {
		this.computer = computer;
	}

	public interface Queries {

	    class FindInschrijvingenVoorStandplaatsAndSoortHuurder {
            public static final String NAME = "Inschrijving.FindInschrijvingenVoorStandplaatsAndSoortHuurder";
            public static final String PARAMETER_STANDPLAATS_ID = "standplaatsId";
            public static final String PARAMETER_SOORT_HUURDER = "soortHuurder";
            static final String QUERY = "select distinct insch from Inschrijving insch " +
                    "where insch.standplaatsId = :" + PARAMETER_STANDPLAATS_ID +
                    " AND insch.soorthuurder = :" + PARAMETER_SOORT_HUURDER +
                    " order by insch.dateVan desc, insch.id";
        }

	    class FindInschrijvingenVoorStandplaats {
            public static final String NAME = "Inschrijving.FindInschrijvingenVoorStandplaats";
            public static final String PARAMETER_STANDPLAATS_ID = "standplaatsId";
            static final String QUERY = "select distinct insch from Inschrijving insch " +
                    "where insch.standplaatsId = :" + PARAMETER_STANDPLAATS_ID +
                    " order by insch.dateVan desc, insch.id";
        }

	    class FindInschrijvingenVoorBadge {
            public static final String NAME = "Inschrijving.FindInschrijvingenVoorBadge";
            public static final String PARAMETER_BADGE_ID = "badgeId";
            static final String QUERY = "select insch from Inschrijving insch where insch.badge.id = :" + PARAMETER_BADGE_ID;
        }

		class GetLaatsteInschrijvingByComputerName {
			public static final String NAME = "Inschrijving.GetLaatsteInschrijvingByComputerName";
			public static final String PARAMETER_COMPUTER_NAME = "computername";
			static final String QUERY = "select insch from Inschrijving insch " +
					"where insch.computer = :" + PARAMETER_COMPUTER_NAME +
					" and insch.id = (select max(insch2.id) from Inschrijving insch2)";
		}

        class GetLaatsteInschrijving {
            public static final String NAME = "Inschrijving.GetLaatsteInschrijving";
            static final String QUERY = "select insch from Inschrijving insch " +
                    "where insch.id = (select max(insch2.id) from Inschrijving insch2)";
        }

        class FindLaatsteFichenummer {
            public static final String NAME = "Inschrijving.FindLaatsteFichenummer";
            static final String QUERY = "select MAX(insch.fichenummer) from Inschrijving insch";
        }
	}
}