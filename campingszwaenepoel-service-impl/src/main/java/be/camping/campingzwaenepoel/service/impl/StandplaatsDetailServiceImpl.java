package be.camping.campingzwaenepoel.service.impl;

import be.camping.campingzwaenepoel.domain.model.StandplaatsDetail;
import be.camping.campingzwaenepoel.domain.repository.StandplaatsDetailRepository;
import be.camping.campingzwaenepoel.service.StandplaatsDetailService;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDetailAssembler;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("StandplaatsDetailService")
@Transactional
public class StandplaatsDetailServiceImpl implements StandplaatsDetailService {

	@Autowired
	StandplaatsDetailRepository standplaatsDetailRepository;

	@Override
	public void removeStandplaatsDetail(StandplaatsDetailDTO standplaatsDetailDTO) {
		StandplaatsDetailAssembler standplaatsDetailAssembler = new StandplaatsDetailAssembler();
		StandplaatsDetail standplaatsDetail = standplaatsDetailAssembler.getStandplaatsDetail(standplaatsDetailDTO);
		standplaatsDetailRepository.remove(standplaatsDetail);
	}

	@Override
	public StandplaatsDetailDTO getStandplaatsDetail(int id) {
		StandplaatsDetail standplaatsDetail = standplaatsDetailRepository.findById(id);
		StandplaatsDetailAssembler standplaatsDetailAssembler = new StandplaatsDetailAssembler();
		StandplaatsDetailDTO standplaatsDetailDTO = standplaatsDetailAssembler.getStandplaatsDetailDTO(standplaatsDetail);
		return standplaatsDetailDTO;
	}

	@Override
	public List<StandplaatsDetailDTO> getStandplaatsDetails() {
		List<StandplaatsDetail> standplaatsDetails = standplaatsDetailRepository.getStandplaatsDetails();
		StandplaatsDetailAssembler standplaatsDetailAssembler = new StandplaatsDetailAssembler();
		List<StandplaatsDetailDTO> standplaatsDetailDTOs = new ArrayList<>();
		for (StandplaatsDetail standplaatsDetail : standplaatsDetails) {
			standplaatsDetailDTOs.add(standplaatsDetailAssembler.getStandplaatsDetailDTO(standplaatsDetail));
		}
		return standplaatsDetailDTOs;
	}

	@Override
	public void storeStandplaatsDetail(StandplaatsDetailDTO standplaatsDetailDTO) {
		StandplaatsDetailAssembler standplaatsDetailAssembler = new StandplaatsDetailAssembler();
		StandplaatsDetail standplaatsDetail = standplaatsDetailAssembler.getStandplaatsDetail(standplaatsDetailDTO);
		standplaatsDetailRepository.store(standplaatsDetail);
		standplaatsDetailDTO.setId(standplaatsDetail.getId());
	}
}