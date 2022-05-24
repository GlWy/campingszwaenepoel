package be.camping.campingzwaenepoel.service.impl;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisCommunicatie;
import be.camping.campingzwaenepoel.domain.repository.GeschiedenisCommunicatieRepository;
import be.camping.campingzwaenepoel.service.GeschiedenisCommunicatieService;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisCommunicatieAssembler;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisCommunicatieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("GeschiedenisCommunicatieService")
@Transactional
public class GeschiedenisCommunicatieServiceImpl implements	GeschiedenisCommunicatieService {

	@Autowired
	private GeschiedenisCommunicatieRepository geschiedenisCommunicatieRepository;
	
	@Override
	public List<GeschiedenisCommunicatieDTO> findAllCommunicaties() {
		GeschiedenisCommunicatieAssembler assembler = new GeschiedenisCommunicatieAssembler();
		List<GeschiedenisCommunicatieDTO> communicatieDtos = new ArrayList<>();
		List<GeschiedenisCommunicatie> communicaties = geschiedenisCommunicatieRepository.findAll();
		for (GeschiedenisCommunicatie communicatie : communicaties) {
			communicatieDtos.add(assembler.getGeschiedenisCommunicatieDTO(communicatie));
		}
		return communicatieDtos;
	}

	@Override
	public GeschiedenisCommunicatieDTO findGeschiedenisCommunicatie(int id) {
		GeschiedenisCommunicatieAssembler assembler = new GeschiedenisCommunicatieAssembler();
		GeschiedenisCommunicatie communicatie = geschiedenisCommunicatieRepository.findById(id);
		return assembler.getGeschiedenisCommunicatieDTO(communicatie);
	}

	@Override
	public GeschiedenisCommunicatieDTO findGeschiedenisCommunicatie(String communicatie) {
		GeschiedenisCommunicatieAssembler assembler = new GeschiedenisCommunicatieAssembler();
		GeschiedenisCommunicatie geschiedenisCommunicatie = geschiedenisCommunicatieRepository.findByCommunicatie(communicatie);
		return assembler.getGeschiedenisCommunicatieDTO(geschiedenisCommunicatie);
	}

	@Override
	public void removeGeschiedenisCommunicatie(GeschiedenisCommunicatieDTO geschiedenisCommunicatieDTO) {
		GeschiedenisCommunicatieAssembler assembler = new GeschiedenisCommunicatieAssembler();
		geschiedenisCommunicatieRepository.remove(assembler.getGeschiedenisCommunicatie(geschiedenisCommunicatieDTO));
	}

	@Override
	public GeschiedenisCommunicatieDTO storeGeschiedenisCommunicatie(GeschiedenisCommunicatieDTO geschiedenisCommunicatieDTO) {
		GeschiedenisCommunicatieAssembler assembler = new GeschiedenisCommunicatieAssembler();
		GeschiedenisCommunicatie communicatie = geschiedenisCommunicatieRepository.store(assembler.getGeschiedenisCommunicatie(geschiedenisCommunicatieDTO));
		geschiedenisCommunicatieDTO.setId(communicatie.getId());
		return geschiedenisCommunicatieDTO;
	}
}