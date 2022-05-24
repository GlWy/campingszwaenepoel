package be.camping.campingzwaenepoel.service.transfer;

public class KassaArtikelAfrekeningDTO {

	private Integer nummer;
	private String naam;
	private Integer aantal = 0;
	private Double eenheidsprijs = 0d;
	private boolean showAllData = true;

	public Integer getNummer() {
		return nummer;
	}

	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Integer getAantal() {
		return aantal;
	}

	public void setAantal(Integer aantal) {
		this.aantal = aantal;
	}

	public boolean isShowAllData() {
		return showAllData;
	}

	public void setShowAllData(boolean showAllData) {
		this.showAllData = showAllData;
	}

	public Double getEenheidsprijs() {
		return eenheidsprijs;
	}

	public void setEenheidsprijs(Double eenheidsprijs) {
		this.eenheidsprijs = eenheidsprijs;
	}

	public Double getTotaal() {
		return aantal * eenheidsprijs;
	}
}
