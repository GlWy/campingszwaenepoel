package be.camping.campingzwaenepoel.presentation.ui.panels;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import java.awt.Dimension;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

public class KassaPanel2 extends JPanel implements PanelInterface {

	private static final long serialVersionUID = 1L;

    private Controller controller;
    
	private Dimension aantalDim = new Dimension(60, 36);
	private Dimension kostenDim = new Dimension(140, 36);
	private Dimension prijsDim = new Dimension(80, 36);

	private JLabel jLabelKasbon = null;

	private JLabel jLblNachten = null;

	private JLabel jLblAuto100 = null;

	private JLabel JLblAuto150 = null;

	private JLabel jLblWaarborg = null;

	private JLabel jLblJeton = null;

	private JLabel jLblZakGeel = null;

	private JLabel jLblZakBlauw = null;

	private JLabel jLblCar = null;

	private JLabel jLblTent = null;

	private JLabel jLblVolwassen = null;

	private JLabel jLblKinderen = null;

	private JLabel jLblTelefoon = null;

	private JComboBox jCboKasbon = null;

	private JTextField jTxtNachten = null;

	private JTextField jTxtAuto100 = null;

	private JTextField jTxtAuto150 = null;

	private JTextField jTxtWaarborg = null;

	private JTextField jTxtJeton = null;

	private JTextField jTxtZakGeel = null;

	private JTextField jTxtZakBlauw = null;

	private JTextField jTxtCar = null;

	private JTextField jTxtTent = null;

	private JTextField jTxtVolwassen = null;

	private JTextField jTxtKinderen = null;
	
	private JTextField jTxtKostAuto100 = null;

	private JTextField jTxtKostAuto150 = null;

	private JTextField jTxtKostWaarborg = null;

	private JTextField jTxtKostJeton = null;

	private JTextField jTxtKostZakGeel = null;

	private JTextField jTxtKostZakBlauw = null;

	private JTextField jTxtKostCar = null;

	private JTextField jTxtKostTent = null;

	private JTextField jTxtKostVolwassen = null;

	private JTextField jTxtKostKinderen = null;

	private JTextField jTxtExtraKosten = null;

	private JTextField jTxtPrijsAuto100 = null;

	private JTextField jTxtPrijsAuto150 = null;

	private JTextField jTxtPrijsWaarborg = null;

	private JTextField jTxtPrijsJeton = null;

	private JTextField jTxtPrijsZakGeel = null;

	private JTextField jTxtPrijsZakBlauw = null;

	private JTextField jTxtPrijsCar = null;

	private JTextField jTxtPrijsTent = null;

	private JTextField jTxtPrijsVolwassen = null;

	private JTextField jTxtPrijsKinderen = null;

	private JButton jBtnStandaardWaarden = null;

	private JLabel jLblDatum = null;

	private JTextField jTxtDatum = null;

	private JLabel jLblKasbonNr = null;

	private JLabel jLblNaam = null;

	private JTextField jTxtNaam = null;

	private JLabel jLblStandplaats = null;

	private JTextField jTxtStandplaats = null;

	private JLabel jLblAutoplaat = null;

	private JTextField jTxtAutoplaat = null;

	private JLabel jLblBadge = null;

	private JTextField jTxtBadge = null;

	private JLabel jLblTotaal = null;

	private JLabel jLblBetaling = null;

	private JLabel jLblTerug = null;

	private JTextField jTxtTotaal = null;

	private JTextField jTxtBetaling = null;

	private JTextField jTxtTerug = null;

	private JLabel jLblKostNacht = null;

	private JLabel jLblAantalNachten = null;

	private JLabel jLblTotaalNachten = null;

	private JTextField jTxtKostNacht = null;

	private JTextField jTxtAantalNachten = null;

	private JTextField jTxtTotaalNachten = null;

	private JLabel JLblJetonsZakKost = null;

	private JLabel jLblTotZonderWB = null;

	private JTextField jTxtJetonsZakKost = null;

	private JTextField jTxtTotZonderWB = null;

	private JTextField jTxtDatVan = null;

	private JTextField jTxtDatTot = null;

	private JButton jBtnPrint = null;

	private JButton jBtnVerwijder = null;

	private JLabel jLblNieuw = null;

	private JButton jbtnFiche = null;

	private JButton jBtnReset = null;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setRemarksInGui() {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public KassaPanel2() {
		super();
//		initComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
		gridBagConstraints75.gridx = 7;
		gridBagConstraints75.anchor = GridBagConstraints.EAST;
		gridBagConstraints75.gridy = 10;
		GridBagConstraints gridBagConstraints74 = new GridBagConstraints();
		gridBagConstraints74.gridx = 7;
		gridBagConstraints74.anchor = GridBagConstraints.EAST;
		gridBagConstraints74.gridy = 9;
		GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
		gridBagConstraints73.gridx = 7;
		gridBagConstraints73.gridy = 8;
		jLblNieuw = new JLabel();
		jLblNieuw.setText("Nieuwe kasbon");
		GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
		gridBagConstraints72.gridx = 6;
		gridBagConstraints72.anchor = GridBagConstraints.WEST;
		gridBagConstraints72.gridy = 8;
		GridBagConstraints gridBagConstraints711 = new GridBagConstraints();
		gridBagConstraints711.gridx = 5;
		gridBagConstraints711.anchor = GridBagConstraints.EAST;
		gridBagConstraints711.gridy = 8;
		GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
		gridBagConstraints70.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints70.gridy = 7;
		gridBagConstraints70.anchor = GridBagConstraints.EAST;
		gridBagConstraints70.gridx = 7;
		GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
		gridBagConstraints69.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints69.gridy = 7;
		gridBagConstraints69.gridx = 6;
		GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
		gridBagConstraints67.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints67.gridy = 18;
		gridBagConstraints67.anchor = GridBagConstraints.WEST;
		gridBagConstraints67.gridx = 4;
		GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
		gridBagConstraints66.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints66.gridy = 17;
		gridBagConstraints66.anchor = GridBagConstraints.WEST;
		gridBagConstraints66.gridx = 4;
		GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
		gridBagConstraints65.gridx = 3;
		gridBagConstraints65.gridy = 18;
		jLblTotZonderWB = new JLabel();
		jLblTotZonderWB.setText("Tot. zonder WB");
		GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
		gridBagConstraints64.gridx = 3;
		gridBagConstraints64.gridwidth = 1;
		gridBagConstraints64.anchor = GridBagConstraints.WEST;
		gridBagConstraints64.gridy = 17;
		JLblJetonsZakKost = new JLabel();
		JLblJetonsZakKost.setText("Jetons/Zak/Kost");
		GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
		gridBagConstraints63.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints63.gridy = 16;
		gridBagConstraints63.anchor = GridBagConstraints.WEST;
		gridBagConstraints63.gridx = 5;
		GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
		gridBagConstraints62.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints62.gridy = 16;
		gridBagConstraints62.anchor = GridBagConstraints.WEST;
		gridBagConstraints62.gridx = 4;
		GridBagConstraints gridBagConstraints611 = new GridBagConstraints();
		gridBagConstraints611.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints611.gridy = 16;
		gridBagConstraints611.gridx = 3;
		GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
		gridBagConstraints60.gridx = 5;
		gridBagConstraints60.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints60.gridy = 15;
		jLblTotaalNachten = new JLabel();
		jLblTotaalNachten.setText("Totaal nachten");
		GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
		gridBagConstraints59.gridx = 4;
		gridBagConstraints59.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints59.gridy = 15;
		jLblAantalNachten = new JLabel();
		jLblAantalNachten.setText("Aantal nachten = ");
		GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
		gridBagConstraints58.gridx = 3;
		gridBagConstraints58.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints58.gridy = 15;
		jLblKostNacht = new JLabel();
		jLblKostNacht.setText("Kost/nacht x ");
		GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
		gridBagConstraints57.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints57.gridy = 18;
		gridBagConstraints57.anchor = GridBagConstraints.WEST;
		gridBagConstraints57.gridwidth = 2;
		gridBagConstraints57.gridx = 1;
		GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
		gridBagConstraints56.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints56.gridy = 17;
		gridBagConstraints56.gridwidth = 2;
		gridBagConstraints56.anchor = GridBagConstraints.WEST;
		gridBagConstraints56.gridx = 1;
		GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
		gridBagConstraints55.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints55.gridy = 15;
		gridBagConstraints55.anchor = GridBagConstraints.WEST;
		gridBagConstraints55.gridwidth = 2;
		gridBagConstraints55.gridheight = 2;
		gridBagConstraints55.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints55.gridx = 1;
		GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
		gridBagConstraints53.gridx = 0;
		gridBagConstraints53.gridy = 18;
		jLblTerug = new JLabel();
		jLblTerug.setText("Terug");
		GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
		gridBagConstraints52.gridx = 0;
		gridBagConstraints52.gridy = 17;
		jLblBetaling = new JLabel();
		jLblBetaling.setText("Betaling");
		GridBagConstraints gridBagConstraints511 = new GridBagConstraints();
		gridBagConstraints511.gridx = 0;
		gridBagConstraints511.gridheight = 2;
		gridBagConstraints511.gridy = 15;
		jLblTotaal = new JLabel();
		jLblTotaal.setText("Totaal");
		GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
		gridBagConstraints48.gridy = 6;
//		gridBagConstraints48.weightx = 1.0;
		gridBagConstraints48.gridwidth = 2;
		gridBagConstraints48.anchor = GridBagConstraints.WEST;
		gridBagConstraints48.gridx = 6;
		GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
		gridBagConstraints47.gridx = 5;
		gridBagConstraints47.anchor = GridBagConstraints.EAST;
		gridBagConstraints47.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints47.gridy = 6;
		jLblBadge = new JLabel();
		jLblBadge.setText("Badge");
		GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
		gridBagConstraints46.gridy = 5;
		gridBagConstraints46.gridwidth = 2;
		gridBagConstraints46.anchor = GridBagConstraints.WEST;
		gridBagConstraints46.gridx = 6;
		GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
		gridBagConstraints45.gridx = 5;
		gridBagConstraints45.anchor = GridBagConstraints.EAST;
		gridBagConstraints45.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints45.gridy = 5;
		jLblAutoplaat = new JLabel();
		jLblAutoplaat.setText("Autoplaat");
		GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
		gridBagConstraints44.gridy = 4;
		gridBagConstraints44.anchor = GridBagConstraints.WEST;
		gridBagConstraints44.gridwidth = 2;
		gridBagConstraints44.gridx = 6;
		GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
		gridBagConstraints43.gridx = 5;
		gridBagConstraints43.anchor = GridBagConstraints.EAST;
		gridBagConstraints43.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints43.gridy = 4;
		jLblStandplaats = new JLabel();
		jLblStandplaats.setText("Standplaats");
		GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
		gridBagConstraints42.gridy = 3;
		gridBagConstraints42.gridwidth = 2;
		gridBagConstraints42.anchor = GridBagConstraints.WEST;
		gridBagConstraints42.gridx = 6;
		GridBagConstraints gridBagConstraints411 = new GridBagConstraints();
		gridBagConstraints411.gridx = 5;
		gridBagConstraints411.anchor = GridBagConstraints.EAST;
		gridBagConstraints411.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints411.gridy = 3;
		jLblNaam = new JLabel();
		jLblNaam.setText("Naam");
		GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
		gridBagConstraints40.gridx = 8;
		gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints40.anchor = GridBagConstraints.WEST;
		gridBagConstraints40.gridy = 1;
		jLblKasbonNr = new JLabel();
		jLblKasbonNr.setText("Kasbon Nr:");
		GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
		gridBagConstraints39.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints39.gridy = 1;
//		gridBagConstraints39.weightx = 1.0;
		gridBagConstraints39.anchor = GridBagConstraints.WEST;
		gridBagConstraints39.gridx = 6;
		GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
		gridBagConstraints38.gridx = 5;
		gridBagConstraints38.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints38.anchor = GridBagConstraints.EAST;
		gridBagConstraints38.gridy = 1;
		jLblDatum = new JLabel();
		jLblDatum.setText("Kasbon datum :");
		GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
		gridBagConstraints37.gridx = 4;
		gridBagConstraints37.anchor = GridBagConstraints.WEST;
		gridBagConstraints37.gridy = 3;
		GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
		gridBagConstraints36.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints36.gridy = 12;
//		gridBagConstraints36.weightx = 1.0;
		gridBagConstraints36.anchor = GridBagConstraints.WEST;
		gridBagConstraints36.gridx = 3;
		GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
		gridBagConstraints35.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints35.gridy = 11;
//		gridBagConstraints35.weightx = 1.0;
		gridBagConstraints35.anchor = GridBagConstraints.WEST;
		gridBagConstraints35.gridx = 3;
		GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
		gridBagConstraints34.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints34.gridy = 10;
//		gridBagConstraints34.weightx = 1.0;
		gridBagConstraints34.anchor = GridBagConstraints.WEST;
		gridBagConstraints34.gridx = 3;
		GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
		gridBagConstraints33.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints33.gridy = 9;
//		gridBagConstraints33.weightx = 1.0;
		gridBagConstraints33.anchor = GridBagConstraints.WEST;
		gridBagConstraints33.gridx = 3;
		GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
		gridBagConstraints32.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints32.gridy = 8;
//		gridBagConstraints32.weightx = 1.0;
		gridBagConstraints32.anchor = GridBagConstraints.WEST;
		gridBagConstraints32.gridx = 3;
		GridBagConstraints gridBagConstraints311 = new GridBagConstraints();
		gridBagConstraints311.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints311.gridy = 7;
//		gridBagConstraints311.weightx = 1.0;
		gridBagConstraints311.anchor = GridBagConstraints.WEST;
		gridBagConstraints311.gridx = 3;
		GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
		gridBagConstraints30.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints30.gridy = 6;
//		gridBagConstraints30.weightx = 1.0;
		gridBagConstraints30.anchor = GridBagConstraints.WEST;
		gridBagConstraints30.gridx = 3;
		GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
		gridBagConstraints29.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints29.gridy = 5;
//		gridBagConstraints29.weightx = 1.0;
		gridBagConstraints29.anchor = GridBagConstraints.WEST;
		gridBagConstraints29.gridx = 3;
		GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
		gridBagConstraints28.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints28.gridy = 4;
//		gridBagConstraints28.weightx = 1.0;
		gridBagConstraints28.anchor = GridBagConstraints.WEST;
		gridBagConstraints28.gridx = 3;
		GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
		gridBagConstraints27.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints27.gridy = 3;
//		gridBagConstraints27.weightx = 1.0;
		gridBagConstraints27.anchor = GridBagConstraints.WEST;
		gridBagConstraints27.gridx = 3;
		setBorder(new LineBorder(Color.BLACK, 2));
		
		GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
		gridBagConstraints26.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints26.gridy = 13;
//		gridBagConstraints26.weightx = 1.0;
		gridBagConstraints26.anchor = GridBagConstraints.WEST;
		gridBagConstraints26.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints26.gridx = 2;
		GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
		gridBagConstraints25.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints25.gridy = 12;
//		gridBagConstraints25.weightx = 1.0;
		gridBagConstraints25.anchor = GridBagConstraints.WEST;
		gridBagConstraints25.gridx = 2;
		GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
		gridBagConstraints24.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints24.gridy = 11;
//		gridBagConstraints24.weightx = 1.0;
		gridBagConstraints24.anchor = GridBagConstraints.WEST;
		gridBagConstraints24.gridx = 2;
		GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
		gridBagConstraints23.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints23.gridy = 10;
//		gridBagConstraints23.weightx = 1.0;
		gridBagConstraints23.anchor = GridBagConstraints.WEST;
		gridBagConstraints23.gridx = 2;
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints22.gridy = 9;
//		gridBagConstraints22.weightx = 1.0;
		gridBagConstraints22.anchor = GridBagConstraints.WEST;
		gridBagConstraints22.gridx = 2;
		GridBagConstraints gridBagConstraints211 = new GridBagConstraints();
		gridBagConstraints211.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints211.gridy = 8;
//		gridBagConstraints211.weightx = 1.0;
		gridBagConstraints211.anchor = GridBagConstraints.WEST;
		gridBagConstraints211.gridx = 2;
		GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
		gridBagConstraints20.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints20.gridy = 7;
//		gridBagConstraints20.weightx = 1.0;
		gridBagConstraints20.anchor = GridBagConstraints.WEST;
		gridBagConstraints20.gridx = 2;
		GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
		gridBagConstraints17.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints17.gridy = 6;
//		gridBagConstraints17.weightx = 1.0;
		gridBagConstraints17.anchor = GridBagConstraints.WEST;
		gridBagConstraints17.gridx = 2;
		GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
		gridBagConstraints16.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints16.gridy = 5;
//		gridBagConstraints16.weightx = 1.0;
		gridBagConstraints16.anchor = GridBagConstraints.WEST;
		gridBagConstraints16.gridx = 2;
		GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
		gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints15.gridy = 4;
//		gridBagConstraints15.weightx = 1.0;
		gridBagConstraints15.anchor = GridBagConstraints.WEST;
		gridBagConstraints15.gridx = 2;
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints14.gridy = 3;
//		gridBagConstraints14.weightx = 1.0;
		gridBagConstraints14.anchor = GridBagConstraints.WEST;
		gridBagConstraints14.gridx = 2;
		GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
		gridBagConstraints121.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints121.gridy = 12;
//		gridBagConstraints121.weightx = 1.0;
		gridBagConstraints121.anchor = GridBagConstraints.WEST;
		gridBagConstraints121.gridx = 1;
		GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
		gridBagConstraints111.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints111.gridy = 11;
//		gridBagConstraints111.weightx = 1.0;
		gridBagConstraints111.anchor = GridBagConstraints.WEST;
		gridBagConstraints111.gridx = 1;
		GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
		gridBagConstraints101.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints101.gridy = 10;
//		gridBagConstraints101.weightx = 1.0;
		gridBagConstraints101.anchor = GridBagConstraints.WEST;
		gridBagConstraints101.gridx = 1;
		GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
		gridBagConstraints91.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints91.gridy = 9;
//		gridBagConstraints91.weightx = 1.0;
		gridBagConstraints91.anchor = GridBagConstraints.WEST;
		gridBagConstraints91.gridx = 1;
		GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
		gridBagConstraints81.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints81.gridy = 8;
//		gridBagConstraints81.weightx = 1.0;
		gridBagConstraints81.anchor = GridBagConstraints.WEST;
		gridBagConstraints81.gridx = 1;
		GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
		gridBagConstraints71.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints71.gridy = 7;
//		gridBagConstraints71.weightx = 1.0;
		gridBagConstraints71.anchor = GridBagConstraints.WEST;
		gridBagConstraints71.gridx = 1;
		GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
		gridBagConstraints61.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints61.gridy = 6;
//		gridBagConstraints61.weightx = 1.0;
		gridBagConstraints61.anchor = GridBagConstraints.WEST;
		gridBagConstraints61.gridx = 1;
		GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
		gridBagConstraints51.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints51.gridy = 5;
//		gridBagConstraints51.weightx = 1.0;
		gridBagConstraints51.anchor = GridBagConstraints.WEST;
		gridBagConstraints51.gridx = 1;
		GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
		gridBagConstraints41.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints41.gridy = 4;
//		gridBagConstraints41.weightx = 1.0;
		gridBagConstraints41.anchor = GridBagConstraints.WEST;
		gridBagConstraints41.gridx = 1;
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints31.gridy = 3;
//		gridBagConstraints31.weightx = 1.0;
		gridBagConstraints31.anchor = GridBagConstraints.WEST;
		gridBagConstraints31.gridx = 1;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridy = 1;
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.gridx = 1;
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridy = 0;
		gridBagConstraints13.gridwidth = 8;
		gridBagConstraints13.anchor = GridBagConstraints.WEST;
		gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints13.gridx = 1;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.anchor = GridBagConstraints.EAST;
		gridBagConstraints12.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints12.gridy = 13;
		jLblTelefoon = new JLabel();
		jLblTelefoon.setText("Telefoon");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.EAST;
		gridBagConstraints11.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints11.gridy = 12;
		jLblKinderen = new JLabel();
		jLblKinderen.setText("Kinderen");
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.anchor = GridBagConstraints.EAST;
		gridBagConstraints10.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints10.gridy = 11;
		jLblVolwassen = new JLabel();
		jLblVolwassen.setText("Volwassen");
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.anchor = GridBagConstraints.EAST;
		gridBagConstraints9.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints9.gridy = 10;
		jLblTent = new JLabel();
		jLblTent.setText("Tent");
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.anchor = GridBagConstraints.EAST;
		gridBagConstraints8.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints8.gridy = 9;
		jLblCar = new JLabel();
		jLblCar.setText("Car");
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.anchor = GridBagConstraints.EAST;
		gridBagConstraints7.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints7.gridy = 8;
		jLblZakBlauw = new JLabel();
		jLblZakBlauw.setText("Zak Blauw");
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.anchor = GridBagConstraints.EAST;
		gridBagConstraints6.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints6.gridy = 7;
		jLblZakGeel = new JLabel();
		jLblZakGeel.setText("Zak Geel");
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.anchor = GridBagConstraints.EAST;
		gridBagConstraints5.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints5.gridy = 6;
		jLblJeton = new JLabel();
		jLblJeton.setText("Jeton");
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.anchor = GridBagConstraints.EAST;
		gridBagConstraints4.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints4.gridy = 5;
		jLblWaarborg = new JLabel();
		jLblWaarborg.setText("Waarborg");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.anchor = GridBagConstraints.EAST;
		gridBagConstraints3.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints3.gridy = 4;
		JLblAuto150 = new JLabel();
		JLblAuto150.setText("Auto 150");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.anchor = GridBagConstraints.EAST;
		gridBagConstraints2.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints2.gridy = 3;
		jLblAuto100 = new JLabel();
		jLblAuto100.setText("Auto 100");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.anchor = GridBagConstraints.EAST;
		gridBagConstraints1.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints1.gridy = 1;
		jLblNachten = new JLabel();
		jLblNachten.setText("Aantal nachten");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints.gridy = 0;
		jLabelKasbon = new JLabel();
		jLabelKasbon.setText("Kasbon zoeken");
		
		GridBagConstraints gbcDivider1 = new GridBagConstraints();
		gbcDivider1.gridx = 0;
		gbcDivider1.anchor = GridBagConstraints.WEST;
		gbcDivider1.insets = new Insets(0, 20, 0, 10);
		gbcDivider1.fill = GridBagConstraints.HORIZONTAL;
		gbcDivider1.gridwidth = 6;
		gbcDivider1.gridy = 14;
//		gbcDivider1.gridwidth = 4;
		JPanel divider1 = getDivider(600);
		
		this.setSize(1126, 634);
		this.setLayout(new GridBagLayout());
		this.add(jLabelKasbon, gridBagConstraints);
		this.add(jLblNachten, gridBagConstraints1);
		this.add(jLblAuto100, gridBagConstraints2);
		this.add(JLblAuto150, gridBagConstraints3);
		this.add(jLblWaarborg, gridBagConstraints4);
		this.add(jLblJeton, gridBagConstraints5);
		this.add(jLblZakGeel, gridBagConstraints6);
		this.add(jLblZakBlauw, gridBagConstraints7);
		this.add(jLblCar, gridBagConstraints8);
		this.add(jLblTent, gridBagConstraints9);
		this.add(jLblVolwassen, gridBagConstraints10);
		this.add(jLblKinderen, gridBagConstraints11);
		this.add(jLblTelefoon, gridBagConstraints12);
		this.add(getJCboKasbon(), gridBagConstraints13);
		this.add(getJTxtNachten(), gridBagConstraints21);
		this.add(getJTxtAuto100(), gridBagConstraints31);
		this.add(getJTxtAuto150(), gridBagConstraints41);
		this.add(getJTxtWaarborg(), gridBagConstraints51);
		this.add(getJTxtJeton(), gridBagConstraints61);
		this.add(getJTxtZakGeel(), gridBagConstraints71);
		this.add(getJTxtZakBlauw(), gridBagConstraints81);
		this.add(getJtxtCar(), gridBagConstraints91);
		this.add(getJTxtTent(), gridBagConstraints101);
		this.add(getJTxtVolwassen(), gridBagConstraints111);
		this.add(getJTxtKinderen(), gridBagConstraints121);
		this.add(getJTxtKostAuto100(), gridBagConstraints14);
		this.add(getJTxtKostAuto150(), gridBagConstraints15);
		this.add(getJTxtKostWaarborg(), gridBagConstraints16);
		this.add(getJTxtKostJeton(), gridBagConstraints17);
		this.add(getJTxtKostZakGeel(), gridBagConstraints20);
		this.add(getJTxtKostZakBlauw(), gridBagConstraints211);
		this.add(getJTxtKostCar(), gridBagConstraints22);
		this.add(getJTxtKostTent(), gridBagConstraints23);
		this.add(getJTxtKostVolwassen(), gridBagConstraints24);
		this.add(getJTxtKostKinderen(), gridBagConstraints25);
		this.add(getJTxtExtraKosten(), gridBagConstraints26);
		this.add(getJTxtPrijsAuto100(), gridBagConstraints27);
		this.add(getJTxtPrijsAuto150(), gridBagConstraints28);
		this.add(getJTxtPrijsWaarborg(), gridBagConstraints29);
		this.add(getJTxtPrijsJeton(), gridBagConstraints30);
		this.add(getJTxtPrijsZakGeel(), gridBagConstraints311);
		this.add(getJTxtPrijsZakBlauw(), gridBagConstraints32);
		this.add(getJTxtPrijsCar(), gridBagConstraints33);
		this.add(getJTxtPrijsTent(), gridBagConstraints34);
		this.add(getJTxtPrijsVolwassen(), gridBagConstraints35);
		this.add(getJTxtPrijsKinderen(), gridBagConstraints36);
		this.add(getJBtnStandaardWaarden(), gridBagConstraints37);
		this.add(jLblDatum, gridBagConstraints38);
		this.add(getJTxtDatum(), gridBagConstraints39);
		this.add(jLblKasbonNr, gridBagConstraints40);
		this.add(jLblNaam, gridBagConstraints411);
		this.add(getJTxtNaam(), gridBagConstraints42);
		this.add(jLblStandplaats, gridBagConstraints43);
		this.add(getJTxtStandplaats(), gridBagConstraints44);
		this.add(jLblAutoplaat, gridBagConstraints45);
		this.add(getJTxtAutoplaat(), gridBagConstraints46);
		this.add(jLblBadge, gridBagConstraints47);
		this.add(getJTxtBadge(), gridBagConstraints48);
		this.add(divider1, gbcDivider1);
		this.add(jLblTotaal, gridBagConstraints511);
		this.add(jLblBetaling, gridBagConstraints52);
		this.add(jLblTerug, gridBagConstraints53);
		this.add(getJTxtTotaal(), gridBagConstraints55);
		this.add(getJTxtBetaling(), gridBagConstraints56);
		this.add(getJTxtTerug(), gridBagConstraints57);
		this.add(jLblKostNacht, gridBagConstraints58);
		this.add(jLblAantalNachten, gridBagConstraints59);
		this.add(jLblTotaalNachten, gridBagConstraints60);
		this.add(getJTxtKostNacht(), gridBagConstraints611);
		this.add(getJTxtAantalNachten(), gridBagConstraints62);
		this.add(getJTxtTotaalNachten(), gridBagConstraints63);
		this.add(JLblJetonsZakKost, gridBagConstraints64);
		this.add(jLblTotZonderWB, gridBagConstraints65);
		this.add(getJTxtJetonsZakKost(), gridBagConstraints66);
		this.add(getJTxtTotZonderWB(), gridBagConstraints67);
		this.add(getJTxtDatVan(), gridBagConstraints69);
		this.add(getJTxtDatTot(), gridBagConstraints70);
		this.add(getJBtnPrint(), gridBagConstraints711);
		this.add(getJBtnVerwijder(), gridBagConstraints72);
		this.add(jLblNieuw, gridBagConstraints73);
		this.add(getJbtnFiche(), gridBagConstraints74);
		this.add(getJBtnReset(), gridBagConstraints75);
	}

	/**
	 * This method initializes jCboKasbon	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCboKasbon() {
		if (jCboKasbon == null) {
			jCboKasbon = new JComboBox();
		}
		return jCboKasbon;
	}

	/**
	 * This method initializes jTxtNachten	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtNachten() {
		if (jTxtNachten == null) {
			jTxtNachten = new JTextField();
			jTxtNachten.setPreferredSize(aantalDim);
		}
		return jTxtNachten;
	}

	/**
	 * This method initializes jTxtAuto100	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtAuto100() {
		if (jTxtAuto100 == null) {
			jTxtAuto100 = new JTextField();
			jTxtAuto100.setPreferredSize(aantalDim);
		}
		return jTxtAuto100;
	}

	/**
	 * This method initializes jTxtAuto150	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtAuto150() {
		if (jTxtAuto150 == null) {
			jTxtAuto150 = new JTextField();
			jTxtAuto150.setPreferredSize(aantalDim);
		}
		return jTxtAuto150;
	}

	/**
	 * This method initializes jTxtWaarborg	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtWaarborg() {
		if (jTxtWaarborg == null) {
			jTxtWaarborg = new JTextField();
			jTxtWaarborg.setPreferredSize(aantalDim);
		}
		return jTxtWaarborg;
	}

	/**
	 * This method initializes jTxtJeton	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtJeton() {
		if (jTxtJeton == null) {
			jTxtJeton = new JTextField();
			jTxtJeton.setPreferredSize(aantalDim);
		}
		return jTxtJeton;
	}

	/**
	 * This method initializes jTxtZakGeel	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtZakGeel() {
		if (jTxtZakGeel == null) {
			jTxtZakGeel = new JTextField();
			jTxtZakGeel.setPreferredSize(aantalDim);
		}
		return jTxtZakGeel;
	}

	/**
	 * This method initializes jTxtZakBlauw	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtZakBlauw() {
		if (jTxtZakBlauw == null) {
			jTxtZakBlauw = new JTextField();
			jTxtZakBlauw.setPreferredSize(aantalDim);
		}
		return jTxtZakBlauw;
	}

	/**
	 * This method initializes jtxtCar	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtCar() {
		if (jTxtCar == null) {
			jTxtCar = new JTextField();
			jTxtCar.setPreferredSize(aantalDim);
		}
		return jTxtCar;
	}

	/**
	 * This method initializes jTxtTent	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtTent() {
		if (jTxtTent == null) {
			jTxtTent = new JTextField();
			jTxtTent.setPreferredSize(aantalDim);
		}
		return jTxtTent;
	}

	/**
	 * This method initializes jTxtVolwassen	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtVolwassen() {
		if (jTxtVolwassen == null) {
			jTxtVolwassen = new JTextField();
			jTxtVolwassen.setPreferredSize(aantalDim);
		}
		return jTxtVolwassen;
	}

	/**
	 * This method initializes jTxtKinderen	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKinderen() {
		if (jTxtKinderen == null) {
			jTxtKinderen = new JTextField();
			jTxtKinderen.setPreferredSize(aantalDim);
		}
		return jTxtKinderen;
	}

	/**
	 * This method initializes jTxtKostAuto100	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostAuto100() {
		if (jTxtKostAuto100 == null) {
			jTxtKostAuto100 = new JTextField();
			jTxtKostAuto100.setPreferredSize(kostenDim);
		}
		return jTxtKostAuto100;
	}

	/**
	 * This method initializes jTxtKostAuto150	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostAuto150() {
		if (jTxtKostAuto150 == null) {
			jTxtKostAuto150 = new JTextField();
			jTxtKostAuto150.setPreferredSize(kostenDim);
		}
		return jTxtKostAuto150;
	}

	/**
	 * This method initializes jTxtKostWaarborg	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostWaarborg() {
		if (jTxtKostWaarborg == null) {
			jTxtKostWaarborg = new JTextField();
			jTxtKostWaarborg.setPreferredSize(kostenDim);
		}
		return jTxtKostWaarborg;
	}

	/**
	 * This method initializes jTxtKostJeton	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostJeton() {
		if (jTxtKostJeton == null) {
			jTxtKostJeton = new JTextField();
			jTxtKostJeton.setPreferredSize(kostenDim);
		}
		return jTxtKostJeton;
	}

	/**
	 * This method initializes jTxtKostZakGeel	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostZakGeel() {
		if (jTxtKostZakGeel == null) {
			jTxtKostZakGeel = new JTextField();
			jTxtKostZakGeel.setPreferredSize(kostenDim);
		}
		return jTxtKostZakGeel;
	}

	/**
	 * This method initializes jTxtKostZakBlauw	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostZakBlauw() {
		if (jTxtKostZakBlauw == null) {
			jTxtKostZakBlauw = new JTextField();
			jTxtKostZakBlauw.setPreferredSize(kostenDim);
		}
		return jTxtKostZakBlauw;
	}

	/**
	 * This method initializes jTxtKostCar	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostCar() {
		if (jTxtKostCar == null) {
			jTxtKostCar = new JTextField();
			jTxtKostCar.setPreferredSize(kostenDim);
		}
		return jTxtKostCar;
	}

	/**
	 * This method initializes jTxtKostTent	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostTent() {
		if (jTxtKostTent == null) {
			jTxtKostTent = new JTextField();
			jTxtKostTent.setPreferredSize(kostenDim);
		}
		return jTxtKostTent;
	}

	/**
	 * This method initializes jTxtKostVolwassen	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostVolwassen() {
		if (jTxtKostVolwassen == null) {
			jTxtKostVolwassen = new JTextField();
			jTxtKostVolwassen.setPreferredSize(kostenDim);
		}
		return jTxtKostVolwassen;
	}

	/**
	 * This method initializes jTxtKostKinderen	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostKinderen() {
		if (jTxtKostKinderen == null) {
			jTxtKostKinderen = new JTextField();
			jTxtKostKinderen.setPreferredSize(kostenDim);
		}
		return jTxtKostKinderen;
	}

	/**
	 * This method initializes jTxtExtraKosten	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtExtraKosten() {
		if (jTxtExtraKosten == null) {
			jTxtExtraKosten = new JTextField();
			jTxtExtraKosten.setPreferredSize(kostenDim);
		}
		return jTxtExtraKosten;
	}

	/**
	 * This method initializes jTxtPrijsAuto100	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsAuto100() {
		if (jTxtPrijsAuto100 == null) {
			jTxtPrijsAuto100 = new JTextField();
			jTxtPrijsAuto100.setPreferredSize(prijsDim);
		}
		return jTxtPrijsAuto100;
	}

	/**
	 * This method initializes jTxtPrijsAuto150	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsAuto150() {
		if (jTxtPrijsAuto150 == null) {
			jTxtPrijsAuto150 = new JTextField();
			jTxtPrijsAuto150.setPreferredSize(prijsDim);
		}
		return jTxtPrijsAuto150;
	}

	/**
	 * This method initializes jTxtPrijsWaarborg	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsWaarborg() {
		if (jTxtPrijsWaarborg == null) {
			jTxtPrijsWaarborg = new JTextField();
			jTxtPrijsWaarborg.setPreferredSize(prijsDim);
		}
		return jTxtPrijsWaarborg;
	}

	/**
	 * This method initializes jTxtPrijsJeton	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsJeton() {
		if (jTxtPrijsJeton == null) {
			jTxtPrijsJeton = new JTextField();
			jTxtPrijsJeton.setPreferredSize(prijsDim);
		}
		return jTxtPrijsJeton;
	}

	/**
	 * This method initializes jTxtPrijsZakGeel	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsZakGeel() {
		if (jTxtPrijsZakGeel == null) {
			jTxtPrijsZakGeel = new JTextField();
			jTxtPrijsZakGeel.setPreferredSize(prijsDim);
		}
		return jTxtPrijsZakGeel;
	}

	/**
	 * This method initializes jTxtPrijsZakBlauw	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsZakBlauw() {
		if (jTxtPrijsZakBlauw == null) {
			jTxtPrijsZakBlauw = new JTextField();
			jTxtPrijsZakBlauw.setPreferredSize(prijsDim);
		}
		return jTxtPrijsZakBlauw;
	}

	/**
	 * This method initializes jTxtPrijsCar	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsCar() {
		if (jTxtPrijsCar == null) {
			jTxtPrijsCar = new JTextField();
			jTxtPrijsCar.setPreferredSize(prijsDim);
		}
		return jTxtPrijsCar;
	}

	/**
	 * This method initializes jTxtPrijsTent	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsTent() {
		if (jTxtPrijsTent == null) {
			jTxtPrijsTent = new JTextField();
			jTxtPrijsTent.setPreferredSize(prijsDim);
		}
		return jTxtPrijsTent;
	}

	/**
	 * This method initializes jTxtPrijsVolwassen	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsVolwassen() {
		if (jTxtPrijsVolwassen == null) {
			jTxtPrijsVolwassen = new JTextField();
			jTxtPrijsVolwassen.setPreferredSize(prijsDim);
		}
		return jTxtPrijsVolwassen;
	}

	/**
	 * This method initializes jTxtPrijsKinderen	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtPrijsKinderen() {
		if (jTxtPrijsKinderen == null) {
			jTxtPrijsKinderen = new JTextField();
			jTxtPrijsKinderen.setPreferredSize(prijsDim);
		}
		return jTxtPrijsKinderen;
	}

	/**
	 * This method initializes jBtnStandaardWaarden	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnStandaardWaarden() {
		if (jBtnStandaardWaarden == null) {
			jBtnStandaardWaarden = new JButton();
			jBtnStandaardWaarden.setText("Standaard waarden");
		}
		return jBtnStandaardWaarden;
	}

	/**
	 * This method initializes jTxtDatum	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtDatum() {
		if (jTxtDatum == null) {
			jTxtDatum = new JTextField();
			jTxtDatum.setPreferredSize(new Dimension(120, 36));
		}
		return jTxtDatum;
	}

	/**
	 * This method initializes JTxtNaam	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtNaam() {
		if (jTxtNaam == null) {
			jTxtNaam = new JTextField();
			jTxtNaam.setPreferredSize(new Dimension(300, 36));
		}
		return jTxtNaam;
	}

	/**
	 * This method initializes jTxtStandplaats	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtStandplaats() {
		if (jTxtStandplaats == null) {
			jTxtStandplaats = new JTextField();
			jTxtStandplaats.setPreferredSize(new Dimension(300, 36));
		}
		return jTxtStandplaats;
	}

	/**
	 * This method initializes jTxtAutoplaat	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtAutoplaat() {
		if (jTxtAutoplaat == null) {
			jTxtAutoplaat = new JTextField();
			jTxtAutoplaat.setPreferredSize(new Dimension(300, 36));
		}
		return jTxtAutoplaat;
	}

	/**
	 * This method initializes jTxtBadge	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtBadge() {
		if (jTxtBadge == null) {
			jTxtBadge = new JTextField();
			jTxtBadge.setPreferredSize(new Dimension(300, 36));
		}
		return jTxtBadge;
	}

	private JPanel getDivider(int width) {
		int widthDiv = width;
		if (width == 0) {
			widthDiv = this.getWidth();
		}
		JPanel divider = new JPanel();
		divider.setBackground(new Color(SystemColor.WINDOW_BORDER));
		divider.setPreferredSize(new Dimension(widthDiv, 1));
		return divider;
	}

	/**
	 * This method initializes jTxtTotaal	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtTotaal() {
		if (jTxtTotaal == null) {
			jTxtTotaal = new JTextField();
			int width = (int)(aantalDim.getWidth() + kostenDim.getWidth());
			jTxtTotaal.setPreferredSize(new Dimension(width, (int)aantalDim.getHeight()*2));
		}
		return jTxtTotaal;
	}

	/**
	 * This method initializes jTxtBetaling	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtBetaling() {
		if (jTxtBetaling == null) {
			jTxtBetaling = new JTextField();
			int width = (int)(aantalDim.getWidth() + kostenDim.getWidth());
			jTxtBetaling.setPreferredSize(new Dimension(width, (int)aantalDim.getHeight()));
		}
		return jTxtBetaling;
	}

	/**
	 * This method initializes jTxtTerug	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtTerug() {
		if (jTxtTerug == null) {
			jTxtTerug = new JTextField();
			int width = (int)(aantalDim.getWidth() + kostenDim.getWidth());
			jTxtTerug.setPreferredSize(new Dimension(width, (int)aantalDim.getHeight()));
		}
		return jTxtTerug;
	}

	/**
	 * This method initializes jTxtKostNacht	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtKostNacht() {
		if (jTxtKostNacht == null) {
			jTxtKostNacht = new JTextField();
			jTxtKostNacht.setPreferredSize(prijsDim);
		}
		return jTxtKostNacht;
	}

	/**
	 * This method initializes jTxtAantalNachten	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtAantalNachten() {
		if (jTxtAantalNachten == null) {
			jTxtAantalNachten = new JTextField();
			jTxtAantalNachten.setPreferredSize(kostenDim);
		}
		return jTxtAantalNachten;
	}

	/**
	 * This method initializes jTxtTotaalNachten	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtTotaalNachten() {
		if (jTxtTotaalNachten == null) {
			jTxtTotaalNachten = new JTextField();
			jTxtTotaalNachten.setPreferredSize(kostenDim);
		}
		return jTxtTotaalNachten;
	}

	/**
	 * This method initializes jTxtJetonsZakKost	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtJetonsZakKost() {
		if (jTxtJetonsZakKost == null) {
			jTxtJetonsZakKost = new JTextField();
			jTxtJetonsZakKost.setPreferredSize(kostenDim);
		}
		return jTxtJetonsZakKost;
	}

	/**
	 * This method initializes jTxtTotZonderWB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtTotZonderWB() {
		if (jTxtTotZonderWB == null) {
			jTxtTotZonderWB = new JTextField();
			jTxtTotZonderWB.setPreferredSize(kostenDim);
		}
		return jTxtTotZonderWB;
	}

	/**
	 * This method initializes jTxtDatVan	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtDatVan() {
		if (jTxtDatVan == null) {
			jTxtDatVan = new JTextField();
			jTxtDatVan.setPreferredSize(new Dimension(120, 36));
		}
		return jTxtDatVan;
	}

	/**
	 * This method initializes jTxtDatTot	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTxtDatTot() {
		if (jTxtDatTot == null) {
			jTxtDatTot = new JTextField();
			jTxtDatTot.setPreferredSize(new Dimension(120, 36));
		}
		return jTxtDatTot;
	}

	/**
	 * This method initializes jBtnPrint	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnPrint() {
		if (jBtnPrint == null) {
			jBtnPrint = new JButton();
			jBtnPrint.setText("Afdrukken");
		}
		return jBtnPrint;
	}

	/**
	 * This method initializes jBtnVerwijder	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnVerwijder() {
		if (jBtnVerwijder == null) {
			jBtnVerwijder = new JButton();
			jBtnVerwijder.setText("Verwijder");
		}
		return jBtnVerwijder;
	}

	/**
	 * This method initializes jbtnFiche	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnFiche() {
		if (jbtnFiche == null) {
			jbtnFiche = new JButton();
			jbtnFiche.setText("Met gegevens fiche");
		}
		return jbtnFiche;
	}

	/**
	 * This method initializes jBtnReset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnReset() {
		if (jBtnReset == null) {
			jBtnReset = new JButton();
			jBtnReset.setText("Blanco");
		}
		return jBtnReset;
	}
}
