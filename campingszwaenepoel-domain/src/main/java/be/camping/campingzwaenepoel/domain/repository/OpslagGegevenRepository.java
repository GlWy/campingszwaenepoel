package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.OpslagGegeven;

public interface OpslagGegevenRepository {

	public List<OpslagGegeven> getOpslagGegevens();

	public OpslagGegeven store(OpslagGegeven receptionist);
}
