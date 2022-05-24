package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Factuur;

public class FactuurAssembler {

	public Factuur getFactuur (FactuurDTO factuurDTO) {
		Factuur factuur = new Factuur();
		factuur.setId(factuurDTO.getId());
		factuur.setBetaald(factuurDTO.isBetaald());
		factuur.setJaar(factuurDTO.getJaar());
/*		Set<FactuurDetail> factuurDetails = new HashSet<FactuurDetail>();
		FactuurDetailAssembler factuurDetailAssembler = new FactuurDetailAssembler();
		for (FactuurDetailDTO factuurDetailDTO : factuurDTO.getFactuurDetails()) {
			factuurDetails.add(factuurDetailAssembler.getFactuurDetail(factuurDetailDTO));
		}*/
//		factuur.setFactuurDetails(factuurDetails);
		return factuur;
	}

	public FactuurDTO getFactuurDTO (Factuur factuur) {
		FactuurDTO factuurDTO = new FactuurDTO();
		factuurDTO.setId(factuur.getId());
		factuurDTO.setBetaald(factuur.isBetaald());
		factuurDTO.setJaar(factuur.getJaar());
//		factuurDTO.setFactuurDetails(new HashSet<FactuurDetailDTO>());
		return factuurDTO;
	}

	public FactuurDTO getFactuurDTOWithFactuurDetails (Factuur factuur) {
		FactuurDTO factuurDTO = new FactuurDTO();
		factuurDTO.setId(factuur.getId());
		factuurDTO.setBetaald(factuur.isBetaald());
		factuurDTO.setJaar(factuur.getJaar());
/*		Set<FactuurDetailDTO> factuurDetailDTOs = new HashSet<FactuurDetailDTO>();
		FactuurDetailAssembler factuurDetailAssembler = new FactuurDetailAssembler();
		for (FactuurDetail factuurDetail : factuur.getFactuurDetails()) {
			factuurDetailDTOs.add(factuurDetailAssembler.getFactuurDetailDTO(factuurDetail));
		}*/
//		factuurDTO.setFactuurDetails(factuurDetailDTOs);
		return factuurDTO;
	}
}
