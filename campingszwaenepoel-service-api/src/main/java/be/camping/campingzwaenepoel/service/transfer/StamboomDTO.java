package be.camping.campingzwaenepoel.service.transfer;

public class StamboomDTO {

	private int id;
	private String naam;
	private String opmerking;
	private int generatie;
	private int rang;
	private int standplaatsId;

	// getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getOpmerking() {
		return opmerking;
	}
	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}
	public int getStandplaatsId() {
		return standplaatsId;
	}
	public void setStandplaatsId(int standplaatsId) {
		this.standplaatsId = standplaatsId;
	}
	public int getGeneratie() {
		return generatie;
	}
	public void setGeneratie(int generatie) {
		this.generatie = generatie;
	}
	public int getRang() {
		return rang;
	}
	public void setRang(int rang) {
		this.rang = rang;
	}

}
