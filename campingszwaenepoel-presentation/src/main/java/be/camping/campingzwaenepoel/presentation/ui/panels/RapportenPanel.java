package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.PathConstant;
import be.camping.campingzwaenepoel.common.enums.BetalingRapportEnum;
import be.camping.campingzwaenepoel.common.enums.TaalEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.dialog.PrijsAanpassingenDialog;
import be.camping.campingzwaenepoel.presentation.dialog.directorychooser.DirectoryChooser;
import be.camping.campingzwaenepoel.presentation.dialog.fotochooser.FotoChooser;
import be.camping.campingzwaenepoel.presentation.dialog.persoon.ChangeAndRemovePersoonDialog;
import be.camping.campingzwaenepoel.presentation.listeners.RequestFocusListener;
import be.camping.campingzwaenepoel.presentation.pdf.*;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.*;
import be.camping.campingzwaenepoel.service.foto.FotoService;
import be.camping.campingzwaenepoel.service.threads.betaling.PrijsAanpassingBusyThread;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class RapportenPanel extends JPanel implements PanelInterface, ActionListener {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RapportenPanel.class);

	private static final String GEMEENTELIJKE_BELASTING = "gemeentelijke_belasting";

	private Controller controller;

	@Autowired
	private StandplaatsService standplaatsService;
	@Autowired
	private PersoonService persoonService;
	@Autowired
	private StandplaatsDetailService standplaatsDetailService;
	@Autowired
	private KasbonService kasbonService;
	@Autowired
	private KassaService kassaService;
	@Autowired
	private FotoService fotoService;
	@Autowired
	private ConfiguratieService configuratieService;
	@Autowired
	private FactuurDetailService factuurDetailService;

	private DirectoryChooser directoryChooser;
	private FotoChooser fotoChooser;

	private ConfiguratieDTO configuratieDTO;

	private JButton saveButton;
	private JPanel jPanelFileDirectory;

	private JButton jBtnNummerplaten;
	private JButton jBtnKasbon;
	private JButton jBtnKassa;
	private JButton jBtnOpmGrond;
	private JButton jBtnBetalingen;
	private JButton jBtnNieuwePrijzen;
	private JButton jBtnFactuurAanmaak;
	private JButton jBtnConvertTaalEnReglGetekendDatum;
	private JButton jBtnConvertInschrijving;
	private JButton jBtnConvertFacturatie;
	private JButton jBtnConvertBetalers;
	private JButton jBtnConvertBetalingen;
	private JButton jBtnBerekenFacturatie;
	private JButton jBtnPrintFotosVoorFacturatie;
	private JButton jBtnChangePersoon;
	private JButton jBtnUpdateNieuwePrijzen;
	private JButton jBtnUpdateBasisPrijzen;

	private static final String CHANGE_PERSOON = "changePersoon";
	private static final String SOURCE_PERSON = "source";
	private static final String TARGET_PERSON = "target";

	private final Dimension btnDim = new Dimension(300, 28);
	Font fontButton = new Font("Times New Roman", Font.BOLD, 24);

	public void initComponents() {
		initButtons();
		initUIComponents();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbcNummerplaat = new GridBagConstraints();
		gbcNummerplaat.gridx = 0;
		gbcNummerplaat.gridy = 0;
		gbcNummerplaat.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcNieuwePrijzen = new GridBagConstraints();
		gbcNieuwePrijzen.gridx = 3;
		gbcNieuwePrijzen.gridy = 0;
		gbcNieuwePrijzen.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcFactuurAanmaak = new GridBagConstraints();
		gbcFactuurAanmaak.gridx = 3;
		gbcFactuurAanmaak.gridy = 1;
		gbcFactuurAanmaak.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcConvTaalRegl = new GridBagConstraints();
		gbcConvTaalRegl.gridx = 3;
		gbcConvTaalRegl.gridy = 2;
		gbcConvTaalRegl.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcConvInschrijving = new GridBagConstraints();
		gbcConvInschrijving.gridx = 3;
		gbcConvInschrijving.gridy = 3;
		gbcConvInschrijving.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcChangepersoon = new GridBagConstraints();
		gbcChangepersoon.gridx = 3;
		gbcChangepersoon.gridy = 4;
		gbcChangepersoon.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcUpdateNieuwePrijzen = new GridBagConstraints();
		gbcUpdateNieuwePrijzen.gridx = 3;
		gbcUpdateNieuwePrijzen.gridy = 5;
		gbcUpdateNieuwePrijzen.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcKasbon = new GridBagConstraints();
		gbcKasbon.gridx = 0;
		gbcKasbon.gridy = 1;
		gbcKasbon.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcKassa = new GridBagConstraints();
		gbcKassa.gridx = 0;
		gbcKassa.gridy = 2;
		gbcKassa.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcOpmGrond = new GridBagConstraints();
		gbcOpmGrond.gridx = 0;
		gbcOpmGrond.gridy = 3;
		gbcOpmGrond.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcBetalingen = new GridBagConstraints();
		gbcBetalingen.gridx = 0;
		gbcBetalingen.gridy = 4;
		gbcBetalingen.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcFotosFacturatie = new GridBagConstraints();
		gbcFotosFacturatie.gridx = 0;
		gbcFotosFacturatie.gridy = 5;
		gbcFotosFacturatie.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcFileDirectory = new GridBagConstraints();
		gbcFileDirectory.gridx = 0;
		gbcFileDirectory.gridy = 6;
		gbcFileDirectory.gridwidth = 4;
		gbcFileDirectory.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcFotoChooser = new GridBagConstraints();
		gbcFotoChooser.gridx = 0;
		gbcFotoChooser.gridy = 7;
		gbcFotoChooser.gridwidth = 4;
		gbcFotoChooser.anchor = GridBagConstraints.WEST;
		gbcFotoChooser.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcConvFacturatie = new GridBagConstraints();
		gbcConvFacturatie.gridx = 3;
		gbcConvFacturatie.gridy = 4;
		gbcConvFacturatie.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel1 = new GridBagConstraints();
		gbcLabel1.gridx = 1;
		gbcLabel1.gridy = 0;
		gbcLabel1.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel2 = new GridBagConstraints();
		gbcLabel2.gridx = 2;
		gbcLabel2.gridy = 0;
		gbcLabel2.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel3 = new GridBagConstraints();
		gbcLabel3.gridx = 1;
		gbcLabel3.gridy = 1;
		gbcLabel3.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel4 = new GridBagConstraints();
		gbcLabel4.gridx = 2;
		gbcLabel4.gridy = 1;
		gbcLabel4.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel5 = new GridBagConstraints();
		gbcLabel5.gridx = 1;
		gbcLabel5.gridy = 2;
		gbcLabel5.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel6 = new GridBagConstraints();
		gbcLabel6.gridx = 2;
		gbcLabel6.gridy = 2;
		gbcLabel6.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel7 = new GridBagConstraints();
		gbcLabel7.gridx = 1;
		gbcLabel7.gridy = 3;
		gbcLabel7.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints gbcLabel8 = new GridBagConstraints();
		gbcLabel8.gridx = 2;
		gbcLabel8.gridy = 3;
		gbcLabel8.fill = GridBagConstraints.HORIZONTAL;
		add(jBtnNummerplaten, gbcNummerplaat);
		JLabel l1 = new JLabel("                                            ");
		JLabel l2 = new JLabel("                                   			");
		JLabel l3 = new JLabel("                                       		");
		JLabel l4 = new JLabel("                                   	    	");
		JLabel l5 = new JLabel("                                            ");
		JLabel l6 = new JLabel("                                  			");
		JLabel l7 = new JLabel("                                            ");
		// JLabel l8 = new
		// JLabel(" ");
		Dimension dim = new Dimension(400, 30);
		l1.setSize(dim);
		l2.setSize(dim);
		l3.setSize(dim);
		l4.setSize(dim);
		add(l1, gbcLabel1);
		add(l2, gbcLabel2);
		add(l3, gbcLabel3);
		add(l4, gbcLabel4);
		add(l5, gbcLabel5);
		add(l6, gbcLabel6);
		add(l7, gbcLabel7);
		add(jBtnBerekenFacturatie, gbcLabel7);
		add(jBtnConvertBetalers, gbcLabel8);
		add(jBtnKasbon, gbcKasbon);
		add(jBtnKassa, gbcKassa);
		add(jBtnOpmGrond, gbcOpmGrond);
		add(jBtnBetalingen, gbcBetalingen);
		add(jBtnPrintFotosVoorFacturatie, gbcFotosFacturatie);
		add(jPanelFileDirectory, gbcFileDirectory);
		add(jBtnNieuwePrijzen, gbcNieuwePrijzen);
		add(jBtnUpdateNieuwePrijzen, gbcNieuwePrijzen);
		// add(getjBtnUpdateNieuwePrijzen(), gbcUpdateNieuwePrijzen);
		add(jBtnUpdateBasisPrijzen, gbcUpdateNieuwePrijzen);
		add(jBtnFactuurAanmaak, gbcFactuurAanmaak);
		add(jBtnConvertTaalEnReglGetekendDatum, gbcConvTaalRegl);
		add(jBtnConvertInschrijving, gbcConvInschrijving);
		add(jBtnConvertFacturatie, gbcConvFacturatie);
		add(fotoChooser, gbcFotoChooser);
		this.setBorder(new LineBorder(Color.BLACK, 2));

		// changePersons();
	}

	private void initButtons() {
		initBtnNummerplaten();
		initBtnKasbon();
		initBtnKassa();
		initBtnOpmGrond();
		initBtnBetalingen();
		initBtnNieuwePrijzen();
		initBtnUpdateNieuwePrijzen();
		initBtnUpdateBasisPrijzen();
		initBtnFactuurAanmaak();
		initBtnConvertInschrijving();
		initBtnConvertFacturatie();
		initBtnConvertBetalers();
		initBtnConvertBetalingen();
		initBtnBerekenFacturatie();
		initBtnPrintFotosVoorFacturatie();
		initBtnChangePersoon();
		initBtnConvertTaalEnReglGetekendDatum();
		initSaveButton();
	}

	public void initBtnNummerplaten() {
		jBtnNummerplaten = new JButton("Nummerplaten");
		jBtnNummerplaten.setPreferredSize(btnDim);
		jBtnNummerplaten.setFont(fontButton);
		jBtnNummerplaten.addActionListener(this);
	}

	public void initBtnKasbon() {
		jBtnKasbon = new JButton("Kasbon");
		jBtnKasbon.setPreferredSize(btnDim);
		jBtnKasbon.setFont(fontButton);
		jBtnKasbon.addActionListener(this);
	}

	public void initBtnKassa() {
		jBtnKassa = new JButton("Afrekening Kassa");
		jBtnKassa.setPreferredSize(btnDim);
		jBtnKassa.setFont(fontButton);
		jBtnKassa.addActionListener(this);
	}

	public void initBtnOpmGrond() {
		jBtnOpmGrond = new JButton("Opmerking Grond");
		jBtnOpmGrond.setPreferredSize(btnDim);
		jBtnOpmGrond.setFont(fontButton);
		jBtnOpmGrond.addActionListener(this);
	}

	public void initBtnBetalingen() {
		jBtnBetalingen = new JButton("Betalingen");
		jBtnBetalingen.setPreferredSize(btnDim);
		jBtnBetalingen.setFont(fontButton);
		jBtnBetalingen.addActionListener(this);
	}

	public void initBtnNieuwePrijzen() {
		jBtnNieuwePrijzen = new JButton("Nieuwe prijzen");
		jBtnNieuwePrijzen.setForeground(Color.RED);
		jBtnNieuwePrijzen.setPreferredSize(btnDim);
		jBtnNieuwePrijzen.setFont(fontButton);
		jBtnNieuwePrijzen.addActionListener(this);
	}

	public void initBtnUpdateNieuwePrijzen() {
		jBtnUpdateNieuwePrijzen = new JButton("Update Nieuwe prijzen");
		jBtnUpdateNieuwePrijzen.setForeground(Color.RED);
		jBtnUpdateNieuwePrijzen.setPreferredSize(btnDim);
		jBtnUpdateNieuwePrijzen.setFont(fontButton);
		jBtnUpdateNieuwePrijzen.addActionListener(this);
		jBtnUpdateNieuwePrijzen.setVisible(false);
	}

	public void initBtnUpdateBasisPrijzen() {
		jBtnUpdateBasisPrijzen = new JButton("Update Basis prijs");
		jBtnUpdateBasisPrijzen.setForeground(Color.RED);
		jBtnUpdateBasisPrijzen.setPreferredSize(btnDim);
		jBtnUpdateBasisPrijzen.setFont(fontButton);
		jBtnUpdateBasisPrijzen.addActionListener(this);
		jBtnUpdateBasisPrijzen.setVisible(false);
	}

	public void initBtnFactuurAanmaak() {
		jBtnFactuurAanmaak = new JButton("Aanmaak facturen");
		jBtnFactuurAanmaak.setForeground(Color.RED);
		jBtnFactuurAanmaak.setPreferredSize(btnDim);
		jBtnFactuurAanmaak.setFont(fontButton);
		jBtnFactuurAanmaak.addActionListener(this);
		jBtnFactuurAanmaak.setEnabled(false);
	}

	public void initBtnConvertInschrijving() {
		jBtnConvertInschrijving = new JButton("Conv Inschrijvingen");
		jBtnConvertInschrijving.addActionListener(this);
		jBtnConvertInschrijving.setVisible(false);
	}

	public void initBtnConvertFacturatie() {
		jBtnConvertFacturatie = new JButton("Conv Facturatie");
		jBtnConvertFacturatie.addActionListener(this);
		jBtnConvertFacturatie.setVisible(false);
	}

	public void initBtnConvertBetalers() {
		jBtnConvertBetalers = new JButton("Conv Betalers");
		jBtnConvertBetalers.addActionListener(this);
		jBtnConvertBetalers.setVisible(false);
	}

	public void initBtnConvertBetalingen() {
		jBtnConvertBetalingen = new JButton("Conv Betalingen");
		jBtnConvertBetalingen.addActionListener(this);
		jBtnConvertBetalers.setVisible(false);
	}

	public void initBtnBerekenFacturatie() {
		jBtnBerekenFacturatie = new JButton("bereken facturatie");
		jBtnBerekenFacturatie.addActionListener(this);
		jBtnBerekenFacturatie.setVisible(false);
	}

	public void initBtnPrintFotosVoorFacturatie() {
		jBtnPrintFotosVoorFacturatie = new JButton("Foto's facturatie");
		jBtnPrintFotosVoorFacturatie.setPreferredSize(btnDim);
		jBtnPrintFotosVoorFacturatie.setFont(fontButton);
		jBtnPrintFotosVoorFacturatie.addActionListener(this);
	}

	public void initBtnChangePersoon() {
		jBtnChangePersoon = new JButton("persoon aanpassen");
		jBtnChangePersoon.setSize(btnDim);
		jBtnChangePersoon.setFont(fontButton);
		jBtnChangePersoon.addActionListener(this);
	}

	public void initBtnConvertTaalEnReglGetekendDatum() {
		jBtnConvertTaalEnReglGetekendDatum = new JButton();
		jBtnConvertTaalEnReglGetekendDatum.setText("Conv taal en Regl get");
		jBtnConvertTaalEnReglGetekendDatum.addActionListener(this);
		jBtnConvertTaalEnReglGetekendDatum.setVisible(false);
	}

	public void initSaveButton() {
		saveButton = new JButton("Opslaan");
		saveButton.addActionListener(new saveAction());
	}

	private void initUIComponents() {
		directoryChooser = new DirectoryChooser();
		initPanelFileDirectory();
	}

	public void setDirectoryChooser(DirectoryChooser directoryChooser) {
		this.directoryChooser = directoryChooser;
		directoryChooser.getLog().setEditable(false);
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private void changePersons() {
		ConfiguratieDTO campingConfigDTO = configuratieService.getConfiguratie("camping_naam");
		if (!"lispanne".equalsIgnoreCase(campingConfigDTO.getWaarde())) {
			return;
		}
		ConfiguratieDTO configDTO = configuratieService.getConfiguratie(CHANGE_PERSOON);
		if (null == configDTO) {
			configDTO = new ConfiguratieDTO();
			configDTO.setNaam(CHANGE_PERSOON);
			configDTO.setWaarde("notdone");
			configuratieService.storeConfiguratie(configDTO);
		}
		if (!"done".equals(configDTO.getWaarde())) {
			List<Map<String, Integer>> persons = fillList();
			for (Map<String, Integer> map : persons) {
				PersoonDTO sourcePersoon = persoonService.findPersoonById(map.get(SOURCE_PERSON));
				PersoonDTO targetPersoon = persoonService.findPersoonById(map.get(TARGET_PERSON));
				if (null != sourcePersoon && null != targetPersoon) {
					persoonService.changePersoon(map.get(TARGET_PERSON), map.get(SOURCE_PERSON));
				}
			}

			configDTO.setWaarde("done");
			configuratieService.storeConfiguratie(configDTO);
		}
	}

	private List<Map<String, Integer>> fillList() {
		List<Integer> sourcePersons = new ArrayList<Integer>();
		List<Integer> targetPersons = new ArrayList<Integer>();

		sourcePersons.add(6327);
		targetPersons.add(25917);

		sourcePersons.add(15303);
		targetPersons.add(10301);

		sourcePersons.add(35503);
		targetPersons.add(36571);

		sourcePersons.add(13022);
		targetPersons.add(13019);

		sourcePersons.add(16973);
		targetPersons.add(20754);

		sourcePersons.add(34663);
		targetPersons.add(25677);

		sourcePersons.add(37440);
		targetPersons.add(25677);

		sourcePersons.add(6049);
		targetPersons.add(37075);

		sourcePersons.add(8476);
		targetPersons.add(37075);

		sourcePersons.add(6049);
		targetPersons.add(37076);

		sourcePersons.add(8476);
		targetPersons.add(37076);

		sourcePersons.add(8477);
		targetPersons.add(37076);

		sourcePersons.add(24573);
		targetPersons.add(37076);

		sourcePersons.add(156);
		targetPersons.add(24570);

		sourcePersons.add(5144);
		targetPersons.add(28341);

		sourcePersons.add(29466);
		targetPersons.add(26937);

		sourcePersons.add(36885);
		targetPersons.add(178);

		sourcePersons.add(36890);
		targetPersons.add(178);

		sourcePersons.add(18121);
		targetPersons.add(30545);

		sourcePersons.add(20372);
		targetPersons.add(28031);

		sourcePersons.add(36727);
		targetPersons.add(35491);

		sourcePersons.add(30811);
		targetPersons.add(34269);

		sourcePersons.add(290);
		targetPersons.add(19970);

		sourcePersons.add(34856);
		targetPersons.add(31138);

		sourcePersons.add(291);
		targetPersons.add(24048);

		sourcePersons.add(29441);
		targetPersons.add(20612);

		sourcePersons.add(10647);
		targetPersons.add(10651);

		sourcePersons.add(5142);
		targetPersons.add(5334);

		sourcePersons.add(28617);
		targetPersons.add(28870);

		sourcePersons.add(31724);
		targetPersons.add(31714);

		sourcePersons.add(19089);
		targetPersons.add(12832);

		sourcePersons.add(8541);
		targetPersons.add(14390);

		sourcePersons.add(29625);
		targetPersons.add(5431);

		sourcePersons.add(11776);
		targetPersons.add(36484);

		sourcePersons.add(19823);
		targetPersons.add(36527);

		sourcePersons.add(471);
		targetPersons.add(22972);

		sourcePersons.add(8299);
		targetPersons.add(12461);

		sourcePersons.add(5359);
		targetPersons.add(27892);

		sourcePersons.add(22867);
		targetPersons.add(18300);

		sourcePersons.add(504);
		targetPersons.add(20261);

		sourcePersons.add(36080);
		targetPersons.add(36086);

		sourcePersons.add(36824);
		targetPersons.add(34422);

		sourcePersons.add(21622);
		targetPersons.add(36071);

		sourcePersons.add(31865);
		targetPersons.add(35809);

		sourcePersons.add(527);
		targetPersons.add(35950);

		sourcePersons.add(23935);
		targetPersons.add(35950);

		sourcePersons.add(12848);
		targetPersons.add(16035);

		sourcePersons.add(535);
		targetPersons.add(33590);

		sourcePersons.add(18953);
		targetPersons.add(32973);

		sourcePersons.add(6211);
		targetPersons.add(5636);

		sourcePersons.add(6209);
		targetPersons.add(5634);

		sourcePersons.add(557);
		targetPersons.add(35682);

		sourcePersons.add(11558);
		targetPersons.add(36109);

		sourcePersons.add(7207);
		targetPersons.add(29010);

		sourcePersons.add(5886);
		targetPersons.add(35606);

		sourcePersons.add(581);
		targetPersons.add(33412);

		sourcePersons.add(31728);
		targetPersons.add(25905);

		sourcePersons.add(7241);
		targetPersons.add(35895);

		sourcePersons.add(6742);
		targetPersons.add(600);

		sourcePersons.add(10867);
		targetPersons.add(33088);

		sourcePersons.add(642);
		targetPersons.add(36294);

		sourcePersons.add(701);
		targetPersons.add(11930);

		sourcePersons.add(33706);
		targetPersons.add(21296);

		sourcePersons.add(37057);
		targetPersons.add(22763);

		sourcePersons.add(757);
		targetPersons.add(21316);

		sourcePersons.add(26153);
		targetPersons.add(27939);

		sourcePersons.add(25096);
		targetPersons.add(23573);

		sourcePersons.add(8363);
		targetPersons.add(22619);

		sourcePersons.add(37096);
		targetPersons.add(31112);

		sourcePersons.add(5681);
		targetPersons.add(28093);

		sourcePersons.add(20413);
		targetPersons.add(1040);

		sourcePersons.add(33695);
		targetPersons.add(33698);

		sourcePersons.add(11660);
		targetPersons.add(16386);

		sourcePersons.add(28364);
		targetPersons.add(37539);

		sourcePersons.add(31696);
		targetPersons.add(32175);

		sourcePersons.add(1334);
		targetPersons.add(10644);

		sourcePersons.add(35517);
		targetPersons.add(1359);

		sourcePersons.add(1360);
		targetPersons.add(802);

		sourcePersons.add(7269);
		targetPersons.add(8550);

		sourcePersons.add(908);
		targetPersons.add(35588);

		sourcePersons.add(914);
		targetPersons.add(29510);

		sourcePersons.add(926);
		targetPersons.add(31873);

		sourcePersons.add(1028);
		targetPersons.add(12999);

		sourcePersons.add(26112);
		targetPersons.add(22995);

		sourcePersons.add(19967);
		targetPersons.add(23874);

		sourcePersons.add(10237);
		targetPersons.add(24972);

		sourcePersons.add(10238);
		targetPersons.add(24974);

		sourcePersons.add(5264);
		targetPersons.add(5396);

		sourcePersons.add(23002);
		targetPersons.add(35572);

		sourcePersons.add(33205);
		targetPersons.add(35573);

		sourcePersons.add(5704);
		targetPersons.add(19196);

		sourcePersons.add(814);
		targetPersons.add(23492);

		sourcePersons.add(818);
		targetPersons.add(8065);

		sourcePersons.add(5681);
		targetPersons.add(28093);

		sourcePersons.add(5392);
		targetPersons.add(25221);

		sourcePersons.add(1077);
		targetPersons.add(21697);

		sourcePersons.add(15019);
		targetPersons.add(35943);

		sourcePersons.add(8062);
		targetPersons.add(21158);

		sourcePersons.add(21698);
		targetPersons.add(21158);

		sourcePersons.add(29845);
		targetPersons.add(35942);

		sourcePersons.add(5115);
		targetPersons.add(20155);

		List<Map<String, Integer>> persons = new ArrayList<Map<String, Integer>>();

		for (int i = 0; i < sourcePersons.size(); i++) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(SOURCE_PERSON, sourcePersons.get(i));
			map.put(TARGET_PERSON, targetPersons.get(i));
			persons.add(map);
		}

		return persons;
	}

	public ConfiguratieDTO getConfiguratieDTO() {
		if (configuratieDTO == null) {
			configuratieDTO = configuratieService.getFileDirectory();
			if (configuratieDTO == null) {
				configuratieDTO = new ConfiguratieDTO();
				configuratieDTO.setNaam(PathConstant.getFotoDirectoryPath());
			} else if (StringUtils.isEmpty(configuratieDTO.getNaam())) {
				configuratieDTO.setNaam(PathConstant.getFotoDirectoryPath());
			}
		}
		return configuratieDTO;
	}

	public void setConfiguratieDTO(ConfiguratieDTO configuratieDTO) {
		this.configuratieDTO = configuratieDTO;
	}

	public void setFotoChooser(FotoChooser fotoChooser) {
		this.fotoChooser = fotoChooser;
	}

	public class saveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (StringUtils.isEmpty(directoryChooser.getLog().getText())) {
				JOptionPane.showMessageDialog(null, "Gelieve een map te kiezen voor de opslag van uw foto's",
						"Geen map gekozen", 1);
			} else {
				if (directoryChooser.getDirectory().exists() && directoryChooser.getDirectory().isDirectory()) {
					save();
				} else {
					JOptionPane.showMessageDialog(null, "Het gekozen pad is geen map", "Geen geldig pad gekozen", 1);
				}
			}

		}

	}

	public void initPanelFileDirectory() {
		jPanelFileDirectory = new JPanel();
		jPanelFileDirectory.setLayout(new BorderLayout());
		jPanelFileDirectory.add(directoryChooser, BorderLayout.CENTER);
		jPanelFileDirectory.add(saveButton, BorderLayout.EAST);
		jPanelFileDirectory.setBorder(new LineBorder(Color.BLACK, 2));
		jPanelFileDirectory.setSize(new Dimension(30, 700));
	}

	@Override
	public boolean checkData() {
		boolean ret = false;
		if (!StringUtils.isEmpty(directoryChooser.getLog().getText())) {
			ret = true;
		}
		return ret;
	}

	@Override
	public boolean checkDataChanged() {
		boolean ret = true;
		ConfiguratieDTO confDTO = getConfiguratieDTO();
		String waardeDTO = "";
		if (confDTO != null && confDTO.getWaarde() != null) {
			waardeDTO = confDTO.getWaarde();
		}
		if (waardeDTO.equals(directoryChooser.getLog().getText())) {
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean checkDataForParent() {
		boolean ret = false;
		ConfiguratieDTO confDTO = getConfiguratieDTO();
		if (confDTO != null && confDTO.getWaarde() != null) {
			ret = true;
		}
		return ret;
	}

	@Override
	public Object getDataFromGUI() {
		configuratieDTO.setWaarde(directoryChooser.getDirectory().toString());
		return null;
	}

	@Override
	public void save() {
		getDataFromGUI();
		configuratieService.storeConfiguratie(configuratieDTO);
		fotoChooser.setOutputDir(configuratieDTO.getWaarde());
	}

	@Override
	public void setDataInGUI() {
		ConfiguratieDTO confDTO = getConfiguratieDTO();
		if (confDTO != null && confDTO.getWaarde() != null) {
			directoryChooser.getLog().setText(configuratieDTO.getWaarde());
			fotoChooser.setOutputDir(configuratieDTO.getWaarde());
		}
		fotoChooser.setList((ArrayList<String>) standplaatsService.getStandplaatsNamen());
	}

	@Override
	public void setRemarksInGui() {
		boolean b = checkData();
		controller.updateDataForPanel(b, 9);
	}

	public class PrintHandler implements ActionListener {

		private final JTable jTable;

		public PrintHandler(JTable jTable) {
			this.jTable = jTable;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				jTable.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks the passed-in array against the correct password. After this method
	 * returns, you should invoke eraseArray on the passed-in array.
	 */
	private static boolean isPasswordCorrect(char[] input, int number) {
		boolean isCorrect = true;
		if (number == 1) {
			char[] correctPassword = { 'p', 'a', 't', 'j', 'e' };

			if (input.length != correctPassword.length) {
				isCorrect = false;
			} else {
				isCorrect = Arrays.equals(input, correctPassword);
			}

			// Zero out the password.
			Arrays.fill(correctPassword, '0');
		} else if (number == 2) {
			char[] correctPassword = { 'W', 'h', 'i', 's', 'k', 'y', '1', '2', '3' };

			if (input.length != correctPassword.length) {
				isCorrect = false;
			} else {
				isCorrect = Arrays.equals(input, correctPassword);
			}

			Arrays.fill(correctPassword, '0');
		}

		return isCorrect;
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == jBtnNummerplaten) {
			String s1 = "Voor welk jaar wenst u de nummerplaten af te drukken?";
			JPanel jPanel = new JPanel();
			JTextField jTxtField = new JTextField();
			jPanel.add(jTxtField);
			jTxtField.setPreferredSize(new Dimension(60, 30));
			jTxtField.addAncestorListener(new RequestFocusListener());
			Object[] arr = { s1, jPanel };
			int i = JOptionPane.showConfirmDialog(null, arr, "", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (i == 0) {
				try {
					int jaar = Integer.parseInt(jTxtField.getText());
					List<Object[]> nummerplaten = standplaatsService.findNummerplaten(jaar - 1);
					ConfiguratieDTO configDTO = configuratieService.getConfiguratie("camping_naam");
					String camping = "Camping " + configDTO.getWaarde();
					configDTO = configuratieService.getConfiguratie("camping_adres");
					String contact = configDTO.getWaarde();
					configDTO = configuratieService.getConfiguratie("camping_telefoon");
					contact += " - " + configDTO.getWaarde();
					configDTO = configuratieService.getConfiguratie("nummer_wachtdienst");
					contact += " - " + configDTO.getWaarde();
					new NummerplatenPdf(nummerplaten, jaar, camping, contact);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (a.getSource() == jBtnOpmGrond) {
			List<StandplaatsDetailDTO> standplaatsdetails = standplaatsDetailService.getStandplaatsDetails();
			JComboBox jCboDetails = new JComboBox();
			for (StandplaatsDetailDTO standplaatsdetail : standplaatsdetails) {
				jCboDetails.addItem(standplaatsdetail.getNaam());
			}
			Object[] arr = { "Opmerking grond", jCboDetails };
			int i = JOptionPane.showConfirmDialog(null, arr, "", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (i == 0) {
				int index = jCboDetails.getSelectedIndex();
				StandplaatsDetailDTO standplaatsDetail = standplaatsdetails.get(index);
				OpmerkingenGrondTabelModel model = new OpmerkingenGrondTabelModel(standplaatsDetail.getNummer(),
						standplaatsDetail.isPrintAlles(), standplaatsDetail.isNamenTonen());
				JTable jTable = new JTable(model);
				jTable.setGridColor(Color.gray);
				TableColumn colStandplaats = jTable.getColumnModel().getColumn(0);
				colStandplaats.setPreferredWidth(70);
				colStandplaats.setMaxWidth(70);
				colStandplaats.setMinWidth(70);
				if (standplaatsDetail.isNamenTonen()) {
					TableColumn colBetaler = jTable.getColumnModel().getColumn(1);
					colBetaler.setPreferredWidth(200);
					colBetaler.setMaxWidth(200);
					colBetaler.setMinWidth(200);
				}

				JScrollPane jScrollPane = new JScrollPane(jTable);
				jScrollPane.setPreferredSize(new Dimension(750, 600));
				if (standplaatsDetail.isPrintAlles()) {
					jTable.setRowHeight(40);
				}
				JPanel jPanelPrint = new JPanel();
				jPanelPrint.setPreferredSize(new Dimension(150, 30));
				JButton jBtnAfdrukken = new JButton("Afdrukken");
				jBtnAfdrukken.addActionListener(new PrintHandler(jTable));
				jBtnAfdrukken.setPreferredSize(new Dimension(150, 30));
				jPanelPrint.add(jBtnAfdrukken);
				Object[] arr2 = { standplaatsDetail.getNaam(), jScrollPane, jPanelPrint };
				JOptionPane.showConfirmDialog(null, arr2, "", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (a.getSource() == jBtnBetalingen) {
			JRadioButton rBtnMetUitstel = new JRadioButton("Pascal");
			rBtnMetUitstel.setActionCommand("Pascal");
			rBtnMetUitstel.setFont(fontButton);
			JRadioButton rBtnLangeVersie = new JRadioButton("Patrick lange versie");
			rBtnLangeVersie.setActionCommand("Patrick lange versie");
			rBtnLangeVersie.setFont(fontButton);
			JRadioButton rBtnKorteVersie = new JRadioButton("Patrick korte versie");
			rBtnKorteVersie.setActionCommand("Patrick korte versie");
			rBtnKorteVersie.setFont(fontButton);
			JRadioButton rBtnAndere = new JRadioButton("Andere");
			rBtnAndere.setActionCommand("Andere");
			rBtnAndere.setFont(fontButton);
			JRadioButton rBtnBetalingen = new JRadioButton("tabel betalingen");
			rBtnBetalingen.setActionCommand("tabel_betalingen");
			rBtnBetalingen.setFont(fontButton);
			JPanel jPanelBetalingen = new JPanel();
			jPanelBetalingen.setLayout(new GridLayout(5, 1));
			ButtonGroup group = new ButtonGroup();
			group.add(rBtnMetUitstel);
			group.add(rBtnLangeVersie);
			group.add(rBtnKorteVersie);
			group.add(rBtnAndere);
			group.add(rBtnBetalingen);
			jPanelBetalingen.add(rBtnMetUitstel);
			jPanelBetalingen.add(rBtnLangeVersie);
			jPanelBetalingen.add(rBtnKorteVersie);
			jPanelBetalingen.add(rBtnAndere);
			jPanelBetalingen.add(rBtnBetalingen);
			Object[] arr = { "Welk betalings rapport wenst u af te drukken?", jPanelBetalingen };
			int i = JOptionPane.showConfirmDialog(null, arr, "", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (i == JOptionPane.OK_OPTION) {
				try {
					boolean uitstel = rBtnMetUitstel.isSelected();
					BetalingRapportEnum rapportType = null;
					if (rBtnMetUitstel.isSelected())
						rapportType = BetalingRapportEnum.UITSTEL;
					if (rBtnLangeVersie.isSelected())
						rapportType = BetalingRapportEnum.LANGEVERSIE;
					if (rBtnKorteVersie.isSelected())
						rapportType = BetalingRapportEnum.KORTEVERSIE;
					if (rBtnAndere.isSelected())
						rapportType = BetalingRapportEnum.ANDERE;
					if (rBtnBetalingen.isSelected())
						rapportType = BetalingRapportEnum.TABEL;
					if (rapportType != null) {
						List<StandplaatsDTO> tmpStandplaatsen = standplaatsService
								.getStandplaatsenBetalingNietOk(rapportType);
						if (BetalingRapportEnum.UITSTEL.equals(rapportType)
								|| BetalingRapportEnum.LANGEVERSIE.equals(rapportType)) {
							BetalingenPdf betalingenPdf = new BetalingenPdf();
							betalingenPdf.createDocument(new Date(), tmpStandplaatsen, uitstel, true);
						} else if (BetalingRapportEnum.KORTEVERSIE.equals(rapportType)) {
							BetalingenPdf betalingenPdf = new BetalingenPdf();
							betalingenPdf.createDocument(new Date(), tmpStandplaatsen, uitstel, false);
						} else if (BetalingRapportEnum.ANDERE.equals(rapportType)) {
							BetalingenAnderePdf betalingenPdf = new BetalingenAnderePdf();
							betalingenPdf.createDocument(new Date(), tmpStandplaatsen);
						} else if (BetalingRapportEnum.TABEL.equals(rapportType)) {
							BetalingenTabelPdf betalingenPdf = new BetalingenTabelPdf();
							betalingenPdf.createDocument(new Date(), tmpStandplaatsen);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
		if (a.getSource() == jBtnNieuwePrijzen) {

			JPasswordField jPasswordField = new JPasswordField();
			jPasswordField.setFont(fontButton);
			jPasswordField.setPreferredSize(new Dimension(150, 28));
			jPasswordField.setMinimumSize(new Dimension(120, 28));
			jPasswordField.addAncestorListener(new RequestFocusListener());
			JPanel jPanelPassword = new JPanel();
			jPanelPassword.add(new JLabel("Geef uw paswoord in: "));
			jPanelPassword.add(jPasswordField);
			Object[] array = { jPanelPassword };
			String[] choices = { "OK", "ANNULEREN" };
			int ret = JOptionPane.showOptionDialog(this, array, "Wachtwoord", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
			// JOptionPane.showConfirmDialog(null,array,"Wachtwoord",JOptionPane.OK_CANCEL_OPTION,
			// JOptionPane.WARNING_MESSAGE);
			char[] input = jPasswordField.getPassword();
			if (ret == JOptionPane.OK_OPTION && isPasswordCorrect(input, 1)) {
				if (ret == JOptionPane.YES_OPTION) {
					array[0] = "Doorgaan...?";
					ret = JOptionPane.showOptionDialog(this, array, "Kritische bewerking !!!",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
					// JOptionPane.showConfirmDialog(null,array,"Kritische bewerking
					// !!!",JOptionPane.YES_NO_OPTION,
					// JOptionPane.WARNING_MESSAGE);
					if (ret == JOptionPane.YES_OPTION) {
						array[0] = "Klik JA voor het opmaken van een lijst met alle bestaande prijzen";
						ret = JOptionPane.showOptionDialog(this, array, "Alle bestaande prijzen...",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices,
								choices[1]);
						// JOptionPane.showConfirmDialog(null,array,"Alle bestaande
						// prijzen...",JOptionPane.YES_NO_OPTION,
						// JOptionPane.WARNING_MESSAGE);
						if (ret == JOptionPane.YES_OPTION) {
							ConfiguratieDTO configuratie = configuratieService.getConfiguratie(GEMEENTELIJKE_BELASTING);
							List<Map<String, Object>> grondprijzen = standplaatsService.zoekBasisprijzen();
							PrijsAanpassingenDialog prijsAanpassingenDialog = new PrijsAanpassingenDialog(grondprijzen,
									configuratie.getWaarde());
							// getPrijsAanpassingenDialog().reset();
							Object[] arr = { prijsAanpassingenDialog };
							int i = JOptionPane.showOptionDialog(this, arr, "", JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
							// JOptionPane.showConfirmDialog(null,arr,"",JOptionPane.OK_CANCEL_OPTION,
							// JOptionPane.INFORMATION_MESSAGE);
							if (i == JOptionPane.OK_OPTION) {
								array[0] = "Weet u zeker dat u de prijzen wil aanpassen?";
								ret = JOptionPane.showOptionDialog(this, array, "Prijzen aanpassen...",
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices,
										choices[1]);
								// JOptionPane.showConfirmDialog(null,array,"Prijzen
								// aanpassen...",JOptionPane.YES_NO_OPTION,
								// JOptionPane.WARNING_MESSAGE);
								if (ret == JOptionPane.YES_OPTION) {
									List<Map<String, Double>> prijzen = new ArrayList<Map<String, Double>>();
									try {
										prijzen = prijsAanpassingenDialog.getPrijzen();
									} catch (ParseException e1) {
										e1.printStackTrace();
									}
									standplaatsService.storeNieuweGrondprijzen(prijzen);
									try {
										if (!configuratie.getWaarde().equals(
												prijsAanpassingenDialog.getAangepasteGemeentelijkeBelasting())) {
											configuratie.setWaarde(
													prijsAanpassingenDialog.getAangepasteGemeentelijkeBelasting());
											configuratieService.storeConfiguratie(configuratie);
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
									ConfiguratieDTO prijsJaarConfiguratieDTO = configuratieService
											.getConfiguratie("PRIJS_JAAR");
									if (null == prijsJaarConfiguratieDTO) {
										prijsJaarConfiguratieDTO = requireYearFromUser();
									}
									int year = Integer.parseInt(prijsJaarConfiguratieDTO.getWaarde()) + 1;
									boolean ok = standplaatsService.resetBetalingenNieuweGrondprijzen(
											factuurDetailService, configuratieService, year);
									if (!ok) {
										JOptionPane.showMessageDialog(this,
												"Er is een fout opgetreden bij het resetten van de betaling", "Fout",
												JOptionPane.ERROR_MESSAGE);
									} else {
										List<JComponent> components = new ArrayList<JComponent>();
										components.add(jBtnNieuwePrijzen);
										components.add(jBtnUpdateNieuwePrijzen);
										new PrijsAanpassingBusyThread(components, standplaatsService);
										jBtnNieuwePrijzen.setEnabled(true);
										jBtnUpdateNieuwePrijzen.setEnabled(true);
									}
								}
							}
						}
					}
				}
			}
		}
		if (a.getSource() == jBtnUpdateNieuwePrijzen) {
			JPasswordField jPasswordField = new JPasswordField();
			jPasswordField.setFont(fontButton);
			jPasswordField.setPreferredSize(new Dimension(150, 28));
			jPasswordField.setMinimumSize(new Dimension(120, 28));
			jPasswordField.addAncestorListener(new RequestFocusListener());
			JPanel jPanelTelefoon = new JPanel();
			jPanelTelefoon.add(new JLabel("Geef uw paswoord in: "));
			jPanelTelefoon.add(jPasswordField);
			Object[] array = { jPanelTelefoon };
			String[] choices = { "OK", "ANNULEREN" };
			int ret = JOptionPane.showOptionDialog(this, array, "Wachtwoord", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
			char[] input = jPasswordField.getPassword();
			if (ret == JOptionPane.OK_OPTION && isPasswordCorrect(input, 1)) {
				if (ret == JOptionPane.YES_OPTION) {
					array[0] = "Doorgaan...?";
					ret = JOptionPane.showOptionDialog(this, array, "Kritische bewerking !!!",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
					if (ret == JOptionPane.YES_OPTION) {
						array[0] = "Klik JA voor het aanpassen van de prijzen";
						ret = JOptionPane.showOptionDialog(this, array, "Alle bestaande prijzen...",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices,
								choices[1]);
						if (ret == JOptionPane.YES_OPTION) {
							array[0] = "Weet u zeker dat u de prijzen wil aanpassen?";
							ret = JOptionPane.showOptionDialog(this, array, "Prijzen aanpassen...",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices,
									choices[1]);
							if (ret == JOptionPane.YES_OPTION) {
								ConfiguratieDTO prijsJaarConfiguratieDTO = configuratieService
										.getConfiguratie("PRIJS_JAAR");
								if (null == prijsJaarConfiguratieDTO) {
									prijsJaarConfiguratieDTO = requireYearFromUser();
								}
								int year = Integer.parseInt(prijsJaarConfiguratieDTO.getWaarde()) + 1;
								boolean ok = standplaatsService.resetBetalingenNieuweGrondprijzen(factuurDetailService,
										configuratieService, year);
								if (!ok) {
									JOptionPane.showMessageDialog(this,
											"Er is een fout opgetreden bij het updaten van de betaling", "Fout",
											JOptionPane.ERROR_MESSAGE);
								} else {
									List<JComponent> components = new ArrayList<JComponent>();
									components.add(jBtnNieuwePrijzen);
									components.add(jBtnUpdateNieuwePrijzen);
									new PrijsAanpassingBusyThread(components, standplaatsService);
									jBtnNieuwePrijzen.setEnabled(true);
									jBtnUpdateNieuwePrijzen.setEnabled(true);
								}
							}
						}
					}
				}
			}
		}
		if (a.getSource() == jBtnFactuurAanmaak) {
			standplaatsService.createBetalingNieuwJaar();
		}
		if (a.getSource() == jBtnUpdateBasisPrijzen) {
			standplaatsService.adjustBasePrices();
			JOptionPane.showMessageDialog(this, "De basisprijzen werden gewijzigd.");
		}
		if (a.getSource() == jBtnConvertTaalEnReglGetekendDatum) {
			/**
			 * volgende zaken moeten mogelijks nog opgelost worden: - betaler panel: grond
			 * B107, B199, C168, E047, E087, G001, W010 heeft betaler.hoofdbetaler.id die
			 * niet bestaat - betaler panel: grond W010 verwijst naar badge die er niet is
			 */
			List<Integer> standplaatsIds = standplaatsService.getStandplaatsIds();
			List<StandplaatsDetailDTO> standplaatsDetails = standplaatsDetailService.getStandplaatsDetails();
			int nummerTaal = 1000;
			for (StandplaatsDetailDTO standplaatsDetail : standplaatsDetails) {
				if (standplaatsDetail.getNaam().equals("taal")) {
					nummerTaal = standplaatsDetail.getNummer();
					break;
				}
			}
			if (nummerTaal != 1000) {
				for (int id : standplaatsIds) {
					StandplaatsDTO standplaats = standplaatsService.getStandplaats(id);
					Set<GrondInformatieDTO> grondInformaties = standplaats.getGrondInformaties();
					for (GrondInformatieDTO grondInformatie : grondInformaties) {
						if (grondInformatie.getNummer() == nummerTaal) {
							try {
								String s = grondInformatie.getWaarde();
								if (!StringUtils.isEmpty(s)) {
									String[] arr = s.split(" ");
									if (arr.length == 2) {
										BetalerDTO betaler = getHuidigeBetaler(standplaats.getBetalers());
										if (betaler != null) {
											// taal
											if (arr[0].equalsIgnoreCase("frans")) {
												if (betaler.getHoofdbetaler() != null) {
													betaler.getHoofdbetaler().setTaal(TaalEnum.FR);
												}
												if (betaler.getBetaler() != null) {
													betaler.getBetaler().setTaal(TaalEnum.FR);
												}
											} else if (arr[0].equalsIgnoreCase("nederlands")) {
												if (betaler.getHoofdbetaler() != null) {
													betaler.getHoofdbetaler().setTaal(TaalEnum.NL);
												}
												if (betaler.getBetaler() != null) {
													betaler.getBetaler().setTaal(TaalEnum.NL);
												}
											} else if (arr[0].equalsIgnoreCase("duits")) {
												if (betaler.getHoofdbetaler() != null) {
													betaler.getHoofdbetaler().setTaal(TaalEnum.DU);
												}
												if (betaler.getBetaler() != null) {
													betaler.getBetaler().setTaal(TaalEnum.DU);
												}
											}

											// datum reglement getekend
											String[] datum = null;
											if (arr[1].contains("/")) {
												datum = arr[1].split("/");

											} else if (arr[1].contains("-")) {
												datum = arr[1].split("-");
											}
											if (datum != null) {
												int dag;
												int maand;
												int jaar;
												try {
													dag = Integer.parseInt(datum[0]);
													maand = Integer.parseInt(datum[1]) - 1;
													jaar = Integer.parseInt(datum[2]);
													if (jaar < 100) {
														if (jaar < 11) {
															jaar = 2000 + jaar;
														} else {
															jaar = 1900 + jaar;
														}
													}
													Calendar cal = Calendar.getInstance();
													cal.set(jaar, maand, dag);
													betaler.setReglementGetekendDatum(cal.getTime());
													standplaatsService.storeStandplaats(standplaats);
												} catch (Exception e) {
													System.err.println("Error parsing data, format not valid: " + id
															+ " - " + datum);
													e.printStackTrace();
												}
											}
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		if (a.getSource() == jBtnConvertInschrijving) {
			standplaatsService.convertInschrijving();
		}
		if (a.getSource() == jBtnConvertFacturatie) {
			standplaatsService.convertFacturatie();
		}
		if (a.getSource() == jBtnConvertBetalers) {
			standplaatsService.convertBetalers();
		}
		if (a.getSource() == jBtnBerekenFacturatie) {
			standplaatsService.berekenFacturatie();
			jBtnBerekenFacturatie.setVisible(false);
		}
		if (a.getSource() == jBtnPrintFotosVoorFacturatie) {
			List<Map<String, Object>> fotosFacturaties = fotoService
					.findPhotosWithRemarks(getConfiguratieDTO().getWaarde());
			new FotoAndOpmerkingenFacturatiePdf(fotosFacturaties);
		}
		if (a.getSource() == jBtnKassa) {
			// List<KassaKostprijsDTO> kostprijzen = kassaKostprijsService.getKostprijzen();
			// List<KasbonDTO> kasbons = kasbonService.getKasbons();
			Date date = controller.getTimeFromKassa();
			List<KassaAfrekeningDTO> kassaAfrekeningDTOs = kassaService.berekenDagInkomsted(date);
			DagRapportPdf.createDocument(kassaAfrekeningDTOs);

			Object[] array = { "Is uw kasregister correct afgeprint?" };
			int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.YES_OPTION) {
				array[0] = "Mogen alle kasbons van vandaag definitief gewist worden?";
				int ret2 = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ret2 == JOptionPane.YES_OPTION) {
					array[0] = "Bent u zeker dat u alle kasbons wil wissen?";
					int ret3 = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (ret3 == JOptionPane.YES_OPTION) {
						kasbonService.removeAll();
						// controller.resetKasbon();
					}
				}
			}
		}
		if (a.getSource() == jBtnKasbon) {
			boolean b = true;
			String s = "";
			while (b) {
				boolean geldigNummer = true;
				JTextField jTxtKasbonNummer = new JTextField();
				jTxtKasbonNummer.setFont(new Font("Times New Roman", Font.PLAIN, 18));
				jTxtKasbonNummer.setPreferredSize(new Dimension(100, 28));
				jTxtKasbonNummer.setMinimumSize(new Dimension(100, 28));
				jTxtKasbonNummer.setText(s);
				JPanel jPanelKasbonnummer = new JPanel();
				jPanelKasbonnummer.add(new JLabel("KASBON NUMMER: "));
				jPanelKasbonnummer.add(jTxtKasbonNummer);
				Object[] array = { jPanelKasbonnummer };
				jTxtKasbonNummer.addAncestorListener(new RequestFocusListener());
				int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtKasbonNummer.getText())) {
					Integer kasbonnummer = null;
					try {
						s = jTxtKasbonNummer.getText();
						kasbonnummer = Integer.parseInt(jTxtKasbonNummer.getText());
						if (kasbonnummer != null) {
							geldigNummer = true;
							KasbonDTO kasbon = kasbonService.getKasbonByKasbonNummer(kasbonnummer);
							if (kasbon != null) {
								controller.setKasbonInKassaPanel(kasbon);
								b = false;
							} else {
								String message = "EEN KASBON MET DIT NUMMER BESTAAT NIET!";
								String[] choices = { "OK" };
								JOptionPane.showOptionDialog(this, message, "Waarschuwing",
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices,
										choices[0]);
							}
						} else {
							geldigNummer = false;
						}
					} catch (Exception e) {
						geldigNummer = false;
					}
					if (!geldigNummer) {
						String message = "GELIEVE EEN GELDIG KASBON NUMMER IN TE GEVEN!";
						String[] choices = { "OK" };
						JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
					}
				} else {
					b = false;
				}
			}
		} else if (a.getSource() == jBtnChangePersoon) {
			JPasswordField jPasswordField = new JPasswordField();
			jPasswordField.setFont(fontButton);
			jPasswordField.setPreferredSize(new Dimension(150, 28));
			jPasswordField.setMinimumSize(new Dimension(120, 28));
			jPasswordField.addAncestorListener(new RequestFocusListener());
			JPanel jPanelTelefoon = new JPanel();
			jPanelTelefoon.add(new JLabel("Geef uw paswoord in: "));
			jPanelTelefoon.add(jPasswordField);
			Object[] array = { jPanelTelefoon };
			String[] choices = { "OK", "ANNULEREN" };
			int ret = JOptionPane.showOptionDialog(this, array, "Wachtwoord", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if (ret == JOptionPane.OK_OPTION && isPasswordCorrect(jPasswordField.getPassword(), 2)) {
				if (ret == JOptionPane.YES_OPTION) {
					new ChangeAndRemovePersoonDialog(persoonService);
				}
			}
		}
		/*
		 * if (a.getSource() == jBtnConvertBetalingen) {
		 * standplaatsService.convertBetalingen(); }
		 */
	}

	private BetalerDTO getHuidigeBetaler(Set<BetalerDTO> betalers) {
		BetalerDTO betaler = null;
		Calendar cal = null;
		for (BetalerDTO betalerDTO : betalers) {
			if (cal != null) {
				Calendar tmpCal = Calendar.getInstance();
				tmpCal.setTime(betalerDTO.getDatumVan());
				if (tmpCal.after(cal)) {
					cal = tmpCal;
					betaler = betalerDTO;
				}
			} else {
				cal = Calendar.getInstance();
				cal.setTime(betalerDTO.getDatumVan());
				betaler = betalerDTO;
			}
		}
		return betaler;
	}

	private ConfiguratieDTO requireYearFromUser() {
		JTextField yearField = new JTextField();
		yearField.setFont(fontButton);
		yearField.setPreferredSize(new Dimension(150, 28));
		yearField.setMinimumSize(new Dimension(120, 28));
		yearField.addAncestorListener(new RequestFocusListener());
		JPanel jPanelPassword = new JPanel();
		jPanelPassword.add(new JLabel("Geef het laatste jaar in waarvoor de grondprijzen werden berekend: "));
		jPanelPassword.add(yearField);
		Object[] array = { jPanelPassword };
		String[] choices = { "OK", "ANNULEREN" };
		ConfiguratieDTO configuratieDTO = new ConfiguratieDTO();
		String sYear = null;
		while (StringUtils.isEmpty(sYear)) {
			int ret = JOptionPane.showOptionDialog(this, array, "jaar", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
			if (ret == JOptionPane.YES_OPTION) {
				sYear = yearField.getText();
				try {
					Integer.parseInt(sYear);
					configuratieDTO.setNaam("PRIJS_JAAR");
					configuratieDTO.setWaarde(sYear);
					configuratieDTO = configuratieService.storeConfiguratie(configuratieDTO);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "U heeft geen geldig getal ingegeven!");
					sYear = null;
				}
			}
		}
		return configuratieDTO;
	}

	private Vector<Object[]> getColumnDataVoorOpmerkingenGrond(int nummer, boolean printAlles, boolean namenTonen) {
		Vector<Object[]> opmerkingen = new Vector<Object[]>();
		List<Object[]> tmpOpmerkingen = standplaatsService.zoekOpmerkingenGrond(nummer, printAlles, namenTonen);
		if (namenTonen) {
			for (Object[] o : tmpOpmerkingen) {
				Object[] o2 = new Object[3];
				int i = (Integer) o[1];
				String s = Integer.toString(i);
				while (s.length() > 3) {
					s = '0' + s;
				}
				Character c = (Character) o[0];
				s = c.toString() + s;
				o2[0] = s;
				String naam = (String) o[2] + (String) o[3];
				o2[1] = naam;
				o2[2] = o[4];
				opmerkingen.add(o2);
			}
		} else {
			for (Object[] o : tmpOpmerkingen) {
				Object[] o2 = new Object[2];
				int i = (Integer) o[1];
				String s = Integer.toString(i);
				while (s.length() > 3) {
					s = '0' + s;
				}
				Character c = (Character) o[0];
				s = c.toString() + s;
				o2[0] = s;
				o2[1] = o[2];
				opmerkingen.add(o2);
			}
		}
		return opmerkingen;
	}

	private class OpmerkingenGrondTabelModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private String[] columnNames;

		private Vector<Object[]> data = null;

		public OpmerkingenGrondTabelModel(int nummer, boolean printAlles, boolean namenTonen) {
			data = getColumnDataVoorOpmerkingenGrond(nummer, printAlles, namenTonen);
			if (namenTonen) {
				columnNames = new String[3];
				columnNames[0] = "Standplaats";
				columnNames[1] = "Betaler(s)";
				columnNames[2] = "Opmerking";
			} else {
				columnNames = new String[2];
				columnNames[0] = "Standplaats";
				columnNames[1] = "Opmerking";
			}
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
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			Object[] o = data.get(row);
			return o[col];
		}

		@Override
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			Object[] o = data.get(row);
			o[col] = value;
		}
	}
}
