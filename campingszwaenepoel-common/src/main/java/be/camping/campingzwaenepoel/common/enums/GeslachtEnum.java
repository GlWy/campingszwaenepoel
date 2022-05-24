package be.camping.campingzwaenepoel.common.enums;

public enum GeslachtEnum implements TranslatableEnum {

	M("man", 0),
	V("vrouw", 1),
	O("onbepaald", 2);

    private String translationKey;
    private int value;

    private GeslachtEnum(String pTranslationKey, int pValue) {
        translationKey = pTranslationKey;
        value = pValue;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    
    public static GeslachtEnum getGeslachtEnumForTranslationKey(String translationKey) {
    	GeslachtEnum geslachtEnum = null;
    	if ("M".equals(translationKey)) {
    		geslachtEnum = GeslachtEnum.M;
    	} else if ("V".equals(translationKey)) {
    		geslachtEnum = GeslachtEnum.V;
    	} else {
    		geslachtEnum = GeslachtEnum.O;
    	}
    	return geslachtEnum;
    }
}
