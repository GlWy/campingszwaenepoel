package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.GeschiedenisThemaDTO;

public interface GeschiedenisThemaService {

	public GeschiedenisThemaDTO findGeschiedenisThema(int id);
	
	public GeschiedenisThemaDTO storeGeschiedenisThema(GeschiedenisThemaDTO geschiedenisThemaDTO);
	
	public void remove(GeschiedenisThemaDTO geschiedenisThemaDTO);
	
	public List<GeschiedenisThemaDTO> findAllThemas();
	
	public GeschiedenisThemaDTO findGeschiedenisThema(String thema);

}
