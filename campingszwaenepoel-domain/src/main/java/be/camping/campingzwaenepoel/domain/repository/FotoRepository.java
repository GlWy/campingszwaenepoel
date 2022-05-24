package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;
import java.util.Map;

public interface FotoRepository {

	boolean store(String sourcePath, String targetPath, String standplaatsNaam);
	
	boolean remove(String path, String foto);
	
	List<Map<String, Object>> findFotosVoorFacturatie(String path);
	
}
