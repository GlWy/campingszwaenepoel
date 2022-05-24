package be.camping.campingzwaenepoel.presentation.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class NummerplatenPdf {
	
	private Logger logger = Logger.getLogger(BetalingenPdf.class);
	
	public NummerplatenPdf(List<Object[]> nummerplaten, int jaar, String camping, String contact) {
		createDocument(nummerplaten, jaar, camping, contact);
	}
	
	public void createDocument(List<Object[]> nummerplaten, int jaar, String camping, String contact) {
		Document document = null;
		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String name = "/tmp/nummerplaten" + System.nanoTime() + ".pdf";
		try {
			document = new Document(PageSize.A4);
			PdfWriter writer = null;
			writer = PdfWriter.getInstance(document,new FileOutputStream(name));
			document.open();
			document.newPage();
			
			PdfContentByte cb = writer.getDirectContent();
			createContent(document, cb, nummerplaten, jaar, camping, contact);
			b = true;

		} catch (FileNotFoundException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (DocumentException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (document != null) {
				document.close();			
			}
			if (b) {
				try {
					Executable.openDocument(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				Executable.printDocument(name);
			}
		}
	}
	
	private void createContent(Document document, PdfContentByte cb, List<Object[]> nummerplaten, int jaar, String camping, String contact)
																										throws DocumentException, IOException {
		int teller = 1;
		int offsetX = 0;
		int offsetY = 0;
		System.err.println("aantal: " + nummerplaten.size());
		for (Object[] o : nummerplaten) {
			String naam = jaar + " - " + camping + " - " + jaar;
			createNummerplaatTemplate(document, cb, o, naam, contact, offsetX, offsetY);

			if (teller%8 == 0) {
				document.newPage();
				offsetY = 0;
				offsetX = 0;
				teller = 0;
			} else if (teller%2 != 0) {
				offsetX = 285;
			} else if (teller%2 == 0) {
				offsetX = 0;
				offsetY += 199;
			}
			
			teller++;
		}
	}

	private void createNummerplaatTemplate(Document document, PdfContentByte cb, Object[] gegevens, 
											String camping, String contact, int offsetX, int offsetY) {
		try {
			
			String plaatsnummer = Integer.toString((Integer)gegevens[1]);
			while (plaatsnummer.length() < 3) {
				plaatsnummer = "0" + plaatsnummer;
			}
			String standplaats = (Character)gegevens[0] + " " + plaatsnummer;
			cb.beginText();

			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb.setFontAndSize(bf, 52);
			
//			float horScaling = cb.getHorizontalScaling();
			cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, standplaats, 147 + offsetX, 777 - offsetY, 0);

//			if (((String)gegevens[2]).length() <= 6) cb.setHorizontalScaling(140f);
			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, (String)gegevens[2], 147 + offsetX, 652 - offsetY, 0);

			cb.setFontAndSize(bf, 15);
			cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
//			cb.setHorizontalScaling(horScaling);
			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, camping, 147 + offsetX, 737 - offsetY, 0);
			
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb.setFontAndSize(bf, 7);
			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, contact, 147 + offsetX, 717 - offsetY, 0);

			drawLine(cb, offsetX, 842 - offsetY, 5 + offsetX, 842 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, 280 + offsetX, 842 - offsetY, 285 + offsetX, 842 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, 285 + offsetX, 842 - offsetY, 285 + offsetX, 837 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, 285 + offsetX, 648 - offsetY, 285 + offsetX, 643 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, 285 + offsetX, 643 - offsetY, 280 + offsetX, 643 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, 5 + offsetX, 643 - offsetY, offsetX, 643 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, offsetX, 643 - offsetY, offsetX, 648 - offsetY, Color.BLACK, 0.5f);
			drawLine(cb, offsetX, 837 - offsetY, offsetX, 842 - offsetY, Color.BLACK, 0.5f);

			cb.endText();
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}
	 
	private void drawLine(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color, Float thickness) {
		if (thickness == null) {
			cb.setLineWidth(1f);
		} else {
			cb.setLineWidth(thickness);
		}
		if (color != null) cb.setColorStroke(color);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y2);
		cb.stroke();
	}

}
