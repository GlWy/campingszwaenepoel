package be.camping.campingzwaenepoel.common.enums;

public enum TabBlad {

	KASSA("Kassa"), OPMERKINGEN("Opmerkingen"), BETALER("Betaler"), GROND("Grond"), PERSOON("Persoon"), BETALING(
			"Betaling"), FOTO("Photo"), GESCHIEDENIS("Geschiedenis");

	private String translationKey;

	private TabBlad(String pTranslationKey) {
		translationKey = pTranslationKey;
	}

	public String getTranslationKey() {
		return translationKey;
	}
}
