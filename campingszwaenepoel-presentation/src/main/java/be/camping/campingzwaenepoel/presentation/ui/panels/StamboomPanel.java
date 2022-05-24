/*
 * StamboomPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.utils.StringUtilities;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.StamboomService;
import be.camping.campingzwaenepoel.service.transfer.StamboomDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author  __USER__
 */
public class StamboomPanel extends javax.swing.JPanel implements PanelInterface, ActionListener {

	@Autowired
	private StamboomService stamboomService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<StamboomKoppelPanel>kinderenPanels;
	
	/** Creates new form StamboomPanel */
	public StamboomPanel() {
		initComponents();
	}

	private Controller controller;

	Font font = new Font("Times New Roman", Font.BOLD, 18);

	public javax.swing.JButton getjBtnSave() {
		if (jBtnSave == null) {
			jBtnSave = new JButton();
			jBtnSave.setText("OPSLAAN");
			
			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new OpslaanActionListener("Opslaan");
		    InputMap inputMap = jBtnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		    inputMap.put(stroke, actionKey);
		    ActionMap actionMap = jBtnSave.getActionMap();
		    actionMap.put(actionKey, action);
		    jBtnSave.addActionListener(this);
		}
		return jBtnSave;
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
	    	jBtnSave.doClick();
	    }
	}

	public javax.swing.JButton getjBtnNew() {
		if (jBtnNew == null) {
			jBtnNew = new JButton();
//			jBtnNew.setPreferredSize(new Dimension(220, 30));
			jBtnNew.setText("NIEUW");
			jBtnNew.addActionListener(this);
		}
		return jBtnNew;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public javax.swing.JPanel getjPanelKinderen(int numRows) {
		if (jPanelKinderen == null) {
			jPanelKinderen = new javax.swing.JPanel();
			/**
			 * Haal aantal koppels op (minimum 5)
			 * maak de panels aan (dus ook minimum 5)
			 * zet de info in de panels.
			 */
			jPanelKinderen.setLayout(new GridLayout(numRows, 1));
			numRows = (numRows < 5)?5:numRows;
			while (kinderenPanels.size() < numRows) {
				StamboomKoppelPanel koppelPanel = new StamboomKoppelPanel("", "");
				jPanelKinderen.add(koppelPanel);
				kinderenPanels.add(koppelPanel);
			}
		}
		return jPanelKinderen;
	}
	
	private int getKoppelsKinderen(List<StamboomDTO> kinderen) {
		int numOfKoppels = 0;
		List<StamboomDTO> koppels = new ArrayList<StamboomDTO>();
		for (StamboomDTO kind : kinderen) {
			if (!koppels.contains(kind)) {
				koppels.add(kind);
				StamboomDTO partner = getPartner(kinderen, kind.getRang());
				if (partner != null) {
					koppels.add(partner);
				}
				numOfKoppels++;
			}
		}
		return numOfKoppels;
	}
	
	private StamboomDTO getPartner(List<StamboomDTO> personen, int rang) {
		StamboomDTO partner = null;
		int rangPartner = (rang%2 != 0)?rang+1:rang-1;
		for (StamboomDTO persoon : personen) {
			if (persoon.getRang() == rangPartner) {
				partner = persoon;
				break;
			}
		}
		return partner;
	}
	
	private List<StamboomDTO> getKinderen(List<StamboomDTO> personen) {
		List<StamboomDTO> kinderen = new ArrayList<StamboomDTO>();
		for (StamboomDTO persoon : personen) {
			if (persoon.getGeneratie() == 3) {
				kinderen.add(persoon);
			}
		}
		return kinderen;
	}

	public JTextField getjTxtGrootmoeder() {
		if (jTxtGrootmoeder == null) {
			jTxtGrootmoeder = new JTextField();
			jTxtGrootmoeder.setPreferredSize(new Dimension(240, 36));
			jTxtGrootmoeder.setFont(font);

		}
		return jTxtGrootmoeder;
	}

	public JTextField getjTxtGrootvader() {
		if (jTxtGrootvader == null) {
			jTxtGrootvader = new JTextField();
			jTxtGrootvader.setPreferredSize(new Dimension(240, 36));
			jTxtGrootvader.setFont(font);
		}
		return jTxtGrootvader;
	}

	public void setjTxtGrootmoeder(JTextField jTxtGrootmoeder) {
		this.jTxtGrootmoeder = jTxtGrootmoeder;
	}

	public void setjTxtGrootvader(JTextField jTxtGrootvader) {
		this.jTxtGrootvader = jTxtGrootvader;
	}

	public void setjTxtEchtgenote(JTextField jTxtEchtgenote) {
		this.jTxtEchtgenote = jTxtEchtgenote;
	}

	public void setjTxtHoofd(JTextField jTxtHoofd) {
		this.jTxtHoofd = jTxtHoofd;
	}

	public void setjTxtSchoonmoeder(JTextField jTxtSchoonmoeder) {
		this.jTxtSchoonmoeder = jTxtSchoonmoeder;
	}

	public void setjTxtSchoonvader(JTextField jTxtSchoonvader) {
		this.jTxtSchoonvader = jTxtSchoonvader;
	}

	public JTextField getjTxtEchtgenote() {
		if (jTxtEchtgenote == null) {
			jTxtEchtgenote = new JTextField();
			jTxtEchtgenote.setPreferredSize(new Dimension(240, 36));
			jTxtEchtgenote.setFont(font);
//			jTxtEchtgenote.setForeground(Color.RED);
			Border raisedetched = BorderFactory.createLineBorder(Color.BLACK, 1);
			jTxtEchtgenote.setBorder(raisedetched);
		}
		return jTxtEchtgenote;
	}

	public JTextField getjTxtHoofd() {
		if (jTxtHoofd == null) {
			jTxtHoofd = new JTextField();
			jTxtHoofd.setPreferredSize(new Dimension(240, 36));
			jTxtHoofd.setFont(font);
//			jTxtHoofd.setForeground(Color.RED);
			Border raisedetched = BorderFactory.createLineBorder(Color.BLACK, 1);
			jTxtHoofd.setBorder(raisedetched);
//			jPanelNorth.setBorder(raisedetched);
			
		}
		return jTxtHoofd;
	}

	public JTextField getjTxtSchoonmoeder() {
		if (jTxtSchoonmoeder == null) {
			jTxtSchoonmoeder = new JTextField();
			jTxtSchoonmoeder.setPreferredSize(new Dimension(240, 36));
			jTxtSchoonmoeder.setFont(font);
		}
		return jTxtSchoonmoeder;
	}

	public JTextField getjTxtSchoonvader() {
		if (jTxtSchoonvader == null) {
			jTxtSchoonvader = new JTextField();
			jTxtSchoonvader.setPreferredSize(new Dimension(240, 36));
			jTxtSchoonvader.setFont(font);
		}
		return jTxtSchoonvader;
	}

	public javax.swing.JScrollPane getjScrollPaneOpmerking() {
		if (jScrollPaneOpmerking == null) {
			jScrollPaneOpmerking = new javax.swing.JScrollPane();
			jScrollPaneOpmerking.setFont(new java.awt.Font("Tahoma", 0, 12));
			jScrollPaneOpmerking.setHorizontalScrollBar(null);
			jScrollPaneOpmerking.setVerifyInputWhenFocusTarget(false);
			jScrollPaneOpmerking.setViewportView(getjTextAreaOpmerking());
		}
		return jScrollPaneOpmerking;
	}

	public javax.swing.JTextArea getjTextAreaOpmerking() {
		if (jTextAreaOpmerking == null) {
			jTextAreaOpmerking = new javax.swing.JTextArea();
			jTextAreaOpmerking.setColumns(20);
			jTextAreaOpmerking.setLineWrap(true);
			jTextAreaOpmerking.setRows(5);
			jTextAreaOpmerking.setWrapStyleWord(true);
			jTextAreaOpmerking.setFont(font);
		}
		return jTextAreaOpmerking;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
//		initializeVariables();
//		createStamboomPanel();
	}
	//GEN-END:initComponents

	private void createStamboomPanel(int numRows) {
		this.removeAll();
		
		setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jPanelLeft.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
		jPanelLeft.setLayout(jPanelLeftLayout);
		jPanelLeftLayout
				.setHorizontalGroup(jPanelLeftLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(getjScrollPaneOpmerking(),
								javax.swing.GroupLayout.PREFERRED_SIZE, 229,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(
								jPanelLeftLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanelLeftLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
/*														.addGroup(
																jPanelLeftLayout
																		.createSequentialGroup()
																		.addComponent(
																				getjTextFieldkoppels(),
																				0,
																				148,
																				Short.MAX_VALUE)
																		.addContainerGap())*/
														.addGroup(
																jPanelLeftLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
/*																		.addComponent(
																				getjBtnRemove(),
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)*/
																		.addComponent(
																				getjBtnNew(),
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				getjBtnSave(),
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				200,
																				Short.MAX_VALUE)))));
		jPanelLeftLayout.setVerticalGroup(jPanelLeftLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanelLeftLayout.createSequentialGroup().addComponent(
						getjScrollPaneOpmerking(),
						javax.swing.GroupLayout.PREFERRED_SIZE, 432,
						javax.swing.GroupLayout.PREFERRED_SIZE)/*.addGap(5, 10,
						18).addComponent(getjTextFieldkoppels(),
						javax.swing.GroupLayout.DEFAULT_SIZE, 34,
						Short.MAX_VALUE)/*.addGap(5, 10, 18).addComponent(
						getjBtnRemove(), javax.swing.GroupLayout.PREFERRED_SIZE, 34,
						javax.swing.GroupLayout.PREFERRED_SIZE)*/.addGap(5, 10,
						18).addComponent(getjBtnNew(),
						javax.swing.GroupLayout.PREFERRED_SIZE, 34,
						javax.swing.GroupLayout.PREFERRED_SIZE).addGap(5, 10,
						18).addComponent(getjBtnSave(),
						javax.swing.GroupLayout.PREFERRED_SIZE, 34,
						javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		javax.swing.GroupLayout jPanelCenterLayout = new javax.swing.GroupLayout(jPanelCenter);
		jPanelCenter.setLayout(jPanelCenterLayout);
		jPanelCenterLayout
				.setHorizontalGroup(jPanelCenterLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelCenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												getjTxtGrootvader(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(94, Short.MAX_VALUE))
						.addGroup(
								jPanelCenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												getjTxtGrootmoeder(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(94, Short.MAX_VALUE))
						.addGroup(
								jPanelCenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												getjTxtSchoonmoeder(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(94, Short.MAX_VALUE))
						.addGroup(
								jPanelCenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												getjTxtSchoonvader(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(94, Short.MAX_VALUE))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelCenterLayout
										.createSequentialGroup()
										.addContainerGap(94, Short.MAX_VALUE)
										.addGroup(
												jPanelCenterLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																getjTxtEchtgenote(),
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																getjTxtHoofd(),
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		jPanelCenterLayout
				.setVerticalGroup(jPanelCenterLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelCenterLayout
										.createSequentialGroup()
										.addGap(72, 72, 72)
										.addComponent(
												getjTxtGrootvader(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(26, 26, 26)
										.addComponent(
												getjTxtHoofd(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(27, 27, 27)
										.addComponent(
												getjTxtGrootmoeder(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												218, Short.MAX_VALUE)
										.addComponent(
												getjTxtSchoonvader(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(34, 34, 34)
										.addComponent(
												getjTxtEchtgenote(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(28, 28, 28)
										.addComponent(
												getjTxtSchoonmoeder(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(56, 56, 56)));

		jScrollPaneKinderen.setHorizontalScrollBar(null);

		jScrollPaneKinderen.setViewportView(getjPanelKinderen(numRows));

		javax.swing.GroupLayout jPanelRightLayout = new javax.swing.GroupLayout(jPanelRight);
		jPanelRight.setLayout(jPanelRightLayout);
		jPanelRightLayout.setHorizontalGroup(jPanelRightLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPaneKinderen,
						javax.swing.GroupLayout.DEFAULT_SIZE, 533,
						Short.MAX_VALUE));
		jPanelRightLayout.setVerticalGroup(jPanelRightLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelRightLayout.createSequentialGroup().addComponent(
								jScrollPaneKinderen,
								javax.swing.GroupLayout.DEFAULT_SIZE, 642,
								Short.MAX_VALUE).addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addComponent(
												jPanelLeft,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanelCenter,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanelRight,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanelCenter, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanelRight,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

	}
	
	private void initializeVariables() {
		jPanelLeft = new javax.swing.JPanel();
		jScrollPaneOpmerking = null;
		jTextAreaOpmerking = null;
		jBtnSave = null;
		jBtnNew = null;
//		jBtnRemove = null;
//		jTxtKoppels = null;
		jPanelCenter = new javax.swing.JPanel();
		setjTxtGrootvader(null);
		setjTxtGrootmoeder(null);
		setjTxtSchoonvader(null);
		setjTxtSchoonmoeder(null);
		setjTxtHoofd(null);
		setjTxtEchtgenote(null);
		jPanelRight = new javax.swing.JPanel();
		jScrollPaneKinderen = new javax.swing.JScrollPane();
		kinderenPanels = new ArrayList<StamboomKoppelPanel>();
		jPanelKinderen = null;
	}
	
	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkDataChanged() {
		boolean ret = false;
		List<StamboomDTO> stamboom = stamboomService.getStamboom(getController().getStandplaatsDTO().getId());
		StamboomDTO grootvader = null;
		StamboomDTO grootmoeder = null;
		StamboomDTO schoonvader = null;
		StamboomDTO schoonmoeder = null;
		StamboomDTO hoofd = null;
		StamboomDTO echtgenote = null;
		List<StamboomDTO> kinderen = getKinderen(stamboom);
		
		// define the above variables
		for (StamboomDTO persoon : stamboom) {
			switch (persoon.getGeneratie()) {
				case 1: 
					switch (persoon.getRang()) {
						case 1: grootvader = persoon;
								break;
						case 2: grootmoeder = persoon;
								break;
						case 3: schoonvader = persoon;
								break;
						case 4: schoonmoeder = persoon;
								break;
					}
					break;
				case 2:
					switch (persoon.getRang()) {
						case 1: hoofd = persoon;
								break;
						case 2: echtgenote = persoon;
								break;
					}
					break;
			}
		}
		
		// check grootvader
		if ((grootvader == null && !StringUtils.isEmpty(getjTxtGrootvader().getText()))
				|| ((grootvader != null) && 
					((!StringUtils.isEmpty(grootvader.getNaam()) && StringUtils.isEmpty(getjTxtGrootvader().getText()))
					   || (StringUtils.isEmpty(grootvader.getNaam()) && !StringUtils.isEmpty(getjTxtGrootvader().getText()))
					   || (!grootvader.getNaam().equals(getjTxtGrootvader().getText()))))) {
			ret = true;
		}
		// check grootmoeder
		if (!ret && ((grootmoeder == null && !StringUtils.isEmpty(getjTxtGrootmoeder().getText()))
				|| ((grootmoeder != null) && 
					((!StringUtils.isEmpty(grootmoeder.getNaam()) && StringUtils.isEmpty(getjTxtGrootmoeder().getText()))
					   || (StringUtils.isEmpty(grootmoeder.getNaam()) && !StringUtils.isEmpty(getjTxtGrootmoeder().getText()))
					   || (!grootmoeder.getNaam().equals(getjTxtGrootmoeder().getText())))))) {
			ret = true;
		}
		// check schoonvader
		if (!ret && ((schoonvader == null && !StringUtils.isEmpty(getjTxtSchoonvader().getText()))
				|| ((schoonvader != null) && 
					((!StringUtils.isEmpty(schoonvader.getNaam()) && StringUtils.isEmpty(getjTxtSchoonvader().getText()))
					   || (StringUtils.isEmpty(schoonvader.getNaam()) && !StringUtils.isEmpty(getjTxtSchoonvader().getText()))
					   || (!schoonvader.getNaam().equals(getjTxtSchoonvader().getText())))))) {
			ret = true;
		}
		// check schoonmoeder
		if (!ret && ((schoonmoeder == null && !StringUtils.isEmpty(getjTxtSchoonmoeder().getText()))
				|| ((schoonmoeder != null) && 
					((!StringUtils.isEmpty(schoonmoeder.getNaam()) && StringUtils.isEmpty(getjTxtSchoonmoeder().getText()))
					   || (StringUtils.isEmpty(schoonmoeder.getNaam()) && !StringUtils.isEmpty(getjTxtSchoonmoeder().getText()))
					   || (!schoonmoeder.getNaam().equals(getjTxtSchoonmoeder().getText())))))) {
			ret = true;
		}
		// check hoofd
		if (!ret && ((hoofd == null && !StringUtils.isEmpty(getjTxtHoofd().getText()))
				|| ((hoofd != null) && 
					((!StringUtils.isEmpty(hoofd.getNaam()) && StringUtils.isEmpty(getjTxtHoofd().getText()))
					   || (StringUtils.isEmpty(hoofd.getNaam()) && !StringUtils.isEmpty(getjTxtHoofd().getText()))
					   || (!hoofd.getNaam().equals(getjTxtHoofd().getText())))))) {
			ret = true;
		}
		// check hoofd opmerking
		if (!ret && ((hoofd == null && !StringUtils.isEmpty(getjTxtHoofd().getText()))
				|| ((hoofd != null) && (
						(!StringUtils.isEmpty(hoofd.getOpmerking()) && StringUtils.isEmpty(getjTextAreaOpmerking().getText()))
						|| (StringUtils.isEmpty(hoofd.getOpmerking()) && !StringUtils.isEmpty(getjTextAreaOpmerking().getText()))
				)))) {
			ret = true;
		}
		// check echtgenote
		if (!ret && ((echtgenote == null && !StringUtils.isEmpty(getjTxtEchtgenote().getText()))
				|| ((echtgenote != null) && 
					((!StringUtils.isEmpty(echtgenote.getNaam()) && StringUtils.isEmpty(getjTxtEchtgenote().getText()))
					   || (StringUtils.isEmpty(echtgenote.getNaam()) && !StringUtils.isEmpty(getjTxtEchtgenote().getText()))
					   || (!echtgenote.getNaam().equals(getjTxtEchtgenote().getText())))))) {
			ret = true;
		}
		// check kinderen
		int teller = 1;
		for (StamboomKoppelPanel koppelPanel : kinderenPanels) {
			StamboomDTO kind = findPersoon(kinderen, teller++);
			StamboomDTO partner = findPersoon(kinderen, teller++);
			if (kind != null) {
				if (!StringUtilities.equals(kind.getNaam(), koppelPanel.getKind())) {
					ret = true;
				}
				if (!StringUtilities.equals(kind.getOpmerking(), koppelPanel.getOpmerking())) {
					ret = true;
				}
			} else if (!StringUtils.isEmpty(koppelPanel.getKind())) {
				ret = true;
			}
			if (partner != null) {
				if (!StringUtilities.equals(partner.getOpmerking(), koppelPanel.getOpmerking())) {
					ret = true;
				}
				if (!StringUtilities.equals(partner.getNaam(), koppelPanel.getPartner())) {
					ret = true;
				}
			} else if (!StringUtils.isEmpty(koppelPanel.getPartner())) {
				ret = true;
			}
		}
		return ret;
	}
	
	private StamboomDTO findPersoon(List<StamboomDTO> personen, int index) {
		StamboomDTO stamboomDTO = null;
		for (StamboomDTO persoon : personen) {
			if (persoon.getRang() == index) {
				stamboomDTO = persoon;
				break;
			}
		}
		return stamboomDTO;
	}

	@Override
	public boolean checkDataForParent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getDataFromGUI() {
		List<Map<String, StamboomDTO>> stamboom = new ArrayList<Map<String, StamboomDTO>>();
		int standplaatsId = getController().getStandplaatsDTO().getId();
		
		// grootouders
		String naam = getjTxtGrootvader().getText();
		Map<String, StamboomDTO> mapGrootouders = new HashMap<String, StamboomDTO>();
		// Grootvader
		boolean grootouders = false;
		if (!StringUtils.isEmpty(naam)) {
			StamboomDTO persoon = new StamboomDTO();
			persoon.setNaam(naam);
			persoon.setGeneratie(1);
			persoon.setRang(1);
			persoon.setStandplaatsId(standplaatsId);
			mapGrootouders.put("persoon", persoon);
			grootouders = true;
		}
		
		// grootmoeder
		naam = getjTxtGrootmoeder().getText();
		if (!StringUtils.isEmpty(naam)) {
			StamboomDTO persoon = new StamboomDTO();
			persoon.setNaam(naam);
			persoon.setGeneratie(1);
			persoon.setRang(2);
			persoon.setStandplaatsId(standplaatsId);
			mapGrootouders.put("partner", persoon);
			grootouders = true;
		}
		
		if (grootouders) {
			stamboom.add(mapGrootouders);
		}
		// *********************************
		
		// schoonouders
		naam = (String)getjTxtSchoonvader().getText();
		Map<String, StamboomDTO> mapSchoonouders = new HashMap<String, StamboomDTO>();
		boolean schoonouders = false;

		// Grootvader
		if (!StringUtils.isEmpty(naam)) {
			StamboomDTO persoon = new StamboomDTO();
			persoon.setNaam(naam);
			persoon.setGeneratie(1);
			persoon.setRang(3);
			persoon.setStandplaatsId(standplaatsId);
			mapSchoonouders.put("persoon", persoon);
			schoonouders = true;
		}
		
		// grootmoeder
		naam = (String)getjTxtSchoonmoeder().getText();
		if (!StringUtils.isEmpty(naam)) {
			StamboomDTO persoon = new StamboomDTO();
			persoon.setNaam(naam);
			persoon.setGeneratie(1);
			persoon.setRang(4);
			persoon.setStandplaatsId(standplaatsId);
			mapSchoonouders.put("partner", persoon);
			schoonouders = true;
		}
		
		if (schoonouders) {
			stamboom.add(mapSchoonouders);
		}
		// *********************************

		// ouders
		naam = (String)getjTxtHoofd().getText();
		Map<String, StamboomDTO> mapOuders = new HashMap<String, StamboomDTO>();
		// vader
		boolean ouders = false;
		boolean opmerkingOuder = false;
		if (!StringUtils.isEmpty(naam)) {
			StamboomDTO persoon = new StamboomDTO();
			persoon.setNaam(naam);
			persoon.setGeneratie(2);
			persoon.setRang(1);
			persoon.setStandplaatsId(standplaatsId);
			persoon.setOpmerking(getjTextAreaOpmerking().getText());
			opmerkingOuder = true;
			mapOuders.put("persoon", persoon);
			ouders = true;
		}
		
		// moeder
		naam = (String)getjTxtEchtgenote().getText();
		if (!StringUtils.isEmpty(naam)) {
			StamboomDTO persoon = new StamboomDTO();
			persoon.setNaam(naam);
			persoon.setGeneratie(2);
			persoon.setRang(2);
			persoon.setStandplaatsId(standplaatsId);
			if (!opmerkingOuder) {
				persoon.setOpmerking(getjTextAreaOpmerking().getText());
			}
			mapOuders.put("partner", persoon);
			ouders = true;
		}
		
		if (ouders) {
			stamboom.add(mapOuders);
		}
		// *********************************

		
		// kinderen
		int kinderrang = 1;
		for (StamboomKoppelPanel koppelPanel : kinderenPanels) {
			naam = (String)koppelPanel.getTxtKind().getText();
			Map<String, StamboomDTO> mapKinderen = new HashMap<String, StamboomDTO>();
			// vader
			boolean kinderen = false;
//			boolean opmerking = false;
			if (!StringUtils.isEmpty(naam)) {
				StamboomDTO persoon = new StamboomDTO();
				persoon.setNaam(naam);
				persoon.setGeneratie(3);
				persoon.setRang(kinderrang);
				persoon.setStandplaatsId(standplaatsId);
				persoon.setOpmerking(koppelPanel.getOpmerking());
				mapKinderen.put("persoon", persoon);
				kinderen = true;
//				opmerking = true;
			}
			kinderrang++;
			
			// moeder
			naam = (String)koppelPanel.getTxtPartner().getText();
			if (!StringUtils.isEmpty(naam)) {
				StamboomDTO persoon = new StamboomDTO();
				persoon.setNaam(naam);
				persoon.setGeneratie(3);
				persoon.setRang(kinderrang);
				persoon.setStandplaatsId(standplaatsId);
//				if (!opmerking) {
					persoon.setOpmerking(koppelPanel.getOpmerking());
//				}
				mapKinderen.put("partner", persoon);
				kinderen = true;
			}
			kinderrang++;
//			opmerking = false;
			
			if (kinderen) {
				stamboom.add(mapKinderen);
			}
		}
		return stamboom;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save() {
        stamboomService.removeStamboomVanStandplaats(getController().getStandplaatsDTO().getId());
		List<Map<String, StamboomDTO>> stamboom = (List<Map<String, StamboomDTO>>)getDataFromGUI();
		for (Map<String, StamboomDTO> map : stamboom) {
			boolean hasPersoon = (map.containsKey("persoon"))?true:false;
			boolean hasPartner = (map.containsKey("partner"))?true:false;
			StamboomDTO persoon = null;
			StamboomDTO partner = null;
			if (hasPersoon) {
				persoon = map.get("persoon");
				persoon = stamboomService.storeStamboom(persoon);
			}
			if (hasPartner) {
				partner = map.get("partner");
				partner = stamboomService.storeStamboom(partner);
			}
		}
	}

	@Override
	public void setDataInGUI() {
		initializeVariables();
		List<StamboomDTO> stamboom = stamboomService.getStamboom(getController().getStandplaatsDTO().getId());
		List<StamboomDTO> kinderen = getKinderen(stamboom);
		int kinderKoppels = getKoppelsKinderen(kinderen);
		kinderKoppels = (kinderKoppels < 5)?5:kinderKoppels;
		createStamboomPanel(kinderKoppels);
		setStamboomData(stamboom);
	}
	
	private void setStamboomData(List<StamboomDTO> stamboom) {
		for(StamboomDTO persoon : stamboom) {
			switch (persoon.getGeneratie()) {
				case 1: 
					switch (persoon.getRang()) {
						case 1: getjTxtGrootvader().setText(persoon.getNaam());
								break;
						case 2: getjTxtGrootmoeder().setText(persoon.getNaam());
								break;
						case 3: getjTxtSchoonvader().setText(persoon.getNaam());
								break;
						case 4: getjTxtSchoonmoeder().setText(persoon.getNaam());
								break;
					}
					break;
				case 2:
					switch (persoon.getRang()) {
						case 1: getjTxtHoofd().setText(persoon.getNaam());
								getjTextAreaOpmerking().setText(persoon.getOpmerking());
								break;
						case 2: getjTxtEchtgenote().setText(persoon.getNaam());
								break;
					}
					break;
				case 3:
					if (persoon.getRang()%2 != 0) {
						int kinderPanel = (int)persoon.getRang()/2;
						StamboomKoppelPanel koppelPanel = kinderenPanels.get(kinderPanel); 
						koppelPanel.setKind(persoon.getNaam());
						if (!StringUtils.isEmpty(persoon.getOpmerking())){
							koppelPanel.setOpmerking(persoon.getOpmerking());
						}
					} else {
						int kinderPanel = (int)persoon.getRang()/2 - 1;
						StamboomKoppelPanel koppelPanel = kinderenPanels.get(kinderPanel); 
						koppelPanel.setPartner(persoon.getNaam());
						if (!StringUtils.isEmpty(persoon.getOpmerking())) {
							koppelPanel.setOpmerking(persoon.getOpmerking());
						}
					}
					break;
			}
		}
	}

	@Override
	public void setRemarksInGui() {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jBtnSave) {
			save();
		} else if (e.getSource() == getjBtnNew()) {
			StamboomKoppelPanel koppelPanel = new StamboomKoppelPanel("", "");
			jPanelKinderen.setLayout(new GridLayout(kinderenPanels.size()+1, 1));
			jPanelKinderen.add(koppelPanel);
			kinderenPanels.add(koppelPanel);
			jPanelKinderen.repaint();
		}
		
		/*else if (e.getSource() == jBtnRemove) {
			int index = getCboStandplaatsDetails().getSelectedIndex();
			if (index != 0) {
				if (StringUtils.isEmpty((String)getCboStandplaatsDetails().getDataList().get(0))) {
					index--;
				}
				getStandplaatsController().removeStandplaatsDetailDTO(standplaatsDetailDTOs.get(index));
				getStandplaatsController().removeStandplaatsInformatieByNumber(standplaatsDetailDTOs.get(index).getNummer());
				for (GrondInformatieDTO grondInformatieDTO : controller.getStandplaatsDTO().getGrondInformaties()) {
					if (grondInformatieDTO.getNummer().equals(standplaatsDetailDTOs.get(index).getNummer())) {
						controller.getStandplaatsDTO().getGrondInformaties().remove(grondInformatieDTO);
//						getStandplaatsController().store(controller.getStandplaatsDTO());
						break;
					}
				}
				standplaatsDetailDTOs.remove(index);
				resetFieldPanel();
				setDataInGUI();
			}
		} else if (e.getSource() == btnNewDateField) {
			if (checkDataChanged()) {
				SaveDialog dialog = new SaveDialog();
				boolean ok = dialog.isUnsavedChanges();
				if (ok) {
					save();
				}
				dialog.dispose();
			}
			StandplaatsDetailDTO standplaatsDetailDTO = new StandplaatsDetailDTO();
			standplaatsDetailDTO.setDatumveld(true);
			standplaatsDetailDTO.setNummer(new Short(Integer.toString(standplaatsDetailDTOs.size() + 1)));
			getStandplaatsController().storeStandplaatsDetailDTO(standplaatsDetailDTO);
			standplaatsDetailDTOs.add(standplaatsDetailDTO);
			resetFieldPanel();
			setDataInGUI();
		}*/
	}

//GEN-BEGIN:variables
// Variables declaration - do not modify
private javax.swing.JButton jBtnSave;
private javax.swing.JButton jBtnNew;
//private javax.swing.JButton jBtnRemove;
//private JTextField jTxtKoppels;
private javax.swing.JPanel jPanelCenter;
private javax.swing.JPanel jPanelKinderen;
private JTextField jTxtEchtgenote;
private JTextField jTxtGrootmoeder;
private JTextField jTxtGrootvader;
private JTextField jTxtHoofd;
private javax.swing.JPanel jPanelLeft;
private javax.swing.JPanel jPanelRight;
private JTextField jTxtSchoonmoeder;
private JTextField jTxtSchoonvader;
private javax.swing.JScrollPane jScrollPaneKinderen;
private javax.swing.JScrollPane jScrollPaneOpmerking;
private javax.swing.JTextArea jTextAreaOpmerking;
// End of variables declaration//GEN-END:variables
}