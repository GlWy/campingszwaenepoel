package be.camping.campingzwaenepoel.presentation.ui.panels;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;

public class InUitPanel extends JPanel implements PanelInterface {

	private static final long serialVersionUID = 1L;

    private Controller controller;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkDataChanged() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub

	}

	public void setDataInGUI() {
		// TODO Auto-generated method stub

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
//		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
	}

}
