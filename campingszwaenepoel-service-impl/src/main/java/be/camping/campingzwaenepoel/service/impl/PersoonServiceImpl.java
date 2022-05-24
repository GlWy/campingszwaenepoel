package be.camping.campingzwaenepoel.service.impl;

import be.belgium.eid.eidlib.BeID;
import be.belgium.eid.exceptions.EIDException;
import be.belgium.eid.objects.IDAddress;
import be.belgium.eid.objects.IDData;
import be.camping.campingzwaenepoel.common.enums.GeslachtEnum;
import be.camping.campingzwaenepoel.common.enums.LandEnum;
import be.camping.campingzwaenepoel.domain.model.Adres;
import be.camping.campingzwaenepoel.domain.model.Persoon;
import be.camping.campingzwaenepoel.domain.repository.ContactgegevenRepository;
import be.camping.campingzwaenepoel.domain.repository.PersoonRepository;
import be.camping.campingzwaenepoel.domain.repository.WagenRepository;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service("PersoonService")
@Transactional
public class PersoonServiceImpl implements PersoonService {

    @Autowired
    private PersoonRepository persoonRepository;

    @Autowired
    private ContactgegevenRepository contactgegevenRepository;

    @Autowired
    private WagenRepository wagenRepository;

    @Override
    public List<Object> getPersonen() {
        return persoonRepository.getPersonen();
    }

    @Override
    public List<Object[]> zoekPersonen(Map<String, Object> zoekCriteria) {
        for (String key : zoekCriteria.keySet()) {
            if (zoekCriteria.get(key) instanceof String) {
                String value = (String) zoekCriteria.get(key);
                if (value.contains("\u00E0")) {
                    value.replaceAll("\u00E0", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00E1")) {
                    value.replaceAll("\u00E1", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00E2")) {
                    value.replaceAll("\u00E2", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00E3")) {
                    value.replaceAll("\u00E3", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00E4")) {
                    value.replaceAll("\u00E4", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00E8")) {
                    value.replaceAll("\u00E8", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00E9")) {
                    value.replaceAll("\u00E9", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00EA")) {
                    value.replaceAll("\u00EA", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00EB")) {
                    value.replaceAll("\u00EB", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00F9")) {
                    value.replaceAll("\u00F9", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00FA")) {
                    value.replaceAll("\u00FA", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00FB")) {
                    value.replaceAll("\u00FB", "%");
                    zoekCriteria.put(key, value);
                }
                if (value.contains("\u00FC")) {
                    value.replaceAll("\u00FC", "%");
                    zoekCriteria.put(key, value);
                }
            }
        }
        List<Object[]> personen = persoonRepository.zoekPersonen(zoekCriteria);
        return personen;
    }

    @Override
    public List<PersoonDTO> zoekPersonen(Calendar geboortedatum, String naam,
                                         String voornaam) {
        List<Persoon> personen = persoonRepository.zoekPersonen(geboortedatum, naam,
                voornaam);
        List<PersoonDTO> persoonDTOs = new ArrayList<PersoonDTO>();
        PersoonAssembler persoonAssembler = new PersoonAssembler();
        for (Persoon persoon : personen) {
            persoonDTOs.add(persoonAssembler.getPersoonDTO(persoon));
        }
        return persoonDTOs;
    }

    @Override
    public PersoonDTO store(PersoonDTO persoonDTO) {
        PersoonAssembler assembler = new PersoonAssembler();
        Persoon persoon = persoonRepository.store(assembler.getPersoon(persoonDTO));
        PersoonDTO persoonDTO2 = assembler.getPersoonDTO(persoon);
        return persoonDTO2;
    }

    @Override
    public PersoonDTO findPersoonById(int id) {
        Persoon persoon = persoonRepository.findById(id);
        PersoonDTO persoonDTO = null;
        if (null != persoon) {
            PersoonAssembler persoonAssembler = new PersoonAssembler();
            persoonDTO = persoonAssembler.getPersoonDTO(persoon);
        }
        return persoonDTO;
    }

    @Override
    public void removeContactgegeven(ContactgegevenDTO contactgegeven) {
        ContactgegevenAssembler assembler = new ContactgegevenAssembler();
        contactgegevenRepository.removeContactgegeven(assembler.getContactgegeven(contactgegeven));
    }

    @Override
    public void removeWagen(WagenDTO wagen) {
        WagenAssembler assembler = new WagenAssembler();
        wagenRepository.removeWagen(assembler.getWagen(wagen));
    }

    @Override
    public List<String> getPersoonNamen() {
        List<Object> list = getPersonen();
        List<String> retList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            Object[] o = (Object[]) list.get(i);
            String naam = (String) o[0] + " " + (String) o[1];
            retList.add(naam);
        }
        if (retList.contains(" ")) {
            retList.remove(" ");
        }
        if (!retList.contains("")) {
            retList.add(0, "");
        }
        return retList;
    }

//	@Override
//	public Map<String, Object> retrieveCardData() {
//		logger.info("retrieve card data");
//		Map<String, Object> cardData = new HashMap<String, Object>();
//		BeID eID = new BeID(true);
//		try {
//			Persoon persoon = getPersoonDataFromEid(eID);
//			PersoonAssembler assembler = new PersoonAssembler();
//			cardData.put(Constant.ERRORCODE, 1);
//			cardData.put(Constant.ERRORMESSAGE, "");
//			cardData.put(Constant.PERSOON, assembler.getPersoonDTO(persoon));
//			cardData.put(Constant.PASFOTO, eID.getIDPhoto().getPhoto());
//		} catch (EIDException e) {
//			cardData.put(Constant.ERRORCODE, 0);
//			cardData.put(Constant.ERRORMESSAGE, "error occured reading the card data");
//			e.printStackTrace();
//		}
//		return cardData;
//	}

    @Override
    public PersoonDTO getPersoonDataFromEid(final BeID beID) throws EIDException {
        Persoon persoon = new Persoon();
        addEidDataToPersoonData(beID, persoon);
        addAddressDataToPersoonData(beID, persoon);
        PersoonAssembler assembler = new PersoonAssembler();
        return assembler.getPersoonDTO(persoon);
    }


    private Persoon addEidDataToPersoonData(final BeID beID, final Persoon persoon)
            throws EIDException {
        IDData idData = beID.getIDData();
        persoon.setNaam(decodeUTF8(encodeIso88591(idData.getName())));
        persoon.setVoornaam(decodeUTF8(encodeIso88591(idData.get1stFirstname())));
        persoon.setNationaliteit(decodeUTF8(encodeIso88591(idData.getNationality())));
        persoon.setGeboorteplaats(decodeUTF8(encodeIso88591(idData.getBirthPlace())));
        persoon.setGeslacht((new Character('M').equals(idData.getSex()) ? GeslachtEnum.M : GeslachtEnum.V));
        persoon.setGeboortedatum(idData.getBirthDate());
        persoon.setIdentiteitskaartnummer(idData.getCardNumber());
        persoon.setRijksregisternummer(idData.getNationalNumber());
        return persoon;
    }

    private Persoon addAddressDataToPersoonData(final BeID beID, final Persoon persoon) throws EIDException {
        IDAddress idAddress = beID.getIDAddress();
        Adres adres = new Adres();
        adres.setLand(LandEnum.BE);
        adres.setPlaats(decodeUTF8(encodeIso88591(idAddress.getMunicipality())));
        adres.setStraat(decodeUTF8(encodeIso88591(idAddress.getStreet())));
        adres.setPostcode(idAddress.getZipCode());
        persoon.setAdres(adres);
        return persoon;
    }


    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private final Charset ISO8891_CHARSET = Charset.forName("ISO-8859-1");

    byte[] encodeIso88591(String string) {
        // byte[] iso88591Data = null;
        // iso88591Data = "Cyrène".getBytes("ISO-8859-1");
        // iso88591Data = "Grégory Léon".getBytes("ISO-8859-1");
        return string.getBytes(ISO8891_CHARSET);
    }


    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }


    @Override
    public byte[] getPicture(final BeID beID) throws EIDException {
//		return EidReader.getPicture();
        return beID.getIDPhoto().getPhoto();
    }

    @Override
    public void changePersoon(int newId, int oldId) {
        persoonRepository.changePersoon(newId, oldId);
    }
}