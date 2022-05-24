package be.camping.campingzwaenepoel.domain.repository;

import be.camping.campingzwaenepoel.domain.model.Configuratie;

public interface ConfiguratieRepository {

	public Configuratie findById (int id);
	
	public Configuratie findByNaam (String naam);
	
	public Configuratie store(Configuratie configuratie);
}
