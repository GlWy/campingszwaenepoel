package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.HuurderPositieEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.dialog.ZoekResultatenDialog;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ZoekPanel extends JPanel implements PanelInterface, ActionListener {

    @Autowired
    private StandplaatsService standplaatsService;

	private static final long serialVersionUID = 1L;

	private Controller controller;

	@Autowired
	private PersoonService persoonService;

	private final Dimension dimTextfield = new Dimension(150, 28);
	private final Dimension dimTextfield2 = new Dimension(220, 28);

	private JPanel jPanelNorth = null;

	private JPanel jPanelSouth = null;

	private JPanel jPanelNorthWest = null;

	private JPanel jPanelSouthWest = null;

	private JPanel jPanelSouthEast = null;

	private JLabel jLblFichenummer = null;

	private JLabel jLblStandplaats = null;

	private JLabel jLblBadgeType = null;

	private JTextField jTxtFichenummer = null;

	private JTextField jTxtStandplaats = null;

	private JComboBox jCboBadgetype = null;

	private JLabel jLblBadge = null;

	private JTextField jTxtBadge = null;

	private JPanel jPanelAankomst = null;

	private JLabel jLblAankomst = null;

	private JComboBox jCboAankomst = null;

	private JDatePicker jDpAankomst = null; // @jve:decl-index=0:

	private JDatePicker jDpVertrek = null; // @jve:decl-index=0:

	private JPanel jPanelVertrek = null;

	private JLabel jLblVertrek = null;

	private JComboBox jCboVertrek = null;

	private JPanel jPanelBetaling = null;

	private JLabel jLblBetaling = null;

	private JLabel jLblTeBetalen = null;

	private JLabel jLblBetaald = null;

	private JLabel jLblSaldo = null;

	private JTextField jTxtTeBetalen = null;

	private JTextField jTxtBetaald = null;

	private JComboBox jCboSaldo = null;

	private JLabel jLblTypePersoon = null;

	private JComboBox jCboTypePersoon = null;

	private JLabel jLblNaam = null;

	private JLabel jLblVoornaam = null;

	private JLabel jLblGeslacht = null;

	private JLabel jLblStraat = null;

	private JLabel jLblNummer = null;

	private JLabel jLblPostcode = null;

	private JLabel jLblPlaats = null;

	private JLabel jLblOpmerking = null;

	private JTextField jTxtNaam = null;

	private JTextField jTxtVoornaam = null;

	private JComboBox jCboGeslacht = null;

	private JTextField jTxtStraat = null;

	private JTextField jTxtNummer = null;

	private JTextField jTxtPostcode = null;

	private JTextField jTxtPlaats = null;

	private JTextField jTxtOpmerking = null;

	private JLabel jLblIdentiteitskaart = null;

	private JLabel jLblNationaliteit = null;

	private JLabel jLblGeboortedatum = null;

	private JLabel jLblGeboorteplaats = null;

	private JLabel jLblNummerplaat = null;

	private JLabel jLblTelefoon = null;

	private JTextField jTxtIdentiteitskaart = null;

	private JComboBox jCboNationaliteit = null;

	private JTextField jTxtGeboorteplaats = null;

	private JTextField jTxtNummerplaat = null;

	private JTextField jTxtTelefoon = null;

	private JLabel jLblKeuze = null;

	private JPanel jPanelKeuze = null;

	private JDatePicker jDpGeboortedatum = null;

	private JButton jBtnWis = null;

	private JButton jBtnFilter = null;

	Font font = new Font("Times New Roman", Font.PLAIN, 18);

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {

	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkDataChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkDataForParent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getDataFromGUI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDataInGUI() {
		getJTxtFichenummer().requestFocusInWindow();
	}

	@Override
	public void setRemarksInGui() {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public ZoekPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setBorder(new LineBorder(Color.BLACK, 2));
		jLblOpmerking = new JLabel();
		jLblOpmerking.setText("Opmerking:");
		jLblOpmerking.addMouseListener(new MouseClickListener(jLblOpmerking));
		jLblOpmerking.setFont(font);
		jLblPlaats = new JLabel();
		jLblPlaats.setText("Woonplaats:");
		jLblPlaats.addMouseListener(new MouseClickListener(jLblPlaats));
		jLblPlaats.setFont(font);
		jLblPostcode = new JLabel();
		jLblPostcode.setText("Postcode:");
		jLblPostcode.addMouseListener(new MouseClickListener(jLblPostcode));
		jLblPostcode.setFont(font);
		jLblNummer = new JLabel();
		jLblNummer.setText("Huisnummer:");
		jLblNummer.addMouseListener(new MouseClickListener(jLblNummer));
		jLblNummer.setFont(font);
		jLblStraat = new JLabel();
		jLblStraat.setText("Straat:");
		jLblStraat.addMouseListener(new MouseClickListener(jLblStraat));
		jLblStraat.setFont(font);
		jLblGeslacht = new JLabel();
		jLblGeslacht.setText("Geslacht:");
		jLblGeslacht.addMouseListener(new MouseClickListener(jLblGeslacht));
		jLblGeslacht.setFont(font);
		GridBagConstraints gridBagConstraints78 = new GridBagConstraints();
		gridBagConstraints78.gridx = -1;
		gridBagConstraints78.gridy = -1;
		jLblBadgeType = new JLabel();
		jLblBadgeType.setText("Badgetype:");
		jLblBadgeType.addMouseListener(new MouseClickListener(jLblBadgeType));
		jLblBadgeType.setFont(font);
		jLblStandplaats = new JLabel();
		jLblStandplaats.setText("Standplaats:");
		jLblStandplaats.setForeground(Color.BLUE);
		jLblStandplaats.addMouseListener(new MouseClickListener(jLblStandplaats));
		jLblStandplaats.setFont(font);
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = 0;
		this.setSize(989, 551);
		this.setLayout(new GridBagLayout());
		this.add(getJPanelNorth(), gridBagConstraints);
		this.add(getJPanelSouth(), gridBagConstraints1);
	}

	/**
	 * This method initializes jPanelNorth
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNorth() {
		if (jPanelNorth == null) {
			jPanelNorth = new JPanel();
			jPanelNorth.setLayout(new BorderLayout());
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			jPanelNorth.setBorder(raisedetched);
			// jPanelNorth.add(getJPanelBetaling(), gridBagConstraints81);
			jPanelNorth.add(getJPanelNorthWest(), BorderLayout.CENTER);
			jPanelNorth.add(getJPanelKeuze(), BorderLayout.EAST);
		}
		return jPanelNorth;
	}

	/**
	 * This method initializes jPanelSouth
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSouth() {
		if (jPanelSouth == null) {
			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
			gridBagConstraints75.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = -1;
			gridBagConstraints2.insets = new Insets(0, 50, 0, 0);
			gridBagConstraints2.anchor = GridBagConstraints.NORTH;
			gridBagConstraints2.gridy = 1;
			jPanelSouth = new JPanel();
			jPanelSouth.setLayout(new GridBagLayout());
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			jPanelSouth.setBorder(raisedetched);
			jPanelSouth.add(getJPanelSouthWest(), gridBagConstraints75);
			jPanelSouth.add(getJPanelSouthEast(), gridBagConstraints2);
		}
		return jPanelSouth;
	}

	/**
	 * This method initializes jPanelNorthWest
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNorthWest() {
		if (jPanelNorthWest == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints26.gridy = 5;
			gridBagConstraints26.weightx = 1.0;
			gridBagConstraints26.gridx = 0;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridx = 0;
			gridBagConstraints25.gridy = 4;
			jLblTypePersoon = new JLabel();
			jLblTypePersoon.setText("Type Persoon:");
			jLblTypePersoon.addMouseListener(new MouseClickListener(jLblTypePersoon));
			jLblTypePersoon.setFont(font);
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = -1;
			gridBagConstraints18.gridy = -1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 2;
			gridBagConstraints14.gridwidth = 2;
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.insets = new Insets(0, 0, 10, 0);
			gridBagConstraints14.gridy = 2;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.insets = new Insets(0, 0, 10, 0);
			gridBagConstraints10.gridy = 2;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.insets = new Insets(0, 20, 15, 10);
			gridBagConstraints9.gridx = 3;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 3;
			gridBagConstraints8.gridy = 0;
			jLblBadge = new JLabel();
			jLblBadge.setText("Badge");
			jLblBadge.addMouseListener(new MouseClickListener(jLblBadge));
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(0, 0, 15, 0);
			gridBagConstraints7.gridx = 2;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(0, 0, 15, 20);
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(0, 10, 15, 20);
			gridBagConstraints5.weightx = 1.0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = -1;
			gridBagConstraints4.gridy = -1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = -1;
			gridBagConstraints3.gridy = -1;
			GridBagConstraints gbcBetalingsPanel = new GridBagConstraints();
			gbcBetalingsPanel.gridx = 0;
			gbcBetalingsPanel.gridwidth = 4;
			gbcBetalingsPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcBetalingsPanel.insets = new Insets(0, 0, 10, 0);
			gbcBetalingsPanel.gridy = 3;
			jLblFichenummer = new JLabel();
			jLblFichenummer.setText("Fichenummer:");
			jLblFichenummer.addMouseListener(new MouseClickListener(jLblFichenummer));
			jLblFichenummer.setFont(font);
			jPanelNorthWest = new JPanel();
			jPanelNorthWest.setLayout(new GridBagLayout());
			jPanelNorthWest.add(jLblFichenummer, new GridBagConstraints());
			jPanelNorthWest.add(jLblStandplaats, gridBagConstraints3);
			jPanelNorthWest.add(jLblBadgeType, gridBagConstraints4);
			jPanelNorthWest.add(getJTxtFichenummer(), gridBagConstraints5);
			jPanelNorthWest.add(getJTxtStandplaats(), gridBagConstraints6);
			jPanelNorthWest.add(getJCboBadgetype(), gridBagConstraints7);
			jPanelNorthWest.add(jLblBadge, gridBagConstraints8);
			jPanelNorthWest.add(getJTxtBadge(), gridBagConstraints9);
			jPanelNorthWest.add(getJPanelAankomst(), gridBagConstraints10);
			jPanelNorthWest.add(getJPanelVertrek(), gridBagConstraints14);
			jPanelNorthWest.add(getJPanelBetaling(), gbcBetalingsPanel);
			jPanelNorthWest.add(jLblTypePersoon, gridBagConstraints25);
			jPanelNorthWest.add(getJCboTypePersoon(), gridBagConstraints26);
		}
		return jPanelNorthWest;
	}

	/**
	 * This method initializes jPanelSouthWest
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSouthWest() {
		if (jPanelSouthWest == null) {
			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
			gridBagConstraints73.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints73.gridy = 6;
			gridBagConstraints73.weightx = 1.0;
			gridBagConstraints73.anchor = GridBagConstraints.WEST;
			gridBagConstraints73.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints73.gridx = 3;
			GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
			gridBagConstraints72.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints72.gridy = 5;
			gridBagConstraints72.weightx = 1.0;
			gridBagConstraints72.anchor = GridBagConstraints.WEST;
			gridBagConstraints72.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints72.gridx = 3;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints71.gridy = 4;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.anchor = GridBagConstraints.WEST;
			gridBagConstraints71.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints71.gridx = 3;
			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints70.gridy = 3;
			gridBagConstraints70.weightx = 1.0;
			gridBagConstraints70.anchor = GridBagConstraints.WEST;
			gridBagConstraints70.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints70.gridx = 3;
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints69.gridy = 1;
			gridBagConstraints69.weightx = 1.0;
			gridBagConstraints69.gridx = 3;
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints68.gridy = 0;
			gridBagConstraints68.weightx = 1.0;
			gridBagConstraints68.anchor = GridBagConstraints.WEST;
			gridBagConstraints68.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints68.gridx = 3;
			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.gridx = 2;
			gridBagConstraints67.anchor = GridBagConstraints.WEST;
			gridBagConstraints67.insets = new Insets(0, 100, 0, 0);
			gridBagConstraints67.gridy = 6;
			jLblTelefoon = new JLabel();
			jLblTelefoon.setText("Telefoon:");
			jLblTelefoon.addMouseListener(new MouseClickListener(jLblTelefoon));
			jLblTelefoon.setFont(font);
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.gridx = 2;
			gridBagConstraints66.anchor = GridBagConstraints.WEST;
			gridBagConstraints66.insets = new Insets(0, 100, 0, 0);
			gridBagConstraints66.gridy = 5;
			jLblNummerplaat = new JLabel();
			jLblNummerplaat.setText("Nummerplaat:");
			jLblNummerplaat.addMouseListener(new MouseClickListener(jLblNummerplaat));
			jLblNummerplaat.setFont(font);
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.gridx = 2;
			gridBagConstraints65.anchor = GridBagConstraints.WEST;
			gridBagConstraints65.insets = new Insets(0, 100, 0, 0);
			gridBagConstraints65.gridy = 4;
			jLblGeboorteplaats = new JLabel();
			jLblGeboorteplaats.setText("Geboorteplaats:");
			jLblGeboorteplaats.addMouseListener(new MouseClickListener(jLblGeboorteplaats));
			jLblGeboorteplaats.setFont(font);
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.gridx = 2;
			gridBagConstraints64.anchor = GridBagConstraints.WEST;
			gridBagConstraints64.insets = new Insets(0, 100, 0, 0);
			gridBagConstraints64.gridy = 3;
			jLblGeboortedatum = new JLabel();
			jLblGeboortedatum.setText("Geboortedatum:");
			jLblGeboortedatum.addMouseListener(new MouseClickListener(jLblGeboortedatum));
			jLblGeboortedatum.setFont(font);
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.gridx = 2;
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.insets = new Insets(0, 100, 0, 0);
			gridBagConstraints63.gridy = 1;
			jLblNationaliteit = new JLabel();
			jLblNationaliteit.setText("Nationaliteit:");
			jLblNationaliteit.addMouseListener(new MouseClickListener(jLblNationaliteit));
			jLblNationaliteit.setFont(font);
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridx = 2;
			gridBagConstraints62.anchor = GridBagConstraints.WEST;
			gridBagConstraints62.insets = new Insets(0, 100, 0, 0);
			gridBagConstraints62.gridy = 0;
			jLblIdentiteitskaart = new JLabel();
			jLblIdentiteitskaart.setText("Identiteitskaart:");
			jLblIdentiteitskaart.addMouseListener(new MouseClickListener(jLblIdentiteitskaart));
			jLblIdentiteitskaart.setFont(font);
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints61.gridy = 7;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints61.gridx = 1;
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints60.gridy = 6;
			gridBagConstraints60.weightx = 1.0;
			gridBagConstraints60.anchor = GridBagConstraints.WEST;
			gridBagConstraints60.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints60.gridx = 1;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints59.gridy = 5;
			gridBagConstraints59.weightx = 1.0;
			gridBagConstraints59.anchor = GridBagConstraints.WEST;
			gridBagConstraints59.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints59.gridx = 1;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints58.gridy = 4;
			gridBagConstraints58.weightx = 1.0;
			gridBagConstraints58.anchor = GridBagConstraints.WEST;
			gridBagConstraints58.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints58.gridx = 1;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints57.gridy = 3;
			gridBagConstraints57.weightx = 1.0;
			gridBagConstraints57.anchor = GridBagConstraints.WEST;
			gridBagConstraints57.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints57.gridx = 1;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints56.gridy = 2;
			gridBagConstraints56.weightx = 1.0;
			gridBagConstraints56.anchor = GridBagConstraints.WEST;
			gridBagConstraints56.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints56.gridx = 1;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.gridx = 0;
			gridBagConstraints55.gridy = 7;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridx = 0;
			gridBagConstraints54.gridy = 6;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 0;
			gridBagConstraints53.gridy = 5;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridx = 0;
			gridBagConstraints52.gridy = 4;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.gridy = 3;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.gridx = 0;
			gridBagConstraints50.gridy = 2;
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints49.gridy = 1;
			gridBagConstraints49.weightx = 1.0;
			gridBagConstraints49.anchor = GridBagConstraints.WEST;
			gridBagConstraints49.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints49.gridx = 1;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints48.gridy = 0;
			gridBagConstraints48.weightx = 1.0;
			gridBagConstraints48.anchor = GridBagConstraints.WEST;
			gridBagConstraints48.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints48.gridx = 1;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.gridx = 0;
			gridBagConstraints47.gridy = 1;
			jLblVoornaam = new JLabel();
			jLblVoornaam.setText("Voornaam:");
			jLblVoornaam.addMouseListener(new MouseClickListener(jLblVoornaam));
			jLblVoornaam.setForeground(Color.BLUE);
			jLblVoornaam.setFont(font);
			jLblNaam = new JLabel();
			jLblNaam.setText("Familienaam:");
			jLblNaam.addMouseListener(new MouseClickListener(jLblNaam));
			jLblNaam.setForeground(Color.BLUE);
			jLblNaam.setFont(font);
			jPanelSouthWest = new JPanel();
			jPanelSouthWest.setLayout(new GridBagLayout());
			jPanelSouthWest.add(jLblNaam, new GridBagConstraints());
			jPanelSouthWest.add(jLblVoornaam, gridBagConstraints47);
			jPanelSouthWest.add(getJTxtNaam(), gridBagConstraints48);
			jPanelSouthWest.add(getJTxtVoornaam(), gridBagConstraints49);
			jPanelSouthWest.add(jLblGeslacht, gridBagConstraints50);
			jPanelSouthWest.add(jLblStraat, gridBagConstraints51);
			jPanelSouthWest.add(jLblNummer, gridBagConstraints52);
			jPanelSouthWest.add(jLblPostcode, gridBagConstraints53);
			jPanelSouthWest.add(jLblPlaats, gridBagConstraints54);
			jPanelSouthWest.add(jLblOpmerking, gridBagConstraints55);
			jPanelSouthWest.add(getJCboGeslacht(), gridBagConstraints56);
			jPanelSouthWest.add(getJTxtStraat(), gridBagConstraints57);
			jPanelSouthWest.add(getJTxtNummer(), gridBagConstraints58);
			jPanelSouthWest.add(getJTxtPostcode(), gridBagConstraints59);
			jPanelSouthWest.add(getJTxtPlaats(), gridBagConstraints60);
			jPanelSouthWest.add(getJTxtOpmerking(), gridBagConstraints61);
			jPanelSouthWest.add(jLblIdentiteitskaart, gridBagConstraints62);
			jPanelSouthWest.add(jLblNationaliteit, gridBagConstraints63);
			jPanelSouthWest.add(jLblGeboortedatum, gridBagConstraints64);
			jPanelSouthWest.add(jLblGeboorteplaats, gridBagConstraints65);
			jPanelSouthWest.add(jLblNummerplaat, gridBagConstraints66);
			jPanelSouthWest.add(jLblTelefoon, gridBagConstraints67);
			jPanelSouthWest.add(getJTxtIdentiteitskaart(), gridBagConstraints68);
			jPanelSouthWest.add(getJCboNationaliteit(), gridBagConstraints69);
			jPanelSouthWest.add((JComponent) getjDpGeboortedatum(), gridBagConstraints70);
			jPanelSouthWest.add(getJTxtGeboorteplaats(), gridBagConstraints71);
			jPanelSouthWest.add(getJTxtNummerplaat(), gridBagConstraints72);
			jPanelSouthWest.add(getJTxtTelefoon(), gridBagConstraints73);
		}
		return jPanelSouthWest;
	}

	/**
	 * This method initializes jPanelSouthEast
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSouthEast() {
		if (jPanelSouthEast == null) {
			jLblKeuze = new JLabel();
			jLblKeuze.setText("Maak uw keuze");
			jLblKeuze.setFont(font);
			jPanelSouthEast = new JPanel();
			jPanelSouthEast.setLayout(new GridBagLayout());
		}
		return jPanelSouthEast;
	}

	/**
	 * This method initializes jTxtFichenummer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtFichenummer() {
		if (jTxtFichenummer == null) {
			jTxtFichenummer = new JTextField();
			jTxtFichenummer.setPreferredSize(dimTextfield);
			jTxtFichenummer.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtStandplaats().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtFichenummer;
	}

	/**
	 * This method initializes jTxtStandplaats
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtStandplaats() {
		if (jTxtStandplaats == null) {
			jTxtStandplaats = new JTextField();
			jTxtStandplaats.setPreferredSize(dimTextfield);
			jTxtStandplaats.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJCboBadgetype().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtStandplaats;
	}

	/**
	 * This method initializes jCboBadgetype
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboBadgetype() {
		if (jCboBadgetype == null) {
			jCboBadgetype = new JComboBox();
			jCboBadgetype.addItem("");
			jCboBadgetype.addItem("LOS");
			jCboBadgetype.addItem("VAST");
			jCboBadgetype.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtBadge().requestFocusInWindow();
					}
				}
			});
		}
		return jCboBadgetype;
	}

	/**
	 * This method initializes jTxtBadge
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtBadge() {
		if (jTxtBadge == null) {
			jTxtBadge = new JTextField();
			jTxtBadge.setPreferredSize(dimTextfield);
			jTxtBadge.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJCboAankomst().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtBadge;
	}

	/**
	 * This method initializes jPanelAankomst
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelAankomst() {
		if (jPanelAankomst == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridwidth = 2;
			gridBagConstraints12.insets = new Insets(0, 40, 0, 0);
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridx = 0;
			jLblAankomst = new JLabel();
			jLblAankomst.setText("Aankomst:");
			jLblAankomst.addMouseListener(new MouseClickListener(jLblAankomst));
			jLblAankomst.setFont(font);
			jPanelAankomst = new JPanel();
			jPanelAankomst.setLayout(new GridBagLayout());
			jPanelAankomst.add(getJCboAankomst(), gridBagConstraints11);
			jPanelAankomst.add((Component) getjDpAankomst(), gridBagConstraints13);
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			jPanelAankomst.setBorder(raisedetched);

			jPanelAankomst.add(jLblAankomst, gridBagConstraints12);
		}
		return jPanelAankomst;
	}

	/**
	 * This method initializes jCboAankomst
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboAankomst() {
		if (jCboAankomst == null) {
			jCboAankomst = new JComboBox();
			jCboAankomst.addItem("");
			jCboAankomst.addItem("OP");
			jCboAankomst.addItem("VOOR");
			jCboAankomst.addItem("NA");
			jCboAankomst.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getjDpAankomst().getJFormattedTextField().requestFocusInWindow();
					}
				}
			});
		}
		return jCboAankomst;
	}

	/**
	 * This method initializes jPanelVertrek
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelVertrek() {
		if (jPanelVertrek == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridwidth = 2;
			gridBagConstraints17.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints16.gridy = 1;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.gridx = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints15.gridy = 1;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.gridx = 0;
			jLblVertrek = new JLabel();
			jLblVertrek.setText("Vertrek:");
			jLblVertrek.addMouseListener(new MouseClickListener(jLblVertrek));
			jLblVertrek.setFont(font);
			jPanelVertrek = new JPanel();
			jPanelVertrek.setLayout(new GridBagLayout());
			jPanelVertrek.add(getJCboVertrek(), gridBagConstraints15);
			jPanelVertrek.add((Component) getjDpVertrek(), gridBagConstraints16);
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			jPanelVertrek.setBorder(raisedetched);
			jPanelVertrek.add(jLblVertrek, gridBagConstraints17);
		}
		return jPanelVertrek;
	}

	/**
	 * This method initializes jCboVertrek
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboVertrek() {
		if (jCboVertrek == null) {
			jCboVertrek = new JComboBox();
			jCboVertrek.addItem("");
			jCboVertrek.addItem("OP");
			jCboVertrek.addItem("VOOR");
			jCboVertrek.addItem("NA");
			jCboVertrek.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getjDpVertrek().getJFormattedTextField().requestFocusInWindow();
					}
				}
			});
		}
		return jCboVertrek;
	}

	/**
	 * This method initializes jPanelBetaling
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelBetaling() {
		if (jPanelBetaling == null) {
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints24.gridy = 2;
			gridBagConstraints24.gridx = 2;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints23.gridy = 2;
			gridBagConstraints23.insets = new Insets(0, 0, 0, 40);
			gridBagConstraints23.gridx = 1;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints22.gridy = 2;
			gridBagConstraints22.insets = new Insets(0, 0, 0, 40);
			gridBagConstraints22.gridx = 0;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.gridy = 1;
			jLblSaldo = new JLabel();
			jLblSaldo.setText("Saldo:");
			jLblSaldo.addMouseListener(new MouseClickListener(jLblSaldo));
			jLblSaldo.setFont(font);
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 1;
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 1;
			jLblBetaald = new JLabel();
			jLblBetaald.setText("Betaald:");
			jLblBetaald.addMouseListener(new MouseClickListener(jLblBetaald));
			jLblBetaald.setFont(font);
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridy = 1;
			jLblTeBetalen = new JLabel();
			jLblTeBetalen.setText("Te betalen:");
			jLblTeBetalen.addMouseListener(new MouseClickListener(jLblTeBetalen));
			jLblBetaling = new JLabel();
			jLblBetaling.setText("Betaling");
			jLblBetaling.setFont(font);
			jPanelBetaling = new JPanel();
			jPanelBetaling.setLayout(new GridBagLayout());
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			jPanelBetaling.setBorder(raisedetched);
			jPanelBetaling.add(jLblBetaling, new GridBagConstraints());
			jPanelBetaling.add(jLblTeBetalen, gridBagConstraints19);
			jPanelBetaling.add(jLblBetaald, gridBagConstraints20);
			jPanelBetaling.add(jLblSaldo, gridBagConstraints21);
			jPanelBetaling.add(getJTxtTeBetalen(), gridBagConstraints22);
			jPanelBetaling.add(getJTxtBetaald(), gridBagConstraints23);
			jPanelBetaling.add(getJCboSaldo(), gridBagConstraints24);
		}
		return jPanelBetaling;
	}

	/**
	 * This method initializes jTxtTeBetalen
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtTeBetalen() {
		if (jTxtTeBetalen == null) {
			jTxtTeBetalen = new JTextField();
			jTxtTeBetalen.setPreferredSize(dimTextfield);
			jTxtTeBetalen.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtBetaald().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtTeBetalen;
	}

	/**
	 * This method initializes jTxtBetaald
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtBetaald() {
		if (jTxtBetaald == null) {
			jTxtBetaald = new JTextField();
			jTxtBetaald.setPreferredSize(dimTextfield);
			jTxtBetaald.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJCboSaldo().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtBetaald;
	}

	/**
	 * This method initializes jCboSaldo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboSaldo() {
		if (jCboSaldo == null) {
			jCboSaldo = new JComboBox();
			jCboSaldo.addItem("");
			jCboSaldo.addItem(Constant.VEREFFEND);
			jCboSaldo.addItem(Constant.OPENSTAAND_SALDO);
			jCboSaldo.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJCboTypePersoon().requestFocusInWindow();
					}
				}
			});
		}
		return jCboSaldo;
	}

	/**
	 * This method initializes jCboTypePersoon
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboTypePersoon() {
		if (jCboTypePersoon == null) {
			jCboTypePersoon = new JComboBox();
			jCboTypePersoon.addItem("");
			jCboTypePersoon.addItem("ANDERE");
			jCboTypePersoon.addItem("Betalers");
			jCboTypePersoon.addItem("CHAUFFEUR");
			jCboTypePersoon.addItem("DOCHTER");
			jCboTypePersoon.addItem("EIGENAAR HOOFD");
			jCboTypePersoon.addItem("EIGENAAR VROUW");
			jCboTypePersoon.addItem("HUURDER");
			jCboTypePersoon.addItem("Ongehuwd kind");
			jCboTypePersoon.addItem("OUDERS");
			jCboTypePersoon.addItem("SchoonDochter");
			jCboTypePersoon.addItem("SchoonZoon");
			jCboTypePersoon.addItem("ZOON");
			jCboTypePersoon.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtNaam().requestFocusInWindow();
					}
				}
			});
		}
		return jCboTypePersoon;
	}

	/**
	 * This method initializes jTxtNaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtNaam() {
		if (jTxtNaam == null) {
			jTxtNaam = new JTextField();
			jTxtNaam.setPreferredSize(dimTextfield2);
			jTxtNaam.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtVoornaam().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtNaam;
	}

	/**
	 * This method initializes jTxtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtVoornaam() {
		if (jTxtVoornaam == null) {
			jTxtVoornaam = new JTextField();
			jTxtVoornaam.setPreferredSize(dimTextfield2);
			jTxtVoornaam.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJCboGeslacht().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtVoornaam;
	}

	/**
	 * This method initializes jCboGeslacht
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboGeslacht() {
		if (jCboGeslacht == null) {
			jCboGeslacht = new JComboBox();
			jCboGeslacht.addItem("");
			jCboGeslacht.addItem("M");
			jCboGeslacht.addItem("V");
			jCboGeslacht.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtStraat().requestFocusInWindow();
					}
				}
			});
		}
		return jCboGeslacht;
	}

	/**
	 * This method initializes jTxtStraat
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtStraat() {
		if (jTxtStraat == null) {
			jTxtStraat = new JTextField();
			jTxtStraat.setPreferredSize(dimTextfield2);
			jTxtStraat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtNummer().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtStraat;
	}

	/**
	 * This method initializes jTxtNummer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtNummer() {
		if (jTxtNummer == null) {
			jTxtNummer = new JTextField();
			jTxtNummer.setPreferredSize(dimTextfield2);
			jTxtNummer.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtPostcode().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtNummer;
	}

	/**
	 * This method initializes jTxtPostcode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtPostcode() {
		if (jTxtPostcode == null) {
			jTxtPostcode = new JTextField();
			jTxtPostcode.setPreferredSize(dimTextfield2);
			jTxtPostcode.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtPlaats().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtPostcode;
	}

	/**
	 * This method initializes jTxtPlaats
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtPlaats() {
		if (jTxtPlaats == null) {
			jTxtPlaats = new JTextField();
			jTxtPlaats.setPreferredSize(dimTextfield2);
			jTxtPlaats.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtOpmerking().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtPlaats;
	}

	/**
	 * This method initializes jTxtOpmerking
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtOpmerking() {
		if (jTxtOpmerking == null) {
			jTxtOpmerking = new JTextField();
			jTxtOpmerking.setPreferredSize(dimTextfield2);
			jTxtOpmerking.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtIdentiteitskaart().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtOpmerking;
	}

	/**
	 * This method initializes jTxtIdentiteitskaart
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtIdentiteitskaart() {
		if (jTxtIdentiteitskaart == null) {
			jTxtIdentiteitskaart = new JTextField();
			jTxtIdentiteitskaart.setPreferredSize(dimTextfield2);
			jTxtIdentiteitskaart.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getjDpGeboortedatum().getJFormattedTextField().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtIdentiteitskaart;
	}

	/**
	 * This method initializes jCboNationaliteit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboNationaliteit() {
		if (jCboNationaliteit == null) {
			jCboNationaliteit = new JComboBox();
			jCboNationaliteit.addItem("");
		}
		return jCboNationaliteit;
	}

	/**
	 * This method initializes jTxtGeboorteplaats
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtGeboorteplaats() {
		if (jTxtGeboorteplaats == null) {
			jTxtGeboorteplaats = new JTextField();
			jTxtGeboorteplaats.setPreferredSize(dimTextfield2);
			jTxtGeboorteplaats.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtNummerplaat().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtGeboorteplaats;
	}

	/**
	 * This method initializes jTxtNummerplaat
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtNummerplaat() {
		if (jTxtNummerplaat == null) {
			jTxtNummerplaat = new JTextField();
			jTxtNummerplaat.setPreferredSize(dimTextfield2);
			jTxtNummerplaat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtTelefoon().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtNummerplaat;
	}

	/**
	 * This method initializes jTxtTelefoon
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtTelefoon() {
		if (jTxtTelefoon == null) {
			jTxtTelefoon = new JTextField();
			jTxtTelefoon.setPreferredSize(dimTextfield2);
			jTxtTelefoon.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtFichenummer().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtTelefoon;
	}

	/**
	 * This method initializes jPanelKeuze
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelKeuze() {
		if (jPanelKeuze == null) {
			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.insets = new Insets(20, 5, 0, 0);
			gridBagConstraints80.gridy = 1;
			gridBagConstraints80.gridx = 0;
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			gridBagConstraints79.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints79.gridy = 0;
			gridBagConstraints79.gridwidth = 3;
			gridBagConstraints79.gridx = 0;
			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
			gridBagConstraints77.gridx = 0;
			gridBagConstraints77.gridwidth = 3;
			gridBagConstraints77.gridy = 0;
			jLblKeuze = new JLabel();
			jLblKeuze.setText("Maak uw keuze");
			jLblKeuze.setFont(font);
			GridBagConstraints gridBagConstraints76 = new GridBagConstraints();
			gridBagConstraints76.gridx = -1;
			gridBagConstraints76.gridy = -1;
			jPanelKeuze = new JPanel();
			jPanelKeuze.setLayout(new GridBagLayout());
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			jPanelKeuze.setBorder(raisedetched);
			// jPanelKeuze.add(jLblKeuze, gridBagConstraints77);
			jPanelKeuze.add(getJBtnWis(), gridBagConstraints79);
			jPanelKeuze.add(getJBtnFilter(), gridBagConstraints80);
		}
		return jPanelKeuze;
	}

	public JDatePicker getjDpGeboortedatum() {
		if (jDpGeboortedatum == null) {
			jDpGeboortedatum = JDateComponentFactory.createJDatePicker();
			jDpGeboortedatum.setTextEditable(true);
			jDpGeboortedatum.setShowYearButtons(true);
			jDpGeboortedatum.clearTextField();
			jDpGeboortedatum.getJFormattedTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtGeboorteplaats().requestFocusInWindow();
					}
				}
			});
		}
		return jDpGeboortedatum;
	}

	public JDatePicker getjDpAankomst() {
		if (jDpAankomst == null) {
			jDpAankomst = JDateComponentFactory.createJDatePicker();
			jDpAankomst.setTextEditable(true);
			jDpAankomst.setShowYearButtons(true);
			jDpAankomst.clearTextField();
			jDpAankomst.getJFormattedTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJCboVertrek().requestFocusInWindow();
					}
				}
			});
		}
		return jDpAankomst;
	}

	public JDatePicker getjDpVertrek() {
		if (jDpVertrek == null) {
			jDpVertrek = JDateComponentFactory.createJDatePicker();
			jDpVertrek.setTextEditable(true);
			jDpVertrek.setShowYearButtons(true);
			jDpVertrek.clearTextField();
			jDpVertrek.getJFormattedTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtTeBetalen().requestFocusInWindow();
					}
				}
			});
		}
		return jDpVertrek;
	}

	public void setjDpGeboortedatum(JDatePicker jDpGeboortedatum) {
		this.jDpGeboortedatum = jDpGeboortedatum;
	}

	public PersoonService getPersoonService() {
		return persoonService;
	}

	public void setPersoonService(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	private void clearFields() {
		getJTxtFichenummer().setText("");
		getJTxtStandplaats().setText("");
		getJTxtBadge().setText("");
		getJTxtTeBetalen().setText("");
		getJTxtBetaald().setText("");
		getJTxtNaam().setText("");
		getJTxtVoornaam().setText("");
		getJCboGeslacht().setSelectedIndex(0);
		getJTxtStraat().setText("");
		getJTxtNummer().setText("");
		getJTxtPostcode().setText("");
		getJTxtPlaats().setText("");
		getJTxtOpmerking().setText("");
		getJTxtIdentiteitskaart().setText("");
		getJTxtGeboorteplaats().setText("");
		getJTxtNummerplaat().setText("");
		getJTxtTelefoon().setText("");
		getJCboAankomst().setSelectedIndex(0);
		getJCboBadgetype().setSelectedIndex(0);
		getJCboGeslacht().setSelectedIndex(0);
		getJCboNationaliteit().setSelectedIndex(0);
		getJCboSaldo().setSelectedIndex(0);
		getJCboTypePersoon().setSelectedIndex(0);
		getJCboVertrek().setSelectedIndex(0);
		getjDpAankomst().clearTextField();
		getjDpVertrek().clearTextField();
		getjDpGeboortedatum().clearTextField();
	}

	private class MouseClickListener implements MouseInputListener {

		private final JLabel jLabel;

		public MouseClickListener(JLabel jLabel) {
			this.jLabel = jLabel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (jLabel.getForeground().equals(Color.BLUE)) {
				jLabel.setForeground(null);
			} else {
				jLabel.setForeground(Color.BLUE);
			}
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * This method initializes jBtnWis
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnWis() {
		if (jBtnWis == null) {
			jBtnWis = new JButton();
			jBtnWis.setFont(new Font("SansSerif", Font.BOLD, 24));
			jBtnWis.setText("Wissen");
			jBtnWis.setPreferredSize(new Dimension(200, 50));
			jBtnWis.addActionListener(this);
		}
		return jBtnWis;
	}

	/**
	 * This method initializes jBtnFilter
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnFilter() {
		if (jBtnFilter == null) {
			jBtnFilter = new JButton();
			jBtnFilter.setText("Filter");
			jBtnFilter.setFont(new Font("SansSerif", Font.BOLD, 24));
			jBtnFilter.setPreferredSize(new Dimension(200, 50));
			jBtnFilter.addActionListener(this);
		}
		return jBtnFilter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getJBtnWis()) {
			clearFields();
		} else if (e.getSource() == getJBtnFilter()) {
			if (!checkPrerequisites()) {
				return;
			}
			try {
				Map<String, Object> zoekCriteria = createZoekCriteria();
				Map<String, Object> projectionList = createProjectionList();
				List<Object[]> data = standplaatsService.zoekStandplaatsEnFicheGegevens(zoekCriteria, projectionList);
				Vector<Object[]> list = new Vector<Object[]>();
				if (projectionList.containsKey(Constant.STANDPLAATS)) {
					for (int j = 0; j < data.size(); j++) {
						Object[] o = data.get(j);
						Object[] o2 = new Object[o.length - 1];
						Character plaatsgroep = (Character) o[0];
						int plaatsnummer = (Integer) o[1];
						String sPlaatsnummer = Integer.toString(plaatsnummer);
						while (sPlaatsnummer.length() < 3) {
							sPlaatsnummer = "0" + sPlaatsnummer;
						}
						o2[0] = Character.toString(plaatsgroep) + sPlaatsnummer;
						if (o.length > 2) {
							for (int i = 2; i < o.length; i++) {
								o2[i - 1] = o[i];
							}
						}
						list.add(o2);
					}
				}

				new ZoekResultatenDialog(list, getColumnNames(), getController());

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private boolean checkPrerequisites() {
		boolean ret = true;
		if (getjDpAankomst().getTime() != null
				&& !StringUtils.isEmpty(getjDpAankomst().getJFormattedTextField().getText())
				&& getJCboAankomst().getSelectedIndex() == 0) {
			ret = false;
		}
		if (getjDpVertrek().getTime() != null
				&& !StringUtils.isEmpty(getjDpVertrek().getJFormattedTextField().getText())
				&& getJCboVertrek().getSelectedIndex() == 0) {
			ret = false;
		}
		if (!ret) {
			String message = "Gelieve bij selectie van datum de optie OP, VOOR of NA te selecteren.";
			String title = "Selectie datum";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
		}
		return ret;
	}

	private Object[] getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		if (jLblStandplaats.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.STANDPLAATS);
		if (jLblNaam.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.NAAM);
		if (jLblVoornaam.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.VOORNAAM);
		if (jLblBadge.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.BADGE);
		if (jLblBadgeType.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.BADGETYPE);
		if (jLblFichenummer.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.FICHENUMMER);
		if (jLblAankomst.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.AANKOMST);
		if (jLblVertrek.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.VERTREK);
		if (jLblTypePersoon.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.TYPE_PERSOON);
		if (jLblGeboortedatum.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.GEBOORTEDATUM);
		if (jLblGeboorteplaats.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.GEBOORTEPLAATS);
		if (jLblIdentiteitskaart.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.IDENTITEITSKAART);
		if (jLblNationaliteit.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.NATIONALITEIT);
		if (jLblOpmerking.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.OPMERKING);
		if (jLblStraat.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.STRAAT);
		if (jLblNummer.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.NUMMER);
		if (jLblPostcode.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.POSTCODE);
		if (jLblPlaats.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.PLAATS);
		if (jLblTelefoon.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.TELEFOON);
		if (jLblNummerplaat.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.NUMMERPLAAT);
		if (jLblTeBetalen.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.TE_BETALEN);
		if (jLblBetaald.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.BETAALD);
		if (jLblSaldo.getForeground().equals(Color.BLUE))
			columnNames.add(Constant.SALDO);

		Object[] sArr = columnNames.toArray();
		return sArr;
	}

	class ZoekTableModel extends AbstractTableModel {
		/**
			 * 
			 */
		private static final long serialVersionUID = 1L;
		private final Object[] columnNames = getColumnNames();
		private Vector<Object[]> data = null;

		public ZoekTableModel(Vector<Object[]> list) {
			data = list;
		}

		public void setColumnData(Vector<Object[]> list) {
			this.data = list;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int col) {
			return (String) columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			Object[] o = data.get(row);
			Object obj = o[col];
			if (obj == null)
				obj = "";
			return obj;
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for each cell. If we didn't implement this
		 * method, then the last column would contain text ("true"/"false"), rather than a check box.
		 */
		@Override
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */

		private final boolean DEBUG = false;

		@Override
		public void setValueAt(Object value, int row, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col + " to " + value + " (an instance of "
						+ value.getClass() + ")");
			}

			Object[] o = data.get(row);
			o[col] = value;
			// Normally, one should call fireTableCellUpdated() when
			// a value is changed. However, doing so in this demo
			// causes a problem with TableSorter. The tableChanged()
			// call on TableSorter that results from calling
			// fireTableCellUpdated() causes the indices to be regenerated
			// when they shouldn't be. Ideally, TableSorter should be
			// given a more intelligent tableChanged() implementation,
			// and then the following line can be uncommented.
			// fireTableCellUpdated(row, col);

			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					Object[] o = data.get(i);
					System.out.print("  " + o[j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}

	private String getHuurderspositieVoorNummer(int i) {
		String s = "";
		switch (i) {
		case 1:
			s = HuurderPositieEnum.ANDERE.toString();
			break;
		case 3:
			s = HuurderPositieEnum.CHAUFFEUR.toString();
			break;
		case 4:
			s = HuurderPositieEnum.DOCHTER.toString();
			break;
		case 5:
			s = HuurderPositieEnum.HOOFD.toString();
			break;
		case 6:
			s = HuurderPositieEnum.ECHTGENOTE.toString();
			break;
		case 7:
			s = HuurderPositieEnum.HUURDER.toString();
			break;
		case 8:
			s = HuurderPositieEnum.KIND.toString();
			break;
		case 9:
			s = HuurderPositieEnum.OUDER.toString();
			break;
		case 10:
			s = HuurderPositieEnum.SCHOONDOCHTER.toString();
			break;
		case 11:
			s = HuurderPositieEnum.SCHOONZOON.toString();
			break;
		case 12:
			s = HuurderPositieEnum.ZOON.toString();
			break;
		}
		return s;
	}

	private Map<String, Object> createZoekCriteria() {
		Map<String, Object> zoekCriteria = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(getJTxtStandplaats().getText())) {
			try {
				String plaatsgroep = getJTxtStandplaats().getText().substring(0, 1);
				if (getJTxtStandplaats().getText().length() > 1) {
					String plaatsnummer = getJTxtStandplaats().getText().substring(1);
					zoekCriteria.put(Constant.PLAATSGROEP, plaatsgroep);
					zoekCriteria.put(Constant.PLAATSNUMMER, plaatsnummer);
				}
			} catch (Exception e) {
			}
		}
		if (!StringUtils.isEmpty(getJTxtBadge().getText()))
			zoekCriteria.put(Constant.BADGE, getJTxtBadge().getText());
		if (!StringUtils.isEmpty((String) getJCboBadgetype().getSelectedItem()))
			zoekCriteria.put(Constant.BADGETYPE, getJCboBadgetype().getSelectedItem());
		if (!StringUtils.isEmpty(getJTxtFichenummer().getText()))
			zoekCriteria.put(Constant.FICHENUMMER, getJTxtFichenummer().getText());
		if (getjDpAankomst().getTime() != null
				&& !StringUtils.isEmpty(getjDpAankomst().getJFormattedTextField().getText())
				&& getJCboAankomst().getSelectedIndex() != 0) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = formatter.format(getjDpAankomst().getTime());
			zoekCriteria.put(Constant.AANKOMST, formattedDate);
			if (getJCboAankomst().getSelectedItem() == Constant.VAN) {
				zoekCriteria.put(Constant.AANKOMST_TYPE, Constant.VAN);
			} else if (getJCboAankomst().getSelectedItem() == Constant.TOT) {
				zoekCriteria.put(Constant.AANKOMST_TYPE, Constant.TOT);
			} else if (getJCboAankomst().getSelectedItem() == Constant.OP) {
				zoekCriteria.put(Constant.AANKOMST_TYPE, Constant.OP);
			}
		}
		if (getjDpVertrek().getTime() != null
				&& !StringUtils.isEmpty(getjDpVertrek().getJFormattedTextField().getText())
				&& getJCboVertrek().getSelectedIndex() != 0) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = formatter.format(getjDpVertrek().getTime());
			zoekCriteria.put(Constant.VERTREK, formattedDate);
			if (getJCboVertrek().getSelectedItem() == Constant.VAN) {
				zoekCriteria.put(Constant.VERTREK_TYPE, Constant.VAN);
			} else if (getJCboVertrek().getSelectedItem() == Constant.TOT) {
				zoekCriteria.put(Constant.VERTREK_TYPE, Constant.TOT);
			} else if (getJCboVertrek().getSelectedItem() == Constant.OP) {
				zoekCriteria.put(Constant.VERTREK_TYPE, Constant.OP);
			}
		}
		if (!StringUtils.isEmpty((String) getJCboTypePersoon().getSelectedItem())) {
			String s = getHuurderspositieVoorNummer(getJCboTypePersoon().getSelectedIndex());
			if (s != "") {
				zoekCriteria.put(Constant.TYPE_PERSOON, s);
			}
		}

		// Map<String, Object> zoekCritPersoon = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(getJTxtNaam().getText())) {
			zoekCriteria.put(Constant.NAAM, getJTxtNaam().getText());
		}

		if (!StringUtils.isEmpty(getJTxtVoornaam().getText())) {
			zoekCriteria.put(Constant.VOORNAAM, getJTxtVoornaam().getText());
		}
		if (!StringUtils.isEmpty(getjDpGeboortedatum().getTime().toString())
				&& !StringUtils.isEmpty(getjDpGeboortedatum().getJFormattedTextField().getText())) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			zoekCriteria.put(Constant.GEBOORTEDATUM, formatter.format(getjDpGeboortedatum().getTime()));
		}
		if (!StringUtils.isEmpty(getJTxtGeboorteplaats().getText())) {
			zoekCriteria.put(Constant.GEBOORTEPLAATS, getJTxtGeboorteplaats().getText());
		}
		if (!StringUtils.isEmpty(getJTxtIdentiteitskaart().getText())) {
			zoekCriteria.put(Constant.IDENTITEITSKAART, getJTxtIdentiteitskaart().getText());
		}
		if (!StringUtils.isEmpty((String) getJCboNationaliteit().getSelectedItem())) {
			zoekCriteria.put(Constant.NATIONALITEIT, getJCboNationaliteit().getSelectedItem());
		}
		if (!StringUtils.isEmpty(getJTxtOpmerking().getText())) {
			zoekCriteria.put(Constant.OPMERKING, getJTxtOpmerking().getText());
		}
		// zoekCriteria.put(Constant.PERSOON, zoekCritPersoon);

		// Map<String, Object> zoekCritAdres = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(getJTxtStraat().getText())) {
			zoekCriteria.put(Constant.STRAAT, getJTxtStraat().getText());
		}
		if (!StringUtils.isEmpty(getJTxtNummer().getText())) {
			zoekCriteria.put(Constant.NUMMER, getJTxtNummer().getText());
		}
		if (!StringUtils.isEmpty(getJTxtPostcode().getText())) {
			zoekCriteria.put(Constant.POSTCODE, getJTxtPostcode().getText());
		}
		if (!StringUtils.isEmpty(getJTxtPlaats().getText())) {
			zoekCriteria.put(Constant.PLAATS, getJTxtPlaats().getText());
		}
		// zoekCriteria.put(Constant.ADRES, zoekCritAdres);

		if (!StringUtils.isEmpty(getJTxtTelefoon().getText())) {
			zoekCriteria.put(Constant.TELEFOON, getJTxtTelefoon().getText());
		}
		if (!StringUtils.isEmpty(getJTxtNummerplaat().getText())) {
			zoekCriteria.put(Constant.NUMMERPLAAT, getJTxtNummerplaat().getText());
		}

		if (!StringUtils.isEmpty(getJTxtTeBetalen().getText())) {
			zoekCriteria.put(Constant.TE_BETALEN, getJTxtTeBetalen().getText());
		}
		if (!StringUtils.isEmpty(getJTxtBetaald().getText())) {
			zoekCriteria.put(Constant.BETAALD, getJTxtBetaald().getText());
		}
		if (getJCboSaldo().getSelectedIndex() != 0) {
			zoekCriteria.put(Constant.SALDO, getJCboSaldo().getSelectedItem());
		}

		return zoekCriteria;
	}

	private Map<String, Object> createProjectionList() {
		Map<String, Object> projectionList = new HashMap<String, Object>();
		// Map<String, Object> projListPersoon = new HashMap<String, Object>();
		// Map<String, Object> projListAdres = new HashMap<String, Object>();
		if (jLblStandplaats.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.STANDPLAATS, true);
		if (jLblBadge.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.BADGE, true);
		if (jLblBadgeType.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.BADGETYPE, true);
		if (jLblFichenummer.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.FICHENUMMER, true);
		if (jLblAankomst.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.AANKOMST, true);
		if (jLblVertrek.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.VERTREK, true);
		if (jLblTypePersoon.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.TYPE_PERSOON, true);

		if (jLblNaam.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.NAAM, true);
		if (jLblVoornaam.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.VOORNAAM, true);
		if (jLblGeboortedatum.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.GEBOORTEDATUM, true);
		if (jLblGeboorteplaats.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.GEBOORTEPLAATS, true);
		if (jLblIdentiteitskaart.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.IDENTITEITSKAART, true);
		if (jLblNationaliteit.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.NATIONALITEIT, true);
		if (jLblOpmerking.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.OPMERKING, true);
		// projectionList.put(Constant.PERSOON, projListPersoon);

		if (jLblStraat.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.STRAAT, true);
		if (jLblNummer.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.NUMMER, true);
		if (jLblPostcode.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.POSTCODE, true);
		if (jLblPlaats.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.PLAATS, true);
		// projectionList.put(Constant.ADRES, projListAdres);

		if (jLblTelefoon.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.TELEFOON, true);
		if (jLblNummerplaat.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.NUMMERPLAAT, true);

		if (jLblTeBetalen.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.TE_BETALEN, true);
		if (jLblBetaald.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.BETAALD, true);
		if (jLblSaldo.getForeground().equals(Color.BLUE))
			projectionList.put(Constant.SALDO, true);
		return projectionList;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
