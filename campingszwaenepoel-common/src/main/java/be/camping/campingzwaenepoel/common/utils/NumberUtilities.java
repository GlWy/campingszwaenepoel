package be.camping.campingzwaenepoel.common.utils;


public class NumberUtilities {

	public static boolean isValidNumber(Object o) {
		boolean ret = false;
		if (o != null && !o.equals("")) {
			if (o instanceof java.lang.Integer || o instanceof java.lang.Double) {
				ret = true;
			} else if (o instanceof java.lang.String) {
				try {
					@SuppressWarnings("unused")
					double d = Double.parseDouble((String)o);
					ret = true;
				} catch (ClassCastException cce) {
				} catch (Exception e) {
					
				}
			}
		}
		return ret;
	}

	public static double getNumberFromObject(Object o) {
		double ret = 0;
		if (o instanceof java.lang.Integer) {
			ret = (Integer)o;
		} else if (o instanceof java.lang.Double) {
			ret = (Double)o;
		} else if (o instanceof java.lang.String) {
			ret = Double.parseDouble((String)o);
		}
		return ret;
	}
	
	public static String getStringForDouble(double d) {
		String s = "";
		try {
			s = Double.toString(d);
		} catch (Exception e) {
		}
		return s;
	}
	
	public static String getStringForInt(int i) {
		String s = "";
		try {
			s = Integer.toString(i);
		} catch (Exception e) {
		}
		return s;
	}
}
