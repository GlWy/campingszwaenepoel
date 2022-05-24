package be.camping.campingzwaenepoel.service.cache;

import java.util.List;

public class CacheManager {

	private static List<String> standplaatsen;

	public static List<String> getStandplaatsen() {
		return standplaatsen;
	}

	public static void setStandplaatsen(List<String> standplaatsen) {
		CacheManager.standplaatsen = standplaatsen;
	}
}
