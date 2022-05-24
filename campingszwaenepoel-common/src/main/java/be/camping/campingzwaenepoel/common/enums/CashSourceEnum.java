package be.camping.campingzwaenepoel.common.enums;

public enum CashSourceEnum implements TranslatableEnum {

	CASH("CASH", 0),
	PAYCONIC("PAYCONIC", 1);

    private String translationKey;
    private int value;

    private CashSourceEnum(String pTranslationKey, int pValue) {
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
