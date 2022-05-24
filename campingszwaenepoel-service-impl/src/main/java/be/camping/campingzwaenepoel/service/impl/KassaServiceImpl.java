package be.camping.campingzwaenepoel.service.impl;

import be.camping.campingzwaenepoel.common.enums.CashSourceEnum;
import be.camping.campingzwaenepoel.domain.model.Kasbon;
import be.camping.campingzwaenepoel.domain.model.KassaKostprijs;
import be.camping.campingzwaenepoel.domain.repository.KasbonRepository;
import be.camping.campingzwaenepoel.domain.repository.KassaKostprijsRepository;
import be.camping.campingzwaenepoel.service.KassaService;
import be.camping.campingzwaenepoel.service.transfer.KassaAfrekeningDTO;
import be.camping.campingzwaenepoel.service.transfer.KassaArtikelAfrekeningDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("KassaService")
@Transactional
public class KassaServiceImpl implements KassaService {

	@Autowired
	private KassaKostprijsRepository kassaKostprijsRepository;

	@Autowired
	private KasbonRepository kasbonRepository;

	/**
	 * Auto 100; Auto 150; Waarborg; Jeton; ZakGeel; ZakBlauw; Car; Tent; Volwassen; Kinderen; Telefoon;
	 */
	@Override
	public List<KassaAfrekeningDTO> berekenDagInkomsted(Date date) {
		List<KassaKostprijs> kostprijzen = kassaKostprijsRepository.kostprijzen();
		List<Kasbon> kasbons = kasbonRepository.getKasbons();
		KassaAfrekeningDTO cashKassaAfrekeningDTO = populateKassaAfrekeningDTO(kostprijzen, kasbons, date, CashSourceEnum.CASH);
		KassaAfrekeningDTO payconicKassaAfrekeningDTO = populateKassaAfrekeningDTO(kostprijzen, kasbons, date, CashSourceEnum.PAYCONIC);
		List<KassaAfrekeningDTO> kassaAfrekeningDTOs = new ArrayList<KassaAfrekeningDTO>();
		kassaAfrekeningDTOs.add(cashKassaAfrekeningDTO);
		kassaAfrekeningDTOs.add(payconicKassaAfrekeningDTO);
		return kassaAfrekeningDTOs;
	}
	
	private KassaAfrekeningDTO populateKassaAfrekeningDTO(List<KassaKostprijs> kostprijzen, List<Kasbon> kasbons, Date date, CashSourceEnum cashSource) {
		KassaArtikelAfrekeningDTO auto100KassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO auto150KassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		// KassaArtikelAfrekeningDTO waarborgKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO jetonKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO zakGeelKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO zakBlauwKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO carKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO tentKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO volwassenenKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO kinderenKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();
		KassaArtikelAfrekeningDTO telefoonKassaArtikelAfrekeningDTO = new KassaArtikelAfrekeningDTO();

		for (Kasbon kasbon : kasbons) {
			if (!cashSource.equals(kasbon.getCashSource())) continue;
			auto100KassaArtikelAfrekeningDTO.setAantal(auto100KassaArtikelAfrekeningDTO.getAantal()
					+ kasbon.getAuto100() * kasbon.getAantalNachten());
			auto150KassaArtikelAfrekeningDTO.setAantal(auto150KassaArtikelAfrekeningDTO.getAantal()
					+ kasbon.getAuto150() * kasbon.getAantalNachten());
			// waarborgKassaArtikelAfrekeningDTO.setAantal(waarborgKassaArtikelAfrekeningDTO.getAantal()
			// + kasbon.getWaarborg());
			jetonKassaArtikelAfrekeningDTO.setAantal(jetonKassaArtikelAfrekeningDTO.getAantal() + kasbon.getJeton());
			zakGeelKassaArtikelAfrekeningDTO.setAantal(zakGeelKassaArtikelAfrekeningDTO.getAantal()
					+ kasbon.getZakGeel());
			zakBlauwKassaArtikelAfrekeningDTO.setAantal(zakBlauwKassaArtikelAfrekeningDTO.getAantal()
					+ kasbon.getZakBlauw());
			carKassaArtikelAfrekeningDTO.setAantal(carKassaArtikelAfrekeningDTO.getAantal() + kasbon.getCar()
					* kasbon.getAantalNachten());
			tentKassaArtikelAfrekeningDTO.setAantal(tentKassaArtikelAfrekeningDTO.getAantal() + kasbon.getTent()
					* kasbon.getAantalNachten());
			volwassenenKassaArtikelAfrekeningDTO.setAantal(volwassenenKassaArtikelAfrekeningDTO.getAantal()
					+ kasbon.getVolwassenen() * kasbon.getAantalNachten());
			kinderenKassaArtikelAfrekeningDTO.setAantal(kinderenKassaArtikelAfrekeningDTO.getAantal()
					+ kasbon.getKinderen() * kasbon.getAantalNachten());
			telefoonKassaArtikelAfrekeningDTO.setEenheidsprijs(telefoonKassaArtikelAfrekeningDTO.getEenheidsprijs()
					+ kasbon.getTelefoon());
		}
		telefoonKassaArtikelAfrekeningDTO.setAantal(1);

		KassaAfrekeningDTO kassaAfrekeningDTO = new KassaAfrekeningDTO();
		kassaAfrekeningDTO.setDatum(date);
		kassaAfrekeningDTO.setCashSource(cashSource);

		for (KassaKostprijs kassaKostprijs : kostprijzen) {
			// if (kassaKostprijs.getNummer() == 3) // waarborg
			// continue;
			switch (kassaKostprijs.getNummer()) {
			case 1:
				auto100KassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				auto100KassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				auto100KassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(auto100KassaArtikelAfrekeningDTO);
				break;
			case 2:
				auto150KassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				auto150KassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				auto150KassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(auto150KassaArtikelAfrekeningDTO);
				break;
			// case 3:
			// waarborgKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
			// waarborgKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
			// waarborgKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
			// kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(waarborgKassaArtikelAfrekeningDTO);
			// break;
			case 4:
				jetonKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				jetonKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				jetonKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(jetonKassaArtikelAfrekeningDTO);
				break;
			case 5:
				zakGeelKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				zakGeelKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				zakGeelKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(zakGeelKassaArtikelAfrekeningDTO);
				break;
			case 6:
				zakBlauwKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				zakBlauwKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				zakBlauwKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(zakBlauwKassaArtikelAfrekeningDTO);
				break;
			case 7:
				carKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				carKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				carKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(carKassaArtikelAfrekeningDTO);
				break;
			case 8:
				tentKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				tentKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				tentKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(tentKassaArtikelAfrekeningDTO);
				break;
			case 9:
				volwassenenKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				volwassenenKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				volwassenenKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(volwassenenKassaArtikelAfrekeningDTO);
				break;
			case 10:
				kinderenKassaArtikelAfrekeningDTO.setNummer(kassaKostprijs.getNummer());
				kinderenKassaArtikelAfrekeningDTO.setNaam(kassaKostprijs.getNaam());
				kinderenKassaArtikelAfrekeningDTO.setEenheidsprijs(kassaKostprijs.getWaarde());
				kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(kinderenKassaArtikelAfrekeningDTO);
				break;
			default:
				break;
			}
		}

		telefoonKassaArtikelAfrekeningDTO.setNummer(11);
		telefoonKassaArtikelAfrekeningDTO.setNaam("Telekosten");
		telefoonKassaArtikelAfrekeningDTO.setShowAllData(false);
		kassaAfrekeningDTO.addKassaArtilelAfrekeningDTO(telefoonKassaArtikelAfrekeningDTO);
		return kassaAfrekeningDTO;
	}
}