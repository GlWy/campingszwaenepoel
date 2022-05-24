package be.camping.campingzwaenepoel.common.enums;

public enum BetalingEnum implements TranslatableEnum{

	BETAALD("BT", 0),
	NOGTEBETALEN("NTB", 1),
	UITSTEL("UIT", 2);

    private String translationKey;
    private int value;

    private BetalingEnum(String pTranslationKey, int pValue) {
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
