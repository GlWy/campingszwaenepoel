package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisThema;

public interface GeschiedenisThemaRepository {

	public GeschiedenisThema findById(int id);
	
	public GeschiedenisThema store(GeschiedenisThema geschiedenisThema);
	
	public void remove(GeschiedenisThema geschiedenisThema);
	
	public List<GeschiedenisThema> findAll();
	
	public GeschiedenisThema findByThema(String thema);
}
