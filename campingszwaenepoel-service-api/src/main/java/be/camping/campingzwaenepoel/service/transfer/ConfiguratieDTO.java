package be.camping.campingzwaenepoel.service.transfer;


public class ConfiguratieDTO {

	private Integer id;
	private String naam;
	private String waarde;

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getWaarde() {
		return this.waarde;
	}

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}

}
