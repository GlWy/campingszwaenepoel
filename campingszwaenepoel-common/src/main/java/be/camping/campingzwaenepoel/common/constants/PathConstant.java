package be.camping.campingzwaenepoel.common.constants;

import java.io.File;


public class PathConstant {

	@SuppressWarnings("unused")
	private final static String LOG4J_PATH = "/applicationContext.xml";
	private final static String SPRING_APPLICATIONCONTEXT_PATH_CONSTANT = "/applicationContext.xml";
	
	@SuppressWarnings("unused")
	private final static String ROOTDIR_PATH = getRootPath();
	private final static String RESOURCES_PATH = getRootPath() + "/src/main/resources";
	
	private static String FOTODIRECTORY_PATH = "fotodirectory";
	
	public static String getSpringApplicationContextPathConstant() {
		return SPRING_APPLICATIONCONTEXT_PATH_CONSTANT;
	}
	
	public static String getResourcesPath() {
		return RESOURCES_PATH;
	}
	
	private static String getRootPath() {
		String result = "";
		try {
			File file = new File (".");
			if (file.exists()) {
				result = file.getCanonicalPath();
			}
        } catch(Exception e) {
	        System.out.println(e);
	    }

		return result;
	}
	
	public static String getFotoDirectoryPath() {
		return FOTODIRECTORY_PATH;
	}
	
}

