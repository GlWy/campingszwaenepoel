package be.camping.campingzwaenepoel.common.enums;

public enum TaalEnum {
	
	NL("Nederlands", 0, "CAMPING"),
	FR("Frans", 1, "CAMPING"),
	DU("Duits", 2, "CAMPING");

    private String translationKey;
    private int value;
    @SuppressWarnings("unused")
	private String bedrijf;

    private TaalEnum(String pTranslationKey, int pValue, String pBedrijf) {
        translationKey = pTranslationKey;
        value = pValue;
        bedrijf = pBedrijf;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }

}
