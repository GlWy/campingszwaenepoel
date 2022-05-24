package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.*;
import be.camping.campingzwaenepoel.common.utils.DateUtilities;
import be.camping.campingzwaenepoel.common.utils.StringUtilities;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.component.UpcaseFilter;
import be.camping.campingzwaenepoel.presentation.dialog.ConfirmPersonDialog;
import be.camping.campingzwaenepoel.presentation.dialog.SaveDialog;
import be.camping.campingzwaenepoel.presentation.dialog.ZoekPersonenKaartResultatenDialog;
import be.camping.campingzwaenepoel.presentation.dialog.ZoekPersonenResultatenDialog;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.listeners.RequestFocusListener;
import be.camping.campingzwaenepoel.presentation.pdf.Fiche;
import be.camping.campingzwaenepoel.presentation.pdf.PersoonPdf;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.*;
import be.camping.campingzwaenepoel.service.transfer.*;
import be.fedict.eid.applet.DiagnosticTests;
import be.fedict.eid.applet.Messages.MESSAGE_ID;
import be.fedict.eid.applet.Status;
import be.fedict.eid.applet.View;
import be.fedict.eid.applet.service.Address;
import be.fedict.eid.applet.service.Gender;
import be.fedict.eid.applet.service.Identity;
import be.fedict.eidviewer.gui.ViewerPrefs;
import be.fedict.eidviewer.gui.helper.ProxyUtils;
import be.fedict.eidviewer.lib.PCSCEid;
import be.fedict.eidviewer.lib.PCSCEidController;
import be.fedict.eidviewer.lib.TrustServiceController;
import com.lowagie.text.DocumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PersoonPanel extends JPanel implements PanelInterface, ActionListener, View, Observer {

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private ConfiguratieService configuratieService;

    private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PersoonPanel.class);

	private static final boolean isPersoonIdVisible = false;

	public PersoonService getPersoonService() {
		return persoonService;
	}

	public void setPersoonService(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	private JPanel personLeftPanel;
	private JPanel personCentralPanel;
	private JPanel personNorthPanel;
	private JPanel personSouthPanel;
	private JPanel buttonPanel;
	private JPanel buttonPanelPersoon;

	private JList jListPersonen = null;
	private JButton btnSave = null;
	private JButton btnSavePersoon = null;

	private JScrollPane jScrollPane = null;
	private JButton btnWisPersoon = null;
	private JButton btnWisRegistratie = null;
	private JButton btnSelect = null;
	private JButton btnKaartlezer = null;
	private JButton btnAfdrukkenpersoon = null;

	private JTextField txtNaam = null;
	private JLabel lblVoornaam = null;
	private JTextField txtVoornaam = null;
	private JRadioButton rbtnMan = null;
	private JRadioButton rbtnVrouw = null;
	private JLabel lblGeboortedatum = null;
	private JTextField txtGeboortePlaats = null;
	private JTextField txtLeeftijd = null;
	private JTextField txtStraat = null;
	private JPanel panelNummer = null;
	private JLabel lblNummer = null;
	private JTextField txtNummer = null;
	private JTextField txtBus = null;
	private JPanel panelBus = null;
	private JLabel lblPostcode = null;
	private JTextField txtPostcode = null;
	private JTextField txtPlaats = null;
	private JPanel panelGeslacht = null;
	private JDatePicker geboortedatumPicker = null;

	private JTextField txtEmail = null;
	private final JTextField txtNrPlaat = null;
	private JTextArea taOpmerking = null;
	private JComboBox cboPositie = null;
	private JTextField txtPersoonId = null;
	private JTextField txtIdentiteitskaart = null;
	private JLabel lblNationaliteit = null;
	private JTextField txtNationaliteit = null;
	private JLabel lblRijksregisternummer = null;
	private JTextField txtRijksregisternummer = null;

	private final Font font = new Font("Times New Roman", Font.PLAIN, 18);
	private final Font font2 = new Font("SansSerif", Font.BOLD, 18);
	private final Font fontButton = new Font("Times New Roman", Font.BOLD, 24);

	private final Dimension dimMinBtnPersoon = new Dimension(310, 42);

	private List<Integer> gezochtePersonen = null;
	private Controller controller;
	@Autowired
	private PersoonService persoonService;
	private ActionJListPersonen actionJListPersonen;
	private PersonFocusAdapter focusAdapter;

	private JScrollPane jScrollPaneTelefoon;
	private JList jListTelefoon;
	private DefaultListModel jListModelTelefoon = null;
	private JScrollPane jScrollPaneNummerplaat;
	private JList jListNummerplaat;
	private DefaultListModel jListModelNummerplaat = null;
	private JButton btnTelephone;
	private JButton btnRemoveTel;
	private JButton btnEditTel;
	private JButton btnNrPlaat;
	private JButton btnEditNrPlaat;
	private JButton btnRemoveNrPlaat;
	private JPanel jPanelTelephone;
	private JPanel jPanelNrPlaat;
	private JPanel jPanelNrPlaatGegevens;
	private JTextField jTxtNummerplaat;
	private JTextField jTxtMerk;
	private JTextField jTxtSticker;
	private JTextField jTxtOpmerking;

	private JPanel jPanelPostcode;
	private JPanel jPanelNationaliteit;
	private JPanel jPanelVoornaam;
	private JPanel jPanelGeboortedatum;
	private JPanel jPanelRijksregisternummer;

	private SoortHuurderEnum soortHuurder;
	private PersoonDTO persoon = DTOFactory.getPersoonDTO();

	private ConfiguratieDTO printerZwaarPapierConfiguratie;
	private ConfiguratieDTO printerLichtPapierConfiguratie;
	private ConfiguratieDTO computerNameConfiguratie;
	private String computerNamesForPrinting;

	Insets insets = new Insets(5, 0, 0, 5);

	private InschrijvingDTO inschrijving;
	private RegistratiePanel registratiePanel;

	private List<String> badges;
	private String badgenummer;
	private boolean zoekEncheckPersonExists = true;
	// private boolean zoekStandplaatsVoorPersoon = true;

	private ZoekPersonenListener zoekPersonenListener;

	private JPanel panelStandplaatsPhoto = null;

	private List<ContactgegevenDTO> contactGegevensToRemove;
	private List<WagenDTO> wagensToRemove;

	@Autowired
	private StandplaatsService standplaatsService;

	private PCSCEid eid;
	private PCSCEidController eidController;
	private TrustServiceController trustServiceController;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK, 1));
		actionJListPersonen = new ActionJListPersonen(null);
		zoekPersonenListener = new ZoekPersonenListener();
		focusAdapter = new PersonFocusAdapter();
		this.add(getPersonLeftPanel(), BorderLayout.WEST);
		this.add(getPersonCentralPanel(), BorderLayout.CENTER);
		setBadges(badgeService.getBadges());
		eid = new PCSCEid(this, ViewerPrefs.getLocale());
		eidController = new PCSCEidController(eid);
		trustServiceController = new TrustServiceController(ViewerPrefs.getTrustServiceURL());
		trustServiceController.start();
		setTrustServiceProxy();

		eidController.setTrustServiceController(trustServiceController);
		eidController.setAutoValidateTrust(ViewerPrefs.getIsAutoValidating());

		eidController.addObserver(this);

		eidController.start();

		initializePrinterConfiguration();
	}

	private void initializePrinterConfiguration() {
		printerZwaarPapierConfiguratie = configuratieService.getConfiguratie(Constant.PRINTER_ZWAAR_PAPIER);
		printerLichtPapierConfiguratie = configuratieService.getConfiguratie(Constant.PRINTER_LICHT_PAPIER);
		computerNameConfiguratie = configuratieService.getConfiguratie(Constant.COMPUTER_NAME);

		if (printerZwaarPapierConfiguratie == null) {
			printerZwaarPapierConfiguratie = new ConfiguratieDTO();
		}

		if (printerLichtPapierConfiguratie == null) {
			printerLichtPapierConfiguratie = new ConfiguratieDTO();
		}

		if (computerNameConfiguratie == null) {
			computerNameConfiguratie = new ConfiguratieDTO();
		}
	}

	private void setTrustServiceProxy() {
		Proxy proxyToUse = ViewerPrefs.getProxy();
		if (proxyToUse != Proxy.NO_PROXY)
			trustServiceController.setProxy(ProxyUtils.getHostName(proxyToUse), ProxyUtils.getPort(proxyToUse));
		else
			trustServiceController.setProxy(null, 0);
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean checkPersoonGewijzigd() {
		boolean ret = false;

		if (getPersoon() != null) {
			if (getPersoon().getId() == 0) {
				// person entered manually
				if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())) {
					return true;
				}
			} else {
				// person info is in GUI after selecting from the list or
				// entered via EID
				updatePersoonDataFromGUI();
				PersoonDTO persoonGUI = getPersoon();
				PersoonDTO persoonDB = persoonService.findPersoonById(persoonGUI.getId());
				if (!StringUtilities.equals(persoonGUI.getNaam(), persoonDB.getNaam())) {
					return true;
				}
				if (!StringUtilities.equals(persoonGUI.getVoornaam(), persoonDB.getVoornaam())) {
					return true;
				}
				if (!StringUtilities.equals(persoonGUI.getGeboorteplaats(), persoonDB.getGeboorteplaats())) {
					return true;
				}
				if (!DateUtilities.equals(persoonGUI.getGeboortedatum(), persoonDB.getGeboortedatum())) {
					return true;
				}
				if (!StringUtilities.equals(persoonGUI.getOpmerking(), persoonDB.getOpmerking())) {
					return true;
				}
				/*
				 * if(!StringUtilities.equals(persoonGUI.getGeslacht(). getTranslationKey(),
				 * persoonDB.getGeslacht().getTranslationKey())) { return true; }
				 */AdresDTO adresGUI = persoonGUI.getAdresDTO();
				AdresDTO adresDB = persoonDB.getAdresDTO();
				if (adresGUI.getId() != adresDB.getId()) {
					return true;
				}
				if (!StringUtilities.equals(adresGUI.getStraat(), adresDB.getStraat())) {
					return true;
				}
				if (!StringUtilities.equals(adresGUI.getHuisnummer(), adresDB.getHuisnummer())) {
					return true;
				}
				if (!StringUtilities.equals(adresGUI.getPostcode(), adresDB.getPostcode())) {
					return true;
				}
				if (!StringUtilities.equals(adresGUI.getPlaats(), adresDB.getPlaats())) {
					return true;
				}

				if (checkContactgegevensChanged(persoonGUI.getContactgegevens(), persoonDB.getContactgegevens())) {
					return true;
				}

				if (checkWagensChanged(persoonDB.getWagens(), persoonGUI.getWagens())) {
					return true;
				}
			}
		}

		return ret;
	}

	// private boolean checkContactgegevensChanged(Set<ContactgegevenDTO> cgs1, Set<ContactgegevenDTO> cgs2) {
	// boolean ret = true;
	// for (ContactgegevenDTO cg1 : cgs1) {
	// for (ContactgegevenDTO cg2 : cgs2) {
	// if (cg1.getId() == cg2.getId()) {
	// if ((!cg1.getContactgegevenType().equals(cg2.getContactgegevenType()))
	// || (!cg1.getWaarde().equals(cg2.getWaarde()))) {
	// ret = false;
	// }
	// break;
	// }
	// }
	// if (ret) {
	// break;
	// }
	// }
	// return ret;
	// }

	private boolean checkContactgegevensChanged(Set<ContactgegevenDTO> cgs1, Set<ContactgegevenDTO> cgs2) {
		boolean ret = false;
		if (cgs1.size() != cgs2.size()) {
			ret = true;
		} else {
			for (ContactgegevenDTO cg1 : cgs1) {
				for (ContactgegevenDTO cg2 : cgs2) {
					if (cg1.getId() == cg2.getId()) {
						if ((!cg1.getContactgegevenType().equals(cg2.getContactgegevenType()))
								|| (!cg1.getWaarde().equals(cg2.getWaarde()))) {
							ret = true;
						} else {
							cgs2.remove(cg2);
						}
						break;
					}
				}
				if (ret) {
					break;
				}
			}
			if (cgs2.size() > 0) {
				ret = true;
			}
		}
		return ret;
	}

	private boolean checkWagensChanged(Set<WagenDTO> wagens1, Set<WagenDTO> wagens2) {
		boolean ret = false;
		if (wagens1.size() != wagens2.size()) {
			ret = true;
		} else {
			for (WagenDTO wagen2 : wagens2) {
				for (WagenDTO wagen1 : wagens1) {
					if (wagen1.getId() == wagen2.getId()) {
						String merkWagen1 = (StringUtils.isEmpty(wagen1.getMerk())) ? "" : wagen1.getMerk();
						String merkWagen2 = (StringUtils.isEmpty(wagen2.getMerk())) ? "" : wagen2.getMerk();
						String nrplaatWagen1 = (StringUtils.isEmpty(wagen1.getNummerplaat())) ? "" : wagen1
								.getNummerplaat();
						String nrplaatWagen2 = (StringUtils.isEmpty(wagen2.getNummerplaat())) ? "" : wagen2
								.getNummerplaat();
						String opmWagen1 = (StringUtils.isEmpty(wagen1.getOpmerking())) ? "" : wagen1.getOpmerking();
						String opmWagen2 = (StringUtils.isEmpty(wagen2.getOpmerking())) ? "" : wagen2.getOpmerking();
						String stickerWagen1 = (StringUtils.isEmpty(wagen1.getSticker())) ? "" : wagen1.getSticker();
						String stickerWagen2 = (StringUtils.isEmpty(wagen2.getSticker())) ? "" : wagen2.getSticker();
						if ((!merkWagen1.equals(merkWagen2)) || (!nrplaatWagen1.equals(nrplaatWagen2))
								|| (!opmWagen1.equals(opmWagen2)) || (!stickerWagen1.equals(stickerWagen2))) {
							ret = true;
						} else {
							wagens1.remove(wagen1);
						}
						break;
					}
				}
				if (ret) {
					break;
				}
			}
			if (wagens1.size() > 0) {
				ret = true;
			}
		}
		return ret;
	}

	public void checkDataChanged(boolean b) {
		boolean persoonGewijzigd = checkPersoonGewijzigd();
		if (persoonGewijzigd) {
			if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())
					&& !StringUtils.isEmpty(getGeboortedatumPicker().getJFormattedTextField().getText())) {
				String message = "De gegevens van ";
				message += getTxtNaam().getText() + " " + getTxtVoornaam().getText();
				message += " werden gewijzigd. Wenst u deze wijzigingen op te slaan?";
				SaveDialog dialog = new SaveDialog(message);
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					savePersoon();
				}
				dialog.dispose();
			}
		}
	}

	@Override
	public boolean checkDataChanged() {
		return (getInschrijving() != null);
	}

	@Override
	public boolean checkDataForParent() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface #getDataFromGUI()
	 */
	@Override
	public Object getDataFromGUI() {
		StandplaatsDTO standplaats = getController().getStandplaatsDTO();
		standplaats.getInschrijvingen().add(getInschrijving());
		return standplaats;
	}

	private void updatePersoonDataFromGUI() {
		/**
		 * algemene persoonsgegevens
		 */
		getPersoon().setNaam(getTxtNaam().getText());
		getPersoon().setVoornaam(getTxtVoornaam().getText());
		if (getRbtnVrouw().isSelected()) {
			getPersoon().setGeslacht(GeslachtEnum.V);
		} else if (getRbtnMan().isSelected()) {
			getPersoon().setGeslacht(GeslachtEnum.M);
		} else {
			getPersoon().setGeslacht(GeslachtEnum.O);
		}
		Calendar calGebDat = Calendar.getInstance();
		String sDate = getGeboortedatumPicker().getModel().getDay() + "-"
				+ (getGeboortedatumPicker().getModel().getMonth() + 1) + "-"
				+ getGeboortedatumPicker().getModel().getYear();
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = formatter.parse(sDate);
			calGebDat.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("date not correctly parsed");
		}
		getPersoon().setGeboorteplaats(getTxtGeboorteplaats().getText());
		getPersoon().setGeboortedatum(calGebDat.getTime());
		getPersoon().setOpmerking(getTaOpmerking().getText());
		getPersoon().setIdentiteitskaartnummer(getTxtIdentiteitskaart().getText());
		getPersoon().setNationaliteit(getTxtNationaliteit().getText());
		getPersoon().setRijksregisternummer(getTxtRijksregisternummer().getText());

		/**
		 * adres gegevens
		 */
		AdresDTO adresDTO = null;
		if (getPersoon().getAdresDTO() != null) {
			adresDTO = getPersoon().getAdresDTO();
		} else {
			adresDTO = new AdresDTO();
		}
		adresDTO.setHuisnummer(getTxtNummer().getText());
		adresDTO.setPlaats(getTxtPlaats().getText());
		adresDTO.setPostcode(getTxtPostcode().getText());
		adresDTO.setStraat(getTxtStraat().getText());
		getPersoon().setAdresDTO(adresDTO);

		/**
		 * telefoons
		 */
		Set<ContactgegevenDTO> contactgegevens = new HashSet<ContactgegevenDTO>();
		Set<ContactgegevenDTO> oudeContactgegevens = getPersoon().getContactgegevens();
		List<String> contactList = new ArrayList<String>();
		for (int i = 0; i < getjListModelTelefoon().getSize(); i++) {
			contactList.add((String) getjListModelTelefoon().getElementAt(i));
		}

		for (int i = 0; i < getjListModelTelefoon().getSize(); i++) {
			ContactgegevenDTO contactgegevenDTO = containsContactgegeven(oudeContactgegevens,
					(String) getjListModelTelefoon().get(i));
			if (contactgegevenDTO == null) {
				ContactgegevenDTO contactgegeven = new ContactgegevenDTO();
				contactgegeven.setContactgegevenType(ContactgegevenTypeEnum.TELEFOON);
				contactgegeven.setWaarde((String) getjListModelTelefoon().get(i));
				contactgegevens.add(contactgegeven);
			} else {
				contactgegevens.add(contactgegevenDTO);
			}
		}

		/**
		 * email
		 */
		ContactgegevenDTO contactgegevenDTO = zoekEmail(oudeContactgegevens);
		if (contactgegevenDTO == null) {
			if (!StringUtils.isEmpty(getTxtEmail().getText())) {
				ContactgegevenDTO contactgegeven = new ContactgegevenDTO();
				contactgegeven.setContactgegevenType(ContactgegevenTypeEnum.EMAIL);
				contactgegeven.setWaarde(getTxtEmail().getText());
				contactgegevens.add(contactgegeven);
			}
		} else {
			if (!StringUtils.isEmpty(getTxtEmail().getText())) {
				contactgegevenDTO.setWaarde(getTxtEmail().getText());
				contactgegevens.add(contactgegevenDTO);
			} else {
				getPersoon().getContactgegevens().remove(contactgegevenDTO);
				getContactGegevensToRemove().add(contactgegevenDTO);
			}
		}
		getPersoon().setContactgegevens(contactgegevens);

		/**
		 * gegevens over de wagen reeds toegevoegd bij invullen gegevens van wagen
		 */

	}

	private ContactgegevenDTO containsContactgegeven(Set<ContactgegevenDTO> contactgegevens, String waarde) {
		ContactgegevenDTO contactgegeven = null;
		for (ContactgegevenDTO contactgegevenDTO : contactgegevens) {
			if (waarde.equals(contactgegevenDTO.getWaarde())) {
				contactgegeven = contactgegevenDTO;
				contactgegevens.remove(contactgegevenDTO);
				break;
			}
		}
		return contactgegeven;
	}

	private ContactgegevenDTO zoekEmail(Set<ContactgegevenDTO> contactgegevens) {
		ContactgegevenDTO contactgegeven = null;
		for (ContactgegevenDTO contactgegevenDTO : contactgegevens) {
			if (contactgegevenDTO.getContactgegevenType().equals(ContactgegevenTypeEnum.EMAIL)) {
				contactgegeven = contactgegevenDTO;
				break;
			}
		}
		return contactgegeven;
	}

	public void savePersoon() {
		if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())) {
			updatePersoonDataFromGUI();
			if (!StringUtils.isEmpty(getPersoon().getNaam()) && !StringUtils.isEmpty(getPersoon().getVoornaam())) {
				removeContactOrWagen();
				setPersoon(persoonService.store(getPersoon()));
				if (getInschrijving() != null) {
					for (InschrijvingPersoonDTO inschrijvingPersoon : getInschrijving().getInschrijvingPersonen()) {
						if (getPersoon().getId() == inschrijvingPersoon.getPersoon().getId()) {
							inschrijvingPersoon.setPersoon(persoon);
						}
					}
				}
				getController().setPersonen(getPersoonService().getPersoonNamen());
				getController().getMainNavigationpanel().getBetalerPanel().setDataInGUI();
			}
		}
	}

	private void removeContactOrWagen() {
		for (ContactgegevenDTO contactgegeven : getContactGegevensToRemove()) {
			if (contactgegeven != null) {
                persoonService.removeContactgegeven(contactgegeven);
			}
		}
		for (WagenDTO wagen : getWagensToRemove()) {
			if (wagen != null) {
                persoonService.removeWagen(wagen);
			}
		}
		setContactGegevensToRemove(new ArrayList<ContactgegevenDTO>());
		setWagensToRemove(new ArrayList<WagenDTO>());
	}

	@Override
	public void save() {
		// check als het hier over een update of ingave van een nieuw persoon of een nieuwe inschrijving gaat
		if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())
				&& !StringUtils.isEmpty(getGeboortedatumPicker().getJFormattedTextField().getText())) {

			boolean persoonGewijzigd = checkPersoonGewijzigd();

			if (persoonGewijzigd) {
				String message = "De gegevens van ";
				message += getTxtNaam().getText() + " " + getTxtVoornaam().getText();
				message += " werden gewijzigd. Wenst u deze wijzigingen op te slaan?";
				SaveDialog dialog = new SaveDialog(message);
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					savePersoon();
				}
				dialog.dispose();
			}
		}

		String s = "";
		if (null == getInschrijving() || getInschrijving().getInschrijvingPersonen().size() == 0) {
			s += "Er zijn nog geen personen geregistreerd voor deze inschrijving. Gelieve eerst minstens 1 persoon "
					+ "toe te voegen via de Select";
		}

		if (s.length() > 0) {
			JOptionPane.showMessageDialog(this, s, "Registratie nog niet ingevuld", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				String computername = InetAddress.getLocalHost().getHostName();
				getInschrijving().setComputer(computername);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object[] array2 = { "WENST U DEZE INSCHRIJVING OP TE SLAAN?" };
			int ret = JOptionPane.showConfirmDialog(null, array2, "", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.YES_OPTION) {
				try {
					int maxFichenummer = standplaatsService.findLaatseFichenummer();
					getInschrijving().setFichenummer(++maxFichenummer);

					// StandplaatsDTO tmpStandplaats =
					// (StandplaatsDTO)getDataFromGUI();
					StandplaatsDTO tmpStandplaats = getStandplaatsService().getStandplaats(
							getController().getStandplaatsDTO().getId());
					tmpStandplaats.getInschrijvingen().add(getInschrijving());
					getController().setStandplaatsDTO(tmpStandplaats);
					StandplaatsDTO standplaats = standplaatsService.storeStandplaats(tmpStandplaats);
					getController().setStandplaatsDTO(standplaats);
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					ex.printStackTrace();
				}
			}

			if (ret == JOptionPane.YES_OPTION || ret == JOptionPane.NO_OPTION) {
				/**
				 * Dit popup scherm vraagt de gebruiker als hij/zij wenst af te drukken. De JOptionPane retourneert een
				 * 0 als ja, 1 als nee
				 */
				Object[] array = { "WENST U DE FICHE AF TE DRUKKEN?" };
				ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				/**
				 * afdrukken van de inschrijving
				 */
				if (ret == 0) {
					String plaatsgroep = getController().getStandplaatsDTO().getPlaatsgroep();
					String plaatsnummer = Integer.toString(getController().getStandplaatsDTO().getPlaatsnummer());
					while (plaatsnummer.length() < 3) {
						plaatsnummer = "0" + plaatsnummer;
					}
					String plaats = plaatsgroep + plaatsnummer;
					try {
						Fiche fiche = new Fiche(getInschrijving().getSoorthuurder());
						fiche.createDocument(plaats, getInschrijving(), printerZwaarPapierConfiguratie.getWaarde(),
								printerLichtPapierConfiguratie.getWaarde(), getComputerNamesForPrinting());
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PrinterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				resetRegistratie();
				getController().setTabInMainpanel(4, soortHuurder);
			}
		}
	}

	private String getComputerNamesForPrinting() {
		if (null == computerNamesForPrinting) {
			if (null != computerNameConfiguratie) {
				computerNamesForPrinting = computerNameConfiguratie.getWaarde();
			} else {
				computerNamesForPrinting = Constant.NO_COMPUTER_NAME;
			}
		}
		return computerNamesForPrinting;
	}

	@Override
	public void setDataInGUI() {
		getTxtNaam().requestFocusInWindow();
		getTxtNaam().selectAll();
	}

	public void setDataInGUI(PersoonDTO persoonDTO, boolean clearDate, boolean showPhoto) {
		setPersoon(persoonDTO);
		getTxtNaam().setText(persoonDTO.getNaam());
		getTxtVoornaam().setText(persoonDTO.getVoornaam());
		if (persoonDTO.getGeslacht().equals(GeslachtEnum.M)) {
			getRbtnMan().doClick();
		} else if (persoonDTO.getGeslacht().equals(GeslachtEnum.V)) {
			getRbtnVrouw().doClick();
		}
		getTxtGeboorteplaats().setText(persoonDTO.getGeboorteplaats());
		getTaOpmerking().setText(persoonDTO.getOpmerking());
		if (persoonDTO.getAdresDTO() != null) {
			getTxtStraat().setText(persoonDTO.getAdresDTO().getStraat());
			getTxtPlaats().setText(persoonDTO.getAdresDTO().getPlaats());
			getTxtNummer().setText(persoonDTO.getAdresDTO().getHuisnummer());
			getTxtBus().setText(persoonDTO.getAdresDTO().getBus());
			getTxtPostcode().setText(persoonDTO.getAdresDTO().getPostcode());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(persoonDTO.getGeboortedatum());
		if (clearDate) {
			getGeboortedatumPicker().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DATE));
		}
		getTxtLeeftijd().setText(Integer.toString(berekenLeeftijd(cal)));
		getTxtPersoonId().setText(Integer.toString(persoonDTO.getId()));
		getTxtIdentiteitskaart().setText(persoonDTO.getIdentiteitskaartnummer());
		getTxtNationaliteit().setText(persoonDTO.getNationaliteit());
		getTxtRijksregisternummer().setText(persoonDTO.getRijksregisternummer());
		// setPositionOnScreen(persoonDTO);
		getTxtNaam().requestFocusInWindow();
		getTxtNaam().selectAll();

		for (ContactgegevenDTO contactgegeven : persoonDTO.getContactgegevens()) {
			if (contactgegeven.getContactgegevenType().equals(ContactgegevenTypeEnum.TELEFOON)) {
				getjListModelTelefoon().addElement(contactgegeven.getWaarde());
			} else if (contactgegeven.getContactgegevenType().equals(ContactgegevenTypeEnum.EMAIL)) {
				getTxtEmail().setText(contactgegeven.getWaarde());
			}
		}

		for (WagenDTO wagen : persoonDTO.getWagens()) {
			getjListModelNummerplaat().addElement(wagen.getNummerplaat());
		}

		if (showPhoto) {
			setPersoonFoto(persoonDTO.getId());
		}
	}

	@Override
	public void setRemarksInGui() {
		// TODO Auto-generated method stub
	}

	/**
	 * This method initializes personLeftPanel
	 * 
	 * @return be.camping.campingzwaenepoel.presentation.GUI.PersonLeftPanel
	 */
	public JPanel getPersonLeftPanel() {
		if (personLeftPanel == null) {
			personLeftPanel = new JPanel();
			personLeftPanel.setBorder(new LineBorder(Color.BLACK, 2));
			personLeftPanel.setLayout(new BorderLayout());
			personLeftPanel.add(getButtonPanel(), BorderLayout.NORTH);
			personLeftPanel.add(getJScrollPane());
		}
		return personLeftPanel;
	}

	private JList getJListPersonen() {
		if (jListPersonen == null) {
			String listData[] = {};
			jListPersonen = new JList(listData);
			jListPersonen.addMouseListener(actionJListPersonen);
		}
		return jListPersonen;
	}

	class ActionJListPersonen extends MouseAdapter {
		protected JList list;

		public ActionJListPersonen(JList l) {
			list = l;
		}

		public void setActionList(JList l) {
			list = l;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				try {
					checkAndUpdatePerson();

					int index = list.locationToIndex(e.getPoint());
					logger.info("double click met index: " + index);
					if (index >= 0) {
						zoekEncheckPersonExists = false;

						// list.ensureIndexIsVisible(index);
						int id = gezochtePersonen.get(index);

						wisPersoonPanel(true);
						PersoonDTO persoonDTO = persoonService.findPersoonById(id);
						setDataInGUI(persoonDTO, true, true);
						setPersoon(persoonDTO);
						// if (zoekStandplaatsVoorPersoon) {
						// updateStandplaatsVoorPersoon(id);
						// }
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(e);
				}
			}
		}
	}

	private void checkAndUpdatePerson() {
		if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())
				&& !StringUtils.isEmpty(getGeboortedatumPicker().getJFormattedTextField().getText())) {
			boolean persoonGewijzigd = checkPersoonGewijzigd();
			if (persoonGewijzigd) {
				String message = "De gegevens van ";
				message += getTxtNaam().getText() + " " + getTxtVoornaam().getText();
				message += " werden gewijzigd. Wenst u deze wijzigingen op te slaan?";
				SaveDialog dialog = new SaveDialog(message);
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					savePersoon();
				}
				dialog.dispose();
			}
		}
	}

	public void setPersoonInGui(int persoonId) {
		PersoonDTO tmpPersoon = persoonService.findPersoonById(persoonId);
		wisPersoonPanel(true);
		zoekEncheckPersonExists = false;
		setDataInGUI(tmpPersoon, true, true);
		setPersoon(tmpPersoon);
	}

	public void setPersoonInGui(PersoonDTO persoon) {
		checkPersoonGewijzigd();
		wisPersoonPanel(true);
		zoekEncheckPersonExists = false;
		setDataInGUI(persoon, true, true);
		setPersoon(persoon);
	}

	public void findAndShowPersoon(int persoonId) {
		PersoonDTO persoon = persoonService.findPersoonById(persoonId);
		wisPersoonPanel(true);
		setDataInGUI(persoon, true, true);
		setPersoon(persoon);
	}

	public JScrollPane getjScrollPaneTelefoon() {
		if (jScrollPaneTelefoon == null) {
			jScrollPaneTelefoon = new JScrollPane(getjListTelefoon());
			jScrollPaneTelefoon.setPreferredSize(new Dimension(150, 72));
			jScrollPaneTelefoon.setMinimumSize(new Dimension(120, 72));
		}
		return jScrollPaneTelefoon;
	}

	public JList getjListTelefoon() {
		if (jListTelefoon == null) {
			jListTelefoon = new JList(getjListModelTelefoon());

		}
		return jListTelefoon;
	}

	public DefaultListModel getjListModelTelefoon() {
		if (jListModelTelefoon == null) {
			jListModelTelefoon = new DefaultListModel();
			jListModelTelefoon.addListDataListener(new TelefoonDataListener());
		}
		return jListModelTelefoon;
	}

	private class TelefoonDataListener implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent arg0) {
		}

		@Override
		public void intervalAdded(ListDataEvent arg0) {
			zoekPersonen();
		}

		@Override
		public void intervalRemoved(ListDataEvent arg0) {
			zoekPersonen();
		}

	}

	public JScrollPane getjScrollPaneNummerplaat() {
		if (jScrollPaneNummerplaat == null) {
			jScrollPaneNummerplaat = new JScrollPane(getjListNummerplaat());
			jScrollPaneNummerplaat.setPreferredSize(new Dimension(150, 72));
			jScrollPaneNummerplaat.setMinimumSize(new Dimension(150, 72));
		}
		return jScrollPaneNummerplaat;
	}

	public JList getjListNummerplaat() {
		if (jListNummerplaat == null) {
			jListNummerplaat = new JList(getjListModelNummerplaat());
		}
		return jListNummerplaat;
	}

	public DefaultListModel getjListModelNummerplaat() {
		if (jListModelNummerplaat == null) {
			jListModelNummerplaat = new DefaultListModel();
		}
		return jListModelNummerplaat;
	}

	/**
	 * This method initializes btnOpslaan
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			Dimension dim = new Dimension(160, 40);
			btnSave.setPreferredSize(dim);
			btnSave.setFont(fontButton);
			btnSave.setText("OPSL INSCHRIJVING");
			btnSave.addActionListener(this);
		}
		return btnSave;
	}

	public JButton getBtnSavePersoon() {
		if (btnSavePersoon == null) {
			btnSavePersoon = new JButton();
			btnSavePersoon.setFont(fontButton);
			btnSavePersoon.setPreferredSize(new Dimension(270, 40));
			// btnSavePersoon.setPreferredSize(dimMinBtnPersoon);
			btnSavePersoon.setMinimumSize(new Dimension(145, 40));
			btnSavePersoon.setText("OPSLAAN PERSOON");
			// btnSavePersoon.setBorder(new EmptyBorder(new Insets(5, 5, 5,
			// 5)));

			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new OpslaanActionListener("Opslaan");
			InputMap inputMap = btnSavePersoon.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(stroke, actionKey);
			ActionMap actionMap = btnSavePersoon.getActionMap();
			actionMap.put(actionKey, action);
			btnSavePersoon.addActionListener(this);
		}
		return btnSavePersoon;
	}

	public JButton getBtnAfdrukkenpersoon() {
		if (btnAfdrukkenpersoon == null) {
			btnAfdrukkenpersoon = new JButton();
			btnAfdrukkenpersoon.setFont(fontButton);
			// btnAfdrukkenpersoon.setPreferredSize(dimBtnPersoon);
			btnAfdrukkenpersoon.setPreferredSize(new Dimension(320, 40));
			btnAfdrukkenpersoon.setMinimumSize(dimMinBtnPersoon);
			btnAfdrukkenpersoon.setText("AFDRUKKEN PERSOON");
			btnAfdrukkenpersoon.addActionListener(this);
		}
		return btnAfdrukkenpersoon;
	}

	public class UpperCaseField extends JTextField {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4466518566483143968L;

		public UpperCaseField(int cols) {
			super(cols);
		}

		protected Document getDefaultModel() {
			return new UpperCaseDocument();
		}

		class UpperCaseDocument extends PlainDocument {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8517456804483389189L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				if (str == null) {
					return;
				}
				char[] upper = str.toCharArray();
				for (int i = 0; i < upper.length; i++) {
					upper[i] = Character.toUpperCase(upper[i]);
				}
				super.insertString(offs, new String(upper), a);
			}
		}
	}

	public JTextField getjTxtNummerplaat() {
		if (jTxtNummerplaat == null) {
			DocumentFilter dfilter = new UpcaseFilter();
			jTxtNummerplaat = new JTextField();
			jTxtNummerplaat.setPreferredSize(new Dimension(150, 28));
			jTxtNummerplaat.setMinimumSize(new Dimension(100, 28));
			jTxtNummerplaat.setFont(font);
			jTxtNummerplaat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
						getjTxtMerk().requestFocusInWindow();
					}
				}

				@Override
				public void keyPressed(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
					}
				}
			});
			jTxtNummerplaat.addFocusListener(focusAdapter);
			((AbstractDocument) jTxtNummerplaat.getDocument()).setDocumentFilter(dfilter);

			// jTxtNummerplaat.getDocument().addDocumentListener(new
			// UppercaseListener());
		}
		return jTxtNummerplaat;
	}

	public JTextField getjTxtMerk() {
		if (jTxtMerk == null) {
			jTxtMerk = new JTextField();
			jTxtMerk.setPreferredSize(new Dimension(200, 28));
			jTxtMerk.setFont(font);
			jTxtMerk.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
						getjTxtSticker().requestFocusInWindow();
					}
				}

				@Override
				public void keyPressed(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
					}
				}
			});
			jTxtMerk.addFocusListener(focusAdapter);
		}
		return jTxtMerk;
	}

	public JTextField getjTxtSticker() {
		if (jTxtSticker == null) {
			jTxtSticker = new JTextField();
			jTxtSticker.setPreferredSize(new Dimension(200, 28));
			jTxtSticker.setFont(font);
			jTxtSticker.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
						getjTxtOpmerking().requestFocusInWindow();
					}
				}

				@Override
				public void keyPressed(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
					}
				}
			});
			jTxtSticker.addFocusListener(focusAdapter);
		}
		return jTxtSticker;
	}

	public JTextField getjTxtOpmerking() {
		if (jTxtOpmerking == null) {
			jTxtOpmerking = new JTextField();
			jTxtOpmerking.setPreferredSize(new Dimension(300, 28));
			jTxtOpmerking.setFont(font);
			jTxtOpmerking.addFocusListener(focusAdapter);
		}
		return jTxtOpmerking;
	}

	class OpslaanActionListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		OpslaanActionListener(String s) {
			super(s);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			btnSave.doClick();
		}
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(getJListPersonen());
			jScrollPane.setPreferredSize(new Dimension(200, 250));
			jScrollPane.setMinimumSize(new Dimension(200, 250));
			jScrollPane.setFont(font);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnWis
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWisPersoon() {
		if (btnWisPersoon == null) {
			btnWisPersoon = new JButton();
			btnWisPersoon.setPreferredSize(new Dimension(220, 40));
			// btnWisPersoon.setPreferredSize(dimMinBtnPersoon);
			btnWisPersoon.setMinimumSize(new Dimension(145, 40));
			btnWisPersoon.setText("WIS PERSOON");
			btnWisPersoon.setFont(fontButton);
			btnWisPersoon.addActionListener(this);
		}
		return btnWisPersoon;
	}

	public JButton getBtnWisRegistratie() {
		if (btnWisRegistratie == null) {
			btnWisRegistratie = new JButton();
			Dimension dim = new Dimension(145, 40);
			btnWisRegistratie.setPreferredSize(dim);
			btnWisRegistratie.setText("RESET REGISTR.");
			btnWisRegistratie.setFont(fontButton);
			btnWisRegistratie.addActionListener(this);
		}
		return btnWisRegistratie;
	}

	/**
	 * This method initializes btnWis
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton();
			Dimension dim = new Dimension(145, 40);
			btnSelect.setPreferredSize(dim);
			btnSelect.setText("SELECT");
			btnSelect.setFont(fontButton);
			btnSelect.addActionListener(this);
		}
		return btnSelect;
	}

	private JButton getBtnTelephone() {
		if (btnTelephone == null) {
			btnTelephone = new JButton("NIEUW");
			btnTelephone.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btnTelephone.addActionListener(this);
		}
		return btnTelephone;
	}

	public JButton getBtnEditTel() {
		if (btnEditTel == null) {
			btnEditTel = new JButton("WIJZIG");
			btnEditTel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btnEditTel.addActionListener(this);
		}
		return btnEditTel;
	}

	private JButton getBtnRemoveTel() {
		if (btnRemoveTel == null) {
			btnRemoveTel = new JButton("VERWIJDER");
			btnRemoveTel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btnRemoveTel.addActionListener(this);
		}
		return btnRemoveTel;
	}

	private JButton getBtnNrPlaat() {
		if (btnNrPlaat == null) {
			btnNrPlaat = new JButton("NIEUW");
			btnNrPlaat.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btnNrPlaat.addActionListener(this);
		}
		return btnNrPlaat;
	}

	private JButton getBtnEditNrPlaat() {
		if (btnEditNrPlaat == null) {
			btnEditNrPlaat = new JButton("WIJZIG");
			btnEditNrPlaat.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btnEditNrPlaat.addActionListener(this);
		}
		return btnEditNrPlaat;
	}

	private JButton getBtnRemoveNrPlaat() {
		if (btnRemoveNrPlaat == null) {
			btnRemoveNrPlaat = new JButton("VERWIJDER");
			btnRemoveNrPlaat.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btnRemoveNrPlaat.addActionListener(this);
		}
		return btnRemoveNrPlaat;
	}

	private JPanel getJPanelNrPlaat() {
		if (jPanelNrPlaat == null) {
			jPanelNrPlaat = new JPanel();
			jPanelNrPlaat.setLayout(new GridLayout(3, 1));
			jPanelNrPlaat.add(getBtnNrPlaat());
			jPanelNrPlaat.add(getBtnEditNrPlaat());
			jPanelNrPlaat.add(getBtnRemoveNrPlaat());
		}
		return jPanelNrPlaat;
	}

	private JPanel getJPanelTelephone() {
		if (jPanelTelephone == null) {
			jPanelTelephone = new JPanel();
			jPanelTelephone.setLayout(new GridLayout(3, 1));
			jPanelTelephone.add(getBtnTelephone());
			jPanelTelephone.add(getBtnEditTel());
			jPanelTelephone.add(getBtnRemoveTel());
		}
		return jPanelTelephone;
	}

	/**
	 * This method initializes btnKaartlezer
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnKaartlezer() {
		if (btnKaartlezer == null) {
			btnKaartlezer = new JButton();
			btnKaartlezer.setText("KAARTLEZER");
			Dimension dim = new Dimension(145, 40);
			btnKaartlezer.setPreferredSize(dim);
			btnKaartlezer.setFont(fontButton);
			btnKaartlezer.addActionListener(this);
		}
		return btnKaartlezer;
	}

	private void zoekPersonen() {
		if (zoekEncheckPersonExists) {
			Map<String, Object> zoekCriteria = new HashMap<String, Object>();
			Map<String, String> adresCriteria = new HashMap<String, String>();
			Map<String, Object> contactCriteria = new HashMap<String, Object>();
			Map<String, String> wagenCriteria = new HashMap<String, String>();
			if (!StringUtils.isEmpty(getTxtNaam().getText())) {
				zoekCriteria.put(Constant.NAAM, getTxtNaam().getText());
			}
			if (!StringUtils.isEmpty(getTxtVoornaam().getText())) {
				zoekCriteria.put(Constant.VOORNAAM, getTxtVoornaam().getText());
			}
			if (!StringUtils.isEmpty(getTxtGeboorteplaats().getText())) {
				zoekCriteria.put(Constant.GEBOORTEPLAATS, getTxtGeboorteplaats().getText());
			}
			if (!StringUtils.isEmpty(getTxtIdentiteitskaart().getText())) {
				zoekCriteria.put(Constant.IDENTITEITSKAART, getTxtIdentiteitskaart().getText());
			}
			if (!StringUtils.isEmpty(getTxtNationaliteit().getText())) {
				zoekCriteria.put(Constant.NATIONALITEIT, getTxtNationaliteit().getText());
			}
			if (!StringUtils.isEmpty(getGeboortedatumPicker().getJFormattedTextField().getText())) {
				int jaar = getGeboortedatumPicker().getModel().getYear();
				int maand = getGeboortedatumPicker().getModel().getMonth() + 1;
				int dag = getGeboortedatumPicker().getModel().getDay();
				String sDate = jaar + "-";
				if (maand < 10)
					sDate += 0;
				sDate += maand + "-";
				if (dag < 10)
					sDate += 0;
				sDate += dag;
				zoekCriteria.put(Constant.GEBOORTEDATUM, sDate);
			}
			if (!StringUtils.isEmpty(getTaOpmerking().getText())) {
				zoekCriteria.put(Constant.OPMERKING, getTaOpmerking().getText());
			}
			if (!StringUtils.isEmpty(getTxtStraat().getText())) {
				adresCriteria.put(Constant.STRAAT, getTxtStraat().getText());
			}
			if (!StringUtils.isEmpty(getTxtNummer().getText())) {
				adresCriteria.put(Constant.NUMMER, getTxtNummer().getText());
			}
			if (!StringUtils.isEmpty(getTxtPostcode().getText())) {
				adresCriteria.put(Constant.POSTCODE, getTxtPostcode().getText());
			}
			if (!StringUtils.isEmpty(getTxtPlaats().getText())) {
				adresCriteria.put(Constant.PLAATS, getTxtPlaats().getText());
			}
			if (adresCriteria.size() > 0) {
				zoekCriteria.put(Constant.ADRES, adresCriteria);
			}
			if (!StringUtils.isEmpty(getTxtEmail().getText())) {
				contactCriteria.put(Constant.EMAIL, getTxtEmail().getText());
			}
			if (getjListModelTelefoon().size() > 0) {
				List<Object> telefoons = new ArrayList<Object>();
				telefoons.add(getjListModelTelefoon().getElementAt(0));
				/*
				 * for (int i = 0; i < getjListModelTelefoon().size(); i++) {
				 * telefoons.add(getjListModelTelefoon().getElementAt(i)); }
				 */
				contactCriteria.put(Constant.TELEFOON, telefoons);
			}
			if (getjListModelNummerplaat().size() > 0) {
				if (!StringUtils.isEmpty(getjTxtNummerplaat().getText())) {
					wagenCriteria.put(Constant.NUMMERPLAAT, getjTxtNummerplaat().getText());
				}
				if (!StringUtils.isEmpty(getjTxtMerk().getText())) {
					wagenCriteria.put(Constant.MERK, getjTxtMerk().getText());
				}
				if (!StringUtils.isEmpty(getjTxtOpmerking().getText())) {
					wagenCriteria.put(Constant.OPMERKING_WAGEN, getjTxtOpmerking().getText());
				}
				if (!StringUtils.isEmpty(getjTxtSticker().getText())) {
					wagenCriteria.put(Constant.STICKER, getjTxtSticker().getText());
				}
				if (wagenCriteria.size() > 0) {
					zoekCriteria.put(Constant.WAGEN, wagenCriteria);
				}
			}
			if (contactCriteria.size() > 0) {
				zoekCriteria.put(Constant.CONTACTGEGEVEN, contactCriteria);
			}
			if (zoekCriteria.size() > 0) {
				List<Object[]> persoongegevens = persoonService.zoekPersonen(zoekCriteria);
				Vector<String> superClasses = new Vector<String>();
				gezochtePersonen = new ArrayList<Integer>();
				for (Object[] o : persoongegevens) {
					String naam = o[1] + " " + o[2];
					superClasses.add(naam);
					gezochtePersonen.add((Integer) o[0]);
				}
				getJListPersonen().setListData(superClasses);
				JList list = new JList();
				list.setListData(superClasses);
				actionJListPersonen.setActionList(list);
			}
		}
	}

	public JPanel getPersonCentralPanel() {
		if (personCentralPanel == null) {
			personCentralPanel = new JPanel();
			personCentralPanel.setLayout(new BorderLayout());
			personCentralPanel.setBorder(new LineBorder(Color.BLACK, 1));
			personCentralPanel.add(getPersonNorthPanel(), BorderLayout.NORTH);
			personCentralPanel.add(getButtonPanelPersoon(), BorderLayout.CENTER);
			personCentralPanel.add(getPersonSouthPanel(), BorderLayout.SOUTH);
		}
		return personCentralPanel;
	}

	/**
	 * This method initializes personNorthPanel
	 * 
	 * @return be.camping.campingzwaenepoel.presentation.GUI.PersonNorthPanel
	 */
	public JPanel getPersonNorthPanel() {
		if (personNorthPanel == null) {
			personNorthPanel = new JPanel();

			Insets insets = new Insets(5, 0, 0, 5);
			Insets insetsBorder = new Insets(10, 20, 0, 5);
			Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 615, (Toolkit
					.getDefaultToolkit().getScreenSize().height - 120) / 2);
			personNorthPanel.setSize(dim);
			personNorthPanel.setLayout(new GridBagLayout());
			personNorthPanel.setBorder(new LineBorder(Color.BLACK, 1));
			Font font = new Font("SansSerif", Font.BOLD, 18);

			// label naam
			GridBagConstraints gridBagConstraintsNaam = new GridBagConstraints();
			gridBagConstraintsNaam.gridx = 0;
			gridBagConstraintsNaam.gridy = 0;
			gridBagConstraintsNaam.insets = insetsBorder;
			gridBagConstraintsNaam.anchor = GridBagConstraints.EAST;
			JLabel lblNaam = new JLabel();
			lblNaam.setText("Familienaam: ");
			lblNaam.setFont(font);

			// textfields naam
			GridBagConstraints gridBagConstraintsTxtNaam = new GridBagConstraints();
			gridBagConstraintsTxtNaam.fill = GridBagConstraints.VERTICAL;
			gridBagConstraintsTxtNaam.gridx = 1;
			gridBagConstraintsTxtNaam.gridy = 0;
			gridBagConstraintsTxtNaam.weightx = 1.0;
			gridBagConstraintsTxtNaam.gridwidth = 3;
			gridBagConstraintsTxtNaam.insets = insets;
			gridBagConstraintsTxtNaam.anchor = GridBagConstraints.WEST;

			// label geslacht
			GridBagConstraints gridBagConstraintsGeslacht = new GridBagConstraints();
			gridBagConstraintsGeslacht.gridx = 0;
			gridBagConstraintsGeslacht.gridy = 2;
			gridBagConstraintsGeslacht.insets = insetsBorder;
			gridBagConstraintsGeslacht.anchor = GridBagConstraints.EAST;
			JLabel lblGeslacht = new JLabel();
			lblGeslacht.setText("Geslacht: ");
			lblGeslacht.setFont(font);

			// panel geslacht
			GridBagConstraints gridBagConstraintsPanelGeslacht = new GridBagConstraints();
			gridBagConstraintsPanelGeslacht.gridx = 1;
			gridBagConstraintsPanelGeslacht.gridy = 2;
			gridBagConstraintsPanelGeslacht.insets = insetsBorder;
			gridBagConstraintsPanelGeslacht.anchor = GridBagConstraints.WEST;

			// label geboorteplaats
			GridBagConstraints gridBagConstraintsGeboorteplaats = new GridBagConstraints();
			gridBagConstraintsGeboorteplaats.gridx = 0;
			gridBagConstraintsGeboorteplaats.gridy = 1;
			gridBagConstraintsGeboorteplaats.insets = insetsBorder;
			gridBagConstraintsGeboorteplaats.anchor = GridBagConstraints.EAST;
			JLabel lblGeboortePlaats = new JLabel();
			lblGeboortePlaats.setText("Geboren te: ");
			lblGeboortePlaats.setFont(font);

			// textfield geboorteplaats
			GridBagConstraints gridBagConstraintsTxtGeboorteplaats = new GridBagConstraints();
			gridBagConstraintsTxtGeboorteplaats.gridx = 1;
			gridBagConstraintsTxtGeboorteplaats.gridy = 1;
			gridBagConstraintsTxtGeboorteplaats.insets = insets;
			gridBagConstraintsTxtGeboorteplaats.anchor = GridBagConstraints.WEST;

			// label straat
			GridBagConstraints gridBagConstraintsStraat = new GridBagConstraints();
			gridBagConstraintsStraat.gridx = 0;
			gridBagConstraintsStraat.gridy = 3;
			gridBagConstraintsStraat.insets = insetsBorder;
			gridBagConstraintsStraat.anchor = GridBagConstraints.EAST;
			JLabel lblStraat = new JLabel();
			lblStraat.setText("straat: ");
			lblStraat.setFont(font);

			// textfield straat
			GridBagConstraints gridBagConstraintsTxtStraat = new GridBagConstraints();
			gridBagConstraintsTxtStraat.gridx = 1;
			gridBagConstraintsTxtStraat.gridy = 3;
			gridBagConstraintsTxtStraat.gridwidth = 3;
			gridBagConstraintsTxtStraat.insets = insets;
			gridBagConstraintsTxtStraat.anchor = GridBagConstraints.WEST;

			// label nummer
			GridBagConstraints gridBagConstraintsNummer = new GridBagConstraints();
			gridBagConstraintsNummer.gridx = 0;
			gridBagConstraintsNummer.gridy = 4;
			gridBagConstraintsNummer.insets = insetsBorder;
			gridBagConstraintsNummer.anchor = GridBagConstraints.EAST;
			lblNummer = new JLabel();
			lblNummer.setText("Nummer: ");
			lblNummer.setFont(font);

			// textfield nummer
			GridBagConstraints gridBagConstraintsTxtNummer = new GridBagConstraints();
			gridBagConstraintsTxtNummer.gridx = 1;
			gridBagConstraintsTxtNummer.gridy = 4;
			gridBagConstraintsTxtNummer.insets = insets;
			gridBagConstraintsTxtNummer.anchor = GridBagConstraints.WEST;

			// label plaats
			GridBagConstraints gridBagConstraintsPlaats = new GridBagConstraints();
			gridBagConstraintsPlaats.gridx = 0;
			gridBagConstraintsPlaats.gridy = 5;
			gridBagConstraintsPlaats.insets = insetsBorder;
			gridBagConstraintsPlaats.anchor = GridBagConstraints.EAST;
			JLabel lblPlaats = new JLabel();
			lblPlaats.setText("Plaats: ");
			lblPlaats.setFont(font);

			// textfield plaats
			GridBagConstraints gridBagConstraintsTxtPlaats = new GridBagConstraints();
			gridBagConstraintsTxtPlaats.gridx = 1;
			gridBagConstraintsTxtPlaats.gridy = 5;
			gridBagConstraintsTxtPlaats.insets = insets;
			gridBagConstraintsTxtPlaats.anchor = GridBagConstraints.WEST;

			// label identiteitskaart
			GridBagConstraints gridBagConstraintsIdentiteitskaart = new GridBagConstraints();
			gridBagConstraintsIdentiteitskaart.gridx = 0;
			gridBagConstraintsIdentiteitskaart.gridy = 6;
			gridBagConstraintsIdentiteitskaart.insets = insetsBorder;
			gridBagConstraintsIdentiteitskaart.anchor = GridBagConstraints.EAST;
			JLabel lblIdentiteitskaart = new JLabel();
			lblIdentiteitskaart.setText("Identiteitskaart: ");
			lblIdentiteitskaart.setFont(font);

			// textfield identiteitskaart
			GridBagConstraints gridBagConstraintsTxtIdentiteitskaart = new GridBagConstraints();
			gridBagConstraintsTxtIdentiteitskaart.gridx = 1;
			gridBagConstraintsTxtIdentiteitskaart.gridy = 6;
			gridBagConstraintsTxtIdentiteitskaart.insets = insets;
			gridBagConstraintsTxtIdentiteitskaart.anchor = GridBagConstraints.WEST;

			// label rijksregisternummer
			GridBagConstraints gridBagConstraintsRijksregisternummer = new GridBagConstraints();
			gridBagConstraintsRijksregisternummer.gridx = 2;
			gridBagConstraintsRijksregisternummer.gridy = 6;
			gridBagConstraintsRijksregisternummer.insets = insetsBorder;
			gridBagConstraintsRijksregisternummer.anchor = GridBagConstraints.EAST;

			// text rijksregisternummer
			GridBagConstraints gridBagConstraintsTxtRijksregisternummer = new GridBagConstraints();
			gridBagConstraintsTxtRijksregisternummer.gridx = 3;
			gridBagConstraintsTxtRijksregisternummer.gridy = 6;
			gridBagConstraintsTxtRijksregisternummer.insets = insets;
			gridBagConstraintsTxtRijksregisternummer.anchor = GridBagConstraints.WEST;

			// label Voornaam
			GridBagConstraints gridBagConstraintsVoornaam = new GridBagConstraints();
			gridBagConstraintsVoornaam.gridx = 2;
			gridBagConstraintsVoornaam.gridy = 0;
			// gridBagConstraintsVoornaam.gridwidth = 3;
			gridBagConstraintsVoornaam.insets = insets;
			gridBagConstraintsVoornaam.anchor = GridBagConstraints.EAST;

			// text Voornaam
			GridBagConstraints gridBagConstraintsTxtVoornaam = new GridBagConstraints();
			gridBagConstraintsTxtVoornaam.gridx = 3;
			gridBagConstraintsTxtVoornaam.gridy = 0;
			// gridBagConstraintsTxtVoornaam.gridwidth = 3;
			gridBagConstraintsTxtVoornaam.insets = insets;
			gridBagConstraintsTxtVoornaam.anchor = GridBagConstraints.WEST;

			// textfield persoon id
			GridBagConstraints gridBagConstraintsTxtPersoonId = new GridBagConstraints();
			gridBagConstraintsTxtPersoonId.gridx = 2;
			gridBagConstraintsTxtPersoonId.gridy = 2;
			gridBagConstraintsTxtPersoonId.insets = insets;
			gridBagConstraintsTxtPersoonId.anchor = GridBagConstraints.WEST;

			// label geboortedatum
			GridBagConstraints gridBagConstraintsGeboortedatum = new GridBagConstraints();
			gridBagConstraintsGeboortedatum.gridx = 2;
			gridBagConstraintsGeboortedatum.gridy = 1;
			gridBagConstraintsGeboortedatum.insets = insets;
			gridBagConstraintsGeboortedatum.anchor = GridBagConstraints.EAST;

			// label geboortedatum
			GridBagConstraints gridBagConstraintsTxtGeboortedatum = new GridBagConstraints();
			gridBagConstraintsTxtGeboortedatum.gridx = 3;
			gridBagConstraintsTxtGeboortedatum.gridy = 1;
			gridBagConstraintsTxtGeboortedatum.insets = insets;
			gridBagConstraintsTxtGeboortedatum.anchor = GridBagConstraints.WEST;

			// label leeftijd
			GridBagConstraints gridBagConstraintsLeeftijd = new GridBagConstraints();
			gridBagConstraintsLeeftijd.gridx = 4;
			gridBagConstraintsLeeftijd.gridy = 1;
			gridBagConstraintsLeeftijd.insets = insetsBorder;
			JLabel lblLeeftijd = new JLabel();
			lblLeeftijd.setText("Leeftijd: ");
			lblLeeftijd.setFont(font);

			// textfield leeftijd
			GridBagConstraints gridBagConstraintsTxtLeeftijd = new GridBagConstraints();
			gridBagConstraintsTxtLeeftijd.gridx = 5;
			gridBagConstraintsTxtLeeftijd.gridy = 1;
			gridBagConstraintsTxtLeeftijd.insets = insets;
			// gridBagConstraintsTxtLeeftijd.anchor = GridBagConstraints.WEST;

			// label postcode
			GridBagConstraints gridBagConstraintsPostcode = new GridBagConstraints();
			gridBagConstraintsPostcode.gridx = 2;
			gridBagConstraintsPostcode.gridy = 4;
			// gridBagConstraintsPostcode.gridwidth = 2;
			gridBagConstraintsPostcode.insets = insetsBorder;
			gridBagConstraintsPostcode.anchor = GridBagConstraints.EAST;

			// text postcode
			GridBagConstraints gridBagConstraintsTxtPostcode = new GridBagConstraints();
			gridBagConstraintsTxtPostcode.gridx = 3;
			gridBagConstraintsTxtPostcode.gridy = 4;
			// gridBagConstraintsPostcode.gridwidth = 2;
			gridBagConstraintsTxtPostcode.insets = insets;
			gridBagConstraintsTxtPostcode.anchor = GridBagConstraints.WEST;

			// label nationaliteit
			GridBagConstraints gridBagConstraintsNationaliteit = new GridBagConstraints();
			gridBagConstraintsNationaliteit.gridx = 2;
			gridBagConstraintsNationaliteit.gridy = 5;
			// gridBagConstraintsNationaliteit.gridwidth = 2;
			gridBagConstraintsNationaliteit.insets = insetsBorder;
			gridBagConstraintsNationaliteit.anchor = GridBagConstraints.EAST;

			// text nationaliteit
			GridBagConstraints gridBagConstraintsTxtNationaliteit = new GridBagConstraints();
			gridBagConstraintsTxtNationaliteit.gridx = 3;
			gridBagConstraintsTxtNationaliteit.gridy = 5;
			// gridBagConstraintsNationaliteit.gridwidth = 2;
			gridBagConstraintsTxtNationaliteit.insets = insets;
			gridBagConstraintsTxtNationaliteit.anchor = GridBagConstraints.WEST;

			// textfield foto
			GridBagConstraints gridBagConstraintsTxtFoto = new GridBagConstraints();
			gridBagConstraintsTxtFoto.gridx = 4;
			gridBagConstraintsTxtFoto.gridy = 2;
			gridBagConstraintsTxtFoto.gridheight = 5;
			gridBagConstraintsTxtFoto.gridwidth = 2;
			Insets insetsFoto = new Insets(5, 10, 10, 0);
			gridBagConstraintsTxtFoto.insets = insetsFoto;
			gridBagConstraintsTxtFoto.anchor = GridBagConstraints.NORTHWEST;

			lblVoornaam = new JLabel();
			lblVoornaam.setText("Voornaam: ");
			lblVoornaam.setFont(font2);

			lblPostcode = new JLabel();
			lblPostcode.setText("Postcode: ");
			lblPostcode.setFont(font2);

			lblNationaliteit = new JLabel();
			lblNationaliteit.setText("Nationaliteit: ");
			lblNationaliteit.setFont(font2);

			lblRijksregisternummer = new JLabel();
			lblRijksregisternummer.setText("Rijksregisternr: ");
			lblRijksregisternummer.setFont(font2);

			lblGeboortedatum = new JLabel();
			lblGeboortedatum.setText("op: ");
			lblGeboortedatum.setFont(font2);

			personNorthPanel.add(lblNaam, gridBagConstraintsNaam);
			personNorthPanel.add(getTxtNaam(), gridBagConstraintsTxtNaam);
			personNorthPanel.add(lblVoornaam, gridBagConstraintsVoornaam);
			personNorthPanel.add(getTxtVoornaam(), gridBagConstraintsTxtVoornaam);
			personNorthPanel.add(lblGeslacht, gridBagConstraintsGeslacht);
			personNorthPanel.add(getPanelGeslacht(), gridBagConstraintsPanelGeslacht);
			personNorthPanel.add(lblGeboortePlaats, gridBagConstraintsGeboorteplaats);
			personNorthPanel.add(getTxtGeboorteplaats(), gridBagConstraintsTxtGeboorteplaats);
			personNorthPanel.add(lblGeboortedatum, gridBagConstraintsGeboortedatum);
			personNorthPanel.add((JComponent) getGeboortedatumPicker(), gridBagConstraintsTxtGeboortedatum);
			personNorthPanel.add(lblLeeftijd, gridBagConstraintsLeeftijd);
			personNorthPanel.add(getTxtLeeftijd(), gridBagConstraintsTxtLeeftijd);
			personNorthPanel.add(lblStraat, gridBagConstraintsStraat);
			personNorthPanel.add(getTxtStraat(), gridBagConstraintsTxtStraat);
			personNorthPanel.add(lblNummer, gridBagConstraintsNummer);
			personNorthPanel.add(getTxtNummer(), gridBagConstraintsTxtNummer);
			personNorthPanel.add(lblPostcode, gridBagConstraintsPostcode);
			personNorthPanel.add(getTxtPostcode(), gridBagConstraintsTxtPostcode);
			personNorthPanel.add(lblPlaats, gridBagConstraintsPlaats);
			personNorthPanel.add(getTxtPlaats(), gridBagConstraintsTxtPlaats);
			personNorthPanel.add(getTxtPersoonId(), gridBagConstraintsTxtPersoonId);
			personNorthPanel.add(getPanelStandplaatsPhoto(), gridBagConstraintsTxtFoto);
			personNorthPanel.add(lblIdentiteitskaart, gridBagConstraintsIdentiteitskaart);
			personNorthPanel.add(getTxtIdentiteitskaart(), gridBagConstraintsTxtIdentiteitskaart);
			personNorthPanel.add(lblNationaliteit, gridBagConstraintsNationaliteit);
			personNorthPanel.add(getTxtNationaliteit(), gridBagConstraintsTxtNationaliteit);
			personNorthPanel.add(lblRijksregisternummer, gridBagConstraintsRijksregisternummer);
			personNorthPanel.add(getTxtRijksregisternummer(), gridBagConstraintsTxtRijksregisternummer);
		}
		return personNorthPanel;
	}

	/**
	 * This method initializes personSouthPanel
	 * 
	 * @return be.camping.campingzwaenepoel.presentation.GUI.PersonSouthPanel
	 */
	public JPanel getPersonSouthPanel() {
		if (personSouthPanel == null) {
			personSouthPanel = new JPanel();

			Font font = new Font("SansSerif", Font.BOLD, 18);
			Insets insets = new Insets(10, 0, 0, 10);
			Insets insetsBorder = new Insets(10, 20, 0, 5);

			personSouthPanel.setLayout(new GridBagLayout());
			personSouthPanel.setBorder(new LineBorder(Color.BLACK, 1));

			// label telefoon
			GridBagConstraints gridBagConstraintsTelefoon = new GridBagConstraints();
			gridBagConstraintsTelefoon.gridx = 0;
			gridBagConstraintsTelefoon.gridy = 0;
			gridBagConstraintsTelefoon.insets = new Insets(0, 20, 0, 5);
			JLabel lblTelefoon = new JLabel();
			lblTelefoon.setText("Telefoon: ");
			lblTelefoon.setFont(font);

			// textfield telefoon
			GridBagConstraints gridBagConstraintsTxtTelefoon = new GridBagConstraints();
			gridBagConstraintsTxtTelefoon.gridy = 0;
			// gridBagConstraintsTxtTelefoon.weightx = 1.0;
			gridBagConstraintsTxtTelefoon.gridx = 1;
			gridBagConstraintsTxtTelefoon.insets = new Insets(5, 0, 0, 5);
			gridBagConstraintsTxtTelefoon.fill = GridBagConstraints.BOTH;
			gridBagConstraintsTxtTelefoon.anchor = GridBagConstraints.WEST;

			// button telefoon
			GridBagConstraints gridBagConstraintsBtnTelefoon = new GridBagConstraints();
			gridBagConstraintsBtnTelefoon.fill = GridBagConstraints.VERTICAL;
			gridBagConstraintsBtnTelefoon.gridy = 0;
			// gridBagConstraintsTxtTelefoon.weightx = 1.0;
			gridBagConstraintsBtnTelefoon.gridx = 2;
			gridBagConstraintsBtnTelefoon.insets = new Insets(5, 0, 0, 5);
			gridBagConstraintsBtnTelefoon.anchor = GridBagConstraints.WEST;

			// label nummerplaat
			GridBagConstraints gridBagConstraintsNrPlaat = new GridBagConstraints();
			gridBagConstraintsNrPlaat.gridx = 3;
			gridBagConstraintsNrPlaat.gridy = 0;
			gridBagConstraintsNrPlaat.insets = new Insets(0, 0, 0, 5);
			JLabel lblNrPlaat = new JLabel();
			lblNrPlaat.setText("Nummerplaat:");
			lblNrPlaat.setFont(font);

			// textfield nummerplaat
			GridBagConstraints gridBagConstraintsTxtNrplaat = new GridBagConstraints();
			gridBagConstraintsTxtNrplaat.fill = GridBagConstraints.VERTICAL;
			gridBagConstraintsTxtNrplaat.gridy = 0;
			gridBagConstraintsTxtNrplaat.fill = GridBagConstraints.BOTH;
			// gridBagConstraintsTxtNrplaat.weightx = 1.0;
			gridBagConstraintsTxtNrplaat.gridx = 4;
			gridBagConstraintsTxtNrplaat.insets = new Insets(5, 0, 0, 5);
			gridBagConstraintsTxtNrplaat.anchor = GridBagConstraints.WEST;

			// button nummerplaat
			GridBagConstraints gridBagConstraintsBtnNrPlaat = new GridBagConstraints();
			gridBagConstraintsBtnNrPlaat.fill = GridBagConstraints.VERTICAL;
			gridBagConstraintsBtnNrPlaat.gridy = 0;
			gridBagConstraintsBtnNrPlaat.gridx = 5;
			gridBagConstraintsBtnNrPlaat.insets = new Insets(5, 0, 0, 5);
			gridBagConstraintsBtnNrPlaat.anchor = GridBagConstraints.WEST;

			// label GSM
			GridBagConstraints gridBagConstraintsGsm = new GridBagConstraints();
			gridBagConstraintsGsm.gridx = 2;
			gridBagConstraintsGsm.gridy = 0;
			gridBagConstraintsGsm.insets = insets;
			JLabel lblGsm = new JLabel();
			lblGsm.setText("Gsm:");
			lblGsm.setFont(font);

			// textfield gsm
			GridBagConstraints gridBagConstraintsTxtGsm = new GridBagConstraints();
			gridBagConstraintsTxtGsm.fill = GridBagConstraints.VERTICAL;
			gridBagConstraintsTxtGsm.gridy = 0;
			gridBagConstraintsTxtGsm.weightx = 1.0;
			gridBagConstraintsTxtGsm.gridx = 3;
			gridBagConstraintsTxtGsm.insets = insets;
			gridBagConstraintsTxtGsm.anchor = GridBagConstraints.WEST;

			// label email
			GridBagConstraints gridBagConstraintsEmail = new GridBagConstraints();
			gridBagConstraintsEmail.gridx = 0;
			gridBagConstraintsEmail.gridy = 1;
			gridBagConstraintsEmail.insets = insetsBorder;
			JLabel lblEmail = new JLabel();
			lblEmail.setText("Email: ");
			lblEmail.setFont(font);

			// textfield email
			GridBagConstraints gridBagConstraintsTxtEmail = new GridBagConstraints();
			gridBagConstraintsTxtEmail.fill = GridBagConstraints.VERTICAL;
			gridBagConstraintsTxtEmail.gridy = 1;
			gridBagConstraintsTxtEmail.gridx = 1;
			gridBagConstraintsTxtEmail.gridwidth = 3;
			gridBagConstraintsTxtEmail.insets = insets;
			gridBagConstraintsTxtEmail.fill = GridBagConstraints.BOTH;
			gridBagConstraintsTxtEmail.anchor = GridBagConstraints.WEST;

			// label opmerking
			GridBagConstraints gridBagConstraintsOpmerking = new GridBagConstraints();
			gridBagConstraintsOpmerking.gridx = 0;
			gridBagConstraintsOpmerking.gridy = 2;
			gridBagConstraintsOpmerking.insets = insetsBorder;
			JLabel lblOpmerking = new JLabel();
			lblOpmerking.setText("Opmerking:");
			lblOpmerking.setFont(font);

			// textarea opmerking
			GridBagConstraints gridBagConstraintsTAOpmerking = new GridBagConstraints();
			gridBagConstraintsTAOpmerking.gridy = 2;
			gridBagConstraintsTAOpmerking.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraintsTAOpmerking.gridwidth = 5;
			gridBagConstraintsTAOpmerking.gridx = 1;
			gridBagConstraintsTAOpmerking.insets = new Insets(10, 0, 5, 10);
			gridBagConstraintsTAOpmerking.anchor = GridBagConstraints.WEST;

			personSouthPanel.add(lblTelefoon, gridBagConstraintsTelefoon);
			// personSouthPanel.add(lblGsm, gridBagConstraintsGsm);
			personSouthPanel.add(lblEmail, gridBagConstraintsEmail);
			personSouthPanel.add(lblNrPlaat, gridBagConstraintsNrPlaat);
			personSouthPanel.add(lblOpmerking, gridBagConstraintsOpmerking);
			personSouthPanel.add(getjScrollPaneTelefoon(), gridBagConstraintsTxtTelefoon);
			// personSouthPanel.add(getTxtGsm(), gridBagConstraintsTxtGsm);
			personSouthPanel.add(getjScrollPaneNummerplaat(), gridBagConstraintsTxtNrplaat);
			personSouthPanel.add(getTxtEmail(), gridBagConstraintsTxtEmail);
			personSouthPanel.add(getTaOpmerking(), gridBagConstraintsTAOpmerking);
			personSouthPanel.add(getJPanelTelephone(), gridBagConstraintsBtnTelefoon);
			personSouthPanel.add(getJPanelNrPlaat(), gridBagConstraintsBtnNrPlaat);
		}
		return personSouthPanel;
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(4, 1));
			buttonPanel.add(getBtnSelect());
			buttonPanel.add(getBtnWisRegistratie());
			buttonPanel.add(getBtnKaartlezer());
			buttonPanel.add(getBtnSave());
			// buttonPanel.add(getBtnWisPersoon());
			// buttonPanel.add(getBtnSavePersoon());
			// buttonPanel.add(getBtnAfdrukkenpersoon());
		}
		return buttonPanel;
	}

	public JPanel getButtonPanelPersoon() {
		if (buttonPanelPersoon == null) {
			buttonPanelPersoon = new JPanel();
			// buttonPanelPersoon.setLayout(new GridLayout(1, 3));
			buttonPanelPersoon.add(getBtnWisPersoon());
			buttonPanelPersoon.add(getBtnSavePersoon());
			buttonPanelPersoon.add(getBtnAfdrukkenpersoon());
		}
		return buttonPanelPersoon;
	}

	private JPanel getPanelGeslacht() {
		if (panelGeslacht == null) {
			panelGeslacht = new JPanel();
			panelGeslacht.setLayout(new GridLayout(1, 2));

			setGeslachtButtons();
			panelGeslacht.add(getRbtnMan());
			panelGeslacht.add(getRbtnVrouw());
		}
		return panelGeslacht;
	}

	private ButtonGroup setGeslachtButtons() {
		ButtonGroup group = new ButtonGroup();
		group.add(getRbtnMan());
		group.add(getRbtnVrouw());
		return group;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JRadioButton getRbtnMan() {
		if (rbtnMan == null) {
			rbtnMan = new JRadioButton("man");
			rbtnMan.setActionCommand("man");
			rbtnMan.setFont(font2);
		}
		return rbtnMan;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JRadioButton getRbtnVrouw() {
		if (rbtnVrouw == null) {
			rbtnVrouw = new JRadioButton("vrouw");
			rbtnVrouw.setActionCommand("vrouw");
			rbtnVrouw.setFont(font2);
		}
		return rbtnVrouw;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtPostcode() {
		if (txtPostcode == null) {
			txtPostcode = new JTextField();
			txtPostcode.setPreferredSize(new Dimension(140, 36));
			txtPostcode.setMinimumSize(new Dimension(130, 36));
			txtPostcode.setFont(font);
			txtPostcode.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtPlaats().requestFocusInWindow();
					}
				}
			});
			txtPostcode.addFocusListener(focusAdapter);
			txtPostcode.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtPostcode;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtNummer() {
		if (txtNummer == null) {
			txtNummer = new JTextField();
			txtNummer.setPreferredSize(new Dimension(220, 36));
			txtNummer.setMinimumSize(new Dimension(180, 36));
			txtNummer.setFont(font);
			txtNummer.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtPostcode().requestFocusInWindow();
					}
				}
			});
			txtNummer.addFocusListener(focusAdapter);
			txtNummer.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtNummer;
	}

	public JPanel getPanelNummer() {
		if (panelNummer == null) {
			panelNummer = new JPanel();
			panelNummer.setLayout(new GridLayout(1, 2));
			lblNummer = new JLabel();
			lblNummer.setText("Nummer: ");
			Font font = new Font("SansSerif", Font.BOLD, 18);
			lblNummer.setFont(font);
			panelNummer.add(lblNummer);
			panelNummer.add(getTxtNummer());
		}
		return panelNummer;
	}

	public JPanel getPanelBus() {
		if (panelBus == null) {
			panelBus = new JPanel();
			panelBus.setLayout(new GridLayout(1, 2));
			JLabel lblBus = new JLabel();
			lblBus.setText("Bus: ");
			Font font = new Font("SansSerif", Font.BOLD, 18);
			lblBus.setFont(font);
			panelBus.add(lblBus);
			panelBus.add(getTxtBus());
			// panelBus.setPreferredSize(new Dimension(200, 36));
		}
		return panelBus;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtPlaats() {
		if (txtPlaats == null) {
			txtPlaats = new JTextField();
			txtPlaats.setPreferredSize(new Dimension(220, 36));
			txtPlaats.setMinimumSize(new Dimension(180, 36));
			txtPlaats.setFont(font);
			txtPlaats.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtIdentiteitskaart().requestFocusInWindow();
					}
				}
			});
			txtPlaats.addFocusListener(focusAdapter);
			txtPlaats.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtPlaats;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtBus() {
		if (txtBus == null) {
			txtBus = new JTextField();
			// txtBus.setPreferredSize(new Dimension(220, 36));
			txtBus.setFont(font);
		}
		return txtBus;
	}

	/**
	 * This method initializes txtNaam
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getTxtNaam() {
		if (txtNaam == null) {
			txtNaam = new JTextField();
			txtNaam.setPreferredSize(new Dimension(220, 36));
			txtNaam.setMinimumSize(new Dimension(180, 36));
			txtNaam.setFont(font);
			txtNaam.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (!StringUtils.isEmpty(getTxtNaam().getText())
								&& !StringUtils.isEmpty(getTxtVoornaam().getText())
								&& getGeboortedatumPicker().getTime() != null) {
							checkPersonExists();
							getTxtVoornaam().requestFocusInWindow();
						}
					}
				}
			});
			txtNaam.addFocusListener(focusAdapter);
			txtNaam.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtNaam;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtVoornaam() {
		if (txtVoornaam == null) {
			txtVoornaam = new JTextField();
			txtVoornaam.setPreferredSize(new Dimension(220, 36));
			txtVoornaam.setMinimumSize(new Dimension(180, 36));
			txtVoornaam.setFont(font);
			txtVoornaam.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (!StringUtils.isEmpty(getTxtNaam().getText())
								&& !StringUtils.isEmpty(getTxtVoornaam().getText())
								&& getGeboortedatumPicker().getTime() != null) {
							checkPersonExists();
							getTxtGeboorteplaats().requestFocusInWindow();
						}
					}
				}
			});
			txtVoornaam.getDocument().addDocumentListener(zoekPersonenListener);
			txtVoornaam.addFocusListener(focusAdapter);
		}
		return txtVoornaam;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtGeboorteplaats() {
		if (txtGeboortePlaats == null) {
			txtGeboortePlaats = new JTextField();
			txtGeboortePlaats.setPreferredSize(new Dimension(220, 36));
			txtGeboortePlaats.setMinimumSize(new Dimension(180, 36));
			txtGeboortePlaats.setFont(font);
			txtGeboortePlaats.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getGeboortedatumPicker().getJFormattedTextField().requestFocusInWindow();
					}
				}
			});
			txtGeboortePlaats.addFocusListener(focusAdapter);
			txtGeboortePlaats.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtGeboortePlaats;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getTxtLeeftijd() {
		if (txtLeeftijd == null) {
			txtLeeftijd = new JTextField();
			txtLeeftijd.setPreferredSize(new Dimension(50, 36));
			txtLeeftijd.setMinimumSize(new Dimension(50, 36));
			txtLeeftijd.setFont(font);
			// txtLeeftijd.addFocusListener(new LeeftijdFocusListener());
			txtLeeftijd.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtLeeftijd;
	}

	public JTextField getTxtRijksregisternummer() {
		if (txtRijksregisternummer == null) {
			txtRijksregisternummer = new JTextField();
			txtRijksregisternummer.setPreferredSize(new Dimension(140, 36));
			txtRijksregisternummer.setMinimumSize(new Dimension(130, 36));
			txtRijksregisternummer.setFont(font);
			txtRijksregisternummer.getDocument().addDocumentListener(zoekPersonenListener);
			txtRijksregisternummer.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtEmail().requestFocusInWindow();
					}
				}
			});
			txtRijksregisternummer.addFocusListener(focusAdapter);
		}
		return txtRijksregisternummer;
	}

	public JPanel getjPanelPostcode() {
		if (jPanelPostcode == null) {
			jPanelPostcode = new JPanel();
			jPanelPostcode.setLayout(new GridBagLayout());

			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridx = 0;
			gbcLabel.gridy = 0;
			gbcLabel.insets = insets;
			gbcLabel.anchor = GridBagConstraints.EAST;

			GridBagConstraints gbcText = new GridBagConstraints();
			gbcText.gridx = 1;
			gbcText.gridy = 0;
			gbcText.insets = insets;

			lblPostcode = new JLabel();
			lblPostcode.setText("Postcode: ");
			lblPostcode.setFont(font2);

			jPanelPostcode.add(lblPostcode, gbcLabel);
			jPanelPostcode.add(getTxtPostcode(), gbcText);
		}
		return jPanelPostcode;
	}

	public JPanel getjPanelNationaliteit() {
		if (jPanelNationaliteit == null) {
			jPanelNationaliteit = new JPanel();
			jPanelNationaliteit.setLayout(new GridBagLayout());

			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridx = 0;
			gbcLabel.gridy = 0;
			gbcLabel.insets = insets;
			gbcLabel.anchor = GridBagConstraints.EAST;

			GridBagConstraints gbcText = new GridBagConstraints();
			gbcText.gridx = 1;
			gbcText.gridy = 0;
			gbcText.insets = new Insets(5, 0, 5, 5);

			lblNationaliteit = new JLabel();
			lblNationaliteit.setText("Nationaliteit: ");
			lblNationaliteit.setFont(font2);

			jPanelNationaliteit.add(lblNationaliteit, gbcLabel);
			jPanelNationaliteit.add(getTxtNationaliteit(), gbcText);
		}
		return jPanelNationaliteit;
	}

	public JPanel getjPanelVoornaam() {
		if (jPanelVoornaam == null) {
			jPanelVoornaam = new JPanel();
			jPanelVoornaam.setLayout(new GridBagLayout());

			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridx = 0;
			gbcLabel.gridy = 0;
			gbcLabel.insets = insets;
			gbcLabel.anchor = GridBagConstraints.EAST;

			GridBagConstraints gbcText = new GridBagConstraints();
			gbcText.gridx = 1;
			gbcText.gridy = 0;
			gbcText.insets = insets;

			lblVoornaam = new JLabel();
			lblVoornaam.setText("Voornaam: ");
			lblVoornaam.setFont(font2);

			jPanelVoornaam.add(lblVoornaam, gbcLabel);
			jPanelVoornaam.add(getTxtVoornaam(), gbcText);
		}
		return jPanelVoornaam;
	}

	public JPanel getjPanelRijksregisternummer() {
		if (jPanelRijksregisternummer == null) {
			jPanelRijksregisternummer = new JPanel();
			jPanelRijksregisternummer.setLayout(new GridBagLayout());

			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridx = 0;
			gbcLabel.gridy = 0;
			gbcLabel.insets = insets;
			gbcLabel.anchor = GridBagConstraints.EAST;

			GridBagConstraints gbcText = new GridBagConstraints();
			gbcText.gridx = 1;
			gbcText.gridy = 0;
			gbcText.insets = insets;

			lblRijksregisternummer = new JLabel();
			lblRijksregisternummer.setText("Rijksregisternr: ");
			lblRijksregisternummer.setFont(font2);

			jPanelRijksregisternummer.add(lblRijksregisternummer, gbcLabel);
			jPanelRijksregisternummer.add(getTxtRijksregisternummer(), gbcText);
		}
		return jPanelRijksregisternummer;
	}

	public JPanel getjPanelGeboortedatum() {
		if (jPanelGeboortedatum == null) {
			jPanelGeboortedatum = new JPanel();
			jPanelGeboortedatum.setLayout(new GridBagLayout());

			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridx = 0;
			gbcLabel.gridy = 0;
			gbcLabel.insets = insets;
			gbcLabel.anchor = GridBagConstraints.EAST;

			GridBagConstraints gbcText = new GridBagConstraints();
			gbcText.gridx = 1;
			gbcText.gridy = 0;
			gbcText.insets = insets;

			lblGeboortedatum = new JLabel();
			lblGeboortedatum.setText("op: ");
			lblGeboortedatum.setFont(font2);

			jPanelGeboortedatum.add(lblGeboortedatum, gbcLabel);
			jPanelGeboortedatum.add((JComponent) getGeboortedatumPicker(), gbcText);
		}
		return jPanelGeboortedatum;
	}

	public int berekenLeeftijd(Calendar cal) {
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		int age = 0;
		int factor = 0;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date date1 = dateFormat.parse(dateFormat.format(cal.getTime()));
			Date date2 = dateFormat.parse(dateFormat.format(new Date()));
			cal1.setTime(date1);
			cal2.setTime(date2);
			if (cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
				factor = -1;
			}
			age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
		} catch (ParseException e) {

		}
		return age;
		// Calendar now = Calendar.getInstance();
		// now.add(Calendar.YEAR, -cal.get(Calendar.YEAR));
		// now.add(Calendar.MONTH, -cal.get(Calendar.MONTH));
		// now.add(Calendar.DATE, -cal.get(Calendar.DATE));
		// return now.get(Calendar.YEAR);
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtStraat() {
		if (txtStraat == null) {
			txtStraat = new JTextField();
			txtStraat.setPreferredSize(new Dimension(500, 36));
			txtStraat.setMinimumSize(new Dimension(300, 36));
			txtStraat.setFont(font);
			txtStraat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtNummer().requestFocusInWindow();
					}
				}
			});
			txtStraat.addFocusListener(focusAdapter);
			txtStraat.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtStraat;
	}

	public JDatePicker getGeboortedatumPicker() {
		if (geboortedatumPicker == null) {
			geboortedatumPicker = JDateComponentFactory.createJDatePicker();
			geboortedatumPicker.setTextEditable(true);
			geboortedatumPicker.setShowYearButtons(true);
			geboortedatumPicker.getJFormattedTextField().setPreferredSize(new Dimension(150, 36));
			geboortedatumPicker.getJFormattedTextField().setMinimumSize(new Dimension(100, 36));
			geboortedatumPicker.clearTextField();
			geboortedatumPicker.getJFormattedTextField().setFont(font);
			geboortedatumPicker.resetTextFieldToDefaultValue();
			geboortedatumPicker.addActionListener(new DateActionListener());
			geboortedatumPicker.getJFormattedTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtStraat().requestFocusInWindow();
					}
				}
			});
			geboortedatumPicker.getJFormattedTextField().addFocusListener(focusAdapter);
			geboortedatumPicker.getJFormattedTextField().getDocument().addDocumentListener(zoekPersonenListener);
		}
		return geboortedatumPicker;
	}

	private class DateActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())
					&& getGeboortedatumPicker().getTime() != null) {
				checkPersonExists();
				resetLeeftijd();
			}
		}

	}

	private void resetLeeftijd() {
		Calendar cal = Calendar.getInstance();
		cal.set(getGeboortedatumPicker().getModel().getYear(), getGeboortedatumPicker().getModel().getMonth(),
				getGeboortedatumPicker().getModel().getDay());
		int leeftijd = berekenLeeftijd(cal);
		getTxtLeeftijd().setText(Integer.toString(leeftijd));
	}

	public class DateDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			checkPersonExists();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
		}
	}

	private void checkPersonExists() {
		if (zoekEncheckPersonExists) {
			Calendar cal = Calendar.getInstance();
			cal.set(getGeboortedatumPicker().getModel().getYear(), getGeboortedatumPicker().getModel().getMonth(),
					getGeboortedatumPicker().getModel().getDay());
			List<PersoonDTO> personen = persoonService.zoekPersonen(cal, getTxtNaam().getText(),
					getTxtVoornaam().getText());

			if (personen.size() > 0) {
				if (personen.size() == 1) {
					PersoonDTO persoonDTO = personen.get(0);
					ConfirmPersonDialog confirmDialog = new ConfirmPersonDialog(persoonDTO.getNaam() + " "
							+ persoonDTO.getVoornaam(), persoonDTO.getGeboortedatum());
					boolean ok = confirmDialog.isSamePerson();
					if (ok) {
						wisPersoonPanel(false);
						setPersoon(persoonDTO);
						setDataInGUI(getPersoon(), true, true);
						getTxtLeeftijd().setText(Integer.toString(berekenLeeftijd(cal)));
						getTxtNaam().requestFocusInWindow();
						getTxtNaam().selectAll();
						zoekEncheckPersonExists = false;
					}
					confirmDialog.dispose();
				} else {
					new ZoekPersonenResultatenDialog(personen, this, true, true);
					zoekEncheckPersonExists = false;
				}
			}
		}
	}

	/**
	 * This method initializes txtEmail
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new JTextField();
			txtEmail.setPreferredSize(new Dimension(220, 36));
			txtEmail.setMinimumSize(new Dimension(180, 36));
			txtEmail.setFont(font);
			txtEmail.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTaOpmerking().requestFocusInWindow();
					}
				}
			});
			txtEmail.addFocusListener(focusAdapter);
			txtEmail.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtEmail;
	}

	/**
	 * This method initializes taOpmerking
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaOpmerking() {
		if (taOpmerking == null) {
			taOpmerking = new JTextArea();
			taOpmerking.setRows(3);
			taOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
			taOpmerking.setLineWrap(true);
			taOpmerking.setWrapStyleWord(true);
			taOpmerking.setFont(font);
			taOpmerking.setForeground(Color.RED);
			taOpmerking.addFocusListener(focusAdapter);
			taOpmerking.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return taOpmerking;
	}

	public String getOpmerking() {
		return taOpmerking.getText();
	}

	public String getNummerplaat() {
		return txtNrPlaat.getText();
	}

	public JTextField getTxtPersoonId() {
		if (txtPersoonId == null) {
			txtPersoonId = new JTextField();
			txtPersoonId.setPreferredSize(new Dimension(100, 36));
			txtPersoonId.setMinimumSize(new Dimension(100, 36));
			txtPersoonId.setVisible(isPersoonIdVisible);
		}
		return txtPersoonId;
	}

	public JComboBox getCboPositie() {
		if (cboPositie == null) {
			Object[] posities = { "", HuurderPositieEnum.HOOFD, HuurderPositieEnum.ECHTGENOTE, HuurderPositieEnum.ZOON,
					HuurderPositieEnum.DOCHTER, HuurderPositieEnum.SCHOONZOON, HuurderPositieEnum.SCHOONDOCHTER,
					HuurderPositieEnum.CHAUFFEUR, HuurderPositieEnum.ANDERE };
			cboPositie = new JComboBox(posities);
			cboPositie.setFont(font);
		}
		return cboPositie;
	}

	public PersoonDTO getPersoon() {
		return persoon;
	}

	public void setPersoon(PersoonDTO persoon) {
		this.persoon = persoon;
	}

	public RegistratiePanel getRegistratiePanel() {
		return registratiePanel;
	}

	public void setRegistratiePanel(RegistratiePanel registratiePanel) {
		this.registratiePanel = registratiePanel;
	}

	public InschrijvingDTO getInschrijving() {
		return inschrijving;
	}

	public void setInschrijving(InschrijvingDTO inschrijving) {
		this.inschrijving = inschrijving;
	}

	public List<String> getBadges() {
		return badges;
	}

	public void setBadges(List<String> badges) {
		this.badges = badges;
	}

	public String getBadgenummer() {
		return badgenummer;
	}

	public void setBadgenummer(String badgenummer) {
		this.badgenummer = badgenummer;
	}

	public JTextField getTxtIdentiteitskaart() {
		if (txtIdentiteitskaart == null) {
			txtIdentiteitskaart = new JTextField();
			txtIdentiteitskaart.setPreferredSize(new Dimension(220, 36));
			txtIdentiteitskaart.setMinimumSize(new Dimension(180, 36));
			txtIdentiteitskaart.setFont(font);
			txtIdentiteitskaart.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtNationaliteit().requestFocusInWindow();
					}
				}
			});
			txtIdentiteitskaart.addFocusListener(focusAdapter);
			txtIdentiteitskaart.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtIdentiteitskaart;
	}

	public JTextField getTxtNationaliteit() {
		if (txtNationaliteit == null) {
			txtNationaliteit = new JTextField();
			txtNationaliteit.setPreferredSize(new Dimension(140, 36));
			txtNationaliteit.setMinimumSize(new Dimension(130, 36));
			txtNationaliteit.setFont(font);
			txtNationaliteit.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getTxtRijksregisternummer().requestFocusInWindow();
					}
				}
			});
			txtNationaliteit.addFocusListener(focusAdapter);
			txtNationaliteit.getDocument().addDocumentListener(zoekPersonenListener);
		}
		return txtNationaliteit;
	}

	public List<ContactgegevenDTO> getContactGegevensToRemove() {
		if (contactGegevensToRemove == null) {
			contactGegevensToRemove = new ArrayList<ContactgegevenDTO>();
		}
		return contactGegevensToRemove;
	}

	public void setContactGegevensToRemove(List<ContactgegevenDTO> contactGegevensToRemove) {
		this.contactGegevensToRemove = contactGegevensToRemove;
	}

	public List<WagenDTO> getWagensToRemove() {
		if (wagensToRemove == null) {
			wagensToRemove = new ArrayList<WagenDTO>();
		}
		return wagensToRemove;
	}

	public void setWagensToRemove(List<WagenDTO> wagenToRemove) {
		this.wagensToRemove = wagenToRemove;
	}

	public void wisPersoonPanel(boolean clearDate) {
		taOpmerking.setText("");
		txtEmail.setText("");
		txtNaam.setText("");
		txtVoornaam.setText("");
		getRbtnMan().setSelected(false);
		getRbtnVrouw().setSelected(false);
		getCboPositie().setSelectedIndex(0);
		txtGeboortePlaats.setText("");
		if (clearDate) {
			geboortedatumPicker.resetTextFieldToDefaultValue();
		}
		txtLeeftijd.setText("");
		txtStraat.setText("");
		txtNummer.setText("");
		txtPostcode.setText("");
		txtPlaats.setText("");
		txtIdentiteitskaart.setText("");
		txtNationaliteit.setText("");
		txtRijksregisternummer.setText("");
		getjListModelTelefoon().removeAllElements();
		getjListModelNummerplaat().removeAllElements();
		setPersoon(DTOFactory.getPersoonDTO());
		setPersoonFoto(0);
		setContactGegevensToRemove(new ArrayList<ContactgegevenDTO>());
		setWagensToRemove(new ArrayList<WagenDTO>());
	}

	private void resetRegistratie() {
		soortHuurder = null;
		setRegistratiePanel(null);
		setInschrijving(null);
		setBadgenummer(null);
	}

	private SoortHuurderEnum kiesSoortHuurder() {
		JRadioButton jRBtnVast = new JRadioButton("VAST");
		JRadioButton jRBtnLos = new JRadioButton("LOS");
		ButtonGroup groupVastLos = new ButtonGroup();
		groupVastLos.add(jRBtnVast);
		groupVastLos.add(jRBtnLos);
		Object[] arrVastLos = { "KIES TYPE HUURDER", jRBtnVast, jRBtnLos };
		SoortHuurderEnum soortHuurder = null;
		int i = JOptionPane.showConfirmDialog(null, arrVastLos, "", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (i == 0) {
			if (jRBtnVast.isSelected()) {
				soortHuurder = SoortHuurderEnum.VAST;
				Calendar calVan = Calendar.getInstance();
				Calendar calTot = Calendar.getInstance();
				Calendar calEinde = Calendar.getInstance();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MM-yyyy");
				int jaar = calVan.get(Calendar.YEAR);
				calEinde.set(15, 9, jaar);
				try {
					if (calVan.after(calEinde)) {
						calTot.setTime(formatter.parse("15-10-" + jaar));
					} else {
						calTot.setTime(formatter.parse("15-10-" + (jaar + 1)));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (jRBtnLos.isSelected()) {
				soortHuurder = SoortHuurderEnum.LOS;
			}
		}

		return soortHuurder;
	}

	public boolean isSoortHuurderGekozen() {
		boolean ret = false;
		if (soortHuurder != null) {
			ret = true;
		}
		return ret;
	}

	private JPanel getJPanelNrPlaatGegevens() {
		if (jPanelNrPlaatGegevens == null) {
			jPanelNrPlaatGegevens = new JPanel();
			jPanelNrPlaatGegevens.setLayout(new BorderLayout());
			JPanel jPanelLabels = new JPanel();
			jPanelLabels.setLayout(new GridLayout(4, 1));
			jPanelLabels.add(new JLabel("Nummerplaat: "));
			jPanelLabels.add(new JLabel("Merk: "));
			jPanelLabels.add(new JLabel("Sticker: "));
			jPanelLabels.add(new JLabel("Opmerking: "));
			JPanel jPanelTextFields = new JPanel();
			jPanelTextFields.setLayout(new GridLayout(4, 1));
			jPanelTextFields.add(getjTxtNummerplaat());
			jPanelTextFields.add(getjTxtMerk());
			jPanelTextFields.add(getjTxtSticker());
			jPanelTextFields.add(getjTxtOpmerking());
			jPanelNrPlaatGegevens.add(jPanelLabels, BorderLayout.WEST);
			jPanelNrPlaatGegevens.add(jPanelTextFields, BorderLayout.CENTER);
		}
		return jPanelNrPlaatGegevens;
	}

	private void setJPanelNrPlaatGegevens(JPanel jPanel) {
		this.jPanelNrPlaatGegevens = jPanel;
	}

	private void resetJPanelNrPlaatsGegevens() {
		getjTxtNummerplaat().setText("");
		getjTxtMerk().setText("");
		getjTxtSticker().setText("");
		getjTxtOpmerking().setText("");
		setJPanelNrPlaatGegevens(null);
		// carFocusListener.setHadFocus(false);
	}

	private List<InschrijvingPersoonDTO> sortInschrijvingPersonen(Set<InschrijvingPersoonDTO> inschrijvingPersonen) {
		List<InschrijvingPersoonDTO> sortedInschrijvingPersonen = new ArrayList<InschrijvingPersoonDTO>();
		for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijvingPersonen) {
			boolean added = false;
			for (InschrijvingPersoonDTO tmpInschrijvingPersoon : sortedInschrijvingPersonen) {
				if (inschrijvingPersoon.getInschrijvingDatum() < tmpInschrijvingPersoon.getInschrijvingDatum()) {
					int index = sortedInschrijvingPersonen.indexOf(tmpInschrijvingPersoon);
					sortedInschrijvingPersonen.add(index, inschrijvingPersoon);
					added = true;
					break;
				}
			}
			if (!added) {
				sortedInschrijvingPersonen.add(inschrijvingPersoon);
			}
		}
		return sortedInschrijvingPersonen;
	}

	public void vernieuwInschrijving(int inschrijvingId) {
		InschrijvingDTO tmpInschrijving = standplaatsService.getInschrijving(inschrijvingId);
		InschrijvingDTO nieuweInschrijving = new InschrijvingDTO();
		nieuweInschrijving.setBadge(tmpInschrijving.getBadge());
		nieuweInschrijving.setSoorthuurder(tmpInschrijving.getSoorthuurder());
		nieuweInschrijving.setDateVan(tmpInschrijving.getDateVan());
		nieuweInschrijving.setDateTot(tmpInschrijving.getDateTot());
		nieuweInschrijving.setFichenummer(tmpInschrijving.getFichenummer());
		List<InschrijvingPersoonDTO> inschrijvingPersonen = sortInschrijvingPersonen(tmpInschrijving
				.getInschrijvingPersonen());
		for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijvingPersonen) {
			InschrijvingPersoonDTO nieuweInschrijvingPersoon = DTOFactory.getInschrijvingPersoonDTO();
			nieuweInschrijvingPersoon.setGezinsPositie(inschrijvingPersoon.getGezinsPositie());
			nieuweInschrijvingPersoon.setHuurdersPositie(inschrijvingPersoon.getHuurdersPositie());
			nieuweInschrijvingPersoon.setPersoon(inschrijvingPersoon.getPersoon());
			nieuweInschrijving.getInschrijvingPersonen().add(nieuweInschrijvingPersoon);
		}

		setInschrijving(nieuweInschrijving);
		setRegistratiePanel(new RegistratiePanel(getInschrijving().getSoorthuurder(), getInschrijving(), getBadges(),
				getController(), getController().getStandplaatsDTO().getId(), standplaatsService, badgeService));
		getRegistratiePanel().setPersoon(getPersoon());
		getRegistratiePanel().setDataInGui();
		soortHuurder = tmpInschrijving.getSoorthuurder();
		Object[] array = { getRegistratiePanel() };

		int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ret == 0) {
			setBadgenummer((String) getRegistratiePanel().getJ2aKaartnummers().getSelectedItem());
			if (getInschrijving().getSoorthuurder().equals(SoortHuurderEnum.LOS)
					&& !StringUtils.isEmpty(getBadgenummer())) {
				Optional<BadgeDTO> badge = badgeService.findBadge(badgenummer);
				getInschrijving().setBadge(badge.get());
			}
			getInschrijving().setDateVan(getRegistratiePanel().getjDPAankomst().getTime());
			getInschrijving().setDateTot(getRegistratiePanel().getjDPVertrek().getTime());
			// zoekStandplaatsVoorPersoon = false;
		} else {
			resetRegistratie();
		}
	}

	private void registratie(SoortHuurderEnum soortHuurder) {
		if (getRegistratiePanel() == null) {
			setRegistratiePanel(new RegistratiePanel(soortHuurder, getInschrijving(), getBadges(),
					getController(), getController().getStandplaatsDTO().getId(), standplaatsService, badgeService));
		}

		getRegistratiePanel().setPersoon(getPersoon());

		Object[] array = { getRegistratiePanel() };

		int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ret == 0) {
			InschrijvingPersoonDTO inschrijvingPersoon = getRegistratiePanel().getInschrijvingPersoon();
			if (inschrijvingPersoon != null) {
				inschrijvingPersoon.setPersoon(getPersoon());
				// TODO : badge opslaan bij inschrijvingpersoon en slechts
				// ��nmaal, globale var badgenummer nog nodig
				setBadgenummer((String) getRegistratiePanel().getJ2aKaartnummers().getSelectedItem());
				if (getInschrijving().getSoorthuurder().equals(SoortHuurderEnum.LOS)
						&& !StringUtils.isEmpty(getBadgenummer())) {
					Optional<BadgeDTO> badge = badgeService.findBadge(badgenummer);
					getInschrijving().setBadge(badge.get());
				}
				getInschrijving().getInschrijvingPersonen().add(inschrijvingPersoon);
				getInschrijving().setDateVan(getRegistratiePanel().getjDPAankomst().getTime());
				getInschrijving().setDateTot(getRegistratiePanel().getjDPVertrek().getTime());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == getBtnSelect()) {
				if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())
						&& !StringUtils.isEmpty(getGeboortedatumPicker().getJFormattedTextField().getText())) {

					boolean persoonGewijzigd = checkPersoonGewijzigd();
					if (persoonGewijzigd) {
						String message = "De gegevens van ";
						message += getTxtNaam().getText() + " " + getTxtVoornaam().getText();
						message += " werden gewijzigd. Wenst u deze wijzigingen op te slaan?";
						SaveDialog dialog = new SaveDialog(message);
						boolean ok = dialog.isUnsavedChanges();
						if (ok) {
							savePersoon();
						}
						dialog.dispose();
					}
				}

				if (getInschrijving() == null) {
					setInschrijving(new InschrijvingDTO());
				}

				if (!isSoortHuurderGekozen()) {
					soortHuurder = kiesSoortHuurder();
					if (soortHuurder != null) {
						getInschrijving().setSoorthuurder(soortHuurder);
					} else {
						resetRegistratie();
					}
				}

				if (soortHuurder != null
						&& (soortHuurder.equals(SoortHuurderEnum.VAST) || soortHuurder.equals(SoortHuurderEnum.LOS))) {
					registratie(soortHuurder);
					// zoekStandplaatsVoorPersoon = false;
				}
			} else if (e.getSource() == getBtnTelephone()) {
				JTextField jTxtTelefoon = new JTextField();
				jTxtTelefoon.setFont(font);
				jTxtTelefoon.setPreferredSize(new Dimension(150, 28));
				jTxtTelefoon.setMinimumSize(new Dimension(120, 28));
				jTxtTelefoon.addAncestorListener(new RequestFocusListener());
				JPanel jPanelTelefoon = new JPanel();
				jPanelTelefoon.add(new JLabel("TELEFOONNUMMER: "));
				jPanelTelefoon.add(jTxtTelefoon);
				Object[] array = { jPanelTelefoon };
				int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtTelefoon.getText())) {
					int pos = getjListTelefoon().getModel().getSize();
					getjListModelTelefoon().add(pos, jTxtTelefoon.getText());
				}
			} else if (e.getSource() == getBtnEditTel()) {
				String telNr = (String) getjListTelefoon().getSelectedValue();
				if (!StringUtils.isEmpty(telNr)) {
					int pos = getjListTelefoon().getSelectedIndex();
					JTextField jTxtTelefoon = new JTextField();
					jTxtTelefoon.setFont(font);
					jTxtTelefoon.setPreferredSize(new Dimension(150, 28));
					jTxtTelefoon.setMinimumSize(new Dimension(120, 28));
					jTxtTelefoon.addAncestorListener(new RequestFocusListener());
					jTxtTelefoon.setText(telNr);
					JPanel jPanelTelefoon = new JPanel();
					jPanelTelefoon.add(new JLabel("TELEFOONNUMMER: "));
					jPanelTelefoon.add(jTxtTelefoon);
					Object[] array = { jPanelTelefoon };
					int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtTelefoon.getText())) {
						getjListModelTelefoon().removeElementAt(pos);
						getjListModelTelefoon().add(pos, jTxtTelefoon.getText());
						for (ContactgegevenDTO contactGegeven : getPersoon().getContactgegevens()) {
							if (ContactgegevenTypeEnum.TELEFOON.equals(contactGegeven.getContactgegevenType())
									&& telNr.equals(contactGegeven.getWaarde())) {
								contactGegeven.setWaarde(jTxtTelefoon.getText());
							}
						}
					}
				}
			} else if (e.getSource() == getBtnRemoveTel()) {
				int i = getjListTelefoon().getSelectedIndex();
				String tel = (String) getjListTelefoon().getSelectedValue();
				ContactgegevenDTO contactgegeven = containsContactgegeven(getPersoon().getContactgegevens(), tel);
				getPersoon().getContactgegevens().remove(contactgegeven);
				if (contactgegeven != null) {
					getContactGegevensToRemove().add(contactgegeven);
				}
				getjListModelTelefoon().remove(i);
			} else if (e.getSource() == getBtnSave()) {
				try {
					save();
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					ex.printStackTrace();
				}
			} else if (e.getSource() == getBtnSavePersoon()) {
				savePersoon();
			} else if (e.getSource() == getBtnAfdrukkenpersoon()) {
				PersoonPdf persoonPdf = new PersoonPdf();
				ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
				String dir = "";
				if (configuratie != null) {
					dir = configuratie.getWaarde() + "/personen/" + getPersoon().getId() + ".jpg";
				}
				persoonPdf.createDocument(getPersoon(), dir);

			} else if (e.getSource() == getBtnNrPlaat()) {
				resetJPanelNrPlaatsGegevens();
				Object[] array = { getJPanelNrPlaatGegevens() };
				getjTxtNummerplaat().addAncestorListener(new RequestFocusListener());
				int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ret == 0) {
					/** add value to JListWagen */
					if (getjTxtNummerplaat().getText().length() > 0) {
						WagenDTO wagen = new WagenDTO();
						wagen.setNummerplaat(getjTxtNummerplaat().getText());
						wagen.setMerk(getjTxtMerk().getText());
						wagen.setSticker(getjTxtSticker().getText());
						wagen.setOpmerking(getjTxtOpmerking().getText());
						getPersoon().getWagens().add(wagen);
						int pos = getjListNummerplaat().getModel().getSize();
						getjListModelNummerplaat().add(pos, wagen.getNummerplaat());
						zoekPersonen();
					}
				}
			} else if (e.getSource() == getBtnEditNrPlaat()) {
				resetJPanelNrPlaatsGegevens();
				String nrPlaat = (String) getjListNummerplaat().getSelectedValue();
				if (!StringUtils.isEmpty(nrPlaat)) {
					WagenDTO wagen = null;
					for (WagenDTO tmpWagen : getPersoon().getWagens()) {
						if (tmpWagen.getNummerplaat().equals(nrPlaat)) {
							wagen = tmpWagen;
							break;
						}
					}
					getjTxtNummerplaat().setText(wagen.getNummerplaat());
					getjTxtMerk().setText(wagen.getMerk());
					getjTxtSticker().setText(wagen.getSticker());
					getjTxtOpmerking().setText(wagen.getOpmerking());
					Object[] array = { getJPanelNrPlaatGegevens() };
					getjTxtNummerplaat().addAncestorListener(new RequestFocusListener());
					int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (ret == 0) {
						/* add value to JListWagen */
						/**
						 * TODO: afwerken voor het wijzigen van de wagen gegevens
						 */
						wagen.setNummerplaat(getjTxtNummerplaat().getText());
						wagen.setMerk(getjTxtMerk().getText());
						wagen.setSticker(getjTxtSticker().getText());
						wagen.setOpmerking(getjTxtOpmerking().getText());
						getPersoon().getWagens().add(wagen);
						int pos = getjListNummerplaat().getSelectedIndex();
						getjListModelNummerplaat().removeElementAt(pos);
						getjListModelNummerplaat().add(pos, wagen.getNummerplaat());
						zoekPersonen();
					}
					jTxtNummerplaat = null;
					jPanelNrPlaatGegevens = null;
				}
			} else if (e.getSource() == getBtnRemoveNrPlaat()) {
				int i = getjListNummerplaat().getSelectedIndex();
				String nrPlaat = (String) getjListNummerplaat().getSelectedValue();
				getjListModelNummerplaat().remove(i);
				for (WagenDTO wagen : getPersoon().getWagens()) {
					if (wagen.getNummerplaat().equals(nrPlaat)) {
						// getPersoon().getWagens().remove(wagen);
						getWagensToRemove().add(wagen);
						for (WagenDTO tmpWagen : getPersoon().getWagens()) {
							if (tmpWagen.getId() == wagen.getId()) {
								getPersoon().getWagens().remove(tmpWagen);
								break;
							}
						}
						break;
					}
				}
				zoekPersonen();
			} else if (e.getSource() == getBtnWisPersoon()) {
				if (!StringUtils.isEmpty(getTxtNaam().getText()) && !StringUtils.isEmpty(getTxtVoornaam().getText())
						&& !StringUtils.isEmpty(getGeboortedatumPicker().getJFormattedTextField().getText())) {
					boolean persoonGewijzigd = checkPersoonGewijzigd();
					if (persoonGewijzigd) {
						String message = "De gegevens van ";
						message += getTxtNaam().getText() + " " + getTxtVoornaam().getText();
						message += " werden gewijzigd. Wenst u deze wijzigingen op te slaan?";
						SaveDialog dialog = new SaveDialog(message);
						boolean ok = dialog.isUnsavedChanges();
						if (ok) {
							savePersoon();
						}
						dialog.dispose();
					}
				}

				wisPersoonPanel(true);
				JList list = new JList();
				list.setListData(new Vector<String>());
				actionJListPersonen.setActionList(list);
				getJListPersonen().setListData(new Vector<String>());
				getTxtNaam().requestFocusInWindow();
				getTxtNaam().selectAll();
				zoekEncheckPersonExists = true;
			} else if (e.getSource() == getBtnWisRegistratie()) {
				// TODO nog popup om te vragen als zeker
				Object[] arr = { "Bent u zeker dat u de registratie wil resetten?" };
				int i = JOptionPane.showConfirmDialog(null, arr, "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (i == JOptionPane.YES_OPTION) {
					resetRegistratie();
					// zoekStandplaatsVoorPersoon = true;
				}
			} else if (e.getSource() == getBtnKaartlezer()) {
				savePersoon();
				wisPersoonPanel(true);
				zoekEncheckPersonExists = false;

				new SwingWorker<Boolean, String>() {

					@Override
					protected Boolean doInBackground() throws Exception {
						int standplaatsId = getController().getStandplaatsDTO().getId();
						Integer persoonId = null;
						boolean cardPersoonDataProcessed = false;
						boolean cardPersoonPhotoProcessed = false;
						while (!cardPersoonDataProcessed || !cardPersoonPhotoProcessed) {
							if (eidController.getState() == PCSCEidController.STATE.EID_PRESENT
									|| eidController.getState() == PCSCEidController.STATE.EID_YIELDED
									|| eidController.getState() == PCSCEidController.STATE.FILE_LOADED) {
								if (eidController.hasIdentity() && eidController.hasAddress()
										&& !cardPersoonDataProcessed) {
									try {
										PersoonDTO cardPersoon = new PersoonDTO();
										cardPersoon = addEidDataToPersoonData(cardPersoon);
										cardPersoon = addAddressDataToPersoonData(cardPersoon);
										Calendar cal = Calendar.getInstance();
										cal.setTime(cardPersoon.getGeboortedatum());
										List<PersoonDTO> personen = persoonService.zoekPersonen(cal,
												cardPersoon.getNaam(), cardPersoon.getVoornaam());

										if (personen.size() == 1) {
											PersoonDTO persoonDTO = personen.get(0);
											ConfirmPersonDialog confirmDialog = new ConfirmPersonDialog(
													persoonDTO.getNaam() + " " + persoonDTO.getVoornaam(),
													persoonDTO.getGeboortedatum());
											boolean ok = confirmDialog.isSamePerson();
											confirmDialog.dispose();
											if (ok) {
												setPersoon(persoonDTO);
												getPersoon().setNationaliteit(cardPersoon.getNationaliteit());
												getPersoon().setGeboortedatum(cardPersoon.getGeboortedatum());
												AdresDTO adresDTO = new AdresDTO();
												adresDTO.setStraat(cardPersoon.getAdresDTO().getStraat());
												adresDTO.setPostcode(cardPersoon.getAdresDTO().getPostcode());
												adresDTO.setPlaats(cardPersoon.getAdresDTO().getPlaats());
												adresDTO.setLand(cardPersoon.getAdresDTO().getLand());
												getPersoon().setAdresDTO(adresDTO);
												getPersoon().setIdentiteitskaartnummer(
														cardPersoon.getIdentiteitskaartnummer());
												// if
												// (zoekStandplaatsVoorPersoon)
												// {
												// updateStandplaatsVoorPersoon(getPersoon().getId());
												// standplaatsId =
												// getController().getStandplaatsDTO().getId();
												// }
											} else {
												updatePersoon(cardPersoon);
											}
										} else if (personen.size() > 1) {
											ZoekPersonenKaartResultatenDialog zoekPersonenResultatenDialog = new ZoekPersonenKaartResultatenDialog(
													personen);
											if (zoekPersonenResultatenDialog.isReturnValue()) {
												setPersoon(zoekPersonenResultatenDialog.getPersoonDTO());
												getPersoon().setNationaliteit(cardPersoon.getNationaliteit());
												getPersoon().setGeboortedatum(cardPersoon.getGeboortedatum());
												AdresDTO adresDTO = new AdresDTO();
												adresDTO.setStraat(cardPersoon.getAdresDTO().getStraat());
												adresDTO.setPostcode(cardPersoon.getAdresDTO().getPostcode());
												adresDTO.setPlaats(cardPersoon.getAdresDTO().getPlaats());
												adresDTO.setLand(cardPersoon.getAdresDTO().getLand());
												getPersoon().setAdresDTO(adresDTO);
												getPersoon().setIdentiteitskaartnummer(
														cardPersoon.getIdentiteitskaartnummer());
												// if
												// (zoekStandplaatsVoorPersoon)
												// {
												// updateStandplaatsVoorPersoon(getPersoon().getId());
												// standplaatsId =
												// getController().getStandplaatsDTO().getId();
												// }
											} else {
												updatePersoon(cardPersoon);
											}
										} else {
											updatePersoon(cardPersoon);
										}
										setDataInGUI(getPersoon(), true, false);
										savePersoon();
										persoonId = getPersoon().getId();
										setContactGegevensToRemove(new ArrayList<ContactgegevenDTO>());
										setWagensToRemove(new ArrayList<WagenDTO>());
										cardPersoonDataProcessed = true;
									} catch (Exception e) {
										e.printStackTrace();
										Thread.sleep(500);
									}
								}

								if (eidController.hasPhoto() && !cardPersoonPhotoProcessed) {
									try {
										byte[] cardPictureBytes = eid.getPhotoJPEG();
										savePhoto(cardPictureBytes, standplaatsId, persoonId);
										cardPersoonPhotoProcessed = true;
									} catch (Exception e) {
										Thread.sleep(1000);
										if (eidController.getState() != PCSCEidController.STATE.EID_PRESENT) {
											e.printStackTrace();
										}
									}
								}
							} else {
								Thread.sleep(2000);
								if (eidController.getState() == PCSCEidController.STATE.NO_READERS
										|| eidController.getState() == PCSCEidController.STATE.NO_EID_PRESENT) {
									break;
								}
							}
							Thread.sleep(200);
						}
						return true;
					}
				}.execute();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updatePersoon(PersoonDTO newPersoon) {
		getPersoon().setNaam(newPersoon.getNaam());
		getPersoon().setVoornaam(newPersoon.getVoornaam());
		getPersoon().setGeslacht(newPersoon.getGeslacht());
		getPersoon().setGeboortedatum(newPersoon.getGeboortedatum());
		getPersoon().setGeboorteplaats(newPersoon.getGeboorteplaats());
		getPersoon().setNationaliteit(newPersoon.getNationaliteit());
		getPersoon().setRijksregisternummer(newPersoon.getRijksregisternummer());
		getPersoon().setIdentiteitskaartnummer(newPersoon.getIdentiteitskaartnummer());
		AdresDTO adresDTO = new AdresDTO();
		adresDTO.setStraat(newPersoon.getAdresDTO().getStraat());
		adresDTO.setPostcode(newPersoon.getAdresDTO().getPostcode());
		adresDTO.setPlaats(newPersoon.getAdresDTO().getPlaats());
		adresDTO.setLand(newPersoon.getAdresDTO().getLand());
		getPersoon().setAdresDTO(adresDTO);
	}

	public void savePhoto(byte[] pictureBytes, final Integer standplaatsId, final Integer persoonId) {
		if (null == persoonId || 0 == persoonId) {
			System.out.println("ERROR: Trying to save photo while person without id");
			return;
		}
		InputStream in = null;
		try {
			getPanelStandplaatsPhoto().removeAll();
			if (null != standplaatsId && standplaatsId == getController().getStandplaatsDTO().getId()) {
				JLabel lblImage = new JLabel();
				ImageIcon imageIcon = new ImageIcon(pictureBytes);
				lblImage.setIcon(imageIcon);
				getPanelStandplaatsPhoto().add(lblImage);
			}
			try {
				// convert byte array back to BufferedImage
				in = new ByteArrayInputStream(pictureBytes);
				BufferedImage bImageFromConvert = ImageIO.read(in);
				ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
				if (configuratie != null) {
					String dir = configuratie.getWaarde() + "/personen/" + persoonId + ".jpg";
					ImageIO.write(bImageFromConvert, "jpg", new File(dir));
				}
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.error(ex);
		}
	}

	public void toonInschrijving(int inschrijvingId, SoortHuurderEnum soortHuurder) {
		setDataInGUI();
		InschrijvingDTO inschrijving = null;
		for (InschrijvingDTO tmpInschrijving : getController().getStandplaatsDTO().getInschrijvingen()) {
			if (inschrijvingId == tmpInschrijving.getId()) {
				inschrijving = tmpInschrijving;
				break;
			}
		}
		setRegistratiePanel(new RegistratiePanel(soortHuurder, inschrijving, getBadges(),
				getController(), getController().getStandplaatsDTO().getId(), standplaatsService, badgeService));
		registratie(soortHuurder);
	}

	private void setPanelStandplaatsPhoto(JPanel jPanel) {
		this.panelStandplaatsPhoto = jPanel;
	}

	private JPanel getPanelStandplaatsPhoto() {
		if (panelStandplaatsPhoto == null) {
			panelStandplaatsPhoto = new JPanel();
			panelStandplaatsPhoto.setLayout(new BorderLayout());
			Dimension dim = new Dimension(140, 200);
			panelStandplaatsPhoto.setPreferredSize(dim);
			panelStandplaatsPhoto.setMinimumSize(dim);
			panelStandplaatsPhoto.setBorder(new LineBorder(Color.BLACK, 1));
		}
		return panelStandplaatsPhoto;
	}

	private void setPersoonFoto(int persoonId) {
		ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
		if (configuratie != null) {
			getPersonNorthPanel().remove(getPanelStandplaatsPhoto());
			setPanelStandplaatsPhoto(null);
			String dir = configuratie.getWaarde() + "/personen/" + persoonId + ".jpg";
			getPanelStandplaatsPhoto().add(new ShowImage(dir));

			GridBagConstraints gridBagConstraintsTxtFoto = new GridBagConstraints();
			gridBagConstraintsTxtFoto.gridx = 4;
			gridBagConstraintsTxtFoto.gridy = 2;
			gridBagConstraintsTxtFoto.gridheight = 5;
			gridBagConstraintsTxtFoto.gridwidth = 2;
			Insets insets = new Insets(5, 10, 10, 0);
			gridBagConstraintsTxtFoto.insets = insets;
			gridBagConstraintsTxtFoto.anchor = GridBagConstraints.NORTHWEST;

			getPersonNorthPanel().add(getPanelStandplaatsPhoto(), gridBagConstraintsTxtFoto);
		}
	}

	public class ShowImage extends Panel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4474726615381635853L;
		BufferedImage image;

		public ShowImage(String path) {
			File file = new File(path);
			if (file.exists()) {
				try {
					File input = new File(path);
					image = ImageIO.read(input);
				} catch (IOException ie) {
					System.out.println("Error:" + ie.getMessage());
				}
			}
		}

		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}

	public class ZoekPersonenListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			zoekPersonen();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			zoekPersonen();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			zoekPersonen();
		}
	}

	private class PersonFocusAdapter extends FocusAdapter {

		public PersonFocusAdapter() {
		}

		@Override
		public void focusGained(FocusEvent e) {
			super.focusGained(e);

			Object obj = e.getSource();
			if (obj instanceof JTextField) {
				((JTextField) obj).selectAll();
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (e.getSource().equals(getGeboortedatumPicker().getJFormattedTextField())) {

				String sDate = getGeboortedatumPicker().getJFormattedTextField().getText();
				sDate = sDate.replace("/", "-");

				if (!formatAndSetLeeftijd(sDate, new SimpleDateFormat("dd-MM-yyyy"))) {
					formatAndSetLeeftijd(sDate, new SimpleDateFormat("dd-MMM-yyyy"));
				}

				getTxtStraat().requestFocusInWindow();
			}
		}
	}

	private boolean formatAndSetLeeftijd(final String sDate, final DateFormat formatter) {
		boolean ok = false;
		try {
			setLeeftijd(formatter.parse(sDate));
			ok = true;
		} catch (ParseException pe) {
			// TODO Auto-generated catch block
			System.err.println("date not correctly parsed");
		}
		return ok;
	}

	private void setLeeftijd(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int leeftijd = berekenLeeftijd(cal);
		getTxtLeeftijd().setText(Integer.toString(leeftijd));
		getGeboortedatumPicker().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
	}

	@Override
	public void addDetailMessage(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTestResult(DiagnosticTests arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getParentComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void increaseProgress() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean privacyQuestion(boolean arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetProgress(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProgressIndeterminate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatusMessage(Status arg0, MESSAGE_ID arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable o, Object arg) {

	}

	private PersoonDTO addEidDataToPersoonData(final PersoonDTO persoon) throws Exception {
		Identity identity = eid.getIdentity();
		persoon.setNaam(identity.getName());
		persoon.setVoornaam(identity.getFirstName() + " " + identity.getMiddleName());
		persoon.setNationaliteit(identity.getNationality());
		persoon.setGeboorteplaats(identity.getPlaceOfBirth());
		persoon.setGeslacht((identity.getGender().equals(Gender.MALE)) ? GeslachtEnum.M : GeslachtEnum.V);
		persoon.setGeboortedatum(identity.getDateOfBirth().getTime());
		persoon.setIdentiteitskaartnummer(identity.getCardNumber());
		persoon.setRijksregisternummer(identity.getNationalNumber());
		return persoon;
	}

	private PersoonDTO addAddressDataToPersoonData(final PersoonDTO persoon) throws Exception {
		Address address = eid.getAddress();
		AdresDTO adres = new AdresDTO();
		adres.setLand(LandEnum.BE);
		adres.setPlaats(address.getMunicipality());
		adres.setStraat(address.getStreetAndNumber());
		adres.setPostcode(address.getZip());
		persoon.setAdresDTO(adres);
		return persoon;
	}

	// private String fixChars(String input)
	// {
	// byte[] byInput;
	// byInput = System.Text.Encoding.Default.GetBytes(input);
	// input = System.Text.Encoding.UTF8.GetString(byInput);
	//
	// return input;
	// }

	private String fixChars(String input) throws UnsupportedEncodingException {
		byte[] byInput = input.getBytes();
		return new String(byInput, "UTF-8");
	}
}