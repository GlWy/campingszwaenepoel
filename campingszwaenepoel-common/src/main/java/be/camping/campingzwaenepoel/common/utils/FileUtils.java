package be.camping.campingzwaenepoel.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.common.error.ErrorCode;

public class FileUtils {

    public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String GIF = "gif";
    public final static String TIFF = "tiff";
    public final static String TIF = "tif";
    public final static String PNG = "png";

    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    public static String getName(File f) {
    	int index = f.getName().indexOf(".");
    	return f.getName().substring(0, index);
    }

    public static String getName(String filename) {
        int i = filename.lastIndexOf('.');

        if (i > 0 &&  i < filename.length() - 1) {
        	filename = filename.substring(0, i);
        }
        return filename;
    }
    
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

	public static void zipCurrentFotos(final String photoDir) throws Exception {
		File file = new File(photoDir + File.separator + "standplaats");
		if (!file.exists() || file.list().length == 0) return;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-k-mm-ss");
		String dest = photoDir + File.separator + "standplaats-" + sdf.format(new Date()) + ".zip";
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest));
		try {
			ZipUtils.zipDir(photoDir + File.separator + "standplaats", zos);
		} finally {
			zos.close();
		}
		File zipFile = new File(dest);
		if (!zipFile.exists()) {
			FotoConstant.addErrorToFotoImport(ErrorCode.ZIP_OLD_PHOTO_DIR);
			throw new Exception(ErrorCode.ZIP_OLD_PHOTO_DIR);
		}
		if (!ZipUtils.removeDirectory(new File(photoDir + File.separator + "standplaats"))) {
			FotoConstant.addErrorToFotoImport(ErrorCode.DELETE_CURRENT_PHOTO_DIR);
			throw new Exception(ErrorCode.DELETE_CURRENT_PHOTO_DIR);
		}
	}

    public static void copyFolder(File src, File dest)
	throws IOException{

	if(src.isDirectory()){

		//if directory not exists, create it
		if(!dest.exists()){
		   dest.mkdir();
		   System.out.println("Directory copied from " 
                          + src + "  to " + dest);
		}

		//list all the directory contents
		String files[] = src.list();

		for (String file : files) {
		   //construct the src and dest file structure
		   File srcFile = new File(src, file);
		   File destFile = new File(dest, file);
		   //recursive copy
		   copyFolder(srcFile,destFile);
		}

	}else{
		//if file, then copy it
		//Use bytes stream to support all file types
		InputStream in = new FileInputStream(src);
	        OutputStream out = new FileOutputStream(dest); 

	        byte[] buffer = new byte[1024];

	        int length;
	        //copy the file content in bytes 
	        while ((length = in.read(buffer)) > 0){
	    	   out.write(buffer, 0, length);
	        }

	        in.close();
	        out.close();
	        System.out.println("File copied from " + src + " to " + dest);
	}
}
}
