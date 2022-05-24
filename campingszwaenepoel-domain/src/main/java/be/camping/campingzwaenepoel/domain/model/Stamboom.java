package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;


@Entity
@Table(name = "stamboom")
@NamedQueries({
        @NamedQuery(name = Stamboom.Queries.FindByStandplaats.NAME, query = Stamboom.Queries.FindByStandplaats.QUERY),
        @NamedQuery(name = Stamboom.Queries.FindByPersoon.NAME, query = Stamboom.Queries.FindByPersoon.QUERY)
})
public class Stamboom {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;
    @Column(name = "NAAM", nullable = false, length = 100)
    private String naam;
    @Column(name = "OPMERKING")
    private String opmerking;
    @Column(name = "FK_STANDPLAATS_ID")
    private int standplaatsId;
    @Column(name = "GENERATIE", length = 4)
    private int generatie;
    @Column(name = "RANG", length = 4)
    private int rang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public int getStandplaatsId() {
        return standplaatsId;
    }

    public void setStandplaatsId(int standplaatsId) {
        this.standplaatsId = standplaatsId;
    }

    public int getGeneratie() {
        return generatie;
    }

    public void setGeneratie(int generatie) {
        this.generatie = generatie;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public interface Queries {

        class FindByStandplaats {
            public static final String NAME = "Stamboom.FindByStandplaats";
            public static final String PARAMETER_STANDPLAATS = "standplaatsId";
            static final String QUERY = "select s from Stamboom s where s.standplaatsId = :" + PARAMETER_STANDPLAATS;
        }

        class FindByPersoon {
            public static final String NAME = "Stamboom.FindByPersoon";
            public static final String PARAMETER_PERSOON = "naam";
            static final String QUERY = "select distinct s from Stamboom s where s.naam = :" + PARAMETER_PERSOON;
        }
    }
}
