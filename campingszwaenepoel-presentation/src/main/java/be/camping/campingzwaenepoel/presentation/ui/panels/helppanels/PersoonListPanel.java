package be.camping.campingzwaenepoel.presentation.ui.panels.helppanels;

import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PersoonListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public PersoonService getPersoonService() {
		return persoonService;
	}

	public void setPersoonService(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	private JList jListPersonen = null;

	private JScrollPane jScrollPane = null;

	private final Font font = new Font("Times New Roman", Font.PLAIN, 18);

	private Controller controller;
	private PersoonService persoonService;

	private PersoonDTO persoon = DTOFactory.getPersoonDTO();

	Insets insets = new Insets(5, 0, 0, 5);

	public PersoonListPanel() {
		initComponents();
	}

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

		String listData[] = {};
		jListPersonen = new JList(listData);

		jScrollPane = new JScrollPane(jListPersonen);
		jScrollPane.setPreferredSize(new Dimension(200, 600));
		jScrollPane.setMinimumSize(new Dimension(200, 500));
		jScrollPane.setFont(font);

		// personPanel = new JPanel();
		// personPanel.setBorder(new LineBorder(Color.BLACK, 2));
		// personPanel.add(jScrollPane);

		// this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK, 1));
		this.add(jScrollPane);
	}

	public PersoonDTO getPersoon() {
		return persoon;
	}

	public void setPersoon(PersoonDTO persoon) {
		this.persoon = persoon;
	}

	public JList getjListPersonen() {
		return jListPersonen;
	}

}