package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.Kasbon;

public interface KasbonRepository {

	public Kasbon findById(int id);
	
	public Kasbon findByKasbonNummer(int kasbonnummer);
	
	public Kasbon store(Kasbon kasbon);
	
	public void remove(Kasbon kasbon);
	
	public void removeAll();
	
	public List<Kasbon> getKasbons();
	
	public List<Kasbon> getKasbons(String computername);
}
