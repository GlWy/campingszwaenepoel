package be.camping.campingzwaenepoel.common.enums;

public enum ContactgegevenTypeEnum implements TranslatableEnum {
	
	TELEFOON("telefoon", 0),
	FAX("fax", 1),
	EMAIL("email", 2),
	MONDELING("mondeling", 3),
	BRIEF("brief", 4),
	VISUEEL("visueel", 5),
	XXX("XXX", 6),
	YYY("YYY", 7),
	ZZZ("ZZZ", 8);

    private String translationKey;
    private int value;

    private ContactgegevenTypeEnum(String pTranslationKey, int pValue) {
        translationKey = pTranslationKey;
        value = pValue;
    }

    public int value() {
        return value;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    
    public static ContactgegevenTypeEnum getContactgegevenTypeEnum(String contactgegeven) {
    	ContactgegevenTypeEnum contactgegevenType = null;
    	if (ContactgegevenTypeEnum.EMAIL.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.EMAIL;
    	}
    	if (ContactgegevenTypeEnum.FAX.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.FAX;
    	}
    	if (ContactgegevenTypeEnum.TELEFOON.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.TELEFOON;
    	}
    	if (ContactgegevenTypeEnum.MONDELING.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.MONDELING;
    	}
    	if (ContactgegevenTypeEnum.BRIEF.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.BRIEF;
    	}
    	if (ContactgegevenTypeEnum.VISUEEL.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.VISUEEL;
    	}
    	if (ContactgegevenTypeEnum.XXX.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.XXX;
    	}
    	if (ContactgegevenTypeEnum.YYY.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.YYY;
    	}
    	if (ContactgegevenTypeEnum.ZZZ.getTranslationKey().equalsIgnoreCase(contactgegeven)) {
    		contactgegevenType = ContactgegevenTypeEnum.ZZZ;
    	}
    	return contactgegevenType;
    }

}
