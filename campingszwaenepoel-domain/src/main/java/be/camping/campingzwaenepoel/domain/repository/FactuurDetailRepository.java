package be.camping.campingzwaenepoel.domain.repository;

import be.camping.campingzwaenepoel.domain.model.FactuurDetail;

public interface FactuurDetailRepository {

	public void remove(FactuurDetail factuurDetail);

	public void removeFactuurDetails(int standplaatsId);

}
