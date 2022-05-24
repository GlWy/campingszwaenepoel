package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.KassaKostprijs;

public class KassaKostprijsAssembler {

	public KassaKostprijs getKassaKostprijs(KassaKostprijsDTO kassaKostprijsDTO) {
		KassaKostprijs kassaKostprijs = new KassaKostprijs();
		kassaKostprijs.setId(kassaKostprijsDTO.getId());
		kassaKostprijs.setNummer(kassaKostprijsDTO.getNummer());
		kassaKostprijs.setNaam(kassaKostprijsDTO.getNaam());
		kassaKostprijs.setWaarde(kassaKostprijsDTO.getWaarde());
		return kassaKostprijs;
	}

	public KassaKostprijsDTO getKassaKostprijsDTO(KassaKostprijs kassaKostprijs) {
		KassaKostprijsDTO kassaKostprijsDTO = new KassaKostprijsDTO();
		kassaKostprijsDTO.setId(kassaKostprijs.getId());
		kassaKostprijsDTO.setNummer(kassaKostprijs.getNummer());
		kassaKostprijsDTO.setNaam(kassaKostprijs.getNaam());
		kassaKostprijsDTO.setWaarde(kassaKostprijs.getWaarde());
		return kassaKostprijsDTO;
	}
}
