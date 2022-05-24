package be.camping.campingzwaenepoel.service.impl;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.CampingContact;
import be.camping.campingzwaenepoel.domain.repository.CampingContactRepository;
import be.camping.campingzwaenepoel.service.CampingContactService;
import be.camping.campingzwaenepoel.service.transfer.CampingContactAssembler;
import be.camping.campingzwaenepoel.service.transfer.CampingContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("CampingContactService")
@Transactional
public class CampingContactServiceImpl implements CampingContactService {

	@Autowired
	private CampingContactRepository campingContactRepository;
	
	@Override
	public List<CampingContactDTO> findAllCampingContacts() {
		List<CampingContact> contacten = campingContactRepository.getContacten();
		CampingContactAssembler assembler = new CampingContactAssembler();
		List<CampingContactDTO> contactDTOs = new ArrayList<CampingContactDTO>();
		for (CampingContact campingContact : contacten) {
			contactDTOs.add(assembler.getCampingContactDTO(campingContact));
		}
		return contactDTOs;
	}

	@Override
	public void remove(CampingContactDTO campingContactDTO) {
		CampingContactAssembler assembler = new CampingContactAssembler();
		CampingContact campingContact = assembler.getCampingContact(campingContactDTO);
		campingContactRepository.remove(campingContact);
	}

	@Override
	public void store(CampingContactDTO campingContactDTO) {
		CampingContactAssembler assembler = new CampingContactAssembler();
		CampingContact campingContact = assembler.getCampingContact(campingContactDTO);
		campingContactRepository.store(campingContact);
	}
}