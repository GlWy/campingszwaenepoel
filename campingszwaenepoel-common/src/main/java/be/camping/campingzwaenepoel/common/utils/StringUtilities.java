package be.camping.campingzwaenepoel.common.utils;

import org.apache.commons.lang.StringUtils;

public class StringUtilities {

	public static boolean equals(String s1, String s2) {
		boolean ret = true;
		String s3 = (StringUtils.isEmpty(s1))?"":s1;
		String s4 = (StringUtils.isEmpty(s2))?"":s2;
		if (!s3.equals(s4)) {
			ret = false;
		}
		return ret;
	}
}
