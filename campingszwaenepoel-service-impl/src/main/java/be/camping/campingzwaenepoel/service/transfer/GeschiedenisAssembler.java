package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Geschiedenis;

public class GeschiedenisAssembler {

	public Geschiedenis getGeschiedenis(GeschiedenisDTO geschiedenisDTO) {
		Geschiedenis geschiedenis = new Geschiedenis();
		geschiedenis.setId(geschiedenisDTO.getId());
		geschiedenis.setVerwittiging(geschiedenisDTO.getVerwittiging());
		geschiedenis.setDatum(geschiedenisDTO.getDatum());
		geschiedenis.setNaam(geschiedenisDTO.getNaam());
		geschiedenis.setThema(geschiedenisDTO.getThema());
		geschiedenis.setCommunicatie(geschiedenisDTO.getCommunicatie());
		geschiedenis.setGeschiedenis(geschiedenisDTO.getGeschiedenis());
		return geschiedenis;
	}

	public GeschiedenisDTO getGeschiedenisDTO(Geschiedenis geschiedenis) {
		GeschiedenisDTO geschiedenisDTO = new GeschiedenisDTO();
		geschiedenisDTO.setId(geschiedenis.getId());
		geschiedenisDTO.setVerwittiging(geschiedenis.getVerwittiging());
		geschiedenisDTO.setDatum(geschiedenis.getDatum());
		geschiedenisDTO.setNaam(geschiedenis.getNaam());
		geschiedenisDTO.setThema(geschiedenis.getThema());
		geschiedenisDTO.setCommunicatie(geschiedenis.getCommunicatie());
		geschiedenisDTO.setGeschiedenis(geschiedenis.getGeschiedenis());
		return geschiedenisDTO;
	}
}
