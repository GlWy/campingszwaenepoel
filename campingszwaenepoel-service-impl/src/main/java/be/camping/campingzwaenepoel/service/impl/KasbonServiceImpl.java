package be.camping.campingzwaenepoel.service.impl;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.Kasbon;
import be.camping.campingzwaenepoel.domain.repository.KasbonRepository;
import be.camping.campingzwaenepoel.service.KasbonService;
import be.camping.campingzwaenepoel.service.transfer.KasbonAssembler;
import be.camping.campingzwaenepoel.service.transfer.KasbonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("KasbonService")
@Transactional
public class KasbonServiceImpl implements KasbonService {

	@Autowired
	private KasbonRepository kasbonRepository;

	@Override
	public KasbonDTO getKasbon(int kasbonId) {
		Kasbon kasbon = kasbonRepository.findById(kasbonId);
		KasbonAssembler assembler = new KasbonAssembler();
		return assembler.getKasboDTO(kasbon);
	}

	@Override
	public void remove(KasbonDTO kasbon) {
		KasbonAssembler assembler = new KasbonAssembler();
		kasbonRepository.remove(assembler.getKasbon(kasbon));
	}

	@Override
	public void removeAll() {
		kasbonRepository.removeAll();
	}

	@Override
	public KasbonDTO store(KasbonDTO kasbonDTO) {
		KasbonAssembler assembler = new KasbonAssembler();
		Kasbon kasbon = kasbonRepository.store(assembler.getKasbon(kasbonDTO));
		return assembler.getKasboDTO(kasbon);
	}

	@Override
	public List<KasbonDTO> getKasbons() {
		List<Kasbon> kasbons = kasbonRepository.getKasbons();
		List<KasbonDTO> kasbonDTOs = new ArrayList<>();
		KasbonAssembler assembler = new KasbonAssembler();
		for (Kasbon kasbon : kasbons) {
			kasbonDTOs.add(assembler.getKasboDTO(kasbon));
		}
		return kasbonDTOs;
	}

	@Override
	public KasbonDTO getKasbonByKasbonNummer(int kasbonnummer) {
		Kasbon kasbon = kasbonRepository.findByKasbonNummer(kasbonnummer);
		KasbonAssembler assembler = new KasbonAssembler();
		KasbonDTO kasbonDTO = null;
		if (kasbon != null) {
			kasbonDTO = assembler.getKasboDTO(kasbon);
		}
		return kasbonDTO;
	}

	@Override
	public List<KasbonDTO> getKasbons(String computername) {
		List<Kasbon> kasbons = kasbonRepository.getKasbons(computername);
		List<KasbonDTO> kasbonDTOs = new ArrayList<>();
		KasbonAssembler assembler = new KasbonAssembler();
		for (Kasbon kasbon : kasbons) {
			kasbonDTOs.add(assembler.getKasboDTO(kasbon));
		}
		return kasbonDTOs;
	}
}