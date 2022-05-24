package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.dialog.SaveDialog;
import be.camping.campingzwaenepoel.presentation.ui.layout.GridLayout2;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.CampingContactService;
import be.camping.campingzwaenepoel.service.transfer.CampingContactDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class TelefoonPanel extends JPanel implements PanelInterface, ActionListener {

	@Autowired
	private CampingContactService campingContactService;

	private static final long serialVersionUID = 1L;

    Font labelFont = new Font("SansSerif", Font.BOLD, 18);
    Insets insets = new Insets(5, 20, 10, 10);
	private Dimension txtFieldTelephoneDim = new Dimension(150, 30);
	private Dimension txtFieldNamesDim = new Dimension(250, 30);

    private JPanel panelButtons;
    private JPanel panelContacts;
    
    private JButton btnSave;
    private JButton btnNewTextField;
    private JButton btnDelete;

    private Dimension buttonDimension = new Dimension(145, 40);
    private Java2sAutoComboBox cboContacts;

    private List<CampingContactDTO> contacten = null;
    private List<Map<String, Object>> contactmaps;
    
    private final String NUMMER = "nummer";
    private final String CONTACT = "contact";
    private final String NAAMVELD = "naamveld";
    private final String TELEFOONVELD = "telefoonveld";
    
	public List<CampingContactDTO> getContacts() {
		return campingContactService.findAllCampingContacts();
	}

	public void setContacten(List<CampingContactDTO> contacten) {
		this.contacten = contacten;
	}

	public List<Map<String, Object>> getContactmaps() {
		if (contactmaps == null) {
			contactmaps = new ArrayList<Map<String, Object>>();
		}
		return contactmaps;
	}

	public void setContactmaps(List<Map<String, Object>> contactmaps) {
		this.contactmaps = contactmaps;
	}

	public JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new FlowLayout());
			panelButtons.add(getBtnSave());
			panelButtons.add(getBtnNewTextField());
			panelButtons.add(getCboContacts());
			panelButtons.add(getBtnDelete());
			panelButtons.setBorder(new LineBorder(Color.BLACK, 1));
		}
		return panelButtons;
	}

	/**
	 * This method initializes btnWis	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
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
		return btnSave;
	}

	public JButton getBtnNewTextField() {
		if (btnNewTextField == null) {
			btnNewTextField = new JButton();
			btnNewTextField.setPreferredSize(buttonDimension);
			btnNewTextField.setText("NIEUW TEXT VELD");
			btnNewTextField.addActionListener(this);
		}
		return btnNewTextField;
	}

	public Java2sAutoComboBox getCboContacts() {
		if (cboContacts == null) {
			cboContacts = new Java2sAutoComboBox(new ArrayList<String>());
			cboContacts.setPreferredSize(txtFieldNamesDim);
		}
		return cboContacts;
	}

	public JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setPreferredSize(buttonDimension);
			btnDelete.setText("VERWIJDER");
			btnDelete.addActionListener(this);
		}
		return btnDelete;
	}

	public JPanel getPanelContacts() {
		if (panelContacts == null) {
			panelContacts = new JPanel();
		}
		return panelContacts;
	}

	public void setPanelContacts(JPanel panelContacts) {
		this.panelContacts = panelContacts;
	}

	private List<String> getContactList() {
		List<String> contacten = new ArrayList<String>();
		for (CampingContactDTO contact : getContacts()) {
			String naam = (StringUtils.isEmpty(contact.getNaam()))?"":contact.getNaam();
			contacten.add(naam);
		}
		if (contacten.size() > 1) {
			Collections.sort(contacten, String.CASE_INSENSITIVE_ORDER);
		}
		if (!contacten.contains("")) {
			contacten.add(0, "");
		}
		return contacten;
	}

	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkDataChanged() {
		boolean ret = false;
		for (Map<String, Object> map : getContactmaps()) {
			CampingContactDTO contact = (CampingContactDTO)map.get(CONTACT);
			JTextField jTextFieldNaam = (JTextField)map.get(NAAMVELD);
			JTextField jTextFieldTelephone = (JTextField)map.get(TELEFOONVELD);
			String name = (StringUtils.isEmpty(contact.getNaam()))?"":contact.getNaam();
			String telephone = (StringUtils.isEmpty(contact.getTelefoon()))?"":contact.getTelefoon();
			String contactname = (StringUtils.isEmpty(jTextFieldNaam.getText()))?"":jTextFieldNaam.getText();
			String contacttelephone = (StringUtils.isEmpty(jTextFieldTelephone.getText()))?"":jTextFieldTelephone.getText();
			if (!(name.equals(contactname) && telephone.equals(contacttelephone))) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public boolean checkDataForParent() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getDataFromGUI() {
		// TODO Auto-generated method stub
		return null;
	}

	public void save() {
		for (Map<String, Object> map : getContactmaps()) {
			CampingContactDTO contact = (CampingContactDTO)map.get(CONTACT);
			JTextField jTextFieldNaam = (JTextField)map.get(NAAMVELD);
			JTextField jTextFieldTelephone = (JTextField)map.get(TELEFOONVELD);
			contact.setNaam(jTextFieldNaam.getText());
			contact.setTelefoon(jTextFieldTelephone.getText());
			campingContactService.store(contact);
		}
		getCboContacts().setDataList(getContactList());
	}

	public void setDataInGUI() {
		createPanelContacts();
		getCboContacts().setDataList(getContactList());
	}
		
	public void setRemarksInGui() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		BorderLayout layout = new BorderLayout();

		this.setLayout(layout);
//		this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		this.setBorder(new LineBorder(Color.BLACK, 2));

		this.add(getPanelButtons(), BorderLayout.NORTH);
		this.add(getPanelContacts(), BorderLayout.CENTER);
	}

	public class OpslaanHandler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			save();
		}
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
	    	btnSave.doClick();
	    }
	}

	private void createPanelContacts() {
		getPanelContacts().removeAll();
		setContactmaps(new ArrayList<Map<String, Object>>());
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		int x = 0, y = 0;
		int teller = 1;
		List<CampingContactDTO> contacten = getContacts();
		for (CampingContactDTO contact : contacten) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = x;
			gridBagConstraints.gridy = y;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.insets = insets;
			y++;
			jPanel.add(createJPanel(contact, teller++), gridBagConstraints);
			if (y == 10) {
				x = 1;
				y = 0;
			}
		}
		setFocusOnComponents();
		getPanelContacts().add(jPanel, BorderLayout.CENTER);
	}

	private void setFocusOnComponents() {
		for (Map<String, Object> map : getContactmaps()) {
			JTextField jTextField = (JTextField)map.get(TELEFOONVELD);
			int number = (Integer)map.get(NUMMER);
			int nextNumber = (number != getContactmaps().size()-1)?++number:0;
			for (Map<String, Object> nextMap : getContactmaps()) {
				if (nextNumber == (Integer)map.get(NUMMER)) {
					final JTextField jNextTextField = (JTextField)nextMap.get(TELEFOONVELD);
					jTextField.addKeyListener( new KeyAdapter() {
						@Override
						public void keyReleased( final KeyEvent e ) {
							if (e.getKeyCode() == KeyEvent.VK_ENTER) {
								jNextTextField.requestFocusInWindow();
							}
						}
					});
//					jTextField.setNextFocusableComponent(jNextTextField);
				}
				break;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave) {
			save();
		} else if (e.getSource() == btnDelete) {
			if (checkOkToSave()) {
				String naam = (String)getCboContacts().getSelectedItem();
				for (Map<String, Object> map : getContactmaps()) {
					CampingContactDTO contact = (CampingContactDTO)map.get(CONTACT);
					String contactNaam = (StringUtils.isEmpty(contact.getNaam()))?"":contact.getNaam();
					if (naam.equals(contactNaam)) {
						getContactmaps().remove(map);
						campingContactService.remove(contact);
						break;
					}
				}
				createPanelContacts();
				getCboContacts().setDataList(getContactList());
				repaint();
			}
		} else if (e.getSource() == btnNewTextField) {
			if (checkDataChanged()) {
				SaveDialog dialog = new SaveDialog();
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					save();
				}
				dialog.dispose();
			}
			CampingContactDTO contact = new CampingContactDTO();
			campingContactService.store(contact);
			createPanelContacts();
			getCboContacts().setDataList(getContactList());
		}
	}
	
	private boolean checkOkToSave() {
		boolean ret = false;
		int index = getCboContacts().getSelectedIndex();
		int teller = 0;
		if (index > 0) {
			ret = true;
		}
/*		for (Object o : getCboContacts().getDataList()) {
			if (StringUtils.isEmpty((String)o) && (++teller > 1)) {
				ret = true;
				break;
			}
		}*/
		if (!ret && index == 0) {
			for(Map<String, Object> map : getContactmaps()) {
				List<CampingContactDTO> contacts = getContacts();
				for (CampingContactDTO contact : contacts) {
					if (StringUtils.isEmpty(contact.getNaam())) {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}

	private JPanel createJPanel(CampingContactDTO contact, int teller) {
		JPanel jPanel = new JPanel();
		GridLayout2 gridLayout2 = new GridLayout2(1, 2);
		jPanel.setLayout(gridLayout2);
		
		JTextField jTxtFieldName = createJTextFieldForName(contact.getNaam());
		jPanel.add(jTxtFieldName);
		JTextField jTextField = createJTextField(contact.getTelefoon());
		jPanel.add(jTextField);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(NUMMER, teller);
		map.put(CONTACT, contact);
		map.put(NAAMVELD, jTxtFieldName);
		map.put(TELEFOONVELD, jTextField);
		getContactmaps().add(map);
		
		return jPanel;
	}
	
	private JTextField createJTextFieldForName(String value) {
		JTextField jTextField = new JTextField(value);
		jTextField.setPreferredSize(txtFieldNamesDim);
		jTextField.setFont(labelFont);
//		jTextField.getDocument().addDocumentListener(grondListener);
		return jTextField;
	}

	private JTextField createJTextField(String value) {
		JTextField jTextField = new JTextField(value);
		jTextField.setPreferredSize(txtFieldTelephoneDim);
		jTextField.setFont(labelFont);
//		jTextField.getDocument().addDocumentListener(grondListener);
		return jTextField;
	}

}
