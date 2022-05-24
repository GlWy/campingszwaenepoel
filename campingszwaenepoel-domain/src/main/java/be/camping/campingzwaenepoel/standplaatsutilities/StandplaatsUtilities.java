package be.camping.campingzwaenepoel.standplaatsutilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.camping.campingzwaenepoel.domain.model.FactuurDetail;

public class StandplaatsUtilities {

	public static List<FactuurDetail> sortFactuurDetails(Set<FactuurDetail> factuurDetails) {
		List<FactuurDetail> sortedFactuurDetails = new ArrayList<FactuurDetail>();
		for (FactuurDetail tmpFactuurDetail : factuurDetails) {
			boolean sorted = false;
			for (FactuurDetail sortedFactuurDetail : sortedFactuurDetails) {
				if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
					sortedFactuurDetails.add(sortedFactuurDetails.indexOf(sortedFactuurDetail), tmpFactuurDetail);
					sorted = true;
					break;
				}
			}
			if (!sorted) {
				sortedFactuurDetails.add(tmpFactuurDetail);
			}
		}
		return sortedFactuurDetails;
	}

	public static List<FactuurDetail> sortFactuurDetails(List<FactuurDetail> factuurDetails) {
		List<FactuurDetail> sortedFactuurDetails = new ArrayList<FactuurDetail>();
		for (FactuurDetail tmpFactuurDetail : factuurDetails) {
			boolean sorted = false;
			for (FactuurDetail sortedFactuurDetail : sortedFactuurDetails) {
				if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
					sortedFactuurDetails.add(sortedFactuurDetails.indexOf(sortedFactuurDetail), tmpFactuurDetail);
					sorted = true;
					break;
				}
			}
			if (!sorted) {
				sortedFactuurDetails.add(tmpFactuurDetail);
			}
		}
		return sortedFactuurDetails;
	}
	
	public static String createStandplaatsName(String s) {
		return null;
	}

}
