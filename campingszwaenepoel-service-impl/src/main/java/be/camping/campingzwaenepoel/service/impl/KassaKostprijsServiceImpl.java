package be.camping.campingzwaenepoel.service.impl;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.KassaKostprijs;
import be.camping.campingzwaenepoel.domain.repository.KassaKostprijsRepository;
import be.camping.campingzwaenepoel.service.KassaKostprijsService;
import be.camping.campingzwaenepoel.service.transfer.KassaKostprijsAssembler;
import be.camping.campingzwaenepoel.service.transfer.KassaKostprijsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("KassaKostprijsService")
@Transactional
public class KassaKostprijsServiceImpl implements KassaKostprijsService {

	@Autowired
	private KassaKostprijsRepository kassaKostprijsRepository;
	
	@Override
	public KassaKostprijsDTO storeKassaKostprij(KassaKostprijsDTO kassaKostprijsDTO) {
		KassaKostprijsAssembler assembler = new KassaKostprijsAssembler();
		KassaKostprijs kassaKostprijs = assembler.getKassaKostprijs(kassaKostprijsDTO);
		kassaKostprijs = kassaKostprijsRepository.store(kassaKostprijs);
		return assembler.getKassaKostprijsDTO(kassaKostprijs);
	}

	@Override
	public List<KassaKostprijsDTO> getKostprijzen() {
		List<KassaKostprijs> kostprijzen = kassaKostprijsRepository.kostprijzen();
		KassaKostprijsAssembler assembler = new KassaKostprijsAssembler();
		List<KassaKostprijsDTO> kostprijsDTOs = new ArrayList<KassaKostprijsDTO>();
		for (KassaKostprijs kassaKostprijs : kostprijzen) {
			kostprijsDTOs.add(assembler.getKassaKostprijsDTO(kassaKostprijs));
		}
		return kostprijsDTOs;
	}
}