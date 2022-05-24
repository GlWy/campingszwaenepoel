package be.camping.campingzwaenepoel.presentation.ui.panels.helppanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.enums.ContactgegevenTypeEnum;
import be.camping.campingzwaenepoel.common.enums.GeslachtEnum;
import be.camping.campingzwaenepoel.presentation.common.SwingUtilities;
import be.camping.campingzwaenepoel.presentation.component.UpcaseFilter;
import be.camping.campingzwaenepoel.presentation.event.adapter.SelectAllFocusAdapter;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.listeners.RequestFocusListener;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.transfer.AdresDTO;
import be.camping.campingzwaenepoel.service.transfer.ContactgegevenDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.WagenDTO;

public class PersoonDataPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(PersoonDataPanel.class);

	private static final boolean isPersoonIdVisible = false;

	private JPanel personCentralPanel;
	private JPanel personNorthPanel;
	private JPanel personSouthPanel;

	private JLabel lblNaam = null;
	private JTextField txtNaam = null;
	private JLabel lblVoornaam = null;
	private JTextField txtVoornaam = null;
	private JLabel lblGeslacht = null;
	private JRadioButton rbtnMan = null;
	private JRadioButton rbtnVrouw = null;
	private JLabel lblGeboortedatum = null;
	private JLabel lblGeboortePlaats = null;
	private JTextField txtGeboortePlaats = null;
	private JLabel lblStraat = null;
	private JTextField txtStraat = null;
	private JPanel panelNummer = null;
	private JLabel lblNummer = null;
	private JTextField txtNummer = null;
	private JLabel lblPostcode = null;
	private JTextField txtPostcode = null;
	private JLabel lblPlaats = null;
	private JTextField txtPlaats = null;
	private JPanel panelGeslacht = null;
	private JDatePicker geboortedatumPicker = null;

	private JLabel lblTelefoon = null;
	private JLabel lblEmail = null;
	private JTextField txtEmail = null;
	private JLabel lblNrPlaat = null;
	private JLabel lblOpmerking = null;
	private JTextArea taOpmerking = null;
	private JTextField txtPersoonId = null;
	private JLabel lblIdentiteitskaart = null;
	private JTextField txtIdentiteitskaart = null;
	private JLabel lblNationaliteit = null;
	private JTextField txtNationaliteit = null;
	private JLabel lblRijksregisternummer = null;
	private JTextField txtRijksregisternummer = null;

	private final Font font = new Font("SansSerif", Font.BOLD, 18);

	private SelectAllFocusAdapter focusAdapter;

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

	private PersoonDTO persoon = DTOFactory.getPersoonDTO();

	Insets insets = new Insets(5, 0, 0, 5);

	private JPanel panelStandplaatsPhoto = null;

	private List<ContactgegevenDTO> contactGegevensToRemove;
	private List<WagenDTO> wagensToRemove;

	private final String fileDirectory;

	public PersoonDataPanel(final String fileDirectory) {
		this.fileDirectory = fileDirectory;
		initComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK, 1));
		focusAdapter = new SelectAllFocusAdapter();
		initVariables();
		this.add(getPersonCentralPanel(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes the JComponents.
	 */
	private void initVariables() {
		initPersonComponents();
		initAddressComponents();
		initCardComponents();
		initTelephoneComponents();
		initCarComponents();
		initLabels();

		txtEmail = new JTextField();
		txtEmail.setPreferredSize(new Dimension(220, 36));
		txtEmail.setMinimumSize(new Dimension(180, 36));
		txtEmail.setFont(font);
		txtEmail.addFocusListener(focusAdapter);

		taOpmerking = new JTextArea();
		taOpmerking.setRows(3);
		taOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
		taOpmerking.setLineWrap(true);
		taOpmerking.setWrapStyleWord(true);
		taOpmerking.setFont(font);
		taOpmerking.setForeground(Color.RED);
		taOpmerking.addFocusListener(focusAdapter);
	}

	private void initPersonComponents() {
		txtPersoonId = new JTextField();
		txtPersoonId.setPreferredSize(new Dimension(100, 36));
		txtPersoonId.setMinimumSize(new Dimension(100, 36));
		txtPersoonId.setVisible(isPersoonIdVisible);

		txtNaam = new JTextField();
		txtNaam.setPreferredSize(new Dimension(220, 36));
		txtNaam.setMinimumSize(new Dimension(180, 36));
		txtNaam.setFont(font);
		txtNaam.addFocusListener(focusAdapter);

		txtVoornaam = new JTextField();
		txtVoornaam.setPreferredSize(new Dimension(220, 36));
		txtVoornaam.setMinimumSize(new Dimension(180, 36));
		txtVoornaam.setFont(font);
		txtVoornaam.addFocusListener(focusAdapter);

		txtGeboortePlaats = new JTextField();
		txtGeboortePlaats.setPreferredSize(new Dimension(220, 36));
		txtGeboortePlaats.setMinimumSize(new Dimension(180, 36));
		txtGeboortePlaats.setFont(font);
		txtGeboortePlaats.addFocusListener(focusAdapter);

		rbtnMan = new JRadioButton("man");
		rbtnMan.setActionCommand("man");
		rbtnMan.setFont(font);

		rbtnVrouw = new JRadioButton("vrouw");
		rbtnVrouw.setActionCommand("vrouw");
		rbtnVrouw.setFont(font);
	}

	private void initAddressComponents() {
		txtPostcode = new JTextField();
		txtPostcode.setPreferredSize(new Dimension(140, 36));
		txtPostcode.setMinimumSize(new Dimension(130, 36));
		txtPostcode.setFont(font);
		txtPostcode.addFocusListener(focusAdapter);

		txtNummer = new JTextField();
		txtNummer.setPreferredSize(new Dimension(220, 36));
		txtNummer.setMinimumSize(new Dimension(180, 36));
		txtNummer.setFont(font);
		txtNummer.addFocusListener(focusAdapter);

		txtPlaats = new JTextField();
		txtPlaats.setPreferredSize(new Dimension(220, 36));
		txtPlaats.setMinimumSize(new Dimension(180, 36));
		txtPlaats.setFont(font);
		txtPlaats.addFocusListener(focusAdapter);

		txtStraat = new JTextField();
		txtStraat.setPreferredSize(new Dimension(375, 36));
		txtStraat.setMinimumSize(new Dimension(300, 36));
		txtStraat.setFont(font);
		txtStraat.addFocusListener(focusAdapter);
	}

	private void initCardComponents() {
		txtRijksregisternummer = new JTextField();
		txtRijksregisternummer.setPreferredSize(new Dimension(140, 36));
		txtRijksregisternummer.setMinimumSize(new Dimension(130, 36));
		txtRijksregisternummer.setFont(font);
		txtRijksregisternummer.addFocusListener(focusAdapter);

		txtIdentiteitskaart = new JTextField();
		txtIdentiteitskaart.setPreferredSize(new Dimension(220, 36));
		txtIdentiteitskaart.setMinimumSize(new Dimension(180, 36));
		txtIdentiteitskaart.setFont(font);
		txtIdentiteitskaart.addFocusListener(focusAdapter);

		txtNationaliteit = new JTextField();
		txtNationaliteit.setPreferredSize(new Dimension(140, 36));
		txtNationaliteit.setMinimumSize(new Dimension(130, 36));
		txtNationaliteit.setFont(font);
		txtNationaliteit.addFocusListener(focusAdapter);
	}

	private void initTelephoneComponents() {
		jListModelTelefoon = new DefaultListModel();
		jListTelefoon = new JList(jListModelTelefoon);

		jScrollPaneTelefoon = new JScrollPane(jListTelefoon);
		jScrollPaneTelefoon.setPreferredSize(new Dimension(150, 72));
		jScrollPaneTelefoon.setMinimumSize(new Dimension(120, 72));

		btnTelephone = new JButton("NIEUW");
		btnTelephone.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnTelephone.addActionListener(this);

		btnEditTel = new JButton("WIJZIG");
		btnEditTel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnEditTel.addActionListener(this);

		btnRemoveTel = new JButton("VERWIJDER");
		btnRemoveTel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnRemoveTel.addActionListener(this);
	}

	private void initCarComponents() {
		jListModelNummerplaat = new DefaultListModel();
		jListNummerplaat = new JList(jListModelNummerplaat);

		jScrollPaneNummerplaat = new JScrollPane(jListNummerplaat);
		jScrollPaneNummerplaat.setPreferredSize(new Dimension(150, 72));
		jScrollPaneNummerplaat.setMinimumSize(new Dimension(150, 72));

		DocumentFilter dfilter = new UpcaseFilter();
		jTxtNummerplaat = new JTextField();
		jTxtNummerplaat.setPreferredSize(new Dimension(150, 28));
		jTxtNummerplaat.setMinimumSize(new Dimension(100, 28));
		jTxtNummerplaat.setFont(font);
		jTxtNummerplaat.addFocusListener(focusAdapter);
		((AbstractDocument) jTxtNummerplaat.getDocument()).setDocumentFilter(dfilter);

		jTxtMerk = new JTextField();
		jTxtMerk.setPreferredSize(new Dimension(200, 28));
		jTxtMerk.setFont(font);
		jTxtMerk.addFocusListener(focusAdapter);

		jTxtSticker = new JTextField();
		jTxtSticker.setPreferredSize(new Dimension(200, 28));
		jTxtSticker.setFont(font);
		jTxtSticker.addFocusListener(focusAdapter);

		jTxtOpmerking = new JTextField();
		jTxtOpmerking.setPreferredSize(new Dimension(300, 28));
		jTxtOpmerking.setFont(font);
		jTxtOpmerking.addFocusListener(focusAdapter);

		btnNrPlaat = new JButton("NIEUW");
		btnNrPlaat.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnNrPlaat.addActionListener(this);

		btnEditNrPlaat = new JButton("WIJZIG");
		btnEditNrPlaat.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnEditNrPlaat.addActionListener(this);

		btnRemoveNrPlaat = new JButton("VERWIJDER");
		btnRemoveNrPlaat.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnRemoveNrPlaat.addActionListener(this);
	}

	private void initLabels() {
		lblNaam = new JLabel();
		lblNaam.setText("Familienaam: ");
		lblNaam.setFont(font);

		lblVoornaam = new JLabel();
		lblVoornaam.setText("Voornaam: ");
		lblVoornaam.setFont(font);

		lblGeboortePlaats = new JLabel();
		lblGeboortePlaats.setText("Geboren te: ");
		lblGeboortePlaats.setFont(font);

		lblGeboortedatum = new JLabel();
		lblGeboortedatum.setText("op: ");
		lblGeboortedatum.setFont(font);

		lblGeslacht = new JLabel();
		lblGeslacht.setText("Geslacht: ");
		lblGeslacht.setFont(font);

		lblStraat = new JLabel();
		lblStraat.setText("straat: ");
		lblStraat.setFont(font);

		lblNummer = new JLabel();
		lblNummer.setText("Nummer: ");
		lblNummer.setFont(font);

		lblPostcode = new JLabel();
		lblPostcode.setText("Postcode: ");
		lblPostcode.setFont(font);

		lblPlaats = new JLabel();
		lblPlaats.setText("Plaats: ");
		lblPlaats.setFont(font);

		lblIdentiteitskaart = new JLabel();
		lblIdentiteitskaart.setText("Identiteitskaart: ");
		lblIdentiteitskaart.setFont(font);

		lblNationaliteit = new JLabel();
		lblNationaliteit.setText("Nationaliteit: ");
		lblNationaliteit.setFont(font);

		lblRijksregisternummer = new JLabel();
		lblRijksregisternummer.setText("Rijksregisternr: ");
		lblRijksregisternummer.setFont(font);

		lblTelefoon = new JLabel();
		lblTelefoon.setText("Telefoon: ");
		lblTelefoon.setFont(font);

		lblNrPlaat = new JLabel();
		lblNrPlaat.setText("Nummerplaat:");
		lblNrPlaat.setFont(font);

		lblEmail = new JLabel();
		lblEmail.setText("Email: ");
		lblEmail.setFont(font);

		lblOpmerking = new JLabel();
		lblOpmerking.setText("Opmerking:");
		lblOpmerking.setFont(font);
	}

	private ContactgegevenDTO containsContactgegeven(Set<ContactgegevenDTO> contactgegevens, String waarde) {
		ContactgegevenDTO contactgegeven = null;
		for (ContactgegevenDTO contactgegevenDTO : contactgegevens) {
			if (waarde.equals(contactgegevenDTO.getWaarde())) {
				contactgegeven = contactgegevenDTO;
				break;
			}
		}
		return contactgegeven;
	}

	private JPanel getJPanelNrPlaat() {
		if (jPanelNrPlaat == null) {
			jPanelNrPlaat = new JPanel();
			jPanelNrPlaat.setLayout(new GridLayout(3, 1));
			jPanelNrPlaat.add(btnNrPlaat);
			jPanelNrPlaat.add(btnEditNrPlaat);
			jPanelNrPlaat.add(btnRemoveNrPlaat);
		}
		return jPanelNrPlaat;
	}

	private JPanel getJPanelTelephone() {
		if (jPanelTelephone == null) {
			jPanelTelephone = new JPanel();
			jPanelTelephone.setLayout(new GridLayout(3, 1));
			jPanelTelephone.add(btnTelephone);
			jPanelTelephone.add(btnEditTel);
			jPanelTelephone.add(btnRemoveTel);
		}
		return jPanelTelephone;
	}

	public JPanel getPersonCentralPanel() {
		if (personCentralPanel == null) {
			personCentralPanel = new JPanel();
			personCentralPanel.setLayout(new BorderLayout());
			personCentralPanel.setBorder(new LineBorder(Color.BLACK, 1));
			personCentralPanel.add(getPersonNorthPanel(), BorderLayout.NORTH);
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

			Insets insets = new Insets(5, 5, 5, 0);
			// Insets insetsBorder = new Insets(10, 10, 5, 0);
			// Dimension dim = new
			// Dimension(Toolkit.getDefaultToolkit().getScreenSize().width -
			// 615, (Toolkit
			// .getDefaultToolkit().getScreenSize().height - 120) / 2);
			// personNorthPanel.setSize(dim);
			personNorthPanel.setLayout(new GridBagLayout());
			personNorthPanel.setBorder(new LineBorder(Color.BLACK, 1));

			personNorthPanel
					.add(getPanelStandplaatsPhoto(), SwingUtilities.getGridBagConstraints(0, 0,
							GridBagConstraints.NORTHWEST, -1, insets, -1, -1, -1, 5));

			personNorthPanel.add(lblNaam,
					SwingUtilities.getGridBagConstraints(1, 0, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtNaam,
					SwingUtilities.getGridBagConstraints(1, 1, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));

			personNorthPanel.add(lblVoornaam,
					SwingUtilities.getGridBagConstraints(1, 2, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtVoornaam,
					SwingUtilities.getGridBagConstraints(1, 3, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblGeslacht,
					SwingUtilities.getGridBagConstraints(0, 5, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(getPanelGeslacht(),
					SwingUtilities.getGridBagConstraints(1, 5, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblGeboortePlaats,
					SwingUtilities.getGridBagConstraints(0, 6, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtGeboortePlaats,
					SwingUtilities.getGridBagConstraints(1, 6, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblGeboortedatum,
					SwingUtilities.getGridBagConstraints(0, 7, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add((JComponent) getGeboortedatumPicker(),
					SwingUtilities.getGridBagConstraints(1, 7, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblStraat,
					SwingUtilities.getGridBagConstraints(0, 8, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtStraat,
					SwingUtilities.getGridBagConstraints(0, 9, GridBagConstraints.WEST, -1, insets, -1, -1, 2, -1));
			personNorthPanel.add(lblNummer,
					SwingUtilities.getGridBagConstraints(0, 10, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtNummer,
					SwingUtilities.getGridBagConstraints(1, 10, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblPostcode,
					SwingUtilities.getGridBagConstraints(0, 11, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtPostcode,
					SwingUtilities.getGridBagConstraints(1, 11, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblPlaats,
					SwingUtilities.getGridBagConstraints(0, 12, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtPlaats,
					SwingUtilities.getGridBagConstraints(1, 12, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			// personNorthPanel.add(txtPersoonId,
			// SwingUtilities.getGridBagConstraints(0, 0,
			// GridBagConstraints.WEST, -1, insets, -1, -1, 1, 1));
			personNorthPanel.add(lblNationaliteit,
					SwingUtilities.getGridBagConstraints(0, 13, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtNationaliteit,
					SwingUtilities.getGridBagConstraints(1, 13, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblIdentiteitskaart,
					SwingUtilities.getGridBagConstraints(0, 14, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtIdentiteitskaart,
					SwingUtilities.getGridBagConstraints(1, 14, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(lblRijksregisternummer,
					SwingUtilities.getGridBagConstraints(0, 15, GridBagConstraints.EAST, -1, insets, -1, -1, -1, -1));
			personNorthPanel.add(txtRijksregisternummer,
					SwingUtilities.getGridBagConstraints(1, 15, GridBagConstraints.WEST, -1, insets, -1, -1, -1, -1));
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

			Insets insets = new Insets(10, 0, 0, 10);
			Insets insetsBorder = new Insets(10, 20, 0, 5);

			personSouthPanel.setLayout(new GridBagLayout());
			personSouthPanel.setBorder(new LineBorder(Color.BLACK, 1));

			personSouthPanel.add(lblTelefoon,
					SwingUtilities.getGridBagConstraints(0, 0, -1, -1, new Insets(0, 20, 0, 5), -1, -1, -1, -1));
			personSouthPanel.add(jScrollPaneTelefoon, SwingUtilities.getGridBagConstraints(0, 1,
					GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 0, 0, 5), -1, -1, -1, -1));
			personSouthPanel.add(getJPanelTelephone(), SwingUtilities.getGridBagConstraints(1, 1,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(5, 0, 0, 5), -1, -1, -1, -1));

			personSouthPanel.add(lblNrPlaat,
					SwingUtilities.getGridBagConstraints(0, 2, -1, -1, new Insets(0, 0, 0, 5), -1, -1, -1, -1));
			personSouthPanel.add(jScrollPaneNummerplaat, SwingUtilities.getGridBagConstraints(0, 3,
					GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 0, 0, 5), -1, -1, -1, -1));
			personSouthPanel.add(getJPanelNrPlaat(), SwingUtilities.getGridBagConstraints(1, 3,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(5, 0, 0, 5), -1, -1, -1, -1));

			personSouthPanel.add(lblEmail,
					SwingUtilities.getGridBagConstraints(0, 4, -1, -1, insetsBorder, -1, -1, -1, -1));
			personSouthPanel.add(txtEmail, SwingUtilities.getGridBagConstraints(1, 4, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, insets, -1, -1, -1, -1));

			personSouthPanel.add(lblOpmerking,
					SwingUtilities.getGridBagConstraints(0, 5, -1, -1, insetsBorder, -1, -1, -1, -1));
			personSouthPanel.add(taOpmerking, SwingUtilities.getGridBagConstraints(1, 5, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(10, 0, 5, 10), -1, -1, -1, -1));
		}
		return personSouthPanel;
	}

	private JPanel getPanelGeslacht() {
		if (panelGeslacht == null) {
			panelGeslacht = new JPanel();
			panelGeslacht.setLayout(new GridLayout(1, 2));

			ButtonGroup group = new ButtonGroup();
			group.add(rbtnMan);
			group.add(rbtnVrouw);

			panelGeslacht.add(rbtnMan);
			panelGeslacht.add(rbtnVrouw);
		}
		return panelGeslacht;
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
			panelNummer.add(txtNummer);
		}
		return panelNummer;
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
			geboortedatumPicker.getJFormattedTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
						txtStraat.requestFocusInWindow();

						String sDate = getGeboortedatumPicker().getJFormattedTextField().getText();
						sDate = sDate.replace("/", "-");
						DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						try {
							Date date = formatter.parse(sDate);
							Calendar cal = Calendar.getInstance();
							cal.setTime(date);
							getGeboortedatumPicker().getModel().setDate(cal.get(Calendar.YEAR),
									cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
						} catch (ParseException pe) {
							// TODO Auto-generated catch block
							LOGGER.error("date not correctly parsed");
						}

					}
				}
			});
			geboortedatumPicker.getJFormattedTextField().addFocusListener(focusAdapter);
		}
		return geboortedatumPicker;
	}

	public PersoonDTO getPersoon() {
		updatePersoonDataFromGUI();
		return persoon;
	}

	public void setPersoon(PersoonDTO persoon) {
		this.persoon = persoon;
		// setDataInGUI(persoon);
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

	public JTextField getTxtNaam() {
		return txtNaam;
	}

	public JTextField getTxtVoornaam() {
		return txtVoornaam;
	}

	public void wisPersoonPanel() {
		taOpmerking.setText("");
		txtEmail.setText("");
		txtNaam.setText("");
		txtVoornaam.setText("");
		rbtnMan.setSelected(false);
		rbtnVrouw.setSelected(false);
		txtGeboortePlaats.setText("");
		geboortedatumPicker.resetTextFieldToDefaultValue();
		txtStraat.setText("");
		txtNummer.setText("");
		txtPostcode.setText("");
		txtPlaats.setText("");
		txtIdentiteitskaart.setText("");
		txtNationaliteit.setText("");
		txtRijksregisternummer.setText("");
		jListModelTelefoon.removeAllElements();
		jListModelNummerplaat.removeAllElements();
		setPersoon(DTOFactory.getPersoonDTO());
		setPersoonFoto(0);
		setContactGegevensToRemove(new ArrayList<ContactgegevenDTO>());
		setWagensToRemove(new ArrayList<WagenDTO>());
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
			jPanelTextFields.add(jTxtNummerplaat);
			jPanelTextFields.add(jTxtMerk);
			jPanelTextFields.add(jTxtSticker);
			jPanelTextFields.add(jTxtOpmerking);
			jPanelNrPlaatGegevens.add(jPanelLabels, BorderLayout.WEST);
			jPanelNrPlaatGegevens.add(jPanelTextFields, BorderLayout.CENTER);
		}
		return jPanelNrPlaatGegevens;
	}

	private void setJPanelNrPlaatGegevens(JPanel jPanel) {
		this.jPanelNrPlaatGegevens = jPanel;
	}

	private void resetJPanelNrPlaatsGegevens() {
		jTxtNummerplaat.setText("");
		jTxtMerk.setText("");
		jTxtSticker.setText("");
		jTxtOpmerking.setText("");
		setJPanelNrPlaatGegevens(null);
		// carFocusListener.setHadFocus(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnTelephone) {
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
					int pos = jListTelefoon.getModel().getSize();
					jListModelTelefoon.add(pos, jTxtTelefoon.getText());
				}
			} else if (e.getSource() == btnEditTel) {
				String telNr = (String) jListTelefoon.getSelectedValue();
				if (!StringUtils.isEmpty(telNr)) {
					int pos = jListTelefoon.getSelectedIndex();
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
						jListModelTelefoon.removeElementAt(pos);
						jListModelTelefoon.add(pos, jTxtTelefoon.getText());
						for (ContactgegevenDTO contactGegeven : getPersoon().getContactgegevens()) {
							if (ContactgegevenTypeEnum.TELEFOON.equals(contactGegeven.getContactgegevenType())
									&& telNr.equals(contactGegeven.getWaarde())) {
								contactGegeven.setWaarde(jTxtTelefoon.getText());
							}
						}
					}
				}
			} else if (e.getSource() == btnRemoveTel) {
				int i = jListTelefoon.getSelectedIndex();
				String tel = (String) jListTelefoon.getSelectedValue();
				ContactgegevenDTO contactgegeven = containsContactgegeven(getPersoon().getContactgegevens(), tel);
				getPersoon().getContactgegevens().remove(contactgegeven);
				if (contactgegeven != null) {
					getContactGegevensToRemove().add(contactgegeven);
				}
				jListModelTelefoon.remove(i);

			} else if (e.getSource() == btnNrPlaat) {
				resetJPanelNrPlaatsGegevens();
				Object[] array = { getJPanelNrPlaatGegevens() };
				jTxtNummerplaat.addAncestorListener(new RequestFocusListener());
				int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ret == 0) {
					/** add value to JListWagen */
					if (jTxtNummerplaat.getText().length() > 0) {
						WagenDTO wagen = new WagenDTO();
						wagen.setNummerplaat(jTxtNummerplaat.getText());
						wagen.setMerk(jTxtMerk.getText());
						wagen.setSticker(jTxtSticker.getText());
						wagen.setOpmerking(jTxtOpmerking.getText());
						getPersoon().getWagens().add(wagen);
						int pos = jListNummerplaat.getModel().getSize();
						jListModelNummerplaat.add(pos, wagen.getNummerplaat());
					}
				}
			} else if (e.getSource() == btnEditNrPlaat) {
				resetJPanelNrPlaatsGegevens();
				String nrPlaat = (String) jListNummerplaat.getSelectedValue();
				if (!StringUtils.isEmpty(nrPlaat)) {
					WagenDTO wagen = null;
					for (WagenDTO tmpWagen : getPersoon().getWagens()) {
						if (tmpWagen.getNummerplaat().equals(nrPlaat)) {
							wagen = tmpWagen;
							break;
						}
					}
					jTxtNummerplaat.setText(wagen.getNummerplaat());
					jTxtMerk.setText(wagen.getMerk());
					jTxtSticker.setText(wagen.getSticker());
					jTxtOpmerking.setText(wagen.getOpmerking());
					Object[] array = { getJPanelNrPlaatGegevens() };
					jTxtNummerplaat.addAncestorListener(new RequestFocusListener());
					int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (ret == 0) {
						/* add value to JListWagen */
						/**
						 * TODO: afwerken voor het wijzigen van de wagen gegevens
						 */
						wagen.setNummerplaat(jTxtNummerplaat.getText());
						wagen.setMerk(jTxtMerk.getText());
						wagen.setSticker(jTxtSticker.getText());
						wagen.setOpmerking(jTxtOpmerking.getText());
						getPersoon().getWagens().add(wagen);
						int pos = jListNummerplaat.getSelectedIndex();
						jListModelNummerplaat.removeElementAt(pos);
						jListModelNummerplaat.add(pos, wagen.getNummerplaat());
					}
					jTxtNummerplaat = null;
					jPanelNrPlaatGegevens = null;
				}
			} else if (e.getSource() == btnRemoveNrPlaat) {
				int i = jListNummerplaat.getSelectedIndex();
				String nrPlaat = (String) jListNummerplaat.getSelectedValue();
				jListModelNummerplaat.remove(i);
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
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		getPersonNorthPanel().remove(getPanelStandplaatsPhoto());
		this.panelStandplaatsPhoto = null;
		String dir = fileDirectory + "/personen/" + persoonId + ".jpg";
		getPanelStandplaatsPhoto().add(new ShowImage(dir));

		// GridBagConstraints gridBagConstraintsTxtFoto = new GridBagConstraints();
		// gridBagConstraintsTxtFoto.gridx = 4;
		// gridBagConstraintsTxtFoto.gridy = 2;
		// gridBagConstraintsTxtFoto.gridheight = 5;
		// gridBagConstraintsTxtFoto.gridwidth = 2;
		// Insets insets = new Insets(5, 10, 10, 0);
		// gridBagConstraintsTxtFoto.insets = insets;
		// gridBagConstraintsTxtFoto.anchor = GridBagConstraints.NORTHWEST;

		getPersonNorthPanel().add(getPanelStandplaatsPhoto(),
				SwingUtilities.getGridBagConstraints(0, 0, GridBagConstraints.NORTHWEST, -1, insets, -1, -1, -1, 5));
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

	private void updatePersoonDataFromGUI() {
		/**
		 * algemene persoonsgegevens
		 */
		persoon.setNaam(txtNaam.getText());
		persoon.setVoornaam(txtVoornaam.getText());
		if (rbtnVrouw.isSelected()) {
			persoon.setGeslacht(GeslachtEnum.V);
		} else if (rbtnMan.isSelected()) {
			persoon.setGeslacht(GeslachtEnum.M);
		} else {
			persoon.setGeslacht(GeslachtEnum.O);
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
		persoon.setGeboorteplaats(txtGeboortePlaats.getText());
		persoon.setGeboortedatum(calGebDat.getTime());
		persoon.setOpmerking(taOpmerking.getText());
		persoon.setIdentiteitskaartnummer(txtIdentiteitskaart.getText());
		persoon.setNationaliteit(txtNationaliteit.getText());
		persoon.setRijksregisternummer(txtRijksregisternummer.getText());

		/**
		 * adres gegevens
		 */
		AdresDTO adresDTO = null;
		if (persoon.getAdresDTO() != null) {
			adresDTO = persoon.getAdresDTO();
		} else {
			adresDTO = new AdresDTO();
		}
		adresDTO.setHuisnummer(txtNummer.getText());
		adresDTO.setPlaats(txtPlaats.getText());
		adresDTO.setPostcode(txtPostcode.getText());
		adresDTO.setStraat(txtStraat.getText());
		persoon.setAdresDTO(adresDTO);

		/**
		 * telefoons
		 */
		Set<ContactgegevenDTO> contactgegevens = new HashSet<ContactgegevenDTO>();
		Set<ContactgegevenDTO> oudeContactgegevens = persoon.getContactgegevens();
		List<String> contactList = new ArrayList<String>();
		for (int i = 0; i < jListModelTelefoon.getSize(); i++) {
			contactList.add((String) jListModelTelefoon.getElementAt(i));
		}

		for (int i = 0; i < jListModelTelefoon.getSize(); i++) {
			ContactgegevenDTO contactgegevenDTO = containsContactgegeven(oudeContactgegevens,
					(String) jListModelTelefoon.get(i));
			if (contactgegevenDTO == null) {
				ContactgegevenDTO contactgegeven = new ContactgegevenDTO();
				contactgegeven.setContactgegevenType(ContactgegevenTypeEnum.TELEFOON);
				contactgegeven.setWaarde((String) jListModelTelefoon.get(i));
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
			if (!StringUtils.isEmpty(txtEmail.getText())) {
				ContactgegevenDTO contactgegeven = new ContactgegevenDTO();
				contactgegeven.setContactgegevenType(ContactgegevenTypeEnum.EMAIL);
				contactgegeven.setWaarde(txtEmail.getText());
				contactgegevens.add(contactgegeven);
			}
		} else {
			if (!StringUtils.isEmpty(txtEmail.getText())) {
				contactgegevenDTO.setWaarde(txtEmail.getText());
				contactgegevens.add(contactgegevenDTO);
			} else {
				persoon.getContactgegevens().remove(contactgegevenDTO);
				getContactGegevensToRemove().add(contactgegevenDTO);
			}
		}
		persoon.setContactgegevens(contactgegevens);

		/**
		 * gegevens over de wagen reeds toegevoegd bij invullen gegevens van wagen
		 */

	}

	public void setDataInGUI(PersoonDTO persoonDTO) {
		wisPersoonPanel();
		setPersoon(persoonDTO);

		txtNaam.setText(persoonDTO.getNaam());
		txtVoornaam.setText(persoonDTO.getVoornaam());
		if (persoonDTO.getGeslacht().equals(GeslachtEnum.M)) {
			rbtnMan.doClick();
		} else if (persoonDTO.getGeslacht().equals(GeslachtEnum.V)) {
			rbtnVrouw.doClick();
		}
		txtGeboortePlaats.setText(persoonDTO.getGeboorteplaats());
		taOpmerking.setText(persoonDTO.getOpmerking());
		if (persoonDTO.getAdresDTO() != null) {
			txtStraat.setText(persoonDTO.getAdresDTO().getStraat());
			txtPlaats.setText(persoonDTO.getAdresDTO().getPlaats());
			txtNummer.setText(persoonDTO.getAdresDTO().getHuisnummer());
			txtPostcode.setText(persoonDTO.getAdresDTO().getPostcode());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(persoonDTO.getGeboortedatum());
		getGeboortedatumPicker().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		txtPersoonId.setText(Integer.toString(persoonDTO.getId()));
		txtIdentiteitskaart.setText(persoonDTO.getIdentiteitskaartnummer());
		txtNationaliteit.setText(persoonDTO.getNationaliteit());
		txtRijksregisternummer.setText(persoonDTO.getRijksregisternummer());
		txtNaam.requestFocusInWindow();
		txtNaam.selectAll();

		for (ContactgegevenDTO contactgegeven : persoonDTO.getContactgegevens()) {
			if (contactgegeven.getContactgegevenType().equals(ContactgegevenTypeEnum.TELEFOON)) {
				jListModelTelefoon.addElement(contactgegeven.getWaarde());
			} else if (contactgegeven.getContactgegevenType().equals(ContactgegevenTypeEnum.EMAIL)) {
				txtEmail.setText(contactgegeven.getWaarde());
			}
		}

		for (WagenDTO wagen : persoonDTO.getWagens()) {
			jListModelNummerplaat.addElement(wagen.getNummerplaat());
		}

		setPersoonFoto(persoonDTO.getId());
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

	public void setDocumentListener(final DocumentListener documentListener) {
		txtNaam.getDocument().addDocumentListener(documentListener);
		txtVoornaam.getDocument().addDocumentListener(documentListener);
		geboortedatumPicker.getJFormattedTextField().getDocument().addDocumentListener(documentListener);
	}
}