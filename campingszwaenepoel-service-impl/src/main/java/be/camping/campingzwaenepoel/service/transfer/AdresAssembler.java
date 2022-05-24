package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Adres;

public class AdresAssembler {

	public Adres getAdres (AdresDTO adresDTO) {
		Adres adres = new Adres();
		adres.setBus(adresDTO.getBus());
		adres.setHuisnummer(adresDTO.getHuisnummer());
		adres.setId(adresDTO.getId());
		adres.setLand(adresDTO.getLand());
		adres.setPlaats(adresDTO.getPlaats());
		adres.setPostcode(adresDTO.getPostcode());
		adres.setStraat(adresDTO.getStraat());
		return adres;
	}

	public AdresDTO getAdresDTO (Adres adres) {
		AdresDTO adresDTO = new AdresDTO();
		adresDTO.setBus(adres.getBus());
		adresDTO.setHuisnummer(adres.getHuisnummer());
		adresDTO.setId(adres.getId());
		adresDTO.setLand(adres.getLand());
		adresDTO.setPlaats(adres.getPlaats());
		adresDTO.setPostcode(adres.getPostcode());
		adresDTO.setStraat(adres.getStraat());
		return adresDTO;
	}
}
