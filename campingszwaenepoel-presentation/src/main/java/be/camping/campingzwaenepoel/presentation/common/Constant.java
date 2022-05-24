package be.camping.campingzwaenepoel.presentation.common;

import javax.swing.JOptionPane;

public class Constant {

	private static final int DIALOG_YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION;
	private static final int DIALOG_YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
	
	public static int  getYesNoCancelOption() {
		return DIALOG_YES_NO_CANCEL_OPTION;
	}
	
	public static int getYesNoOption() {
		return DIALOG_YES_NO_OPTION;
	}
}
