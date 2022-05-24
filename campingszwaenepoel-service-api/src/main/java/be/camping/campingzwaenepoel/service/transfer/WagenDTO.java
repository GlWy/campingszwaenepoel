package be.camping.campingzwaenepoel.service.transfer;


public class WagenDTO {

	private int id;
	private String nummerplaat;
	private String merk;
	private String opmerking;
	private String sticker;

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNummerplaat() {
		return this.nummerplaat;
	}

	public void setNummerplaat(String nummerplaat) {
		this.nummerplaat = nummerplaat;
	}

	public String getMerk() {
		return this.merk;
	}

	public void setMerk(String merk) {
		this.merk = merk;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public String getSticker() {
		return this.sticker;
	}

	public void setSticker(String sticker) {
		this.sticker = sticker;
	}

}
