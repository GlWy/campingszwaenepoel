package be.camping.campingzwaenepoel.service.impl;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.OpslagGegeven;
import be.camping.campingzwaenepoel.domain.repository.OpslagGegevenRepository;
import be.camping.campingzwaenepoel.service.OpslagGegevenService;
import be.camping.campingzwaenepoel.service.transfer.OpslagGegevenAssembler;
import be.camping.campingzwaenepoel.service.transfer.OpslagGegevenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("OpslagGegevenService")
@Transactional
public class OpslagGegevenServiceImpl implements OpslagGegevenService {

    @Autowired
	private OpslagGegevenRepository opslagGegevenRepository;

	@Override
	public OpslagGegevenDTO store(OpslagGegevenDTO opslagGegevenDTO) {
		OpslagGegevenAssembler assembler = new OpslagGegevenAssembler();
		OpslagGegeven opslagGegeven = opslagGegevenRepository.store(assembler.getOpslagGegeven(opslagGegevenDTO));
		return assembler.getOpslagGegevenDTO(opslagGegeven);
	}

	@Override
	public List<OpslagGegevenDTO> getOpslagGegevens() {
		OpslagGegevenAssembler assembler = new OpslagGegevenAssembler();
		List<OpslagGegeven> opslagGegevens = opslagGegevenRepository.getOpslagGegevens();
		return assembler.getOpslagGegevens(opslagGegevens);
	}
}