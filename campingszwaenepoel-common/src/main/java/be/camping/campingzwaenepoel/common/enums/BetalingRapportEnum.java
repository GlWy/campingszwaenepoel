package be.camping.campingzwaenepoel.common.enums;

public enum BetalingRapportEnum implements TranslatableEnum {

	UITSTEL("uitstel"),
	LANGEVERSIE("lange versie"),
	KORTEVERSIE("korte versie"),
	ANDERE("andere"),
	TABEL("tabel");

    private String translationKey;
    
    private BetalingRapportEnum(String pTranslationKey) {
        translationKey = pTranslationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }

}
