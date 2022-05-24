package be.camping.campingzwaenepoel.presentation;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.presentation.ui.panels.PersoonPanel;
import be.camping.campingzwaenepoel.presentation.ui.panels.mainpanel.Header;
import be.camping.campingzwaenepoel.presentation.ui.panels.mainpanel.MainNavigationpanel;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.KasbonDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class Controller {
	
	private Header header;
	private MainNavigationpanel mainNavigationpanel;
	private PersoonPanel persoonPanel;
	private List<String> personen;

	@Autowired
	private StandplaatsService standplaatsService;

	@Autowired
	private PersoonService persoonService;

	public StandplaatsDTO getStandplaatsDTO() {
		return getHeader().getStandplaatsDTO();
	}
	
	public void setStandplaatsDTO(StandplaatsDTO standplaatsDTO) {
		getHeader().setStandplaatsDTO(standplaatsDTO);
	}
	
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public MainNavigationpanel getMainNavigationpanel() {
		return mainNavigationpanel;
	}

	public void setMainNavigationpanel(MainNavigationpanel mainNavigationpanel) {
		this.mainNavigationpanel = mainNavigationpanel;
	}

	public PersoonPanel getPersoonPanel() {
		return persoonPanel;
	}

	public void setPersoonPanel(PersoonPanel persoonPanel) {
		this.persoonPanel = persoonPanel;
	}

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}

	public void setStandplaatsService(StandplaatsService standplaatsService) {
		this.standplaatsService = standplaatsService;
	}

	public List<String> getPersonen() {
		return personen;
	}

	public void setPersonen(List<String> personen) {
		this.personen = personen;
	}

	public void updateDataForPanel(boolean ok, int panel) {
		int currentPanel = getMainNavigationpanel().getJTabbedPane().getSelectedIndex();
		if (currentPanel == panel) {
			getHeader().setTabNameColor(ok);
			getMainNavigationpanel().setTabColor(ok);
		}
	}

	public void updateDataForHeader() {
		getMainNavigationpanel().checkDataAndUpdateTabInformation();
	}
	
	public void updateDataForTabbedPane(String tabname, boolean ok) {
		if (tabname == null) {
			getHeader().setTabNameColor(ok);
		} else {
			getHeader().setTabName(tabname, ok);
		}
	}
	
	public void checkDataSaved() {
		getMainNavigationpanel().checkTabSaved(true);
	}
	
	public void setBetalersInHeader(String hoofdBetaler, String betaler) {
		getHeader().setBetalersInHeader(hoofdBetaler, betaler);
	}

	public void setTabInMainpanel(int index, SoortHuurderEnum soortHuurder) {
		getMainNavigationpanel().setTab(index, soortHuurder);
	}
	
	public void setPersoonInPersoonPanel(int persoonId) {
		getMainNavigationpanel().setTab(5, null);
		getPersoonPanel().setPersoonInGui(persoonId);
	}
	
	public void setPersoonInPersoonPanel(PersoonDTO persoon) {
		getPersoonPanel().setPersoonInGui(persoon);
	}
	
	public void setKasbonInKassaPanel(KasbonDTO kasbon) {
		getMainNavigationpanel().setTab(0, null);
		getMainNavigationpanel().setKasbonInKassaPanel(kasbon);
	}
	
	public void vernieuwInschrijving(int inschrijvingId) {
		getMainNavigationpanel().setTab(5, null);
		getPersoonPanel().vernieuwInschrijving(inschrijvingId);
	}

	public void setStandplaatsEnTabVoorZoek(String standplaats, int tab) {
		getHeader().zoekStandplaats(standplaats);
		getMainNavigationpanel().setTab(tab, null);
	}
	
	public Date getTimeFromKassa() {
		return getMainNavigationpanel().getKassaPanel().getjDpDatum().getTime();
	}
	
	public void resetHoofdfoto() {
		getHeader().setPhotoInStandplaats();
	}
}