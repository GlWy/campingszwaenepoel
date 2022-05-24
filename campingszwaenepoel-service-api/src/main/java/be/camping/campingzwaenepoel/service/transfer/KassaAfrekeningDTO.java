package be.camping.campingzwaenepoel.service.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.camping.campingzwaenepoel.common.enums.CashSourceEnum;

public class KassaAfrekeningDTO {

	private Date datum;
	private CashSourceEnum cashSource;
	private List<KassaArtikelAfrekeningDTO> kassaArtikelAfrekeningDTOs = new ArrayList<KassaArtikelAfrekeningDTO>();

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public CashSourceEnum getCashSource() {
		return cashSource;
	}
	
	public void setCashSource(CashSourceEnum cashSource) {
		this.cashSource = cashSource;
	}

	public Double getTotaal() {
		Double totaal = 0d;
		for (KassaArtikelAfrekeningDTO kassaArtikelAfrekeningDTO : kassaArtikelAfrekeningDTOs) {
			totaal += kassaArtikelAfrekeningDTO.getAantal() * kassaArtikelAfrekeningDTO.getEenheidsprijs();
		}
		return totaal;
	}

	public List<KassaArtikelAfrekeningDTO> getKassaArtikelAfrekeningDTOs() {
		return kassaArtikelAfrekeningDTOs;
	}

	public void setKassaArtikelAfrekeningDTOs(List<KassaArtikelAfrekeningDTO> kassaArtikelAfrekeningDTOs) {
		this.kassaArtikelAfrekeningDTOs = kassaArtikelAfrekeningDTOs;
	}

	public void addKassaArtilelAfrekeningDTO(KassaArtikelAfrekeningDTO kassaArtikelAfrekeningDTO) {
		this.kassaArtikelAfrekeningDTOs.add(kassaArtikelAfrekeningDTO);
	}
}
