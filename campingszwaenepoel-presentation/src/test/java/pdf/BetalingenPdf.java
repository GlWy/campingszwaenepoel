package pdf;

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
import java.util.Set;

import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;
import be.camping.campingzwaenepoel.service.transfer.BetalerDTO;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class BetalingenPdf {
	
	private static Logger logger = Logger.getLogger(BetalingenPdf.class);
	private static DecimalFormat df = new DecimalFormat("#,##0.00");
	private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main (String[] args) {
		List<StandplaatsDTO> standplaatsen = new ArrayList<StandplaatsDTO>();
		for (int i = 0; i < 9; i++) {
			FactuurDetailDTO factuurDetail1 = new FactuurDetailDTO();
			factuurDetail1.setAardBetaling(BetalingEnum.NOGTEBETALEN);
			factuurDetail1.setBedrag(1300.44);
			FactuurDetailDTO factuurDetail2 = new FactuurDetailDTO();
			factuurDetail2.setAardBetaling(BetalingEnum.BETAALD);
			factuurDetail2.setBedrag(300.23);
			FactuurDetailDTO factuurDetail3 = new FactuurDetailDTO();
			factuurDetail3.setAardBetaling(BetalingEnum.UITSTEL);
			factuurDetail3.setBedrag(200);
			factuurDetail3.setDatum(new Date());
			FactuurDetailDTO factuurDetail4 = new FactuurDetailDTO();
			factuurDetail4.setAardBetaling(BetalingEnum.UITSTEL);
			factuurDetail4.setBedrag(250);
			factuurDetail4.setDatum(new Date());
			StandplaatsDTO standplaats = new StandplaatsDTO();
			standplaats.setPlaatsgroep("A");
			standplaats.setPlaatsnummer(i+1);
			BetalerDTO betaler = new BetalerDTO();
			PersoonDTO persoon1 = new PersoonDTO();
			persoon1.setNaam("Wybo");
			persoon1.setVoornaam("Donald");
			PersoonDTO persoon2 = new PersoonDTO();
			persoon2.setNaam("Van Eessen");
			persoon2.setVoornaam("Francine");
			betaler.setHoofdbetaler(persoon1);
			betaler.setBetaler(persoon2);
			standplaats.getBetalers().add(betaler);
			standplaats.getFactuurDetails().add(factuurDetail1);
			standplaats.getFactuurDetails().add(factuurDetail2);
			standplaats.getFactuurDetails().add(factuurDetail3);
			standplaats.getFactuurDetails().add(factuurDetail4);
			standplaatsen.add(standplaats);
		}
		Date date = new Date();
		createDocument(date, standplaatsen, true);
	}
	
	public static void createDocument(Date datum, List<StandplaatsDTO> standplaatsen, boolean uitstel) {
		Document document = null;
		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String name = "/tmp/betalingen" + System.nanoTime() + ".pdf";
		try {
			document = new Document(PageSize.A4);
			PdfWriter writer = null;
			writer = PdfWriter.getInstance(document,new FileOutputStream(name));
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();
			createHeader(cb);
			createFooter(cb, datum);
			createContent(document, cb, standplaatsen, uitstel);
			b = true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
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
	
	private static void createHeader(PdfContentByte cb) throws DocumentException, IOException {
		cb.beginText();
		drawLine(cb, 80, 800, 520, 800, Color.LIGHT_GRAY, 2f);
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 24);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Openstaande betalingen:", 90, 770, 0);
		drawLine(cb, 80, 760, 520, 760, Color.BLACK, 2f);
		cb.endText();
	}
	
	private static void createContent(Document document, PdfContentByte cb, List<StandplaatsDTO> standplaatsen, boolean bUitstel) throws DocumentException, IOException {
		int x = 90;
		int y = 730;
		int teller = 0;
		for (StandplaatsDTO standplaats : standplaatsen) {
			int totaal = y-15;
			if (bUitstel) {
				int aantalUitstellen = 0;
				for (FactuurDetailDTO factuurDetail : standplaats.getFactuurDetails()) {
					if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) aantalUitstellen++;
				}
				totaal -= aantalUitstellen*12;
			}			
			if (totaal < 90) {
				document.newPage();
				y = 750;
			}
			cb.beginText();
			// standplaats
			colorRectangle(cb, Color.LIGHT_GRAY, x, y, 60, 15);
			drawBox(cb, x, y, 150, y+15, Color.BLACK);
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb.setFontAndSize(bf, 10);
			String s = Integer.toString(standplaats.getPlaatsnummer());
			while (s.length() < 3) {
				s = "0" + s;
			}
			s = standplaats.getPlaatsgroep() + s;
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, s, 110, y+4, 0);
			
			// betalers
			if (standplaats.getBetalers().size() > 0) {
				BetalerDTO betaler = null;
				for (BetalerDTO betalerDTO : standplaats.getBetalers()) {
					betaler = betalerDTO;
					break;
				}
				String naam = "";
				if (betaler.getHoofdbetaler() != null) {
					naam += betaler.getHoofdbetaler().getNaam() + " " + betaler.getHoofdbetaler().getVoornaam();
					if (betaler.getBetaler() != null) {
						naam += " - ";
					}
				}
				if (betaler.getBetaler() != null) {
					naam += betaler.getBetaler().getNaam() + " " + betaler.getBetaler().getVoornaam();
				}
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, naam, 170, y+4, 0);
			}
			
			// betalingen
			double tebetalen = 0;
			double betaald = 0;
			for(FactuurDetailDTO factuurDetail : standplaats.getFactuurDetails()) {
				if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) {
					tebetalen += factuurDetail.getBedrag() + factuurDetail.getAndereKosten() 
							  + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				} else if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) {
					betaald += factuurDetail.getBedrag() + factuurDetail.getAndereKosten() 
					  + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				}
			}
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "TeBetalen:", 90, y-=15, 0);
			drawLine(cb, 90, y-1, 140, y-1, Color.BLACK, 1f);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Betaald:", 230, y, 0);
			drawLine(cb, 230, y-1, 268, y-1, Color.BLACK, 1f);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Saldo:", 350, y, 0);
			drawLine(cb, 350, y-1, 380, y-1, Color.BLACK, 1f);

			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(tebetalen) + " \u20ac", 150, y, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(betaald) + " \u20ac", 280, y, 0);
			double totaalSom = tebetalen - betaald;
			if (totaalSom < 0) totaalSom = 0;
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(totaalSom) + " \u20ac", 390, y, 0);

			if (bUitstel) {
				cb.setFontAndSize(bf, 9);
				List<FactuurDetailDTO> sortedFactuurDetails = sortFactuurDetails(standplaats.getFactuurDetails());
				for(FactuurDetailDTO factuurDetail : sortedFactuurDetails) {
					if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) {
						String uitstel = "- ";
						uitstel += formatter.format(factuurDetail.getDatum());
						uitstel += " N.T.B.:              ";
						try {
							uitstel += df.format(factuurDetail.getBedrag()) + " \u20ac";						
						} catch (Exception e) {
							logger.error("error formatting date of factuurDetailDTO.uitstel");
						}
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT, uitstel, x+30, y-=12, 0);
					}
				}
			}

			y-=30;
			teller++;
			cb.endText();
		}
	}
	
	private static List<FactuurDetailDTO> sortFactuurDetails(Set<FactuurDetailDTO> factuurDetails) {
		List<FactuurDetailDTO> sortedFactuurDetails = new ArrayList<FactuurDetailDTO>();
		for (FactuurDetailDTO tmpFactuurDetail : factuurDetails) {
			if (!BetalingEnum.UITSTEL.equals(tmpFactuurDetail.getAardBetaling())) continue;
			boolean sorted = false;
			for (FactuurDetailDTO sortedFactuurDetail : sortedFactuurDetails) {
				if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
					sortedFactuurDetails.add(sortedFactuurDetails.indexOf(sortedFactuurDetail), tmpFactuurDetail);
					sorted = true;
					break;
				}
			}
			if (!sorted) {
				sortedFactuurDetails.add(tmpFactuurDetail);
			}
		}
		return sortedFactuurDetails;
	}
	
	private static void createFooter(PdfContentByte cb, Date date) throws DocumentException, IOException {
		cb.beginText();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 12);
		DateFormat formatter; 
	    try {
		    formatter = new SimpleDateFormat("dd MMMM yyyy");
	    	cb.showTextAligned(PdfContentByte.ALIGN_LEFT, formatter.format(date), 90, 50, 0);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		cb.endText();
	}
	
	private static void drawBox(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color) {
		cb.setLineWidth(1f);
		if (color != null) cb.setColorStroke(color);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y1);
		cb.lineTo(x2, y2);
		cb.lineTo(x1, y2);
		cb.lineTo(x1, y1);
		cb.stroke();
	}
	
	private static void drawLine(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color, Float thickness) {
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

	/**
     * Draws a colored rectangle.
     * @param canvas the canvas to draw on
     * @param color the Color
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
	private static void colorRectangle(PdfContentByte canvas, Color color, float x, float y, float width, float height) {
        canvas.saveState();
        canvas.setColorFill(color);
        canvas.setColorStroke(Color.LIGHT_GRAY);
        canvas.rectangle(x, y, width, height);
        canvas.fillStroke();
        canvas.restoreState();
    }

}
