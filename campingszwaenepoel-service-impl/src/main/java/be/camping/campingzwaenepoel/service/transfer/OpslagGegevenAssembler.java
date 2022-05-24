package be.camping.campingzwaenepoel.service.transfer;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.OpslagGegeven;

public class OpslagGegevenAssembler {

	public OpslagGegeven getOpslagGegeven(OpslagGegevenDTO opslagGegevenDTO) {
		return new OpslagGegeven(opslagGegevenDTO.getNaam(), opslagGegevenDTO.getTabblad(), opslagGegevenDTO.getDatum());
	}

	public OpslagGegevenDTO getOpslagGegevenDTO(OpslagGegeven opslagGegeven) {
		return new OpslagGegevenDTO(opslagGegeven.getNaam(), opslagGegeven.getTabblad(), opslagGegeven.getDatum());
	}

	public List<OpslagGegevenDTO> getOpslagGegevens(List<OpslagGegeven> opslagGegevens) {
		List<OpslagGegevenDTO> opslagGegevenDTOs = new ArrayList<OpslagGegevenDTO>();
		for (OpslagGegeven opslagGegeven : opslagGegevens) {
			opslagGegevenDTOs.add(getOpslagGegevenDTO(opslagGegeven));
		}
		return opslagGegevenDTOs;
	}
}
