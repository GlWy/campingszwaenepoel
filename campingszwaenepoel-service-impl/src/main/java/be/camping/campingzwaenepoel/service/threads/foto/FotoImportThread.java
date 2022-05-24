package be.camping.campingzwaenepoel.service.threads.foto;

import java.io.File;

import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.common.error.ErrorCode;
import be.camping.campingzwaenepoel.common.utils.FileUtils;
import be.camping.campingzwaenepoel.common.utils.ImageUtils;
import be.camping.campingzwaenepoel.domain.model.Configuratie;
import be.camping.campingzwaenepoel.domain.repository.ConfiguratieRepository;
import be.camping.campingzwaenepoel.domain.repository.FotoRepository;
import be.camping.campingzwaenepoel.infrastructure.filesystem.FotoRepositoryFileSystem;

public class FotoImportThread extends Thread {

	private String sourcePath;
	private String photoDir;
	
	private FotoRepository fotoRepository = new FotoRepositoryFileSystem();
			
	public FotoRepository getFotoRepository() {
		return fotoRepository;
	}

	public void setFotoRepository(FotoRepository fotoRepository) {
		this.fotoRepository = fotoRepository;
	}

	private ConfiguratieRepository configuratieRepository;
	
	public FotoImportThread(String sourcePath, String photoDir, 
			ConfiguratieRepository configuratieRepository) {
		this.sourcePath = sourcePath;
		this.photoDir = photoDir;
		this.configuratieRepository = configuratieRepository;
	    this.start();
	}
	
	public void run() {
		String[] files = null;
		File dir = new File(sourcePath);
		if (!dir.isDirectory()) {
			FotoConstant.addErrorToFotoImport(ErrorCode.FOTO_INPUT_DIRECTORY_NO_DIR);
			FotoConstant.setImportBusy(false);
		} else {
			files = dir.list();
		}
		if (null != files) {
	        try {
				
				for (String path : files) { // path of photo's that need to be copied
					
					if (FotoConstant.getQuitProgram()) {
						FotoConstant.setImportBusy(false);
						continue;
					}
					
					if (!ImageUtils.isImage(path)) continue; // || !isCorrectFormat(FileUtils.getName(path))) continue;

		    		String standplaatsVolNaam = null;
		    		if (path.contains("-")) {
		    			standplaatsVolNaam = path.split("-")[0];
		    		} else { // hoofd foto
		    			standplaatsVolNaam = FileUtils.getName(new File(path).getName());
		    		}
	    			try {
	    				Integer.parseInt(standplaatsVolNaam.substring(0, 1));
	    				while (standplaatsVolNaam.length() < 3) {
	    					standplaatsVolNaam = "0" + standplaatsVolNaam;
	    				}
	    				standplaatsVolNaam = "H" + standplaatsVolNaam;
	    			} catch (Exception e) {
	    				
	    			}
		    		
		        	Configuratie configuratie = configuratieRepository.findByNaam(FotoConstant.getImportedFotos());
					
		    		if (configuratie.getWaarde().contains(path)) {
		    			continue;
		    		}
		    		
			        try {
			        	File file = new File(photoDir + File.separator + "standplaats" + File.separator + standplaatsVolNaam);
			        	if (!configuratie.getWaarde().contains(standplaatsVolNaam) && file.exists()) {
			        		FileUtils.deleteFolder(file);
			        	}
			        	if (null != file) {
			        		getFotoRepository().store(sourcePath + File.separator + path, photoDir, standplaatsVolNaam);
			        	}
			    		System.gc();
			        	configuratie.setWaarde(configuratie.getWaarde() + path);
			        	configuratieRepository.store(configuratie);
			    		Thread.sleep(100);
					} catch (InterruptedException e) {
						FotoConstant.addErrorToFotoImport(ErrorCode.FOTO_IMPORT_INTERRUPTED);
						e.printStackTrace();
					} catch (Exception e) {
						FotoConstant.addErrorToFotoImport(ErrorCode.FOTO_IMPORT_INTERRUPTED);
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				FotoConstant.addErrorToFotoImport("Exception caught while importing errors.");
				e.printStackTrace();
			} finally {
				FotoConstant.setImportBusy(false);
			}
		}
		
		if (!FotoConstant.getQuitProgram()) {
			Configuratie configuratie = configuratieRepository.findByNaam(FotoConstant.getImportDone());
			configuratie.setWaarde("true");
			configuratieRepository.store(configuratie);
			configuratie = configuratieRepository.findByNaam(FotoConstant.getImportedFotos());
			configuratie.setWaarde("");
			configuratieRepository.store(configuratie);
		}
	}
	
}
