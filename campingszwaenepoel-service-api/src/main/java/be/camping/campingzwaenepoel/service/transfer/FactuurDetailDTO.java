package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;

public class FactuurDetailDTO {

	private int id;
	private Date datum;
	private BetalingEnum aardBetaling;
	private double bedrag;
	private double uitstelkosten;
	private double rappel;
	private double andereKosten;
	private String opmerking;
	private long datumAanmaak;
	private boolean show;
	private boolean colored = false;

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public BetalingEnum getAardBetaling() {
		return aardBetaling;
	}

	public void setAardBetaling(BetalingEnum aardBetaling) {
		this.aardBetaling = aardBetaling;
	}

	public double getBedrag() {
		return bedrag;
	}

	public void setBedrag(double bedrag) {
		this.bedrag = bedrag;
	}

	public double getUitstelkosten() {
		return uitstelkosten;
	}

	public void setUitstelkosten(double uitstelkosten) {
		this.uitstelkosten = uitstelkosten;
	}

	public double getRappel() {
		return rappel;
	}

	public void setRappel(double rappel) {
		this.rappel = rappel;
	}

	public double getAndereKosten() {
		return andereKosten;
	}

	public void setAndereKosten(double andereKosten) {
		this.andereKosten = andereKosten;
	}

	public long getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(long datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isColored() {
		return colored;
	}

	public void setColored(boolean colored) {
		this.colored = colored;
	}

}
