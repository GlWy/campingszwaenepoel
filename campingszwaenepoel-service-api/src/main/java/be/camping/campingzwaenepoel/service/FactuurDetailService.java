package be.camping.campingzwaenepoel.service;

import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;

public interface FactuurDetailService {

	public void removeFactuurDetail(FactuurDetailDTO factuurDetail);
	
	public void removeFactuurDetails(int standplaatsId);

	public boolean isPrijsAanpassingBusy();
}
