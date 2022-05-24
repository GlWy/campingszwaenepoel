package be.camping.campingzwaenepoel.presentation.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.constants.Constant;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class FotoAndOpmerkingenFacturatiePdf {
	
	private Logger logger = Logger.getLogger(BetalingenPdf.class);
	
	public FotoAndOpmerkingenFacturatiePdf(List<Map<String, Object>> FotosFacturatie) {
		createDocument(FotosFacturatie);
	}
	
	public void createDocument(List<Map<String, Object>> FotosFacturatie) {
		Document document = null;
		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String name = "/tmp/facturatieFotoOpmerkingen" + System.nanoTime() + ".pdf";
		try {
			document = new Document(PageSize.A4);
			PdfWriter writer = null;
			writer = PdfWriter.getInstance(document,new FileOutputStream(name));
			document.open();
			document.newPage();
			
			PdfContentByte cb = writer.getDirectContent();
			createContent(document, cb, FotosFacturatie);
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
	
	@SuppressWarnings("unchecked")
	private void createContent(Document document, PdfContentByte cb, List<Map<String, Object>> fotosFacturaties) throws DocumentException, IOException {
		for (Map<String, Object> map : fotosFacturaties) {
			cb.setColorFill(Color.BLACK);
			try {
				cb.beginText();

				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				cb.setFontAndSize(bf, 12);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)map.get(Constant.STANDPLAATS), 30, 810, 0);
				drawLine(cb, 30, 808, 60, 808, Color.BLACK, 1f);

				bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				int offset = 0;
				
				if (map.containsKey(Constant.OPMERKING)) {
					cb.setFontAndSize(bf, 10);
					float tbWidth = 520f;
					/**
					 * TODO: not ok --> split with \n length/2 + number of \n
					 */
					String[] lines = ((String)map.get(Constant.OPMERKING)).split("\n");
					int nbrOfLines = lines.length - 1;
					for (String line : lines) {
						int length = line.length();
						while (length > 80) {
							nbrOfLines++;
							length -= 80;
						}
					}
					if (nbrOfLines == 0) nbrOfLines = 1;
					float tbHeight = nbrOfLines * 11 + 15;
					offset = (int)tbHeight;
					
					PdfTemplate template = cb.createTemplate(tbWidth, tbHeight);
					
					Font font = new Font(Font.COURIER, 10f, Font.NORMAL);
					Phrase p = new Phrase((String)map.get(Constant.OPMERKING), font);
					
					ColumnText ct = new ColumnText(template);
					ct.setSimpleColumn(p, 0, 0, tbWidth, tbHeight, 10, Phrase.ALIGN_LEFT);
					ct.go();
					
					cb.addTemplate(template, 30, 805 - offset);
				}
								
				int teller = 1;
				int size = 0;

				if (map.containsKey(Constant.FOTOS)) {
					List<String> fotos = (List<String>)map.get(Constant.FOTOS);
					size = fotos.size();
					for (String foto : fotos) {
						if (teller%2 != 0) {
							drawBox(cb, 50, 800-offset, 495, 510-offset, null);
							insertImage(document, foto, 50, 510-offset);
						} else {
							drawBox(cb, 50, 490-offset, 495, 200-offset, null);
							insertImage(document, foto, 50, 200-offset);
							if (teller < size) {
								cb.endText();
								document.newPage();
								cb.beginText();
							}							
						}
						teller++;
						if (teller > 2) offset = 0;
					}
				}

				cb.endText();
				
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			document.newPage();
		}
	}

	 
	private void drawBox(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color) {
		cb.setLineWidth(1f);
		if (color != null) cb.setColorStroke(color);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y1);
		cb.lineTo(x2, y2);
		cb.lineTo(x1, y2);
		cb.lineTo(x1, y1);
		cb.stroke();
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

	private void insertImage(Document document, String imageUrl, int x, int y) throws IOException, DocumentException {
		java.awt.Image awtImg = java.awt.Toolkit.getDefaultToolkit().createImage(imageUrl);
		Image image = Image.getInstance(awtImg, null);
		image.setAbsolutePosition(x, y);
		document.add(image);

	}

}
