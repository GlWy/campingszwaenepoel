package be.camping.campingzwaenepoel.service.foto;

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;


public interface FotoService {

	public boolean importFoto(String importPath, String photoPath);
	
	public boolean remove(String directory, List<String> fotopaden);
	
	public boolean isImportBusy();
	
	public List<Map<String, Object>> findPhotosWithRemarks(String path);
}
