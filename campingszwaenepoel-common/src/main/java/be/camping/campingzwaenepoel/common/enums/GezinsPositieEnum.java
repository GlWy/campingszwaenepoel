package be.camping.campingzwaenepoel.common.enums;

public enum GezinsPositieEnum {

	HOOFD("Hoofd", 0),
	ECHTGENOTE("Echtgenote", 1),
	KIND("Kind", 2),
	ANDERE("Andere", 3);
	
    private String translationKey;
    private int value;

    private GezinsPositieEnum (String pTranslationKey, int pValue) {
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
