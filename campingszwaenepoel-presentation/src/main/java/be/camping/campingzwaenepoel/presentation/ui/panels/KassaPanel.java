package be.camping.campingzwaenepoel.presentation.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.StringUtils;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.CashSourceEnum;
import be.camping.campingzwaenepoel.common.enums.GezinsPositieEnum;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.common.utils.ComputerUtils;
import be.camping.campingzwaenepoel.common.utils.DateUtilities;
import be.camping.campingzwaenepoel.common.utils.NumberUtilities;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.pdf.KasbonPdf;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.KasbonService;
import be.camping.campingzwaenepoel.service.KassaKostprijsService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingDTO;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingPersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.KasbonDTO;
import be.camping.campingzwaenepoel.service.transfer.KassaKostprijsDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.WagenDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class KassaPanel extends JPanel implements PanelInterface, ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;

	private List<KassaKostprijsDTO> kostprijzen; // @jve:decl-index=0:

	private final Dimension aantalDim = new Dimension(55, 30);
	private final Dimension kostenDim = new Dimension(130, 30);
	private final Dimension prijsDim = new Dimension(110, 30);

	private JLabel jLabelKasbon = null;
	private JLabel jLblNachten = null;
	private JLabel jLblAuto100 = null;
	private JLabel jLblAuto150 = null;
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
	private JDatePicker jDpDatum = null; // @jve:decl-index=0:
	private JDatePicker jDpDatumVan = null;
	private JDatePicker jDpDatumTot = null;
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
	private JButton jBtnPrintCash = null;
	private JButton jBtnPrintPayconic = null;
	private JButton jBtnVerwijder = null;
	private JLabel jLblNieuw = null;

	private JButton jbtnFiche = null;
	private JButton jBtnReset = null;
	private JButton jBtnNieuweWaarden = null;
	private JPanel jPanelKosten = null;
	private JButton jBtnOpslaan = null;

	private final int widthDivider = 325;
	private final Insets insetsDivider = new Insets(0, 20, 0, 10); // @jve:decl-index=0:

	private JPanel jPanelKasbon = null;
	private JPanel jPanelKassa = null;
	private JPanel jPanelTotaal = null;

	private final Color colorYellow = Color.YELLOW;
	private final Color colorBlue = Color.MAGENTA;
	private final Color colorGray = Color.LIGHT_GRAY;

	private JPanel jPanelKostprijzen;
	private JTextField jNaam1;
	private JTextField jNaam2;
	private JTextField jNaam3;
	private JTextField jNaam4;
	private JTextField jNaam5;
	private JTextField jNaam6;
	private JTextField jNaam7;
	private JTextField jNaam8;
	private JTextField jNaam9;
	private JTextField jNaam10;
	private JTextField jWaarde1;
	private JTextField jWaarde2;
	private JTextField jWaarde3;
	private JTextField jWaarde4;
	private JTextField jWaarde5;
	private JTextField jWaarde6;
	private JTextField jWaarde7;
	private JTextField jWaarde8;
	private JTextField jWaarde9;
	private JTextField jWaarde10;

	private final Dimension txtFieldDim = new Dimension(115, 30); // @jve:decl-index=0:

	private final DecimalFormat df = new DecimalFormat("0.00"); // @jve:decl-index=0:
	private final Font font = new Font("Times New Roman", Font.BOLD, 20); // @jve:decl-index=0:
	private final Font font2 = new Font("Times New Roman", Font.BOLD, 14);
	private final Font fontEyes = new Font("Times New Roman", Font.BOLD, 24);

	private Border defaultBorder = null;

	private KasbonDTO kasbon; // @jve:decl-index=0:
	private List<KasbonDTO> kasbons; // @jve:decl-index=0:
	private int index = 1;

	public KasbonDTO getKasbon() {
		if (kasbon == null) {
			kasbon = DTOFactory.createKasbon();
		}
		return kasbon;
	}

	public void setKasbon(KasbonDTO kasbon) {
		this.kasbon = kasbon;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Autowired
	private KassaKostprijsService kassaKostprijsService; // @jve:decl-index=0:

    @Autowired
	private KasbonService kasbonService; // @jve:decl-index=0:

	@Autowired
	private StandplaatsService standplaatsService; // @jve:decl-index=0:

	private JButton jBtnOpslaanKasbon = null;

	public KassaKostprijsService getKassaKostprijsService() {
		return kassaKostprijsService;
	}

	public KasbonService getKasbonService() {
		return kasbonService;
	}

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}

	public void setStandplaatsService(StandplaatsService standplaatsService) {
		this.standplaatsService = standplaatsService;
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
		getKasbon().setNaam(getJTxtNaam().getText());
		getKasbon().setStandplaats(getJTxtStandplaats().getText());
		getKasbon().setNummerplaat(getJTxtAutoplaat().getText());
		getKasbon().setBadge(getJTxtBadge().getText());
		getKasbon().setComputer(ComputerUtils.getComputername());
		if (!StringUtils.isEmpty(getjDpDatum().getJFormattedTextField().getText())) {
			Calendar cal = Calendar.getInstance();
			cal.set(getjDpDatum().getModel().getYear(), getjDpDatum().getModel().getMonth(), getjDpDatum().getModel()
					.getDay());
			getKasbon().setKasbonDatum(cal.getTime());
		}
		if (!StringUtils.isEmpty(getjDpDatumTot().getJFormattedTextField().getText())) {
			Calendar cal = Calendar.getInstance();
			cal.set(getjDpDatumTot().getModel().getYear(), getjDpDatumTot().getModel().getMonth(), getjDpDatumTot()
					.getModel().getDay());
			getKasbon().setDatumTot(cal.getTime());
		}
		if (!StringUtils.isEmpty(getjDpDatumVan().getJFormattedTextField().getText())) {
			Calendar cal = Calendar.getInstance();
			cal.set(getjDpDatumVan().getModel().getYear(), getjDpDatumVan().getModel().getMonth(), getjDpDatumVan()
					.getModel().getDay());
			getKasbon().setDatumVan(cal.getTime());
		}
		return getKasbon();
	}

	@Override
	public void save() {
		if ("\u20ac 0,00".equals(getJTxtTotaal().getText()))
			return;
		getDataFromGUI();
		boolean insert = true;
		if (getKasbon().getId() != null && getKasbon().getId() > 0) {
			insert = false;
		}
		if (insert)
			getKasbon().setKasbonnummer(index);
		KasbonDTO tmpKasbon = getKasbon();
		tmpKasbon = getKasbonService().store(tmpKasbon);
		if (insert) {
			getKasbon().setId(tmpKasbon.getId());
			getKasbons().add(tmpKasbon);
		}
		String tijd = "";
		String van = "";
		String tot = "";
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tijd = formatter.format(tmpKasbon.getKasbonDatum());
		} catch (Exception e) {
			System.err.println("kasbondatum not correctly parsed");
		}
		try {
			van = formatter.format(tmpKasbon.getDatumVan());
		} catch (Exception e) {
			System.err.println("datumVan not correctly parsed");
		}
		try {
			tot = formatter.format(tmpKasbon.getDatumTot());
		} catch (Exception e) {
			System.err.println("datumTot not correctly parsed");
		}
		String naam = tmpKasbon.getNaam();
		String plaats = tmpKasbon.getStandplaats();
		Double dTotaal = berekendTotalen(false, tmpKasbon);
		String totaal = Double.toString(dTotaal);
		totaal = totaal.replace(".", ",");
		if (insert) {
			getJCboKasbon().addItem(new Row(Integer.toString(index), tijd, naam, plaats, van, tot, totaal));
			index++;
		} else {
			for (int i = 1; i < getJCboKasbon().getModel().getSize(); i++) {
				Row row = (Row) getJCboKasbon().getItemAt(i);
				if (tmpKasbon.getKasbonnummer() == Integer.parseInt(row.getId())) {
					row.setNaam(naam);
					row.setPlaats(plaats);
					row.setVan(van);
					row.setTot(tot);
					row.setTijd(tijd);
					row.setTotaal(totaal);
					break;
				}
			}
		}
	}

	@Override
	public void setDataInGUI() {
		VulKostprijzenIn();
		getJTxtNachten().requestFocus();
	}

	private void createKostprijzenPanel() {
		getjPanelKostprijzen().setLayout(new GridLayout(10, 2));
		getjPanelKostprijzen().add(getjNaam1());
		getjPanelKostprijzen().add(getjWaarde1());
		getjPanelKostprijzen().add(getjNaam2());
		getjPanelKostprijzen().add(getjWaarde2());
		getjPanelKostprijzen().add(getjNaam3());
		getjPanelKostprijzen().add(getjWaarde3());
		getjPanelKostprijzen().add(getjNaam4());
		getjPanelKostprijzen().add(getjWaarde4());
		getjPanelKostprijzen().add(getjNaam5());
		getjPanelKostprijzen().add(getjWaarde5());
		getjPanelKostprijzen().add(getjNaam6());
		getjPanelKostprijzen().add(getjWaarde6());
		getjPanelKostprijzen().add(getjNaam7());
		getjPanelKostprijzen().add(getjWaarde7());
		getjPanelKostprijzen().add(getjNaam8());
		getjPanelKostprijzen().add(getjWaarde8());
		getjPanelKostprijzen().add(getjNaam9());
		getjPanelKostprijzen().add(getjWaarde9());
		getjPanelKostprijzen().add(getjNaam10());
		getjPanelKostprijzen().add(getjWaarde10());
	}

	private void VulKostprijzenIn() {
		getjNaam1().setText(getKostprijzen().get(0).getNaam());
		getjNaam2().setText(getKostprijzen().get(1).getNaam());
		getjNaam3().setText(getKostprijzen().get(2).getNaam());
		getjNaam4().setText(getKostprijzen().get(3).getNaam());
		getjNaam5().setText(getKostprijzen().get(4).getNaam());
		getjNaam6().setText(getKostprijzen().get(5).getNaam());
		getjNaam7().setText(getKostprijzen().get(6).getNaam());
		getjNaam8().setText(getKostprijzen().get(7).getNaam());
		getjNaam9().setText(getKostprijzen().get(8).getNaam());
		getjNaam10().setText(getKostprijzen().get(9).getNaam());
		getjWaarde1().setText(df.format(getKostprijzen().get(0).getWaarde()));
		getjWaarde2().setText(df.format(getKostprijzen().get(1).getWaarde()));
		getjWaarde3().setText(df.format(getKostprijzen().get(2).getWaarde()));
		getjWaarde4().setText(df.format(getKostprijzen().get(3).getWaarde()));
		getjWaarde5().setText(df.format(getKostprijzen().get(4).getWaarde()));
		getjWaarde6().setText(df.format(getKostprijzen().get(5).getWaarde()));
		getjWaarde7().setText(df.format(getKostprijzen().get(6).getWaarde()));
		getjWaarde8().setText(df.format(getKostprijzen().get(7).getWaarde()));
		getjWaarde9().setText(df.format(getKostprijzen().get(8).getWaarde()));
		getjWaarde10().setText(df.format(getKostprijzen().get(9).getWaarde()));

		jLblAuto100.setText(getKostprijzen().get(0).getNaam());
		jLblAuto150.setText(getKostprijzen().get(1).getNaam());
		jLblWaarborg.setText(getKostprijzen().get(2).getNaam());
		jLblJeton.setText(getKostprijzen().get(3).getNaam());
		jLblZakGeel.setText(getKostprijzen().get(4).getNaam());
		jLblZakBlauw.setText(getKostprijzen().get(5).getNaam());
		jLblCar.setText(getKostprijzen().get(6).getNaam());
		jLblTent.setText(getKostprijzen().get(7).getNaam());
		jLblVolwassen.setText(getKostprijzen().get(8).getNaam());
		jLblKinderen.setText(getKostprijzen().get(9).getNaam());
		getJTxtPrijsAuto100().setText("\u20ac " + df.format(getKostprijzen().get(0).getWaarde()));
		getJTxtPrijsAuto150().setText("\u20ac " + df.format(getKostprijzen().get(1).getWaarde()));
		getJTxtPrijsWaarborg().setText("\u20ac " + df.format(getKostprijzen().get(2).getWaarde()));
		getJTxtPrijsJeton().setText("\u20ac " + df.format(getKostprijzen().get(3).getWaarde()));
		getJTxtPrijsZakGeel().setText("\u20ac " + df.format(getKostprijzen().get(4).getWaarde()));
		getJTxtPrijsZakBlauw().setText("\u20ac " + df.format(getKostprijzen().get(5).getWaarde()));
		getJTxtPrijsCar().setText("\u20ac " + df.format(getKostprijzen().get(6).getWaarde()));
		getJTxtPrijsTent().setText("\u20ac " + df.format(getKostprijzen().get(7).getWaarde()));
		getJTxtPrijsVolwassen().setText("\u20ac " + df.format(getKostprijzen().get(8).getWaarde()));
		getJTxtPrijsKinderen().setText("\u20ac " + df.format(getKostprijzen().get(9).getWaarde()));
	}

	private void vulKasbonIn() {
		getJTxtNachten().setText(NumberUtilities.getStringForInt(getKasbon().getAantalNachten()));
		getJTxtAantalNachten().setText(NumberUtilities.getStringForInt(getKasbon().getAantalNachten()));
		getJTxtAuto100().setText(NumberUtilities.getStringForInt(getKasbon().getAuto100()));
		getJTxtAuto150().setText(NumberUtilities.getStringForInt(getKasbon().getAuto150()));
		getJTxtWaarborg().setText(NumberUtilities.getStringForInt(getKasbon().getWaarborg()));
		getJTxtJeton().setText(NumberUtilities.getStringForInt(getKasbon().getJeton()));
		getJTxtZakGeel().setText(NumberUtilities.getStringForInt(getKasbon().getZakGeel()));
		getJTxtZakBlauw().setText(NumberUtilities.getStringForInt(getKasbon().getZakBlauw()));
		getJtxtCar().setText(NumberUtilities.getStringForInt(getKasbon().getCar()));
		getJTxtTent().setText(NumberUtilities.getStringForInt(getKasbon().getTent()));
		getJTxtVolwassen().setText(NumberUtilities.getStringForInt(getKasbon().getVolwassenen()));
		getJTxtKinderen().setText(NumberUtilities.getStringForInt(getKasbon().getKinderen()));
		if (getKasbon().getTelefoon() != 0) {
			getJTxtExtraKosten().setText(NumberUtilities.getStringForDouble(getKasbon().getTelefoon()));
		} else {
			getJTxtExtraKosten().setText("");
		}
		if (getKasbon().getBetaling() != 0) {
			getJTxtBetaling().setText(NumberUtilities.getStringForDouble(getKasbon().getBetaling()));
		} else {
			getJTxtBetaling().setText("");
		}
		getJTxtTerug().setText(NumberUtilities.getStringForDouble(getKasbon().getTerug()));
		getJTxtNaam().setText(getKasbon().getNaam());
		getJTxtStandplaats().setText(getKasbon().getStandplaats());
		getJTxtAutoplaat().setText(getKasbon().getNummerplaat());
		getJTxtBadge().setText(getKasbon().getBadge());
		if (getKasbon().getKasbonDatum() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getKasbon().getKasbonDatum());
			getjDpDatum().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
		}
		if (getKasbon().getDatumVan() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getKasbon().getDatumVan());
			getjDpDatumVan().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
		}
		if (getKasbon().getDatumTot() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getKasbon().getDatumTot());
			getjDpDatumTot().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
		}
	}

	@Override
	public void setRemarksInGui() {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public KassaPanel() {
		super();
		// initComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints510 = new GridBagConstraints();
		gridBagConstraints510.gridx = 5;
		gridBagConstraints510.gridy = 12;
		GridBagConstraints gridBagConstraints410 = new GridBagConstraints();
		gridBagConstraints410.gridx = 0;
		gridBagConstraints410.gridwidth = 5;
		gridBagConstraints410.gridheight = 4;
		gridBagConstraints410.gridy = 29;
		GridBagConstraints gridBagConstraints310 = new GridBagConstraints();
		gridBagConstraints310.gridx = 6;
		gridBagConstraints310.gridheight = 3;
		gridBagConstraints310.gridy = 10;
		GridBagConstraints gridBagConstraints210 = new GridBagConstraints();
		gridBagConstraints210.gridx = 5;
		gridBagConstraints210.gridwidth = 3;
		gridBagConstraints210.gridheight = 6;
		gridBagConstraints210.gridy = 1;
		GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
		gridBagConstraints92.gridx = 0;
		gridBagConstraints92.gridwidth = 4;
		gridBagConstraints92.gridheight = 17;
		gridBagConstraints92.gridy = 1;
		jLblNieuw = new JLabel();
		jLblNieuw.setText("Nieuwe kasbon");
		jLblNieuw.setFont(font2);
		GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
		gridBagConstraints72.gridx = 5;
		gridBagConstraints72.anchor = GridBagConstraints.WEST;
		gridBagConstraints72.gridy = 10;
		GridBagConstraints gridBagConstraints711 = new GridBagConstraints();
		gridBagConstraints711.gridx = 5;
		gridBagConstraints711.anchor = GridBagConstraints.EAST;
		gridBagConstraints711.gridy = 9;
		GridBagConstraints gridBagConstraints722 = new GridBagConstraints();
		gridBagConstraints722.gridx = 6;
//		gridBagConstraints722.anchor = GridBagConstraints.WEST;
		gridBagConstraints722.gridy = 9;
		jLblTotZonderWB = new JLabel();
		jLblTotZonderWB.setText("Tot. zonder WB");
		jLblTotZonderWB.setFont(font2);
		JLblJetonsZakKost = new JLabel();
		JLblJetonsZakKost.setText("Jetons/Zak/Kost");
		JLblJetonsZakKost.setFont(font2);
		jLblTotaalNachten = new JLabel();
		jLblTotaalNachten.setText("Totaal nachten");
		jLblTotaalNachten.setFont(font2);
		jLblAantalNachten = new JLabel();
		jLblAantalNachten.setText("Aantal nachten = ");
		jLblAantalNachten.setFont(font2);
		jLblKostNacht = new JLabel();
		jLblKostNacht.setText("Kost/nacht x ");
		jLblKostNacht.setFont(font2);
		jLblTerug = new JLabel();
		jLblTerug.setText("Terug");
		jLblTerug.setFont(font2);
		jLblBetaling = new JLabel();
		jLblBetaling.setText("Betaling");
		jLblBetaling.setFont(font2);
		jLblTotaal = new JLabel();
		jLblTotaal.setText("Totaal");
		jLblTotaal.setFont(font2);
		jLblBadge = new JLabel();
		jLblBadge.setText("Badge");
		jLblBadge.setFont(font2);
		jLblAutoplaat = new JLabel();
		jLblAutoplaat.setText("Autoplaat");
		jLblAutoplaat.setFont(font2);
		jLblStandplaats = new JLabel();
		jLblStandplaats.setText("Standplaats");
		jLblStandplaats.setFont(font2);
		jLblNaam = new JLabel();
		jLblNaam.setText("Naam");
		jLblNaam.setFont(font2);
		jLblDatum = new JLabel();
		jLblDatum.setText("Kasbon datum :");
		jLblDatum.setFont(font2);
		setBorder(new LineBorder(Color.BLACK, 2));

		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridy = 0;
		gridBagConstraints13.gridwidth = 8;
		gridBagConstraints13.anchor = GridBagConstraints.WEST;
		gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints13.gridx = 1;
		jLblTelefoon = new JLabel();
		jLblTelefoon.setText("Telefoon");
		jLblTelefoon.setFont(font2);
		jLblKinderen = new JLabel();
		jLblKinderen.setText("Kinderen");
		jLblKinderen.setFont(font2);
		jLblVolwassen = new JLabel();
		jLblVolwassen.setText("Volwassen");
		jLblVolwassen.setFont(font2);
		jLblTent = new JLabel();
		jLblTent.setText("Tent");
		jLblTent.setFont(font2);
		jLblCar = new JLabel();
		jLblCar.setText("Car");
		jLblCar.setFont(font2);
		jLblZakBlauw = new JLabel();
		jLblZakBlauw.setText("Zak Blauw");
		jLblZakBlauw.setFont(font2);
		jLblZakGeel = new JLabel();
		jLblZakGeel.setText("Zak Geel");
		jLblZakGeel.setFont(font2);
		jLblJeton = new JLabel();
		jLblJeton.setText("Jeton");
		jLblJeton.setFont(font2);
		jLblWaarborg = new JLabel();
		jLblWaarborg.setText("Waarborg");
		jLblWaarborg.setFont(font2);
		jLblAuto150 = new JLabel();
		jLblAuto150.setText("Auto 150");
		jLblAuto150.setFont(font2);
		jLblAuto100 = new JLabel();
		jLblAuto100.setText("Auto 100");
		jLblAuto100.setFont(font2);
		jLblNachten = new JLabel();
		jLblNachten.setText("Aantal nachten");
		jLblNachten.setFont(font2);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints.gridy = 0;
		jLabelKasbon = new JLabel();
		jLabelKasbon.setText("Kasbon zoeken");
		// jLabelKasbon.setFont(font);

		this.setSize(1126, 634);
		this.setLayout(new GridBagLayout());
		this.add(jLabelKasbon, gridBagConstraints);
		this.add(getJCboKasbon(), gridBagConstraints13);
		this.add(getJBtnPrintCash(), gridBagConstraints711);
		this.add(getJBtnPrintPayconic(), gridBagConstraints722);
		this.add(getJBtnVerwijder(), gridBagConstraints72);
		this.add(getJPanelTotaal(), gridBagConstraints410);
		this.add(getJPanelKosten(), gridBagConstraints92);
		this.add(getJPanelKasbon(), gridBagConstraints210);
		this.add(getJPanelKassa(), gridBagConstraints310);
		// this.add(getJBtnOpslaanKasbon(), gridBagConstraints510);

		VulKostprijzenIn();
	}

	/**
	 * This method initializes jCboKasbon
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJCboKasbon() {
		if (jCboKasbon == null) {
			jCboKasbon = new JComboBox();
			jCboKasbon.setBackground(colorYellow);
			jCboKasbon.setFont(font);
			jCboKasbon.addItem(new Row("id", "tijd", "naam", "plaats", "van", "tot", "totaal"));
			populateCboKasbon();
			jCboKasbon.setRenderer(new RowCellRenderer());
			// jCboKasbon.addMouseListener(new CboMouseListener());
			jCboKasbon.addPopupMenuListener(new PopupMenuListener() {

				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					setKasbons(null);
					populateCboKasbon();
				}

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					// TODO Auto-generated method stub

				}
			});
			jCboKasbon.addActionListener(this);
		}
		return jCboKasbon;
	}

	public void setJCboKasbon(JComboBox jCboKasbon) {
		this.jCboKasbon = jCboKasbon;
	}

	class CboMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setKasbons(null);
			populateCboKasbon();
		}
	}

	public void resetKasbon() {
		index = 1;
		while (getJCboKasbon().getModel().getSize() > 1) {
			getJCboKasbon().removeItemAt(1);
		}
	}

	private void populateCboKasbon() {
		resetKasbon();
		for (KasbonDTO kasbon : getKasbons()) {
			String id = Integer.toString(kasbon.getKasbonnummer());
			String tijd = (kasbon.getKasbonDatum() == null) ? "" : kasbon.getKasbonDatum().toString();
			String naam = kasbon.getNaam();
			String plaats = kasbon.getStandplaats();
			String van = (kasbon.getDatumVan() == null) ? "" : kasbon.getDatumVan().toString();
			String tot = (kasbon.getDatumTot() == null) ? "" : kasbon.getDatumTot().toString();
			Double dTotaal = berekendTotalen(false, kasbon);
			String totaal = Double.toString(dTotaal);
			totaal = totaal.replace(".", ",");
			getJCboKasbon().addItem(new Row(id, tijd, naam, plaats, van, tot, totaal));
			index = (index < kasbon.getKasbonnummer()) ? kasbon.getKasbonnummer() : index;
		}
		if (getKasbons().size() > 0) {
			index++;
		} else {
			setKasbon(DTOFactory.createKasbon());
		}
	}

	/**
	 * This method initializes jTxtNachten
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTxtNachten() {
		if (jTxtNachten == null) {
			jTxtNachten = new JTextField();
			jTxtNachten.setMinimumSize(aantalDim);
			jTxtNachten.setPreferredSize(aantalDim);
			jTxtNachten.setBackground(colorBlue);
			jTxtNachten.setText("1");
			jTxtNachten.setFont(fontEyes);
			jTxtNachten.setForeground(Color.white);
			jTxtNachten.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtNachten.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtAuto100().requestFocusInWindow();
						getJTxtAuto100().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtBetaling().requestFocusInWindow();
						getJTxtBetaling().selectAll();
					}
				}
			});
			jTxtNachten.addFocusListener(this);
			jTxtNachten.setCaretColor(Color.white);
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
			jTxtAuto100.setMinimumSize(aantalDim);
			jTxtAuto100.setBackground(colorBlue);
			jTxtAuto100.setFont(fontEyes);
			jTxtAuto100.setForeground(Color.white);
			jTxtAuto100.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtAuto100.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtAuto150().requestFocusInWindow();
						getJTxtAuto150().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtNachten().requestFocusInWindow();
						getJTxtNachten().selectAll();
					}
				}
			});
			jTxtAuto100.addFocusListener(this);
			jTxtAuto100.setCaretColor(Color.white);
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
			jTxtAuto150.setMinimumSize(aantalDim);
			jTxtAuto150.setBackground(colorBlue);
			jTxtAuto150.setFont(fontEyes);
			jTxtAuto150.setForeground(Color.white);
			jTxtAuto150.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtAuto150.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtWaarborg().requestFocusInWindow();
						getJTxtWaarborg().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtAuto100().requestFocusInWindow();
						getJTxtAuto100().selectAll();
					}
				}
			});
			jTxtAuto150.addFocusListener(this);
			jTxtAuto150.setCaretColor(Color.white);
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
			jTxtWaarborg.setMinimumSize(aantalDim);
			jTxtWaarborg.setBackground(colorBlue);
			jTxtWaarborg.setFont(fontEyes);
			jTxtWaarborg.setForeground(Color.white);
			jTxtWaarborg.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtWaarborg.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtJeton().requestFocusInWindow();
						getJTxtJeton().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtAuto150().requestFocusInWindow();
						getJTxtAuto150().selectAll();
					}
				}
			});
			jTxtWaarborg.addFocusListener(this);
			jTxtWaarborg.setCaretColor(Color.white);
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
			jTxtJeton.setMinimumSize(aantalDim);
			jTxtJeton.setBackground(colorBlue);
			jTxtJeton.setFont(fontEyes);
			jTxtJeton.setForeground(Color.white);
			jTxtJeton.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtJeton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtZakGeel().requestFocusInWindow();
						getJTxtZakGeel().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtWaarborg().requestFocusInWindow();
						getJTxtWaarborg().selectAll();
					}
				}
			});
			jTxtJeton.addFocusListener(this);
			jTxtJeton.setCaretColor(Color.white);
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
			jTxtZakGeel.setMinimumSize(aantalDim);
			jTxtZakGeel.setBackground(colorBlue);
			jTxtZakGeel.setFont(fontEyes);
			jTxtZakGeel.setForeground(Color.white);
			jTxtZakGeel.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtZakGeel.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtZakBlauw().requestFocusInWindow();
						getJTxtZakBlauw().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtJeton().requestFocusInWindow();
						getJTxtJeton().selectAll();
					}
				}
			});
			jTxtZakGeel.addFocusListener(this);
			jTxtZakGeel.setCaretColor(Color.white);
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
			jTxtZakBlauw.setMinimumSize(aantalDim);
			jTxtZakBlauw.setBackground(colorBlue);
			jTxtZakBlauw.setFont(fontEyes);
			jTxtZakBlauw.setForeground(Color.white);
			jTxtZakBlauw.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtZakBlauw.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJtxtCar().requestFocusInWindow();
						getJtxtCar().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtZakGeel().requestFocusInWindow();
						getJTxtZakGeel().selectAll();
					}
				}
			});
			jTxtZakBlauw.addFocusListener(this);
			jTxtZakBlauw.setCaretColor(Color.white);
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
			jTxtCar.setMinimumSize(aantalDim);
			jTxtCar.setBackground(colorBlue);
			jTxtCar.setFont(fontEyes);
			jTxtCar.setForeground(Color.white);
			jTxtCar.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtCar.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtTent().requestFocusInWindow();
						getJTxtTent().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtZakBlauw().requestFocusInWindow();
						getJTxtZakBlauw().selectAll();
					}
				}
			});
			jTxtCar.addFocusListener(this);
			jTxtCar.setCaretColor(Color.white);
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
			jTxtTent.setMinimumSize(aantalDim);
			jTxtTent.setBackground(colorBlue);
			jTxtTent.setFont(fontEyes);
			jTxtTent.setForeground(Color.white);
			jTxtTent.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtTent.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtVolwassen().requestFocusInWindow();
						getJTxtVolwassen().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJtxtCar().requestFocusInWindow();
						getJtxtCar().selectAll();
					}
				}
			});
			jTxtTent.addFocusListener(this);
			jTxtTent.setCaretColor(Color.white);
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
			jTxtVolwassen.setMinimumSize(aantalDim);
			jTxtVolwassen.setBackground(colorBlue);
			jTxtVolwassen.setFont(fontEyes);
			jTxtVolwassen.setForeground(Color.white);
			jTxtVolwassen.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtVolwassen.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtKinderen().requestFocusInWindow();
						getJTxtKinderen().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtTent().requestFocusInWindow();
						getJTxtTent().selectAll();
					}
				}
			});
			jTxtVolwassen.addFocusListener(this);
			jTxtVolwassen.setCaretColor(Color.white);
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
			jTxtKinderen.setMinimumSize(aantalDim);
			jTxtKinderen.setBackground(colorBlue);
			jTxtKinderen.setFont(fontEyes);
			jTxtKinderen.setForeground(Color.white);
			jTxtKinderen.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtKinderen.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtExtraKosten().requestFocusInWindow();
						getJTxtExtraKosten().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtVolwassen().requestFocusInWindow();
						getJTxtVolwassen().selectAll();
					}
				}
			});
			jTxtKinderen.addFocusListener(this);
			jTxtKinderen.setCaretColor(Color.white);
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
			jTxtKostAuto100.setMinimumSize(kostenDim);
			jTxtKostAuto100.setBackground(colorYellow);
			jTxtKostAuto100.setEditable(false);
			jTxtKostAuto100.setText("\u20ac 0,00");
			jTxtKostAuto100.setFont(font);
			jTxtKostAuto100.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostAuto100.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostAuto150.setMinimumSize(kostenDim);
			jTxtKostAuto150.setBackground(colorYellow);
			jTxtKostAuto150.setEditable(false);
			jTxtKostAuto150.setText("\u20ac 0,00");
			jTxtKostAuto150.setFont(font);
			jTxtKostAuto150.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostAuto150.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostWaarborg.setMinimumSize(kostenDim);
			jTxtKostWaarborg.setBackground(colorYellow);
			jTxtKostWaarborg.setEditable(false);
			jTxtKostWaarborg.setText("\u20ac 0,00");
			jTxtKostWaarborg.setFont(font);
			jTxtKostWaarborg.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostWaarborg.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostJeton.setMinimumSize(kostenDim);
			jTxtKostJeton.setBackground(colorYellow);
			jTxtKostJeton.setEditable(false);
			jTxtKostJeton.setText("\u20ac 0,00");
			jTxtKostJeton.setFont(font);
			jTxtKostJeton.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostJeton.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostZakGeel.setMinimumSize(kostenDim);
			jTxtKostZakGeel.setBackground(colorYellow);
			jTxtKostZakGeel.setEditable(false);
			jTxtKostZakGeel.setText("\u20ac 0,00");
			jTxtKostZakGeel.setFont(font);
			jTxtKostZakGeel.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostZakGeel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostZakBlauw.setMinimumSize(kostenDim);
			jTxtKostZakBlauw.setBackground(colorYellow);
			jTxtKostZakBlauw.setEditable(false);
			jTxtKostZakBlauw.setText("\u20ac 0,00");
			jTxtKostZakBlauw.setFont(font);
			jTxtKostZakBlauw.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostZakBlauw.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostCar.setMinimumSize(kostenDim);
			jTxtKostCar.setBackground(colorYellow);
			jTxtKostCar.setEditable(false);
			jTxtKostCar.setText("\u20ac 0,00");
			jTxtKostCar.setFont(font);
			jTxtKostCar.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostCar.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostTent.setMinimumSize(kostenDim);
			jTxtKostTent.setBackground(colorYellow);
			jTxtKostTent.setEditable(false);
			jTxtKostTent.setText("\u20ac 0,00");
			jTxtKostTent.setFont(font);
			jTxtKostTent.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostTent.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostVolwassen.setMinimumSize(kostenDim);
			jTxtKostVolwassen.setBackground(colorYellow);
			jTxtKostVolwassen.setEditable(false);
			jTxtKostVolwassen.setText("\u20ac 0,00");
			jTxtKostVolwassen.setFont(font);
			jTxtKostVolwassen.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostVolwassen.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtKostKinderen.setMinimumSize(kostenDim);
			jTxtKostKinderen.setBackground(colorYellow);
			jTxtKostKinderen.setEditable(false);
			jTxtKostKinderen.setText("\u20ac 0,00");
			jTxtKostKinderen.setFont(font);
			jTxtKostKinderen.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtKostKinderen.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtExtraKosten.setMinimumSize(kostenDim);
			jTxtExtraKosten.setBackground(colorYellow);
			jTxtExtraKosten.setFont(font);
			jTxtExtraKosten.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtBetaling().requestFocusInWindow();
						getJTxtBetaling().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtKinderen().requestFocusInWindow();
						getJTxtKinderen().selectAll();
					}
				}
			});
			jTxtExtraKosten.addFocusListener(this);
			jTxtExtraKosten.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtExtraKosten.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsAuto100.setMinimumSize(prijsDim);
			jTxtPrijsAuto100.setBackground(colorGray);
			jTxtPrijsAuto100.setEditable(false);
			jTxtPrijsAuto100.setFont(font);
			jTxtPrijsAuto100.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsAuto100.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsAuto150.setMinimumSize(prijsDim);
			jTxtPrijsAuto150.setBackground(colorGray);
			jTxtPrijsAuto150.setEditable(false);
			jTxtPrijsAuto150.setFont(font);
			jTxtPrijsAuto150.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsAuto150.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsWaarborg.setMinimumSize(prijsDim);
			jTxtPrijsWaarborg.setBackground(colorGray);
			jTxtPrijsWaarborg.setEditable(false);
			jTxtPrijsWaarborg.setFont(font);
			jTxtPrijsWaarborg.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsWaarborg.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsJeton.setMinimumSize(prijsDim);
			jTxtPrijsJeton.setBackground(colorGray);
			jTxtPrijsJeton.setEditable(false);
			jTxtPrijsJeton.setFont(font);
			jTxtPrijsJeton.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsJeton.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsZakGeel.setMinimumSize(prijsDim);
			jTxtPrijsZakGeel.setBackground(colorGray);
			jTxtPrijsZakGeel.setEditable(false);
			jTxtPrijsZakGeel.setFont(font);
			jTxtPrijsZakGeel.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsZakGeel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsZakBlauw.setMinimumSize(prijsDim);
			jTxtPrijsZakBlauw.setBackground(colorGray);
			jTxtPrijsZakBlauw.setEditable(false);
			jTxtPrijsZakBlauw.setFont(font);
			jTxtPrijsZakBlauw.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsZakBlauw.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsCar.setMinimumSize(prijsDim);
			jTxtPrijsCar.setBackground(colorGray);
			jTxtPrijsCar.setEditable(false);
			jTxtPrijsCar.setFont(font);
			jTxtPrijsCar.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsCar.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsTent.setMinimumSize(prijsDim);
			jTxtPrijsTent.setBackground(colorGray);
			jTxtPrijsTent.setEditable(false);
			jTxtPrijsTent.setFont(font);
			jTxtPrijsTent.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsTent.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsVolwassen.setMinimumSize(prijsDim);
			jTxtPrijsVolwassen.setBackground(colorGray);
			jTxtPrijsVolwassen.setEditable(false);
			jTxtPrijsVolwassen.setFont(font);
			jTxtPrijsVolwassen.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsVolwassen.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jTxtPrijsKinderen.setMinimumSize(prijsDim);
			jTxtPrijsKinderen.setBackground(colorGray);
			jTxtPrijsKinderen.setEditable(false);
			jTxtPrijsKinderen.setFont(font);
			jTxtPrijsKinderen.setHorizontalAlignment(SwingConstants.RIGHT);
			jTxtPrijsKinderen.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
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
			jBtnStandaardWaarden.setFont(font);
			jBtnStandaardWaarden.addActionListener(this);
		}
		return jBtnStandaardWaarden;
	}

	/**
	 * This method initializes JTxtNaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtNaam() {
		if (jTxtNaam == null) {
			jTxtNaam = new JTextField();
			jTxtNaam.setFont(font);
			jTxtNaam.setPreferredSize(new Dimension(280, 36));
			jTxtNaam.setMinimumSize(new Dimension(280, 36));
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
			jTxtStandplaats.setFont(font);
			jTxtStandplaats.setPreferredSize(new Dimension(280, 36));
			jTxtStandplaats.setMinimumSize(new Dimension(280, 36));
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
			jTxtAutoplaat.setFont(font);
			jTxtAutoplaat.setPreferredSize(new Dimension(280, 36));
			jTxtAutoplaat.setMinimumSize(new Dimension(280, 36));
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
			jTxtBadge.setFont(font);
			jTxtBadge.setPreferredSize(new Dimension(280, 36));
			jTxtBadge.setMinimumSize(new Dimension(280, 36));
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
			int width = (int) (aantalDim.getWidth() + kostenDim.getWidth());
			jTxtTotaal.setPreferredSize(new Dimension(width, (int) aantalDim.getHeight() * 2));
			jTxtTotaal.setMinimumSize(new Dimension(width, (int) aantalDim.getHeight() * 2));
			jTxtTotaal.setBackground(Color.RED);
			jTxtTotaal.setEditable(false);
			jTxtTotaal.setText("\u20ac 0,00");
			jTxtTotaal.setFont(fontEyes);
			jTxtTotaal.setForeground(Color.white);
			jTxtTotaal.setHorizontalAlignment(SwingConstants.CENTER);
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
			int width = (int) (aantalDim.getWidth() + kostenDim.getWidth());
			jTxtBetaling.setPreferredSize(new Dimension(width, (int) aantalDim.getHeight()));
			jTxtBetaling.setMinimumSize(new Dimension(width, (int) aantalDim.getHeight()));
			jTxtBetaling.setBackground(Color.GREEN);
			jTxtBetaling.setFont(font);
			jTxtBetaling.addFocusListener(this);
			jTxtBetaling.setHorizontalAlignment(SwingConstants.CENTER);
			jTxtBetaling.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
						getJTxtNachten().requestFocusInWindow();
						getJTxtNachten().selectAll();
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						getJTxtExtraKosten().requestFocusInWindow();
						getJTxtExtraKosten().selectAll();
					}
				}
			});
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
			int width = (int) (aantalDim.getWidth() + kostenDim.getWidth());
			jTxtTerug.setPreferredSize(new Dimension(width, (int) aantalDim.getHeight()));
			jTxtTerug.setMinimumSize(new Dimension(width, (int) aantalDim.getHeight()));
			jTxtTerug.setEditable(false);
			jTxtTerug.setText("\u20ac 0,00");
			jTxtTerug.setFont(font);
			jTxtTerug.setHorizontalAlignment(SwingConstants.CENTER);
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
			jTxtKostNacht.setMinimumSize(prijsDim);
			jTxtKostNacht.setBackground(colorBlue);
			jTxtKostNacht.setEditable(false);
			jTxtKostNacht.setText("\u20ac 0,00");
			jTxtKostNacht.setFont(fontEyes);
			jTxtKostNacht.setForeground(Color.white);
			jTxtKostNacht.setHorizontalAlignment(SwingConstants.CENTER);
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
			jTxtAantalNachten.setMinimumSize(kostenDim);
			jTxtAantalNachten.setBackground(colorBlue);
			jTxtAantalNachten.setEditable(false);
			jTxtAantalNachten.setText("1");
			jTxtAantalNachten.setFont(fontEyes);
			jTxtAantalNachten.setForeground(Color.white);
			jTxtAantalNachten.setHorizontalAlignment(SwingConstants.CENTER);
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
			jTxtTotaalNachten.setMinimumSize(kostenDim);
			jTxtTotaalNachten.setBackground(colorBlue);
			jTxtTotaalNachten.setEditable(false);
			jTxtTotaalNachten.setText("\u20ac 0,00");
			jTxtTotaalNachten.setFont(fontEyes);
			jTxtTotaalNachten.setForeground(Color.white);
			jTxtTotaalNachten.setHorizontalAlignment(SwingConstants.CENTER);
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
			jTxtJetonsZakKost.setMinimumSize(kostenDim);
			jTxtJetonsZakKost.setBackground(Color.GREEN);
			jTxtJetonsZakKost.setEditable(false);
			jTxtJetonsZakKost.setText("\u20ac 0,00");
			jTxtJetonsZakKost.setFont(font);
			jTxtJetonsZakKost.setHorizontalAlignment(SwingConstants.CENTER);
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
			jTxtTotZonderWB.setMinimumSize(kostenDim);
			jTxtTotZonderWB.setBackground(colorYellow);
			jTxtTotZonderWB.setEditable(false);
			jTxtTotZonderWB.setText("\u20ac 0,00");
			jTxtTotZonderWB.setFont(font);
			jTxtTotZonderWB.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jTxtTotZonderWB;
	}

	public JDatePicker getjDpDatum() {
		if (jDpDatum == null) {
			jDpDatum = JDateComponentFactory.createJDatePicker();
			jDpDatum.setTextEditable(true);
			jDpDatum.setShowYearButtons(true);
			jDpDatum.getJFormattedTextField().setPreferredSize(new Dimension(150, 30));
			jDpDatum.getJFormattedTextField().setMinimumSize(new Dimension(150, 30));
			jDpDatum.getJFormattedTextField().setFont(font);
		}
		return jDpDatum;
	}

	public JDatePicker getjDpDatumVan() {
		if (jDpDatumVan == null) {
			jDpDatumVan = JDateComponentFactory.createJDatePicker();
			jDpDatumVan.setTextEditable(true);
			jDpDatumVan.setShowYearButtons(true);
			jDpDatumVan.clearTextField();
			jDpDatumVan.getJFormattedTextField().setPreferredSize(txtFieldDim);
			jDpDatumVan.getJFormattedTextField().setMinimumSize(txtFieldDim);
			jDpDatumVan.getJFormattedTextField().setFont(font);
			jDpDatumVan.getJFormattedTextField().getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					// wijzigAantalDagen();
					// berekendTotalen(true, null);
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					wijzigAantalDagen();
					berekendTotalen(true, null);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// wijzigAantalDagen();
					// berekendTotalen(true, null);
				}
			});
		}
		return jDpDatumVan;
	}

	public JDatePicker getjDpDatumTot() {
		if (jDpDatumTot == null) {
			jDpDatumTot = JDateComponentFactory.createJDatePicker();
			jDpDatumTot.setTextEditable(true);
			jDpDatumTot.setShowYearButtons(true);
			jDpDatumTot.clearTextField();
			jDpDatumTot.getJFormattedTextField().setPreferredSize(txtFieldDim);
			jDpDatumTot.getJFormattedTextField().setMinimumSize(txtFieldDim);
			jDpDatumTot.getJFormattedTextField().setFont(font);
			jDpDatumTot.getJFormattedTextField().getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					// wijzigAantalDagen();
					// berekendTotalen(true, null);
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					wijzigAantalDagen();
					berekendTotalen(true, null);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// wijzigAantalDagen();
					// berekendTotalen(true, null);
				}
			});
		}
		return jDpDatumTot;
	}

	private void wijzigAantalDagen() {
		Calendar calVan = Calendar.getInstance();
		calVan.setTime(getjDpDatumVan().getTime());
		Calendar calTot = Calendar.getInstance();
		calTot.setTime(getjDpDatumTot().getTime());
		long aantalDagen = DateUtilities.getDifference(calVan, calTot, TimeUnit.DAYS);
		int iAantalNachten = 0;
		String s = Long.toString(aantalDagen);
		try {
			iAantalNachten = Integer.parseInt(s);
			iAantalNachten = (iAantalNachten > 0) ? iAantalNachten : 1;
			getJTxtNachten().setText(Integer.toString(iAantalNachten));
			getKasbon().setAantalNachten(iAantalNachten);
		} catch (Exception ex) {

		}
	}

	/**
	 * This method initializes jBtnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnPrintCash() {
		if (jBtnPrintCash == null) {
			jBtnPrintCash = new JButton();
			jBtnPrintCash.setFont(font);
			jBtnPrintCash.setText("Afdrukken contant");
			jBtnPrintCash.addActionListener(this);
		}
		return jBtnPrintCash;
	}
	
	private JButton getJBtnPrintPayconic() {
		if (jBtnPrintPayconic == null) {
			jBtnPrintPayconic = new JButton();
			jBtnPrintPayconic.setFont(font);
			jBtnPrintPayconic.setText("Afdrukken payconic");
			jBtnPrintPayconic.addActionListener(this);
		}
		return jBtnPrintPayconic;
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
			jBtnVerwijder.setFont(font);
			jBtnVerwijder.addActionListener(this);
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
			jbtnFiche.setFont(font);
			jbtnFiche.addActionListener(this);
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
			jBtnReset.setFont(font);
			jBtnReset.addActionListener(this);
		}
		return jBtnReset;
	}

	public JButton getjBtnOpslaan() {
		if (jBtnOpslaan == null) {
			jBtnOpslaan = new JButton("Opslaan");
			jBtnOpslaan.setFont(font);
			jBtnOpslaan.addActionListener(this);
		}
		return jBtnOpslaan;
	}

	/**
	 * This method initializes jPanelKosten
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelKosten() {
		if (jPanelKosten == null) {
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.anchor = GridBagConstraints.WEST;
			gridBagConstraints37.gridy = 2;
			gridBagConstraints37.gridwidth = 2;
			gridBagConstraints37.gridx = 4;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			gridBagConstraints26.insets = new Insets(0, 0, 10, 0);
			gridBagConstraints26.gridx = 2;
			gridBagConstraints26.gridy = 16;
			gridBagConstraints26.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 16;
			gridBagConstraints12.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gbcDivider5 = new GridBagConstraints();
			gbcDivider5.gridx = 0;
			gbcDivider5.anchor = GridBagConstraints.WEST;
			gbcDivider5.insets = insetsDivider;
			gbcDivider5.fill = GridBagConstraints.HORIZONTAL;
			gbcDivider5.gridwidth = 4;
			gbcDivider5.gridy = 15;
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.gridx = 3;
			gridBagConstraints36.gridy = 14;
			gridBagConstraints36.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			gridBagConstraints25.gridx = 2;
			gridBagConstraints25.gridy = 14;
			gridBagConstraints25.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints76 = new GridBagConstraints();
			gridBagConstraints76.anchor = GridBagConstraints.WEST;
			gridBagConstraints76.gridx = 1;
			gridBagConstraints76.gridy = 14;
			gridBagConstraints76.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.anchor = GridBagConstraints.EAST;
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 14;
			gridBagConstraints11.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.anchor = GridBagConstraints.WEST;
			gridBagConstraints35.gridx = 3;
			gridBagConstraints35.gridy = 13;
			gridBagConstraints35.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridx = 2;
			gridBagConstraints24.gridy = 13;
			gridBagConstraints24.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.anchor = GridBagConstraints.WEST;
			gridBagConstraints71.gridx = 1;
			gridBagConstraints71.gridy = 13;
			gridBagConstraints71.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 13;
			gridBagConstraints10.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.anchor = GridBagConstraints.WEST;
			gridBagConstraints34.gridx = 3;
			gridBagConstraints34.gridy = 12;
			gridBagConstraints34.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.gridy = 12;
			gridBagConstraints23.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.anchor = GridBagConstraints.WEST;
			gridBagConstraints68.gridx = 1;
			gridBagConstraints68.gridy = 12;
			gridBagConstraints68.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 12;
			gridBagConstraints9.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.anchor = GridBagConstraints.WEST;
			gridBagConstraints33.gridx = 3;
			gridBagConstraints33.gridy = 11;
			gridBagConstraints33.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridx = 2;
			gridBagConstraints22.gridy = 11;
			gridBagConstraints22.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.anchor = GridBagConstraints.WEST;
			gridBagConstraints62.gridx = 1;
			gridBagConstraints62.gridy = 11;
			gridBagConstraints62.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 11;
			gridBagConstraints8.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gbcDivider4 = new GridBagConstraints();
			gbcDivider4.gridx = 0;
			gbcDivider4.anchor = GridBagConstraints.WEST;
			gbcDivider4.insets = insetsDivider;
			gbcDivider4.fill = GridBagConstraints.HORIZONTAL;
			gbcDivider4.gridwidth = 4;
			gbcDivider4.gridy = 10;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.anchor = GridBagConstraints.WEST;
			gridBagConstraints32.gridx = 3;
			gridBagConstraints32.gridy = 9;
			gridBagConstraints32.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.gridx = 2;
			gridBagConstraints61.gridy = 9;
			gridBagConstraints61.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.anchor = GridBagConstraints.WEST;
			gridBagConstraints54.gridx = 1;
			gridBagConstraints54.gridy = 9;
			gridBagConstraints54.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 9;
			gridBagConstraints7.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.anchor = GridBagConstraints.WEST;
			gridBagConstraints51.gridx = 3;
			gridBagConstraints51.gridy = 8;
			gridBagConstraints51.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridx = 2;
			gridBagConstraints20.gridy = 8;
			gridBagConstraints20.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = GridBagConstraints.WEST;
			gridBagConstraints50.gridx = 1;
			gridBagConstraints50.gridy = 8;
			gridBagConstraints50.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 8;
			gridBagConstraints6.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.gridx = 3;
			gridBagConstraints30.gridy = 7;
			gridBagConstraints30.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridx = 2;
			gridBagConstraints17.gridy = 7;
			gridBagConstraints17.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.anchor = GridBagConstraints.WEST;
			gridBagConstraints49.gridx = 1;
			gridBagConstraints49.gridy = 7;
			gridBagConstraints49.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 7;
			gridBagConstraints5.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gbcDivider3 = new GridBagConstraints();
			gbcDivider3.gridx = 0;
			gbcDivider3.anchor = GridBagConstraints.WEST;
			gbcDivider3.insets = insetsDivider;
			gbcDivider3.fill = GridBagConstraints.HORIZONTAL;
			gbcDivider3.gridwidth = 4;
			gbcDivider3.gridy = 6;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridx = 2;
			gridBagConstraints16.gridy = 5;
			gridBagConstraints16.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.anchor = GridBagConstraints.WEST;
			gridBagConstraints29.gridx = 3;
			gridBagConstraints29.gridy = 5;
			gridBagConstraints29.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridx = 1;
			gridBagConstraints41.gridy = 5;
			gridBagConstraints41.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 5;
			gridBagConstraints4.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gbcDivider2 = new GridBagConstraints();
			gbcDivider2.gridx = 0;
			gbcDivider2.anchor = GridBagConstraints.WEST;
			gbcDivider2.insets = insetsDivider;
			gbcDivider2.fill = GridBagConstraints.HORIZONTAL;
			gbcDivider2.gridwidth = 4;
			gbcDivider2.gridy = 4;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.anchor = GridBagConstraints.WEST;
			gridBagConstraints28.gridx = 3;
			gridBagConstraints28.gridy = 3;
			gridBagConstraints28.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 2;
			gridBagConstraints15.gridy = 3;
			gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.anchor = GridBagConstraints.WEST;
			gridBagConstraints27.gridx = 3;
			gridBagConstraints27.gridy = 2;
			gridBagConstraints27.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints31.gridy = -1;
			gridBagConstraints31.gridx = -1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridx = 2;
			gridBagConstraints14.gridy = 2;
			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridy = 3;
			gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.gridy = 2;
			gridBagConstraints19.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.insets = new Insets(0, 20, 0, 10);
			GridBagConstraints gbcDivider1 = new GridBagConstraints();
			gbcDivider1.gridx = 0;
			gbcDivider1.anchor = GridBagConstraints.WEST;
			gbcDivider1.insets = insetsDivider;
			gbcDivider1.fill = GridBagConstraints.HORIZONTAL;
			gbcDivider1.gridwidth = 4;
			gbcDivider1.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridheight = 1;
			gridBagConstraints1.insets = new Insets(0, 20, 0, 10);
			jPanelKosten = new JPanel();
			jPanelKosten.setBorder(BorderFactory.createRaisedBevelBorder());
			jPanelKosten.setLayout(new GridBagLayout());
			jPanelKosten.add(jLblNachten, gridBagConstraints1);
			jPanelKosten.add(getJTxtNachten(), gridBagConstraints18);
			jPanelKosten.add(getDivider(widthDivider), gbcDivider1);
			jPanelKosten.add(jLblAuto100, gridBagConstraints2);
			jPanelKosten.add(getJTxtAuto100(), gridBagConstraints19);
			jPanelKosten.add(getJTxtKostAuto100(), gridBagConstraints14);
			jPanelKosten.add(getJTxtPrijsAuto100(), gridBagConstraints27);
			jPanelKosten.add(jLblAuto150, gridBagConstraints3);
			jPanelKosten.add(getJTxtAuto150(), gridBagConstraints21);
			jPanelKosten.add(getJTxtKostAuto150(), gridBagConstraints15);
			jPanelKosten.add(getJTxtPrijsAuto150(), gridBagConstraints28);
			jPanelKosten.add(jLblWaarborg, gridBagConstraints4);
			jPanelKosten.add(getJTxtWaarborg(), gridBagConstraints41);
			jPanelKosten.add(getJTxtPrijsWaarborg(), gridBagConstraints29);
			jPanelKosten.add(getJTxtKostWaarborg(), gridBagConstraints16);
			jPanelKosten.add(jLblJeton, gridBagConstraints5);
			jPanelKosten.add(getJTxtJeton(), gridBagConstraints49);
			jPanelKosten.add(getJTxtKostJeton(), gridBagConstraints17);
			jPanelKosten.add(getJTxtPrijsJeton(), gridBagConstraints30);
			jPanelKosten.add(jLblZakGeel, gridBagConstraints6);
			jPanelKosten.add(getJTxtZakGeel(), gridBagConstraints50);
			jPanelKosten.add(getJTxtKostZakGeel(), gridBagConstraints20);
			jPanelKosten.add(getJTxtPrijsZakGeel(), gridBagConstraints51);
			jPanelKosten.add(jLblZakBlauw, gridBagConstraints7);
			jPanelKosten.add(getJTxtZakBlauw(), gridBagConstraints54);
			jPanelKosten.add(getJTxtKostZakBlauw(), gridBagConstraints61);
			jPanelKosten.add(getJTxtPrijsZakBlauw(), gridBagConstraints32);
			jPanelKosten.add(jLblCar, gridBagConstraints8);
			jPanelKosten.add(getJtxtCar(), gridBagConstraints62);
			jPanelKosten.add(getJTxtKostCar(), gridBagConstraints22);
			jPanelKosten.add(getJTxtPrijsCar(), gridBagConstraints33);
			jPanelKosten.add(jLblTent, gridBagConstraints9);
			jPanelKosten.add(getJTxtTent(), gridBagConstraints68);
			jPanelKosten.add(getJTxtKostTent(), gridBagConstraints23);
			jPanelKosten.add(getJTxtPrijsTent(), gridBagConstraints34);
			jPanelKosten.add(jLblVolwassen, gridBagConstraints10);
			jPanelKosten.add(getJTxtVolwassen(), gridBagConstraints71);
			jPanelKosten.add(getJTxtKostVolwassen(), gridBagConstraints24);
			jPanelKosten.add(getJTxtPrijsVolwassen(), gridBagConstraints35);
			jPanelKosten.add(jLblKinderen, gridBagConstraints11);
			jPanelKosten.add(getJTxtKinderen(), gridBagConstraints76);
			jPanelKosten.add(getJTxtKostKinderen(), gridBagConstraints25);
			jPanelKosten.add(getJTxtPrijsKinderen(), gridBagConstraints36);
			jPanelKosten.add(jLblTelefoon, gridBagConstraints12);
			jPanelKosten.add(getJTxtExtraKosten(), gridBagConstraints26);
			jPanelKosten.add(getJBtnStandaardWaarden(), gridBagConstraints37);
			jPanelKosten.add(getDivider(widthDivider), gbcDivider2);
			jPanelKosten.add(getDivider(widthDivider), gbcDivider3);
			jPanelKosten.add(getDivider(widthDivider), gbcDivider4);
			jPanelKosten.add(getDivider(widthDivider), gbcDivider5);
		}
		return jPanelKosten;
	}

	/**
	 * This method initializes jPanelKasbon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelKasbon() {
		if (jPanelKasbon == null) {
			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.anchor = GridBagConstraints.EAST;
			gridBagConstraints70.gridx = 2;
			gridBagConstraints70.gridy = 5;
			gridBagConstraints70.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints69.gridy = 5;
			gridBagConstraints69.gridx = 1;
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = GridBagConstraints.WEST;
			gridBagConstraints40.gridx = 2;
			gridBagConstraints40.gridy = 0;
			gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.anchor = GridBagConstraints.WEST;
			gridBagConstraints48.gridx = 1;
			gridBagConstraints48.gridy = 4;
			gridBagConstraints48.gridwidth = 2;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridx = 1;
			gridBagConstraints46.gridy = 3;
			gridBagConstraints46.gridwidth = 2;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.anchor = GridBagConstraints.WEST;
			gridBagConstraints44.gridx = 1;
			gridBagConstraints44.gridy = 2;
			gridBagConstraints44.gridwidth = 2;
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.anchor = GridBagConstraints.WEST;
			gridBagConstraints42.gridx = 1;
			gridBagConstraints42.gridy = 1;
			gridBagConstraints42.gridwidth = 2;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.anchor = GridBagConstraints.WEST;
			gridBagConstraints39.gridx = 1;
			gridBagConstraints39.gridy = 0;
			gridBagConstraints39.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.anchor = GridBagConstraints.EAST;
			gridBagConstraints47.gridx = 0;
			gridBagConstraints47.gridy = 4;
			gridBagConstraints47.insets = new Insets(0, 0, 0, 10);
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.anchor = GridBagConstraints.EAST;
			gridBagConstraints45.gridx = 0;
			gridBagConstraints45.gridy = 3;
			gridBagConstraints45.insets = new Insets(0, 0, 0, 10);
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.anchor = GridBagConstraints.EAST;
			gridBagConstraints43.gridx = 0;
			gridBagConstraints43.gridy = 2;
			gridBagConstraints43.insets = new Insets(0, 0, 0, 10);
			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
			gridBagConstraints77.anchor = GridBagConstraints.EAST;
			gridBagConstraints77.gridx = 0;
			gridBagConstraints77.gridy = 1;
			gridBagConstraints77.insets = new Insets(0, 0, 0, 10);
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.anchor = GridBagConstraints.EAST;
			gridBagConstraints38.gridx = 0;
			gridBagConstraints38.gridy = 0;
			gridBagConstraints38.insets = new Insets(0, 20, 0, 10);
			jPanelKasbon = new JPanel();
			jPanelKasbon.setLayout(new GridBagLayout());
			jPanelKasbon.setBorder(BorderFactory.createRaisedBevelBorder());
			jPanelKasbon.add(jLblDatum, gridBagConstraints38);
			jPanelKasbon.add(jLblNaam, gridBagConstraints77);
			jPanelKasbon.add(jLblStandplaats, gridBagConstraints43);
			jPanelKasbon.add(jLblAutoplaat, gridBagConstraints45);
			jPanelKasbon.add(jLblBadge, gridBagConstraints47);
			jPanelKasbon.add((Component) getjDpDatum(), gridBagConstraints39);
			jPanelKasbon.add(getJTxtNaam(), gridBagConstraints42);
			jPanelKasbon.add(getJTxtStandplaats(), gridBagConstraints44);
			jPanelKasbon.add(getJTxtAutoplaat(), gridBagConstraints46);
			jPanelKasbon.add(getJTxtBadge(), gridBagConstraints48);
			jPanelKasbon.add((Component) getjDpDatumVan(), gridBagConstraints69);
			jPanelKasbon.add((Component) getjDpDatumTot(), gridBagConstraints70);
		}
		return jPanelKasbon;
	}

	/**
	 * This method initializes jPanelKassa
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelKassa() {
		if (jPanelKassa == null) {
			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
			gridBagConstraints75.anchor = GridBagConstraints.EAST;
			gridBagConstraints75.gridy = 2;
			gridBagConstraints75.gridx = 0;
			GridBagConstraints gridBagConstraints74 = new GridBagConstraints();
			gridBagConstraints74.anchor = GridBagConstraints.EAST;
			gridBagConstraints74.gridy = 1;
			gridBagConstraints74.gridx = 0;
			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
			gridBagConstraints73.gridx = 0;
			gridBagConstraints73.gridy = 0;
			jPanelKassa = new JPanel();
			jPanelKassa.setLayout(new GridBagLayout());
			jPanelKassa.setBorder(BorderFactory.createRaisedBevelBorder());
			jPanelKassa.add(jLblNieuw, gridBagConstraints73);
			jPanelKassa.add(getJbtnFiche(), gridBagConstraints74);
			jPanelKassa.add(getJBtnReset(), gridBagConstraints75);
		}
		return jPanelKassa;
	}

	/**
	 * This method initializes jPanelTotaal
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTotaal() {
		if (jPanelTotaal == null) {
			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints80.gridy = 1;
			gridBagConstraints80.weightx = 1.0;
			gridBagConstraints80.gridx = 3;
			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.anchor = GridBagConstraints.WEST;
			gridBagConstraints67.gridx = 5;
			gridBagConstraints67.gridy = 3;
			gridBagConstraints67.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.anchor = GridBagConstraints.WEST;
			gridBagConstraints66.gridx = 5;
			gridBagConstraints66.gridy = 2;
			gridBagConstraints66.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.gridx = 4;
			gridBagConstraints65.gridy = 3;
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.anchor = GridBagConstraints.WEST;
			gridBagConstraints64.gridx = 4;
			gridBagConstraints64.gridy = 2;
			gridBagConstraints64.gridwidth = 1;
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.gridx = 5;
			gridBagConstraints63.gridy = 1;
			gridBagConstraints63.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			gridBagConstraints79.anchor = GridBagConstraints.WEST;
			gridBagConstraints79.gridx = 4;
			gridBagConstraints79.gridy = 1;
			gridBagConstraints79.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints60.gridy = 0;
			gridBagConstraints60.gridx = 5;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints59.gridy = 0;
			gridBagConstraints59.gridx = 4;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints58.gridy = 0;
			gridBagConstraints58.gridx = 3;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.anchor = GridBagConstraints.WEST;
			gridBagConstraints57.gridwidth = 2;
			gridBagConstraints57.gridx = 2;
			gridBagConstraints57.gridy = 3;
			gridBagConstraints57.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.anchor = GridBagConstraints.WEST;
			gridBagConstraints56.gridwidth = 2;
			gridBagConstraints56.gridx = 2;
			gridBagConstraints56.gridy = 2;
			gridBagConstraints56.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.anchor = GridBagConstraints.WEST;
			gridBagConstraints55.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints55.gridheight = 2;
			gridBagConstraints55.gridwidth = 2;
			gridBagConstraints55.gridx = 1;
			gridBagConstraints55.gridy = 0;
			gridBagConstraints55.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 0;
			gridBagConstraints53.gridy = 3;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridx = 0;
			gridBagConstraints52.gridy = 2;
			GridBagConstraints gridBagConstraints78 = new GridBagConstraints();
			gridBagConstraints78.gridheight = 2;
			gridBagConstraints78.gridy = 0;
			gridBagConstraints78.gridx = 0;
			jPanelTotaal = new JPanel();
			jPanelTotaal.setLayout(new GridBagLayout());
			jPanelTotaal.setBorder(BorderFactory.createRaisedBevelBorder());
			jPanelTotaal.add(jLblTotaal, gridBagConstraints78);
			jPanelTotaal.add(jLblBetaling, gridBagConstraints52);
			jPanelTotaal.add(jLblTerug, gridBagConstraints53);
			jPanelTotaal.add(getJTxtTotaal(), gridBagConstraints55);
			jPanelTotaal.add(getJTxtBetaling(), gridBagConstraints56);
			jPanelTotaal.add(getJTxtTerug(), gridBagConstraints57);
			jPanelTotaal.add(jLblKostNacht, gridBagConstraints58);
			jPanelTotaal.add(jLblAantalNachten, gridBagConstraints59);
			jPanelTotaal.add(jLblTotaalNachten, gridBagConstraints60);
			jPanelTotaal.add(getJTxtAantalNachten(), gridBagConstraints79);
			jPanelTotaal.add(getJTxtTotaalNachten(), gridBagConstraints63);
			jPanelTotaal.add(JLblJetonsZakKost, gridBagConstraints64);
			jPanelTotaal.add(jLblTotZonderWB, gridBagConstraints65);
			jPanelTotaal.add(getJTxtJetonsZakKost(), gridBagConstraints66);
			jPanelTotaal.add(getJTxtTotZonderWB(), gridBagConstraints67);
			jPanelTotaal.add(getJTxtKostNacht(), gridBagConstraints80);
		}
		return jPanelTotaal;
	}

	public JTextField getjNaam1() {
		if (jNaam1 == null) {
			jNaam1 = new JTextField();
			jNaam1.setFont(font);
		}
		return jNaam1;
	}

	public JTextField getjNaam2() {
		if (jNaam2 == null) {
			jNaam2 = new JTextField();
			jNaam2.setFont(font);
		}
		return jNaam2;
	}

	public JTextField getjNaam3() {
		if (jNaam3 == null) {
			jNaam3 = new JTextField();
			jNaam3.setFont(font);
		}
		return jNaam3;
	}

	public JTextField getjNaam4() {
		if (jNaam4 == null) {
			jNaam4 = new JTextField();
			jNaam4.setFont(font);
		}
		return jNaam4;
	}

	public JTextField getjNaam5() {
		if (jNaam5 == null) {
			jNaam5 = new JTextField();
			jNaam5.setFont(font);
		}
		return jNaam5;
	}

	public JTextField getjNaam6() {
		if (jNaam6 == null) {
			jNaam6 = new JTextField();
			jNaam6.setFont(font);

		}
		return jNaam6;
	}

	public JTextField getjNaam7() {
		if (jNaam7 == null) {
			jNaam7 = new JTextField();
			jNaam7.setFont(font);
		}
		return jNaam7;
	}

	public JTextField getjNaam8() {
		if (jNaam8 == null) {
			jNaam8 = new JTextField();
			jNaam8.setFont(font);
		}
		return jNaam8;
	}

	public JTextField getjNaam9() {
		if (jNaam9 == null) {
			jNaam9 = new JTextField();
			jNaam9.setFont(font);
		}
		return jNaam9;
	}

	public JTextField getjNaam10() {
		if (jNaam10 == null) {
			jNaam10 = new JTextField();
			jNaam10.setFont(font);
		}
		return jNaam10;
	}

	public JTextField getjWaarde1() {
		if (jWaarde1 == null) {
			jWaarde1 = new JTextField();
			jWaarde1.setFont(font);
		}
		return jWaarde1;
	}

	public JTextField getjWaarde8() {
		if (jWaarde8 == null) {
			jWaarde8 = new JTextField();
			jWaarde8.setFont(font);
		}
		return jWaarde8;
	}

	public JTextField getjWaarde2() {
		if (jWaarde2 == null) {
			jWaarde2 = new JTextField();
			jWaarde2.setFont(font);
		}
		return jWaarde2;
	}

	public JTextField getjWaarde3() {
		if (jWaarde3 == null) {
			jWaarde3 = new JTextField();
			jWaarde3.setFont(font);
		}
		return jWaarde3;
	}

	public JTextField getjWaarde4() {
		if (jWaarde4 == null) {
			jWaarde4 = new JTextField();
			jWaarde4.setFont(font);
		}
		return jWaarde4;
	}

	public JTextField getjWaarde5() {
		if (jWaarde5 == null) {
			jWaarde5 = new JTextField();
			jWaarde5.setFont(font);
		}
		return jWaarde5;
	}

	public JTextField getjWaarde6() {
		if (jWaarde6 == null) {
			jWaarde6 = new JTextField();
			jWaarde6.setFont(font);
		}
		return jWaarde6;
	}

	public JTextField getjWaarde7() {
		if (jWaarde7 == null) {
			jWaarde7 = new JTextField();
			jWaarde7.setFont(font);
		}
		return jWaarde7;
	}

	public JTextField getjWaarde9() {
		if (jWaarde9 == null) {
			jWaarde9 = new JTextField();
			jWaarde9.setFont(font);
		}
		return jWaarde9;
	}

	public JTextField getjWaarde10() {
		if (jWaarde10 == null) {
			jWaarde10 = new JTextField();
			jWaarde10.setFont(font);
		}
		return jWaarde10;
	}

	public void setjWaarde8(JTextField jWaarde8) {
		this.jWaarde8 = jWaarde8;
	}

	public JPanel getjPanelKostprijzen() {
		if (jPanelKostprijzen == null) {
			jPanelKostprijzen = new JPanel();
			createKostprijzenPanel();
		}
		return jPanelKostprijzen;
	}

	public void setjPanelKostprijzen(JPanel jPanelKostprijzen) {
		this.jPanelKostprijzen = jPanelKostprijzen;
	}

	public List<KassaKostprijsDTO> getKostprijzen() {
		if (kostprijzen == null) {
			kostprijzen = getKassaKostprijsService().getKostprijzen();
		}
		return kostprijzen;
	}

	public void setKostprijzen(List<KassaKostprijsDTO> kostprijzen) {
		this.kostprijzen = kostprijzen;
	}

	public JButton getjBtnNieuweWaarden() {
		if (jBtnNieuweWaarden == null) {
			jBtnNieuweWaarden = new JButton("Opslaan");
			jBtnNieuweWaarden.setFont(font);
			jBtnNieuweWaarden.addActionListener(this);
		}
		return jBtnNieuweWaarden;
	}

	public List<KasbonDTO> getKasbons() {
		if (kasbons == null) {
			kasbons = getKasbonService().getKasbons(ComputerUtils.getComputername());
		}
		return kasbons;
	}

	public void setKasbons(List<KasbonDTO> kasbons) {
		this.kasbons = kasbons;
	}

	private void saveNieuweWaarden() {
		for (int index = 0; index < getKostprijzen().size(); index++) {
			KassaKostprijsDTO kassaKostprijs = getKostprijzen().get(index);
			if (!isNaamEnWaardeGelijk(index)) {
				kassaKostprijs = getKassaKostprijsService().storeKassaKostprij(kassaKostprijs);
				switch (index) {
				case 0:
					getjNaam1().setText(kassaKostprijs.getNaam());
					break;
				case 1:
					getjNaam2().setText(kassaKostprijs.getNaam());
					break;
				case 2:
					getjNaam3().setText(kassaKostprijs.getNaam());
					break;
				case 3:
					getjNaam4().setText(kassaKostprijs.getNaam());
					break;
				case 4:
					getjNaam5().setText(kassaKostprijs.getNaam());
					break;
				case 5:
					getjNaam6().setText(kassaKostprijs.getNaam());
					break;
				case 6:
					getjNaam7().setText(kassaKostprijs.getNaam());
					break;
				case 7:
					getjNaam8().setText(kassaKostprijs.getNaam());
					break;
				case 8:
					getjNaam9().setText(kassaKostprijs.getNaam());
					break;
				case 9:
					getjNaam10().setText(kassaKostprijs.getNaam());
					break;
				}
			}
		}
	}

	private boolean isNaamEnWaardeGelijk(int index) {
		String s1 = (StringUtils.isEmpty(getKostprijzen().get(index).getNaam())) ? "" : getKostprijzen().get(index)
				.getNaam();
		double d1 = getKostprijzen().get(index).getWaarde();
		String s2 = "";
		double d2 = 0;

		boolean ret = true;
		switch (index) {
		case 0:
			s2 = (StringUtils.isEmpty(getjNaam1().getText())) ? "" : getjNaam1().getText();
			d2 = getDoubleFromText(getjWaarde1().getText());
			break;
		case 1:
			s2 = (StringUtils.isEmpty(getjNaam2().getText())) ? "" : getjNaam2().getText();
			d2 = getDoubleFromText(getjWaarde2().getText());
			break;
		case 2:
			s2 = (StringUtils.isEmpty(getjNaam3().getText())) ? "" : getjNaam3().getText();
			d2 = getDoubleFromText(getjWaarde3().getText());
			break;
		case 3:
			s2 = (StringUtils.isEmpty(getjNaam4().getText())) ? "" : getjNaam4().getText();
			d2 = getDoubleFromText(getjWaarde4().getText());
			break;
		case 4:
			s2 = (StringUtils.isEmpty(getjNaam5().getText())) ? "" : getjNaam5().getText();
			d2 = getDoubleFromText(getjWaarde5().getText());
			break;
		case 5:
			s2 = (StringUtils.isEmpty(getjNaam6().getText())) ? "" : getjNaam6().getText();
			d2 = getDoubleFromText(getjWaarde6().getText());
			break;
		case 6:
			s2 = (StringUtils.isEmpty(getjNaam7().getText())) ? "" : getjNaam7().getText();
			d2 = getDoubleFromText(getjWaarde7().getText());
			break;
		case 7:
			s2 = (StringUtils.isEmpty(getjNaam8().getText())) ? "" : getjNaam8().getText();
			d2 = getDoubleFromText(getjWaarde8().getText());
			break;
		case 8:
			s2 = (StringUtils.isEmpty(getjNaam9().getText())) ? "" : getjNaam9().getText();
			d2 = getDoubleFromText(getjWaarde9().getText());
			break;
		case 9:
			s2 = (StringUtils.isEmpty(getjNaam10().getText())) ? "" : getjNaam10().getText();
			d2 = getDoubleFromText(getjWaarde10().getText());
			break;
		default:
			break;
		}
		if (!s1.equals(s2) || d1 != d2) {
			ret = false;
			getKostprijzen().get(index).setNaam(s2);
			getKostprijzen().get(index).setWaarde(d2);
		}
		return ret;
	}

	private double getDoubleFromText(String s) {
		double d = 0;
		if (!StringUtils.isEmpty(s)) {
			s = s.replace(",", ".");
			try {
				d = Double.parseDouble(s);
			} catch (Exception e) {
			}
		}
		return d;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getJBtnStandaardWaarden()) {
			VulKostprijzenIn();
			Object[] arrVastLos = { "Kassa: standaard waarden", getjPanelKostprijzen() };
			int i = JOptionPane.showConfirmDialog(null, arrVastLos, "", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (i == 0) {
				saveNieuweWaarden();
				VulKostprijzenIn();
			}
		}
		if (e.getSource() == getJbtnFiche()) {
			InschrijvingDTO laatsteInschrijving = getStandplaatsService().getLaatsteInschrijving();
			if (laatsteInschrijving != null) {
				PersoonDTO hoofd = null;
				for (InschrijvingPersoonDTO inschrijvingPersoon : laatsteInschrijving.getInschrijvingPersonen()) {
					if (inschrijvingPersoon.getGezinsPositie().equals(GezinsPositieEnum.HOOFD)) {
						hoofd = inschrijvingPersoon.getPersoon();
						break;
					}
				}
				String nummerplaat = "";
				if (hoofd != null) {
					for (WagenDTO wagen : hoofd.getWagens()) {
						if (nummerplaat.length() > 0) {
							nummerplaat += ", ";
						}
						nummerplaat += wagen.getNummerplaat();
					}
				} else {
					for (InschrijvingPersoonDTO inschrijvingPersoon : laatsteInschrijving.getInschrijvingPersonen()) {
						if (inschrijvingPersoon.getGezinsPositie().equals(GezinsPositieEnum.ECHTGENOTE)) {
							hoofd = inschrijvingPersoon.getPersoon();
							break;
						}
					}
				}
				Calendar calVan = Calendar.getInstance();
				calVan.setTime(laatsteInschrijving.getDateVan());
				Calendar calTot = Calendar.getInstance();
				calTot.setTime(laatsteInschrijving.getDateTot());
				long aantalDagen = DateUtilities.getDifference(calVan, calTot, TimeUnit.DAYS);
				// long aantalDagen = DateUtilities.countDaysBetween(laatsteInschrijving.getDateVan(), laatsteInschrijving.getDateTot());
				int iAantalNachten = 0;
				String s = Long.toString(aantalDagen);
				try {
					iAantalNachten = Integer.parseInt(s);
				} catch (Exception ex) {

				}
				Map<String, String> map = getStandplaatsService().getBadgeVoorStandplaats(laatsteInschrijving.getStandplaatsId());
				String badge = "";
				if (laatsteInschrijving.getSoorthuurder().equals(SoortHuurderEnum.LOS)) {
					badge = (laatsteInschrijving.getBadge() == null) ? "" : laatsteInschrijving.getBadge().getBadgenummer();
				} else if (laatsteInschrijving.getSoorthuurder().equals(SoortHuurderEnum.VAST)) {
					if (map.containsKey(Constant.BADGE)) {
						String tmpBadgenummer = map.get(Constant.BADGE);
						if (tmpBadgenummer.length() == 8) {
							badge = tmpBadgenummer.substring(0, 3) + " " + tmpBadgenummer.substring(3, 4) + " "
									+ tmpBadgenummer.substring(4, 6) + " " + tmpBadgenummer.substring(6);
						}
					}
				}
				String standplaats = map.get(Constant.PLAATSGROEP);
				String plaatsnummer = map.get(Constant.PLAATSNUMMER);
				while (plaatsnummer.length() < 3) {
					plaatsnummer = "0" + plaatsnummer;
				}
				standplaats += plaatsnummer;

				getJTxtNaam().setText(hoofd.getNaam() + " " + hoofd.getVoornaam());
				getJTxtStandplaats().setText(standplaats);
				getJTxtAutoplaat().setText(nummerplaat);
				getJTxtBadge().setText(badge);
				getjDpDatumVan().getModel().setDate(calVan.get(Calendar.YEAR), calVan.get(Calendar.MONTH),
						calVan.get(Calendar.DATE));
				getjDpDatumTot().getModel().setDate(calTot.get(Calendar.YEAR), calTot.get(Calendar.MONTH),
						calTot.get(Calendar.DATE));
				getJTxtNachten().setText(Long.toString(aantalDagen));
				getKasbon().setAantalNachten(iAantalNachten);

				berekendTotalen(true, null);
				save();
			}
		}
		if (e.getSource() == getJBtnReset()) {
			String s = getJTxtTotaal().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", "");
			s = s.replace(".", "");
			double d = NumberUtilities.getNumberFromObject(s);
			if (d != 0) {
				save();
				reset();
				getJTxtJeton().requestFocus();
			}
		}
		if (e.getSource() == getJBtnVerwijder()) {
			for (KasbonDTO kasbon : getKasbons()) {
				if (kasbon.getId() == getKasbon().getId()) {
					getKasbons().remove(kasbon);
					break;
				}
			}
			getKasbonService().remove(getKasbon());

			for (int i = 1; i < getJCboKasbon().getModel().getSize(); i++) {
				Row row = (Row) getJCboKasbon().getItemAt(i);
				if (getKasbon().getKasbonnummer() == Integer.parseInt(row.getId())) {
					getJCboKasbon().removeItemAt(i);
					break;
				}
			}
			getJCboKasbon().setSelectedIndex(0);
			if (getKasbons().size() == 0)
				index = 1;
			reset();
		}
		if (e.getSource() == getJCboKasbon()) {
			if (getJCboKasbon().getSelectedIndex() != 0) {
				int index = getJCboKasbon().getSelectedIndex() - 1;
				reset();
				setDatainGui(index);
			}
		}
		if (e.getSource() == getJBtnPrintCash()) {
			getKasbon().setCashSource(CashSourceEnum.CASH);
			save();
			KasbonPdf kasbon = new KasbonPdf();
			String adres = "NV Campings Zwaenepoel Vosseslag 20/5 - 8420 De Haan";
			List<Double> prijzen = new ArrayList<Double>();
			for (KassaKostprijsDTO kostprijs : getKostprijzen()) {
				prijzen.add(kostprijs.getWaarde());
			}
			kasbon.createDocument(getKasbon(), prijzen, adres, getKasbonNames());
		}
		if (e.getSource() == getJBtnPrintPayconic()) {
			getKasbon().setCashSource(CashSourceEnum.PAYCONIC);
			save();
			KasbonPdf kasbon = new KasbonPdf();
			String adres = "NV Campings Zwaenepoel Vosseslag 20/5 - 8420 De Haan";
			List<Double> prijzen = new ArrayList<Double>();
			for (KassaKostprijsDTO kostprijs : getKostprijzen()) {
				prijzen.add(kostprijs.getWaarde());
			}
			kasbon.createDocument(getKasbon(), prijzen, adres, getKasbonNames());
		}
	}

	private String[] getKasbonNames() {
		String[] names = new String[10];
		names[0] = getjNaam1().getText();
		names[1] = getjNaam2().getText();
		names[2] = getjNaam3().getText();
		names[3] = getjNaam4().getText();
		names[4] = getjNaam5().getText();
		names[5] = getjNaam6().getText();
		names[6] = getjNaam7().getText();
		names[7] = getjNaam8().getText();
		names[8] = getjNaam9().getText();
		names[9] = getjNaam10().getText();
		return names;
	}

	private void setDatainGui(int index) {
		KasbonDTO kasbon = getKasbons().get(index);
		setDatainGui(kasbon);
	}

	public void setDatainGui(KasbonDTO kasbon) {
		setKasbon(kasbon);
		vulKasbonIn();
		berekenAlleProducten();
		berekendTotalen(true, kasbon);
	}

	private void berekenAlleProducten() {
		double result = getKasbon().getAuto100() * getKostprijzen().get(0).getWaarde();
		getJTxtKostAuto100().setText("\u20ac " + df.format(result));

		double result2 = getKasbon().getAuto150() * getKostprijzen().get(1).getWaarde();
		getJTxtKostAuto150().setText("\u20ac " + df.format(result2));

		double result3 = getKasbon().getWaarborg() * getKostprijzen().get(2).getWaarde();
		getJTxtKostWaarborg().setText("\u20ac " + df.format(result3));

		double result4 = getKasbon().getJeton() * getKostprijzen().get(3).getWaarde();
		getJTxtKostJeton().setText("\u20ac " + df.format(result4));

		double result5 = getKasbon().getZakGeel() * getKostprijzen().get(4).getWaarde();
		getJTxtKostZakGeel().setText("\u20ac " + df.format(result5));

		double result6 = getKasbon().getZakBlauw() * getKostprijzen().get(5).getWaarde();
		getJTxtKostZakBlauw().setText("\u20ac " + df.format(result6));

		double result7 = getKasbon().getCar() * getKostprijzen().get(6).getWaarde();
		getJTxtKostCar().setText("\u20ac " + df.format(result7));

		double result8 = getKasbon().getTent() * getKostprijzen().get(7).getWaarde();
		getJTxtKostTent().setText("\u20ac " + df.format(result8));

		double result9 = getKasbon().getVolwassenen() * getKostprijzen().get(8).getWaarde();
		getJTxtKostVolwassen().setText("\u20ac " + df.format(result9));

		double result10 = getKasbon().getKinderen() * getKostprijzen().get(9).getWaarde();
		getJTxtKostKinderen().setText("\u20ac " + df.format(result10));

		if (getKasbon().getTelefoon() != 0) {
			getJTxtExtraKosten().setText("\u20ac " + df.format(getKasbon().getTelefoon()));
		} else {
			getJTxtExtraKosten().setText("");
		}
	}

	private void reset() {
		setKasbon(DTOFactory.createKasbon());
		getJTxtAantalNachten().setText("1");
		getJTxtNachten().setText("1");
		getJTxtAuto100().setText("");
		getJTxtAuto150().setText("");
		getJTxtWaarborg().setText("");
		getJTxtJeton().setText("");
		getJTxtZakGeel().setText("");
		getJTxtZakBlauw().setText("");
		getJtxtCar().setText("");
		getJTxtTent().setText("");
		getJTxtVolwassen().setText("");
		getJTxtKinderen().setText("");
		getJTxtExtraKosten().setText("");
		getJTxtTotaal().setText("\u20ac 0,00");
		getJTxtBetaling().setText("");
		getJTxtTerug().setText("\u20ac 0,00");
		getJTxtKostNacht().setText("\u20ac 0,00");
		getJTxtTotaalNachten().setText("\u20ac 0,00");
		getJTxtJetonsZakKost().setText("\u20ac 0,00");
		getJTxtTotZonderWB().setText("\u20ac 0,00");
		getJTxtNaam().setText("");
		getJTxtStandplaats().setText("");
		getJTxtAutoplaat().setText("");
		getJTxtBadge().setText("");
		getjDpDatumVan().clearTextField();
		getjDpDatumTot().clearTextField();
		getJTxtKostAuto100().setText("\u20ac 0,00");
		getJTxtKostAuto150().setText("\u20ac 0,00");
		getJTxtKostWaarborg().setText("\u20ac 0,00");
		getJTxtKostJeton().setText("\u20ac 0,00");
		getJTxtKostZakGeel().setText("\u20ac 0,00");
		getJTxtKostZakBlauw().setText("\u20ac 0,00");
		getJTxtKostCar().setText("\u20ac 0,00");
		getJTxtKostTent().setText("\u20ac 0,00");
		getJTxtKostVolwassen().setText("\u20ac 0,00");
		getJTxtKostKinderen().setText("\u20ac 0,00");
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField jTxtField = (JTextField) e.getSource();
			if (null == defaultBorder) {
				defaultBorder = jTxtField.getBorder();
			}
			jTxtField.setBorder(new LineBorder(Color.blue, 2));
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField jTxtField = (JTextField) e.getSource();
			jTxtField.setBorder(defaultBorder);
		}

		// TODO Auto-generated method stub
		if (e.getSource() == getJTxtNachten()) {
			String s = getJTxtNachten().getText();
			if (!StringUtils.isEmpty(s)) {
				try {
					int i = Integer.parseInt(s);
					getKasbon().setAantalNachten(i);
					getJTxtAantalNachten().setText(getJTxtNachten().getText());
				} catch (Exception ex) {
					System.err.println("error parsing getJTxtNachten to int");
				}
			}
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtAuto100()) {
			double factor = getKostprijzen().get(0).getWaarde();
			int waarde = 0;
			String s = getJTxtAuto100().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtAuto100().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from Auto 100: " + getJTxtAuto100().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setAuto100(waarde);
			getJTxtKostAuto100().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtAuto150()) {
			double factor = getKostprijzen().get(1).getWaarde();
			int waarde = 0;
			String s = getJTxtAuto150().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtAuto150().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from Auto 150: " + getJTxtAuto150().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setAuto150(waarde);
			getJTxtKostAuto150().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtWaarborg()) {
			double factor = getKostprijzen().get(2).getWaarde();
			int waarde = 0;
			String s = getJTxtWaarborg().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtWaarborg().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from waarborg: " + getJTxtWaarborg().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setWaarborg(waarde);
			getJTxtKostWaarborg().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtJeton()) {
			double factor = getKostprijzen().get(3).getWaarde();
			int waarde = 0;
			String s = getJTxtJeton().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtJeton().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from jeton: " + getJTxtJeton().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setJeton(waarde);
			getJTxtKostJeton().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtZakGeel()) {
			double factor = getKostprijzen().get(4).getWaarde();
			int waarde = 0;
			String s = getJTxtZakGeel().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtZakGeel().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from zak geel: " + getJTxtZakGeel().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setZakGeel(waarde);
			getJTxtKostZakGeel().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtZakBlauw()) {
			double factor = getKostprijzen().get(5).getWaarde();
			int waarde = 0;
			String s = getJTxtZakBlauw().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtZakBlauw().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from zak blauw: " + getJTxtZakGeel().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setZakBlauw(waarde);
			getJTxtKostZakBlauw().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJtxtCar()) {
			double factor = getKostprijzen().get(6).getWaarde();
			int waarde = 0;
			String s = getJtxtCar().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJtxtCar().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from car: " + getJtxtCar().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setCar(waarde);
			getJTxtKostCar().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtTent()) {
			double factor = getKostprijzen().get(7).getWaarde();
			int waarde = 0;
			String s = getJTxtTent().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtTent().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from tent: " + getJTxtTent().getText());
				}
			}
			double result = waarde * factor;
			getKasbon().setTent(waarde);
			getJTxtKostTent().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtVolwassen()) {
			double factor = getKostprijzen().get(8).getWaarde();
			int waarde = 0;
			String s = getJTxtVolwassen().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtVolwassen().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from volwassen: " + getJTxtVolwassen());
				}
			}
			double result = waarde * factor;
			getKasbon().setVolwassenen(waarde);
			getJTxtKostVolwassen().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtKinderen()) {
			double factor = getKostprijzen().get(9).getWaarde();
			int waarde = 0;
			String s = getJTxtKinderen().getText();
			s = s.replace("\u20ac", "");
			s = s.trim();
			s = s.replace(",", ".");
			if (!StringUtils.isEmpty(s)) {
				try {
					waarde = Integer.parseInt(getJTxtKinderen().getText());
				} catch (Exception ex) {
					System.err.println("error parsing int from kinderen: " + getJTxtKinderen());
				}
			}
			double result = waarde * factor;
			getKasbon().setKinderen(waarde);
			getJTxtKostKinderen().setText("\u20ac " + df.format(result));
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtExtraKosten()) {
			String s = getJTxtExtraKosten().getText();
			if (!StringUtils.isEmpty(s)) {
				s = s.replace("\u20ac", "");
				s = s.trim();
				s = s.replace(",", ".");
				try {
					double d = Double.parseDouble(s);
					getJTxtExtraKosten().setText("\u20ac " + df.format(d));
					getKasbon().setTelefoon(d);
				} catch (Exception ex) {
					System.err.println("error parsing double from extra kosten - telefoon: "
							+ getJTxtExtraKosten().getText());
				}
			}
			berekendTotalen(true, null);
		}
		if (e.getSource() == getJTxtBetaling()) {
			String s = getJTxtBetaling().getText();
			if (!StringUtils.isEmpty(s)) {
				try {
					s = s.replace("\u20ac", "");
					s = s.replace(",", ".");
					s = s.trim();
					double waarde = Double.parseDouble(s);
					double totaal = berekendTotalen(false, null);
					double terug = waarde - totaal;
					getJTxtBetaling().setText("\u20ac " + df.format(waarde));
					getJTxtTerug().setText("\u20ac " + df.format(terug));
					getKasbon().setBetaling(waarde);
					getKasbon().setTerug(terug);
					if (terug < 0) {
						Object[] arr = { "Deze klant heeft te weinig betaald!" };
						JOptionPane.showConfirmDialog(null, arr, "", JOptionPane.DEFAULT_OPTION,
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception ex) {
					System.err.println("error parsing int from betaling: " + getJTxtBetaling());
				}
			}
		}
	}

	private double berekenJetonsZakKost(KasbonDTO kasbon) {
		if (kasbon == null) {
			kasbon = getKasbon();
		}
		double totaal = kasbon.getJeton() * getKostprijzen().get(3).getWaarde();
		totaal += kasbon.getZakGeel() * getKostprijzen().get(4).getWaarde();
		totaal += kasbon.getZakBlauw() * getKostprijzen().get(5).getWaarde();
		totaal += kasbon.getTelefoon();
		return totaal;
	}

	private double berekenKostPerNacht(KasbonDTO kasbon) {
		if (kasbon == null) {
			kasbon = getKasbon();
		}
		double totaal = kasbon.getAuto100() * getKostprijzen().get(0).getWaarde();
		totaal += kasbon.getAuto150() * getKostprijzen().get(1).getWaarde();
		totaal += kasbon.getCar() * getKostprijzen().get(6).getWaarde();
		totaal += kasbon.getTent() * getKostprijzen().get(7).getWaarde();
		totaal += kasbon.getVolwassenen() * getKostprijzen().get(8).getWaarde();
		totaal += kasbon.getKinderen() * getKostprijzen().get(9).getWaarde();
		return totaal;
	}

	private double berekendTotalen(boolean updateTextfields, KasbonDTO kasbon) {
		if (kasbon == null) {
			kasbon = getKasbon();
		}
		double zakJetonsKost = berekenJetonsZakKost(kasbon);
		double kostPerNacht = berekenKostPerNacht(kasbon);
		int aantalNachten = kasbon.getAantalNachten();
		double totaalNachten = kostPerNacht * aantalNachten;
		double totaalZonderWb = totaalNachten + zakJetonsKost;

		double waarborg = kasbon.getWaarborg() * getKostprijzen().get(2).getWaarde();
		double totaal = totaalZonderWb + waarborg;
		if (updateTextfields) {
			String betaling = getJTxtBetaling().getText();
			double terug = 0;
			if (!StringUtils.isEmpty(betaling)) {
				betaling = betaling.replace("\u20ac", "");
				betaling = betaling.replace(",", ".");
				betaling = betaling.replace(" ", "");
				double betaald = Double.parseDouble(betaling);
				terug = betaald - totaal;
				if (terug < 0)
					terug = 0;
			}
			getJTxtJetonsZakKost().setText("\u20ac " + df.format(zakJetonsKost));
			getJTxtKostNacht().setText("\u20ac " + df.format(kostPerNacht));
			getJTxtTotaalNachten().setText("\u20ac " + df.format(totaalNachten));
			getJTxtTotZonderWB().setText("\u20ac " + df.format(totaalZonderWb));
			getJTxtTotaal().setText("\u20ac " + df.format(totaal));
			getJTxtAantalNachten().setText(getJTxtNachten().getText());
			getJTxtTerug().setText("\u20ac " + df.format(terug));
		}
		return totaal;
	}

	/**
	 * This method initializes jBtnOpslaanKasbon
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnOpslaanKasbon() {
		if (jBtnOpslaanKasbon == null) {
			jBtnOpslaanKasbon = new JButton("Opslaan");
			jBtnOpslaanKasbon.setFont(font);
			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new OpslaanActionListener("Opslaan");
			InputMap inputMap = jBtnOpslaanKasbon.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(stroke, actionKey);
			ActionMap actionMap = jBtnOpslaanKasbon.getActionMap();
			actionMap.put(actionKey, action);
			jBtnOpslaanKasbon.addActionListener(this);
		}
		return jBtnOpslaanKasbon;
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
			getJBtnOpslaanKasbon().doClick();
		}
	}

	private static class Row {

		private final String id;
		private String tijd;
		private String naam;
		private String plaats;
		private String van;
		private String tot;
		private String totaal;

		public Row(String id, String tijd, String naam, String plaats, String van, String tot, String totaal) {
			this.id = id;
			this.tijd = tijd;
			this.naam = naam;
			this.plaats = plaats;
			this.van = van;
			this.tot = tot;
			this.totaal = totaal;
		}

		public String getId() {
			return id;
		}

		public String getTijd() {
			return tijd;
		}

		public String getNaam() {
			return naam;
		}

		public String getPlaats() {
			return plaats;
		}

		public String getVan() {
			return van;
		}

		public String getTot() {
			return tot;
		}

		public String getTotaal() {
			return totaal;
		}

		public void setTijd(String tijd) {
			this.tijd = tijd;
		}

		public void setNaam(String naam) {
			this.naam = naam;
		}

		public void setPlaats(String plaats) {
			this.plaats = plaats;
		}

		public void setVan(String van) {
			this.van = van;
		}

		public void setTot(String tot) {
			this.tot = tot;
		}

		public void setTotaal(String totaal) {
			this.totaal = totaal;
		}

	}

	private static class RowCellRenderer extends JTable implements ListCellRenderer {

		private static final long serialVersionUID = 4663174015585521195L;

		public RowCellRenderer() {
			setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			setGridColor(Color.GRAY);
			setSize(super.getSize());
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			setModel(new RowTableModel((Row) value));
			if (isSelected) {
				getSelectionModel().setSelectionInterval(0, 0);
			}
			return this;
		}
	}

	private static class RowTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 6466250493479185679L;
		private final Row row;

		public RowTableModel(Row row) {
			this.row = row;
		}

		@Override
		public int getRowCount() {
			return 1;
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return row.getId();
			case 1:
				return row.getTijd();
			case 2:
				return row.getNaam();
			case 3:
				return row.getPlaats();
			case 4:
				return row.getVan();
			case 5:
				return row.getTot();
			case 6:
				return row.getTotaal();
			}
			return null;
		}
	}

}
