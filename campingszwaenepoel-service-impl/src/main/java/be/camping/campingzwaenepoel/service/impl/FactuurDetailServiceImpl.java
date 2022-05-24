package be.camping.campingzwaenepoel.service.impl;

import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.domain.repository.FactuurDetailRepository;
import be.camping.campingzwaenepoel.service.FactuurDetailService;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailAssembler;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("FactuurDetailService")
@Transactional
public class FactuurDetailServiceImpl implements FactuurDetailService {

	@Autowired
	private FactuurDetailRepository factuurDetailRepository;

	@Override
	public void removeFactuurDetail(FactuurDetailDTO factuurDetail) {
		FactuurDetailAssembler assembler = new FactuurDetailAssembler();
		factuurDetailRepository.remove(assembler.getFactuurDetail(factuurDetail));
	}

	@Override
	public void removeFactuurDetails(int standplaatsId) {
		factuurDetailRepository.removeFactuurDetails(standplaatsId);
	}

	@Override
	public boolean isPrijsAanpassingBusy() {
		return FotoConstant.isPrijsAanpassingBusy();
	}
}