package be.camping.campingzwaenepoel.service.impl;

import be.camping.campingzwaenepoel.common.constants.PathConstant;
import be.camping.campingzwaenepoel.domain.model.Configuratie;
import be.camping.campingzwaenepoel.domain.repository.ConfiguratieRepository;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieAssembler;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ConfiguratieService")
@Transactional
public class ConfiguratieServiceImpl implements ConfiguratieService {

	@Autowired
	private ConfiguratieRepository configuratieRepository;
	
	@Override
	public ConfiguratieDTO getConfiguratie(int id) {
		Configuratie configuratie = configuratieRepository.findById(id);
		ConfiguratieAssembler assembler = new ConfiguratieAssembler();
		return assembler.getConfiguratieDTO(configuratie);
	}

	@Override
	public ConfiguratieDTO getConfiguratie(String naam) {
		Configuratie configuratie = configuratieRepository.findByNaam(naam);
		ConfiguratieDTO configuratieDTO = null;
		if (configuratie != null) {
			ConfiguratieAssembler assembler = new ConfiguratieAssembler();
			configuratieDTO = assembler.getConfiguratieDTO(configuratie);
		}
		return configuratieDTO;
	}

	@Override
	public ConfiguratieDTO storeConfiguratie(ConfiguratieDTO configuratieDTO) {
		ConfiguratieAssembler assembler = new ConfiguratieAssembler();
		Configuratie configuratie = assembler.getConfiguratie(configuratieDTO);
		configuratieRepository.store(configuratie);
		return assembler.getConfiguratieDTO(configuratie);
	}

	@Override
    public ConfiguratieDTO getFileDirectory() {
	    return getConfiguratie(PathConstant.getFotoDirectoryPath());
    }
}
