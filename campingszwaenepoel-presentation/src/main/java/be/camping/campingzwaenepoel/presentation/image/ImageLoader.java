package be.camping.campingzwaenepoel.presentation.image;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageLoader {

	public static Image getImage(String filename) {
	    Image returnValue = null;
	    FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (fis != null) {
	    	BufferedInputStream bis = new BufferedInputStream(fis);
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	try {
	    		int ch;
	    		while ((ch = bis.read()) != -1) {
	    			baos.write(ch);
	    		}
	    		returnValue = Toolkit.getDefaultToolkit().createImage(
	            baos.toByteArray());
	    	} catch (IOException exception) {
	    		System.err.println("Error loading: " + filename);
	    	}
	    }
	    return returnValue;
	}
}
