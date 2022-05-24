package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.StandplaatsDetail;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDetailDTO;

public class StandplaatsDetailAssembler {

	public StandplaatsDetailDTO getStandplaatsDetailDTO(StandplaatsDetail standplaatsDetail) {
		StandplaatsDetailDTO standplaatsDetailDTO = new StandplaatsDetailDTO();
		standplaatsDetailDTO.setId(standplaatsDetail.getId());
		standplaatsDetailDTO.setDatumveld(standplaatsDetail.isDatumveld());
		standplaatsDetailDTO.setNaam(standplaatsDetail.getNaam());
		standplaatsDetailDTO.setNummer(standplaatsDetail.getNummer());
		standplaatsDetailDTO.setVerplicht(standplaatsDetail.isVerplicht());
		standplaatsDetailDTO.setPrintAlles(standplaatsDetail.isPrintAlles());
		standplaatsDetailDTO.setNamenTonen(standplaatsDetail.isNamenTonen());
		return standplaatsDetailDTO;
	}

	public StandplaatsDetail getStandplaatsDetail(StandplaatsDetailDTO standplaatsDetailDTO) {
		StandplaatsDetail standplaatsDetail = new StandplaatsDetail();
		standplaatsDetail.setId(standplaatsDetailDTO.getId());
		standplaatsDetail.setDatumveld(standplaatsDetailDTO.isDatumveld());
		standplaatsDetail.setNaam(standplaatsDetailDTO.getNaam());
		standplaatsDetail.setNummer(standplaatsDetailDTO.getNummer());
		standplaatsDetail.setVerplicht(standplaatsDetailDTO.isVerplicht());
		standplaatsDetail.setPrintAlles(standplaatsDetailDTO.isPrintAlles());
		standplaatsDetail.setNamenTonen(standplaatsDetailDTO.isNamenTonen());
		return standplaatsDetail;
	}
}
