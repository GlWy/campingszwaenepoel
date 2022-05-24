package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.utils.ImageUtils;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.dialog.foto.FotoOptiesPanel;
import be.camping.campingzwaenepoel.presentation.image.ImageLoader;
import be.camping.campingzwaenepoel.presentation.pdf.FotoAndOpmerkingenFacturatiePdf;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.foto.FotoService;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FotoPanel extends JPanel implements PanelInterface, ActionListener, KeyListener {


    @Autowired
    private StandplaatsService standplaatsService;

    @Autowired
    private ConfiguratieService configuratieService;

    private static final long serialVersionUID = 1L;
	
	private JPanel jNorthPanel;
	private JPanel jCentralPanel;
	private JPanel jSouthPanel;
	
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;
	
	private JPanel jPanelNorthCheckboxes = null;
	private JPanel jPanelSouthCheckboxes = null;
	
	private JCheckBox jCheckBox1 = new JCheckBox();
	private JCheckBox jCheckBox2 = new JCheckBox();
	private JCheckBox jCheckBox3 = new JCheckBox();
	private JCheckBox jCheckBox4 = new JCheckBox();
	private JCheckBox jCheckBox5 = new JCheckBox();
	private JCheckBox jCheckBox6 = new JCheckBox();
	
	private JTextArea jTAOpmerking;
	private JButton btnOpslaan = null;
	private JButton btnImport = null;
	private JButton btnRemove = null;
	private JButton btnPrint = null;
	private JPanel jPanelSave = null;
	
	Font font = new Font("Times New Roman", Font.BOLD, 24);
	Font fontButton = new Font("Times New Roman", Font.BOLD, 20);
	
	private Controller controller;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Autowired
	private FotoService fotoService;

	public FotoService getFotoService() {
		return fotoService;
	}

	public void setFotoService(FotoService fotoService) {
		this.fotoService = fotoService;
	}

	private FotoOptiesPanel fotoOptiesPanel;

	public FotoOptiesPanel getFotoOptiesPanel() {
		return fotoOptiesPanel;
	}

	public void setFotoOptiesPanel(FotoOptiesPanel fotoOptiesPanel) {
		this.fotoOptiesPanel = fotoOptiesPanel;
	}

	public JTextArea getjTAOpmerking() {
		if (jTAOpmerking == null) {
			jTAOpmerking = new JTextArea(5, 50);
			jTAOpmerking.setBorder(new LineBorder(Color.BLACK, 2));
			jTAOpmerking.setFont(font);
			jTAOpmerking.getDocument().addDocumentListener(new FotoOpmerkingListener());
			jTAOpmerking.setLineWrap(true);
			jTAOpmerking.setWrapStyleWord(true);
		}
		return jTAOpmerking;
	}

	public void setjTAOpmerking(JTextArea jTAOpmerking) {
		this.jTAOpmerking = jTAOpmerking;
	}
	
	public class FotoOpmerkingListener implements DocumentListener {
		
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

	public StandplaatsDTO getStandplaatsDTO() {
		return getController().getStandplaatsDTO();
	}

	public boolean checkData() {
		boolean ret = true;
		if (!StringUtils.isEmpty(getjTAOpmerking().getText())) {
			ret = false;
		}
		ConfiguratieDTO configuratieDTO = configuratieService.getFileDirectory();
		if (configuratieDTO != null && !StringUtils.isEmpty(configuratieDTO.getWaarde())) {
			StandplaatsDTO standplaatsDTO = controller.getStandplaatsDTO();
			int standplaatsnummer = standplaatsDTO.getPlaatsnummer();
			String standplaats = Integer.toString(standplaatsnummer);
			while (standplaats.length() < 3) {
				standplaats = "0" + standplaats;
			}
			String dir = configuratieDTO.getWaarde() + "/standplaats/" + standplaatsDTO.getPlaatsgroep() + standplaats;
			File files = new File(dir);
			if (files.isDirectory()) {
				File[] fotobestanden = files.listFiles();
				for (File file : fotobestanden) {
					if (!file.isFile() && !file.getName().startsWith(".")) {
						ret = false;
						break;
					}
				}
			}
		}
		return ret;
	}

	public boolean checkDataChanged() {
		boolean ret = true;
		StandplaatsDTO grondDTO = controller.getStandplaatsDTO();
		String textGrondDTO = (grondDTO.getFotoOpmerking() == null)?"":grondDTO.getFotoOpmerking();
		String textOpmerking = (getjTAOpmerking().getText() == null)?"":getjTAOpmerking().getText();
		if (grondDTO != null && (textGrondDTO.equals(textOpmerking))) {
			ret = false;
		}
		return ret;
	}

	public boolean checkDataForParent() {
		boolean ret = true;
		if (!StringUtils.isEmpty(getStandplaatsDTO().getFotoOpmerking())) {
			ret = false;
		}
		ConfiguratieDTO configuratieDTO = configuratieService.getFileDirectory();
		if (configuratieDTO != null && !StringUtils.isEmpty(configuratieDTO.getWaarde())) {
			StandplaatsDTO standplaatsDTO = controller.getStandplaatsDTO();
			int standplaatsnummer = standplaatsDTO.getPlaatsnummer();
			String standplaats = Integer.toString(standplaatsnummer);
			while (standplaats.length() < 3) {
				standplaats = "0" + standplaats;
			}
			String dir = configuratieDTO.getWaarde() + "/standplaats/" + standplaatsDTO.getPlaatsgroep() + standplaats + "/foto/";
			File files = new File(dir);
			if (files.isDirectory()) {
				File[] fotobestanden = files.listFiles();
				for (File file : fotobestanden) {
					if (ImageUtils.isImage(file.getName())) {
						ret = false;
						break;
					}
				}
			}
		}

		return ret;
	}

	public Object getDataFromGUI() {
		StandplaatsDTO grondDTO = getStandplaatsDTO();
		if (getjTAOpmerking().getText() != null) {
			grondDTO.setFotoOpmerking(getjTAOpmerking().getText());
		} else {
			grondDTO.setFotoOpmerking("");
		}

		return grondDTO;
	}

	public void save() {
		StandplaatsDTO standplaatsDTO = (StandplaatsDTO)getDataFromGUI();
		standplaatsService.storeStandplaats(standplaatsDTO);
	}

	public void setDataInGUI() {
		reset();
		StandplaatsDTO grondDTO = controller.getStandplaatsDTO();
		if (grondDTO.getFotoOpmerking() != null) {
			getjTAOpmerking().setText(grondDTO.getFotoOpmerking());
		} else {
			getjTAOpmerking().setText("");
		}
//		clearData();
		ConfiguratieDTO configuratieDTO = configuratieService.getFileDirectory();
		if (configuratieDTO != null && !StringUtils.isEmpty(configuratieDTO.getWaarde())) {
			StandplaatsDTO standplaatsDTO = controller.getStandplaatsDTO();
			int standplaatsnummer = standplaatsDTO.getPlaatsnummer();
			String standplaats = Integer.toString(standplaatsnummer);
			while (standplaats.length() < 3) {
				standplaats = "0" + standplaats;
			}
			String dir = configuratieDTO.getWaarde() + "/standplaats/" + standplaatsDTO.getPlaatsgroep() + standplaats + "/foto/";
			File file = new File(dir);
			if (file.isDirectory()) {
				File[] fotobestanden = file.listFiles();
				setData(fotobestanden);
			}
		}
	}

	public void setRemarksInGui() {
		boolean b = checkData();
		getController().updateDataForPanel(b, 9);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(new LineBorder(Color.BLACK, 2));
		this.add(getjNorthPanel(), BorderLayout.NORTH);
		this.add(getjCentralPanel(), BorderLayout.CENTER);
		this.add(getjSouthPanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes btnWis	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOpslaan() {
		if (btnOpslaan == null) {
			btnOpslaan = new JButton();

//			Dimension dim = new Dimension(220, 40);
//			btnOpslaan.setSize(dim);
//			btnOpslaan.setPreferredSize(dim);
			btnOpslaan.setText("OPSLAAN");
			btnOpslaan.setFont(fontButton);

			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new ButtonActionListener("Opslaan");
		    InputMap inputMap = btnOpslaan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		    inputMap.put(stroke, actionKey);
		    ActionMap actionMap = btnOpslaan.getActionMap();
		    actionMap.put(actionKey, action);
			btnOpslaan.addActionListener(this);

		}
		return btnOpslaan;
	}
	
	public JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();

//			Dimension dim = new Dimension(220, 40);
//			btnImport.setSize(dim);
//			btnImport.setPreferredSize(dim);
			btnImport.setText("OPTIES");
			btnImport.setFont(fontButton);
			btnImport.addActionListener(this);
		}
		return btnImport;
	}

	public JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("VERWIJDEREN");
//			Dimension dim = new Dimension(220, 40);
//			btnRemove.setPreferredSize(dim);
			btnRemove.setFont(fontButton);
			btnRemove.addActionListener(this);
		}
		return btnRemove;
	}

	public JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton("AFDRUKKEN");
//			Dimension dim = new Dimension(220, 40);
//			btnPrint.setPreferredSize(dim);
			btnPrint.setFont(fontButton);
			btnPrint.addActionListener(this);
		}
		return btnPrint;
	}

	private JPanel getJPanelSave() {
		if (jPanelSave == null) {
			jPanelSave = new JPanel();
			jPanelSave.setLayout(new GridLayout(4, 1));
			jPanelSave.add(getBtnImport());
			jPanelSave.add(getBtnOpslaan());
			jPanelSave.add(getBtnRemove());
			jPanelSave.add(getBtnPrint());
		}
		return jPanelSave;
	}

	class ButtonActionListener extends AbstractAction {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ButtonActionListener(String s) {
			super(s);
	    }

	    public void actionPerformed(ActionEvent e) {
	    	getBtnOpslaan().doClick();
	    }
	}
	
	public JPanel getjNorthPanel() {
		if (jNorthPanel == null) {
			jNorthPanel = new JPanel();
			jNorthPanel.setLayout(new GridLayout(1, 3, 5, 5));
			jNorthPanel.add(getjPanel1());
			jNorthPanel.add(getjPanel2());
			jNorthPanel.add(getjPanel3());
		}
		return jNorthPanel;
	}
	
	public void setJNorthPanel(JPanel jPanel) {
		this.jNorthPanel = jPanel;
	}
	
	public void setJSouthPanel(JPanel jPanel) {
		this.jSouthPanel = jPanel;
	}

	public JPanel getjCentralPanel() {
		if (jCentralPanel == null) {
			jCentralPanel = new JPanel();
			jCentralPanel.setLayout(new BorderLayout());
			jCentralPanel.add(getjTAOpmerking(), BorderLayout.CENTER);
			jCentralPanel.add(getJPanelSave(), BorderLayout.EAST);
			jCentralPanel.add(getjPanelNorthCheckboxes(), BorderLayout.NORTH);
			jCentralPanel.add(getjPanelSouthCheckboxes(), BorderLayout.SOUTH);
		}
		return jCentralPanel;
	}

	public JPanel getjPanelNorthCheckboxes() {
		if (jPanelNorthCheckboxes == null) {
			jPanelNorthCheckboxes = new JPanel();
			jPanelNorthCheckboxes.setLayout(new GridLayout(1, 3));
			jPanelNorthCheckboxes.add(jCheckBox1);
			jPanelNorthCheckboxes.add(jCheckBox2);
			jPanelNorthCheckboxes.add(jCheckBox3);
		}
		return jPanelNorthCheckboxes;
	}

	public JPanel getjPanelSouthCheckboxes() {
		if (jPanelSouthCheckboxes == null) {
			jPanelSouthCheckboxes = new JPanel();
			jPanelSouthCheckboxes.setLayout(new GridLayout(1, 3));
			jPanelSouthCheckboxes.add(jCheckBox4);
			jPanelSouthCheckboxes.add(jCheckBox5);
			jPanelSouthCheckboxes.add(jCheckBox6);
		}

		return jPanelSouthCheckboxes;
	}

	public JPanel getjSouthPanel() {
		if (jSouthPanel == null) {
			jSouthPanel = new JPanel();
			jSouthPanel.setLayout(new GridLayout(1, 3, 5, 5));
			jSouthPanel.add(getjPanel4());
			jSouthPanel.add(getjPanel5());
			jSouthPanel.add(getjPanel6());
		}
		return jSouthPanel;
	}

	public JPanel getjPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setPreferredSize(new Dimension(380, 240));
			jPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		return jPanel1;
	}

	public JPanel getjPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setPreferredSize(new Dimension(380, 240));
			jPanel2.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		return jPanel2;
	}

	public JPanel getjPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setPreferredSize(new Dimension(380, 240));
			jPanel3.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		return jPanel3;
	}

	public JPanel getjPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setPreferredSize(new Dimension(380, 250));
			jPanel4.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		return jPanel4;
	}

	public JPanel getjPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setPreferredSize(new Dimension(380, 250));
			jPanel5.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		return jPanel5;
	}

	public JPanel getjPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setPreferredSize(new Dimension(380, 250));
			jPanel6.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		return jPanel6;
	}
	
	public void setjPanel1(JPanel jPanel1) {
		this.jPanel1 = jPanel1;
	}

	public void setjPanel2(JPanel jPanel2) {
		this.jPanel2 = jPanel2;
	}

	public void setjPanel3(JPanel jPanel3) {
		this.jPanel3 = jPanel3;
	}

	public void setjPanel4(JPanel jPanel4) {
		this.jPanel4 = jPanel4;
	}

	public void setjPanel5(JPanel jPanel5) {
		this.jPanel5 = jPanel5;
	}

	public void setjPanel6(JPanel jPanel6) {
		this.jPanel6 = jPanel6;
	}

	private void reset() {
		setjPanel1(null);
		setjPanel2(null);
		setjPanel3(null);
		setjPanel4(null);
		setjPanel5(null);
		setjPanel6(null);
		jCheckBox1.setSelected(false);
		jCheckBox2.setSelected(false);
		jCheckBox3.setSelected(false);
		jCheckBox4.setSelected(false);
		jCheckBox5.setSelected(false);
		jCheckBox6.setSelected(false);
		this.remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.NORTH));
		this.remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.SOUTH));
		setJNorthPanel(null);
		setJSouthPanel(null);
		this.add(getjNorthPanel(), BorderLayout.NORTH);
		this.add(getjSouthPanel(), BorderLayout.SOUTH);
	}
	
	private class PhotoMouseListener implements MouseListener {

		private String filename;
		private JPanel jPanel;
		
		public PhotoMouseListener (String filename, JPanel jPanel) {
			this.filename = filename;
			this.jPanel = jPanel;
		}
		
		public String getFilename() {
			return filename;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getComponent().isEnabled() && e.getClickCount() == 2) {
				reverseColor();
	        	ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
	        	String dir = configuratie.getWaarde() + "/standplaats/" + getFullStandplaatsNaam(getStandplaatsDTO());
	        	dir += "/foto/groot/" + filename;
	        	File orig = new File(dir);
	        	Image warnImage = ImageLoader.getImage(orig.getAbsolutePath());
	        	Icon warnIcon = new ImageIcon(warnImage);
	        	JLabel warnLabel = new JLabel(warnIcon);
	        	JPanel jPanel = new JPanel();
				jPanel.setPreferredSize(new Dimension(933, 700));
				jPanel.add(warnLabel);
				Object[] array = {jPanel};
				JOptionPane.showConfirmDialog(null,array,"",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
	        } else if (e.getComponent().isEnabled() && e.getClickCount() == 1) {
	        	reverseColor();
	        }
		}
		
		private void reverseColor() {
			if (!jPanel.getBackground().equals(Color.blue)) {
        		jPanel.setBackground(Color.blue);
        	} else {
        		jPanel.setBackground(null);
        	}	
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public void setData(File[] fotobestanden) {
		int teller = 0;
		for (File file : fotobestanden) {
			if (ImageUtils.isImage(file.getName())) {			
				Image warnImage = ImageLoader.getImage(file.getAbsolutePath());
	        	Icon warnIcon = new ImageIcon(warnImage);
	        	JLabel warnLabel = new JLabel(warnIcon);
	        	warnLabel.addKeyListener(this);
				switch (teller) {
					case 0:
						getjPanel1().add(warnLabel);
						getjPanel1().addMouseListener(new PhotoMouseListener(file.getName(), getjPanel1()));
						break;
					case 1:
						getjPanel2().add(warnLabel);
						getjPanel2().addMouseListener(new PhotoMouseListener(file.getName(), getjPanel2()));
						break;
					case 2:
						getjPanel3().add(warnLabel);
						getjPanel3().addMouseListener(new PhotoMouseListener(file.getName(), getjPanel3()));
						break;
					case 3:
						getjPanel4().add(warnLabel);
						getjPanel4().addMouseListener(new PhotoMouseListener(file.getName(), getjPanel4()));
						break;
					case 4:
						getjPanel5().add(warnLabel);
						getjPanel5().addMouseListener(new PhotoMouseListener(file.getName(), getjPanel5()));
						break;
					case 5:
						getjPanel6().add(warnLabel);
						getjPanel6().addMouseListener(new PhotoMouseListener(file.getName(), getjPanel6()));
						break;	
					default:
						break;
				}
				teller++;
				if (teller > 5) {
					break;
				}
			}
		}
	}

	public void clearData() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getBtnOpslaan()) {
			save();
		}
		if (e.getSource() == getBtnImport()) {
			getFotoOptiesPanel().setjComponent(getBtnImport());
			Object[] array = {getFotoOptiesPanel()};
			JOptionPane.showConfirmDialog(null,array,"Foto opties",JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource() == getBtnRemove()) {
			String message = "Weet u zeker dat u de foto's wil verwijderen?";
			String[] choices = {"JA", "NEE"};
			int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.WARNING_MESSAGE, null, choices, choices[1]);
			if (result != 0) return;
			List<String> filenames = new ArrayList<String>();
			if (getjPanel1() != null && getjPanel1().getBackground() == Color.blue) {
				MouseListener[] mouseListeners = getjPanel1().getMouseListeners();
				for (MouseListener mouseListener : mouseListeners) {
					if (mouseListener instanceof PhotoMouseListener) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener) mouseListener;
						filenames.add(photoMouseListener.getFilename());
						((JLabel)getjPanel1().getComponent(0)).removeAll();
						getjPanel1().removeAll();
						System.gc();
						setjPanel1(null);
						break;
					}
				}
			}
			if (getjPanel2() != null && getjPanel2().getBackground() == Color.blue) {
				MouseListener[] mouseListeners = getjPanel2().getMouseListeners();
				for (MouseListener mouseListener : mouseListeners) {
					if (mouseListener instanceof PhotoMouseListener) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener) mouseListener;
						filenames.add(photoMouseListener.getFilename());
						((JLabel)getjPanel2().getComponent(0)).removeAll();
						getjPanel2().removeAll();
						System.gc();
						setjPanel2(null);
						break;
					}
				}
			}
			if (getjPanel3() != null && getjPanel3().getBackground() == Color.blue) {
				MouseListener[] mouseListeners = getjPanel3().getMouseListeners();
				for (MouseListener mouseListener : mouseListeners) {
					if (mouseListener instanceof PhotoMouseListener) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener) mouseListener;
						filenames.add(photoMouseListener.getFilename());
						((JLabel)getjPanel3().getComponent(0)).removeAll();
						getjPanel3().removeAll();
						System.gc();
						setjPanel3(null);
						break;
					}
				}
			}
			if (getjPanel4() != null && getjPanel4().getBackground() == Color.blue) {
				MouseListener[] mouseListeners = getjPanel4().getMouseListeners();
				for (MouseListener mouseListener : mouseListeners) {
					if (mouseListener instanceof PhotoMouseListener) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener) mouseListener;
						filenames.add(photoMouseListener.getFilename());
						((JLabel)getjPanel4().getComponent(0)).removeAll();
						getjPanel4().removeAll();
						System.gc();
						setjPanel4(null);
						break;
					}
				}
			}
			if (getjPanel5() != null && getjPanel5().getBackground() == Color.blue) {
				MouseListener[] mouseListeners = getjPanel5().getMouseListeners();
				for (MouseListener mouseListener : mouseListeners) {
					if (mouseListener instanceof PhotoMouseListener) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener) mouseListener;
						filenames.add(photoMouseListener.getFilename());
						((JLabel)getjPanel5().getComponent(0)).removeAll();
						getjPanel5().removeAll();
						System.gc();
						setjPanel5(null);
						break;
					}
				}
			}
			if (getjPanel6() != null && getjPanel6().getBackground() == Color.blue) {
				MouseListener[] mouseListeners = getjPanel6().getMouseListeners();
				for (MouseListener mouseListener : mouseListeners) {
					if (mouseListener instanceof PhotoMouseListener) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener) mouseListener;
						filenames.add(photoMouseListener.getFilename());
						((JLabel)getjPanel6().getComponent(0)).removeAll();
						getjPanel6().removeAll();
						System.gc();
						setjPanel6(null);
						break;
					}
				}
			}
			ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
			String dir = configuratie.getWaarde() + File.separator + "standplaats" + File.separator
						+ getFullStandplaatsNaam(getStandplaatsDTO()) + File.separator + "foto";
			reset();
			System.gc();
			getFotoService().remove(dir, filenames);
			setDataInGUI();
		} else if (e.getSource() == getBtnPrint()) {
			boolean ok = false;
			List<Map<String, Object>> fotosFacturaties = new ArrayList<Map<String,Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			String standplaats = Integer.toString(getStandplaatsDTO().getPlaatsnummer());
			while (standplaats.length() < 3) {
				standplaats = "0" + standplaats;
			}
			standplaats = getStandplaatsDTO().getPlaatsgroep() + standplaats;
			map.put(Constant.STANDPLAATS, standplaats);
			if (!StringUtils.isEmpty(getStandplaatsDTO().getFotoOpmerking())) {
				map.put(Constant.OPMERKING, getStandplaatsDTO().getFotoOpmerking());
				ok = true;
			}
			
			ConfiguratieDTO configuratieDTO = configuratieService.getFileDirectory();
			String path = configuratieDTO.getWaarde() + File.separator + "standplaats" + File.separator + standplaats
			+ File.separator + "foto" + File.separator + "facturatie" + File.separator;

			File file = new File(path);
			if (file.exists()) {
				List<String> fotosStandplaats = new ArrayList<String>();
				if (jCheckBox1.isSelected() && getjPanel1() != null) {
					MouseListener[] mouseListeners = getjPanel1().getMouseListeners();
					if (mouseListeners.length > 0) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener)mouseListeners[0];
						fotosStandplaats.add(path + photoMouseListener.getFilename());
					}
				}
				if (jCheckBox2.isSelected() && getjPanel2() != null) {
					MouseListener[] mouseListeners = getjPanel2().getMouseListeners();
					if (mouseListeners.length > 0) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener)mouseListeners[0];
						fotosStandplaats.add(path + photoMouseListener.getFilename());
					}
				}
				if (jCheckBox3.isSelected() && getjPanel3() != null) {
					MouseListener[] mouseListeners = getjPanel3().getMouseListeners();
					if (mouseListeners.length > 0) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener)mouseListeners[0];
						fotosStandplaats.add(path + photoMouseListener.getFilename());
					}
				}
				if (jCheckBox4.isSelected() && getjPanel4() != null) {
					MouseListener[] mouseListeners = getjPanel4().getMouseListeners();
					if (mouseListeners.length > 0) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener)mouseListeners[0];
						fotosStandplaats.add(path + photoMouseListener.getFilename());
					}
				}
				if (jCheckBox5.isSelected() && getjPanel5() != null) {
					MouseListener[] mouseListeners = getjPanel5().getMouseListeners();
					if (mouseListeners.length > 0) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener)mouseListeners[0];
						fotosStandplaats.add(path + photoMouseListener.getFilename());
					}
				}
				if (jCheckBox6.isSelected() && getjPanel6() != null) {
					MouseListener[] mouseListeners = getjPanel6().getMouseListeners();
					if (mouseListeners.length > 0) {
						PhotoMouseListener photoMouseListener = (PhotoMouseListener)mouseListeners[0];
						fotosStandplaats.add(path + photoMouseListener.getFilename());
					}
				}
				if (fotosStandplaats.size() > 0) {
					map.put(Constant.FOTOS, fotosStandplaats);
					fotosFacturaties.add(map);
					ok = true;
				}
				if (ok) {
					new FotoAndOpmerkingenFacturatiePdf(fotosFacturaties);
				}
			}
		}
	}

    private String getFullStandplaatsNaam(StandplaatsDTO standplaatsDTO) {
        String naam;
        int standplaatsnummer = standplaatsDTO.getPlaatsnummer();
        String standplaats = Integer.toString(standplaatsnummer);
        while (standplaats.length() < 3) {
            standplaats = "0" + standplaats;
        }
        naam = standplaatsDTO.getPlaatsgroep() + standplaats;
        return naam;
    }

	@Override
	public void keyPressed(KeyEvent e) {
		System.err.println("pressed key");
		int keyCode = e.getKeyCode();
		if (keyCode == 16) {
			System.err.println("pressed shift");
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.err.println("pressed key");
		int keyCode = e.getKeyCode();
		if (keyCode == 16) {
			System.err.println("released shift");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.err.println("pressed key");
	}
}
