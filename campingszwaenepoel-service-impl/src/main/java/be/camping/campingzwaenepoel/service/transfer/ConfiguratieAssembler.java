package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Configuratie;

public class ConfiguratieAssembler {
	
	public ConfiguratieDTO getConfiguratieDTO (Configuratie configuratie) {
		ConfiguratieDTO configuratieDTO = null;
		if (null != configuratie) {
			configuratieDTO = new ConfiguratieDTO();
			configuratieDTO.setId(configuratie.getId());
			configuratieDTO.setNaam(configuratie.getNaam());
			configuratieDTO.setWaarde(configuratie.getWaarde());
		}
		return configuratieDTO;
	}
	
	public Configuratie getConfiguratie (ConfiguratieDTO configuratieDTO) {
		Configuratie configuratie = new Configuratie();
		configuratie.setId(configuratieDTO.getId());
		configuratie.setNaam(configuratieDTO.getNaam());
		configuratie.setWaarde(configuratieDTO.getWaarde());
		return configuratie;
	}
}
