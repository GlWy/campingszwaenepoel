package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.FactuurDetail;

public class FactuurDetailAssembler {

	public FactuurDetailDTO getFactuurDetailDTO(FactuurDetail factuurDetail) {
		FactuurDetailDTO factuurDetailDTO = new FactuurDetailDTO();
		factuurDetailDTO.setId(factuurDetail.getId());
		factuurDetailDTO.setDatum(factuurDetail.getDatum());
		factuurDetailDTO.setAardBetaling(factuurDetail.getAardBetaling());
		factuurDetailDTO.setBedrag(factuurDetail.getBedrag());
		factuurDetailDTO.setUitstelkosten(factuurDetail.getUitstelkosten());
		factuurDetailDTO.setRappel(factuurDetail.getRappel());
		factuurDetailDTO.setAndereKosten(factuurDetail.getAndereKosten());
		factuurDetailDTO.setOpmerking(factuurDetail.getOpmerking());
		factuurDetailDTO.setDatumAanmaak(factuurDetail.getDatumAanmaak());
		factuurDetailDTO.setShow(factuurDetail.isShow());
		return factuurDetailDTO;
	}

	public FactuurDetail getFactuurDetail(FactuurDetailDTO factuurDetailDTO) {
		FactuurDetail factuurDetail = new FactuurDetail();
		factuurDetail.setId(factuurDetailDTO.getId());
		factuurDetail.setDatum(factuurDetailDTO.getDatum());
		factuurDetail.setAardBetaling(factuurDetailDTO.getAardBetaling());
		factuurDetail.setBedrag(factuurDetailDTO.getBedrag());
		factuurDetail.setUitstelkosten(factuurDetailDTO.getUitstelkosten());
		factuurDetail.setRappel(factuurDetailDTO.getRappel());
		factuurDetail.setAndereKosten(factuurDetailDTO.getAndereKosten());
		factuurDetail.setOpmerking(factuurDetailDTO.getOpmerking());
		factuurDetail.setDatumAanmaak(factuurDetailDTO.getDatumAanmaak());
		factuurDetail.setShow(factuurDetailDTO.isShow());
		return factuurDetail;
	}
}
