package be.camping.campingzwaenepoel.presentation.util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class SwingUtilities {

	public static JPanel getDivider(int width) {
		JPanel divider = new JPanel();
		divider.setBackground(Color.LIGHT_GRAY);
		divider.setPreferredSize(new Dimension(width, 1));
		return divider;
	}

}
