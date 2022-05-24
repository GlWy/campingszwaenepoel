package be.camping.campingzwaenepoel.presentation.common;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SwingUtilities {

	public static GridBagConstraints getGridBagConstraints(int x, int y, int anchor, int fill, Insets insets,
			double weightx, double weighty, int gridwidth, int gridheight) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;

		if (insets != null) {
			gridBagConstraints.insets = insets;
		}

		if (anchor != -1) {
			gridBagConstraints.anchor = anchor;
		}

		if (fill != -1) {
			gridBagConstraints.fill = fill;
		}

		if (weightx != -1) {
			gridBagConstraints.weightx = weightx;
		}

		if (weighty != -1) {
			gridBagConstraints.weighty = weighty;
		}

		if (gridwidth != -1) {
			gridBagConstraints.gridwidth = gridwidth;
		}

		if (gridheight != -1) {
			gridBagConstraints.gridheight = gridheight;
		}

		return gridBagConstraints;
	}

}
