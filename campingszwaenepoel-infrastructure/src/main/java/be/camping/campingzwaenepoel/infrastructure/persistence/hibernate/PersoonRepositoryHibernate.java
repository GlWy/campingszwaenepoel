package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.domain.model.Persoon;
import be.camping.campingzwaenepoel.domain.repository.PersoonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Repository("PersoonRepository")
public class PersoonRepositoryHibernate extends AbstractRepositoryJpaImpl<Persoon, Integer> implements PersoonRepository {

    @Override
    public Persoon findById(int id) {
        try {
            return findById(id, Persoon.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public Persoon store(Persoon persoon) {
        try {
            return createOrUpdate(persoon, persoon.getId());
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getPersonen() {
        Query query = getEntityManager().createNamedQuery(Persoon.Queries.FindPersonen.NAME);
        return query.getResultList();
    }

    @Override
    public void remove(Persoon persoon) {
        try {
            delete(persoon.getId(), Persoon.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> zoekPersonen(Map<String, Object> zoekCriteria) {

        Map<String, String> adresCriteria = null;
        if (zoekCriteria.containsKey(Constant.ADRES)) {
            adresCriteria = (Map<String, String>) zoekCriteria.get(Constant.ADRES);
        }
        Map<String, List<Object>> contactCriteria = null;
        if (zoekCriteria.containsKey(Constant.CONTACTGEGEVEN)) {
            contactCriteria = (Map<String, List<Object>>) zoekCriteria.get(Constant.CONTACTGEGEVEN);
        }
        Map<String, String> wagenCriteria = null;
        if (zoekCriteria.containsKey(Constant.WAGEN)) {
            wagenCriteria = (Map<String, String>) zoekCriteria.get(Constant.WAGEN);
        }
        String sql = "select persoon.id, persoon.naam, persoon.voornaam from persoon ";
        if (adresCriteria != null)
            sql += " LEFT JOIN adres ON persoon.fk_adres_id = adres.id ";
        if (contactCriteria != null)
            sql += " LEFT JOIN contactgegeven ON persoon.id=contactgegeven.fk_persoon_id ";
        if (wagenCriteria != null)
            sql += " LEFT JOIN wagen ON persoon.id = wagen.fk_persoon_id ";

        String where = "";
        String order = " ORDER BY persoon.naam, persoon.voornaam";
        // String whereAdres = " PERSOON.FK_ADRES_ID = ADRES.ID AND";
        sql += " where ";

        if (zoekCriteria.containsKey(Constant.NAAM)) {
            where += " persoon.naam like \"%" + zoekCriteria.get(Constant.NAAM) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.VOORNAAM)) {
            if (where.length() > 0) {
                where += " AND";
            }
            where += " persoon.voornaam like \"%" + zoekCriteria.get(Constant.VOORNAAM) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.GESLACHT)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " persoon.geslacht like \"%" + zoekCriteria.get(Constant.GESLACHT) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.GEBOORTEPLAATS)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " persoon.geboorteplaats like \"%" + zoekCriteria.get(Constant.GEBOORTEPLAATS) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.GEBOORTEDATUM)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " persoon.geboortedatum like \"%" + zoekCriteria.get(Constant.GEBOORTEDATUM) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.IDENTITEITSKAART)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " persoon.identiteitskaartnummer like \"%" + zoekCriteria.get(Constant.IDENTITEITSKAART) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.NATIONALITEIT)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " persoon.nationaliteit like \"%" + zoekCriteria.get(Constant.NATIONALITEIT) + "%\"";
        }
        if (zoekCriteria.containsKey(Constant.OPMERKING)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += " persoon.opmerking like \"%" + zoekCriteria.get(Constant.OPMERKING) + "%\"";
        }
        if (contactCriteria != null) {
            if (contactCriteria.containsKey(Constant.TELEFOON)) {
                List<Object> contactgegevens = contactCriteria.get(Constant.TELEFOON);
                for (Object o : contactgegevens) {
                    if (where.length() > 0) {
                        where += " AND ";
                    }
                    where += " contactgegeven.waarde LIKE \"%" + o + "%\"";
                }
            }
            if (contactCriteria.containsKey(Constant.EMAIL)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " contactgegeven.waarde LIKE \"%" + contactCriteria.get(Constant.EMAIL) + "%\"";
            }
        }
        if (wagenCriteria != null) {
            if (wagenCriteria.containsKey(Constant.NUMMERPLAAT)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " wagen.nummerplaat LIKE \"%" + wagenCriteria.get(Constant.NUMMERPLAAT) + "%\"";
            }
            if (wagenCriteria.containsKey(Constant.MERK)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " wagen.merk LIKE \"%" + wagenCriteria.get(Constant.MERK) + "%\"";
            }
            if (wagenCriteria.containsKey(Constant.STICKER)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " wagen.sticker LIKE \"%" + wagenCriteria.get(Constant.STICKER) + "%\"";
            }
            if (wagenCriteria.containsKey(Constant.OPMERKING_WAGEN)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " wagen.opmerking LIKE \"%" + wagenCriteria.get(Constant.OPMERKING_WAGEN) + "%\"";
            }
        }
        if (adresCriteria != null) {
            if (where.length() == 0) {
                // sql =
                // "select PERSOON.ID, PERSOON.NAAM, PERSOON.VOORNAAM from PERSOON LEFT JOIN ADRES ON PERSOON.FK_ADRES_ID = ADRES.ID WHERE ";
                sql = "select persoon.id, persoon.naam, persoon.voornaam from persoon, adres WHERE persoon.fk_adres_id = adres.id AND ";
            }
            if (adresCriteria.containsKey(Constant.STRAAT)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " adres.straat like \"%" + adresCriteria.get(Constant.STRAAT) + "%\"";
            }
            if (adresCriteria.containsKey(Constant.NUMMER)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " adres.huisnummer like \"%" + adresCriteria.get(Constant.NUMMER) + "%\"";
            }
            if (adresCriteria.containsKey(Constant.POSTCODE)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " adres.postcode like \"%" + adresCriteria.get(Constant.POSTCODE) + "%\"";
            }
            if (adresCriteria.containsKey(Constant.PLAATS)) {
                if (where.length() > 0) {
                    where += " AND ";
                }
                where += " adres.plaats like \"%" + adresCriteria.get(Constant.PLAATS) + "%\"";
            }
        }
        if (where.length() > 7) {
            sql += " " + where;
        }
        sql += " " + order;
        Query query = getEntityManager().createNativeQuery(sql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Persoon> zoekPersonen(Calendar geboortedatum, String naam, String voornaam) {
        List<Persoon> personen = new ArrayList<Persoon>();
        // DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (naam.length() > 2)
            naam = naam.substring(0, 3);
        if (voornaam.length() > 2)
            voornaam = voornaam.substring(0, 3);
        String dag = Integer.toString(geboortedatum.get(Calendar.DATE));
        String maand = Integer.toString(geboortedatum.get(Calendar.MONTH) + 1);
        String jaar = Integer.toString(geboortedatum.get(Calendar.YEAR));
        while (dag.length() < 2) {
            dag = "0" + dag;
        }
        while (maand.length() < 2) {
            maand = "0" + maand;
        }
        // String sDate = formatter.format(geboortedatum);
        String datum = jaar + "-" + maand + "-" + dag;
        String sql = "SELECT id FROM persoon WHERE naam LIKE '%" + naam + "%' AND voornaam LIKE '%" + voornaam
                + "%' AND geboortedatum LIKE '" + datum + "'";
        Query query = getEntityManager().createNativeQuery(sql);
        List<Integer> list = query.getResultList();
        for (Integer id : list) {
            Persoon persoon = findById(id);
            personen.add(persoon);
        }
        /*
         * if (list.size() > 0) { Integer id = list.get(0); Persoon persoon = findById(id); personen.add(persoon); }
		 */
        return personen;
    }

    @Override
    public void changePersoon(int newId, int oldId) {
        String sql = "UPDATE inschrijving_persoon set FK_PERSOON_ID = " + newId + " WHERE FK_PERSOON_ID = " + oldId;
        getEntityManager().createNativeQuery(sql).executeUpdate();


        sql = "UPDATE betaler set FK_HOOFDBETALER_ID = " + newId + " WHERE FK_HOOFDBETALER_ID = " + oldId;
        getEntityManager().createNativeQuery(sql).executeUpdate();

        sql = "UPDATE betaler set FK_BETALER_ID = " + newId + " WHERE FK_BETALER_ID = " + oldId;
        getEntityManager().createNativeQuery(sql).executeUpdate();

        sql = "DELETE FROM persoon WHERE ID = " + oldId;
        getEntityManager().createNativeQuery(sql).executeUpdate();
    }
}