package be.camping.campingzwaenepoel.presentation.ui.panels.interfaces;

public interface PanelInterface {

	public void setDataInGUI();
	
	public Object getDataFromGUI();
	
	/**
	 * This method can be called from another class to check if data has been changed.
	 * When selecting another tab or standplaats, this method will be called.
	 * This method helps preventing the loss of data.
	 * @author glennwybo
	 * @return boolean true=data has been changed, false, data did not change
	 */
	public boolean checkDataChanged();
	
	/**
	 * 
	 * @return boolean true/false = data is valid / not valid
	 */
	public boolean checkData();
	
	public void setRemarksInGui();
	
	public void save();
	
	public boolean checkDataForParent();
	
}
