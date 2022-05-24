package be.camping.campingzwaenepoel.service.transfer;


import be.camping.campingzwaenepoel.common.enums.GezinsPositieEnum;
import be.camping.campingzwaenepoel.common.enums.HuurderPositieEnum;

public class InschrijvingPersoonDTO {

	private int id;
	private PersoonDTO persoon;
	private Long inschrijvingDatum;
	private GezinsPositieEnum gezinsPositie;
	private HuurderPositieEnum huurdersPositie;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PersoonDTO getPersoon() {
		return persoon;
	}
	public void setPersoon(PersoonDTO persoon) {
		this.persoon = persoon;
	}
	public Long getInschrijvingDatum() {
		return inschrijvingDatum;
	}
	public void setInschrijvingDatum(Long inschrijvingDatum) {
		this.inschrijvingDatum = inschrijvingDatum;
	}
	public GezinsPositieEnum getGezinsPositie() {
		return gezinsPositie;
	}
	public void setGezinsPositie(GezinsPositieEnum gezinsPositie) {
		this.gezinsPositie = gezinsPositie;
	}
	public HuurderPositieEnum getHuurdersPositie() {
		return huurdersPositie;
	}
	public void setHuurdersPositie(HuurderPositieEnum huurdersPositie) {
		this.huurdersPositie = huurdersPositie;
	}

}
