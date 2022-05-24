package be.camping.campingzwaenepoel.presentation.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.KassaAfrekeningDTO;
import be.camping.campingzwaenepoel.service.transfer.KassaArtikelAfrekeningDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

public class DagRapportPdf {

	private static DecimalFormat df = new DecimalFormat("0.00"); // @jve:decl-index=0:
	private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public static void createDocument(List<KassaAfrekeningDTO> kassaAfrekeningDTOs) {
		Document document = null;
		boolean b = false;
		File tmpDir = new File("/tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String name = "/tmp/dagrapport" + System.nanoTime() + ".pdf";
		try {
			document = new Document(PageSize.A4);
			PdfWriter writer = null;
			writer = PdfWriter.getInstance(document, new FileOutputStream(name));
			document.open();

			PdfContentByte cb = writer.getDirectContent();
			for (KassaAfrekeningDTO kassaAfrekeningDTO : kassaAfrekeningDTOs) {
				drawboxes(cb);
				vulDagRapportHeaderIn(cb, kassaAfrekeningDTO);
				vulDagRapportGegevensIn(cb, kassaAfrekeningDTO);
				b = true;
				if (!kassaAfrekeningDTO.equals(kassaAfrekeningDTOs.get(kassaAfrekeningDTOs.size() - 1))) document.newPage();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
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
				// Executable.printDocument(name);
			}
		}
	}

	private static void drawboxes(PdfContentByte cb) {
		drawBox(cb, 100, 750, 200, 770, null);
		drawBox(cb, 300, 750, 400, 770, null);
		drawBox(cb, 300, 720, 400, 740, null);
		drawBox(cb, 65, 460, 500, 690, Color.blue);
		drawLine(cb, 65, 670, 500, 670, Color.blue);
		drawLine(cb, 65, 605, 500, 605, Color.blue);
		drawLine(cb, 65, 585, 500, 585, Color.blue);
		drawLine(cb, 65, 565, 500, 565, Color.blue);
		drawLine(cb, 65, 485, 500, 485, Color.blue);
		drawLine(cb, 185, 460, 185, 690, Color.blue);
		drawLine(cb, 255, 460, 255, 690, Color.blue);
		drawLine(cb, 370, 460, 370, 690, Color.blue);
	}

	private static void vulDagRapportGegevensIn(PdfContentByte cb, KassaAfrekeningDTO kassaAfrekeningDTO)
			throws DocumentException, IOException {
		cb.beginText();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 11);

		if (kassaAfrekeningDTO.getDatum() != null) {
			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, formatter.format(kassaAfrekeningDTO.getDatum()), 348, 756, 0);
		}

		int offsetArtikel = 170;
		int offsetAantal = 200;
		int offsetEenheidsprijs = 345;
		int offsetTotaal = 470;
		int offsetArtikelY = 650;

		bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 11);

		int count = 1;

		for (KassaArtikelAfrekeningDTO kassaArtikelAfrekeningDTO : kassaAfrekeningDTO.getKassaArtikelAfrekeningDTOs()) {
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, kassaArtikelAfrekeningDTO.getNaam(), offsetArtikel,
					offsetArtikelY, 0);
			if (kassaArtikelAfrekeningDTO.getEenheidsprijs() > 0) {
				if (kassaArtikelAfrekeningDTO.isShowAllData()) {
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
							Integer.toString(kassaArtikelAfrekeningDTO.getAantal()), offsetAantal, offsetArtikelY, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
							"\u20ac " + df.format(kassaArtikelAfrekeningDTO.getEenheidsprijs()), offsetEenheidsprijs,
							offsetArtikelY, 0);
				}
				cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
						"\u20ac " + df.format(kassaArtikelAfrekeningDTO.getTotaal()), offsetTotaal, offsetArtikelY, 0);
			}
			offsetArtikelY -= 20;
			count++;
			if (count == 1)
				break;
		}

		bf = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "\u20ac " + df.format(kassaAfrekeningDTO.getTotaal()), 348, 726, 0);

		cb.endText();
	}

	private static void vulDagRapportHeaderIn(PdfContentByte cb, KassaAfrekeningDTO kassaAfrekeningDTO) throws DocumentException, IOException {
		cb.beginText();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.setFontAndSize(bf, 11);

		// Data in header
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kassaAfrekeningDTO.getCashSource().getTranslationKey(), 110, 755, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Dag:", 290, 755, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Totaal kassa:", 290, 725, 0);

		// offsets for columns
		int offsetArtikel = 170;
		int offsetAantal = 200;
		int offsetEenheidsprijs = 345;
		int offsetTotaal = 470;
		int offsetYHeader = 675;

		// titels in column
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Artikel", offsetArtikel, offsetYHeader, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Aantal", offsetAantal, offsetYHeader, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "E�nheidsprijs", offsetEenheidsprijs, offsetYHeader, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Totaal", offsetTotaal, offsetYHeader, 0);

		cb.endText();
	}

	private static void drawBox(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color) {
		cb.setLineWidth(1f);
		if (color != null)
			cb.setColorStroke(color);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y1);
		cb.lineTo(x2, y2);
		cb.lineTo(x1, y2);
		cb.lineTo(x1, y1);
		cb.stroke();
	}

	private static void drawLine(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color) {
		cb.setLineWidth(1f);
		if (color != null)
			cb.setColorStroke(color);
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y2);
		cb.stroke();
	}
}
