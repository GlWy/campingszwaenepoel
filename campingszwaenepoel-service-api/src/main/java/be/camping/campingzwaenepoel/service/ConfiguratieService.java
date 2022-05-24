package be.camping.campingzwaenepoel.service;

import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;

public interface ConfiguratieService {
	
	ConfiguratieDTO getConfiguratie(int id);
	
	ConfiguratieDTO getConfiguratie(String naam);
	
	ConfiguratieDTO storeConfiguratie(ConfiguratieDTO configuratieDTO);

	ConfiguratieDTO getFileDirectory();
}
