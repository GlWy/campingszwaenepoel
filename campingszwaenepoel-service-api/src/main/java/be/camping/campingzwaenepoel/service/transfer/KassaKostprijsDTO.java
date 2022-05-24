package be.camping.campingzwaenepoel.service.transfer;


public class KassaKostprijsDTO {

	private Integer id;
	private Integer nummer;
	private String naam;
	private Double waarde;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNummer() {
		return this.nummer;
	}

	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Double getWaarde() {
		return this.waarde;
	}

	public void setWaarde(Double waarde) {
		this.waarde = waarde;
	}

}
