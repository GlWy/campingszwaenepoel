package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Wagen;

public class WagenAssembler {

	public Wagen getWagen(WagenDTO wagenDTO) {
		Wagen wagen = new Wagen();
		wagen.setId(wagenDTO.getId());
		wagen.setMerk(wagenDTO.getMerk());
		wagen.setNummerplaat(wagenDTO.getNummerplaat());
		wagen.setOpmerking(wagenDTO.getOpmerking());
		wagen.setSticker(wagenDTO.getSticker());
		return wagen;
	}

	public WagenDTO getWagenDTO(Wagen wagen) {
		WagenDTO wagenDTO = new WagenDTO();
		wagenDTO.setId(wagen.getId());
		wagenDTO.setMerk(wagen.getMerk());
		wagenDTO.setNummerplaat(wagen.getNummerplaat());
		wagenDTO.setOpmerking(wagen.getOpmerking());
		wagenDTO.setSticker(wagen.getSticker());
		return wagenDTO;
	}
}
