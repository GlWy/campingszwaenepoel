package image;

import java.io.IOException;

import be.camping.campingzwaenepoel.common.utils.ImageUtils;

public class ImageUtilsTest {

	public static void main(String[] args) {
		testImageUtils();
	}
	
	public static void testImageUtils() {
		String sourceFile = "/Users/glennwybo/Development/Camping Zwaenepool/foto\'s/standplaats/A001/foto/origineel/A001-1.JPG";
		String destFile = "/Users/glennwybo/Development/Camping Zwaenepool/foto\'s/standplaats/A001/foto/origineel/A001-1-scaled.JPG";
		try {
			ImageUtils.scale(sourceFile, 650, 425, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
