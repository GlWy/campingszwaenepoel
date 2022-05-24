package be.camping.campingzwaenepoel.common.enums;

public enum LandEnum {
	
	BE("land.Belgi\u00EB", 0);

    private String translationKey;
    private int value;

    private LandEnum(String pTranslationKey, int pValue) {
        translationKey = pTranslationKey;
        value = pValue;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    
    public static LandEnum getCountry(String country) {
    	LandEnum landEnum = null;
    	if ("be".equalsIgnoreCase(country)) landEnum = LandEnum.BE;
    	return landEnum;
    }

}
