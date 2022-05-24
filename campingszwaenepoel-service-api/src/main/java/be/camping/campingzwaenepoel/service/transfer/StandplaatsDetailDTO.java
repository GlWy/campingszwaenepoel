package be.camping.campingzwaenepoel.service.transfer;

public class StandplaatsDetailDTO {

	// fields
	private Integer id;
	private Short nummer;
	private String naam;
	private Boolean datumveld;
	private Boolean verplicht;
	private Boolean printAlles;
	private Boolean namenTonen;
	
	// getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Short getNummer() {
		return nummer;
	}
	public void setNummer(Short nummer) {
		this.nummer = nummer;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public Boolean isDatumveld() {
		return datumveld;
	}
	public void setDatumveld(Boolean datumveld) {
		this.datumveld = datumveld;
	}
	public Boolean isVerplicht() {
		return verplicht;
	}
	public void setVerplicht(Boolean verplicht) {
		this.verplicht = verplicht;
	}
	public Boolean isPrintAlles() {
		return printAlles;
	}
	public void setPrintAlles(Boolean printAlles) {
		this.printAlles = printAlles;
	}
	public Boolean isNamenTonen() {
		return namenTonen;
	}
	public void setNamenTonen(Boolean namenTonen) {
		this.namenTonen = namenTonen;
	}
}
