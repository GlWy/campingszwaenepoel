package be.camping.campingzwaenepoel.domain.model;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;
import be.camping.campingzwaenepoel.common.enums.GebruikerEnum;
import be.camping.campingzwaenepoel.standplaatsutilities.StandplaatsUtilities;

/**
 * Grond entity. @author Glenn Wybo
 */
@Entity
@Table(name = "standplaats")
@NamedQueries({
        @NamedQuery(name = Standplaats.Queries.FindOpmerkingenVoorFacturatie.NAME, query = Standplaats.Queries.FindOpmerkingenVoorFacturatie.QUERY),
        @NamedQuery(name = Standplaats.Queries.ZoekStandplaatsenAndereNietBetaald.NAME, query = Standplaats.Queries.ZoekStandplaatsenAndereNietBetaald.QUERY),
        @NamedQuery(name = Standplaats.Queries.ZoekStandplaatsenNietBetaald.NAME, query = Standplaats.Queries.ZoekStandplaatsenNietBetaald.QUERY),
        @NamedQuery(name = Standplaats.Queries.GetBadgeVoorStandplaats.NAME, query = Standplaats.Queries.GetBadgeVoorStandplaats.QUERY),
        @NamedQuery(name = Standplaats.Queries.ZoekOpmerkingenGrondMetNamen.NAME, query = Standplaats.Queries.ZoekOpmerkingenGrondMetNamen.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindStandplaatsIds.NAME, query = Standplaats.Queries.FindStandplaatsIds.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindStandplaatsNamen.NAME, query = Standplaats.Queries.FindStandplaatsNamen.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindStandplaatsMetGroepEnNummber.NAME, query = Standplaats.Queries.FindStandplaatsMetGroepEnNummber.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindNextStandplaats.NAME, query = Standplaats.Queries.FindNextStandplaats.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindNextStandplaatsWithOffset.NAME, query = Standplaats.Queries.FindNextStandplaatsWithOffset.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindPreviousStandplaats.NAME, query = Standplaats.Queries.FindPreviousStandplaats.QUERY),
        @NamedQuery(name = Standplaats.Queries.FindPreviousStandplaatsWithOffset.NAME, query = Standplaats.Queries.FindPreviousStandplaatsWithOffset.QUERY)
})
public class Standplaats implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;
    @Column(name = "PLAATSGROEP", nullable = false, length = 1)
    private String plaatsgroep;
    @Column(name = "PLAATSNUMMER", nullable = false)
    private int plaatsnummer;
    @Column(name = "GESCHIEDENIS", length = 16777215)
    private String geschiedenis;
    @Column(name = "GESCHIEDENIS_EDITOR", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private GebruikerEnum geschiedenisEditor = GebruikerEnum.NIEMAND;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GESCHIEDENIS_DATUM")
    private Date geschiedenisDatum;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_GROND_ID", nullable = false)
    private Set<GrondInformatie> grondInformaties = new HashSet<>();
    @Column(name = "ALGEMENE_OPMERKING", length = 16777215)
    private String algemeneOpmerking;
    @Column(name = "FOTO_OPMERKING", length = 16777215)
    private String fotoOpmerking;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_STANDPLAATS_ID", nullable = false)
    private Set<Inschrijving> inschrijvingen = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_BADGE_ID")
    private Badge badge;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_STANDPLAATS_ID", nullable = false)
    private Set<Betaler> betalers = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_STANDPLAATS_ID", nullable = false)
    private Set<FactuurDetail> factuurDetails = new HashSet<>();
    @Column(name = "KOSTPRIJS")
    private double kostprijs;
    @Column(name = "TOTAAL")
    private double totaal;
    @Column(name = "GESCHIEDENIS_FLAG", nullable = false)
    private Boolean geschiedenisFlag;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_STANDPLAATS_ID", nullable = false)
    private Set<Geschiedenis> geschiedenisPunten;
    @Transient
    private double teBetalenSom;
    @Transient
    private double betaaldeSom;


    // methods - business logic
    public void resetFactuurDetailList(boolean uitstel) {
        List<FactuurDetail> betalingen = new ArrayList<FactuurDetail>();
        List<FactuurDetail> uitstellen = new ArrayList<FactuurDetail>();
        List<FactuurDetail> teverwijderen = new ArrayList<FactuurDetail>();
        setTeBetalenSom(0);
        setBetaaldeSom(0);
        for (FactuurDetail factuurDetail : getFactuurDetails()) {
            if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) {
                betalingen.add(factuurDetail);
                betaaldeSom += factuurDetail.getBedrag() + factuurDetail.getRappel()
                        + factuurDetail.getUitstelkosten() + factuurDetail.getAndereKosten();
            }
            if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) uitstellen.add(factuurDetail);
            if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) {
                teBetalenSom += factuurDetail.getBedrag() + factuurDetail.getRappel()
                        + factuurDetail.getUitstelkosten() + factuurDetail.getAndereKosten();
            }
        }
        betalingen = StandplaatsUtilities.sortFactuurDetails(betalingen);
        uitstellen = StandplaatsUtilities.sortFactuurDetails(uitstellen);
        for (FactuurDetail factuurDetailUitstel : uitstellen) {
            boolean bedragOk = false;
            boolean rappelOk = false;
            boolean uitstelOk = false;
            boolean andereOk = false;
            boolean betaalToevoegen = false;
            for (FactuurDetail factuurDetailBetaald : betalingen) {
                if (factuurDetailUitstel.getBedrag() == factuurDetailBetaald.getBedrag()) {
                    bedragOk = true;
                    if (factuurDetailBetaald.getBedrag() > 0) betaalToevoegen = true;
                }
                if (factuurDetailUitstel.getRappel() == factuurDetailBetaald.getRappel()) {
                    rappelOk = true;
                    if (factuurDetailBetaald.getRappel() > 0) betaalToevoegen = true;
                }
                if (factuurDetailUitstel.getUitstelkosten() == factuurDetailBetaald.getUitstelkosten()) {
                    uitstelOk = true;
                    if (factuurDetailBetaald.getUitstelkosten() > 0) betaalToevoegen = true;
                }
                if (factuurDetailUitstel.getAndereKosten() == factuurDetailBetaald.getAndereKosten()) {
                    andereOk = true;
                    if (factuurDetailBetaald.getAndereKosten() > 0) betaalToevoegen = true;
                }

                if (betaalToevoegen) {
                    teverwijderen.add(factuurDetailBetaald);
                }
                if (bedragOk && rappelOk && uitstelOk && andereOk) {
                    teverwijderen.add(factuurDetailUitstel);
                    break;
                }
            }

            for (FactuurDetail factuurDetail : teverwijderen) {
                getFactuurDetails().remove(factuurDetail);
            }

            teverwijderen = new ArrayList<FactuurDetail>();
            Date now = Calendar.getInstance().getTime();
            boolean ok = true;
            for (FactuurDetail factuurDetail : getFactuurDetails()) {
                if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) {
                    if (!factuurDetail.getDatum().after(now)) {
                        ok = false;
                        break;
                    }
                }
            }
            if (!ok || uitstel) {

            }
        }
        for (FactuurDetail factuurDetail : teverwijderen) {
            if (getFactuurDetails().contains(factuurDetail)) getFactuurDetails().remove(factuurDetail);
        }
    }

    // Property accessors - getters and setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaatsgroep() {
        return this.plaatsgroep;
    }

    public void setPlaatsgroep(String plaatsgroep) {
        this.plaatsgroep = plaatsgroep;
    }

    public int getPlaatsnummer() {
        return this.plaatsnummer;
    }

    public void setPlaatsnummer(int plaatsnummer) {
        this.plaatsnummer = plaatsnummer;
    }

    public String getGeschiedenis() {
        return this.geschiedenis;
    }

    public void setGeschiedenis(String geschiedenis) {
        this.geschiedenis = geschiedenis;
    }

    public GebruikerEnum getGeschiedenisEditor() {
        return geschiedenisEditor;
    }

    public void setGeschiedenisEditor(GebruikerEnum geschiedenisEditor) {
        this.geschiedenisEditor = geschiedenisEditor;
    }

    public Date getGeschiedenisDatum() {
        return geschiedenisDatum;
    }

    public void setGeschiedenisDatum(Date geschiedenisDatum) {
        this.geschiedenisDatum = geschiedenisDatum;
    }

    public Collection<GrondInformatie> getGrondInformaties() {
        return grondInformaties;
    }

    public void setGrondInformaties(Set<GrondInformatie> grondInformaties) {
        this.grondInformaties = grondInformaties;
    }

    public Badge getBadge() {
        return this.badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getAlgemeneOpmerking() {
        return this.algemeneOpmerking;
    }

    public void setAlgemeneOpmerking(String algemeneOpmerking) {
        this.algemeneOpmerking = algemeneOpmerking;
    }

    public String getFotoOpmerking() {
        return fotoOpmerking;
    }

    public void setFotoOpmerking(String fotoOpmerking) {
        this.fotoOpmerking = fotoOpmerking;
    }

    public Set<Inschrijving> getInschrijvingen() {
        return inschrijvingen;
    }

    public void setInschrijvingen(Set<Inschrijving> inschrijvingen) {
        this.inschrijvingen = inschrijvingen;
    }

    public void addInschrijving(Inschrijving inschrijving) {
        getInschrijvingen().add(inschrijving);
    }

    public Set<Betaler> getBetalers() {
        return betalers;
    }

    public void setBetalers(Set<Betaler> betalers) {
        this.betalers = betalers;
    }

    public void addBetaler(Betaler betaler) {
        getBetalers().add(betaler);
    }

    public Set<FactuurDetail> getFactuurDetails() {
        return factuurDetails;
    }

    public void setFactuurDetails(Set<FactuurDetail> factuurDetails) {
        this.factuurDetails = factuurDetails;
    }

    public Set<Geschiedenis> getGeschiedenisPunten() {
        return geschiedenisPunten;
    }

    public void setGeschiedenisPunten(Set<Geschiedenis> geschiedenisPunten) {
        this.geschiedenisPunten = geschiedenisPunten;
    }

    public double getKostprijs() {
        return kostprijs;
    }

    public void setKostprijs(double kostprijs) {
        this.kostprijs = kostprijs;
    }

    public double getTotaal() {
        return totaal;
    }

    public void setTotaal(double totaal) {
        this.totaal = totaal;
    }

    public Boolean getGeschiedenisFlag() {
        return geschiedenisFlag;
    }

    public void setGeschiedenisFlag(Boolean geschiedenisFlag) {
        this.geschiedenisFlag = geschiedenisFlag;
    }

    public double getTeBetalenSom() {
        return teBetalenSom;
    }

    public void setTeBetalenSom(double teBetalenSom) {
        this.teBetalenSom = teBetalenSom;
    }

    public double getBetaaldeSom() {
        return betaaldeSom;
    }

    public void setBetaaldeSom(double betaaldeSom) {
        this.betaaldeSom = betaaldeSom;
    }

    public void addFactuurDetail(FactuurDetail factuurDetail) {
        getFactuurDetails().add(factuurDetail);
    }


    public interface Queries {

        class FindOpmerkingenVoorFacturatie {
            public static final String NAME = "Standplaats.FindOpmerkingenVoorFacturatie";
            static final String QUERY = "select plaatsgroep, plaatsnummer, fotoOpmerking from Standplaats " +
                    "where fotoOpmerking is not null and fotoOpmerking <> '' order by plaatsgroep, plaatsnummer";
        }

        class ZoekStandplaatsenAndereNietBetaald {
            public static final String NAME = "Standplaats.ZoekStandplaatsenAndereNietBetaald";
            static final String QUERY = "select distinct s from Standplaats s " +
                    "JOIN s.factuurDetails fd " +
                    "where s.totaal > 0.1 AND fd.andereKosten > 0 " +
                    "ORDER BY s.plaatsgroep, s.plaatsnummer, fd.datum";
        }

        class FindStandplaatsIds {
            public static final String NAME = "Standplaats.FindStandplaatsIds";
            static final String QUERY = "select s.id from Standplaats s ORDER BY s.id";
        }

        class FindStandplaatsNamen {
            public static final String NAME = "Standplaats.FindStandplaatsNamen";
            static final String QUERY = "select s.plaatsgroep, s.plaatsnummer from Standplaats s ORDER BY s.plaatsgroep, s.plaatsnummer";
        }

        class ZoekStandplaatsenNietBetaald {
            public static final String NAME = "Standplaats.ZoekStandplaatsenNietBetaald";
            static final String QUERY = "select distinct s from Standplaats s " +
                    "where s.totaal > 0.1 " +
                    "ORDER BY s.plaatsgroep, s.plaatsnummer";
        }

        class FindStandplaatsMetGroepEnNummber {
            public static final String NAME = "Standplaats.FindStandplaatsMetGroepEnNummber";
            public static final String PARAMETER_GROEP = "plaatsgroep";
            public static final String PARAMETER_NUMMER = "plaatsNummber";
            static final String QUERY = "select s from Standplaats s " +
                    "where s.plaatsgroep = :" + PARAMETER_GROEP +
                    " and  s.plaatsnummer = :" + PARAMETER_NUMMER;
        }

        class FindNextStandplaats {
            public static final String NAME = "Standplaats.FindNextStandplaats";
            public static final String PARAMETER_ID = "standplaatsId";
            static final String QUERY = "select s from Standplaats s " +
                    "where s.id > :" + PARAMETER_ID +
                    " order by s.plaatsgroep, s.plaatsnummer";
        }

        class FindNextStandplaatsWithOffset {
            public static final String NAME = "Standplaats.FindNextStandplaatsWithOffset";
            public static final String PARAMETER_ID = "standplaatsId";
            static final String QUERY = "select s from Standplaats s " +
                    "where s.id > :" + PARAMETER_ID +
                    " order by s.plaatsgroep, s.plaatsnummer";
        }

        class FindPreviousStandplaats {
            public static final String NAME = "Standplaats.FindPreviousStandplaats";
            public static final String PARAMETER_ID = "standplaatsId";
            static final String QUERY = "select s from Standplaats s " +
                    "where s.id < :" + PARAMETER_ID +
                    " order by s.plaatsgroep, s.plaatsnummer desc" +
                    " limit 1";
        }

        class FindPreviousStandplaatsWithOffset {
            public static final String NAME = "Standplaats.FindPreviousStandplaatsWithOffset";
            public static final String PARAMETER_ID = "standplaatsId";
            static final String QUERY = "select s from Standplaats s " +
                    "where s.id < :" + PARAMETER_ID +
                    " order by s.plaatsgroep, s.plaatsnummer desc" +
                    " limit 50";
        }

        class GetBadgeVoorStandplaats {
            public static final String NAME = "Standplaats.GetBadgeVoorStandplaats";
            public static final String PARAMETER_ID = "standplaatsId";
            static final String QUERY = "select s.plaatsgroep, s.plaatsnummer, s.badge from Standplaats s where s.id = :" + PARAMETER_ID;
        }

        class ZoekOpmerkingenGrondMetNamen {
            public static final String NAME = "Standplaats.ZoekOpmerkingenGrondMetNamen";
            public static final String KASBON_NUMMBER = "kasbonNummber";
            static final String QUERY = "select DISTINCT standplaats.plaatsgroep, standplaats.plaatsnummer, persoon.naam, persoon.voornaam, grondInformaties.waarde "
             + "from Standplaats standplaats"
             + " JOIN standplaats.grondInformaties grondInformaties "
             + " JOIN standplaats.betalers betalers "
             + " JOIN betalers.hoofdbetaler persoon "
             + " WHERE grondInformaties.nummer = :" + KASBON_NUMMBER;
        }
    }
}