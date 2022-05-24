package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.OpslagGegevenDTO;

public interface OpslagGegevenService {

	OpslagGegevenDTO store(OpslagGegevenDTO opslagGegevenDTO);

	List<OpslagGegevenDTO> getOpslagGegevens();
}
