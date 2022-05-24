/*
 * StamboomKinderen.java
 *
 * Created on __DATE__, __TIME__
 */

package be.camping.campingzwaenepoel.presentation.ui.panels;


import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

/**
 *
 * @author  __USER__
 */
public class StamboomKoppelPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String kind;
	@SuppressWarnings("unused")
	private String partner;
	Font font = new Font("Times New Roman", Font.BOLD, 18);
	
	/** Creates new form StamboomKinderen */
	public StamboomKoppelPanel() {
		initComponents();
	}
	
	public StamboomKoppelPanel(String kind, String partner) {
		this.kind = kind;
		this.partner = partner;
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																getTxtKind(),
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				19,
																				19,
																				19)
																		.addComponent(
																				getTxtPartner(),
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												getjScrollPaneOpmerking(),
												javax.swing.GroupLayout.DEFAULT_SIZE,
												229, Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addComponent(
												getTxtKind(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												getTxtPartner(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(19, 19, 19)).addComponent(
								getjScrollPaneOpmerking(),
								javax.swing.GroupLayout.DEFAULT_SIZE, 89,
								Short.MAX_VALUE));
	}// </editor-fold>
	//GEN-END:initComponents

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private JTextField txtKind;
	private JTextField txtPartner;
	private javax.swing.JScrollPane jScrollPaneOpmerking;
	private javax.swing.JTextArea jTextAreaOpmerking;
	// End of variables declaration//GEN-END:variables

	public String getKind() {
		return getTxtKind().getText();
	}

	public String getPartner() {
		return getTxtPartner().getText();
	}
	
	public void setKind(String naam) {
		txtKind.setText(naam);
		this.kind = naam;
	}
	
	public void setPartner(String naam) {
		txtPartner.setText(naam);
		this.partner = naam;
	}

	public JTextField getTxtKind() {
		if (txtKind == null) {
			txtKind = new JTextField();
			txtKind.setPreferredSize(new Dimension(240, 36));
			txtKind.setFont(font);
		}
		return txtKind;
	}

	public JTextField getTxtPartner() {
		if (txtPartner == null) {
			txtPartner = new JTextField();
			txtPartner.setPreferredSize(new Dimension(240, 36));
			txtPartner.setFont(font);
		}
		return txtPartner;
	}

	public javax.swing.JScrollPane getjScrollPaneOpmerking() {
		if (jScrollPaneOpmerking == null) {
			jScrollPaneOpmerking = new javax.swing.JScrollPane();

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
	
	public String getOpmerking() {
		return getjTextAreaOpmerking().getText();
	}
	
	public void setOpmerking(String opmerking) {
		getjTextAreaOpmerking().setText(opmerking);
	}

}