package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.KassaKostprijsDTO;

public interface KassaKostprijsService {

	KassaKostprijsDTO storeKassaKostprij(KassaKostprijsDTO kassaKostprijs);
	
	List<KassaKostprijsDTO> getKostprijzen();
}