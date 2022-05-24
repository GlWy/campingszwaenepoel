package be.camping.campingzwaenepoel.service.impl;

import java.util.ArrayList;
import java.util.List;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisThema;
import be.camping.campingzwaenepoel.domain.repository.GeschiedenisThemaRepository;
import be.camping.campingzwaenepoel.service.GeschiedenisThemaService;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisThemaAssembler;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisThemaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("GeschiedenisThemaService")
@Transactional
public class GeschiedenisThemaServiceImpl implements GeschiedenisThemaService {

	@Autowired
	private GeschiedenisThemaRepository geschiedenisThemaRepository;

	@Override
	public GeschiedenisThemaDTO findGeschiedenisThema(int id) {
		GeschiedenisThemaAssembler assembler = new GeschiedenisThemaAssembler();
		GeschiedenisThema thema = geschiedenisThemaRepository.findById(id);
		return assembler.getGeschiedenisThemaDTO(thema);
	}

	@Override
	public void remove(GeschiedenisThemaDTO geschiedenisThemaDTO) {
		GeschiedenisThemaAssembler assembler = new GeschiedenisThemaAssembler();
		geschiedenisThemaRepository.remove(assembler.getGeschiedenisThema(geschiedenisThemaDTO));
	}

	@Override
	public GeschiedenisThemaDTO storeGeschiedenisThema(GeschiedenisThemaDTO geschiedenisThemaDTO) {
		GeschiedenisThemaAssembler assembler = new GeschiedenisThemaAssembler();
		GeschiedenisThema thema = geschiedenisThemaRepository.store(assembler.getGeschiedenisThema(geschiedenisThemaDTO));
		geschiedenisThemaDTO.setId(thema.getId());
		return geschiedenisThemaDTO;
	}

	@Override
	public List<GeschiedenisThemaDTO> findAllThemas() {
		GeschiedenisThemaAssembler assembler = new GeschiedenisThemaAssembler();
		List<GeschiedenisThemaDTO> themaDtos = new ArrayList<>();
		List<GeschiedenisThema> themas = geschiedenisThemaRepository.findAll();
		for (GeschiedenisThema thema : themas) {
			themaDtos.add(assembler.getGeschiedenisThemaDTO(thema));
		}
		return themaDtos;
	}

	@Override
	public GeschiedenisThemaDTO findGeschiedenisThema(String thema) {
		GeschiedenisThemaAssembler assembler = new GeschiedenisThemaAssembler();
		GeschiedenisThema geschiedenisThema = geschiedenisThemaRepository.findByThema(thema);
		return assembler.getGeschiedenisThemaDTO(geschiedenisThema);
	}
}