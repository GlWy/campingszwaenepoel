package be.camping.campingzwaenepoel.service.transfer;


public class FactuurDTO {

	private int id;
	private int jaar;
	private boolean betaald = false;
//    private Set<FactuurDetailDTO> factuurDetails = new HashSet<FactuurDetailDTO>();

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJaar() {
		return jaar;
	}

	public void setJaar(int jaar) {
		this.jaar = jaar;
	}

	public boolean isBetaald() {
		return betaald;
	}

	public void setBetaald(boolean betaald) {
		this.betaald = betaald;
	}

/*	public Set<FactuurDetailDTO> getFactuurDetails() {
		return factuurDetails;
	}

	public void setFactuurDetails(Set<FactuurDetailDTO> factuurDetails) {
		this.factuurDetails = factuurDetails;
	}
	
	public void addFactuurDetail (FactuurDetailDTO factuurDetail) {
		getFactuurDetails().add(factuurDetail);
	}*/


}
