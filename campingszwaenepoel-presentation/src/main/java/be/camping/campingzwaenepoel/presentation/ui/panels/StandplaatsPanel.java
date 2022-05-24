package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.dialog.SaveDialog;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.listeners.RequestFocusListener;
import be.camping.campingzwaenepoel.presentation.ui.layout.GridLayout2;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.StandplaatsDetailService;
import be.camping.campingzwaenepoel.service.StandplaatsInformatieService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.GrondInformatieDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDetailDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class StandplaatsPanel extends JPanel implements PanelInterface, ActionListener {

	@Autowired
	private StandplaatsService standplaatsService;

    @Autowired
    private StandplaatsDetailService standplaatsDetailService;

    @Autowired
    private StandplaatsInformatieService standplaatsInformatieService;

	private static final long serialVersionUID = 1L;

	private Dimension txtFieldDim = new Dimension(300, 30);
	private Dimension txtFieldNamesDim = new Dimension(150, 30);
	
    Font labelFont = new Font("Times New Roman", Font.BOLD, 18);
    
    private JPanel panelButtons;
    private JPanel panelFields = new JPanel();
    private JPanel panelFieldsMandatory;
    private JPanel panelFieldsNotMandatory;
    
    private JButton btnSave;
    private JButton btnNewField;
    private JButton btnEditField;
    private Java2sAutoComboBox cboStandplaatsDetails;
    private JButton btnDelete;
    
    private Controller controller;

    private PropertyChangeDateListener propertyChangeDateListener;
    
    Color colorErrorTab = Constant.ERROR_COLOR;
    
    private Dimension buttonDimension = new Dimension(145, 40);
    
    private List<StandplaatsDetailDTO> standplaatsDetailDTOs;
    private List<StandplaatsPropertyData> standplaatsProperties;
    
    private GrondListener grondListener;

    private Insets insets = new Insets(5, 20, 10, 10);
    
	public void initComponents() {
		
		BorderLayout layout = new BorderLayout();

		this.setLayout(layout);
		this.setBorder(new LineBorder(Color.BLACK, 2));
		
		standplaatsDetailDTOs = standplaatsDetailService.getStandplaatsDetails();
		
		grondListener = new GrondListener();
		initPanelButtons();
		this.add(panelButtons, BorderLayout.NORTH);
		
		createFieldsPanel();
		this.add(panelFields, BorderLayout.CENTER);
	}
	
	private void initPanelButtons() {
		initBtnSave();
		cboStandplaatsDetails = new Java2sAutoComboBox(getStandplaatsDetailList());
		initBtnNewField();
		initBtnEditField();
		initBtnDelete();
		initPanelFieldsMandatory();
		initPanelFieldsNotMandatory();
		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());
		panelButtons.add(btnSave);
		panelButtons.add(cboStandplaatsDetails);
		panelButtons.add(btnNewField);
		panelButtons.add(btnEditField);
		panelButtons.add(btnDelete);
		panelButtons.setBorder(new LineBorder(Color.BLACK, 1));
	}
	
	private void initBtnSave() {
		btnSave = new JButton();
		btnSave.setPreferredSize(buttonDimension);
		btnSave.setText("OPSLAAN");
		
		String actionKey = "opslaan";
		KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
		Action action = new OpslaanActionListener("Opslaan");
	    InputMap inputMap = btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    inputMap.put(stroke, actionKey);
	    ActionMap actionMap = btnSave.getActionMap();
	    actionMap.put(actionKey, action);
		btnSave.addActionListener(this);
	}

	public void initBtnNewField() {
		btnNewField = new JButton();
		btnNewField.setPreferredSize(buttonDimension);
		btnNewField.setText("NIEUW VELD");
		btnNewField.addActionListener(this);
	}

	public void initBtnEditField() {
		btnEditField = new JButton();
		btnEditField.setPreferredSize(buttonDimension);
		btnEditField.setText("WIJZIG VELD");
		btnEditField.addActionListener(this);
	}

	public void initBtnDelete() {
		btnDelete = new JButton();
		btnDelete.setPreferredSize(buttonDimension);
		btnDelete.setText("VERWIJDER");
		btnDelete.addActionListener(this);
	}

	public void initPanelFieldsMandatory() {
		panelFieldsMandatory = new JPanel();
		panelFieldsMandatory.setLayout(new GridBagLayout());
	}
	
	public void initPanelFieldsNotMandatory() {
		panelFieldsNotMandatory = new JPanel();
		panelFieldsNotMandatory.setLayout(new GridBagLayout());
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public StandplaatsDTO getStandplaatsDTO() {
		return controller.getStandplaatsDTO();
	}

	public void setStandplaatsProperties(List<StandplaatsPropertyData> standplaatsProperties) {
		this.standplaatsProperties = standplaatsProperties;
	}

	public class GrondListener implements DocumentListener {
		
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

	public class OpslaanHandler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			save();
		}
	}

	class OpslaanActionListener extends AbstractAction {

		private static final long serialVersionUID = 1L;

		OpslaanActionListener(String s) {
	      super(s);
	    }

	    public void actionPerformed(ActionEvent e) {
	    	btnSave.doClick();
	    }
	}

	public PropertyChangeDateListener getPropertyChangeDateListener() {
		if (propertyChangeDateListener == null) {
			propertyChangeDateListener = new PropertyChangeDateListener();
		}
		return propertyChangeDateListener;
	}

	public class PropertyChangeDateListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent arg0) {
			// TODO Auto-generated method stub
			
//			setRemarksInGui();
		}
		
	}

	public boolean checkData() {
		boolean ret = true;
		for (StandplaatsPropertyData spd : standplaatsProperties) {
			if (!spd.isFieldFilledIn()) {
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	public boolean checkDataChanged() {
		boolean ret = false;
		for (StandplaatsPropertyData spd : standplaatsProperties) {
			if (spd.isChanged()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public boolean checkDataForParent() {
		boolean ret = true;
		Set<GrondInformatieDTO> grondInformatieDTOs = controller.getStandplaatsDTO().getGrondInformaties();
		for (StandplaatsDetailDTO standplaatsDetailDTO : standplaatsDetailDTOs) {
			GrondInformatieDTO standplaatsInformatieDTO = null;
			for (GrondInformatieDTO giDTO : grondInformatieDTOs) {
				if (giDTO.getNummer().equals(standplaatsDetailDTO.getNummer())) {
					standplaatsInformatieDTO = giDTO;
					break;
				}
			}
			if (!standplaatsDetailDTO.isVerplicht()) continue;
			if (standplaatsInformatieDTO == null) {
				ret = false;
				break;
			}
			if (StringUtils.isEmpty(standplaatsInformatieDTO.getWaarde())) {
				ret = false;
				break;
			}
			if (StringUtils.isEmpty(standplaatsDetailDTO.getNaam())) {
				ret = false;
				break;
			}
			if (standplaatsDetailDTO.isDatumveld()) {
				if (isValidDate(standplaatsInformatieDTO.getWaarde())) {
					Calendar cal = Calendar.getInstance();
					Calendar now = Calendar.getInstance();
					now.setTime(new Date());
					String sDate = standplaatsInformatieDTO.getWaarde();
					sDate = sDate.replace("/", "-");
					DateFormat formatter; 
				    formatter = new SimpleDateFormat("dd-MM-yyyy");
				    Date date;
					try {
						date = (Date)formatter.parse(sDate);
						cal.setTime(date);
						if(cal.before(now)) {
							ret = false;
						}
					} catch (ParseException e) {
						System.err.println("date not correctly parsed");
						ret = false;
					} 
				}
			}
		}
		return ret;
	}

	/**
	 * TODO: hier gekomen --> save aanpassen
	 */
	public Object getDataFromGUI() {
		Set<GrondInformatieDTO> grondInformaties = controller.getStandplaatsDTO().getGrondInformaties();
		for (StandplaatsPropertyData spd : standplaatsProperties) {
			grondInformaties.add(spd.updateGrondInformatie());
		}
		controller.getStandplaatsDTO().setGrondInformaties(grondInformaties);
		return controller.getStandplaatsDTO();
	}

	public void save() {
		StandplaatsDTO standplaatsDTO = (StandplaatsDTO)getDataFromGUI();
		standplaatsService.storeStandplaats(standplaatsDTO);
		for (StandplaatsPropertyData spd : standplaatsProperties) {
			if (spd.getGrondInformatie().getId() == null || spd.getGrondInformatie().getId() == 0) {
				for (GrondInformatieDTO grondInformatie : standplaatsDTO.getGrondInformaties()) {
					if (spd.getGrondInformatie().getNummer().equals(grondInformatie.getNummer())) {
						spd.getGrondInformatie().setId(grondInformatie.getId());
						break;
					}
				}
			}
		}
	}
	
	public void setDataInGUI() {
		clearTextFieldValues();
		if (controller.getStandplaatsDTO() != null && controller.getStandplaatsDTO().getId() != 0) {
			for (GrondInformatieDTO grondInformatieDTO : controller.getStandplaatsDTO().getGrondInformaties()) {
				StandplaatsPropertyData spd = getStandplaatsPropertyData(grondInformatieDTO.getNummer());
				if (spd != null)
				spd.setGrondInformatie(grondInformatieDTO);
			}
		}
	}

	public void setRemarksInGui() {
		boolean b = checkData();
		controller.updateDataForPanel(b, 3);
	}
	
	private void clearTextFieldValues() {
		for (StandplaatsPropertyData spd : standplaatsProperties) {
			spd.clearField();
		}
	}
	
	private List<String> getStandplaatsDetailList() {
		List<String> nummers = new ArrayList<String>();
		for (StandplaatsDetailDTO standplaatsDetailDTO : standplaatsDetailDTOs) {
			if (!StringUtils.isEmpty(standplaatsDetailDTO.getNaam())) {
				nummers.add(standplaatsDetailDTO.getNaam());
			} else {
				nummers.add("");
			}
		}
		if (!nummers.contains("")) {
			nummers.add(0, "");
		}
		return nummers;
	}
	
	private void createFieldsPanel() {
		panelFields.removeAll();
		boolean mandatoryFields = false;
		standplaatsProperties = new ArrayList<StandplaatsPropertyData>();
		int teller1 = 1, teller2 = 1;
		for (StandplaatsDetailDTO standplaatsDetailDTO : standplaatsDetailDTOs) {
			if (standplaatsDetailDTO.isVerplicht()) {
				mandatoryFields = true;
				teller2++;
			}
		}
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		for (StandplaatsDetailDTO standplaatsDetailDTO : standplaatsDetailDTOs) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.insets = insets;
			if (standplaatsDetailDTO.isVerplicht()) {
				gridBagConstraints.gridx = x1;
				gridBagConstraints.gridy = y1;
				y1++;
				StandplaatsPropertyData spd = new StandplaatsPropertyData(standplaatsDetailDTO, teller1++);
				panelFieldsMandatory.add(spd, gridBagConstraints);
				standplaatsProperties.add(spd);
				if (y1 == 10) {
					x1 = 1;
					y1 = 0;
				}
			} else {
				gridBagConstraints.gridx = x2;
				gridBagConstraints.gridy = y2;
				y2++;
				StandplaatsPropertyData spd = new StandplaatsPropertyData(standplaatsDetailDTO, teller2++);
				panelFieldsNotMandatory.add(spd, gridBagConstraints);
				standplaatsProperties.add(spd);
				if (y2 == 10) {
					x2 = 1;
					y2 = 0;
				}
			}
		}

		int rows = (mandatoryFields)?3:1;
		panelFields.setLayout(new GridLayout2(rows, 1));
		if (mandatoryFields) {
			panelFields.add(panelFieldsMandatory);
			panelFields.add(getDivider(600));
		}
		panelFields.add(panelFieldsNotMandatory);
		setFocusOnComponents();
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
	
	public class DateListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent arg0) {
			setRemarksInGui();
		}
		
	}
	
	public void resetFieldPanel() {
		this.remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER));
		initPanelFieldsMandatory();
		initPanelFieldsNotMandatory();
		
		createFieldsPanel();
		this.add(panelFields, BorderLayout.CENTER);
		cboStandplaatsDetails.setDataList(getStandplaatsDetailList());
	}

	public boolean isValidDate(String inDate) {

		if (inDate == null)
		      return false;

		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		    
		    String[] s = inDate.split("-");
		    if (s.length == 3) {
			    int i = 0;
			    if (s[0].length() == 1) i++;
			    if (s[1].length() == 1) i++;
			    if (inDate.trim().length() + i != dateFormat.toPattern().length()) {
			    	return false;
			    }
		    } else {
		    	return false;
		    }

		    dateFormat.setLenient(false);
		    
		    try {
		      dateFormat.parse(inDate.trim());
		    }
		    catch (ParseException pe) {
		      return false;
		    }
		    return true;
	}
	  
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave) {
			save();
		} else if (e.getSource() == btnDelete) {
			int index = cboStandplaatsDetails.getSelectedIndex();
			if (index != 0) {
				boolean ok = true;
				String opmerking = (String) cboStandplaatsDetails.getDataList().get(index);

				if (!StringUtils.isEmpty(opmerking)) {
					String message = "Weet u zeker dat u alle opmerkingen met naam " + opmerking + " wil verwijderen?";
					SaveDialog dialog = new SaveDialog(message, be.camping.campingzwaenepoel.presentation.common.Constant.getYesNoOption());
					ok = dialog.isUnsavedChanges();
					dialog.dispose();
				}
				
				if (ok) {
					if (StringUtils.isEmpty((String) cboStandplaatsDetails.getDataList().get(0))) {
						index--;
					}

                    standplaatsDetailService.removeStandplaatsDetail(standplaatsDetailDTOs.get(index));
                    standplaatsInformatieService.removeStandplaatsInformatieByNumber(standplaatsDetailDTOs.get(index).getNummer());
					for (GrondInformatieDTO grondInformatieDTO : controller.getStandplaatsDTO().getGrondInformaties()) {
						if (grondInformatieDTO.getNummer().equals(standplaatsDetailDTOs.get(index).getNummer())) {
							controller.getStandplaatsDTO().getGrondInformaties().remove(grondInformatieDTO);
							break;
						}
					}
					standplaatsDetailDTOs.remove(index);
					resetFieldPanel();
					setDataInGUI();
				}
			}
		} else if (e.getSource() == btnNewField) {
			if (checkDataChanged()) {
				SaveDialog dialog = new SaveDialog();
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					save();
				}
				dialog.dispose();
			}
			StandplaatsPropertyEditor spEditor = new StandplaatsPropertyEditor();
			Object[] array = {spEditor};
			int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == 0) {
				StandplaatsDetailDTO standplaatsDetailDTO = new StandplaatsDetailDTO();
				standplaatsDetailDTO.setNaam(spEditor.getProperty());
				standplaatsDetailDTO.setDatumveld(spEditor.isDate());
				standplaatsDetailDTO.setNummer(newNumber());
				standplaatsDetailDTO.setVerplicht(spEditor.isMandatory());
				standplaatsDetailDTO.setPrintAlles(spEditor.isPrintAlles());
				standplaatsDetailDTO.setNamenTonen(spEditor.isNamenTonen());
                standplaatsDetailService.storeStandplaatsDetail(standplaatsDetailDTO);
				standplaatsDetailDTOs.add(standplaatsDetailDTO);
				resetFieldPanel();
				setDataInGUI();
			}
		} else if (e.getSource() == btnEditField && !StringUtils.isEmpty((String) cboStandplaatsDetails.getSelectedItem())) {
			if (checkDataChanged()) {
				SaveDialog dialog = new SaveDialog();
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					save();
				}
				dialog.dispose();
			}
			int index = cboStandplaatsDetails.getSelectedIndex();
			if (StringUtils.isEmpty((String) cboStandplaatsDetails.getDataList().get(0))) {
				index--;
			}
			StandplaatsDetailDTO standplaatsDetailDTO = standplaatsDetailDTOs.get(index);
			StandplaatsPropertyEditor spEditor = 
				new StandplaatsPropertyEditor(standplaatsDetailDTO.getNaam(), standplaatsDetailDTO.isVerplicht(), standplaatsDetailDTO.isDatumveld(), standplaatsDetailDTO.isPrintAlles(), standplaatsDetailDTO.isNamenTonen());
			Object[] array = {spEditor};
			int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == 0) {
				standplaatsDetailDTO.setNaam(spEditor.getProperty());
				standplaatsDetailDTO.setDatumveld(spEditor.isDate());
				standplaatsDetailDTO.setVerplicht(spEditor.isMandatory());
				standplaatsDetailDTO.setPrintAlles(spEditor.isPrintAlles());
				standplaatsDetailDTO.setNamenTonen(spEditor.isNamenTonen());
                standplaatsDetailService.storeStandplaatsDetail(standplaatsDetailDTO);
				resetFieldPanel();
				setDataInGUI();
			}
		}
	}
	
	private Short newNumber() {
		short max = 0;
		for (StandplaatsDetailDTO standplaatsDetail : standplaatsDetailDTOs) {
			if (standplaatsDetail.getNummer() > max) {
				max = standplaatsDetail.getNummer();
			}
		}
		return ++max;
	}
	
	public class FieldKeyAdapter implements KeyListener {
		
		public FieldKeyAdapter() {

		}	
		
        @Override
        public void keyReleased(KeyEvent e ) {}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
				e.consume();
            	Robot robot;
				try {
					robot = new Robot();
                	robot.keyPress(KeyEvent.VK_TAB);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
	    }
	}
	
	private void setFocusOnComponents() {
		List<StandplaatsPropertyData> sortedProperties = sortStandplaatsProperties();
		for (int i = 0; i < sortedProperties.size(); i++) {
			StandplaatsPropertyData spd = sortedProperties.get(i);
			int nextIndex = (i == sortedProperties.size()-1)?0:i+1;
			final StandplaatsPropertyData spdNext = sortedProperties.get(nextIndex);
			spd.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed (final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                        spdNext.requestFocus();
                    }
				}
				
                @Override
                public void keyReleased( final KeyEvent e ) {
                	if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                        e.consume();
                    }
                }		                
			} );
		}		
	}
	
	private List<StandplaatsPropertyData> sortStandplaatsProperties() {
		List<StandplaatsPropertyData> sortedProperties = new ArrayList<StandplaatsPropertyData>();
		for (StandplaatsPropertyData spd : standplaatsProperties) {
			boolean added = false;
			for (StandplaatsPropertyData spdSorted : sortedProperties) {
				if (spd.getLabelNummer() < spdSorted.getLabelNummer()) {
					int index = sortedProperties.indexOf(spdSorted);
					sortedProperties.add(index, spd);
					added = true;
					break;
				}
			}
			if (!added) {
				sortedProperties.add(spd);
			}
		}
		return sortedProperties;
	}
	
	class StandplaatsPropertyEditor extends JPanel {

		private static final long serialVersionUID = 793435017740922449L;
		private JTextField jTxtStandplaatsProperty;
		private JCheckBox jChkMandatory;
		private JCheckBox jChkDate;
		private JCheckBox jChkPrintAlles;
		private JCheckBox jChknamenTonen;
		
		Font font = new Font("Times New Roman", Font.BOLD, 18);
		
		public StandplaatsPropertyEditor() {
			initComponents();
		}
		
		public StandplaatsPropertyEditor(String property, boolean mandatory, boolean isDate, boolean printAlles, boolean namenTonen) {
			initComponents();
			jTxtStandplaatsProperty.setText(property);
			jChkDate.setSelected(isDate);
			jChkMandatory.setSelected(mandatory);
			jChkPrintAlles.setSelected(printAlles);
			jChknamenTonen.setSelected(namenTonen);
		}

		private void initComponents() {
			setLayout(new GridLayout(1, 6));
			initJTxtStandplaatsProperty();
			initJChkMandatory();
			initJChkDate();
			initJChkPrintAlles();
			initJChkNamenTonen();
			add(jTxtStandplaatsProperty);
			add(jChkMandatory);
			add(jChkDate);
			add(jChkPrintAlles);
			add(jChknamenTonen);
		}

		public void initJTxtStandplaatsProperty() {
			jTxtStandplaatsProperty = new JTextField();
			jTxtStandplaatsProperty.setFont(font);
			jTxtStandplaatsProperty.addAncestorListener(new RequestFocusListener());
		}
		
		public void initJChkMandatory() {
			jChkMandatory = new JCheckBox();
			jChkMandatory.setText("verplicht veld");
			jChkMandatory.setFont(font);
		}
		
		public void initJChkDate() {
			jChkDate = new JCheckBox();
			jChkDate.setText("datum veld");
			jChkDate.setFont(font);
		}
		
		public void initJChkPrintAlles() {
			jChkPrintAlles = new JCheckBox();
			jChkPrintAlles.setText("print alles");
			jChkPrintAlles.setFont(font);
		}

		public void initJChkNamenTonen() {
			jChknamenTonen = new JCheckBox();
			jChknamenTonen.setText("namen tonen");
			jChknamenTonen.setFont(font);
		}

		public String getProperty() {
			return jTxtStandplaatsProperty.getText();
		}

		public boolean isMandatory() {
			return jChkMandatory.isSelected();
		}

		public boolean isDate() {
			return jChkDate.isSelected();
		}
		
		public boolean isPrintAlles() {
			return jChkPrintAlles.isSelected();
		}
		
		public boolean isNamenTonen() {
			return jChknamenTonen.isSelected();
		}
	}
	
	public StandplaatsPropertyData getStandplaatsPropertyData(int number) {
		StandplaatsPropertyData spd = null;
		for (StandplaatsPropertyData spdTemp : standplaatsProperties) {
			if (spdTemp.getStandplaatsDetail().getNummer() == number) {
				spd = spdTemp;
				break;
			}
		}
		return spd;
	}

	class StandplaatsPropertyData extends JPanel {
		
		private static final long serialVersionUID = -7762086315423484170L;
		private StandplaatsDetailDTO standplaatsDetail;
		private GrondInformatieDTO grondInformatie;
		private JTextField jTextField;
		private JDatePicker jDatePicker;
		private JLabel jLabel;
		private int labelNummer;
		
		public StandplaatsPropertyData(StandplaatsDetailDTO standplaatsDetail, int labelNummer) {
			this.standplaatsDetail = standplaatsDetail;
			this.labelNummer = labelNummer;
			initComponents();
		}

		private void initComponents() {
			setLayout(new GridLayout2(1, 2));
			add(getjLabel());
			if (!standplaatsDetail.isDatumveld()) {
				add(getjTextField());
			} else {
				add((JComponent)getjDatePicker());
				getjDatePicker().clearTextField();
			}
		}

		public StandplaatsDetailDTO getStandplaatsDetail() {
			return standplaatsDetail;
		}

		public GrondInformatieDTO getGrondInformatie() {
			if (grondInformatie == null) {
				grondInformatie = new GrondInformatieDTO();
				grondInformatie.setNummer(getStandplaatsDetail().getNummer());
			}
			return grondInformatie;
		}
		
		public GrondInformatieDTO updateGrondInformatie() {
			updateValue();
			return grondInformatie;
		}

		public void setGrondInformatie(GrondInformatieDTO grondInformatie) {
			this.grondInformatie = grondInformatie;
			if (!standplaatsDetail.isDatumveld()) {
				getjTextField().setText(grondInformatie.getWaarde());
			} else {
				if (!StringUtils.isEmpty(grondInformatie.getWaarde())) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					Date date = null;
					try {
						getGrondInformatie().setWaarde(grondInformatie.getWaarde().replace("/", "-"));
						date = formatter.parse(grondInformatie.getWaarde());
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						getjDatePicker().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					getjDatePicker().clearTextField();
				}
			}
		}
		
		public int getLabelNummer() {
			return labelNummer;
		}

		public void setLabelNummer(int labelNummer) {
			this.labelNummer = labelNummer;
		}

		private JTextField getjTextField() {
			if (jTextField == null) {
				jTextField = new JTextField();
				jTextField.setPreferredSize(txtFieldDim);
				jTextField.setFont(labelFont);
				if (standplaatsDetail.isVerplicht()) {
					jTextField.getDocument().addDocumentListener(grondListener);
				}
			}
			return jTextField;
		}

		private JDatePicker getjDatePicker() {
			if (jDatePicker == null) {
				jDatePicker = JDateComponentFactory.createJDatePicker();
				jDatePicker.setTextEditable(true);
				jDatePicker.setShowYearButtons(true);
				jDatePicker.clearTextField();
				jDatePicker.getJFormattedTextField().setPreferredSize(new Dimension(268, 30));
				jDatePicker.getJFormattedTextField().setFont(labelFont);
				if (standplaatsDetail.isVerplicht()) {
					jDatePicker.getJFormattedTextField().getDocument().addDocumentListener(grondListener);
				}
			}
			return jDatePicker;
		}

		private JLabel getjLabel() {
			if (jLabel == null) {
				jLabel = new JLabel();
				jLabel.setText(getLabelValue());
				jLabel.setFont(labelFont);
				jLabel.setPreferredSize(txtFieldNamesDim);
			}
			return jLabel;
		}
		
		private String getLabelValue() {
			String s = Integer.toString(labelNummer) + " ";
			s += standplaatsDetail.getNaam();
			return s;
		}
		
		public String getProperty() {
			return standplaatsDetail.getNaam();
		}

		public Object getValue() {
			Object o = "";
			if (!standplaatsDetail.isDatumveld()) {
				o = getjTextField().getText();
			} else {
				o = getjDatePicker().getTime();
			}
			return o;
		}
		
		public boolean isChanged() {
			boolean ret = true;
			if (!getStandplaatsDetail().isDatumveld()) {
				String s1 = "";
				if (!StringUtils.isEmpty(getGrondInformatie().getWaarde())) {
					s1 = getGrondInformatie().getWaarde();
				}
				String s2 = (StringUtils.isEmpty(getjTextField().getText()))?"":getjTextField().getText();
				ret = (!s1.equals(s2));
			} else {
				if (!StringUtils.isEmpty(getjDatePicker().getJFormattedTextField().getText()) 
						&& !StringUtils.isEmpty(getGrondInformatie().getWaarde())) {
					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					getGrondInformatie().setWaarde(getGrondInformatie().getWaarde().replace("/", "-"));
					DateFormat formatter; 
				    formatter = new SimpleDateFormat("dd-MM-yyyy");
				    Date date1, date2;
					try {
						date1 = (Date)formatter.parse(getGrondInformatie().getWaarde());
						cal1.setTime(date1);
						date2 = (Date)formatter.parse(getjDatePicker().getModel().getDay() + "-" 
														+ (getjDatePicker().getModel().getMonth() + 1) + "-" 
														+ getjDatePicker().getModel().getYear());
						cal2.setTime(date2);
						ret = (!cal1.equals(cal2));
					} catch (ParseException e) {
						System.err.println("date not correctly parsed, id: " + getGrondInformatie().getId());
					} 
				} else {
					if ((StringUtils.isEmpty(getjDatePicker().getJFormattedTextField().getText()) && StringUtils.isEmpty(getGrondInformatie().getWaarde())) ||	
						(!StringUtils.isEmpty(getjDatePicker().getJFormattedTextField().getText()) && !StringUtils.isEmpty(getGrondInformatie().getWaarde()))) {
						ret = false;
					}
				}

			}
			return ret;
		}
		
		public boolean isFieldFilledIn() {
			boolean ret = false;
			if (!standplaatsDetail.isVerplicht()) {
				ret = true;
			} else {
				if (!standplaatsDetail.isDatumveld()) {
					if (!StringUtils.isEmpty(getjTextField().getText())) {
						ret = true;
					}
				} else {
					if (!StringUtils.isEmpty(getjDatePicker().getJFormattedTextField().getText())) {
						Calendar cal = Calendar.getInstance();
						Calendar now = Calendar.getInstance();
						now.setTime(new Date());
						String sDate = getjDatePicker().getModel().getDay() + "-" 
										+ (getjDatePicker().getModel().getMonth()+1) + "-" 
										+ getjDatePicker().getModel().getYear();
						DateFormat formatter;
					    formatter = new SimpleDateFormat("dd-MM-yyyy");
					    Date date;
						try {
							date = (Date)formatter.parse(sDate);
							cal.setTime(date);
							if(now.before(cal)) {
								ret = true;
							}
						} catch (ParseException e) {
							System.err.println("date not correctly parsed");
						} 
					}	
				}
			}
			return ret;
		}
				
		public void updateValue() {
			if (!standplaatsDetail.isDatumveld()) {
				getGrondInformatie().setWaarde(getjTextField().getText());
			} else {
				if (!StringUtils.isEmpty(getjDatePicker().getJFormattedTextField().getText())) {
					try {
						String s = getjDatePicker().getModel().getDay()+"-"+(getjDatePicker().getModel().getMonth()+1)+"-"+getjDatePicker().getModel().getYear();
						getGrondInformatie().setWaarde(s);
					} catch (Exception e) {
					}
				} else {
					getGrondInformatie().setWaarde(null);
				}
			}

		}
		
		public void clearField() {
			if (!standplaatsDetail.isDatumveld()) {
				getjTextField().setText("");
			} else {
				getjDatePicker().getJFormattedTextField().setText("");
				Calendar cal = Calendar.getInstance();
				getjDatePicker().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
			}
			setGrondInformatie(new GrondInformatieDTO());
			getGrondInformatie().setNummer(standplaatsDetail.getNummer());
		}
		
		public void addKeyListener(KeyListener kl) {
			if (!standplaatsDetail.isDatumveld()) {
				getjTextField().addKeyListener(kl);
			} else {
				getjDatePicker().getJFormattedTextField().addKeyListener(kl);
			}		
		}
		
		public void requestFocus() {
			if (!standplaatsDetail.isDatumveld()) {
				getjTextField().requestFocusInWindow();
			} else {
				getjDatePicker().getJFormattedTextField().requestFocusInWindow();
			}
		}
	}
}
