package be.camping.campingzwaenepoel.service;

import be.camping.campingzwaenepoel.service.transfer.GebruikerDTO;

public interface AuthenticationService {
	
	public GebruikerDTO login (String username, String password);

}
