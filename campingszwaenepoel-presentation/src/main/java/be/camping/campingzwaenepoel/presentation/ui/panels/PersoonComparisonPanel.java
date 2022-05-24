package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.ui.panels.helppanels.PersoonDataPanel;
import be.camping.campingzwaenepoel.presentation.ui.panels.helppanels.PersoonListPanel;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;
import be.camping.campingzwaenepoel.service.transfer.ContactgegevenDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.WagenDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

public class PersoonComparisonPanel extends JPanel implements ActionListener {

	@Autowired
	private ConfiguratieService configuratieService;

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PersoonComparisonPanel.class);

	public PersoonService getPersoonService() {
		return persoonService;
	}

	public void setPersoonService(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	private JPanel personChoicePanel;
	private JRadioButton rBtnFromPerson;
	private JRadioButton rBtnToPerson;
	private PersoonDataPanel fromPersoonDataPanel;
	private PersoonDataPanel toPersoonDataPanel;
	private PersoonListPanel persoonListPanel;
	private JButton btnChangePerson;
	private JButton btnWisFromPerson;
	private JButton btnWisToPerson;

	private List<Integer> gezochtePersonen;

	private Controller controller;
	@Autowired
	private PersoonService persoonService;
	private ActionJListPersonen actionJListPersonen;

	Insets insets = new Insets(5, 0, 0, 5);
	private final Font font = new Font("Times New Roman", Font.PLAIN, 18);

	// private ZoekPersonenListener zoekPersonenListener;

	private StandplaatsService standplaatsService;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}

	public void setStandplaatsService(StandplaatsService standplaatsService) {
		this.standplaatsService = standplaatsService;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		initializeComponents();

		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK, 1));
		// zoekPersonenListener = new ZoekPersonenListener();
		// this.add(getPersonCentralPanel());

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(personChoicePanel, BorderLayout.NORTH);

		JPanel centralPanel = new JPanel(new GridLayout(1, 2));
		centralPanel.add(fromPersoonDataPanel);
		centralPanel.add(toPersoonDataPanel);
		panel.add(new JScrollPane(centralPanel), BorderLayout.CENTER);

		this.add(panel, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		buttonPanel.add(btnChangePerson);
		buttonPanel.add(btnWisFromPerson);
		buttonPanel.add(btnWisToPerson);
		leftPanel.add(buttonPanel, BorderLayout.NORTH);
		leftPanel.add(persoonListPanel, BorderLayout.CENTER);

		this.add(leftPanel, BorderLayout.WEST);

		ZoekPersonenListener zoekPersonenListener = new ZoekPersonenListener();

		fromPersoonDataPanel.setDocumentListener(zoekPersonenListener);
		toPersoonDataPanel.setDocumentListener(zoekPersonenListener);
	}

	private void initializeComponents() {
		ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
		String fileDirectory = configuratie.getWaarde() + "/personen/";
		fromPersoonDataPanel = new PersoonDataPanel(fileDirectory);
		toPersoonDataPanel = new PersoonDataPanel(fileDirectory);
		persoonListPanel = new PersoonListPanel();

		personChoicePanel = new JPanel();
		personChoicePanel.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(1, 2));

		ButtonGroup group = new ButtonGroup();
		rBtnFromPerson = new JRadioButton("Te vervangen persoon.");
		rBtnFromPerson.setActionCommand("Te vervangen persoon");
		rBtnFromPerson.setFont(font);
		rBtnToPerson = new JRadioButton("Doel persoon.");
		rBtnToPerson.setActionCommand("Doel persoon");
		rBtnToPerson.setFont(font);
		group.add(rBtnFromPerson);
		group.add(rBtnToPerson);

		panel.add(rBtnFromPerson);
		panel.add(rBtnToPerson);

		rBtnFromPerson.setSelected(true);

		personChoicePanel.add(panel, BorderLayout.CENTER);

		btnChangePerson = new JButton("Persoon aanpassen");
		btnChangePerson.setPreferredSize(new Dimension(160, 40));
		btnChangePerson.addActionListener(this);

		btnWisFromPerson = new JButton("Wis scherm persoon links");
		btnWisFromPerson.setPreferredSize(new Dimension(160, 40));
		btnWisFromPerson.addActionListener(this);

		btnWisToPerson = new JButton("Wis scherm persoon rechts");
		btnWisToPerson.setPreferredSize(new Dimension(160, 40));
		btnWisToPerson.addActionListener(this);

		actionJListPersonen = new ActionJListPersonen(null);
		persoonListPanel.getjListPersonen().addMouseListener(actionJListPersonen);
	}

	class ActionJListPersonen extends MouseAdapter {
		protected JList list;

		public ActionJListPersonen(JList l) {
			list = l;
		}

		public void setActionList(JList l) {
			list = l;
		}

		// public void mouseClicked(MouseEvent e, Calendar gezochtePersonen) {
		// if (e.getClickCount() == 2) {
		// try {
		// int index = list.locationToIndex(e.getPoint());
		// logger.info("double click met index: " + index);
		// if (index >= 0) {
		//
		// PersoonDTO persoonDTO = getPersoonController().getPersoon(gezochtePersonen.get(index));
		//
		// if (rBtnFromPerson.isSelected()) {
		// fromPersoonDataPanel.wisPersoonPanel(true);
		// fromPersoonDataPanel.setDataInGUI(persoonDTO);
		// fromPersoonDataPanel.setPersoon(persoonDTO);
		// } else {
		// toPersoonDataPanel.wisPersoonPanel(true);
		// toPersoonDataPanel.setDataInGUI(persoonDTO);
		// toPersoonDataPanel.setPersoon(persoonDTO);
		// }
		// }
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// logger.error(e);
		// }
		// }
		// }

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				try {
					int index = list.locationToIndex(e.getPoint());
					if (index >= 0) {
						if (rBtnFromPerson.isSelected()) {
							fromPersoonDataPanel.setDataInGUI(persoonService.findPersoonById(gezochtePersonen.get(index)));
						} else if (rBtnToPerson.isSelected()) {
							toPersoonDataPanel.setDataInGUI(persoonService.findPersoonById(gezochtePersonen.get(index)));
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(e);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource().equals(btnChangePerson)) {
				savePersoon();
				if (!StringUtils.isEmpty(toPersoonDataPanel.getTxtNaam().getText())
						&& !StringUtils.isEmpty(toPersoonDataPanel.getTxtVoornaam().getText())
						&& !StringUtils.isEmpty(fromPersoonDataPanel.getTxtNaam().getText())
						&& !StringUtils.isEmpty(fromPersoonDataPanel.getTxtVoornaam().getText())) {

					if (toPersoonDataPanel.getPersoon().getId() == fromPersoonDataPanel.getPersoon().getId()) {
						String s = "De te vervangen persoon en doel persoon die geselecteerd werden zijn dezelfde.";
						JOptionPane.showMessageDialog(this, s, "Twee keer dezelfde persoon geselecteerd",
								JOptionPane.WARNING_MESSAGE);
					} else {
						persoonService.changePersoon(toPersoonDataPanel.getPersoon().getId(), fromPersoonDataPanel
								.getPersoon().getId());
						fromPersoonDataPanel.wisPersoonPanel();
						toPersoonDataPanel.wisPersoonPanel();
						getController().getPersoonPanel().wisPersoonPanel(true);
						getController().getHeader().zoekStandplaats();
						getController().setPersonen(getPersoonService().getPersoonNamen());
						getController().getMainNavigationpanel().getBetalerPanel().updateList();
					}
				}
			} else if (e.getSource().equals(btnWisFromPerson)) {
				fromPersoonDataPanel.wisPersoonPanel();
			} else if (e.getSource().equals(btnWisToPerson)) {
				toPersoonDataPanel.wisPersoonPanel();
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}

	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private final Charset ISO8891_CHARSET = Charset.forName("ISO-8859-1");

	String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	byte[] encodeIso88591(String string) {
		return string.getBytes(ISO8891_CHARSET);
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

	private String getNaam() {
		String naam = "";
		if (rBtnFromPerson.isSelected()) {
			naam = fromPersoonDataPanel.getTxtNaam().getText();
		} else {
			naam = toPersoonDataPanel.getTxtNaam().getText();
		}
		return naam;
	}

	private String getVoornaam() {
		String voorNaam = "";
		if (rBtnFromPerson.isSelected()) {
			voorNaam = fromPersoonDataPanel.getTxtVoornaam().getText();
		} else {
			voorNaam = toPersoonDataPanel.getTxtVoornaam().getText();
		}
		return voorNaam;
	}

	private String getDate() {
		int jaar = 0;
		int maand = 0;
		int dag = 0;
		if (rBtnFromPerson.isSelected()) {
			if (!StringUtils.isEmpty(fromPersoonDataPanel.getGeboortedatumPicker().getJFormattedTextField().getText())) {
				jaar = fromPersoonDataPanel.getGeboortedatumPicker().getModel().getYear();
				maand = fromPersoonDataPanel.getGeboortedatumPicker().getModel().getMonth() + 1;
				dag = fromPersoonDataPanel.getGeboortedatumPicker().getModel().getDay();
			}
		} else {
			if (!StringUtils.isEmpty(toPersoonDataPanel.getGeboortedatumPicker().getJFormattedTextField().getText())) {
				jaar = toPersoonDataPanel.getGeboortedatumPicker().getModel().getYear();
				maand = toPersoonDataPanel.getGeboortedatumPicker().getModel().getMonth() + 1;
				dag = toPersoonDataPanel.getGeboortedatumPicker().getModel().getDay();
			}
		}

		String sDate = "";
		if (0 != jaar && 0 != maand) {
			sDate = jaar + "-";
			if (maand < 10)
				sDate += 0;
			sDate += maand + "-";
			if (dag < 10)
				sDate += 0;
			sDate += dag;
		}

		return sDate;
	}

	private void zoekPersonen() {
		Map<String, Object> zoekCriteria = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(getNaam())) {
			zoekCriteria.put(Constant.NAAM, getNaam());
		}
		if (!StringUtils.isEmpty(getVoornaam())) {
			zoekCriteria.put(Constant.VOORNAAM, getVoornaam());
		}
		String sDate = getDate();
		if (!StringUtils.isEmpty(sDate)) {
			zoekCriteria.put(Constant.GEBOORTEDATUM, sDate);
		}
		if (zoekCriteria.size() > 0) {
			List<Object[]> persoongegevens = persoonService.zoekPersonen(zoekCriteria);
			// List<PersoonDO> persoonDOs = new ArrayList<PersoonComparisonPanel.PersoonDO>();
			Vector<String> superClasses = new Vector<String>();
			gezochtePersonen = new ArrayList<Integer>();
			for (Object[] o : persoongegevens) {
				String naam = o[1] + " " + o[2];
				superClasses.add(naam);
				gezochtePersonen.add((Integer) o[0]);
				// persoonDOs.add(new PersoonDO((Integer) o[0], (String) o[1], (String) o[2]));
			}
			persoonListPanel.getjListPersonen().setListData(superClasses);
			JList list = new JList();
			list.setListData(superClasses);
			actionJListPersonen.setActionList(list);
		}
	}

	class PersoonDO {

		private int id;
		private String naam;
		private String voornaam;

		public PersoonDO(final int id, final String naam, final String voornaam) {
			this.id = id;
			this.naam = naam;
			this.voornaam = voornaam;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNaam() {
			return naam;
		}

		public void setNaam(String naam) {
			this.naam = naam;
		}

		public String getVoornaam() {
			return voornaam;
		}

		public void setVoornaam(String voornaam) {
			this.voornaam = voornaam;
		}
	}

	public void savePersoon() {
		if (!StringUtils.isEmpty(toPersoonDataPanel.getTxtNaam().getText())
				&& !StringUtils.isEmpty(toPersoonDataPanel.getTxtVoornaam().getText())) {

			PersoonDTO persoonTo = toPersoonDataPanel.getPersoon();
			if (!StringUtils.isEmpty(persoonTo.getNaam()) && !StringUtils.isEmpty(persoonTo.getVoornaam())) {
				removeContactOrWagen();
				toPersoonDataPanel.setPersoon(persoonService.store(persoonTo));
				getController().getMainNavigationpanel().getBetalerPanel().setDataInGUI();
			}
		}
	}

	private void removeContactOrWagen() {
		for (ContactgegevenDTO contactgegeven : toPersoonDataPanel.getContactGegevensToRemove()) {
			if (contactgegeven != null) {
				persoonService.removeContactgegeven(contactgegeven);
			}
		}
		for (WagenDTO wagen : toPersoonDataPanel.getWagensToRemove()) {
			if (wagen != null) {
				persoonService.removeWagen(wagen);
			}
		}
		toPersoonDataPanel.setContactGegevensToRemove(new ArrayList<ContactgegevenDTO>());
		toPersoonDataPanel.setWagensToRemove(new ArrayList<WagenDTO>());
	}

}
