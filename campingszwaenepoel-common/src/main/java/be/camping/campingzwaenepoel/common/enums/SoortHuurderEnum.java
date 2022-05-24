package be.camping.campingzwaenepoel.common.enums;

public enum SoortHuurderEnum {

	VAST("Vast", 0),
	LOS("Los", 1),
	ONBEPAALD("Onbepaald", 2);

    private String translationKey;
    private int value;

    private SoortHuurderEnum (String pTranslationKey, int pValue) {
        translationKey = pTranslationKey;
        value = pValue;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }

}
