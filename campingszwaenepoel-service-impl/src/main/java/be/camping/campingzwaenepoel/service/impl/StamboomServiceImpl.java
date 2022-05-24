package be.camping.campingzwaenepoel.service.impl;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.Stamboom;
import be.camping.campingzwaenepoel.domain.repository.StamboomRepository;
import be.camping.campingzwaenepoel.service.StamboomService;
import be.camping.campingzwaenepoel.service.transfer.StamboomAssembler;
import be.camping.campingzwaenepoel.service.transfer.StamboomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("StamboomService")
@Transactional
public class StamboomServiceImpl implements StamboomService {

	@Autowired
	private StamboomRepository stamboomRepository;
	
	@Override
	public List<StamboomDTO> getStamboom(int standplaatsId) {
		List<Stamboom> personen = stamboomRepository.getStamboom(standplaatsId);
		List<StamboomDTO> persoonDTOs = new ArrayList<StamboomDTO>();
		StamboomAssembler assembler = new StamboomAssembler();
		for (Stamboom stamboom : personen) {
			persoonDTOs.add(assembler.getStamBoomDTO(stamboom));
		}
		return persoonDTOs;
	}

	@Override
	public void removeStamboom(StamboomDTO stamboomDTO) {
		StamboomAssembler assembler = new StamboomAssembler();
		stamboomRepository.remove(assembler.getStamBoom(stamboomDTO));
	}

	@Override
	public StamboomDTO storeStamboom(StamboomDTO stamboomDTO) {
		StamboomAssembler assembler = new StamboomAssembler();
		Stamboom stamboom = assembler.getStamBoom(stamboomDTO);
		stamboomRepository.store(stamboom);
		stamboomDTO.setId(stamboom.getId());
		return stamboomDTO;
	}

	@Override
	public List<StamboomDTO> zoekPersonen(String naam) {
		List<Stamboom> personen = stamboomRepository.zoekPersonen(naam);
		List<StamboomDTO> persoonDTOs = new ArrayList<>();
		StamboomAssembler stamboomAssembler = new StamboomAssembler();
		for (Stamboom persoon : personen) {
			persoonDTOs.add(stamboomAssembler.getStamBoomDTO(persoon));
		}
		return persoonDTOs;
	}

	@Override
	public void removeStamboomVanStandplaats(int standplaatsId) {
		stamboomRepository.removeStamboomVanStandplaats(standplaatsId);
	}

}
