package be.camping.campingzwaenepoel.presentation.dialog.persoon;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.ui.layout.GridLayout2;
import be.camping.campingzwaenepoel.service.PersoonService;

public class ChangeAndRemovePersoonDialog extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2849718169975921711L;

	private Java2sAutoComboBox jCboNieuwPersoon;
	private Java2sAutoComboBox jCboOudPersoon;
	private JButton jBtnChange;
	
	private List<String> personen;
	private List<Integer> persoonIds; 
	
	public List<String> getPersonen() {
		return personen;
	}

	public void setPersonen(List<String> personen) {
		this.personen = personen;
	}

	public List<Integer> getPersoonIds() {
		return persoonIds;
	}

	public void setPersoonIds(List<Integer> persoonIds) {
		this.persoonIds = persoonIds;
	}

	public Java2sAutoComboBox getjCboNieuwPersoon() {
		if (jCboNieuwPersoon == null) {
			jCboNieuwPersoon = new Java2sAutoComboBox(getPersonen());
			jCboNieuwPersoon.setCaseSensitive(true);
		}
		return jCboNieuwPersoon;
	}

	public Java2sAutoComboBox getjCboOudPersoon() {
		if (jCboOudPersoon == null) {
			jCboOudPersoon = new Java2sAutoComboBox(getPersonen());
			jCboOudPersoon.setCaseSensitive(true);
		}
		return jCboOudPersoon;
	}

	public JButton getjBtnChange() {
		if (jBtnChange == null) {
			jBtnChange = new JButton("vervang persoon");
			jBtnChange.setSize(new Dimension(200, 28));
			jBtnChange.addActionListener(this);
		}
		return jBtnChange;
	}

	public void setjBtnChange(JButton jBtnChange) {
		this.jBtnChange = jBtnChange;
	}

	public ChangeAndRemovePersoonDialog(PersoonService persoonService) {
		super("change and remove person dialog");
		this.persoonService = persoonService;
		initLists();
		initComponents();
	}
	
	private void initLists() {
		List<Object> list = getPersoonService().getPersonen();
		personen = new ArrayList<String>();
		persoonIds = new ArrayList<Integer>();
		personen.add("");
		persoonIds.add(0);
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[])list.get(i);
			String naam = (String)o[0] + " " + (String)o[1];
			personen.add(naam);
			persoonIds.add((Integer)o[2]);
		}

	}
	
	private PersoonService persoonService;
	
	public PersoonService getPersoonService() {
		return persoonService;
	}

	public void setPersoonService(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	private void initComponents () {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout2(1, 5));
		
		JPanel jPanelLabel1 = new JPanel();
		jPanelLabel1.add(new JLabel("persoon "));
		panel.add(jPanelLabel1);
		
		JPanel jPanelOudPersoon = new JPanel();
		jPanelOudPersoon.add(getjCboOudPersoon());
		panel.add(jPanelOudPersoon);

		JPanel jPanelLabel2 = new JPanel();
		jPanelLabel2.add(new JLabel(" vervangen door "));
		panel.add(jPanelLabel2);
		
		JPanel jPanelNieuwPersoon = new JPanel();
		jPanelNieuwPersoon.add(getjCboNieuwPersoon());
		panel.add(jPanelNieuwPersoon);
		
		JPanel jPanelSave = new JPanel();
		jPanelSave.add(getjBtnChange());
		panel.add(jPanelSave);
//		add(panel);
		
		// Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension panelDim = new Dimension(1000, 150);
	    this.setPreferredSize(panelDim);
	    
	    // Determine the new location of the window
	    int w = panelDim.width;
	    int h = panelDim.height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setLocation(x, y);
	    
	    addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    setVisible(false);
			    dispose();
			    System.exit(0);
			}
	    });

	    pack();

	    Object[] arrVastLos = {panel};
	    JOptionPane.showConfirmDialog(null,arrVastLos,"",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == getjBtnChange()) {
			if (jCboOudPersoon.getSelectedIndex() != 0 && jCboNieuwPersoon.getSelectedIndex() != 0) {
				int idSource = persoonIds.get(jCboOudPersoon.getSelectedIndex());
				int idTarget = persoonIds.get(jCboNieuwPersoon.getSelectedIndex());
				if (idSource != idTarget) {
					Object[] array = {"Weet u zeker dat u deze persoon wil vervangen? Deze actie is niet omkeerbaar!"};
					int ret = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (ret == JOptionPane.YES_OPTION) {
						getPersoonService().changePersoon(idTarget, idSource);
					}
				}
			}
		}
	}
}
