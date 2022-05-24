package be.camping.campingzwaenepoel.service;

import java.util.Calendar;

import be.camping.campingzwaenepoel.common.enums.GeslachtEnum;
import be.camping.campingzwaenepoel.common.enums.HuurderPositieEnum;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.common.enums.TaalEnum;
import be.camping.campingzwaenepoel.domain.model.Betaler;
import be.camping.campingzwaenepoel.domain.model.InschrijvingPersoon;
import be.camping.campingzwaenepoel.service.transfer.AdministratieDTO;
import be.camping.campingzwaenepoel.service.transfer.FactuurDTO;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingDTO;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingPersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.KasbonDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.BetalerDTO;

public class DTOFactory {

	public static PersoonDTO getPersoonDTO() {
		PersoonDTO persoonDTO = new PersoonDTO();
		persoonDTO.setTaal(TaalEnum.NL);
		persoonDTO.setGeslacht(GeslachtEnum.O);
		return persoonDTO;
	}

	public static AdministratieDTO getAdministratieDTO() {
		AdministratieDTO administratieDTO = new AdministratieDTO();
		administratieDTO.setId(0);
		administratieDTO.setBareelGetekendNaam(0);
		administratieDTO.setBareelUitlegNaam(0);
		administratieDTO.setReglementGetekendNaam(0);
		administratieDTO.setReglementUitlegNaam(0);
		return administratieDTO;
	}
	
	public static InschrijvingDTO getInschrijvingDTO() {
		InschrijvingDTO inschrijvingDTO = new InschrijvingDTO();
		inschrijvingDTO.setSoorthuurder(SoortHuurderEnum.ONBEPAALD);
		return inschrijvingDTO;
	}
	
	public static Betaler getStandplaatsBetaler() {
		return new Betaler();
	}
	
	public static BetalerDTO getBetalerDTO() {
		BetalerDTO betaler = new BetalerDTO();
		betaler.setBareelGetekendNaam(0);
		betaler.setBareelUitlegNaam(0);
		betaler.setReglementGetekendNaam(0);
		betaler.setReglementUitlegNaam(0);
		return betaler;
	}
	
	public static InschrijvingPersoonDTO getInschrijvingPersoonDTO() {
		InschrijvingPersoonDTO inschrijvingPersoon = new InschrijvingPersoonDTO();
		inschrijvingPersoon.setInschrijvingDatum(System.nanoTime());
		inschrijvingPersoon.setHuurdersPositie(HuurderPositieEnum.ANDERE);
		return inschrijvingPersoon;
	}
	
	public static InschrijvingPersoon getInschrijvingPersoon() {
		return new InschrijvingPersoon();
	}
	
	public static FactuurDetailDTO getFactuurDetail() {
		FactuurDetailDTO factuurDetail = new FactuurDetailDTO();
		factuurDetail.setDatumAanmaak(System.nanoTime());
		factuurDetail.setShow(true);
		return factuurDetail;
	}

	public static FactuurDTO getFactuur() {
		FactuurDTO factuur = new FactuurDTO();
		factuur.setBetaald(false);
		Calendar now = Calendar.getInstance();
		factuur.setJaar(now.get(Calendar.YEAR));
		return factuur;
	}

	public static KasbonDTO createKasbon() {
		KasbonDTO kasbon = new KasbonDTO();
		kasbon.setAantalNachten(1);
		kasbon.setAuto100(0);
		kasbon.setAuto150(0);
		kasbon.setWaarborg(0);
		kasbon.setJeton(0);
		kasbon.setZakGeel(0);
		kasbon.setZakBlauw(0);
		kasbon.setCar(0);
		kasbon.setTent(0);
		kasbon.setVolwassenen(0);
		kasbon.setKinderen(0);
		kasbon.setTelefoon(new Double(0));
		kasbon.setNaam("Kasbon");
		kasbon.setBetaling(new Double(0));
		kasbon.setTerug(new Double(0));
		return kasbon;
	}

}
