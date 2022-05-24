package be.camping.campingzwaenepoel.presentation.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.BetalerDTO;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class BetalingenTabelPdf {

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	public void createDocument(Date datum, List<StandplaatsDTO> standplaatsen) throws IOException {
		Document document = null;
		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
//		String name = "/Users/glennwybo/Development/Camping Zwaenepool/testfiles/betalingenTabel" + System.nanoTime() + ".pdf";
		String name = "/tmp/betalingenTabel" + System.nanoTime() + ".pdf";
		document = new Document(PageSize.A4);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name));
			document.open();
			document.newPage();
			try {
				PdfPTable table = createTable();
				populateTable(datum, table, standplaatsen);
				document.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PdfContentByte cb = writer.getDirectContent();
			createFooter(cb, datum);
			b = true;
		} catch (FileNotFoundException e) {
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
	
	private static PdfPTable createTable() throws DocumentException {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{15, 35, 11, 11, 11, 11, 35});
        
        table.addCell(new Phrase("Standplaats", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Betaler(s)", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Factuur", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Uitstel", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Rappel", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Andere", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Opmerking", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        
        return table;
    }
	
	private void populateTable(Date datum, PdfPTable table, List<StandplaatsDTO> standplaatsen) {
		for (StandplaatsDTO standplaats : standplaatsen) {
			
			String s = Integer.toString(standplaats.getPlaatsnummer());
			while (s.length() < 3) {
				s = "0" + s;
			}
			s = standplaats.getPlaatsgroep() + s;
	        table.addCell(new Phrase(s, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        
	        String sBetaler = "";
	        PersoonDTO hoofdBetaler = new PersoonDTO();
	        PersoonDTO betaler = new PersoonDTO();
	        if (standplaats.getBetalers().size() > 0) {
	        	for (BetalerDTO betalerDTO : standplaats.getBetalers()) {
	        		hoofdBetaler = betalerDTO.getHoofdbetaler();
	        		betaler = betalerDTO.getBetaler();
	        		break;
	        	}
	        }
	        if (null != hoofdBetaler) {
	        	sBetaler += hoofdBetaler.getNaam() + " " + hoofdBetaler.getVoornaam();
	        	if (null != betaler) {
	        		sBetaler += "\n";
	        	}
	        }
	        if (null != betaler) {
	        	sBetaler += betaler.getNaam() + " " + betaler.getVoornaam();
	        }
	        PdfPCell cell = new PdfPCell(new Phrase(sBetaler, FontFactory.getFont(FontFactory.HELVETICA, 10)));
	        table.addCell(cell);
	        
	        if (standplaats.getFactuurDetails().size() != 1) {
		        table.addCell(new Phrase(""));
		        table.addCell(new Phrase(""));
		        table.addCell(new Phrase(""));
		        table.addCell(new Phrase(""));
		        table.addCell(new Phrase(""));
	        } else {
	        	for (FactuurDetailDTO factuurDetail :  standplaats.getFactuurDetails()) {
	        		table.addCell(new Phrase(df.format(factuurDetail.getBedrag()), FontFactory.getFont(FontFactory.HELVETICA, 10)));
	        		table.addCell(new Phrase(df.format(factuurDetail.getUitstelkosten()), FontFactory.getFont(FontFactory.HELVETICA, 10)));
	        		table.addCell(new Phrase(df.format(factuurDetail.getRappel()), FontFactory.getFont(FontFactory.HELVETICA, 10)));
	        		table.addCell(new Phrase(df.format(factuurDetail.getAndereKosten()), FontFactory.getFont(FontFactory.HELVETICA, 10)));
	        		PdfPCell cellOpmerking = new PdfPCell(new Phrase(factuurDetail.getOpmerking()));
	        		table.addCell(cellOpmerking);
	        	}
	        }

		}
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
	
}
