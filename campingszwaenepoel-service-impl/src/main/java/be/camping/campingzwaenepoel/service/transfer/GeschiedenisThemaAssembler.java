package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisThema;

public class GeschiedenisThemaAssembler {

	public GeschiedenisThema getGeschiedenisThema(GeschiedenisThemaDTO geschiedenisThemaDTO) {
		GeschiedenisThema geschiedenisThema = new GeschiedenisThema();
		geschiedenisThema.setId(geschiedenisThemaDTO.getId());
		geschiedenisThema.setThema(geschiedenisThemaDTO.getThema());
		return geschiedenisThema;
	}

	public GeschiedenisThemaDTO getGeschiedenisThemaDTO(GeschiedenisThema geschiedenisThema) {
		GeschiedenisThemaDTO geschiedenisThemaDTO = new GeschiedenisThemaDTO();
		geschiedenisThemaDTO.setId(geschiedenisThema.getId());
		geschiedenisThemaDTO.setThema(geschiedenisThema.getThema());
		return geschiedenisThemaDTO;
	}
}
