package be.camping.campingzwaenepoel.presentation.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.enums.ContactgegevenTypeEnum;
import be.camping.campingzwaenepoel.service.transfer.ContactgegevenDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.WagenDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class PersoonPdf {
	
	private static Logger logger = Logger.getLogger(PersoonPdf.class);
	
	public void createDocument(PersoonDTO persoon, String path) {
		Document document = null;
//		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String name = "/tmp/persoon" + System.nanoTime() + ".pdf";
		try {
			document = new Document(PageSize.A4);
			PdfWriter writer = null;
			writer = PdfWriter.getInstance(document,new FileOutputStream(name));
			document.open();
			document.newPage();
			
			PdfContentByte cb = writer.getDirectContent();
			vulVasteDataIn(cb);
			vulDataIn(cb, persoon);

			try {
				insertFoto(document, path);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
//		    b = true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
/*			if (document != null) {
				document.close();			
			}*/
//			if (b) {
				try {
					document.close();
					Executable.openDocument(name);
					logger.info("opening document");
//					Executable.printDocument(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
//				Executable.printDocument(name);
//			}
		}
	}
	
	private void insertFoto(Document document, String path) throws IOException, DocumentException {
		String imageUrl = path;
		java.awt.Image awtImg = java.awt.Toolkit.getDefaultToolkit().createImage(imageUrl);
		Image image = Image.getInstance(awtImg, null);
		image.setAbsolutePosition(50f, 600f);
		document.add(image);

	}
	
	private void vulVasteDataIn(PdfContentByte cb) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 12);
		cb.beginText();
		int y = 850;
		int x = 250;
		int x2 = 50;
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Familienaam: ", x, y-80, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Voornaam: ", x, y-110, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Geslacht: ", x, y-140, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Geboorteplaats: ", x, y-170, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Geboortedatum: ", x, y-200, 0);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Leeftijd: ", x, y-180, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Straat: ", x2, y-300, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Nummer: ", x2, y-330, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Postcode: ", x2, y-360, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Plaats: ", x2, y-390, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Identiteitskaart: ", x2, y-420, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Nationaliteit: ", x2, y-450, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rijksregisternummer: ", x2, y-480, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Email: ", x2, y-510, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Telefoon: ", x2, y-540, 0);

		cb.endText();

	}

	private void vulDataIn(PdfContentByte cb, PersoonDTO persoon) throws DocumentException, IOException {

		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 12);
		cb.beginText();

		int y = 850;
		int x = 360;
		int x2 = 200;
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getNaam(), x, y-80, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getVoornaam(), x, y-110, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getGeslacht().getTranslationKey(), x, y-140, 0);
		DateFormat formatter; 
	    formatter = new SimpleDateFormat("dd-MM-yyyy");
	    String geboortedatum = formatter.format(persoon.getGeboortedatum());
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getGeboorteplaats(), x, y-170, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, geboortedatum, x, y-200, 0);
		Calendar leeftijd = Calendar.getInstance();
		leeftijd.setTime(persoon.getGeboortedatum());
//		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(berekenLeeftijd(leeftijd)), 700, 110, 0);
		if (persoon.getAdresDTO() != null) {
			if (persoon.getAdresDTO().getStraat() != null) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getAdresDTO().getStraat(), x2, y-300, 0);
			}
			if (persoon.getAdresDTO().getHuisnummer() != null) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getAdresDTO().getHuisnummer(), x2, y-330, 0);
			}
			if (persoon.getAdresDTO().getPostcode() != null) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getAdresDTO().getPostcode(), x2, y-360, 0);
			}
			if (persoon.getAdresDTO().getPlaats() != null) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getAdresDTO().getPlaats(), x2, y-390, 0);
			}
		}
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getIdentiteitskaartnummer(), x2, y-420, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getNationaliteit(), x2, y-450, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getRijksregisternummer(), x2, y-480, 0);
		String email = "";
		for (ContactgegevenDTO contactgegeven : persoon.getContactgegevens()) {
			if (ContactgegevenTypeEnum.EMAIL.equals(contactgegeven.getContactgegevenType())) {
				email = contactgegeven.getWaarde();
				break;
			}
		}
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, email, x2, y-510, 0);
		int yTel = y-540;
		for (ContactgegevenDTO contactgegeven : persoon.getContactgegevens()) {
			if (ContactgegevenTypeEnum.TELEFOON.equals(contactgegeven.getContactgegevenType())) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, contactgegeven.getWaarde(), x2, yTel, 0);
				yTel -= 20;
			}
		}

		int yNum = yTel - 10;
		if (persoon.getContactgegevens().size() == 0) yNum -= 20;
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Nummerplaat: ", 50, yNum, 0);
		if (persoon.getWagens() != null) {
			for (WagenDTO wagen : persoon.getWagens()) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, wagen.getNummerplaat(), x2, yNum, 0);
				yNum -= 20;
			}
		}
		
		y = yNum - 10;
		if (persoon.getWagens().size() == 0) y -= 20;
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Opmerking: ", 50, y, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, persoon.getOpmerking(), 50, y-30, 0);

		cb.endText();
	}

}
