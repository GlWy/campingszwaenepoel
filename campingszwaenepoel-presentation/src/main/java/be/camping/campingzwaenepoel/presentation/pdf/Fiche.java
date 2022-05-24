package be.camping.campingzwaenepoel.presentation.pdf;

import java.awt.Color;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;

import be.camping.campingzwaenepoel.common.enums.GezinsPositieEnum;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingDTO;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingPersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.WagenDTO;

public class Fiche {

    // Fiche headers
    private final String fiche1 = "Karte-Fiche";
    private final String fiche2 = "Karte-form";
    private String nummer = "N�";
    private final String headerNL = "KONTROLE VAN REIZIGERS OP KAMPEERTERREINEN";
    private final String headerFR = "CONTROLE DES VOYAGEURS DANS LES CAMPINGS";
    private final String headerDU = "AUFSICHT UBER DIE REISENDEN IN CAMPING";
    private final String headerEng = "INSPECTION OF TRAVELLERS IN THE CAMPING";

    // camping data
    private final String camping = "N.V. CAMPINGS ZWAENEPOEL";
    private final String camping2 = "HOOFDZETEL - BUREAU CENTRALE";
    private final String campingAdres = "Vosseslag 20/0005 - 8420 DE HAAN";
    private final String campingContact = "TEL: 059/23.32.50 - WACHTDIENST: 059/77.07.07";
    private final String campingContact2 = "FAX: 059/23.66.37 - E-mail: camping.zwaenepoel@telenet.be";

    // inschrijvingsgegevens
    private final String aankomst = "Datum van aankomst: ";
    private final String vertrek = "Werkelijke vertrekdatum (3): ";
    private String aankomstDatum = "";
    private String vertrekDatum = "";
    private final String aanwezigen = "Aanwezige mensen:";
    private String hoofd = "Gezinshoofd:";
    private String naam = "";
    private String voornaamHoofd = "";
    private String voornaamEcht = "";
    private String voornaamKind = "";
    private final List<String> namenMedeHuurders = new ArrayList<String>();
    private String woonplaats = "";
    private String straat = "";
    private String huisnummer = "";
    private String postcode = "";
    private String nrplaat = "";
    private String echtgenote = "";
    private String aantalOngehKind = "";
    private final String uitleg12 = "(1) In drukletters - (2) De juistheid van die inlichting moet door de exploitant niet worden nagegaan";
    private final String uitleg3 = "(3) Alleen te vermelden op het dubbel van de kaart";
    private final String bevestiging1 = "Ik ben op de hoogte van de campingregels";
    private final String bevestiging2 = "en ik teken voor akkoord.";
    private final String bevestiging1Tr = "Je suis au courant de règles de camping";
    private final String bevestiging2Tr = "et je signe pour accord.";
    private String standplaats = "";
    private final String NUMMER_WACHTDIENST = "059/77.07.07";

    private final int Y_OFFSET = -5;

    public Fiche(final SoortHuurderEnum soortHuurderEnum) {
        if (SoortHuurderEnum.LOS.equals(soortHuurderEnum)) {
            hoofd = "Verantwoordelijke:";
        }
    }

    public void createDocument(String standplaats, InschrijvingDTO inschrijving, String printerZwaarNaam,
                               String printerLichtNaam, String computerNamesForPrinting) throws DocumentException, IOException,
            PrinterException {
        File tmpDir = new File("/tmp");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        String name = "/tmp/fiche" + System.nanoTime() + ".pdf";
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name));
        document.open();

        PdfContentByte cb = writer.getDirectContent();

        // set data
        setData(standplaats, inschrijving);

        if (inschrijving.getSoorthuurder().equals(SoortHuurderEnum.LOS)) {
            createDataLos(cb, inschrijving);
        } else if (inschrijving.getSoorthuurder().equals(SoortHuurderEnum.VAST)) {
            createDataVast(document, cb, standplaats, inschrijving);
        }
        document.close();

        if (computerNamesForPrinting != null && computerNamesForPrinting.contains(inschrijving.getComputer())
                && !StringUtils.isEmpty(printerZwaarNaam) && !StringUtils.isEmpty(printerLichtNaam)) {

            PDDocument pdDocument = null;
            try {
                pdDocument = PDDocument.load(new File(name));

                String printerName = SoortHuurderEnum.LOS.equals(inschrijving.getSoorthuurder()) ? printerLichtNaam : printerZwaarNaam;

                PrintService myPrintService = findPrintService(printerName);

                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPageable(new PDFPageable(pdDocument));
                job.setPrintService(myPrintService);
                job.print();
            } catch (Exception e) {
                Executable.openDocument(name);
            } finally {
                if (pdDocument != null) {
                    pdDocument.close();
                }
            }
        } else {
            Executable.openDocument(name);
        }
    }

    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }

    private void createDataVast(Document document, PdfContentByte cb, String standplaats, InschrijvingDTO inschrijving)
            throws DocumentException, IOException {

        int centerOffset = 240;
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        boolean isLeftBoxWriten = false;
        boolean isFirstPageWritten = false;

        for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
            if ( inschrijvingPersoon.getPersoon().getWagens() != null && !inschrijvingPersoon.getPersoon().getWagens().isEmpty()) {

                isLeftBoxWriten = true;

                for (WagenDTO wagen : inschrijvingPersoon.getPersoon().getWagens()) {
                    if (isFirstPageWritten) {
                        document.newPage();
                    } else {
                        isFirstPageWritten = true;
                    }

                    // right box
                    insertTextInBox(cb, 517, inschrijving.getSoorthuurder());
                    // writeLeftBox(cb, inschrijving);

                    cb.beginText();
                    cb.setColorFill(Color.BLUE);

                    BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    cb.setFontAndSize(bf, 80);
                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, standplaats, centerOffset, 400, 0);
                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, year, centerOffset, 200, 0);

                    bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    cb.setFontAndSize(bf, 70);
                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, wagen.getNummerplaat(), centerOffset, 300, 0);

                    // draw boxes
                    drawBox(cb, 520, Y_OFFSET);
                    drawBox(cb, 557, Y_OFFSET + 40, 778, Y_OFFSET + 170, Color.RED);
                    drawBox(cb, centerOffset - 200, 360, centerOffset + 237, 270, Color.BLUE);

                    cb.endText();
                }
            }
        }

        if (!isLeftBoxWriten) {
            writeLeftBox(cb, inschrijving);
        }
    }

    private void createDataLos(PdfContentByte cb, InschrijvingDTO inschrijving) throws DocumentException, IOException {
        // left box
        writeLeftBox(cb, inschrijving);

        // right box
        insertTextInBox(cb, 520, inschrijving.getSoorthuurder());

        // center box
        insertTextInCentre(cb, 320);

        // draw boxes
        drawBox(cb, 520, Y_OFFSET - 5);
        drawBox(cb, 560, Y_OFFSET + 60, 780, Y_OFFSET + 170, Color.RED);
    }

    private void writeLeftBox(PdfContentByte cb, InschrijvingDTO inschrijving) throws DocumentException, IOException {
        // left box
        drawBox(cb, 10, Y_OFFSET - 5);
        insertTextInBox(cb, 0, inschrijving.getSoorthuurder());
        drawBox(cb, 40, Y_OFFSET + 60, 260, Y_OFFSET + 170, Color.RED);
    }

    private void drawBox(PdfContentByte cb, int x1, int y1) {
        drawBox(cb, x1, y1 + 20, x1 + 310, y1 + 595);
    }

    private void drawBox(PdfContentByte cb, int x1, int y1, int x2, int y2) {
        cb.setLineWidth(1f);
        cb.setColorStroke(Color.lightGray);
        cb.moveTo(x1, y1);
        cb.lineTo(x2, y1);
        cb.lineTo(x2, y2);
        cb.lineTo(x1, y2);
        cb.lineTo(x1, y1);
        cb.stroke();
    }

    private void drawBox(PdfContentByte cb, int x1, int y1, int x2, int y2, Color color) {
        cb.setLineWidth(1f);
        cb.setColorStroke(color);
        cb.moveTo(x1, y1);
        cb.lineTo(x2, y1);
        cb.lineTo(x2, y2);
        cb.lineTo(x1, y2);
        cb.lineTo(x1, y1);
        cb.stroke();
    }

    private void drawLine(PdfContentByte cb, int x1, int y1, int x2, int y2) {
        cb.setLineWidth(1f);
        cb.setColorStroke(Color.lightGray);
        cb.moveTo(x1, y1);
        cb.lineTo(x2, y2);
        cb.stroke();
    }

    private void setData(String plaats, InschrijvingDTO inschrijving) {
        standplaats = plaats;
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        aankomstDatum = formatter.format(inschrijving.getDateVan());
        vertrekDatum = formatter.format(inschrijving.getDateTot());
        if (inschrijving.getFichenummer() > 0) {
            nummer += inschrijving.getFichenummer();
        } else {
            nummer = "";
        }
        for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
            if (GezinsPositieEnum.HOOFD.equals(inschrijvingPersoon.getGezinsPositie())) {
                naam = inschrijvingPersoon.getPersoon().getNaam();
                voornaamHoofd = inschrijvingPersoon.getPersoon().getVoornaam();
                if (inschrijvingPersoon.getPersoon().getAdresDTO().getStraat() != null)
                    straat = inschrijvingPersoon.getPersoon().getAdresDTO().getStraat();
                if (inschrijvingPersoon.getPersoon().getAdresDTO().getHuisnummer() != null)
                    huisnummer = inschrijvingPersoon.getPersoon().getAdresDTO().getHuisnummer();
                if (!StringUtils.isEmpty(inschrijvingPersoon.getPersoon().getAdresDTO().getBus())) {
                    huisnummer += " - " + inschrijvingPersoon.getPersoon().getAdresDTO().getBus();
                }
                if (inschrijvingPersoon.getPersoon().getAdresDTO().getPlaats() != null)
                    woonplaats = inschrijvingPersoon.getPersoon().getAdresDTO().getPlaats();
                if (inschrijvingPersoon.getPersoon().getAdresDTO().getPostcode() != null)
                    postcode = inschrijvingPersoon.getPersoon().getAdresDTO().getPostcode();
                for (WagenDTO wagen : inschrijvingPersoon.getPersoon().getWagens()) {
                    nrplaat = wagen.getNummerplaat();
                    break;
                }
                break;
            }
        }
        if (SoortHuurderEnum.VAST.equals(inschrijving.getSoorthuurder())) {
            for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
                if (GezinsPositieEnum.ECHTGENOTE.equals(inschrijvingPersoon.getGezinsPositie())) {
                    echtgenote = inschrijvingPersoon.getPersoon().getNaam();
                    voornaamEcht = inschrijvingPersoon.getPersoon().getVoornaam();
                    break;
                }
            }
        }
        int aantalkinderen = 0;
        List<String> tmpNamen = new ArrayList<>();

        for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
            if (GezinsPositieEnum.HOOFD.equals(inschrijvingPersoon.getGezinsPositie())
                    || (GezinsPositieEnum.ECHTGENOTE.equals(inschrijvingPersoon.getGezinsPositie()) && SoortHuurderEnum.VAST
                    .equals(inschrijving.getSoorthuurder()))) {
                continue;
            }
            if (aantalkinderen > 0) {
                voornaamKind += ", ";
            }
            voornaamKind += inschrijvingPersoon.getPersoon().getVoornaam();
            tmpNamen.add(inschrijvingPersoon.getPersoon().getVoornaam() + " "
                    + inschrijvingPersoon.getPersoon().getNaam());
            aantalkinderen++;
            // }
        }

        prepareListNamen(tmpNamen);

        if (aantalkinderen > 0) {
            aantalOngehKind = Integer.toString(aantalkinderen);
        } else {
            aantalOngehKind = "GEEN KINDEREN";
        }
    }

    private void prepareListNamen(final List<String> tmpNamen) {
        String line = "";
        for (final String naam : tmpNamen) {
            if (line.length() > 0) {
                line += ", ";
            }
            line += naam;
            final int index = tmpNamen.indexOf(naam);
            if (index == 0 || index == tmpNamen.size() - 1 || index % 2 == 0) {
                namenMedeHuurders.add(line);
                line = "";
            }
        }
    }

    private void insertTextInCentre(PdfContentByte cb, int offset) throws DocumentException, IOException {
        drawBox(cb, offset + 10, Y_OFFSET + 560, offset + 130, Y_OFFSET + 590);
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.setFontAndSize(bf, 18);
        cb.beginText();
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, standplaats, 65 + offset, Y_OFFSET + 567, 0);
        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.setFontAndSize(bf, 10);
        offset += 25;

        cb.setColorFill(Color.RED);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "LISPANNE", offset + 60, 482, 0);
        cb.setColorFill(Color.BLACK);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "VOSSESLAG 20", offset, 458, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "8420 DE HAAN", offset, 442, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "059/23.39.75", offset, 428, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "WACHTDIENST: " + NUMMER_WACHTDIENST, offset, 414, 0);
        drawLine(cb, offset, 412, offset + 75, 412);

        cb.setColorFill(Color.RED);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "CAPRI", offset + 60, 375, 0);
        cb.setColorFill(Color.BLACK);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "DRIFTWEG 157", offset, 351, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "8420 DE HAAN", offset, 335, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "059/23.45.45.", offset, 319, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "WACHTDIENST: " + NUMMER_WACHTDIENST, offset, 303, 0);
        drawLine(cb, offset, 301, offset + 75, 301);

        cb.setColorFill(Color.RED);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "HIPPODROOM", offset + 60, 260, 0);
        cb.setColorFill(Color.BLACK);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "DUINDOORNSTRAAT 25", offset, 236, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "8450 BREDENE", offset, 220, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "059/32.07.91", offset, 204, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "WACHTDIENST: " + NUMMER_WACHTDIENST, offset, 188, 0);
        drawLine(cb, offset, 186, offset + 75, 186);

        cb.endText();
    }

    private void insertTextInBox(PdfContentByte cb, int offset, SoortHuurderEnum soortHuurderEnum)
            throws DocumentException, IOException {
        int offsetXUitleg = 25;
        int offsetXData = 25;
        int offsetXRightAlign = 280;
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 7);
        // Karte - fiche
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, fiche1, 50 + offset, Y_OFFSET + 575, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, fiche2, 50 + offset, Y_OFFSET + 567, 0);

        // CONTROLE DES VOYAGEURS DAN LES CAMPINGS
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headerFR, 150 + offset, Y_OFFSET + 544, 0);
        // AUFSICHT UBER DIE REISENDEN IN CAMPING
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headerDU, 150 + offset, Y_OFFSET + 536, 0);
        // INSPECTION OF TRAVELLERS IN THE CAMPING
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headerEng, 150 + offset, Y_OFFSET + 528, 0);

        cb.setFontAndSize(bf, 9);
        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        // NV CAMPING ZWAENEPOEL
        cb.setColorFill(Color.RED);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, camping, 155 + offset, Y_OFFSET + 509, 0);
        cb.setColorFill(Color.BLACK);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, camping2, 155 + offset, Y_OFFSET + 497, 0);
        // Vosseslag 20 - 8420 De Haan
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, campingAdres, 155 + offset, Y_OFFSET + 485, 0);
        // Tel: 059/23 39 75 - Wachtdienst: 0477/28 37 96
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, campingContact, 155 + offset, Y_OFFSET + 473, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, campingContact2, 155 + offset, Y_OFFSET + 461, 0);

        cb.setFontAndSize(bf, 7);
        cb.setColorFill(Color.RED);

        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, bevestiging1, 150 + offset, 155, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, bevestiging2, 150 + offset, 145, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, bevestiging1Tr, 150 + offset, 70, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, bevestiging2Tr, 150 + offset, 60, 0);

        cb.setColorFill(Color.BLACK);

        drawLine(cb, 20 + offset, Y_OFFSET + 520, 300 + offset, Y_OFFSET + 520);
        drawLine(cb, 20 + offset, Y_OFFSET + 451, 300 + offset, Y_OFFSET + 451);

        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.setFontAndSize(bf, 9);
        // KONTROLE VAN REIZIGERS OP KAMPEERTERREINEN
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headerNL, 150 + offset, Y_OFFSET + 552, 0);

        cb.setFontAndSize(bf, 8);
        // Aankomst
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, aankomst, offsetXData + offset, Y_OFFSET + 438, 0);
        // aankomst datum
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, aankomstDatum, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 438, 0);
        // Vertrek
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, vertrek, offsetXData + offset, Y_OFFSET + 420, 0);
        // vertrek datum
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, vertrekDatum, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 420, 0);
        // Nummerplaat van het voertuig (3): :
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Nummerplaat van het voertuig (2)", offsetXData + offset,
                Y_OFFSET + 402, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, nrplaat, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 402, 0);
        // Straat
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Straat: ", offsetXData + offset, Y_OFFSET + 382, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, straat, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 382, 0);
        // Nummer
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Nummer: ", offsetXData + offset, Y_OFFSET + 364, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, huisnummer, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 364, 0);
        // postcode
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Postcode: ", offsetXData + offset, Y_OFFSET + 346, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, postcode, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 346, 0);
        // plaats
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Woonplaats: ", offsetXData + offset, Y_OFFSET + 328, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, woonplaats, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 328, 0);

        // Aanwezige mensen
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, aanwezigen, offsetXData + offset, Y_OFFSET + 307, 0);
        // Hoofd:
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, hoofd, offsetXData + 10 + offset, Y_OFFSET + 291, 0);
        // Naam
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Naam (1): ", offsetXData + 10 + offset, Y_OFFSET + 273, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, naam, offsetXData + offset + offsetXRightAlign, Y_OFFSET + 273,
                0);
        // Voornaam
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Voornamen: ", offsetXData + 10 + offset, Y_OFFSET + 255, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, voornaamHoofd, offsetXData + offset + offsetXRightAlign,
                Y_OFFSET + 255, 0);
        if (SoortHuurderEnum.VAST.equals(soortHuurderEnum)) {
            // Echtgenote - meisjesnaam (1):
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Echtgenote - meisjesnaam (1): ", offsetXData + 10 + offset,
                    Y_OFFSET + 238, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, echtgenote, offsetXData + offset + offsetXRightAlign,
                    Y_OFFSET + 238, 0);
            // Voornamen:
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Voornaam: ", offsetXData + 10 + offset, Y_OFFSET + 220, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, voornaamEcht, offsetXData + offset + offsetXRightAlign,
                    Y_OFFSET + 220, 0);
            // Aantal ongehuwde kinderen: GEEN KINDEREN
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Aantal ongehuwde kinderen: ", offsetXData + 10 + offset,
                    Y_OFFSET + 202, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, aantalOngehKind, offsetXData + offset + offsetXRightAlign,
                    Y_OFFSET + 202, 0);
            // Voornaam
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Voornamen: ", offsetXData + 10 + offset, Y_OFFSET + 184, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, voornaamKind, offsetXData + offset + offsetXRightAlign,
                    Y_OFFSET + 184, 0);
        } else {
            // Namen mede huurders
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "mede huurders: ", offsetXData + 10 + offset, Y_OFFSET + 238,
                    0);
            int yOffset = Y_OFFSET + 238;
            for (final String naam : namenMedeHuurders) {
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, naam, offsetXData + offset + offsetXRightAlign, yOffset,
                        0);
                yOffset -= 18;
            }
        }

        // nummer N�26542
        cb.setFontAndSize(bf, 18);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nummer, 90 + offset, Y_OFFSET + 567, 0);
        // Standplaats
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, standplaats, 190 + offset, Y_OFFSET + 567, 0);

        cb.setFontAndSize(bf, 6);
        // (1) In drukletters - (2) De juistheid van die inlichting moet door de
        // exploitant niet worden nagegaan
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, uitleg12, offsetXUitleg + offset, 30, 0);
        // (3) Alleen te vermelden op het dubbel van de kaart"
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, uitleg3, offsetXUitleg + offset, 22, 0);

        cb.endText();
    }
}