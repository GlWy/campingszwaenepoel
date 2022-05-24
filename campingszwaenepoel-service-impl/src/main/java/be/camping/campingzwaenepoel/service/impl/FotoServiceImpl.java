package be.camping.campingzwaenepoel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.domain.repository.ConfiguratieRepository;
import be.camping.campingzwaenepoel.domain.repository.FotoRepository;
import be.camping.campingzwaenepoel.domain.repository.StandplaatsRepository;
import be.camping.campingzwaenepoel.service.foto.FotoService;
import be.camping.campingzwaenepoel.service.threads.foto.FotoImportThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("FotoService")
@Transactional
public class FotoServiceImpl implements FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private StandplaatsRepository standplaatsRepository;

	@Autowired
	private ConfiguratieRepository configuratieRepository;
	
	@Override
	public boolean importFoto(String importPath, String photoPath) {
		FotoConstant.resetImportFotoErrors();
		FotoConstant.setImportBusy(true);
		new FotoImportThread(importPath, photoPath, configuratieRepository);
		return true;
	}

	@Override
	public boolean isImportBusy() {
		return FotoConstant.isImportBusy();
	}

	@Override
	public boolean remove(String directory, List<String> fotopaden) {
		for (String s : fotopaden) {
			fotoRepository.remove(directory, s);
		}
		return false;
	}

	/**
	 * 1. retrieve photo path
	 * 2. retrieve remarks
	 * 3. merge
	 */
	@Override
	public List<Map<String, Object>> findPhotosWithRemarks(String path) {
		List<Map<String, Object>> fotos = fotoRepository.findFotosVoorFacturatie(path);
		List<Object[]> opmerkingen = standplaatsRepository.findOpmerkingenVoorFacturatie();
		for (Object[] o : opmerkingen) {
			// check if remark is not just a blank space
			String sTmp = (String)o[2];
			sTmp = sTmp.trim();
			if (StringUtils.isEmpty(sTmp)) continue;
			
			String s = "";
			if (!StringUtils.isEmpty((String)o[0])) {
				s = (String)o[0];
			}
			if (o[1] instanceof java.lang.Integer) {
				String sTemp = o[1].toString();
				while (sTemp.length() < 3) {
					sTemp = "0" + sTemp;
				}
				s += sTemp;
			}
			boolean found = false;
			for (Map<String, Object> map : fotos) {
				if (s.equals(map.get(Constant.STANDPLAATS))) {
					map.put(Constant.OPMERKING, o[2]);
					found = true;
					break;
				}
			}
			if (!found) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Constant.STANDPLAATS, s);
				map.put(Constant.OPMERKING, o[2]);
				fotos.add(map);
			}
		}
		return fotos;
	}
}