package be.camping.campingzwaenepoel.common.enums;

public enum HuurderPositieEnum {

	HOOFD("Eigenaar Hoofd", 0),
	ECHTGENOTE("Eigenaar Echtgenote", 1),
	ZOON("Zoon", 2),
	DOCHTER("Dochter", 3),
	SCHOONZOON("Schoonzoon", 4),
	SCHOONDOCHTER("Schoondochter", 5),
	OUDER("Ouders", 6),
	CHAUFFEUR("chauffeur", 7),
	HUURDER("Huurder", 8),
	KIND("Kind", 9),
	ANDERE("andere", 10);
	
    private String translationKey;
    private int value;

    private HuurderPositieEnum (String pTranslationKey, int pValue) {
        translationKey = pTranslationKey;
        value = pValue;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    
    public static HuurderPositieEnum getHuurdersPositieEnumVoorOudProgramma(int huurdersPositie) {
    	HuurderPositieEnum hpEnum = HuurderPositieEnum.ANDERE;
    	if (huurdersPositie == 1) {
    		hpEnum = HuurderPositieEnum.HOOFD;
    	}
    	if (huurdersPositie == 2) {
    		hpEnum = HuurderPositieEnum.ECHTGENOTE;
    	}
    	if (huurdersPositie == 3) {
    		hpEnum = HuurderPositieEnum.ZOON;
    	}
    	if (huurdersPositie == 4) {
    		hpEnum = HuurderPositieEnum.DOCHTER;
    	}
    	if (huurdersPositie == 5) {
    		hpEnum = HuurderPositieEnum.SCHOONZOON;
    	}
    	if (huurdersPositie == 6) {
    		hpEnum = HuurderPositieEnum.SCHOONDOCHTER;
    	}
    	if (huurdersPositie == 8) {
    		hpEnum = HuurderPositieEnum.OUDER;
    	}
    	if (huurdersPositie == 10) {
    		hpEnum = HuurderPositieEnum.HUURDER;
    	}
    	if (huurdersPositie == 12) {
    		hpEnum = HuurderPositieEnum.KIND;
    	}
    	if (huurdersPositie == 13) {
    		hpEnum = HuurderPositieEnum.KIND;
    	}
    	if (huurdersPositie == 18) {
    		hpEnum = HuurderPositieEnum.CHAUFFEUR;
    	}
    	if (huurdersPositie == 19) {
    		hpEnum = HuurderPositieEnum.ANDERE;
    	}
    	return hpEnum;
    }
}
