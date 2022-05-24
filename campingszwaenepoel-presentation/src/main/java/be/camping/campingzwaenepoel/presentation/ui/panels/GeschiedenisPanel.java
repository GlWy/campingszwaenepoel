package be.camping.campingzwaenepoel.presentation.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.StringUtils;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.GebruikerEnum;
import be.camping.campingzwaenepoel.common.utils.DateUtilities;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.listeners.RequestFocusListener;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.GeschiedenisCommunicatieService;
import be.camping.campingzwaenepoel.service.GeschiedenisThemaService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisCommunicatieDTO;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisDTO;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisThemaDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class GeschiedenisPanel extends JPanel implements PanelInterface, ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextArea jTAGeschiedenis = null;
	private JComboBox jCboNamen;
	private JPanel jPanelIngaveGeschiedenis;
	private JPanel jPanelTableGeschiedenis;
	private JCheckBox jCheckBoxVerplicht;
	private JDatePicker datePicker;
	private JComboBox jCboThema;
	private JComboBox jCboCommunicatie;
	
	private JButton jBtnThema;
	private JButton jBtnNieuwThema;
	private JButton jBtnWijzigThema;
	private JButton jBtnVerwijderThema;
	private JPanel jPanelThema;
	private JComboBox jCboEditThema;
	
	private JButton jBtnCommunicatie;
	private JButton jBtnNieuweCommunicatie;
	private JButton jBtnWijzigCommunicatie;
	private JButton jBtnVerwijderCommunicatie;
	private JPanel jPanelCommunicatie;
	private JComboBox jCboEditCommunicatie;
	
	private JButton jBtnRemoveGeschiedenis;
	
	private JScrollPane jScrollPane;
	private JTable jTable;
	private List<GeschiedenisThemaDTO> themas;
	private List<GeschiedenisCommunicatieDTO> communicaties;
	
	private Font font = new Font("Times New Roman", Font.BOLD, 18);
	
	private Controller controller;

	@Autowired
	private StandplaatsService standplaatsService;
	@Autowired
	private GeschiedenisThemaService geschiedenisThemaService;
	@Autowired
	private GeschiedenisCommunicatieService geschiedenisCommunicatieService;

	Color colorErrorTab = Constant.ERROR_COLOR;
	private JButton jBtnOpslaan = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy k:mm");

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

	public GeschiedenisThemaService getGeschiedenisThemaService() {
		return geschiedenisThemaService;
	}

	public void setGeschiedenisThemaService(
			GeschiedenisThemaService geschiedenisThemaService) {
		this.geschiedenisThemaService = geschiedenisThemaService;
	}

	public GeschiedenisCommunicatieService getGeschiedenisCommunicatieService() {
		return geschiedenisCommunicatieService;
	}

	public void setGeschiedenisCommunicatieService(
			GeschiedenisCommunicatieService geschiedenisCommunicatieService) {
		this.geschiedenisCommunicatieService = geschiedenisCommunicatieService;
	}

	public StandplaatsDTO getGrondDTO() {
		return getController().getStandplaatsDTO();
	}

	public boolean checkData() {
		return !getGrondDTO().getGeschiedenisFlag();
	}

	public boolean checkDataChanged() {
		boolean ret = false;
		if (!StringUtils.isEmpty(getJTAGeschiedenis().getText()) && StringUtils.isEmpty(getGrondDTO().getGeschiedenis())) {
			ret = true;
		}
		return ret;
	}

	public boolean checkDataForParent() {
		return !getController().getStandplaatsDTO().getGeschiedenisFlag();
	}

	public Object getDataFromGUI() {
		StandplaatsDTO standplaats = getGrondDTO();
		Set<GeschiedenisDTO> geschiedenisPunten = new HashSet<GeschiedenisDTO>();
		boolean verwittiging = false;
		if (!StringUtils.isEmpty(getJTAGeschiedenis().getText())) {
			GeschiedenisDTO geschiedenis = new GeschiedenisDTO();
			geschiedenis.setVerwittiging(getjCheckBoxVerplicht().isSelected());
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy k:mm");
			try {
				Date currentDate = datePicker.getTime();
				String sCurrentDate = sdf.format(currentDate);
				String[] currentDates = sCurrentDate.split(" ");
				String sDate = sdf.format(date);
				String[] sDates = sDate.split(" ");
				String newDate = currentDates[0] + " " + sDates[1];
//				System.err.println(newDate);
				geschiedenis.setDatum(sdf.parse(newDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			geschiedenis.setNaam(GebruikerEnum.getGebruikerEnum((String)getjCboNamen().getSelectedItem()));
			geschiedenis.setThema((String)getjCboThema().getSelectedItem());
			geschiedenis.setCommunicatie((String)getjCboCommunicatie().getSelectedItem());
			geschiedenis.setGeschiedenis(getJTAGeschiedenis().getText());
			if (geschiedenis.getVerwittiging()) {
				verwittiging = true;
			}
			geschiedenisPunten.add(geschiedenis);
			standplaats.setGeschiedenis("");
		}
		GeschiedenisTableModel model = (GeschiedenisTableModel)getjTable().getModel();
		for (GeschiedenisDTO geschiedenis : model.getData()) {
			geschiedenisPunten.add(geschiedenis);
			if (geschiedenis.getVerwittiging()) {
				verwittiging = true;
			}
		}
		standplaats.setGeschiedenisPunten(geschiedenisPunten);
		standplaats.setGeschiedenisFlag(verwittiging);
		return standplaats;
	}

	public void save() {
		if (!StringUtils.isEmpty(getJTAGeschiedenis().getText()) && 
				(getjCboCommunicatie().getSelectedIndex() == 0 || getjCboThema().getSelectedIndex() == 0)) {
			boolean bComm = (getjCboCommunicatie().getSelectedIndex() == 0);
			boolean bThema = (getjCboThema().getSelectedIndex() == 0);
			String s = "Gelieve ";
			if (bComm && bThema) {
				s += "het thema en de communicatie in te vullen!";
			} else if (bComm) {
				s += "de communicatie in te vullen";
			} else if (bThema) {
				s += "het thema in te vullen";
			}
			JOptionPane.showMessageDialog(this, s);
		} else {
			StandplaatsDTO standplaats = (StandplaatsDTO)getDataFromGUI();
			standplaats = getStandplaatsService().storeStandplaats(standplaats);
			reset();
			setRemarksInGui();
		}
	}

	private void reset() {
		getJTAGeschiedenis().setText("");
		getjCboNamen().setSelectedIndex(0);
		getDatePicker().clearTextField();
		getjCboCommunicatie().setSelectedIndex(0);
		getjCboThema().setSelectedIndex(0);
		getjCboEditThema().setSelectedIndex(0);
		getjCheckBoxVerplicht().setSelected(false);
		setjTable(null);
	}
	
	public void setDataInGUI() {
		try {
			reset();
			getjScrollPane().setViewportView(getjTable());
			StandplaatsDTO standplaats = getController().getStandplaatsDTO();
			if (!StringUtils.isEmpty(standplaats.getGeschiedenis())) {
				try {
					Calendar cal = Calendar.getInstance();
					cal.setTime(standplaats.getGeschiedenisDatum());
					getDatePicker().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));	
				} catch (Exception e) {

				}
				getjCboNamen().setSelectedIndex(standplaats.getGeschiedenisEditor().value());
				getJTAGeschiedenis().setText(standplaats.getGeschiedenis());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setRemarksInGui();
	}

	public void setRemarksInGui() {
		boolean b = checkData();
		controller.updateDataForPanel(b, 10);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		setLayout(new BorderLayout());
		add(getjPanelIngaveGeschiedenis(), BorderLayout.NORTH);
		add(getjScrollPane(), BorderLayout.CENTER);
		this.setBorder(new LineBorder(Color.BLACK, 2));
	}

	public List<GeschiedenisThemaDTO> getThemas(){
		if (themas == null) {
			themas = getGeschiedenisThemaService().findAllThemas();
		}
		return themas;
	}
	
	public void setThemas(List<GeschiedenisThemaDTO> themas) {
		if (themas == null) {
			themas = getGeschiedenisThemaService().findAllThemas();
		}
		this.themas = themas;
	}

	public List<GeschiedenisCommunicatieDTO> getCommunicaties() {
		if (communicaties == null) {
			communicaties = getGeschiedenisCommunicatieService().findAllCommunicaties();
		}
		return communicaties;
	}

	public void setCommunicaties(List<GeschiedenisCommunicatieDTO> communicaties) {
		if (communicaties == null) {
			communicaties = getGeschiedenisCommunicatieService().findAllCommunicaties();
		}
		this.communicaties = communicaties;
	}

	/**
	 * This method initializes jTAGeschiedenis	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTAGeschiedenis() {
		if (jTAGeschiedenis == null) {
			jTAGeschiedenis = new JTextArea();
			jTAGeschiedenis.setLineWrap(true);
			jTAGeschiedenis.setWrapStyleWord(true);
			jTAGeschiedenis.setColumns(25);
			jTAGeschiedenis.setRows(4);
			jTAGeschiedenis.setMinimumSize(new Dimension(400,100));
			jTAGeschiedenis.getDocument().addDocumentListener(new GeschiedenisListener());
			jTAGeschiedenis.setFont(font);
			jTAGeschiedenis.setBackground(Color.WHITE);
		}
		return jTAGeschiedenis;
	}

	public JPanel getjPanelIngaveGeschiedenis() {
		if (jPanelIngaveGeschiedenis == null) {
			jPanelIngaveGeschiedenis = new JPanel();
			jPanelIngaveGeschiedenis.setLayout(new GridBagLayout());
			Insets insets = new Insets(5, 5, 5, 0);

			GridBagConstraints gbcCheckBoxVerplicht = new GridBagConstraints();
			gbcCheckBoxVerplicht.insets = insets;
			gbcCheckBoxVerplicht.gridx = 0;
			gbcCheckBoxVerplicht.gridy = 0;
			GridBagConstraints gbcDatePicker = new GridBagConstraints();
			gbcDatePicker.insets = insets;
			gbcDatePicker.gridx = 1;
			gbcDatePicker.gridy = 0;
			GridBagConstraints gbcCboNamen = new GridBagConstraints();
			gbcCboNamen.insets = insets;
			gbcCboNamen.gridx = 2;
			gbcCboNamen.gridy = 0;
			GridBagConstraints gbcCboThema = new GridBagConstraints();
			gbcCboThema.insets = insets;
			gbcCboThema.gridx = 3;
			gbcCboThema.gridy = 0;
			GridBagConstraints gbcCommunicatie = new GridBagConstraints();
			gbcCommunicatie.insets = insets;
			gbcCommunicatie.gridx = 4;
			gbcCommunicatie.gridy = 0;
			GridBagConstraints gbcTAGeschiedenis = new GridBagConstraints();
			gbcTAGeschiedenis.weightx = 1.0;
			gbcTAGeschiedenis.insets = insets;
			gbcTAGeschiedenis.gridx = 5;
			gbcTAGeschiedenis.gridy = 0;
			gbcTAGeschiedenis.gridheight = 2;
			GridBagConstraints gbcBtnThema = new GridBagConstraints();
			gbcBtnThema.insets = insets;
			gbcBtnThema.gridx = 0;
			gbcBtnThema.gridy = 1;
			GridBagConstraints gbcBtnCommunicatie = new GridBagConstraints();
			gbcBtnCommunicatie.insets = insets;
			gbcBtnCommunicatie.gridx = 1;
			gbcBtnCommunicatie.gridy = 1;
//			gbcBtnCommunicatie.gridwidth = 2;
			GridBagConstraints gbcBtnOpslaan = new GridBagConstraints();
			gbcBtnOpslaan.insets = insets;
			gbcBtnOpslaan.gridx = 2;
			gbcBtnOpslaan.gridy = 1;
			gbcBtnOpslaan.gridwidth = 2;

			jPanelIngaveGeschiedenis.add(getjCheckBoxVerplicht(), gbcCheckBoxVerplicht);
			jPanelIngaveGeschiedenis.add((JComponent)getDatePicker(), gbcDatePicker);
			jPanelIngaveGeschiedenis.add(getjCboNamen(), gbcCboNamen);
			jPanelIngaveGeschiedenis.add(getjCboThema(), gbcCboThema);
			jPanelIngaveGeschiedenis.add(getjCboCommunicatie(), gbcCommunicatie);
			jPanelIngaveGeschiedenis.add(getJTAGeschiedenis(), gbcTAGeschiedenis);
			jPanelIngaveGeschiedenis.add(getjBtnThema(), gbcBtnThema);
			jPanelIngaveGeschiedenis.add(getjBtnCommunicatie(), gbcBtnCommunicatie);
			jPanelIngaveGeschiedenis.add(getJBtnOpslaan(), gbcBtnOpslaan);
		}
		return jPanelIngaveGeschiedenis;
	}

	public JPanel getjPanelTableGeschiedenis() {
		return jPanelTableGeschiedenis;
	}

	public JComboBox getjCboNamen() {
		if (jCboNamen == null) {
			jCboNamen = new JComboBox(Constant.NAMES);
		}
		return jCboNamen;
	}

	public JCheckBox getjCheckBoxVerplicht() {
		if (jCheckBoxVerplicht == null) {
			jCheckBoxVerplicht = new JCheckBox();
			jCheckBoxVerplicht.setText("verplicht");
		}
		return jCheckBoxVerplicht;
	}

	public JDatePicker getDatePicker() {
		if (datePicker == null) {
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			datePicker = JDateComponentFactory.createJDatePicker();
			datePicker.setTextEditable(true);
			datePicker.setShowYearButtons(true);
			datePicker.getJFormattedTextField().setPreferredSize(new Dimension(150, 30));
			datePicker.getJFormattedTextField().setFont(font);
		}
		return datePicker;
	}

	public JComboBox getjCboThema() {
		if (jCboThema == null) {
			List<GeschiedenisThemaDTO> themas = getThemas();
			if (themas != null) {
				String[] sThemas = new String[themas.size()+1];
				sThemas[0] = "";
				for (int i = 0; i < themas.size(); i++) {
					GeschiedenisThemaDTO thema = themas.get(i);
					sThemas[i+1] = thema.getThema();
				}
				jCboThema = new JComboBox(sThemas);
			} else {
				String[] sThemas = {""};
				jCboThema = new JComboBox(sThemas);
			}			
		}
		return jCboThema;
	}

	public JComboBox getjCboEditThema() {
		if (jCboEditThema == null) {
			List<GeschiedenisThemaDTO> themas = getThemas();
			if (themas != null) {
				String[] sThemas = new String[themas.size()+1];
				sThemas[0] = "";
				for (int i = 0; i < themas.size(); i++) {
					GeschiedenisThemaDTO thema = themas.get(i);
					sThemas[i+1] = thema.getThema();
				}
				jCboEditThema = new JComboBox(sThemas);
				
			} else {
				String[] sThemas = {""};
				jCboEditThema = new JComboBox(sThemas);
			}			
		}
		return jCboEditThema;
	}

	public JComboBox getjCboEditCommunicatie() {
		if (jCboEditCommunicatie == null) {
			List<GeschiedenisCommunicatieDTO> communicaties = getCommunicaties();
			if (communicaties != null) {
				String[] sCommunicaties = new String[communicaties.size()+1];
				sCommunicaties[0] = "";
				for (int i = 0; i < communicaties.size(); i++) {
					GeschiedenisCommunicatieDTO communicatie = communicaties.get(i);
					sCommunicaties[i+1] = communicatie.getCommunicatie();
				}
				jCboEditCommunicatie = new JComboBox(sCommunicaties);
				
			} else {
				String[] sCommunicaties = {""};
				jCboEditCommunicatie = new JComboBox(sCommunicaties);
			}			
		}
		return jCboEditCommunicatie;
	}

	public JComboBox getjCboCommunicatie() {
		if (jCboCommunicatie == null) {
			List<GeschiedenisCommunicatieDTO> communicaties = getCommunicaties();
			if (communicaties != null) {
				String[] sCommunicaties = new String[communicaties.size()+1];
				sCommunicaties[0] = "";
				for (int i = 0; i < communicaties.size(); i++) {
					GeschiedenisCommunicatieDTO communicatie = communicaties.get(i);
					sCommunicaties[i+1] = communicatie.getCommunicatie();
				}
				jCboCommunicatie = new JComboBox(sCommunicaties);
			} else {
				String[] sCommunicaties = {""};
				jCboCommunicatie = new JComboBox(sCommunicaties);
			}			
		}
		return jCboCommunicatie;
	}

	public class GeschiedenisListener implements DocumentListener {
		
		public void changedUpdate(DocumentEvent e) {
			setRemarksInGui();
		}

		public void insertUpdate(DocumentEvent e) {
			setRemarksInGui();
		}

		public void removeUpdate(DocumentEvent e) {
			setRemarksInGui();
		}
		
	}

	/**
	 * This method initializes jBtnOpslaan	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnOpslaan() {
		if (jBtnOpslaan == null) {
			jBtnOpslaan = new JButton();
			jBtnOpslaan.setText("OPSLAAN");
			jBtnOpslaan.setPreferredSize(new Dimension(145, 40));

			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new OpslaanActionListener("Opslaan");
		    InputMap inputMap = jBtnOpslaan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		    inputMap.put(stroke, actionKey);
		    ActionMap actionMap = jBtnOpslaan.getActionMap();
		    actionMap.put(actionKey, action);
		    jBtnOpslaan.addActionListener(this);
		}
		return jBtnOpslaan;
	}

	public JButton getjBtnThema() {
		if (jBtnThema == null) {
			jBtnThema = new JButton();
			jBtnThema.setText("THEMA");
			jBtnThema.setPreferredSize(new Dimension(145, 40));
			jBtnThema.addActionListener(this);
		}
		return jBtnThema;
	}

	public JButton getjBtnCommunicatie() {
		if (jBtnCommunicatie == null) {
			jBtnCommunicatie = new JButton();
			jBtnCommunicatie.setText("COMMUNICATIE");
			jBtnCommunicatie.setPreferredSize(new Dimension(145, 40));
			jBtnCommunicatie.addActionListener(this);
		}
		return jBtnCommunicatie;
	}

	public JScrollPane getjScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
		}
		return jScrollPane;
	}

	public JTable getjTable() {
		if (jTable == null) {
			resetTable();
		}
		return jTable;
	}
	
	public void setjTable(JTable jTable) {
		this.jTable = jTable;
	}
	
	public JButton getjBtnNieuwThema() {
		if (jBtnNieuwThema == null) {
			jBtnNieuwThema = new JButton();
			jBtnNieuwThema.setText("NIEUW THEMA");
			jBtnNieuwThema.setPreferredSize(new Dimension(145, 40));
			jBtnNieuwThema.addActionListener(this);
		}
		return jBtnNieuwThema;
	}

	public JButton getjBtnWijzigThema() {
		if (jBtnWijzigThema == null) {
			jBtnWijzigThema = new JButton();
			jBtnWijzigThema.setText("WIJZIG THEMA");
			jBtnWijzigThema.setPreferredSize(new Dimension(145, 40));
			jBtnWijzigThema.addActionListener(this);
		}
		return jBtnWijzigThema;
	}

	public JButton getjBtnVerwijderThema() {
		if (jBtnVerwijderThema == null) {
			jBtnVerwijderThema = new JButton();
			jBtnVerwijderThema.setText("VERWIJDER THEMA");
			jBtnVerwijderThema.setPreferredSize(new Dimension(145, 40));
			jBtnVerwijderThema.addActionListener(this);
		}
		return jBtnVerwijderThema;
	}

	public JPanel getjPanelThema() {
		if (jPanelThema == null) {
			jPanelThema = new JPanel();
			JLabel jLabelThema = new JLabel("   KIES THEMA: ");
			jPanelThema.setLayout(new GridLayout(1, 5));
			jPanelThema.add(getjBtnNieuwThema());
			jPanelThema.add(jLabelThema);
			jPanelThema.add(getjCboEditThema());
			jPanelThema.add(getjBtnWijzigThema());
			jPanelThema.add(getjBtnVerwijderThema());
		}
		return jPanelThema;
	}

	public JButton getjBtnNieuweCommunicatie() {
		if (jBtnNieuweCommunicatie == null) {
			jBtnNieuweCommunicatie = new JButton();
			jBtnNieuweCommunicatie.setText("NIEUWE COMMUNICATIE");
			jBtnNieuweCommunicatie.setPreferredSize(new Dimension(175, 40));
			jBtnNieuweCommunicatie.addActionListener(this);
		}
		return jBtnNieuweCommunicatie;
	}

	public JButton getjBtnWijzigCommunicatie() {
		if (jBtnWijzigCommunicatie == null) {
			jBtnWijzigCommunicatie = new JButton();
			jBtnWijzigCommunicatie.setText("WIJZIG COMMUNICATIE");
			jBtnWijzigCommunicatie.setPreferredSize(new Dimension(175, 40));
			jBtnWijzigCommunicatie.addActionListener(this);
		}
		return jBtnWijzigCommunicatie;
	}

	public JButton getjBtnVerwijderCommunicatie() {
		if (jBtnVerwijderCommunicatie == null) {
			jBtnVerwijderCommunicatie = new JButton();
			jBtnVerwijderCommunicatie.setText("VERWIJDER COMMUNICATIE");
			jBtnVerwijderCommunicatie.setPreferredSize(new Dimension(200, 40));
			jBtnVerwijderCommunicatie.addActionListener(this);
		}
		return jBtnVerwijderCommunicatie;
	}

	public JPanel getjPanelCommunicatie() {
		if (jPanelCommunicatie == null) {
			jPanelCommunicatie = new JPanel();
			JLabel jLabelCommunicatie = new JLabel("   KIES COMMUNICATIE: ");
			jPanelCommunicatie.setLayout(new GridLayout(1, 5));
			jPanelCommunicatie.add(getjBtnNieuweCommunicatie());
			jPanelCommunicatie.add(jLabelCommunicatie);
			jPanelCommunicatie.add(getjCboEditCommunicatie());
			jPanelCommunicatie.add(getjBtnWijzigCommunicatie());
			jPanelCommunicatie.add(getjBtnVerwijderCommunicatie());
		}
		return jPanelCommunicatie;
	}

	public JButton getjBtnRemoveGeschiedenis() {
		if (jBtnRemoveGeschiedenis == null) {
			jBtnRemoveGeschiedenis = new JButton("Verwijder Geschiedenis");
			jBtnRemoveGeschiedenis.setPreferredSize(new Dimension(175, 40));
			jBtnRemoveGeschiedenis.addActionListener(this);
		}
		return jBtnRemoveGeschiedenis;
	}

	private void resetTable() {
		GeschiedenisTableModel model = new GeschiedenisTableModel();
	    jTable = new JTable(model);
	    jTable.setFillsViewportHeight(true);
	    jTable.setAutoCreateRowSorter(true);
	    jTable.setForeground(Color.BLACK);
	    jTable.setFont(font);
	    jTable.setRowHeight(80);
	    jTable.getTableHeader().setFont(font);
	    jTable.setGridColor(Color.GRAY);
	    jTable.getTableHeader().addMouseListener(new ColumnHeaderListener(model));
	    jTable.addMouseListener(new MouseAdapter()
		{
		    public void mouseClicked(MouseEvent e)
		    {
		        if (e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
		        {
		            Point p = e.getPoint();
		            int row = jTable.rowAtPoint(p);
		            int col = jTable.columnAtPoint(p);
		            handleDoubleClick(row, col);
		        }
		    }
		}
		);
//	    jTable.setDefaultRenderer(String.class, new MultiLineCellRenderer());
	    setUpVerwittigingColumn(jTable.getColumnModel().getColumn(0));
	    setUpGeschiedenisColumn(jTable.getColumnModel().getColumn(5));
	    initColumnSizes(jTable);
	}
	
	public void handleDoubleClick(int row, int col) {
		GeschiedenisTableModel model = (GeschiedenisTableModel)getjTable().getModel();
		GeschiedenisDTO geschiedenis = model.getSelectedGeschiedenis(row);
		if (col == 0) {
			
		} else if (col == 1) {
			String message = "WENST U DEZE GESCHIEDENIS TE VERWIJDEREN? DEZE HANDELING IS NIET OMKEERBAAR!";
			String[] choices = {"JA", "NEE"};
			int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if (result == 0) {
				getStandplaatsService().removeGeschiedenis(geschiedenis);
				model.removeGeschiedenis(row);
			}
		} else {
			JCheckBox tmpJCboVerwittiging = new JCheckBox();
			tmpJCboVerwittiging.setText("verplicht");
			if (geschiedenis.getVerwittiging()) tmpJCboVerwittiging.setSelected(true);
			
			JTextArea tmpJTAGeschiedenis = new JTextArea();
			tmpJTAGeschiedenis.setText(geschiedenis.getGeschiedenis());
			tmpJTAGeschiedenis.setLineWrap(true);
			tmpJTAGeschiedenis.setWrapStyleWord(true);
			tmpJTAGeschiedenis.setColumns(30);
			tmpJTAGeschiedenis.setRows(25);
			tmpJTAGeschiedenis.setFont(font);
			tmpJTAGeschiedenis.setBackground(Color.WHITE);
			JScrollPane tmpJScrollpane = new JScrollPane(tmpJTAGeschiedenis);
//			tmpJScrollpane.setSize(new Dimension(300, 600));
			
			JComboBox tmpJCboNamen = new JComboBox(Constant.NAMES);
			tmpJCboNamen.setSelectedItem(geschiedenis.getNaam().getTranslationKey());
			
			JDatePicker tmpDatePicker = JDateComponentFactory.createJDatePicker();
			tmpDatePicker.setTextEditable(true);
			tmpDatePicker.setShowYearButtons(true);
			tmpDatePicker.getJFormattedTextField().setPreferredSize(new Dimension(150, 30));
			tmpDatePicker.getJFormattedTextField().setFont(font);
			Calendar cal = Calendar.getInstance();
			cal.setTime(geschiedenis.getDatum());
			tmpDatePicker.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

			JComboBox tmpJCboThema;
			List<GeschiedenisThemaDTO> themas = getThemas();
			if (themas != null) {
				String[] sThemas = new String[themas.size()+1];
				sThemas[0] = "";
				for (int i = 0; i < themas.size(); i++) {
					GeschiedenisThemaDTO thema = themas.get(i);
					sThemas[i+1] = thema.getThema();
				}
				tmpJCboThema = new JComboBox(sThemas);
				tmpJCboThema.setSelectedItem(geschiedenis.getThema());
			} else {
				String[] sThemas = {""};
				tmpJCboThema = new JComboBox(sThemas);
			}			

			JComboBox tmpJCboCommunicatie;
			List<GeschiedenisCommunicatieDTO> communicaties = getCommunicaties();
			if (communicaties != null) {
				String[] sCommunicaties = new String[communicaties.size()+1];
				sCommunicaties[0] = "";
				for (int i = 0; i < communicaties.size(); i++) {
					GeschiedenisCommunicatieDTO communicatie = communicaties.get(i);
					sCommunicaties[i+1] = communicatie.getCommunicatie();
				}
				tmpJCboCommunicatie = new JComboBox(sCommunicaties);
				tmpJCboCommunicatie.setSelectedItem(geschiedenis.getCommunicatie());
			} else {
				String[] sCommunicaties = {""};
				tmpJCboCommunicatie = new JComboBox(sCommunicaties);
			}			

			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridLayout(5, 1));
			jPanel.add(tmpJCboVerwittiging);
			jPanel.add((JComponent)tmpDatePicker);
			jPanel.add(tmpJCboNamen);
			jPanel.add(tmpJCboThema);
			jPanel.add(tmpJCboCommunicatie);
			
			JPanel jPanelMain = new JPanel();
			jPanelMain.setLayout(new BorderLayout());
			jPanelMain.add(jPanel, BorderLayout.NORTH);
			jPanelMain.add(tmpJScrollpane, BorderLayout.CENTER);
			
			Object[] array = {jPanelMain};
			int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == 0) {
				geschiedenis.setVerwittiging(tmpJCboVerwittiging.isSelected());
				Date date = new Date() ;
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy k:mm");
				try {
					Date currentDate = tmpDatePicker.getTime();
					String sCurrentDate = sdf.format(currentDate);
					if (!DateUtilities.equals(sCurrentDate, sdf.format(geschiedenis.getDatum()), new SimpleDateFormat("dd-MM-yyyy"))) {
						String[] currentDates = sCurrentDate.split(" ");
						String sDate = sdf.format(date);
						String[] sDates = sDate.split(" ");
						String newDate = currentDates[0] + " " + sDates[1]; 
						System.err.println(newDate);
						geschiedenis.setDatum(sdf.parse(newDate));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				geschiedenis.setNaam(GebruikerEnum.getGebruikerEnum((String)tmpJCboNamen.getSelectedItem()));
				geschiedenis.setCommunicatie((String)tmpJCboCommunicatie.getSelectedItem());
				geschiedenis.setThema((String)tmpJCboThema.getSelectedItem());
				geschiedenis.setGeschiedenis(tmpJTAGeschiedenis.getText());
				StandplaatsDTO standplaats = getController().getStandplaatsDTO();
				for (GeschiedenisDTO tmpGeschiedenis : standplaats.getGeschiedenisPunten()) {
					if (tmpGeschiedenis.getId() == geschiedenis.getId()) {
						standplaats.getGeschiedenisPunten().remove(tmpGeschiedenis);
						standplaats.getGeschiedenisPunten().add(geschiedenis);
						getStandplaatsService().storeStandplaats(standplaats);
						model.fireTableDataChanged();
						break;
					}
				}
			}
		}
	}
	
    class CustomRenderer implements TableCellRenderer {  
        JScrollPane scrollPane;  
        JTextArea textArea;  
       
        public CustomRenderer() {  
            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(font);
            scrollPane = new JScrollPane(textArea);
        }  
       
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,  
                                                       int row, int column) {  
            textArea.setText((String)value);  
            return scrollPane;  
        }  
    } 
   
	private void setUpGeschiedenisColumn(TableColumn geschiedenisColumn) {
		geschiedenisColumn.setCellRenderer(new CustomRenderer());
	}

    private void setUpVerwittigingColumn(TableColumn verwittigingsColumn) {
    	final JCheckBox checkBox = new JCheckBox();  
		final JCheckBox edCheckBox = new JCheckBox(); 
		
//		verwittigingsColumn.setCellEditor(new DefaultCellEditor(jCheckBox));
         checkBox.setHorizontalAlignment(SwingConstants.CENTER);
         edCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		           
         verwittigingsColumn.setCellEditor(new DefaultCellEditor(edCheckBox) {  

			private static final long serialVersionUID = 171747182447010669L;

			public Component getTableCellEditorComponent(JTable table, Object val, boolean isSelected, int row, int col) {  
                  if (val instanceof GeschiedenisDTO) {  
                      val = Boolean.valueOf( ((GeschiedenisDTO)val).getVerwittiging() );  
                  }
                  return super.getTableCellEditorComponent(table, val, isSelected, row, col);  
              }});  
         verwittigingsColumn.setResizable(false);  
//		DefaultTableCellRenderer renderer =	new DefaultTableCellRenderer();
         verwittigingsColumn.setCellRenderer(new DefaultTableCellRenderer() {  

			private static final long serialVersionUID = -177156601672206616L;

			public JCheckBox getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
            		 										boolean hasFocus, int row, int column) { 
	                 if (value == null) {  
	                     checkBox.setSelected(false);  
	                 } else if (value instanceof GeschiedenisDTO) {
	                     checkBox.setSelected(((GeschiedenisDTO)value).getVerwittiging());
	                 }  
	                 return checkBox;  
	             }  
	         });
	}

    private void initColumnSizes(JTable table) {    			
		TableColumn colVnv = table.getColumnModel().getColumn(0);
		colVnv.setPreferredWidth(75);
		colVnv.setMaxWidth(75);
		colVnv.setMinWidth(75);
		TableColumn colDatum = table.getColumnModel().getColumn(1);
		colDatum.setPreferredWidth(165);
		colDatum.setMaxWidth(165);
		colDatum.setMinWidth(165);
		TableColumn colNaam = table.getColumnModel().getColumn(2);
		colNaam.setPreferredWidth(100);
		colNaam.setMaxWidth(100);
		colNaam.setMinWidth(100);
		TableColumn colThema = table.getColumnModel().getColumn(3);
		colThema.setPreferredWidth(100);
		colThema.setMaxWidth(100);
		colThema.setMinWidth(100);
		TableColumn colComm = table.getColumnModel().getColumn(4);
		colComm.setPreferredWidth(165);
		colComm.setMaxWidth(165);
		colComm.setMinWidth(165);
    }

    class OpslaanActionListener extends AbstractAction {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		OpslaanActionListener(String s) {
	      super(s);
	    }

	    public void actionPerformed(ActionEvent e) {
	    	jBtnOpslaan.doClick();
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getjBtnThema()) {
			Object[] array = {getjPanelThema()};
			JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		} else if (e.getSource() == getjBtnCommunicatie()) {
			Object[] array = {getjPanelCommunicatie()};
			JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		} else if (e.getSource() == getjBtnNieuwThema()) {
			JLabel jLabel = new JLabel("NIEUW THEMA: ");
			JTextField jTxtNieuwThema = new JTextField();
			jTxtNieuwThema.setPreferredSize(new Dimension(200, 28));
			jTxtNieuwThema.setMinimumSize(new Dimension(150, 28));
			jTxtNieuwThema.addAncestorListener(new RequestFocusListener());
			jTxtNieuwThema.setFont(font);
			JPanel jPanel = new JPanel();
			jPanel.add(jLabel);
			jPanel.add(jTxtNieuwThema);
			Object[] array = {jPanel};
			int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtNieuwThema.getText())) {
				GeschiedenisThemaDTO geschiedenisThema = new GeschiedenisThemaDTO();
				geschiedenisThema.setThema(jTxtNieuwThema.getText());
				geschiedenisThema = getGeschiedenisThemaService().storeGeschiedenisThema(geschiedenisThema);
				getThemas().add(geschiedenisThema);
				getjCboEditThema().addItem(geschiedenisThema.getThema());
				getjCboThema().addItem(geschiedenisThema.getThema());
			}
		} else if (e.getSource() == getjBtnNieuweCommunicatie()) {
			JLabel jLabel = new JLabel("NIEUWE COMMUNICATIE: ");
			JTextField jTxtNieuweCommunicatie = new JTextField();
			jTxtNieuweCommunicatie.setPreferredSize(new Dimension(200, 28));
			jTxtNieuweCommunicatie.setMinimumSize(new Dimension(150, 28));
			jTxtNieuweCommunicatie.addAncestorListener(new RequestFocusListener());
			jTxtNieuweCommunicatie.setFont(font);
			JPanel jPanel = new JPanel();
			jPanel.add(jLabel);
			jPanel.add(jTxtNieuweCommunicatie);
			Object[] array = {jPanel};
			int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtNieuweCommunicatie.getText())) {
				GeschiedenisCommunicatieDTO geschiedenisCommunicatie = new GeschiedenisCommunicatieDTO();
				geschiedenisCommunicatie.setCommunicatie(jTxtNieuweCommunicatie.getText());
				geschiedenisCommunicatie = getGeschiedenisCommunicatieService().storeGeschiedenisCommunicatie(geschiedenisCommunicatie);
				getCommunicaties().add(geschiedenisCommunicatie);
				getjCboEditCommunicatie().addItem(geschiedenisCommunicatie.getCommunicatie());
				getjCboCommunicatie().addItem(geschiedenisCommunicatie.getCommunicatie());
			}
		} else if (e.getSource() == getjBtnWijzigThema()) {
			String thema = (String)getjCboEditThema().getSelectedItem();
			int index = getjCboEditThema().getSelectedIndex();
			if (!StringUtils.isEmpty(thema)) {
				JLabel jLabel = new JLabel("WIJZIG THEMA: ");
				JTextField jTxtThema = new JTextField();
				jTxtThema.setPreferredSize(new Dimension(150, 28));
				jTxtThema.setMinimumSize(new Dimension(120, 28));
				jTxtThema.addAncestorListener(new RequestFocusListener());
				jTxtThema.setFont(font);
				jTxtThema.setText(thema);
				JPanel jPanel = new JPanel();
				jPanel.add(jLabel);
				jPanel.add(jTxtThema);
				Object[] array = {jPanel};
				int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtThema.getText())) {
					GeschiedenisThemaDTO geschiedenisThema = getGeschiedenisThemaService().findGeschiedenisThema(thema);
					geschiedenisThema.setThema(jTxtThema.getText());
					geschiedenisThema = getGeschiedenisThemaService().storeGeschiedenisThema(geschiedenisThema);
					for (GeschiedenisThemaDTO gt : getThemas()) {
						if (gt.getId() == geschiedenisThema.getId()) {
							gt.setThema(jTxtThema.getText());
						}
					}
					getjCboEditThema().removeItemAt(index);
					getjCboThema().removeItemAt(index);
					getjCboEditThema().insertItemAt(jTxtThema.getText(), index);
					getjCboThema().insertItemAt(jTxtThema.getText(), index);
				}
			}
		} else if (e.getSource() == getjBtnWijzigCommunicatie()) {
			String communicatie = (String)getjCboEditCommunicatie().getSelectedItem();
			int index = getjCboEditCommunicatie().getSelectedIndex();
			if (!StringUtils.isEmpty(communicatie)) {
				JLabel jLabel = new JLabel("WIJZIG COMMUNICATIE: ");
				JTextField jTxtCommunicatie = new JTextField();
				jTxtCommunicatie.setPreferredSize(new Dimension(150, 28));
				jTxtCommunicatie.setMinimumSize(new Dimension(120, 28));
				jTxtCommunicatie.addAncestorListener(new RequestFocusListener());
				jTxtCommunicatie.setFont(font);
				jTxtCommunicatie.setText(communicatie);
				JPanel jPanel = new JPanel();
				jPanel.add(jLabel);
				jPanel.add(jTxtCommunicatie);
				Object[] array = {jPanel};
				int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.OK_OPTION && !StringUtils.isEmpty(jTxtCommunicatie.getText())) {
					GeschiedenisCommunicatieDTO geschiedenisCommunicatie = getGeschiedenisCommunicatieService().findGeschiedenisCommunicatie(communicatie);
					geschiedenisCommunicatie.setCommunicatie(jTxtCommunicatie.getText());
					geschiedenisCommunicatie = getGeschiedenisCommunicatieService().storeGeschiedenisCommunicatie(geschiedenisCommunicatie);
					for (GeschiedenisCommunicatieDTO gc : getCommunicaties()) {
						if (gc.getId() == geschiedenisCommunicatie.getId()) {
							gc.setCommunicatie(jTxtCommunicatie.getText());
						}
					}
					getjCboEditCommunicatie().removeItemAt(index);
					getjCboCommunicatie().removeItemAt(index);
					getjCboEditCommunicatie().insertItemAt(jTxtCommunicatie.getText(), index);
					getjCboCommunicatie().insertItemAt(jTxtCommunicatie.getText(), index);
				}
			}
		} else if (e.getSource() == getjBtnVerwijderThema()) {
			String thema = (String)getjCboEditThema().getSelectedItem();
			int index = getjCboEditThema().getSelectedIndex();
			if (!StringUtils.isEmpty(thema)) {
				int ret = JOptionPane.showConfirmDialog(getjPanelThema(), "Bent u zeker dat u dit thema wil verwijderen?");
				if (ret == JOptionPane.OK_OPTION) {
					GeschiedenisThemaDTO geschiedenisThema = getGeschiedenisThemaService().findGeschiedenisThema(thema);
					getGeschiedenisThemaService().remove(geschiedenisThema);
					Iterator<GeschiedenisThemaDTO> iterator = getThemas().iterator();
					while (iterator.hasNext()) {
					    GeschiedenisThemaDTO gt = iterator.next();
                        if (gt.getId() == geschiedenisThema.getId()) {
                            iterator.remove();
                            break;
                        }
                    }
					getjCboEditThema().removeItemAt(index);
					getjCboThema().removeItemAt(index);
				}
			}
		} else if (e.getSource() == getjBtnVerwijderCommunicatie()) {
			String communicatie = (String)getjCboEditCommunicatie().getSelectedItem();
			int index = getjCboEditCommunicatie().getSelectedIndex();
			if (!StringUtils.isEmpty(communicatie)) {
				int ret = JOptionPane.showConfirmDialog(getjPanelCommunicatie(), "Bent u zeker dat u deze soort communictatie wil verwijderen?");
				if (ret == JOptionPane.OK_OPTION) {
					GeschiedenisCommunicatieDTO geschiedenisCommunicatie = getGeschiedenisCommunicatieService().findGeschiedenisCommunicatie(communicatie);
					getGeschiedenisCommunicatieService().removeGeschiedenisCommunicatie(geschiedenisCommunicatie);
                    Iterator<GeschiedenisCommunicatieDTO> iterator = getCommunicaties().iterator();
                    while (iterator.hasNext()) {
                        GeschiedenisCommunicatieDTO gc = iterator.next();
                        if (gc.getId() == geschiedenisCommunicatie.getId()) {
                            iterator.remove();
                            break;
                        }
                    }
					getjCboEditCommunicatie().removeItemAt(index);
					getjCboCommunicatie().removeItemAt(index);
				}
			}
		} else if (e.getSource() == getJBtnOpslaan()) {
			save();
			resetTable();
			getjScrollPane().setViewportView(getjTable());
		}
	}
	
	private class GeschiedenisTableModel extends AbstractTableModel implements TableModelListener{

		private static final long serialVersionUID = -5813307524313334752L;
		private String[] columnNames = getColumnNames();
		private List<GeschiedenisDTO> data = null;
		private boolean tableChanged = false;
        private boolean col0SortAsc = false;
        private boolean col1SortAsc = false;
        private boolean col2SortAsc = false;
        private boolean col3SortAsc = false;
        private boolean col4SortAsc = false;
        private boolean col5SortAsc = false;
		
		public GeschiedenisTableModel() {
			data = getColumnData();
		}

		public List<GeschiedenisDTO> getData() {
			return data;
		}

        public boolean isTableChanged() {
			return tableChanged;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}
		
		public GeschiedenisDTO getSelectedGeschiedenis(int row) {
			return data.get(row);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			GeschiedenisDTO geschiedenis = data.get(rowIndex);
			Object o = null;
			switch (columnIndex) {
				case 0: o = geschiedenis;
						break;
				case 1: if (geschiedenis.getDatum() != null) {
        					String formattedDate = sdf.format(geschiedenis.getDatum());
			        		o = formattedDate;
		        		} else {
		        			o = "";
		        		}
						break;
				case 2: o = (geschiedenis.getNaam() == null)?"":geschiedenis.getNaam().getTranslationKey();
						break;
				case 3: o = (geschiedenis.getThema() == null)?"":geschiedenis.getThema();
						break;
				case 4: o = (geschiedenis.getCommunicatie() == null)?"":geschiedenis.getCommunicatie();
						break;
				case 5: o = (geschiedenis.getGeschiedenis() == null)?"":geschiedenis.getGeschiedenis();
						break;
				default: break;
			}
			return o;
		}
		
        public void setValueAt(Object value, int row, int col) {
        	GeschiedenisDTO geschiedenis = data.get(row);
        	switch (col) {
	        	case 0: geschiedenis.setVerwittiging(((Boolean) value).booleanValue());
	        			break;
        	}
        	fireTableCellUpdated(row, col);
        }
        
        public void removeGeschiedenis(int index) {
        	data.remove(index);
        	fireTableDataChanged();
        }
        
	    private String[] getColumnNames() {
	        String[] columnNames = {"V/N.V.", "Datum", "Naam", "Thema", "Communicatie", "Geschiedenis"};
	        return columnNames;
	    }

        public String getColumnName(int col) {
            return columnNames[col];
        }

	    private List<GeschiedenisDTO> getColumnData() {
	    	List<GeschiedenisDTO> data = new ArrayList<GeschiedenisDTO>();
	    	StandplaatsDTO standplaats 
	    		= getStandplaatsService().getsStandplaatsMetGeschiedenis(getController().getStandplaatsDTO().getId());
	    	getController().getStandplaatsDTO().setGeschiedenisPunten(standplaats.getGeschiedenisPunten());
	    	for (GeschiedenisDTO geschiedenis : standplaats.getGeschiedenisPunten()) {
	    		boolean added = false;
	    		for (GeschiedenisDTO geschiedenisCopy : data) {
	    			if (geschiedenis.getDatum().after(geschiedenisCopy.getDatum())) {
	    				int index = data.indexOf(geschiedenisCopy);
	    				data.add(index, geschiedenis);
	    				added = true;
	    				break;
	    			}
	    		}
	    		if (!added) {
	    			data.add(geschiedenis);
	    		}
	    	}
	    	return data;
	    }	    

        public void sortData(int col) {
        	boolean sortAsc = getSorting(col);
        	if (col > 1) {
        		sortAlphabetically(col, sortAsc);
        	} else if (col == 1) {
        		sortDate(sortAsc);
        	} else if (col == 0) {
        		sortBoolean(sortAsc);
        	}
        	fireTableDataChanged();
        }
        
        private void sortAlphabetically(int col, boolean asc) {
        	List<GeschiedenisDTO> tmpData = new ArrayList<GeschiedenisDTO>();
        	for (GeschiedenisDTO geschiedenis1 : data) {
        		boolean added = false;
        		String s1 = "";
        		switch (col) {
        			case 2: s1 = geschiedenis1.getNaam().getTranslationKey();
        					break;
        			case 3: s1 = geschiedenis1.getThema();
							break;
        			case 4: s1 = geschiedenis1.getCommunicatie();
							break;
        			case 5: s1 = geschiedenis1.getGeschiedenis();
							break;
					default:break;
        		}
        		for (GeschiedenisDTO geschiedenis2 : tmpData) {
        			String s2 = "";
            		switch (col) {
	        			case 2: s2 = geschiedenis2.getNaam().getTranslationKey();
	        					break;
	        			case 3: s2 = geschiedenis2.getThema();
								break;
	        			case 4: s2 = geschiedenis2.getCommunicatie();
								break;
	        			case 5: s2 = geschiedenis2.getGeschiedenis();
								break;
						default:break;
        		}
        			int i  = s1.compareTo(s2);
            		if ((asc && i < 0) || (!asc && i > 0)) {
            			tmpData.add(tmpData.indexOf(geschiedenis2), geschiedenis1);
            			added = true;
            			break;
            		}                			
        		}
        		if (!added) {
        			tmpData.add(geschiedenis1);
        		}
    		}
        	data = tmpData;
        }
        
        private void sortDate(boolean asc) {
        	List<GeschiedenisDTO> tmpData = new ArrayList<GeschiedenisDTO>();
        	for (GeschiedenisDTO geschiedenis1 : data) {
        		boolean added = false;
				Calendar cal1 = Calendar.getInstance();
				Date date1 = geschiedenis1.getDatum();
				try {
					cal1.setTime(date1);
					for (GeschiedenisDTO geschiedenis2 : tmpData) {
						Calendar cal2 = Calendar.getInstance();
						Date date2 = geschiedenis2.getDatum();
						cal2.setTime(date2);
						System.err.println(cal1.before(cal2));
						if((asc && cal1.before(cal2)) || (!asc && cal1.after(cal2))) {
							tmpData.add(tmpData.indexOf(geschiedenis2), geschiedenis1);
							added = true;
							break;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("date not correctly parsed");
				} 
				if (!added) {
					tmpData.add(geschiedenis1);
				}
        	}
        	data = tmpData;
        }
        
        private void sortBoolean(boolean asc) {
        	List<GeschiedenisDTO> tmpData = new ArrayList<GeschiedenisDTO>();
        	for (GeschiedenisDTO geschiedenis : data) {
        		if (asc) {
    				if (geschiedenis.getVerwittiging()) {
    					tmpData.add(0, geschiedenis);
    				} else {
    					tmpData.add(geschiedenis);
    				}
        		} else {
    				if (geschiedenis.getVerwittiging()) {
    					tmpData.add(geschiedenis);
    				} else {
    					tmpData.add(0, geschiedenis);
    				}
        		}
        	}
        	data = tmpData;
        }
            
        private boolean getSorting(int col) {
        	boolean ret = false;
        	switch (col) {
			case 0:
				ret = col0SortAsc = (col0SortAsc)?false:true;
				break;
			case 1:
				ret = col1SortAsc = (col1SortAsc)?false:true;
				break;
			case 2:
				ret = col2SortAsc = (col2SortAsc)?false:true;
				break;
			case 3:
				ret = col3SortAsc = (col3SortAsc)?false:true;
				break;
			case 4:
				ret = col4SortAsc = (col4SortAsc)?false:true;
				break;
			case 5:
				ret = col5SortAsc = (col5SortAsc)?false:true;
				break;
			default:
				break;
			}
        	return ret;
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
        	if (col == 0) {
        		return true;
        	} else {
                return false;
        	}
        }

		@Override
		public void tableChanged(TableModelEvent e) {
			tableChanged = true;
		}
	}
	
	public class ColumnHeaderListener extends MouseAdapter {
		
		GeschiedenisTableModel model;
		
		public ColumnHeaderListener(GeschiedenisTableModel model) {
			this.model = model;
		}
		
		public void mouseClicked(MouseEvent evt) { 
			JTable table = ((JTableHeader)evt.getSource()).getTable();
			TableColumnModel colModel = table.getColumnModel();
			// The index of the column whose header was clicked 
			int vColIndex = colModel.getColumnIndexAtX(evt.getX());
			
			model.sortData(vColIndex);
		}
	}

}
