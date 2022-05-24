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
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;
import be.camping.campingzwaenepoel.service.transfer.BetalerDTO;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class BetalingenPdf {
	
	private Logger logger = Logger.getLogger(BetalingenPdf.class);
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public void createDocument(Date datum, List<StandplaatsDTO> standplaatsen, boolean uitstel, boolean showDate) {
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
			document.newPage();
			
			PdfContentByte cb = writer.getDirectContent();
			createHeader(cb);
			createFooter(cb, datum);
			createContent(document, cb, standplaatsen, showDate);
//			createContent(document, cb, standplaatsen, uitstel);
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
	
	private void createHeader(PdfContentByte cb) throws DocumentException, IOException {
		cb.beginText();
		drawLine(cb, 80, 800, 520, 800, Color.LIGHT_GRAY, 2f);
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 24);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Openstaande betalingen:", 90, 770, 0);
		drawLine(cb, 80, 760, 520, 760, Color.BLACK, 2f);
		cb.endText();
	}

	private void createContent(Document document, PdfContentByte cb, List<StandplaatsDTO> standplaatsen, boolean showDate) throws DocumentException, IOException {
		int x = 90;
		int y = 730;
		int teller = 0;
		for (StandplaatsDTO standplaats : standplaatsen) {
			cb.setColorFill(Color.BLACK);
			try {
				// betalingen
				double tebetalen = 0;
				double betaald = 0;
				if (standplaats.getTeBetalenSom() != 0 && standplaats.getBetaaldeSom() != 0) {
					tebetalen = standplaats.getTeBetalenSom();
					betaald = standplaats.getBetaaldeSom();
				} else {
					for(FactuurDetailDTO factuurDetail : standplaats.getFactuurDetails()) {
						if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) {
							tebetalen += factuurDetail.getBedrag() + factuurDetail.getAndereKosten() 
									  + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
						} else if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) {
							betaald += factuurDetail.getBedrag() + factuurDetail.getAndereKosten() 
							  + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
						}
					}
				}

				List<FactuurDetailDTO> sortedFactuurDetails = sortFactuurDetails(standplaats.getFactuurDetails());
//				boolean containsuitstel = ((tebetalen - betaald > 0) && (betaald > 0.1))?true:false;
/*				boolean containsuitstel = false;
				for (FactuurDetailDTO factuurDetail : standplaats.getFactuurDetails()) {
					if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) {
						containsuitstel = true;
						break;
					}
				}*/
				
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				logger.info("begintext for " + standplaats.getId());

				int totaal = y-15;
				int aantalUitstellen = standplaats.getFactuurDetails().size();
				totaal -= aantalUitstellen*12;
				if (aantalUitstellen > 0) totaal -= 15;
				if (totaal < 90) {
					document.newPage();
					y = 750;
				}

				cb.beginText();

				// standplaats
				colorRectangle(cb, Color.LIGHT_GRAY, x, y, 60, 15);
				drawBox(cb, x, y, 150, y+15, Color.BLACK);
				cb.setFontAndSize(bf, 10);
				String s = Integer.toString(standplaats.getPlaatsnummer());
				while (s.length() < 3) {
					s = "0" + s;
				}
				s = standplaats.getPlaatsgroep() + s;
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, s, 110, y+4, 0);
				
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

//				if (containsuitstel) {
					cb.setFontAndSize(bf, 9);
					int offsetBedrag = 50;
					int offsetUitstel = 100;
					int offsetRappel = 150;
					int offsetAndere = 200;
					int offsetOpmerking = 250;
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "factuur", x+30+offsetBedrag, y-=12, 0);
					drawLine(cb, x+30+offsetBedrag, y-1, x+57+offsetBedrag, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "uitstel", x+30+offsetUitstel, y, 0);
					drawLine(cb, x+30+offsetUitstel, y-1, x+53+offsetUitstel, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "rappel", x+30+offsetRappel, y, 0);
					drawLine(cb, x+30+offsetRappel, y-1, x+55+offsetRappel, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "andere", x+30+offsetAndere, y, 0);
					drawLine(cb, x+30+offsetAndere, y-1, x+58+offsetAndere, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "opmerking", x+30+offsetOpmerking, y, 0);
					drawLine(cb, x+30+offsetOpmerking, y-1, x+71+offsetOpmerking, y-1, Color.BLACK, 1f);
					y -= 2;
					
					for(FactuurDetailDTO factuurDetail : sortedFactuurDetails) {
						if (!factuurDetail.isShow()) continue;
						if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) cb.setColorFill(Color.BLACK);
						if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) cb.setColorFill(Color.RED);
						if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) cb.setColorFill(Color.GREEN);
						if (showDate) {
							String sUitstel = "- ";
							sUitstel += formatter.format(factuurDetail.getDatum());
							sUitstel += " " + factuurDetail.getAardBetaling().getTranslationKey() + ":              ";
							cb.showTextAligned(PdfContentByte.ALIGN_LEFT, sUitstel, x+5, y-=12, 0);
						} else {
							y-=12;
//							String sAardBetaling = factuurDetail.getAardBetaling().getTranslationKey();
//							cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, sAardBetaling, x+35, y-=12, 0);
						}
						try {
							if (factuurDetail.getBedrag() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getBedrag()) + " \u20ac", x+30+offsetBedrag, y, 0);
							}
							if (factuurDetail.getUitstelkosten() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getUitstelkosten()) + " \u20ac", x+30+offsetUitstel, y, 0);
							}
							if (factuurDetail.getRappel() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getRappel()) + " \u20ac", x+30+offsetRappel, y, 0);
							}
							if (factuurDetail.getAndereKosten() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getAndereKosten()) + " \u20ac", x+30+offsetAndere, y, 0);
							}
							if (!StringUtils.isEmpty(factuurDetail.getOpmerking())) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, factuurDetail.getOpmerking(), x+30+offsetOpmerking, y, 0);
							}
						} catch (Exception e) {
							logger.error("error formatting date of factuurDetailDTO.uitstel");
						}
					}
//				}

				y-=30;
				teller++;
				logger.info("endtext for " + standplaats.getId());
				cb.endText();
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
//				e.printStackTrace();
			}
		}
	}

/*	private void createContent(Document document, PdfContentByte cb, List<StandplaatsDTO> standplaatsen, boolean uitstel) throws DocumentException, IOException {
		int x = 90;
		int y = 730;
		int teller = 0;
		for (StandplaatsDTO standplaats : standplaatsen) {
			try {
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

				List<FactuurDetailDTO> sortedFactuurDetails = sortFactuurDetails(standplaats.getFactuurDetails());
				boolean containsuitstel = false;
				for (FactuurDetailDTO factuurDetail : standplaats.getFactuurDetails()) {
					if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) {
						containsuitstel = true;
						break;
					}
				}
				if (containsuitstel) {
					if (uitstel) {
						sortedFactuurDetails = betalingsLijstVoorMetUitstel(sortedFactuurDetails, betaald);
					} else {
						sortedFactuurDetails = betalingsLijstVoorZonderUitstel(sortedFactuurDetails, betaald, tebetalen);
					}
				}
				
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				logger.info("begintext for " + standplaats.getId());

				if ((sortedFactuurDetails.size() > 0 && containsuitstel) || uitstel || !containsuitstel) {
					int totaal = y-15;
					int aantalUitstellen = standplaats.getFactuurDetails().size();
					totaal -= aantalUitstellen*12;
					if (aantalUitstellen > 0) totaal -= 15;
					if (totaal < 90) {
						document.newPage();
						y = 750;
					}
				}

				cb.beginText();

				if ((sortedFactuurDetails.size() > 0 && containsuitstel) || uitstel || !containsuitstel) {

					// standplaats
					colorRectangle(cb, Color.LIGHT_GRAY, x, y, 60, 15);
					drawBox(cb, x, y, 150, y+15, Color.BLACK);
					cb.setFontAndSize(bf, 10);
					String s = Integer.toString(standplaats.getPlaatsnummer());
					while (s.length() < 3) {
						s = "0" + s;
					}
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
				}
				if (sortedFactuurDetails.size() > 0 && containsuitstel) {
					cb.setFontAndSize(bf, 9);
					int offsetBedrag = 50;
					int offsetUitstel = 100;
					int offsetRappel = 150;
					int offsetAndere = 200;
					int offsetOpmerking = 250;
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "bedrag", x+30+offsetBedrag, y-=12, 0);
					drawLine(cb, x+30+offsetBedrag, y-1, x+57+offsetBedrag, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "uitstel", x+30+offsetUitstel, y, 0);
					drawLine(cb, x+30+offsetUitstel, y-1, x+53+offsetUitstel, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "rappel", x+30+offsetRappel, y, 0);
					drawLine(cb, x+30+offsetRappel, y-1, x+55+offsetRappel, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "andere", x+30+offsetAndere, y, 0);
					drawLine(cb, x+30+offsetAndere, y-1, x+58+offsetAndere, y-1, Color.BLACK, 1f);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "opmerking", x+30+offsetOpmerking, y, 0);
					drawLine(cb, x+30+offsetOpmerking, y-1, x+71+offsetOpmerking, y-1, Color.BLACK, 1f);
					y -= 2;
					
					for(FactuurDetailDTO factuurDetail : sortedFactuurDetails) {
//						if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) cb.setColorFill(Color.BLACK);
//						if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) cb.setColorFill(Color.RED);
//						if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) cb.setColorFill(Color.GREEN);
						String sUitstel = "- ";
						sUitstel += formatter.format(factuurDetail.getDatum());
						sUitstel += " " + factuurDetail.getAardBetaling().getTranslationKey() + ":              ";
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT, sUitstel, x+5, y-=12, 0);
						try {
							if (factuurDetail.getBedrag() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getBedrag()) + " \u20ac", x+30+offsetBedrag, y, 0);
							}
							if (factuurDetail.getUitstelkosten() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getUitstelkosten()) + " \u20ac", x+30+offsetUitstel, y, 0);
							}
							if (factuurDetail.getRappel() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getRappel()) + " \u20ac", x+30+offsetRappel, y, 0);
							}
							if (factuurDetail.getAndereKosten() > 0) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, df.format(factuurDetail.getAndereKosten()) + " \u20ac", x+30+offsetAndere, y, 0);
							}
							if (!StringUtils.isEmpty(factuurDetail.getOpmerking())) {
								cb.showTextAligned(PdfContentByte.ALIGN_LEFT, factuurDetail.getOpmerking(), x+30+offsetOpmerking, y, 0);
							}
						} catch (Exception e) {
							logger.error("error formatting date of factuurDetailDTO.uitstel");
						}
					}
				}
//				cb.setColorFill(Color.BLACK);
				if ((sortedFactuurDetails.size() > 0 && containsuitstel) || uitstel || !containsuitstel) {
					y-=30;
				}
				teller++;
				logger.info("endtext for " + standplaats.getId());
				cb.endText();
			} catch (Exception e) {
				logger.error(e);
//				e.printStackTrace();
			}
		}
	}*/
	 
	private List<FactuurDetailDTO> sortFactuurDetails(Set<FactuurDetailDTO> factuurDetails) {
		List<FactuurDetailDTO> sortedFactuurDetails = new ArrayList<FactuurDetailDTO>();
		for (FactuurDetailDTO tmpFactuurDetail : factuurDetails) {
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
	
	private void createFooter(PdfContentByte cb, Date date) throws DocumentException, IOException {
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

	/**
     * Draws a colored rectangle.
     * @param canvas the canvas to draw on
     * @param color the Color
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param width the width of the rectangle
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

/*	private List<FactuurDetailDTO> betalingsLijstVoorMetUitstel(List<FactuurDetailDTO> tmpFactuurDetails, double betaald) {
		List<FactuurDetailDTO> factuurDetails = new ArrayList<FactuurDetailDTO>();
		for (FactuurDetailDTO tmpFactuurDetail : tmpFactuurDetails) {
			if (BetalingEnum.UITSTEL.equals(tmpFactuurDetail.getAardBetaling())) {
				betaald -= calculateTotalFactuurDetail(tmpFactuurDetail);
				if (betaald < 0) {
					factuurDetails.add(tmpFactuurDetail);
				}
			}
		}
		return factuurDetails;
	}

	private List<FactuurDetailDTO> betalingsLijstVoorZonderUitstel(List<FactuurDetailDTO> tmpFactuurDetails, double betaald, double tebetalen) {
		List<FactuurDetailDTO> factuurDetails = new ArrayList<FactuurDetailDTO>();
		tebetalen -= betaald;
		for (FactuurDetailDTO tmpFactuurDetail : tmpFactuurDetails) {
			if (BetalingEnum.UITSTEL.equals(tmpFactuurDetail.getAardBetaling())) {
				betaald -= calculateTotalFactuurDetail(tmpFactuurDetail);
				if (betaald < 0) {
					factuurDetails.add(tmpFactuurDetail);
				}
				Calendar now = Calendar.getInstance();
				if (!now.getTime().after(tmpFactuurDetail.getDatum())) {
					tebetalen -= calculateTotalFactuurDetail(tmpFactuurDetail);
				}
			}
		}
		if(tebetalen < 0.01) {
			factuurDetails = new ArrayList<FactuurDetailDTO>();
		}
		return factuurDetails;
	}

	private double calculateTotalFactuurDetail(FactuurDetailDTO factuurDetail) {
		double d = 0;
		d = factuurDetail.getBedrag() + factuurDetail.getRappel() + factuurDetail.getUitstelkosten() + factuurDetail.getAndereKosten();
		return d;
	}*/
	
}
