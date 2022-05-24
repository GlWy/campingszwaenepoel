package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Kasbon;

public class KasbonAssembler {

	public Kasbon getKasbon(KasbonDTO kasbonDTO) {
		Kasbon kasbon = new Kasbon();
		kasbon.setId(kasbonDTO.getId());
		kasbon.setKasbonnummer(kasbonDTO.getKasbonnummer());
		kasbon.setAantalNachten(kasbonDTO.getAantalNachten());
		kasbon.setAuto100(kasbonDTO.getAuto100());
		kasbon.setAuto150(kasbonDTO.getAuto150());
		kasbon.setWaarborg(kasbonDTO.getWaarborg());
		kasbon.setJeton(kasbonDTO.getJeton());
		kasbon.setZakGeel(kasbonDTO.getZakGeel());
		kasbon.setZakBlauw(kasbonDTO.getZakBlauw());
		kasbon.setCar(kasbonDTO.getCar());
		kasbon.setTent(kasbonDTO.getTent());
		kasbon.setVolwassenen(kasbonDTO.getVolwassenen());
		kasbon.setKinderen(kasbonDTO.getKinderen());
		kasbon.setTelefoon(kasbonDTO.getTelefoon());
		kasbon.setKasbonDatum(kasbonDTO.getKasbonDatum());
		kasbon.setNaam(kasbonDTO.getNaam());
		kasbon.setStandplaats(kasbonDTO.getStandplaats());
		kasbon.setNummerplaat(kasbonDTO.getNummerplaat());
		kasbon.setBadge(kasbonDTO.getBadge());
		kasbon.setDatumVan(kasbonDTO.getDatumVan());
		kasbon.setDatumTot(kasbonDTO.getDatumTot());
		kasbon.setBetaling(kasbonDTO.getBetaling());
		kasbon.setTerug(kasbonDTO.getTerug());
		kasbon.setComputer(kasbonDTO.getComputer());
		kasbon.setCashSource(kasbonDTO.getCashSource());
		return kasbon;
	}

	public KasbonDTO getKasboDTO(Kasbon kasbon) {
		KasbonDTO kasbonDTO = new KasbonDTO();
		kasbonDTO.setId(kasbon.getId());
		kasbonDTO.setKasbonnummer(kasbon.getKasbonnummer());
		kasbonDTO.setAantalNachten(kasbon.getAantalNachten());
		kasbonDTO.setAuto100(kasbon.getAuto100());
		kasbonDTO.setAuto150(kasbon.getAuto150());
		kasbonDTO.setWaarborg(kasbon.getWaarborg());
		kasbonDTO.setJeton(kasbon.getJeton());
		kasbonDTO.setZakGeel(kasbon.getZakGeel());
		kasbonDTO.setZakBlauw(kasbon.getZakBlauw());
		kasbonDTO.setCar(kasbon.getCar());
		kasbonDTO.setTent(kasbon.getTent());
		kasbonDTO.setVolwassenen(kasbon.getVolwassenen());
		kasbonDTO.setKinderen(kasbon.getKinderen());
		kasbonDTO.setTelefoon(kasbon.getTelefoon());
		kasbonDTO.setKasbonDatum(kasbon.getKasbonDatum());
		kasbonDTO.setNaam(kasbon.getNaam());
		kasbonDTO.setStandplaats(kasbon.getStandplaats());
		kasbonDTO.setNummerplaat(kasbon.getNummerplaat());
		kasbonDTO.setBadge(kasbon.getBadge());
		kasbonDTO.setDatumVan(kasbon.getDatumVan());
		kasbonDTO.setDatumTot(kasbon.getDatumTot());
		kasbonDTO.setBetaling(kasbon.getBetaling());
		kasbonDTO.setTerug(kasbon.getTerug());
		kasbonDTO.setComputer(kasbon.getComputer());
		kasbonDTO.setCashSource(kasbon.getCashSource());
		return kasbonDTO;
	}
}
