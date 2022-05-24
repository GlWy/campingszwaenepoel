package image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/*
 * @author mkyong
 *
 */
public class ImageTest {

   public static void main(String [] args){

   try{

	BufferedImage originalImage = 
       ImageIO.read(new File("C:\\Documents and Settings\\Administrator\\Mijn documenten\\Camping Zwaenepoel\\foto's\\personen\\159.jpg"));	

        //convert BufferedImage to byte array
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write( originalImage, "jpg", baos );
	baos.flush();
	byte[] imageInByte = baos.toByteArray();
	baos.close();

	//convert byte array back to BufferedImage
	InputStream in = new ByteArrayInputStream(imageInByte);
	BufferedImage bImageFromConvert = ImageIO.read(in);

	ImageIO.write(bImageFromConvert, "jpg", 
             new File("c:\\tmp\\mypic_new.jpg")); 

   }catch(IOException e){
	System.out.println(e.getMessage());
   }		
  }	
}
