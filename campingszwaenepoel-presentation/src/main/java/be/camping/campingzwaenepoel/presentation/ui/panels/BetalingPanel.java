package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.jdatepicker.DatePickerCellEditor;
import be.camping.campingzwaenepoel.presentation.pdf.BetalingenPdf;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.FactuurDetailService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class BetalingPanel extends JPanel implements PanelInterface, ActionListener {

	@Autowired
	private StandplaatsService standplaatsService;

	@Autowired
	private FactuurDetailService factuurDetailService;

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(BetalingPanel.class);
	private boolean dataChanged = false;

	private Controller controller;

	private JPanel jPanelSave;
	private JButton btnSave;
	private JButton btnPrint;
	private JPanel jPanelTeBetalen;
	private JTextField jTxtTeBetalen;
	private JTextField jTxtPrijs;

	private JScrollPane jScrollPaneOpvulling;
	private JTable jTableOpvulling;

	private DecimalFormat df = new DecimalFormat("#,##0.00");

	public StandplaatsDTO getStandplaatsDTO() {
		return getController().getStandplaatsDTO();
	}

	public Set<FactuurDetailDTO> getFactuurDetails() {
		if (getStandplaatsDTO().getFactuurDetails().size() == 0) {
			StandplaatsDTO standplaatsMetFactuurdetails = standplaatsService
					.getStandplaatsWithFacturenAndFactuurDetails(getStandplaatsDTO().getId());
			getStandplaatsDTO().setFactuurDetails(standplaatsMetFactuurdetails.getFactuurDetails());
		}
		return getStandplaatsDTO().getFactuurDetails();
	}

	Font font = new Font("Times New Roman", Font.BOLD, 21);
	private Dimension buttonDimension = new Dimension(185, 40);

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private JPanel getjPanelSave() {
		if (jPanelSave == null) {
			jPanelSave = new JPanel();
			jPanelSave.setLayout(new GridLayout(2, 1));
			// jPanelSave.setSize(new Dimension(200, 100));
			jPanelSave.add(getBtnSave());
			jPanelSave.add(getBtnPrint());
		}
		return jPanelSave;
	}

	/**
	 * This method initializes btnWis
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setPreferredSize(buttonDimension);
			btnSave.setFont(font);
			btnSave.setText("OPSLAAN");

			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new OpslaanActionListener("Opslaan");
			InputMap inputMap = btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(stroke, actionKey);
			ActionMap actionMap = btnSave.getActionMap();
			actionMap.put(actionKey, action);
			btnSave.addActionListener(this);
		}
		return btnSave;
	}

	public JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setPreferredSize(buttonDimension);
			btnPrint.setFont(font);
			btnPrint.setText("AFDRUKKEN");

			String actionKey = "afdrukken";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl P");
			Action action = new PrintActionListener("afdrukken");
			InputMap inputMap = btnPrint.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(stroke, actionKey);
			ActionMap actionMap = btnPrint.getActionMap();
			actionMap.put(actionKey, action);
			btnPrint.addActionListener(this);
		}
		return btnPrint;
	}

	class OpslaanActionListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		OpslaanActionListener(String s) {
			super(s);
		}

		public void actionPerformed(ActionEvent e) {
			getBtnSave().doClick();
		}
	}

	class PrintActionListener extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2401360747568878767L;

		PrintActionListener(String s) {
			super(s);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getBtnPrint().doClick();
		}
	}

	public JTextField getjTxtTeBelaten() {
		if (jTxtTeBetalen == null) {
			jTxtTeBetalen = new JTextField();
			jTxtTeBetalen.setMinimumSize(new Dimension(150, 100));
			jTxtTeBetalen.setPreferredSize(new Dimension(200, 100));
			jTxtTeBetalen.setForeground(Color.RED);
			jTxtTeBetalen.setFont(new Font("Times New Roman", Font.BOLD, 36));
			jTxtTeBetalen.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jTxtTeBetalen;
	}

	public JPanel getjPanelTeBetalen() {
		if (jPanelTeBetalen == null) {
			jPanelTeBetalen = new JPanel();
			jPanelTeBetalen.setLayout(new BorderLayout());

			JPanel jPanelGrondPrijs = new JPanel();
			jPanelGrondPrijs.setLayout(new BorderLayout());

			JLabel jLblPrijs = new JLabel("     GRONDPRIJS : ");
			// jLblPrijs.setPreferredSize(new Dimension(220, 100));
			jLblPrijs.setFont(font);

			jPanelGrondPrijs.add(jLblPrijs, BorderLayout.WEST);
			jPanelGrondPrijs.add(getjTxtPrijs(), BorderLayout.EAST);

			JPanel jPanelNogTeBetalen = new JPanel();
			jPanelNogTeBetalen.setLayout(new BorderLayout());

			JLabel jLblTeBetalen = new JLabel("     NOG TE BETALEN : ");
			jLblTeBetalen.setFont(font);
			jLblTeBetalen.setForeground(Color.RED);
			// jLblTeBetalen.setPreferredSize(new Dimension(250, 100));

			jPanelNogTeBetalen.add(jLblTeBetalen, BorderLayout.WEST);
			jPanelNogTeBetalen.add(getjTxtTeBelaten(), BorderLayout.EAST);

			JPanel jPanelTest = new JPanel();
			jPanelTest.add(jLblPrijs);
			jPanelTest.add(getjTxtPrijs());
			jPanelTest.add(jLblTeBetalen);
			jPanelTest.add(getjTxtTeBelaten());

			// jPanelTeBetalen.add(jPanelGrondPrijs, BorderLayout.WEST);
			// jPanelTeBetalen.add(jPanelNogTeBetalen, BorderLayout.CENTER);
			jPanelTeBetalen.add(jPanelTest, BorderLayout.CENTER);
			jPanelTeBetalen.add(getjPanelSave(), BorderLayout.EAST);
		}
		return jPanelTeBetalen;
	}

	public boolean isDataChanged() {
		return dataChanged;
	}

	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}

	public boolean checkData() {
		boolean ret = false;
		List<FactuurDetailDTO> factuurDetails = sortFactuurdetails(getFactuurDetails());
		List<FactuurDetailDTO> betalingen = new ArrayList<FactuurDetailDTO>();
		double totaal = 0;
		for (FactuurDetailDTO factuurDetail : factuurDetails) {
			Calendar now = Calendar.getInstance();
			if (factuurDetail.getAardBetaling().equals(BetalingEnum.UITSTEL)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(factuurDetail.getDatum());
				if (cal.after(now)) {
					double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
							+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
					totaal -= tmpTotaal;
				}
			} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.BETAALD)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
						+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				totaal -= tmpTotaal;
				betalingen.add(factuurDetail);
			} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.NOGTEBETALEN)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
						+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				totaal += tmpTotaal;
			}
		}
		if (totaal < 0.1)
			ret = true;
		return ret;
	}

	public boolean checkData2() {
		boolean ret = false;
		boolean uitstel = true;
		boolean uitstelAanwezig = false;
		BetalingTableModel model = (BetalingTableModel) getJTableBetaling().getModel();
		List<FactuurDetailDTO> factuurDetails = sortFactuurdetails(model.data);
		double totaal = 0;
		for (FactuurDetailDTO factuurDetail : factuurDetails) {
			Calendar now = Calendar.getInstance();
			if (factuurDetail.getAardBetaling().equals(BetalingEnum.UITSTEL)) {
				uitstelAanwezig = true;
				Calendar cal = Calendar.getInstance();
				cal.setTime(factuurDetail.getDatum());
				if (now.after(cal)) {
					uitstel = false;
					for (FactuurDetailDTO tmpFactuurDetail : factuurDetails) {
						if (BetalingEnum.BETAALD.equals(tmpFactuurDetail.getAardBetaling())) {
							Calendar tmpCal = Calendar.getInstance();
							tmpCal.setTime(tmpFactuurDetail.getDatum());
							if (tmpCal.equals(cal)) {
								uitstel = true;
								break;
							}
						}
					}
				}
			} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.BETAALD)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
						+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				totaal -= tmpTotaal;
			} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.NOGTEBETALEN)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
						+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				totaal += tmpTotaal;
			}
		}
		if ((totaal < 0.01 && totaal > -0.01) || (uitstel && uitstelAanwezig)) {
			ret = true;
		}
		return ret;
	}

	public boolean checkDataChanged() {
		BetalingTableModel model = (BetalingTableModel) getJTableBetaling().getModel();
		return model.isTableChanged();
	}

	public boolean checkDataForParent() {
		return checkData();
	}

	public Object getDataFromGUI() {
		StandplaatsDTO standplaats = getStandplaatsDTO();
		Set<FactuurDetailDTO> factuurdetails = new HashSet<FactuurDetailDTO>();
		BetalingTableModel model = (BetalingTableModel) getJTableBetaling().getModel();
		for (FactuurDetailDTO factuurDetail : model.data) {
			if (factuurDetail.getDatum() != null && factuurDetail.getDatum().toString() != ""
					&& (factuurDetail.getAardBetaling() != null)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
						+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				if (tmpTotaal != 0 || (factuurDetail.getAardBetaling().equals(BetalingEnum.UITSTEL))) {
					factuurdetails.add(factuurDetail);
				}
			}
		}

		standplaats.setFactuurDetails(factuurdetails);
		if (factuurdetails.size() > 0)
			standplaats.setTotaal(getTotaal(getJTableBetaling()));

		return standplaats;
	}

	public void save() {
		StandplaatsDTO standplaats = (StandplaatsDTO) getDataFromGUI();
		BetalingTableModel model = (BetalingTableModel) getJTableBetaling().getModel();
		if (!getFactuurDetails().isEmpty()) {
			standplaats = standplaatsService.storeStandplaats(standplaats);
			getController().setStandplaatsDTO(standplaats);
			getController().updateDataForPanel(checkData(), 6);
			model.rearrangeData(standplaats.getFactuurDetails());
		} else if (standplaats.getFactuurDetails().isEmpty() && standplaats.getTotaal() > 0.1) {
			standplaats.setTotaal(0);
			standplaats = standplaatsService.storeStandplaats(standplaats);
			getController().setStandplaatsDTO(standplaats);
		}
		model.setTableChanged(false);
	}

	public void setDataInGUI() {
		reset();
		this.add(getjScrollpaneBetaling(), BorderLayout.NORTH);
		this.add(getjTableOpvulling(), BorderLayout.CENTER);
		this.add(getjPanelTeBetalen(), BorderLayout.SOUTH);
		double totaal = getTotaal(jTableBetaling);
		getjTxtTeBelaten().setText(df.format(totaal));
		String kostprijs = "";
		try {
			kostprijs = df.format(getStandplaatsDTO().getKostprijs());
		} catch (Exception e) {
			logger.error("error while parsing kostprijs van standplaats " + getStandplaatsDTO().getId());
		}
		getjTxtPrijs().setText(kostprijs);
	}

	private void reset() {
		this.removeAll();
		jScrollPaneBetaling = null;
		jTableBetaling = null;
		getjTxtTeBelaten().setText("");
		setDataChanged(false);
		// factuurDetails = new ArrayList<FactuurDetailDTO>();
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
		this.setBorder(new LineBorder(Color.BLACK, 2));
		this.setLayout(new BorderLayout());
	}

	private JScrollPane jScrollPaneBetaling = null;

	private JScrollPane getjScrollpaneBetaling() {
		if (jScrollPaneBetaling == null) {
			jScrollPaneBetaling = new JScrollPane(getJTableBetaling());
		}
		return jScrollPaneBetaling;
	}

	private JTable jTableBetaling = null;

	private JTable getJTableBetaling() {
		if (jTableBetaling == null) {
			BetalingTableModel model = new BetalingTableModel();
			jTableBetaling = new JTable(model);
			jTableBetaling.setFont(font);
			jTableBetaling.setRowHeight(30);
			jTableBetaling.setGridColor(Color.GRAY);
			jTableBetaling.setFillsViewportHeight(true);
			jTableBetaling.setAutoCreateRowSorter(true);
			jTableBetaling.getTableHeader().setFont(font);
			// setTotaal(jTableBetaling);
			// jTableBetaling.setCellSelectionEnabled(true);

			jTableBetaling.addKeyListener(new TableKeyAdapter(jTableBetaling));

			setUpBetalingsColumn(jTableBetaling.getColumnModel().getColumn(1));
			setUpDatumColumn(jTableBetaling.getColumnModel().getColumn(0));
			ColorRenderer cr = new ColorRenderer();
			ColorCellEditor ce = new ColorCellEditor();
			for (int row = 0; row < jTableBetaling.getRowCount(); row++) {
				for (int col = 0; col < jTableBetaling.getColumnCount(); col++) {
					jTableBetaling.getColumn(jTableBetaling.getColumnName(col)).setCellRenderer(cr);
					if (col != 0 && col != 1) {
						jTableBetaling.getColumn(jTableBetaling.getColumnName(col)).setCellEditor(ce);
					}
				}
			}

			jTableBetaling.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getComponent().isEnabled() && e.getClickCount() == 2) {
						Point p = e.getPoint();
						int row = jTableBetaling.rowAtPoint(p);
						int col = jTableBetaling.columnAtPoint(p);
						if (col == 7) {
							Object o8 = jTableBetaling.getModel().getValueAt(row, 8);
							if (o8 != null && o8 != null) {
								handleDoubleClickCell(jTableBetaling, row);
							}
						}
					} else {
						handleCellEvent(jTableBetaling);
					}
				}
			});

			initColumnSizes(jTableBetaling);
		}
		return jTableBetaling;
	}

	public class TableKeyAdapter implements KeyListener {

		JTable jTable;

		public TableKeyAdapter(JTable jTable) {
			this.jTable = jTable;
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Robot robot;
					try {
						robot = new Robot();
						robot.keyPress(KeyEvent.VK_TAB);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			/*
			 * if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP ||
			 * e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT ||
			 * e.getKeyCode() == KeyEvent.VK_TAB) {
			 * 
			 * handleCellEvent(jTable); }
			 */
		}
	}

	private double getDoubleFromTable(Object o) {
		double d = 0; // #,##0.00
		if (o != null && o != "") {
			try {
				if (o instanceof java.lang.Double) {
					d = (Double) o;
				} else if ((o instanceof java.lang.String) && (!StringUtils.isEmpty((String) o))) {
					String s = (String) o;
					s = s.replace(",", "");
					d = Double.parseDouble(s);
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return d;
	}

	private double berekenTotaal(String s, double vorigTotaal, double factuur, double uitstelkosten, double rappel,
			double andere, int row) {
		double totaal = 0;
		if (!s.equals("UIT")) {
			double tmpTotaal = factuur + uitstelkosten + rappel + andere;
			if (s.equals("BT")) {
				tmpTotaal *= -1;
			}
			totaal = vorigTotaal + tmpTotaal;
			if (totaal < 0.1 && totaal > -1)
				totaal = 0;
		} else {
			totaal = vorigTotaal;
		}
		return totaal;
	}

	private StandplaatsDTO handleDoubleClickCell(JTable jTable, int row) {
		StandplaatsDTO standplaats = null;
		String message = "WENST U DEZE RIJ TE VERWIJDEREN";
		String[] choices = { "JA", "NEE" };
		int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
		if (result == 0) {
			BetalingTableModel model = (BetalingTableModel) jTable.getModel();
			FactuurDetailDTO factuurDetail = model.data.get(row);
			for (FactuurDetailDTO tmpFactuurDetail : getStandplaatsDTO().getFactuurDetails()) {
				if (factuurDetail.getId() == tmpFactuurDetail.getId()) {
					getStandplaatsDTO().getFactuurDetails().remove(tmpFactuurDetail);
					break;
				}
			}

			factuurDetailService.removeFactuurDetail(factuurDetail);
			boolean tablechanged = model.isTableChanged();
			model.data.remove(row);
			model.data.add(DTOFactory.getFactuurDetail());
			model.fireTableDataChanged();
			if (!tablechanged) {
				model.setTableChanged(false);
			}
			getController().updateDataForPanel(checkData(), 6);
		}
		return standplaats;
	}

	private List<FactuurDetailDTO> rearrangeColumnData(List<FactuurDetailDTO> tmpData) {
		List<FactuurDetailDTO> data = new ArrayList<FactuurDetailDTO>();
		int year = 0;
		for (FactuurDetailDTO sortedFactuurDetail : tmpData) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sortedFactuurDetail.getDatum());
			int tmpYear = cal.get(Calendar.YEAR);
			if (year != 0 && year != tmpYear) {
				// int index = data.indexOf(sortedFactuurDetail);
				FactuurDetailDTO factuurDetailDTO = DTOFactory.getFactuurDetail();
				factuurDetailDTO.setColored(true);
				data.add(factuurDetailDTO);
			}
			data.add(sortedFactuurDetail);
			year = tmpYear;
		}
		data.add(DTOFactory.getFactuurDetail());
		data.add(DTOFactory.getFactuurDetail());
		int max = (data.size() > 14) ? data.size() + 2 : 16;
		while (data.size() < max) {
			data.add(DTOFactory.getFactuurDetail());
		}

		return data;
	}

	class BetalingTableModel extends AbstractTableModel implements TableModelListener {

		private static final long serialVersionUID = 1L;
		String[] columnNames = getColumnNames();
		private List<FactuurDetailDTO> data = null;
		private boolean tableChanged = false;

		// public final Object[] longValues
		// = {"01-01-2011", "", new Float(1111.11), "UITSTELKOSTEN", "RAPPEL",
		// "ANDERE", "Een opmerking", new Float(1111.11)};

		public BetalingTableModel() {
			data = this.getColumnData();
			this.addTableModelListener(this);
		}

		public List<FactuurDetailDTO> getData() {
			return data;
		}

		public void setData(List<FactuurDetailDTO> data) {
			this.data = data;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			FactuurDetailDTO factuurDetail = data.get(row);
			Object o = "";
			switch (col) {
			case 0:
				if (factuurDetail.getDatum() != null) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String formattedDate = formatter.format(factuurDetail.getDatum());
					o = formattedDate;
				} else {
					o = "";
				}
				break;
			case 1:
				o = (factuurDetail.getAardBetaling() != null) ? factuurDetail.getAardBetaling().getTranslationKey()
						: "";
				break;
			case 2:
				o = (factuurDetail.getBedrag() == 0) ? "" : df.format(factuurDetail.getBedrag());
				break;
			case 3:
				o = (factuurDetail.getUitstelkosten() == 0) ? "" : df.format(factuurDetail.getUitstelkosten());
				break;
			case 4:
				o = (factuurDetail.getRappel() == 0) ? "" : df.format(factuurDetail.getRappel());
				break;
			case 5:
				o = (factuurDetail.getAndereKosten() == 0) ? "" : df.format(factuurDetail.getAndereKosten());
				break;
			case 6:
				o = (StringUtils.isEmpty(factuurDetail.getOpmerking())) ? "" : factuurDetail.getOpmerking();
				break;
			case 7:
				boolean b = false;
				if (factuurDetail.getDatum() != null && factuurDetail.getDatum().toString() != ""
						&& factuurDetail.getAardBetaling() != null) {

					double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getRappel()
							+ factuurDetail.getUitstelkosten() + factuurDetail.getAndereKosten();
					if (tmpTotaal > 0) {
						b = true;
					}
					if (b) {
						double totaal = berekenTotaalRij(data, row);
						o = df.format(totaal);
					} else {
						o = "";
					}
				}
				break;
			default:
				o = "";
				break;
			}
			return o;
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for each
		 * cell. If we didn't implement this method, then the last column would contain
		 * text ("true"/"false"), rather than a check box.
		 */
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col == 7) {
				return false;
			} else {
				return true;
			}
		}

		public boolean isTableChanged() {
			return tableChanged;
		}

		public void setTableChanged(boolean tableChanged) {
			this.tableChanged = tableChanged;
		}

		public boolean isRowBetweenDates(int row) {
			FactuurDetailDTO factuurDetailDTO = data.get(row);
			return factuurDetailDTO.isColored();
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
			FactuurDetailDTO factuurDetail = data.get(row);
			Object o = getValueAt(row, col);
			boolean b = isTableChanged();
			switch (col) {
			case 0:
				if (value != null) {
					factuurDetail.setDatum((Date) value);
				}
				break;
			case 1:
				if (value.equals(BetalingEnum.BETAALD.getTranslationKey())) {
					factuurDetail.setAardBetaling(BetalingEnum.BETAALD);
				} else if (value.equals(BetalingEnum.NOGTEBETALEN.getTranslationKey())) {
					factuurDetail.setAardBetaling(BetalingEnum.NOGTEBETALEN);
				} else if (value.equals(BetalingEnum.UITSTEL.getTranslationKey())) {
					factuurDetail.setAardBetaling(BetalingEnum.UITSTEL);
				} else if (value == null || value == "") {
					factuurDetail.setAardBetaling(null);
				}
				break;
			case 2:
				factuurDetail.setBedrag(getDoubleFromTable(value));
				break;
			case 3:
				factuurDetail.setUitstelkosten(getDoubleFromTable(value));
				break;
			case 4:
				factuurDetail.setRappel(getDoubleFromTable(value));
				break;
			case 5:
				factuurDetail.setAndereKosten(getDoubleFromTable(value));
				break;
			case 6:
				factuurDetail.setOpmerking((String) value);
				break;
			default:
				break;
			}
			fireTableCellUpdated(row, col);
			if (getValueAt(row, col) == null) {
				setValueAt("", row, col);
			}
			if (!b && o.equals(value)) {
				setTableChanged(false);
			}
		}

		@Override
		public void tableChanged(TableModelEvent arg0) {
			handleCellEvent();
			tableChanged = true;
		}

		public boolean recalculateSizeData(int row) {
			boolean ret = false;
			if (row == data.size() - 3) {
				data.add(DTOFactory.getFactuurDetail());
				fireTableDataChanged();
				ret = true;
			}
			return ret;
		}

		private List<FactuurDetailDTO> getColumnData() {
			List<FactuurDetailDTO> tmpData = sortFactuurdetails(getFactuurDetails());
			return rearrangeColumnData(tmpData);
		}

		public void rearrangeData(Set<FactuurDetailDTO> factuurDetails) {
			data = sortFactuurdetails(factuurDetails);
			data = rearrangeColumnData(data);
			fireTableDataChanged();
		}
	}

	class OpvullingTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		String[] columnNames = getColumnNames();
		private List<Object[]> data = null;

		// public final Object[] longValues
		// = {"01-01-2011", "", new Float(1111.11), "UITSTELKOSTEN", "RAPPEL",
		// "ANDERE", "Een opmerking", new Float(1111.11)};

		public OpvullingTableModel() {
			data = this.getColumnData();
		}

		public List<Object[]> getData() {
			return data;
		}

		public void setData(List<Object[]> data) {
			this.data = data;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		private List<Object[]> getColumnData() {
			List<Object[]> data = new ArrayList<Object[]>();
			Object[] o1 = new Object[] { "", "", "", "", "", "", "", "" };
			Object[] o2 = new Object[] { "", "", "", "", "", "", "", "" };
			Object[] o3 = new Object[] { "", "", "", "", "", "", "", "" };
			data.add(o1);
			data.add(o2);
			data.add(o3);
			return data;
		}

		public Object getValueAt(int row, int col) {
			Object[] o = data.get(row);
			return o[col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for each
		 * cell. If we didn't implement this method, then the last column would contain
		 * text ("true"/"false"), rather than a check box.
		 */
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
			Object[] o = data.get(row);
			o[col] = value;
		}
	}

	/*
	 * This method picks good column sizes. If all column heads are wider than the
	 * column's cells' contents, then you can just use column.sizeWidthToFit().
	 */
	private void initColumnSizes(JTable table) {
		TableColumn colOpm = jTableBetaling.getColumnModel().getColumn(6);
		colOpm.setMinWidth(250);
	}

	private void setUpBetalingsColumn(TableColumn betalingsColumn) {
		JComboBox comboBox = new JComboBox();

		comboBox.addItem("");
		comboBox.addItem(BetalingEnum.BETAALD.getTranslationKey());
		comboBox.addItem(BetalingEnum.NOGTEBETALEN.getTranslationKey());
		comboBox.addItem(BetalingEnum.UITSTEL.getTranslationKey());
		betalingsColumn.setCellEditor(new DefaultCellEditor(comboBox));

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Klik om soort betaling te selecteren");
		betalingsColumn.setCellRenderer(renderer);
	}

	private void setUpDatumColumn(TableColumn datumColumn) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		datumColumn.setCellEditor(new DatePickerCellEditor(formatter));

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Klik om datum te selecteren");
		datumColumn.setCellRenderer(renderer);
	}

	private double getTotaal(JTable jTable) {
		double totaal = 0;
		BetalingTableModel model = (BetalingTableModel) jTable.getModel();
		List<FactuurDetailDTO> factuurDetails = sortFactuurdetails(model.data);
		int row = 0;
		for (FactuurDetailDTO factuurDetail : factuurDetails) {
			totaal = berekenTotaal(factuurDetail.getAardBetaling().getTranslationKey(), totaal,
					factuurDetail.getBedrag(), factuurDetail.getUitstelkosten(), factuurDetail.getRappel(),
					factuurDetail.getAndereKosten(), row);
			row++;
		}
		if (totaal < 0 && totaal > -1)
			totaal = 0;
		return totaal;
	}

	private String[] getColumnNames() {
		String[] columnNames = { "Datum", "NTB/TB", "Factuur", "Uitstelkost", "Rappel", "Andere", "Opmerking",
				"Totaal" };
		return columnNames;
	}

	public JScrollPane getjScrollPaneOpvulling() {
		if (jScrollPaneOpvulling == null) {
			jScrollPaneOpvulling = new JScrollPane();
			jScrollPaneOpvulling.setViewportView(getjTableOpvulling());
		}
		return jScrollPaneOpvulling;
	}

	public JTable getjTableOpvulling() {
		if (jTableOpvulling == null) {
			jTableOpvulling = new JTable(new OpvullingTableModel());
			jTableOpvulling.setTableHeader(null);
			jTableOpvulling.setRowHeight(30);
			jTableOpvulling.setGridColor(Color.GRAY);
			jTableOpvulling.setFillsViewportHeight(true);
			jTableOpvulling.setAutoCreateRowSorter(true);
			jTableOpvulling.getColumnModel().getColumn(0)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(0).getWidth());
			jTableOpvulling.getColumnModel().getColumn(1)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(1).getWidth());
			jTableOpvulling.getColumnModel().getColumn(2)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(2).getWidth());
			jTableOpvulling.getColumnModel().getColumn(3)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(3).getWidth());
			jTableOpvulling.getColumnModel().getColumn(4)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(4).getWidth());
			jTableOpvulling.getColumnModel().getColumn(5)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(5).getWidth());
			jTableOpvulling.getColumnModel().getColumn(7)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(7).getWidth() + 16);
			jTableOpvulling.getColumnModel().getColumn(6)
					.setPreferredWidth(getJTableBetaling().getColumnModel().getColumn(6).getWidth());
			// jTableOpvulling.setPreferredSize(new Dimension(600, 90));
			// jTableOpvulling.setMaximumSize(maximumSize)
			// initColumnSizes(jTableOpvulling);
		}
		return jTableOpvulling;
	}

	public JTextField getjTxtPrijs() {
		if (jTxtPrijs == null) {
			jTxtPrijs = new JTextField();
			jTxtPrijs.setPreferredSize(new Dimension(200, 100));
			jTxtPrijs.setMinimumSize(new Dimension(150, 100));
			jTxtPrijs.setFont(new Font("Times New Roman", Font.BOLD, 36));
			jTxtPrijs.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jTxtPrijs;
	}

	private List<FactuurDetailDTO> sortFactuurdetails(Set<FactuurDetailDTO> factuurDetails) {
		List<FactuurDetailDTO> sortedFactuurDetails = new ArrayList<FactuurDetailDTO>();
		for (FactuurDetailDTO tmpFactuurDetail : factuurDetails) {
			if (tmpFactuurDetail.getDatum() == null || tmpFactuurDetail.getDatum().toString() == ""
					|| (tmpFactuurDetail.getAardBetaling() == null)) {
				continue;
			}
			boolean added = false;
			boolean toBeAdded = false;
			for (FactuurDetailDTO sortedFactuurDetail : sortedFactuurDetails) {
				if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
					toBeAdded = true;
				} else if (tmpFactuurDetail.getDatum().equals(sortedFactuurDetail.getDatum())) {
					if (tmpFactuurDetail.getDatumAanmaak() < sortedFactuurDetail.getDatumAanmaak()) {
						toBeAdded = true;
					}
				}
				if (toBeAdded) {
					int index = sortedFactuurDetails.indexOf(sortedFactuurDetail);
					sortedFactuurDetails.add(index, tmpFactuurDetail);
					added = true;
					break;
				}
			}
			if (!added) {
				sortedFactuurDetails.add(tmpFactuurDetail);
			}
		}

		return sortedFactuurDetails;
	}

	private List<FactuurDetailDTO> sortFactuurdetails(List<FactuurDetailDTO> factuurDetails) {
		List<FactuurDetailDTO> sortedFactuurDetails = new ArrayList<FactuurDetailDTO>();
		for (FactuurDetailDTO tmpFactuurDetail : factuurDetails) {
			if (tmpFactuurDetail.getDatum() == null || tmpFactuurDetail.getDatum().toString() == ""
					|| (tmpFactuurDetail.getAardBetaling() == null)) {
				continue;
			}
			boolean added = false;
			boolean toBeAdded = false;
			for (FactuurDetailDTO sortedFactuurDetail : sortedFactuurDetails) {
				if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
					toBeAdded = true;
				} else if (tmpFactuurDetail.getDatum().equals(sortedFactuurDetail.getDatum())) {
					if (tmpFactuurDetail.getDatumAanmaak() < sortedFactuurDetail.getDatumAanmaak()) {
						toBeAdded = true;
					}
				}
				if (toBeAdded) {
					int index = sortedFactuurDetails.indexOf(sortedFactuurDetail);
					sortedFactuurDetails.add(index, tmpFactuurDetail);
					added = true;
					break;
				}
			}
			if (!added) {
				sortedFactuurDetails.add(tmpFactuurDetail);
			}
		}
		return sortedFactuurDetails;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getBtnSave()) {
			save();
		}
		if (e.getSource() == getBtnPrint()) {
			List<StandplaatsDTO> standplaatsen = new ArrayList<StandplaatsDTO>();
			standplaatsen.add(getStandplaatsDTO());
			BetalingenPdf betalingenPdf = new BetalingenPdf();
			betalingenPdf.createDocument(new Date(), standplaatsen, false, true);
		}
	}

	private class ColorRenderer extends JLabel implements TableCellRenderer {

		private static final long serialVersionUID = -202902072067266128L;

		public ColorRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setForeground(Color.BLACK);
			setFont(font);

			BetalingTableModel model = (BetalingTableModel) getJTableBetaling().getModel();
			Color color = Color.BLACK;
			if (value != null) {
				setText(value.toString());
				if (!StringUtils.isEmpty(value.toString())) {
					String s = (String) table.getValueAt(row, 1);
					if (s.equals("BT")) {
						color = Color.GREEN;
					} else if (s.equals("NTB")) {
						color = Color.RED;
					}
				}
			}
			setForeground(color);
			setBackground(Color.WHITE);
			if (hasFocus) {
				setBackground(Color.LIGHT_GRAY);
			}

			switch (column) {
			case 0:
				setHorizontalAlignment(SwingConstants.CENTER);
				setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
				break;
			case 1:
				setHorizontalAlignment(SwingConstants.CENTER);
				setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
				break;
			case 2:
				setHorizontalAlignment(SwingConstants.RIGHT);
				setBorder(new EmptyBorder(new Insets(0, 0, 0, 5)));
				break;
			case 3:
				setHorizontalAlignment(SwingConstants.RIGHT);
				break;
			case 4:
				setHorizontalAlignment(SwingConstants.RIGHT);
				break;
			case 5:
				setHorizontalAlignment(SwingConstants.RIGHT);
				break;
			case 6:
				setHorizontalAlignment(SwingConstants.LEFT);
				setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));
				break;
			case 7:
				setHorizontalAlignment(SwingConstants.RIGHT);
				setBorder(new EmptyBorder(new Insets(0, 0, 0, 5)));
				break;
			default:
				break;
			}

			if (model.isRowBetweenDates(row)) {
				setBackground(Color.blue);
			}
			return this;
		}
	}

	private class ColorCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = -202902072067266128L;
		JTextField editor;

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			if (editor == null) {
				editor = new JTextField();
			}

			editor.setFont(font);
			Color color = Color.BLACK;

			if (value != null) {
				editor.setText(value.toString());
				if (!StringUtils.isEmpty(value.toString())) {
					String s = (String) table.getValueAt(row, 1);
					if (s.equals("BT")) {
						color = Color.GREEN;
					} else if (s.equals("NTB")) {
						color = Color.RED;
					}
				}
			}

			editor.setForeground(color);
			editor.setBackground(Color.WHITE);

			switch (column) {
			case 2:
				editor.setMargin(new Insets(0, 0, 0, 10));
				editor.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
				break;
			case 6:
				editor.setMargin(new Insets(0, 5, 0, 0));
				break;
			case 7:
				editor.setMargin(new Insets(0, 0, 0, 5));
				break;
			default:
				break;
			}

			return editor;
		}

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return editor.getText();
		}
	}

	private double berekenTotaalRij(List<FactuurDetailDTO> data, int index) {
		double totaal = 0;
		for (FactuurDetailDTO tmpFactuurDetail : data) {
			if (data.indexOf(tmpFactuurDetail) > index)
				break;
			if ((tmpFactuurDetail.getAardBetaling() == null)
					|| (tmpFactuurDetail.getAardBetaling().equals(BetalingEnum.UITSTEL)))
				continue;
			double tmpTotaal = tmpFactuurDetail.getBedrag() + tmpFactuurDetail.getAndereKosten()
					+ tmpFactuurDetail.getRappel() + tmpFactuurDetail.getUitstelkosten();
			if (tmpFactuurDetail.getAardBetaling().equals(BetalingEnum.BETAALD))
				tmpTotaal *= -1;
			totaal += tmpTotaal;
		}
		if (totaal < 0 && totaal > -1)
			totaal = 0;
		// if (index == 0 && totaal < 0) totaal *= -1;
		return totaal;
	}

	private void handleCellEvent() {
		handleCellEvent(getJTableBetaling());
	}

	private void handleCellEvent(JTable jTable) {
		BetalingTableModel model = (BetalingTableModel) jTable.getModel();
		int row = jTable.getSelectedRow();
		int col = jTable.getSelectedColumn();
		if (model.isTableChanged())
			;
		{
			jTable.repaint();
			double totaal = getTotaal(jTable);
			getjTxtTeBelaten().setText(df.format(totaal));
		}
		if (model.recalculateSizeData(row)) {
			boolean b = model.isTableChanged();
			jTable.setRowSelectionInterval(row, row);
			jTable.setColumnSelectionInterval(col, col);
			if (!b)
				model.setTableChanged(false);
		}
	}

}
