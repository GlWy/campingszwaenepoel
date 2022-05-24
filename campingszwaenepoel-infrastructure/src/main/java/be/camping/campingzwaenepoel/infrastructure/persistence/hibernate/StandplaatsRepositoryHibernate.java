package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

// default package

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.domain.model.*;
import be.camping.campingzwaenepoel.domain.repository.StandplaatsRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * A data access object (DAO) providing persistence and search support for Grond entities. Transaction control of the
 * save(), update() and delete() operations can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 * @see .Grond
 */

@Repository("StandplaatsRepository")
public class StandplaatsRepositoryHibernate extends AbstractRepositoryJpaImpl<Standplaats, Integer> implements StandplaatsRepository {

    private static final String TARGET_INSCHRIJVING = "inschrijving";

    @Override
    public Standplaats store(Standplaats standplaats) {
        return createOrUpdate(standplaats, standplaats.getId());
    }

    @Override
    public Standplaats findById(Integer id) {
        try {
            return findById(id, Standplaats.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public Standplaats findStandplaats(int start, int max) {

        switch (max) {
            case 1 :
                return (Standplaats) getEntityManager().createNamedQuery(Standplaats.Queries.FindNextStandplaats.NAME)
                        .setParameter(Standplaats.Queries.FindNextStandplaats.PARAMETER_ID, start)
                        .setMaxResults(1)
                        .getSingleResult();
            case Constant.STANDPLAATS_OFFSET :
                return (Standplaats) getEntityManager().createNamedQuery(Standplaats.Queries.FindNextStandplaatsWithOffset.NAME)
                        .setParameter(Standplaats.Queries.FindNextStandplaats.PARAMETER_ID, start)
                        .setMaxResults(max)
                        .getSingleResult();
            case -1 :
                return (Standplaats) getEntityManager().createNamedQuery(Standplaats.Queries.FindPreviousStandplaats.NAME)
                        .setParameter(Standplaats.Queries.FindPreviousStandplaats.PARAMETER_ID, start)
                        .getSingleResult();
            case -Constant.STANDPLAATS_OFFSET :
                return (Standplaats) getEntityManager().createNamedQuery(Standplaats.Queries.FindPreviousStandplaatsWithOffset.NAME)
                        .setParameter(Standplaats.Queries.FindPreviousStandplaatsWithOffset.PARAMETER_ID, start)
                        .getSingleResult();
        }
        return null;
    }

    @Override
    public Standplaats zoekStandplaats(String standplaatsGroep, int standplaatsNummer) {
        return (Standplaats) getEntityManager().createNamedQuery(Standplaats.Queries.FindStandplaatsMetGroepEnNummber.NAME)
                .setParameter(Standplaats.Queries.FindStandplaatsMetGroepEnNummber.PARAMETER_GROEP, standplaatsGroep)
                .setParameter(Standplaats.Queries.FindStandplaatsMetGroepEnNummber.PARAMETER_NUMMER, standplaatsNummer)
                .getSingleResult();
    }

    @Override
    public List<String> getStandplaatsen() {
        List<Object[]> list = getEntityManager().createNamedQuery(Standplaats.Queries.FindStandplaatsNamen.NAME).getResultList();
        List<String> standplaatsen = new ArrayList<>();
        for (Object[] o : list) {
            String s = "";
            if (!StringUtils.isEmpty((String) o[0])) {
                s = (String) o[0];
            }
            if (o[1] instanceof java.lang.Integer) {
                String sTemp = o[1].toString();
                while (sTemp.length() < 3) {
                    sTemp = "0" + sTemp;
                }
                s += sTemp;
            }
            standplaatsen.add(s);
        }

        if (standplaatsen.contains("")) {
            int index = standplaatsen.indexOf("");
            standplaatsen.remove(index);
        }

        standplaatsen.add(0, "");

        return standplaatsen;
    }

    @Override
    public List<Integer> getStandplaatsIds() {
        return getEntityManager().createNamedQuery(Standplaats.Queries.FindStandplaatsIds.NAME).getResultList();
    }

    @Override
    public Standplaats getStandplaatsWithFactuurDetails(int standplaatsId) {
        return findById(standplaatsId);
    }

    @Override
    public Standplaats getStandplaatsWithInschrijvingenEnPersonen(int standplaatsId) {
        try {
            Standplaats standplaats = findById(standplaatsId, Standplaats.class);
//            Hibernate.initialize(standplaats.getInschrijvingen());
//            for (Inschrijving inschrijving : standplaats.getInschrijvingen()) {
  //              Hibernate.initialize(inschrijving.getInschrijvingPersonen());
    //        }
            return standplaats;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Date> getInschrijvingenMetBadge(int standplaatsId) {

        String sql = "select inschrijving.date_tot from standplaats "
                + "LEFT JOIN inschrijving ON standplaats.id = inschrijving.fk_standplaats_id "
                + "where standplaats.id = " + standplaatsId + " AND inschrijving.fk_badge_id is not null "
                + "and inschrijving.fk_badge_id != ''";
        Query query = getEntityManager().createNativeQuery(sql);
        return query.getResultList();
		/*
         * Criteria crit = getSession().createCriteria(Inschrijving.class); Calendar cal = Calendar.getInstance();
		 * cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE) + 1);
		 * crit.add(Restrictions.eq("standplaatsId", standplaatsId)); crit.add(Restrictions.isNotEmpty("badge")); //
		 * crit.add(Restrictions.lt("dateVan", cal.getTime())); List<Inschrijving> inschrijvingen = crit.list();
		 */
    }

    @Override
    public List<Object[]> zoekStandplaatsEnFicheGegevens(final Map<String, Object> zoekCriteria,
                                                         final Map<String, Object> projectionList) {

        List<Object[]> list = createQuery(TARGET_INSCHRIJVING, zoekCriteria, projectionList);
        // list.addAll(createQuery(targetHoofdbetaler, zoekCriteria, projectionList));
        // list.addAll(createQuery(targetPartnerBetaler, zoekCriteria, projectionList));

        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> createQuery(final String target, final Map<String, Object> zoekCriteria,
                                       final Map<String, Object> projectionList) {

        String sql = createSelectString(zoekCriteria, projectionList) + createFromString(zoekCriteria, projectionList);

        sql += createWhereSql(zoekCriteria);

        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    private String createSelectString(final Map<String, Object> zoekCriteria, final Map<String, Object> projectionList) {
        String selectSql = "select distinct ";
        if (projectionList.containsKey(Constant.STANDPLAATS)) {
            selectSql += "standplaats.plaatsgroep, standplaats.plaatsnummer";
        }
        if (projectionList.containsKey(Constant.TE_BETALEN) || projectionList.containsKey(Constant.BETAALD)
                || projectionList.containsKey(Constant.SALDO)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "standplaats.totaal";
        }
        if (projectionList.containsKey(Constant.NAAM)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.naam";
        }
        if (projectionList.containsKey(Constant.VOORNAAM)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.voornaam";
        }
        if (projectionList.containsKey(Constant.BADGE)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            // selectSql += "badgeVast.badgenummer";
            // if ("VAST".equals(zoekCriteria.get(Constant.BADGETYPE))) {
            selectSql += "badge.badgenummer";
            // } else if ("LOS".equals(zoekCriteria.get(Constant.BADGETYPE))) {
            // selectSql += "badge.badgenummer";
            // }
        }
        if (projectionList.containsKey(Constant.BADGETYPE)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            // selectSql += "badgeVast.badgetype";
            // if ("VAST".equals(zoekCriteria.get(Constant.BADGETYPE))) {
            selectSql += "badge.badgetype";
            // } else if ("LOS".equals(zoekCriteria.get(Constant.BADGETYPE))) {
            // selectSql += "badg.badgetype";
            // }
        }
        if (projectionList.containsKey(Constant.FICHENUMMER)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "inschrijving.fichenummer";
        }
        if (projectionList.containsKey(Constant.AANKOMST)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "inschrijving.date_van";
        }
        if (projectionList.containsKey(Constant.VERTREK)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "inschrijving.date_tot";
        }
        if (projectionList.containsKey(Constant.TYPE_PERSOON)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "inschrijving_persoon.huurderspositie";
        }
        if (projectionList.containsKey(Constant.GEBOORTEDATUM)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.geboortedatum";
        }
        if (projectionList.containsKey(Constant.GEBOORTEPLAATS)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.geboorteplaats";
        }
        if (projectionList.containsKey(Constant.IDENTITEITSKAART)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.identiteitskaartnummer";
        }
        if (projectionList.containsKey(Constant.NATIONALITEIT)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.nationaliteit";
        }
        if (projectionList.containsKey(Constant.OPMERKING)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "persoon.opmerking";
        }
        if (projectionList.containsKey(Constant.STRAAT)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "adres.straat";
        }
        if (projectionList.containsKey(Constant.NUMMER)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "adres.huisnummer";
        }
        if (projectionList.containsKey(Constant.POSTCODE)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "adres.postcode";
        }
        if (projectionList.containsKey(Constant.PLAATS)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "adres.plaats";
        }
        if (projectionList.containsKey(Constant.TELEFOON)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "contactgegeven.waarde";
        }
        if (projectionList.containsKey(Constant.NUMMERPLAAT)) {
            if (selectSql.length() > 0) {
                selectSql += ", ";
            }
            selectSql += "wagen.nummerplaat";
        }

        return selectSql;
    }

    private String createFromString(final Map<String, Object> zoekCriteria, final Map<String, Object> projectionList) {
        String fromSQL = " FROM standplaats standplaats";
        fromSQL += " LEFT JOIN inschrijving inschrijving ON standplaats.id = inschrijving.fk_standplaats_id"
                + " LEFT JOIN inschrijving_persoon inschrijving_persoon ON inschrijving_persoon.fk_inschrijving_id = inschrijving.id"
                + " LEFT JOIN persoon persoon ON inschrijving_persoon.fk_persoon_id = persoon.id"
                + " LEFT JOIN badge badge ON ((badge.id = standplaats.fk_badge_id AND inschrijving.soorthuurder = 'VAST')"
                + " OR (badge.id = inschrijving.fk_badge_id AND inschrijving.soorthuurder = 'LOS'))"
                + " LEFT JOIN factuur_detail factuur_detail ON standplaats.ID = factuur_detail.fk_standplaats_id";
        // if (projectionList.containsKey(Constant.BADGE)) {
        // if ("VAST".equals(zoekCriteria.get(Constant.BADGETYPE))) {
        // fromSQL += " LEFT JOIN badge badge ON badge.id = standplaats.fk_badge_id";
        // } else if ("LOS".equals(zoekCriteria.get(Constant.BADGETYPE))) {
        // fromSQL += " LEFT JOIN badge badge ON badge.id = inschrijving.fk_badge_id";
        // }
        // } else {
        // fromSQL +=
        // " JOIN badge badge ON badge.id = standplaats.fk_badge_id and badge.id = inschrijving.fk_badge_id";
        // }
        fromSQL += createJoinAdresContactWagen();

        return fromSQL;
    }

    private String createFromStringInschrijving() {
        String fromSQL = " FROM standplaats standplaats"
                + " LEFT JOIN badge badgeVast ON badgeVast.id = standplaats.fk_badge_id"
                + " LEFT JOIN inschrijving ON standplaats.id = inschrijving.fk_standplaats_id"
                + " LEFT JOIN inschrijving_persoon ON inschrijving_persoon.fk_inschrijving_id = inschrijving.id"
                + " LEFT JOIN persoon persoon ON inschrijving_persoon.fk_persoon_id=persoon.id"
                + " LEFT JOIN badge badgeLos ON badgeLos.id = inschrijving.fk_badge_id" + createJoinAdresContactWagen();

        return fromSQL;
    }

    private String createFromStringHoofdbetaler() {
        String fromSQL = " FROM standplaats standplaats"
                + " LEFT JOIN badge badgeVast ON badgeVast.id = standplaats.fk_badge_id"
                + " LEFT JOIN betaler betaler ON standplaats.id = betaler.fk_standplaats_id"
                + " LEFT JOIN persoon persoon on betaler.fk_hoofdbetaler_id = persoon.id"
                + createJoinAdresContactWagen();

        return fromSQL;
    }

    private String createFromStringPartnerBetaler() {
        String fromSQL = " FROM standplaats standplaats"
                + " LEFT JOIN badge badgeVast ON badgeVast.id = standplaats.fk_badge_id"
                + " LEFT JOIN betaler betaler ON standplaats.id = betaler.fk_standplaats_id"
                + " LEFT JOIN persoon persoon on betaler.fk_betaler_id = persoon.id" + createJoinAdresContactWagen();

        return fromSQL;
    }

    private String createJoinAdresContactWagen() {
        return " LEFT JOIN adres ON persoon.fk_adres_id = adres.id"
                + " LEFT JOIN contactgegeven ON persoon.id = contactgegeven.fk_persoon_id"
                + " LEFT JOIN wagen ON persoon.id = wagen.fk_persoon_id";
    }

    private String createWhereSql(final Map<String, Object> zoekCriteria) {
        String whereSql = "";

        if (zoekCriteria.containsKey(Constant.TE_BETALEN) || zoekCriteria.containsKey(Constant.BETAALD)
                || zoekCriteria.containsKey(Constant.SALDO)) {
            if (zoekCriteria.containsKey(Constant.SALDO)) {
                if (Constant.VEREFFEND.equals(zoekCriteria.get(Constant.SALDO))) {
                    whereSql += " standplaats.totaal < 0.1";
                } else if (Constant.OPENSTAAND_SALDO.equals(zoekCriteria.get(Constant.SALDO))) {
                    whereSql += " standplaats.totaal > 0.1";
                }
            } else if (zoekCriteria.containsKey(Constant.BETAALD)) {
                whereSql += " factuur_detail.bedrag = '" + zoekCriteria.get(Constant.BETAALD) + "'";
            } else {
                if (zoekCriteria.containsKey(Constant.TE_BETALEN)) {
                    whereSql += " standplaats.totaal = '" + zoekCriteria.get(Constant.TE_BETALEN) + "'";
                }
            }
        } else {
            if (zoekCriteria.containsKey(Constant.PLAATSGROEP)) {
                whereSql += " standplaats.plaatsgroep = '" + zoekCriteria.get(Constant.PLAATSGROEP) + "'";
            }
            if (zoekCriteria.containsKey(Constant.PLAATSNUMMER)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                Integer plaatsnummer = Integer.parseInt((String) zoekCriteria.get(Constant.PLAATSNUMMER));
                whereSql += " standplaats.plaatsnummer = " + plaatsnummer;
            }
            if (zoekCriteria.containsKey(Constant.NAAM)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " persoon.naam LIKE '%" + zoekCriteria.get(Constant.NAAM) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.VOORNAAM)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " persoon.voornaam LIKE '%" + zoekCriteria.get(Constant.VOORNAAM) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.BADGE)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " badge.badgenummer LIKE '%" + zoekCriteria.get(Constant.BADGE) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.BADGETYPE)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " badge.badgetype LIKE '%" + zoekCriteria.get(Constant.BADGETYPE) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.FICHENUMMER)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " inschrijving.fichenummer LIKE '%" + zoekCriteria.get(Constant.FICHENUMMER) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.AANKOMST)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " inschrijving.date_van ";
                if (zoekCriteria.get(Constant.AANKOMST_TYPE).equals(Constant.VAN))
                    whereSql += ">";
                if (zoekCriteria.get(Constant.AANKOMST_TYPE).equals(Constant.TOT))
                    whereSql += "<";
                if (zoekCriteria.get(Constant.AANKOMST_TYPE).equals(Constant.OP))
                    whereSql += "=";
                whereSql += "'" + zoekCriteria.get(Constant.AANKOMST) + "'";
            }
            if (zoekCriteria.containsKey(Constant.VERTREK)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " inschrijving.date_tot ";
                if (zoekCriteria.get(Constant.VERTREK_TYPE).equals(Constant.VAN))
                    whereSql += ">";
                if (zoekCriteria.get(Constant.VERTREK_TYPE).equals(Constant.TOT))
                    whereSql += "<";
                if (zoekCriteria.get(Constant.VERTREK_TYPE).equals(Constant.OP))
                    whereSql += "=";
                whereSql += "'" + zoekCriteria.get(Constant.VERTREK) + "'";
            }
            if (zoekCriteria.containsKey(Constant.TYPE_PERSOON)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += " inschrijving_persoon.huurderspositie = '" + zoekCriteria.get(Constant.TYPE_PERSOON) + "'";
            }
            if (zoekCriteria.containsKey(Constant.GEBOORTEDATUM)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "persoon.geboortedatum = '" + zoekCriteria.get(Constant.GEBOORTEDATUM) + "'";
            }
            if (zoekCriteria.containsKey(Constant.GEBOORTEPLAATS)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "persoon.geboorteplaats LIKE  '%" + zoekCriteria.get(Constant.GEBOORTEPLAATS) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.IDENTITEITSKAART)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "persoon.identiteitskaartnummer LIKE '%" + zoekCriteria.get(Constant.IDENTITEITSKAART)
                        + "%'";
            }
            if (zoekCriteria.containsKey(Constant.NATIONALITEIT)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "persoon.nationaliteit LIKE '%" + zoekCriteria.get(Constant.NATIONALITEIT) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.OPMERKING)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "persoon.opmerking LIKE '%" + zoekCriteria.get(Constant.OPMERKING) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.STRAAT)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "adres.straat LIKE '%" + zoekCriteria.get(Constant.STRAAT) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.NUMMER)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "adres.huisnummer LIKE '%" + zoekCriteria.get(Constant.NUMMER) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.POSTCODE)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "adres.postcode LIKE '%" + zoekCriteria.get(Constant.POSTCODE) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.PLAATS)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "adres.plaats LIKE '%" + zoekCriteria.get(Constant.PLAATS) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.TELEFOON)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "contactgegeven.waarde LIKE '%" + zoekCriteria.get(Constant.TELEFOON) + "%'";
            }
            if (zoekCriteria.containsKey(Constant.NUMMERPLAAT)) {
                if (whereSql.length() > 0) {
                    whereSql += " AND ";
                }
                whereSql += "wagen.nummerplaat LIKE '%" + zoekCriteria.get(Constant.NUMMERPLAAT) + "%'";
            }
        }

        whereSql += " ORDER BY standplaats.plaatsgroep, standplaats.plaatsnummer";
        return " WHERE " + whereSql;
    }

    @Override
    public Map<String, String> getBadgeVoorStandplaats(int id) {
        Map<String, String> map = new HashMap<>();
        Standplaats s = findById(id);
        Badge badge = s.getBadge();
        map.put(Constant.PLAATSGROEP, s.getPlaatsgroep());
        map.put(Constant.PLAATSNUMMER, Integer.toString(s.getPlaatsnummer()));
        if (badge != null) {
            map.put(Constant.BADGE, badge.getBadgenummer());
        }
        return map;
    }

    @Override
    public List<Object[]> zoekNummerplaten(int jaar) {
        String sql = "select DISTINCT standplaats.plaatsgroep, standplaats.plaatsnummer, wagen.nummerplaat from standplaats ";
        sql += " LEFT JOIN inschrijving ON inschrijving.fk_standplaats_id=standplaats.id ";
        sql += " LEFT JOIN inschrijving_persoon ON inschrijving.id=inschrijving_persoon.fk_inschrijving_id ";
        sql += " LEFT JOIN persoon ON persoon.id = inschrijving_persoon.fk_persoon_id ";
        sql += " LEFT JOIN wagen ON persoon.id = wagen.fk_persoon_id ";
        sql += " WHERE wagen.nummerplaat != ''";
        if (jaar != 0) {
            sql += " AND inschrijving.date_tot LIKE '%" + jaar + "%'";
        }
        sql += " ORDER BY standplaats.plaatsgroep, standplaats.plaatsnummer";
//        SQLQuery query = getSession().createSQLQuery(sql);
//        List<Object[]> list = query.list();
//        return list;
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> zoekOpmerkingenGrond(int nummer, boolean printAlles, boolean namenTonen) {
        String sql;
        if (namenTonen) {
            sql = "select DISTINCT standplaats.plaatsgroep, standplaats.plaatsnummer, persoon.naam, persoon.voornaam, grond_informatie.waarde ";
            sql += "from standplaats ";
            sql += " LEFT JOIN grond_informatie ON grond_informatie.fk_grond_id=standplaats.id ";
            sql += " LEFT JOIN betaler ON standplaats.id=betaler.fk_standplaats_id ";
            sql += " LEFT JOIN persoon ON persoon.id = betaler.fk_hoofdbetaler_id ";
            sql += " WHERE grond_informatie.nummer = " + nummer;
        } else {
            sql = "select DISTINCT standplaats.plaatsgroep, standplaats.plaatsnummer, grond_informatie.waarde ";
            sql += "from standplaats ";
            sql += " LEFT JOIN grond_informatie ON grond_informatie.fk_grond_id=standplaats.id ";
            sql += " WHERE grond_informatie.nummer = " + nummer;
        }

        if (!printAlles) {
            sql += " AND grond_informatie.waarde != ''";
        }
        sql += " ORDER BY standplaats.plaatsgroep, standplaats.plaatsnummer";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> haalBadgetoewijzigenOpVoorStandplaats(int id) {
        String sql = "SELECT ID, Badgetype, Badgenummer, Van, Tot from Badgetoewijzingen where Standplaats = " + id
                + " and tot is not null order by Van asc";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findRegistratiesVoorBadgetoewijzing(int id) {
        String sql = "SELECT Koppel from Registratie WHERE Badgetoewijzing = " + id
                + " and Created like '%2010%' order by Created asc";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @Override
    public Object[] findKoppelVoorRegistratie(int id) {
        String sql = "SELECT ID, Partner1, VerwantschapPartner1, Partner2, VerwantschapPartner2 from Koppels WHERE ID = "
                + id;
        return (Object[]) getEntityManager().createNativeQuery(sql).getSingleResult();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findKinderenVanKoppel(int id) {
        String sql = "SELECT Kind, Verwantschap from Kinderen WHERE Koppel = " + id;
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findStandplaatsen() {
        String sql = "SELECT ID from Standplaatsen order by PlaatsGroep, PlaatsNummer";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getOudeFacturatieVoorStandplaats(int id) {
        String sql = "SELECT Standplaats, Bedrag, Datum, Status from Rappels where Standplaats = " + id
                + " order by Datum";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getBetalersAccessVoorStandplaats(int id) {
        String sql = "SELECT Betaler from Betalers where Standplaats = " + id + " order by ID";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Deprecated
    @Override
    public Object[] getBetalingenAccessVoorStandplaats(int id) {
        String sql = "SELECT ID, TeBetalen from Betalingen where Standplaats = " + id;
        return (Object[]) getEntityManager().createNativeQuery(sql).getSingleResult();
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getVoorschottenAccessVoorBetaling(int id) {
        String sql = "SELECT Bedrag, Datum, Status from Voorschotten where Betaling = " + id + " order by Datum";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @Override
    public void removeGrondInformatie(int nummer) {
        String hql = "delete from GROND_INFORMATIE where nummer = " + nummer;
        getEntityManager().createNativeQuery(hql).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Standplaats> zoekStandplaatsenNietBetaald() {
        return getEntityManager().createNamedQuery(Standplaats.Queries.ZoekStandplaatsenNietBetaald.NAME)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Standplaats> zoekStandplaatsenAndereNietBetaald() {
        return getEntityManager().createNamedQuery(Standplaats.Queries.ZoekStandplaatsenAndereNietBetaald.NAME)
                .getResultList();
    }

    @Override
    public List<Map<String, Object>> zoekGrondprijzen() {
        List<Double> prijzen = searchCurrentPrices();
        List<Map<String, Object>> grondprijzen = new ArrayList<>();

        for (Double grondprijs : prijzen) {
            DecimalFormat df = new DecimalFormat("##0.####");
            String sGrondprijs = df.format(grondprijs);
            if (sGrondprijs.contains(",") && !sGrondprijs.contains("."))
                sGrondprijs = sGrondprijs.replace(",", ".");
            String sql = "SELECT COUNT(ID) from standplaats where kostprijs like \"" + sGrondprijs + "\"";
            BigInteger aantal = (BigInteger) getEntityManager().createNativeQuery(sql).getSingleResult();
            Map<String, Object> map = new HashMap<>();
            map.put(Constant.AANTAL_GRONDPRIJS, aantal);
            map.put(Constant.BASISPRIJS, grondprijs);
            grondprijzen.add(map);
        }
        return grondprijzen;
    }

    @Override
    public List<Map<String, Object>> zoekBasisprijzen() {
        List<Double> prijzen = searchBasePrices();
        List<Map<String, Object>> grondprijzen = new ArrayList<>();

        for (Double grondprijs : prijzen) {
            DecimalFormat df = new DecimalFormat("##0.####");
            String sGrondprijs = df.format(grondprijs);
            if (sGrondprijs.contains(",") && !sGrondprijs.contains("."))
                sGrondprijs = sGrondprijs.replace(",", ".");
            String sql = "SELECT COUNT(ID) from standplaats where basisprijs like \"" + sGrondprijs + "\"";
            BigInteger aantal = (BigInteger) getEntityManager().createNativeQuery(sql).getSingleResult();
            Map<String, Object> map = new HashMap<>();
            map.put(Constant.AANTAL_GRONDPRIJS, aantal);
            map.put(Constant.BASISPRIJS, grondprijs);
            grondprijzen.add(map);
        }
        return grondprijzen;
    }

    @Override
    public void adjustBasePrices() {
        List<Double> huidigePrijzen = searchCurrentPrices();
        for (Double prijs : huidigePrijzen) {
            Double nieuwePrijs = prijs / 1.06 - 240;
            DecimalFormat df = new DecimalFormat("#.####");
            df.setRoundingMode(RoundingMode.HALF_EVEN);
            String basisprijs = df.format(nieuwePrijs);
            String sql = "UPDATE standplaats SET BASISPRIJS = " + basisprijs + " WHERE KOSTPRIJS = " + prijs;
            getEntityManager().createNativeQuery(sql).executeUpdate();
        }
    }

    private List<Double> searchCurrentPrices() {
        String sqlZoek = "select distinct kostprijs from standplaats where kostprijs is not null and kostprijs != '' order by kostprijs";
        return getEntityManager().createNativeQuery(sqlZoek).getResultList();
    }

    private List<Double> searchBasePrices() {
        String sqlZoek = "select distinct basisprijs from standplaats where basisprijs is not null and basisprijs != '' order by basisprijs";
        return getEntityManager().createNativeQuery(sqlZoek).getResultList();
    }

    @Override
    public void storeNieuweGrondprijzen(List<Map<String, Double>> prijzen) {
        for (Map<String, Double> map : prijzen) {
            String hql = "UPDATE standplaats" + " SET kostprijs = " + map.get(Constant.NIEUWE_GRONDPRIJS)
                    + ", basisprijs = " + map.get(Constant.NIEUWE_BASISPRIJS) + " WHERE basisprijs like "
                    + map.get(Constant.BASISPRIJS);
            getEntityManager().createNativeQuery(hql).executeUpdate();
        }
    }

    @Override
    public void removeInschrijving(int inschrijvingId) {
        try {
            Inschrijving inschrijving = getEntityManager().find(Inschrijving.class, inschrijvingId);
            for (InschrijvingPersoon inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
                inschrijvingPersoon.setPersoon(null);
            }
            inschrijving.setBadge(null);
            getEntityManager().remove(inschrijving);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void removeGeschiedenis(Geschiedenis geschiedenis) {
        getEntityManager().remove(getEntityManager().contains(geschiedenis) ? geschiedenis : getEntityManager().merge(geschiedenis));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findOpmerkingenVoorFacturatie() {
        return getEntityManager().createNamedQuery(Standplaats.Queries.FindOpmerkingenVoorFacturatie.NAME).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findNummerplaten(int year) {
        String sql = "SELECT DISTINCT standplaats.plaatsgroep, standplaats.plaatsnummer, wagen.nummerplaat "
                + "FROM standplaats, inschrijving, inschrijving_persoon, persoon, wagen "
                + "WHERE standplaats.id = inschrijving.fk_standplaats_id AND inschrijving.id = inschrijving_persoon.fk_inschrijving_id "
                + "AND inschrijving_persoon.fk_persoon_id = persoon.id AND persoon.id = wagen.fk_persoon_id "
                + "AND inschrijving.soorthuurder = 'VAST' AND inschrijving.date_van LIKE '%" + year + "%' "
                + "order by standplaats.plaatsgroep, standplaats.plaatsnummer";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getStandplaatsEnKostprijs() {
        String sql = "SELECT ID, KOSTPRIJS, TOTAAL FROM standplaats ORDER BY PLAATSGROEP, PLAATSNUMMER";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }
}