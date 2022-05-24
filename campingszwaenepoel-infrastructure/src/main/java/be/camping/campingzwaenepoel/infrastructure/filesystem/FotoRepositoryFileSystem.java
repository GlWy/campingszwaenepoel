package be.camping.campingzwaenepoel.infrastructure.filesystem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.utils.FileUtils;
import be.camping.campingzwaenepoel.common.utils.ImageUtils;
import be.camping.campingzwaenepoel.domain.repository.FotoRepository;
import org.springframework.stereotype.Repository;

@Repository("FotoRepository")
public class FotoRepositoryFileSystem implements FotoRepository {

	@Override
	public boolean remove(String path, String foto) {
		// verwijder thumb
		File file = new File(path + File.separator + foto);
		if (!file.delete()) {
			System.err.println("removal failed of file: " + file.getAbsolutePath());
			return false;
		}
		
		// verwijder facturatie foto
		File fileFacturatie = new File(path + File.separator + "facturatie" + File.separator + foto);
		if (!fileFacturatie.delete()) {
			System.err.println("removal failed of file: " + fileFacturatie.getAbsolutePath());
			return false;
		}
		
		// verwijder popup foto (groot)
		File fileGroot = new File(path + File.separator + "groot" + File.separator + foto);
		if (!fileGroot.delete()) {
			System.err.println("removal failed of file: " + fileGroot.getAbsolutePath());
			return false;
		}
		
		// verwijder origineel
		File fileOrigineel = new File(path + File.separator + "origineel" + File.separator + foto);
		if (!fileOrigineel.delete()) {
			System.err.println("removal failed of file: " + fileOrigineel.getAbsolutePath());
			return false;
		}
				
		return true;
	}

	@Override
	public boolean store(String sourcePath, String targetPath, String standplaatsNaam) {

		File file = new File(sourcePath);
		String filename = FileUtils.getName(file);

		String outDir = targetPath + File.separator + "standplaats";
		File outputDir = new File(outDir);
		if (!outputDir.exists()) {
			if (!outputDir.mkdir()) {
				System.err.println("standplaats directory not created");
			}
		}
		outDir += File.separator + standplaatsNaam;
		outputDir = new File(outDir);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}

		if (filename.contains("-")) {
			outDir += File.separator + "foto";
		} else {
			outDir += File.separator + "hoofdfoto";
		}
		outputDir = new File(outDir);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}

		String grootDir = outDir + File.separator + "groot";
		outputDir = new File(grootDir);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}
		String origDir = outDir + File.separator + "origineel";
		outputDir = new File(origDir);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}

		String factuurDir = outDir + File.separator + "facturatie";
		if (filename.contains("-")) {
			outputDir = new File(factuurDir);
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}
		}

		BufferedInputStream bufferedinputstream = null;
		BufferedOutputStream bufferedoutputstream = null;

		try {
			String dest = grootDir + File.separator + file.getName();
			ImageUtils.scale(file.getAbsolutePath(), 933, 700, dest);

			dest = outDir + File.separator + file.getName();
			if (filename.contains("-")) {
				ImageUtils.scale(file.getAbsolutePath(), 380, 230, dest);
			} else {
				ImageUtils.scale(file.getAbsolutePath(), 138, 120, dest);
			}

			if (filename.contains("-")) {
				dest = factuurDir + File.separator + file.getName();
				ImageUtils.scale(file.getAbsolutePath(), 445, 290, dest);
			}

			File outputFileOrig = new File(origDir + File.separator + file.getName());
			bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
			bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(outputFileOrig));
			byte abyte0[] = new byte[8192];
			int i;
			while ((i = bufferedinputstream.read(abyte0)) != -1) {
				bufferedoutputstream.write(abyte0, 0, i);
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				bufferedinputstream.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				bufferedoutputstream.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	@Override
	public List<Map<String, Object>> findFotosVoorFacturatie(String path) {
		List<Map<String, Object>> fotos = new ArrayList<>();
		path += File.separator + "standplaats";
		File dir = new File(path);
		String[] standplaatsen = dir.list();
		for (String standplaats : standplaatsen) {
			String facturatieDir = path + File.separator + standplaats
					+ File.separator + "foto" + File.separator + "facturatie";
			File file = new File(facturatieDir);
			if (file.exists()) {
				List<String> fotosStandplaats = new ArrayList<String>();
				for (String s : file.list()) {
					if (ImageUtils.isImage(s))
						fotosStandplaats
								.add(facturatieDir + File.separator + s);
				}
				if (fotosStandplaats.size() > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(Constant.STANDPLAATS, standplaats);
					map.put(Constant.FOTOS, fotosStandplaats);
					fotos.add(map);
				}
			}
		}

		return fotos;
	}

}
