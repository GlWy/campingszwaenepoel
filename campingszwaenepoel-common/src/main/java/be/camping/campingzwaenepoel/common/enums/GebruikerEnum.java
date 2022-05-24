package be.camping.campingzwaenepoel.common.enums;

import org.apache.commons.lang.StringUtils;

public enum GebruikerEnum implements TranslatableEnum{

	NIEMAND("XXX", 0),
	IVAN("Ivan", 1),
	PASCAL("Pascal", 2),
	PATRICK("Patrick", 3),
	KRISTOF("Kristof", 4),
	FRANK("Frank", 5),
	JEREMY("Jeremy", 6),
	IGNAS("Ignas", 7);

    private String translationKey;
    private int value;

    private GebruikerEnum(String pTranslationKey, int pValue) {
        translationKey = pTranslationKey;
        value = pValue;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    
    public static GebruikerEnum getGebruikerEnum(String naam) {
    	GebruikerEnum s = null;
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.NIEMAND.getTranslationKey())) {
    			return GebruikerEnum.NIEMAND;
    		}
    	}
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.IVAN.getTranslationKey())) {
    			return GebruikerEnum.IVAN;
    		}
    	}
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.PASCAL.getTranslationKey())) {
    			return GebruikerEnum.PASCAL;
    		}
    	}
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.PATRICK.getTranslationKey())) {
    			return GebruikerEnum.PATRICK;
    		}
    	}
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.KRISTOF.getTranslationKey())) {
    			return GebruikerEnum.KRISTOF;
    		}
    	}
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.FRANK.getTranslationKey())) {
    			return GebruikerEnum.FRANK;
    		}
    	}
    	if (!StringUtils.isEmpty(naam)) {
    		if (naam.equals(GebruikerEnum.IGNAS.getTranslationKey())) {
    			return GebruikerEnum.IGNAS;
    		}
    	}
    	return s;
    }

}
