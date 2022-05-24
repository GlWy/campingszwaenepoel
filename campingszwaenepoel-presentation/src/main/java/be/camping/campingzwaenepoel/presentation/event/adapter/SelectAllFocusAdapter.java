package be.camping.campingzwaenepoel.presentation.event.adapter;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class SelectAllFocusAdapter extends FocusAdapter {

	@Override
	public void focusGained(FocusEvent e) {
		super.focusGained(e);

		Object obj = e.getSource();
		if (obj instanceof JTextField) {
			((JTextField) obj).selectAll();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {

	}
}
