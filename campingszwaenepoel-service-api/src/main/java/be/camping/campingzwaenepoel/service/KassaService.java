package be.camping.campingzwaenepoel.service;

import java.util.Date;
import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.KassaAfrekeningDTO;

public interface KassaService {

	List<KassaAfrekeningDTO> berekenDagInkomsted(Date date);
}
