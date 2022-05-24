package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import be.camping.campingzwaenepoel.common.constants.Constant;

public class PrijsAanpassingenDialog extends JPanel implements FocusListener {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPanePrijzen = null;
	private JTable jTablePrijzen = null;
	private JPanel jPanelAanpassingenPrijzen = null;
	private JPanel jPanelBelastingen = null;
	private JPanel jPanelPrijsopslag = null;
	private JLabel jLabelTitel = null;
	private JLabel jLabelOudeWaarde = null;
	private JLabel jLabelNieuweWaarde = null;
	private JTextField jTxtOudeWaarde = null;
	private JTextField jTxtNieuweWaarde = null;
	private JLabel jLabelOpslag = null;
	private JTextField jTxtOpslag = null;
	private List<Map<String, Object>> grondprijzen = new ArrayList<Map<String, Object>>();
	private final String gemeentelijkeBelasting;
	private final DecimalFormat df = new DecimalFormat("#,##0.00");

	/**
	 * This is the default constructor
	 */
	public PrijsAanpassingenDialog(List<Map<String, Object>> grondprijzen, String gemeentelijkeBelasting) {
		super();
		this.grondprijzen = grondprijzen;
		this.gemeentelijkeBelasting = gemeentelijkeBelasting;
		initialize();
	}

	private String getGemeentelijkeBelasting() {
		return gemeentelijkeBelasting;
	}

	public String getAangepasteGemeentelijkeBelasting() throws ParseException {
		Number nGemeenteBelasting = df.parse(getGemeentelijkeBelasting());
		double dGemeenteBelasting = nGemeenteBelasting.doubleValue();
		dGemeenteBelasting += getSum();
		String s = Double.toString(dGemeenteBelasting);
		s = s.replace(".", ",");
		return s;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(915, 630);
		this.setLayout(new BorderLayout());
		this.add(getJScrollPanePrijzen(), BorderLayout.WEST);
		this.add(getJPanelAanpassingenPrijzen(), BorderLayout.EAST);
	}

	/**
	 * This method initializes jScrollPanePrijzen
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPanePrijzen() {
		if (jScrollPanePrijzen == null) {
			jScrollPanePrijzen = new JScrollPane();
			jScrollPanePrijzen.setViewportView(getJTablePrijzen());
		}
		return jScrollPanePrijzen;
	}

	/**
	 * This method initializes jTablePrijzen
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getJTablePrijzen() {
		if (jTablePrijzen == null) {
			PrijsAanpassingenTabelModel model = new PrijsAanpassingenTabelModel();
			jTablePrijzen = new JTable(model);
		}
		return jTablePrijzen;
	}

	/**
	 * This method initializes jPanelAanpassingenPrijzen
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelAanpassingenPrijzen() {
		if (jPanelAanpassingenPrijzen == null) {
			jPanelAanpassingenPrijzen = new JPanel();
			jPanelAanpassingenPrijzen.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(0, 10, 20, 0);
			gridBagConstraints1.weightx = 1.0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.insets = new Insets(20, 0, 0, 0);
			gridBagConstraints2.weightx = 1.0;
			jPanelAanpassingenPrijzen.add(getJPanelBelastingen(), gridBagConstraints1);
			jPanelAanpassingenPrijzen.add(getJPanelPrijsopslag(), gridBagConstraints2);
		}
		return jPanelAanpassingenPrijzen;
	}

	/**
	 * This method initializes jPanelBelastingen
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelBelastingen() {
		if (jPanelBelastingen == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.insets = new Insets(30, 0, 15, 0);
			gridBagConstraints4.gridwidth = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints3.weightx = 1.0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints2.weightx = 1.0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints1.weightx = 1.0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			gridBagConstraints.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints.weightx = 1.0;
			jLabelNieuweWaarde = new JLabel();
			jLabelNieuweWaarde.setText("Nieuwe waarde: ");
			jLabelOudeWaarde = new JLabel();
			jLabelOudeWaarde.setText("Oude waarde:");
			jLabelTitel = new JLabel();
			jLabelTitel.setText("Gemeentebelastingen");
			jPanelBelastingen = new JPanel();
			jPanelBelastingen.setBorder(BorderFactory.createRaisedBevelBorder());
			jPanelBelastingen.setLayout(new GridBagLayout());
			jPanelBelastingen.add(jLabelTitel, gridBagConstraints4);
			jPanelBelastingen.add(jLabelOudeWaarde, gridBagConstraints3);
			jPanelBelastingen.add(jLabelNieuweWaarde, gridBagConstraints2);
			jPanelBelastingen.add(getJTxtOudeWaarde(), gridBagConstraints);
			jPanelBelastingen.add(getJTxtNieuweWaarde(), gridBagConstraints1);
		}
		return jPanelBelastingen;
	}

	/**
	 * This method initializes jPanelPrijsopslag
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPrijsopslag() {
		if (jPanelPrijsopslag == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(20, 0, 10, 0);
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints2.gridy = 1;
			jLabelOpslag = new JLabel();
			jLabelOpslag.setText("Prijsopslag:");
			jPanelPrijsopslag = new JPanel();
			jPanelPrijsopslag.setLayout(new GridBagLayout());
			jPanelPrijsopslag.setBorder(BorderFactory.createRaisedBevelBorder());
			jPanelPrijsopslag.add(jLabelOpslag, gridBagConstraints1);
			jPanelPrijsopslag.add(getJTxtOpslag(), gridBagConstraints2);
		}
		return jPanelPrijsopslag;
	}

	/**
	 * This method initializes jTxtOudeWaarde
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtOudeWaarde() {
		if (jTxtOudeWaarde == null) {
			jTxtOudeWaarde = new JTextField();
			jTxtOudeWaarde.setText(getGemeentelijkeBelasting() + " \u20ac");
			jTxtOudeWaarde.setHorizontalAlignment(JTextField.CENTER);
			jTxtOudeWaarde.setPreferredSize(new Dimension(150, 30));
			jTxtOudeWaarde.addFocusListener(this);
			jTxtOudeWaarde.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtNieuweWaarde().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtOudeWaarde;
	}

	/**
	 * This method initializes jTxtNieuweWaarde
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtNieuweWaarde() {
		if (jTxtNieuweWaarde == null) {
			jTxtNieuweWaarde = new JTextField();
			jTxtNieuweWaarde.setText(getGemeentelijkeBelasting() + " \u20ac");
			jTxtNieuweWaarde.setHorizontalAlignment(JTextField.CENTER);
			jTxtNieuweWaarde.setPreferredSize(new Dimension(150, 30));
			jTxtNieuweWaarde.addFocusListener(this);
			jTxtNieuweWaarde.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtOpslag().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtNieuweWaarde;
	}

	/**
	 * This method initializes jTxtOpslag
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtOpslag() {
		if (jTxtOpslag == null) {
			jTxtOpslag = new JTextField();
			jTxtOpslag.setHorizontalAlignment(JTextField.CENTER);
			jTxtOpslag.setPreferredSize(new Dimension(150, 30));
			jTxtOpslag.setText("0,00%");
			jTxtOpslag.addFocusListener(this);
			jTxtOpslag.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						getJTxtOudeWaarde().requestFocusInWindow();
					}
				}
			});
		}
		return jTxtOpslag;
	}

	private Vector<Object[]> getColumnData() {
		Vector<Object[]> prijzen = new Vector<Object[]>();
		double dGemeenteBelasting = Double.valueOf(gemeentelijkeBelasting.replace(",", "."));
		for (Map<String, Object> map : getGrondprijzen()) {
			Double d = (Double) map.get(Constant.BASISPRIJS);
			d += dGemeenteBelasting;
			String s = df.format(d);
			Object[] o = { map.get(Constant.AANTAL_GRONDPRIJS), map.get(Constant.BASISPRIJS), s };
			prijzen.add(o);
		}
		return prijzen;
	}

	public List<Map<String, Object>> getGrondprijzen() {
		return grondprijzen;
	}

	public void setGrondprijzen(List<Map<String, Object>> grondprijzen) {
		this.grondprijzen = grondprijzen;
	}

	private class PrijsAanpassingenTabelModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String[] columnNames = { "Aantal", "Huidige prijs", "Nieuwe Prijs" };
		private final Vector<Object[]> data = getColumnData();

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			Object[] o = data.get(row);
			return o[col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for each cell. If we didn't implement this
		 * method, then the last column would contain text ("true"/"false"), rather than a check box.
		 */
		@Override
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */

		@Override
		public void setValueAt(Object value, int row, int col) {
			Object[] o = data.get(row);
			o[col] = value;
			// Normally, one should call fireTableCellUpdated() when
			// a value is changed. However, doing so in this demo
			// causes a problem with TableSorter. The tableChanged()
			// call on TableSorter that results from calling
			// fireTableCellUpdated() causes the indices to be regenerated
			// when they shouldn't be. Ideally, TableSorter should be
			// given a more intelligent tableChanged() implementation,
			// and then the following line can be uncommented.
			// fireTableCellUpdated(row, col);
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if (arg0.getSource() == getJTxtOudeWaarde()) {
			try {
				String s1 = getJTxtOudeWaarde().getText();
				s1 = s1.replace("\u20ac", "");
				s1 = s1.trim();
				String[] ss = s1.split(",");
				if (ss.length == 2 && "00".equals(ss[1])) {
					s1 = ss[0];
				}
				getJTxtOudeWaarde().setText(s1);
			} catch (Exception e) {

			}
		}
		if (arg0.getSource() == getJTxtNieuweWaarde()) {
			try {
				String s2 = getJTxtNieuweWaarde().getText();
				s2 = s2.replace("\u20ac", "");
				s2 = s2.trim();
				String[] ss = s2.split(",");
				if (ss.length == 2 && "00".equals(ss[1])) {
					s2 = ss[0];
				}
				getJTxtNieuweWaarde().setText(s2);
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if ((arg0.getSource() == getJTxtOudeWaarde()) || (arg0.getSource() == getJTxtNieuweWaarde())
				|| arg0.getSource() == getJTxtOpslag()) {
			double sum = 0;
			double perc = 0;
			try {
				String s1 = getJTxtOudeWaarde().getText();
				String s2 = getJTxtNieuweWaarde().getText();
				s1 = s1.replace("\u20ac", "");
				s1 = s1.trim();
				double d1 = Double.parseDouble(s1.replace(",", "."));
				// Number n1 = df.parse(s1);
				// double d1 = n1.doubleValue();
				s2 = s2.replace("\u20ac", "");
				s2 = s2.trim();
				double d2 = Double.parseDouble(s2.replace(",", "."));
				// Number n2 = df.parse(s2);
				// double d2 = n2.doubleValue();
				sum = d2 - d1;

				String sPerc = getJTxtOpslag().getText();
				sPerc = sPerc.replace("%", "");
				sPerc = sPerc.trim();
				perc = Double.parseDouble(sPerc.replace(",", "."));
				// Number nPerc = df.parse(sPerc);
				// perc = nPerc.doubleValue();

				if (sum != 0 || perc != 0)
					calculateValues(sum, perc);

				getJTxtOudeWaarde().setText(df.format(d1) + " \u20ac");
				getJTxtNieuweWaarde().setText(df.format(d2) + " \u20ac");
				getJTxtOpslag().setText(df.format(perc) + "%");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private double getSum() throws ParseException {
		String s1 = getJTxtOudeWaarde().getText();
		String s2 = getJTxtNieuweWaarde().getText();
		s1 = s1.replace("\u20ac", "");
		s1 = s1.trim();
		Number n1 = df.parse(s1);
		double d1 = n1.doubleValue();
		s2 = s2.replace("\u20ac", "");
		s2 = s2.trim();
		Number n2 = df.parse(s2);
		double d2 = n2.doubleValue();
		return d2 - d1;
	}

	private void calculateValuesV2(double sum, double perc) throws ParseException {
		int rowCount = getJTablePrijzen().getModel().getRowCount();
		// double factor = 1 + perc/100;
		PrijsAanpassingenTabelModel model = (PrijsAanpassingenTabelModel) getJTablePrijzen().getModel();
		Number nGemeenteBelasting = df.parse(getGemeentelijkeBelasting());
		double dGemeenteBelasting = nGemeenteBelasting.doubleValue();

		for (int i = 0; i < rowCount; i++) {
			// String s = (String) model.getValueAt(i, 1);
			String s = Double.toString((Double) model.getValueAt(i, 1));
			s = s.replace("\u20ac", "");
			s = s.trim();
			Number n = df.parse(s);

			double d = n.doubleValue();

			d = d / 1.06;
			d -= dGemeenteBelasting;
			double extra = d * perc / 100;
			BigDecimal bdExtra = new BigDecimal(extra * 100).setScale(0, RoundingMode.CEILING);
			d += bdExtra.doubleValue() / 100;
			d += dGemeenteBelasting + sum;
			d *= 106;
			BigDecimal bd = new BigDecimal(d).setScale(0, RoundingMode.CEILING);
			d = bd.doubleValue();
			d /= 100;

			model.setValueAt(df.format(d), i, 2);
		}
		model.fireTableDataChanged();
	}

	private void calculateValues(double sum, double perc) throws ParseException {
		int rowCount = getJTablePrijzen().getModel().getRowCount();
		PrijsAanpassingenTabelModel model = (PrijsAanpassingenTabelModel) getJTablePrijzen().getModel();
		// Number nGemeenteBelasting = df.parse(getGemeentelijkeBelasting());
		double dGemeenteBelasting = Double.parseDouble(getGemeentelijkeBelasting().replace(",", "."));
		// double dGemeenteBelasting = nGemeenteBelasting.doubleValue();

		for (int i = 0; i < rowCount; i++) {
			String s = Double.toString((Double) model.getValueAt(i, 1));
			s = s.replace("\u20ac", "");
			s = s.trim();
			Number n = df.parse(s);

			double d = n.doubleValue();
			d *= (1 + perc / 100);
			d += dGemeenteBelasting + sum;
			d *= 1.06;

			model.setValueAt(df.format(d), i, 2);
		}
		model.fireTableDataChanged();
	}

	public List<Map<String, Double>> getPrijzen() throws ParseException {
		int rowCount = getJTablePrijzen().getModel().getRowCount();
		PrijsAanpassingenTabelModel model = (PrijsAanpassingenTabelModel) getJTablePrijzen().getModel();
		List<Map<String, Double>> prijzen = new ArrayList<Map<String, Double>>();

		String sPerc = getJTxtOpslag().getText();
		sPerc = sPerc.replace("%", "");
		sPerc = sPerc.trim();
		double perc = Double.parseDouble(sPerc.replace(",", "."));

		DecimalFormat df2 = new DecimalFormat("#.####");
		df2.setRoundingMode(RoundingMode.HALF_UP);

		for (int i = 0; i < rowCount; i++) {
			Map<String, Object> map = getGrondprijzen().get(i);
			Double d1 = (Double) map.get(Constant.BASISPRIJS);

			Object o = model.getValueAt(i, 2);
			double d2 = 0;
			if (o instanceof java.lang.Double) {
				d2 = (Double) o;
			} else {
				String s2 = (String) model.getValueAt(i, 2);
				s2 = s2.replace("\u20ac", "");
				s2 = s2.trim();
				Number n2 = df.parse(s2);
				d2 = n2.doubleValue();
			}

			Map<String, Double> map2 = new HashMap<String, Double>();
			map2.put(Constant.BASISPRIJS, d1);
			map2.put(Constant.NIEUWE_GRONDPRIJS, d2);

			d1 *= (1 + perc / 100);
			String basisprijs = df2.format(d1);
			map2.put(Constant.NIEUWE_BASISPRIJS, Double.valueOf(basisprijs));

			prijzen.add(map2);
		}
		return prijzen;
	}

	public void reset() {
		getJTxtOudeWaarde().setText(getGemeentelijkeBelasting());
		getJTxtNieuweWaarde().setText(getGemeentelijkeBelasting());
		getJTxtOpslag().setText("0.00");
	}

} // @jve:decl-index=0:visual-constraint="10,10"
