package be.camping.campingzwaenepoel.presentation.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.transfer.KasbonDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class KasbonPdf {

	private static Logger logger = Logger.getLogger(KasbonPdf.class);

	private String[] names;

	private final int yOffset = 60;
	private final int xOffset = 666;

	public void createDocument(KasbonDTO kasbon, List<Double> prijzen, String adres, String[] names) {
		this.names = names;
		Document document = null;
		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String name = "/tmp/kasbon" + System.nanoTime() + ".pdf";
		try {
			document = new Document(PageSize.A4.rotate());
			PdfWriter writer;
			writer = PdfWriter.getInstance(document, new FileOutputStream(name));
			document.open();
			document.newPage();

			PdfContentByte cb = writer.getDirectContent();
			setCommonData(cb, kasbon);
			setKasbonData(cb, kasbon, adres);

			try {
				insertLogo(document);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			vulDataIn(cb, kasbon, prijzen);
			b = true;

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
			if (document != null) {
				document.close();
			}
			if (b) {
				try {
					document.close();
					Executable.openDocument(name);
					// Executable.printDocument(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Executable.printDocument(name);
			}
		}
	}

	private void insertLogo(Document document) throws IOException, DocumentException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		java.awt.Image awtImg = java.awt.Toolkit.getDefaultToolkit().createImage(classloader.getResource(Constant.LOGO_CAMPING_ZWAENEPOEL));
		
		Image image = Image.getInstance(awtImg, null);
		image.setAbsolutePosition(117f, 105f);
		document.add(image);
	}

	/**
	 * Draws a colored rectangle.
	 * 
	 * @param canvas the canvas to draw on
	 * @param color  the Color
	 * @param x      the X coordinate
	 * @param y      the Y coordinate
	 * @param width  the width of the rectangle
	 * @param height the height of the rectangle
	 */
	private void colorRectangle(PdfContentByte canvas, Color color, float x, float y, float width, float height) {
		canvas.saveState();
		canvas.setColorFill(color);
		canvas.setColorStroke(Color.LIGHT_GRAY);
		canvas.rectangle(x, y, width, height);
		canvas.fillStroke();
		canvas.restoreState();
	}

	private void drawBox(PdfContentByte cb, int x1, int y1, int x2, int y2) {
		cb.setLineWidth(1f);
		cb.setColorStroke(Color.BLACK);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y1);
		cb.lineTo(x2, y2);
		cb.lineTo(x1, y2);
		cb.lineTo(x1, y1);
		cb.stroke();
	}

	private void drawLine(PdfContentByte cb, int x1, int y1, int x2, int y2) {
		cb.setLineWidth(1f);
		cb.setColorStroke(Color.BLACK);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y2);
		cb.stroke();
	}

	private void vulDataIn(PdfContentByte cb, KasbonDTO kasbon, List<Double> prijzen) throws DocumentException,
			IOException {

		int xOffsetPrijzen = xOffset + 125;

		DecimalFormat df = new DecimalFormat("0.00"); // @jve:decl-index=0:
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 12);
		cb.beginText();
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getAantalNachten()), xOffset, 436, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getAuto100()), xOffset, 416, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getAuto150()), xOffset, 401, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getWaarborg()), xOffset, 381, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getJeton()), xOffset, 361, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getZakGeel()), xOffset, 346, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getZakBlauw()), xOffset, 330, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getCar()), xOffset, 311, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getTent()), xOffset, 296, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getVolwassenen()), xOffset, 281, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getKinderen()), xOffset, 266, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(0)), xOffsetPrijzen, 416, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(1)), xOffsetPrijzen, 401, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(2)), xOffsetPrijzen, 381, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(3)), xOffsetPrijzen, 361, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(4)), xOffsetPrijzen, 346, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(5)), xOffsetPrijzen, 330, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(6)), xOffsetPrijzen, 311, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(7)), xOffsetPrijzen, 296, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(8)), xOffsetPrijzen, 281, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(9)), xOffsetPrijzen, 266, 0);

		int offsetPrijzen = 727;

		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(0) * kasbon.getAuto100()),
				offsetPrijzen, 416, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(1) * kasbon.getAuto150()),
				offsetPrijzen, 401, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(2) * kasbon.getWaarborg()),
				offsetPrijzen, 381, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(3) * kasbon.getJeton()),
				offsetPrijzen, 361, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(4) * kasbon.getZakGeel()),
				offsetPrijzen, 346, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(5) * kasbon.getZakBlauw()),
				offsetPrijzen, 330, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(6) * kasbon.getCar()),
				offsetPrijzen, 311, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(7) * kasbon.getTent()),
				offsetPrijzen, 296, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(8) * kasbon.getVolwassenen()),
				offsetPrijzen, 281, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(prijzen.get(9) * kasbon.getKinderen()),
				offsetPrijzen, 266, 0);

		xOffsetPrijzen -= 64;

		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(kasbon.getTelefoon()), xOffsetPrijzen,
				246, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(berekenTotalen(kasbon, prijzen)),
				xOffsetPrijzen, 221, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(kasbon.getBetaling()), xOffsetPrijzen,
				206, 0);
		double terug = (kasbon.getTerug() < 0) ? 0 : kasbon.getTerug();
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(terug), xOffsetPrijzen, 190, 0);

		double kostPerNacht = berekenKostPerNacht(kasbon, prijzen);
		double jzk = berekenJetonsZakKost(kasbon, prijzen);
		double tzw = jzk + kostPerNacht * kasbon.getAantalNachten();
		xOffsetPrijzen += 35;
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(kostPerNacht), xOffsetPrijzen, 167, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, Integer.toString(kasbon.getAantalNachten()), xOffsetPrijzen,
				149, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(kostPerNacht * kasbon.getAantalNachten()),
				xOffsetPrijzen, 129, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(jzk), xOffsetPrijzen, 109, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "\u20ac " + df.format(tzw), xOffsetPrijzen, 89, 0);

		/**
		 * colorRectangle(cb, Color.LIGHT_GRAY, 674, 164, 95, 14); colorRectangle(cb, Color.LIGHT_GRAY, 674, 146, 95,
		 * 14); colorRectangle(cb, Color.LIGHT_GRAY, 674, 126, 95, 14); colorRectangle(cb, Color.LIGHT_GRAY, 674, 106,
		 * 95, 14); colorRectangle(cb, Color.LIGHT_GRAY, 674, 86, 95, 14);
		 */
		// "\u20ac " +
		cb.endText();
	}

	private double berekenJetonsZakKost(KasbonDTO kasbon, List<Double> prijzen) {
		double totaal = kasbon.getJeton() * prijzen.get(3);
		totaal += kasbon.getZakGeel() * prijzen.get(4);
		totaal += kasbon.getZakBlauw() * prijzen.get(5);
		totaal += kasbon.getTelefoon();
		return totaal;
	}

	private double berekenKostPerNacht(KasbonDTO kasbon, List<Double> prijzen) {
		double totaal = kasbon.getAuto100() * prijzen.get(0);
		totaal += kasbon.getAuto150() * prijzen.get(1);
		totaal += kasbon.getCar() * prijzen.get(6);
		totaal += kasbon.getTent() * prijzen.get(7);
		totaal += kasbon.getVolwassenen() * prijzen.get(8);
		totaal += kasbon.getKinderen() * prijzen.get(9);
		return totaal;
	}

	private double berekenTotalen(KasbonDTO kasbon, List<Double> prijzen) {
		double zakJetonsKost = berekenJetonsZakKost(kasbon, prijzen);
		double kostPerNacht = berekenKostPerNacht(kasbon, prijzen);
		int aantalNachten = kasbon.getAantalNachten();
		double totaalNachten = kostPerNacht * aantalNachten;
		double totaalZonderWb = totaalNachten + zakJetonsKost;

		double waarborg = kasbon.getWaarborg() * prijzen.get(2);
		double totaal = totaalZonderWb + waarborg;
		return totaal;
	}

	public void setCommonData(PdfContentByte cb, KasbonDTO kasbon) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		int center = 247;
		int datumVanCenter = 37;
		int datumTotCenter = 467;

		cb.beginText();

		cb.setFontAndSize(bf, 50);
		if (!StringUtils.isEmpty(kasbon.getStandplaats())) {
			char[] chars = kasbon.getStandplaats().toCharArray();
			String s = "";
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				s += c;
			}
			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, s, center + 5, 525, 0);
		}

		cb.setFontAndSize(bf, 60);
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, kasbon.getNummerplaat(), center + 5, 515 - yOffset, 0);
		cb.setFontAndSize(bf, 28);
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (kasbon.getDatumVan() != null) {
			String aankomst = formatter.format(kasbon.getDatumVan());
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, aankomst, datumVanCenter, 470 - yOffset, 0);
		}
		if (kasbon.getDatumTot() != null) {
			String vertrek = formatter.format(kasbon.getDatumTot());
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, vertrek, datumTotCenter, 470 - yOffset, 0);
		}

		cb.setFontAndSize(bf, 12);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "AANKOMST - ARRIVE", datumVanCenter, 445 - yOffset, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "VERTREK - DEPART", datumTotCenter, 445 - yOffset, 0);

		cb.setFontAndSize(bf, 9);
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "DEZE KAART AAN DE AUTOVOORRUIT AANBRENGEN", center,
				140 - yOffset, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "METTEZ CETTE CARTE A LA PARE BRISSE DE VOTRE VOITURE", center,
				125 - yOffset, 0);
		String terugbrengen = "DEZE KAART EN BADGE TERUGBRENGEN OP VERTREKDATUM VOOR 12 UUR";
		String terugbrengenTr = "RENDEZ CETTE CARTE AVEC LE BADGE LE JOUR DE DEPART AVANT MIDI";
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, terugbrengen, center, 95 - yOffset, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, terugbrengenTr, center, 80 - yOffset, 0);

		cb.endText();
	}

	public void setKasbonData(PdfContentByte cb, KasbonDTO kasbon, String adres) throws DocumentException, IOException {
		int xLeftOffsetBox = 547;
		int xRightOffsetBox = 802;
		drawBox(cb, xLeftOffsetBox, 80, xRightOffsetBox, 560);
		drawLine(cb, xLeftOffsetBox, 180, xRightOffsetBox, 180);
		drawLine(cb, xLeftOffsetBox, 450, xRightOffsetBox, 450);
		int rightAlign = xOffset - 49;
		int leftAlign = xOffset - 44;
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 10);
		cb.beginText();
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Naam:", rightAlign, 545, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Standplaats:", rightAlign, 528, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Autoplaat:", rightAlign, 511, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Badge:", rightAlign, 494, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Van:", rightAlign, 477, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Tot:", rightAlign, 460, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kasbon.getNaam(), leftAlign, 545, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kasbon.getStandplaats(), leftAlign, 528, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kasbon.getNummerplaat(), leftAlign, 511, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kasbon.getBadge(), leftAlign, 494, 0);
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (kasbon.getDatumVan() != null) {
			String aankomst = formatter.format(kasbon.getDatumVan());
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, aankomst, leftAlign, 477, 0);
		}
		if (kasbon.getDatumTot() != null) {
			String vertrek = formatter.format(kasbon.getDatumTot());
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, vertrek, leftAlign, 460, 0);
		}
		rightAlign += 15;
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Aantal nachten", rightAlign, 435, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[0], rightAlign, 415, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[1], rightAlign, 400, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[2], rightAlign, 380, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[3], rightAlign, 360, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[4], rightAlign, 345, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[5], rightAlign, 330, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[6], rightAlign, 310, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[7], rightAlign, 295, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[8], rightAlign, 280, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, names[9], rightAlign, 265, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Telefoon", rightAlign, 245, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Totaal", rightAlign, 220, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Betaling", rightAlign, 205, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Terug", rightAlign, 190, 0);

		rightAlign += 30;
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Kost/nacht:", rightAlign, 166, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Aantal nachten:", rightAlign, 148, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Tot kost nachten:", rightAlign, 128, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "jeton/Zak/Kost:", rightAlign, 108, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Tot zonder WB:", rightAlign, 88, 0);

		int leftSideRectangle = rightAlign - 25;
		int width = 30;

		// boxes
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 433, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 413, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 398, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 378, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 358, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 343, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 327, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 308, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 293, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 278, width, 14);
		colorRectangle(cb, Color.MAGENTA, leftSideRectangle, 263, width, 14);

		leftSideRectangle += 34;
		width += 30;

		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 413, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 398, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 378, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 358, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 343, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 328, width, 13);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 308, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 293, width, 13);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 278, width, 14);
		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 263, width, 13);

		colorRectangle(cb, Color.YELLOW, leftSideRectangle, 243, width, 14);

		leftSideRectangle += 64;

		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 413, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 398, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 378, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 358, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 343, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 328, width, 13);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 308, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 293, width, 13);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 278, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 263, width, 13);

		leftSideRectangle -= 98;
		width += 34;

		colorRectangle(cb, Color.RED, leftSideRectangle, 218, width, 14);
		colorRectangle(cb, Color.GREEN, leftSideRectangle, 203, width, 14);
		colorRectangle(cb, Color.WHITE, leftSideRectangle, 187, width, 14);

		leftSideRectangle += 34;
		width += 1;

		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 164, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 146, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 126, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 106, width, 14);
		colorRectangle(cb, Color.LIGHT_GRAY, leftSideRectangle, 86, width, 14);

		width -= 27;

		bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setColorStroke(Color.BLACK);
		cb.setColorFill(Color.BLACK);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Kasbonnummer: " + kasbon.getKasbonnummer(), 552, width, 0);
		if (kasbon.getKasbonDatum() != null) {
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, formatter.format(kasbon.getKasbonDatum()), 802, width, 0);
		}

		width -= 10;

		bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 7);
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, adres, 678, 58, 0);

		cb.endText();
	}

	public void main(String args[]) throws FileNotFoundException, DocumentException {
		KasbonDTO kasbon = DTOFactory.createKasbon();
		kasbon.setStandplaats("B036");
		kasbon.setDatumTot(new Date());
		kasbon.setDatumVan(new Date());
		kasbon.setKasbonDatum(new Date());
		kasbon.setNaam("GYSBERGH HERVE");
		kasbon.setNummerplaat("ABC573");
		kasbon.setBadge("14537543");
		kasbon.setKasbonnummer(3);
		String adres = "NV Campings Zwaenepoel Vosseslag 20/5 - 8420 De Haan";
		List<Double> prijzen = new ArrayList<Double>();
		prijzen.add(2d);
		prijzen.add(4d);
		prijzen.add(60d);
		prijzen.add(1.25d);
		prijzen.add(1d);
		prijzen.add(0.5d);
		prijzen.add(6.5d);
		prijzen.add(5d);
		prijzen.add(2.5d);
		prijzen.add(2d);
		createDocument(kasbon, prijzen, adres, new String[10]);
	}
}
