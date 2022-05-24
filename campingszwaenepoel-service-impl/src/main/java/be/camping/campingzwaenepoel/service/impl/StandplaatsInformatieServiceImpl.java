package be.camping.campingzwaenepoel.service.impl;

import be.camping.campingzwaenepoel.domain.repository.StandplaatsInformatieRepository;
import be.camping.campingzwaenepoel.service.StandplaatsInformatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("standplaatsInformatieService")
@Transactional
public class StandplaatsInformatieServiceImpl implements StandplaatsInformatieService {

	@Autowired
	private StandplaatsInformatieRepository standplaatsInformatieRepository;
	
	@Override
	public void removeStandplaatsInformatieByNumber(int number) {
		standplaatsInformatieRepository.removeByNumber(number);
	}
}