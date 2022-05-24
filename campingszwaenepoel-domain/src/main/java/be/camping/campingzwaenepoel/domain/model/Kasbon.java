package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.*;

import be.camping.campingzwaenepoel.common.enums.CashSourceEnum;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "kasbon")
@NamedQueries({
        @NamedQuery(name = Kasbon.Queries.RemoveAllKasbons.NAME, query = Kasbon.Queries.RemoveAllKasbons.QUERY),
        @NamedQuery(name = Kasbon.Queries.FindByKasbonNummer.NAME, query = Kasbon.Queries.FindByKasbonNummer.QUERY),
        @NamedQuery(name = Kasbon.Queries.FindByComputer.NAME, query = Kasbon.Queries.FindByComputer.QUERY)
})
public class Kasbon implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    @Column(name = "KASBON_NUMMER", nullable = false)
    private Integer kasbonnummer;
    @Column(name = "AANTAL_NACHTEN", nullable = false)
    private Integer aantalNachten;
    @Column(name = "AUTO100", nullable = false)
    private Integer auto100;
    @Column(name = "AUTO150", nullable = false)
    private Integer auto150;
    @Column(name = "WAARBORG", nullable = false)
    private Integer waarborg;
    @Column(name = "JETON", nullable = false)
    private Integer jeton;
    @Column(name = "ZAK_GEEL", nullable = false)
    private Integer zakGeel;
    @Column(name = "ZAK_BLAUW", nullable = false)
    private Integer zakBlauw;
    @Column(name = "CAR", nullable = false)
    private Integer car;
    @Column(name = "TENT", nullable = false)
    private Integer tent;
    @Column(name = "VOLWASSENEN", nullable = false)
    private Integer volwassenen;
    @Column(name = "KINDEREN", nullable = false)
    private Integer kinderen;
    @Column(name = "TELEFOON", nullable = false, precision = 22, scale = 0)
    private Double telefoon;
    @Temporal(TemporalType.DATE)
    @Column(name = "KASBON_DATUM", length = 10)
    private Date kasbonDatum;
    @Column(name = "NAAM", nullable = false)
    private String naam;
    @Column(name = "standplaats", length = 10)
    private String standplaats;
    @Column(name = "NUMMERPLAAT", length = 12)
    private String nummerplaat;
    @Column(name = "BADGE")
    private String badge;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATUM_VAN", length = 10)
    private Date datumVan;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATUM_TOT", length = 10)
    private Date datumTot;
    @Column(name = "BETALING", nullable = false, precision = 22, scale = 0)
    private Double betaling;
    @Column(name = "TERUG", nullable = false, precision = 22, scale = 0)
    private Double terug;
    @Column(name = "COMPUTER", length = 45)
    private String computer;
    @Column(name = "CASH_SOURCE", length = 8)
	@Enumerated(EnumType.STRING)
    private CashSourceEnum cashSource;

    // Property accessors
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKasbonnummer() {
        return kasbonnummer;
    }

    public void setKasbonnummer(Integer kasbonnummer) {
        this.kasbonnummer = kasbonnummer;
    }

    public Integer getAantalNachten() {
        return this.aantalNachten;
    }

    public void setAantalNachten(Integer aantalNachten) {
        this.aantalNachten = aantalNachten;
    }

    public Integer getAuto100() {
        return this.auto100;
    }

    public void setAuto100(Integer auto100) {
        this.auto100 = auto100;
    }

    public Integer getAuto150() {
        return this.auto150;
    }

    public void setAuto150(Integer auto150) {
        this.auto150 = auto150;
    }

    public Integer getWaarborg() {
        return this.waarborg;
    }

    public void setWaarborg(Integer waarborg) {
        this.waarborg = waarborg;
    }

    public Integer getJeton() {
        return this.jeton;
    }

    public void setJeton(Integer jeton) {
        this.jeton = jeton;
    }

    public Integer getZakGeel() {
        return this.zakGeel;
    }

    public void setZakGeel(Integer zakGeel) {
        this.zakGeel = zakGeel;
    }

    public Integer getZakBlauw() {
        return this.zakBlauw;
    }

    public void setZakBlauw(Integer zakBlauw) {
        this.zakBlauw = zakBlauw;
    }

    public Integer getCar() {
        return this.car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public Integer getTent() {
        return this.tent;
    }

    public void setTent(Integer tent) {
        this.tent = tent;
    }

    public Integer getVolwassenen() {
        return this.volwassenen;
    }

    public void setVolwassenen(Integer volwassenen) {
        this.volwassenen = volwassenen;
    }

    public Integer getKinderen() {
        return this.kinderen;
    }

    public void setKinderen(Integer kinderen) {
        this.kinderen = kinderen;
    }

    public Double getTelefoon() {
        return this.telefoon;
    }

    public void setTelefoon(Double telefoon) {
        this.telefoon = telefoon;
    }

    public Date getKasbonDatum() {
        return this.kasbonDatum;
    }

    public void setKasbonDatum(Date kasbonDatum) {
        this.kasbonDatum = kasbonDatum;
    }

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getStandplaats() {
        return this.standplaats;
    }

    public void setStandplaats(String standplaats) {
        this.standplaats = standplaats;
    }

    public String getNummerplaat() {
        return this.nummerplaat;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }

    public String getBadge() {
        return this.badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public Date getDatumVan() {
        return this.datumVan;
    }

    public void setDatumVan(Date datumVan) {
        this.datumVan = datumVan;
    }

    public Date getDatumTot() {
        return this.datumTot;
    }

    public void setDatumTot(Date datumTot) {
        this.datumTot = datumTot;
    }

    public Double getBetaling() {
        return betaling;
    }

    public void setBetaling(Double betaling) {
        this.betaling = betaling;
    }

    public Double getTerug() {
        return terug;
    }

    public void setTerug(Double terug) {
        this.terug = terug;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }
    
    public CashSourceEnum getCashSource() {
    	return cashSource;
    }
    
    public void setCashSource(CashSourceEnum cashSource) {
    	this.cashSource = cashSource;
    }

    public interface Queries {

        class RemoveAllKasbons {
            public static final String NAME = "Kasbon.RemoveAllKasbons";
            static final String QUERY = "delete from Kasbon";
        }

        class FindByKasbonNummer {
            public static final String NAME = "Kasbon.FindByKasbonNummer";
            public static final String PARAMETER_NUMBER = "kasbonNummber";
            static final String QUERY = "select k from Kasbon k where k.kasbonnummer = :" + PARAMETER_NUMBER;
        }

        class FindByComputer {
            public static final String NAME = "Kasbon.FindByComputer";
            public static final String PARAMETER_COMPUTER = "computer";
            static final String QUERY = "select k from Kasbon k where k.computer = :" + PARAMETER_COMPUTER;
        }
    }
}