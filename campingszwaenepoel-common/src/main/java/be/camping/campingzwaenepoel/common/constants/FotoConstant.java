package be.camping.campingzwaenepoel.common.constants;

import java.util.ArrayList;
import java.util.List;

public class FotoConstant {

	private static boolean importBusy;

	public static boolean isImportBusy() {
		return importBusy;
	}

	public static void setImportBusy(boolean importBusy) {
		FotoConstant.importBusy = importBusy;
	}
	
	private static List<String> importFotoErrors = new ArrayList<String>();

	public static List<String> getImportFotoErrors() {
		return importFotoErrors;
	}

	public static void setImportFotoErrors(List<String> importFotoErrors) {
		FotoConstant.importFotoErrors = importFotoErrors;
	}

	public static void resetImportFotoErrors() {
		setImportFotoErrors(new ArrayList<String>());
	}
	
	public static void addErrorToFotoImport(String error) {
		getImportFotoErrors().add(error);
	}

	private static boolean prijsAanpassingBusy;

	public static boolean isPrijsAanpassingBusy() {
		return prijsAanpassingBusy;
	}

	public static void setPrijsAanpassingBusy(boolean prijsAanpassingBusy) {
		FotoConstant.prijsAanpassingBusy = prijsAanpassingBusy;
	}
	
	private static boolean quitProgram;

	public static Boolean getQuitProgram() {
		return quitProgram;
	}

	public static void setQuitProgram(Boolean quitProgram) {
		FotoConstant.quitProgram = quitProgram;
	}
	
	private final static String IMPORTED_FOTOS = "foto_import";

	public static String getImportedFotos() {
		return IMPORTED_FOTOS;
	}
	
	private static final String IMPORT_DONE = "import_done";

	public static String getImportDone() {
		return IMPORT_DONE;
	}

	private static final String IMPORT_DIRECTORY = "IMPORT_DIRECTORY";

	public static String getImportDirectory() {
		return IMPORT_DIRECTORY;
	}
}
