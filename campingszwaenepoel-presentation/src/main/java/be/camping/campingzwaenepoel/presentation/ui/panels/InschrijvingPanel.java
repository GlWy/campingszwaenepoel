package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.ui.layout.GridLayout2;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.BadgeService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class InschrijvingPanel extends JPanel implements PanelInterface {

	@Autowired
	private StandplaatsService standplaatsService;

	@Autowired
	private BadgeService badgeService;

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(InschrijvingPanel.class);

	private Controller controller;
	private JTabbedPane jTabbedPane = null;

	private JScrollPane jScrollPaneVast = null;
	private JTable jTableVast = null;
	private JScrollPane jScrollPaneLos = null;
	private JTable jTableLos = null;
	private JScrollPane jScrollPaneChronologisch = null;
	private JTable jTableChronologisch = null;
	
    private Font font = new Font("Times New Roman", Font.BOLD, 18);
    
    private List<Integer> jaartallen = new ArrayList<>();
    private List<Integer> jaartallenLos = new ArrayList<>();
    private List<Integer> jaartallenVast = new ArrayList<>();
    
    private List<InschrijvingDTO> inschrijvingen = null;
    
    private int colNaamWidth = 0;
    private boolean addColorsAndEmptyRows = true;

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
		this.setLayout(new GridBagLayout());
	}

	public boolean checkData() {
		boolean ret = true;
       	List<Date> inschrijvingen 
       		= standplaatsService.getInschrijvingenMetBadge(getController().getStandplaatsDTO().getId());
//       		= getInschrijvingController().getInschrijvingen(getController().getStandplaatsDTO().getId(), SoortHuurderEnum.LOS);
       	Calendar cal = Calendar.getInstance();
       	int jaar = cal.get(Calendar.YEAR);
       	int maand = cal.get(Calendar.MONTH);
       	int dag = cal.get(Calendar.DATE);
       	cal.set(jaar, maand, dag, 0, 0, 0);
       	for (Date date : inschrijvingen) {
       		try {
           		Calendar calTot = Calendar.getInstance();
           		calTot.setTime(date);
//           		calTot.setTime(inschrijving.getDateTot());
           		int jaarTot = calTot.get(Calendar.YEAR);
           		int maandTot = calTot.get(Calendar.MONTH);
           		int dagTot = calTot.get(Calendar.DATE) + 1;
           		calTot.set(jaarTot, maandTot, dagTot, 0, 0, 0);
//       		if (inschrijving.getBadge() != null && cal.getTime().before(calTot.getTime())) {
           		if (cal.getTime().before(calTot.getTime())) {
           			ret = false;
           			break;
           		}
       		} catch (Exception e) {
       			System.err.println("error parsing date");
       			continue;
       		}
       	}
		return ret;
	}

	public boolean checkDataChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkDataForParent() {
		return checkData();
	}

	public Object getDataFromGUI() {
		// TODO Auto-generated method stub
		return null;
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public void setDataInGUI() {
        this.removeAll();
        this.setLayout(new GridBagLayout());
        this.setBorder(new LineBorder(Color.BLACK, 2));
        reset();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridx = 0;
		this.add(getJTabbedPane(), gridBagConstraints);
	}

	public void setRemarksInGui() {
		// TODO Auto-generated method stub
	}
    
    private String[] getColumnNames() {
		return new String[]{"OPM", "BADGE", "TYPE", "Familienaam", "Voornaam", "NRplaat", "VAN", "TOT"};
    }
    
    private Vector<Object[]> getColumnData(SoortHuurderEnum soortHuurder) {
    	if (inschrijvingen == null) {
    		inschrijvingen 
    			= standplaatsService.getInschrijvingen(getController().getStandplaatsDTO().getId(), soortHuurder);
    	}
       	
    	Vector<Object[]> data = new Vector<Object[]>();
    	List<Integer> tmpJaartallen = new ArrayList<Integer>();
    	int jaar = 0;
    	for (InschrijvingDTO inschrijving : inschrijvingen) {
    		if (soortHuurder != null && !soortHuurder.equals(inschrijving.getSoorthuurder())) continue;
    		if (inschrijving.getDateTot() == null) continue;
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(inschrijving.getDateTot());
    		int tmpJaar = cal.get(Calendar.YEAR);
    		if (jaar == 0 || tmpJaar != jaar) {
    			jaar = tmpJaar;
    			tmpJaartallen.add(jaar);
    		}
    		List<InschrijvingPersoonDTO> sortedInschrijvingPersonen = sortInschrijvingPersonen(inschrijving.getInschrijvingPersonen());
			for (InschrijvingPersoonDTO inschrijvingPersoon : sortedInschrijvingPersonen) {
				PersoonDTO persoon = inschrijvingPersoon.getPersoon();
				int numberOfColumns = 10;
				Object[] o = new Object[numberOfColumns];
    			if (!StringUtils.isEmpty(persoon.getOpmerking())) {
    				o[0] = "x";
    			} else {
    				o[0] = "";
    			}
					BadgeDTO badge = null;
					if (inschrijving.getSoorthuurder().equals(SoortHuurderEnum.VAST)) {
						badge = getController().getStandplaatsDTO().getBadge(); 
					} else {
						badge = inschrijving.getBadge();
					}
				if (badge != null) {
					o[1] = badge.getBadgenummer();
				} else {
					o[1] = "";
				}

    			o[2] = inschrijvingPersoon.getHuurdersPositie().getTranslationKey();
    			o[3] = persoon.getNaam();
    			colNaamWidth = (colNaamWidth < persoon.getNaam().length())?persoon.getNaam().length():colNaamWidth;
    			o[4] = persoon.getVoornaam();
    			Set<WagenDTO> wagens = persoon.getWagens();
    			String s = "";
    			for (WagenDTO wagen : wagens) {
    				if (s.length() > 0) {
    					s += " ";
    				}
    				s += wagen.getNummerplaat();
    			}
    			o[5] = s;
    			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        		String formattedDateVan = formatter.format(inschrijving.getDateVan());
        		String formattedDateTot = formatter.format(inschrijving.getDateTot());
    			o[6] = formattedDateVan;
    			o[7] = formattedDateTot;
    			o[8] = inschrijving.getId();
    			o[9] = persoon.getId();
    			data.add(o);
        	}
			if (addColorsAndEmptyRows) {
				Object[] o = {"","","","","","","","","",""};
				data.add(o);
			}
   		}
    	setJaartallenVoorKleur(tmpJaartallen, soortHuurder);
    	return data;
    }
    
    private List<InschrijvingPersoonDTO> sortInschrijvingPersonen(Set<InschrijvingPersoonDTO> inschrijvingPersonen) {
    	List<InschrijvingPersoonDTO> sortedInschrijvingPersonen = new ArrayList<InschrijvingPersoonDTO>();
    	for(InschrijvingPersoonDTO inschrijvingPersoon : inschrijvingPersonen) {
    		boolean added = false;
    		for (InschrijvingPersoonDTO sortedInschrijvingPersoon : sortedInschrijvingPersonen) {
    			Long l1 = inschrijvingPersoon.getInschrijvingDatum();
    			Long l2 = sortedInschrijvingPersoon.getInschrijvingDatum();
    			if (l1 < l2) {
    				int index = sortedInschrijvingPersonen.indexOf(sortedInschrijvingPersoon);
    				sortedInschrijvingPersonen.add(index, inschrijvingPersoon);
    				added = true;
    				break;
    			}
    		}
    		if (!added) {
    			sortedInschrijvingPersonen.add(inschrijvingPersoon);
    		}
    	}
    	return sortedInschrijvingPersonen;
    }
    
    private void setJaartallenVoorKleur(List<Integer> tmpJaartallen, SoortHuurderEnum soortHuurder) {
		for (int tel = 0; tel < tmpJaartallen.size(); tel++) {
    		if (tel%2 != 0) {
    	    	if (soortHuurder == SoortHuurderEnum.VAST) {
    	    		jaartallenVast.add(tmpJaartallen.get(tel));
    	    	}
    	    	if (soortHuurder == SoortHuurderEnum.LOS) {
    	    		jaartallenLos.add(tmpJaartallen.get(tel));
    	    	}
    	    	if (soortHuurder == null) {
    	    		jaartallen.add(tmpJaartallen.get(tel));
    	    	}
    		}
		}
    }
    
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	public JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = createJTabbedpane();
		}
		return jTabbedPane;
	}
	
	private JTabbedPane createJTabbedpane() {
		jTabbedPane = new JTabbedPane();
		setJScrollPaneChronologisch(new JScrollPane(getJTableChronologisch()));
		setJScrollPaneVast(new JScrollPane());
		setJScrollPaneLos(new JScrollPane());
		jTabbedPane.addTab("CHRONOLOGISCH", null, getJScrollPaneChronologisch(), null);
		jTabbedPane.addTab("VAST", null, getJScrollPaneVast(), null);
		jTabbedPane.addTab("LOS", null, getJScrollPaneLos(), null);
		jTabbedPane.addChangeListener(new ChangeListener() { 
        	// This method is called whenever the selected tab changes 
        	public void stateChanged(ChangeEvent evt) { 
        		JTabbedPane pane = (JTabbedPane)evt.getSource();         		
        		addColorsAndEmptyRows = true;
		    	if (pane.getSelectedIndex() == 0) {
		    		resetJTableChronologisch();
		    		getJScrollPaneChronologisch().setViewportView(getJTableChronologisch());
		    	} else if (pane.getSelectedIndex() == 1) {
		    		resetJTableVast();
		    		getJScrollPaneVast().setViewportView(getJTableVast());
		    	} else if (pane.getSelectedIndex() == 2) {
		    		resetJTableLos();
		    		getJScrollPaneLos().setViewportView(getJTableLos());
		    	}
        	}
        });
/*		jTabbedPane.addMouseListener(new MouseAdapter()
		{
		    public void mouseClicked(MouseEvent e)
		    {
		    	addColorsAndEmptyRows = true;
		    	if (jTabbedPane.getSelectedIndex() == 0) {
		    		resetJTableChronologisch();
		    		getJScrollPaneChronologisch().setViewportView(getJTableChronologisch());
		    	} else if (jTabbedPane.getSelectedIndex() == 1) {
//		    		setJScrollPaneVast(new JScrollPane(getJTableVast()));
		    		resetJTableVast();
		    		getJScrollPaneVast().setViewportView(getJTableVast());
		    	} else if (jTabbedPane.getSelectedIndex() == 2) {
//		    		setJScrollPaneLos(new JScrollPane(getJTableLos()));
		    		resetJTableLos();
		    		getJScrollPaneLos().setViewportView(getJTableLos());
		    	}
		    }
		}
		);*/
		return jTabbedPane;
	}
	
	/**
	 * This method initializes VASE	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneVast() {
		if (jScrollPaneVast == null) {
			jScrollPaneVast = new JScrollPane();
		}
		return jScrollPaneVast;
	}
	
	private void setJScrollPaneVast(JScrollPane jScrollPane) {
		this.jScrollPaneVast = jScrollPane;
	}
	
	private void setJScrollPaneLos(JScrollPane jScrollPane) {
		this.jScrollPaneLos = jScrollPane;
	}
	
	private void setJScrollPaneChronologisch(JScrollPane jScrollPane) {
		this.jScrollPaneChronologisch = jScrollPane;
	}
	
	private JTable getJTableVast() {
		if (jTableVast == null) {
			resetJTableVast();
		}
	    return jTableVast;
	}

	private JTable getJTableLos() {
		if (jTableLos == null) {
			resetJTableLos();
		}
	    return jTableLos;
	}

	private JTable getJTableChronologisch() {
		if (jTableChronologisch == null) {
			resetJTableChronologisch();
		}
	    return jTableChronologisch;
	}

	private void resetJTableVast() {
        //Create a table with a sorter.		
        InschijvingTableModel model = new InschijvingTableModel(SoortHuurderEnum.VAST);
        jTableVast = new JTable(model);
	    jTableVast.setFillsViewportHeight(true);
	    jTableVast.setAutoCreateRowSorter(true);
	    jTableVast.setRowHeight(30);
	    jTableVast.getTableHeader().setFont(font);
	    jTableVast.setGridColor(Color.GRAY);
	    jTableVast.getTableHeader().addMouseListener(new ColumnHeaderListener(model));
	    ColorRenderer cr = new ColorRenderer(SoortHuurderEnum.VAST);
	    for (int row = 0; row < jTableVast.getRowCount(); row++) {
	    	for (int col = 0; col < jTableVast.getColumnCount(); col++) {
	    		jTableVast.getColumn(jTableVast.getColumnName(col)).setCellRenderer(cr);
	    	}
	    }
	    initColumnSizes(jTableVast);
		jTableVast.addMouseListener(new MouseAdapter()
		{
		    public void mouseClicked(MouseEvent e)
		    {
		        if (e.getClickCount() == 2)
		        {
					try {
			            Point p = e.getPoint();
			            int row = jTableVast.rowAtPoint(p);
			            int column = jTableVast.columnAtPoint(p);
			            logger.info("double click met row = " + row + " en column = " + column);
						Object o8 = jTableVast.getModel().getValueAt(row, 8);
						String badgenummer = (String)jTableVast.getModel().getValueAt(row, 1);
						if (processEventDoubleClick(o8, badgenummer, SoortHuurderEnum.VAST, column)) {
				            handleDoubleClickCell(row, column, SoortHuurderEnum.VAST, jTableVast);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(e);
					}
		        }
		    }
		}
		);
	}
	
	private boolean processEventDoubleClick(Object oInschrijvingId, String badgenummer, Object soorthuurder, int col) {
		boolean ret = false;
		if (oInschrijvingId != null && oInschrijvingId != "") { // als er geen inschrijvingId is, werd er op een lege rij geklikt
			int inschrijvingId = (Integer)oInschrijvingId;
			if (col == 1) {
				if (!StringUtils.isEmpty(badgenummer)) {
					if ((soorthuurder == null) && (checkSoortHuurderIsLos(inschrijvingId))) {
						ret = true;
					} else if ((soorthuurder != null) && (soorthuurder.equals(SoortHuurderEnum.LOS))) {
						ret = true;
					}
				}
			} else {
				ret = true;
			}
		}
		return ret;
	}
	
	private void handleDoubleClickCell(int row, int col, SoortHuurderEnum soortHuurder, JTable jTable) {
		if (col == 0) {
			String message = "WENST U DEZE INSCHRIJVING TE VERWIJDEREN? DEZE HANDELING IS NIET OMKEERBAAR!";
			String[] choices = {"JA", "NEE"};
			int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if (result == 0) {
				int inschrijvingId = (Integer)jTable.getModel().getValueAt(row, 8);
				standplaatsService.removeInschrijving(inschrijvingId);
				for (InschrijvingDTO inschrijving : inschrijvingen) {
					if (inschrijving.getId() == inschrijvingId) {
						inschrijvingen.remove(inschrijving);
						break;
					}
				}
				InschijvingTableModel model = (InschijvingTableModel)jTable.getModel();
				Vector<Object[]> inschrijvingenVerwijder = new Vector<Object[]>();
				for (Object[] o : model.getData()) {
					if (o[8] instanceof Integer) {
						int id = (Integer)(o[8]);
						if (id == inschrijvingId) {
							inschrijvingenVerwijder.add(o);
						}
					}
				}
				for (Object[] o : inschrijvingenVerwijder) {
					model.getData().remove(o);
				}
				if (getJTabbedPane().getSelectedIndex() == 0) {
					resetJTableChronologisch();
		    		getJScrollPaneChronologisch().setViewportView(getJTableChronologisch());
				} else if (getJTabbedPane().getSelectedIndex() == 1) {
					resetJTableVast();
		    		getJScrollPaneChronologisch().setViewportView(getJTableVast());
				} else if (getJTabbedPane().getSelectedIndex() == 2) {
					resetJTableLos();
		    		getJScrollPaneChronologisch().setViewportView(getJTableLos());
				}
/*				int rowCount = 0;
				for (Object[] o : model.getData()) {
					if (o[8] instanceof String) {
						String s = (String)o[8];
						s = s.trim();
						if ("".equals(s)) {
							if (rowCount == 0) {
								rowCount++;
							} else if (rowCount == 1) {
								model.getData().remove(o);
								break;
							}
						} else {
							rowCount = 0;
						}
					} else {
						rowCount = 0;
					}
				}*/
				model.fireTableDataChanged();
			}
		} else if (col == 1) {
			int inschrijvingId = (Integer)jTable.getModel().getValueAt(row, 8);
			if (soortHuurder == SoortHuurderEnum.LOS || checkSoortHuurderIsLos(inschrijvingId)) {
				String message = "WENST U DEZE BADGE TE VERWIJDEREN?";
				String[] choices = {"JA", "NEE"};
				int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
				if (result == 0) {
					String badgenummer = (String)jTable.getModel().getValueAt(row, 1);
					if (!StringUtils.isEmpty(badgenummer)) {
						Optional<BadgeDTO> badge = badgeService.findBadge(badgenummer);
						InschrijvingDTO inschrijving = standplaatsService.getInschrijvingVoorBadge(badge.get());
						standplaatsService.removeBadgeVanInschrijving(inschrijving);
						getController().updateDataForPanel(checkData(), 4);
					}
		            for (InschrijvingDTO inschrijving : inschrijvingen) {
		            	if (inschrijving.getBadge() != null && inschrijving.getBadge().getBadgenummer().equals(badgenummer)) {
		            		inschrijving.setBadge(null);
		            	}
		            }
		            jTable.setValueAt("", row, col);
				}
			}
		} else if (col == 3 || col == 4) {
			try {
				int persoonId = (Integer)jTable.getModel().getValueAt(row, 9);
				getController().setPersoonInPersoonPanel(persoonId);
			} catch (ClassCastException cce) {
				
			}
		} else if (col == 5) {
			int inschrijvingId = (Integer)jTable.getModel().getValueAt(row, 8);
			InschrijvingDTO inschrijving = standplaatsService.getInschrijving(inschrijvingId);
			List<String> nummerplaten = new ArrayList<String>();
			for (InschrijvingPersoonDTO inschrijvingPersoon : sortInschrijvingPersonen(inschrijving.getInschrijvingPersonen())) {
				for (WagenDTO wagen : inschrijvingPersoon.getPersoon().getWagens()) {
					if (!nummerplaten.contains(wagen.getNummerplaat())) {
						nummerplaten.add(wagen.getNummerplaat());
					}
				}
			}
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridLayout2(nummerplaten.size(), 1));
			Font tmpFont = new Font("Times New Roman", Font.BOLD, 100);
			for (String nummerplaat : nummerplaten) {
				JLabel jLblNummerplaat = new JLabel(nummerplaat);
				jLblNummerplaat.setFont(tmpFont);
				jPanel.add(jLblNummerplaat);
			}
			String[] choices = {"OK"};
			JOptionPane.showOptionDialog(this, new Object[] {jPanel}, "Nummerplaten", JOptionPane.OK_OPTION, 
							JOptionPane.INFORMATION_MESSAGE, null, choices, choices[0]);
		} else if (col == 6 || col == 7) {
			Object oInschrijving = jTable.getModel().getValueAt(row, 8);
			if (oInschrijving != null && oInschrijving != "") {
				int inschrijvingId = (Integer)jTable.getModel().getValueAt(row, 8);
				getController().vernieuwInschrijving(inschrijvingId);
			}
		}
	}
	
	private boolean checkSoortHuurderIsLos(int inschrijvingId) {
		boolean ret = true;
		for (InschrijvingDTO inschrijving : inschrijvingen) {
			if (inschrijving.getId() == inschrijvingId) {
				if (!inschrijving.getSoorthuurder().equals(SoortHuurderEnum.LOS)) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	private void resetJTableChronologisch() {
		//Create a table with a sorter.
        InschijvingTableModel model = new InschijvingTableModel(null);
	    jTableChronologisch = new JTable(model);
	    jTableChronologisch.setFillsViewportHeight(true);
	    jTableChronologisch.setAutoCreateRowSorter(true);
	    jTableChronologisch.setRowHeight(30);
	    jTableChronologisch.setForeground(Color.BLACK);
	    jTableChronologisch.setFont(font);
	    jTableChronologisch.getTableHeader().setFont(font);
	    jTableChronologisch.setGridColor(Color.GRAY);
	    jTableChronologisch.getTableHeader().addMouseListener(new ColumnHeaderListener(model));
	    ColorRenderer cr = new ColorRenderer(null);
	    for (int row = 0; row < jTableChronologisch.getRowCount(); row++) {
	    	for (int col = 0; col < jTableChronologisch.getColumnCount(); col++) {
	    		jTableChronologisch.getColumn(jTableChronologisch.getColumnName(col)).setCellRenderer(cr);
	    	}
	    }
	    jTableChronologisch.addMouseListener(new MouseAdapter()	{
		    public void mouseClicked(MouseEvent e)
		    {
		        if (e.getClickCount() == 2)
		        {
					try {
			            Point p = e.getPoint();
			            int row = jTableChronologisch.rowAtPoint(p); 
			            int column = jTableChronologisch.columnAtPoint(p);
			            logger.info("double click met row = " + row + " en column = " + column);
						Object o8 = jTableChronologisch.getModel().getValueAt(row, 8);
						String badgenummer = (String)jTableChronologisch.getModel().getValueAt(row, 1);
						if (processEventDoubleClick(o8, badgenummer, null, column)) {
							handleDoubleClickCell(row, column, null, jTableChronologisch);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(e);
					}
		        }
		    }
		}
		);
	    initColumnSizes(jTableChronologisch);
	}

	private void resetJTableLos() {
        //Create a table with a sorter.
        InschijvingTableModel model = new InschijvingTableModel(SoortHuurderEnum.LOS);
	    jTableLos = new JTable(model);
	    jTableLos.setFillsViewportHeight(true);
	    jTableLos.setAutoCreateRowSorter(true);
	    jTableLos.setRowHeight(30);
	    jTableLos.setForeground(Color.BLACK);
	    jTableLos.setFont(font);
	    jTableLos.getTableHeader().setFont(font);
	    jTableLos.setGridColor(Color.GRAY);
	    jTableLos.getTableHeader().addMouseListener(new ColumnHeaderListener(model));
	    ColorRenderer cr = new ColorRenderer(SoortHuurderEnum.LOS);
	    for (int row = 0; row < jTableLos.getRowCount(); row++) {
	    	for (int col = 0; col < jTableLos.getColumnCount(); col++) {
	    		jTableLos.getColumn(jTableLos.getColumnName(col)).setCellRenderer(cr);
	    	}
	    }
	    jTableLos.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
					try {
			            Point p = e.getPoint();
			            int row = jTableLos.rowAtPoint(p); 
			            int column = jTableLos.columnAtPoint(p);
			            logger.info("double click met row = " + row + " en column = " + column);
						Object o8 = jTableLos.getModel().getValueAt(row, 8);
						String badgenummer = (String)jTableLos.getModel().getValueAt(row, 1);
						if (processEventDoubleClick(o8, badgenummer, SoortHuurderEnum.LOS, column)) {
							handleDoubleClickCell(row, column, SoortHuurderEnum.LOS, jTableLos);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(e);
					}
					
					/*		            if (column == 1 && badgenummer.contains("H")) {
			            StandplaatsDTO standplaats = getController().getStandplaatsDTO();
			            for (InschrijvingDTO inschrijving : standplaats.getInschrijvingen()) {
			            	if (inschrijving.getBadge() != null && inschrijving.getBadge().getBadgenummer().equals(badgenummer)) {
			            		inschrijving.setBadge(null);
			            	}
			            }
			            getController().setStandplaatsDTO(standplaats);
			            setDataInGUI();
			            getJTabbedPane().setSelectedIndex(2);
		            }*/
		        }
		    }
		}
		);
	    initColumnSizes(jTableLos);
	}

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table) {
    	
/*		int opmSize = table.getColumnModel().getColumn(0).getWidth();
		int badgeSize = table.getColumnModel().getColumn(1).getWidth();
		int typeSize = table.getColumnModel().getColumn(2).getWidth();
		int naamSize = table.getColumnModel().getColumn(3).getWidth();
		int voornaamSize = table.getColumnModel().getColumn(4).getWidth();
		int nrPlaatSize = table.getColumnModel().getColumn(5).getWidth();
		int vanSize = table.getColumnModel().getColumn(6).getWidth();
		int totSize = table.getColumnModel().getColumn(7).getWidth();*/
		
		TableColumn colOpm = table.getColumnModel().getColumn(0);
		colOpm.setPreferredWidth(50);
		colOpm.setMaxWidth(50);
		colOpm.setMinWidth(50);
		TableColumn colBadge = table.getColumnModel().getColumn(1);
		colBadge.setPreferredWidth(100);
		colBadge.setMaxWidth(100);
		colBadge.setMinWidth(100);
		TableColumn colVan = table.getColumnModel().getColumn(6);
		colVan.setPreferredWidth(100);
		colVan.setMaxWidth(100);
		colVan.setMinWidth(100);
		TableColumn colTot = table.getColumnModel().getColumn(7);
		colTot.setPreferredWidth(100);
		colTot.setMaxWidth(100);
		colTot.setMinWidth(100);
//		TableColumn colType = table.getColumnModel().getColumn(2);
//		colType.setPreferredWidth(180);
//		colType.setMaxWidth(180);
//		colType.setMinWidth(180);
//		TableColumn colVoornaam = table.getColumnModel().getColumn(4);
//		colVoornaam.setPreferredWidth(200);
//		colVoornaam.setMaxWidth(200);
//		TableColumn colNrPlaat = table.getColumnModel().getColumn(5);
//		colNrPlaat.setPreferredWidth(185);
//		colNrPlaat.setMaxWidth(200);

		TableColumn colNaam = table.getColumnModel().getColumn(3);
//		colNaam.setPreferredWidth(colNaamWidth);
		colNaam.setMinWidth(150);
//		System.err.println("col naam breedte: " + colNaamWidth);
//		colNaam.setPreferredWidth(naamSize + (opmSize-10) + (badgeSize-20) + (typeSize-125) + (voornaamSize-145)
//										 + (nrPlaatSize - 75) + (vanSize - 35) + (totSize - 35));
//		colNaam.setPreferredWidth(naamSize + (opmSize-50) + (badgeSize-100) + (vanSize-100) + (totSize-100));
    }

    class InschijvingTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames = getColumnNames();
        private Vector<Object[]> data = null;
        private boolean col0SortAsc = false;
        private boolean col1SortAsc = false;
        private boolean col2SortAsc = false;
        private boolean col3SortAsc = false;
        private boolean col4SortAsc = false;
        private boolean col5SortAsc = false;
        private boolean col6SortAsc = false;
        private boolean col7SortAsc = false;

    	InschijvingTableModel(SoortHuurderEnum soortHuurder) {
    		data = getColumnData(soortHuurder);
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
        	Object[] o = data.get(row);
            return o[col];
        }
        
        public void sortData(int col) {
        	boolean sortAsc = getSorting(col);
        	//OPM", "BADGE", "TYPE", "Familienaam", "Voornaam", "NRplaat", "VAN", "TOT
        	if (col < 6) {
        		sortAlphabetically(col, sortAsc);
        	} else {
        		sortDate(col, sortAsc);
        	}
        	fireTableDataChanged();
        }
        
        private void sortAlphabetically(int col, boolean asc) {
        	Vector<Object[]> tmpData = new Vector<Object[]>(); 
        	for (Object[] o1 : data) {
        		boolean added = false;
        		String s1 = (String)o1[col];
        		for (Object[] o2 : tmpData) {
        			String s2 = (String)o2[col];
        			int i  = s1.compareTo(s2);
            		if ((asc && i < 0) || (!asc && i > 0)) {
            			tmpData.add(tmpData.indexOf(o2), o1);
            			added = true;
            			break;
            		}                			
        		}
        		if (!added) {
        			tmpData.add(o1);
        		}
    		}
        	data = tmpData;
        }
        
        private void sortDate(int col, boolean asc) {
        	Vector<Object[]> tmpData = new Vector<Object[]>(); 
			DateFormat formatter; 
		    formatter = new SimpleDateFormat("dd-MM-yyyy");
        	for (Object[] o1 : data) {
        		boolean added = false;
				Calendar cal1 = Calendar.getInstance();
				String sDate1 = (String)o1[col];
				try {
					Date date1 = (Date)formatter.parse(sDate1);
					cal1.setTime(date1);
					for (Object[] o2 : tmpData) {
						Calendar cal2 = Calendar.getInstance();
						String sDate2 = (String)o2[col];
						Date date2 = (Date)formatter.parse(sDate2);
						cal2.setTime(date2);
						System.err.println(cal1.before(cal2));
						if((asc && cal1.before(cal2)) || (!asc && cal1.after(cal2))) {
							tmpData.add(tmpData.indexOf(o2), o1);
							added = true;
							break;
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.err.println("date not correctly parsed");
				} 
				if (!added) {
					tmpData.add(o1);
				}
        	}
        	data = tmpData;
        }
            
        private boolean getSorting(int col) {
        	boolean ret = false;
        	switch (col) {
			case 0:
				ret = col0SortAsc = (col0SortAsc)?false:true;
				break;
			case 1:
				ret = col1SortAsc = (col1SortAsc)?false:true;
				break;
			case 2:
				ret = col2SortAsc = (col2SortAsc)?false:true;
				break;
			case 3:
				ret = col3SortAsc = (col3SortAsc)?false:true;
				break;
			case 4:
				ret = col4SortAsc = (col4SortAsc)?false:true;
				break;
			case 5:
				ret = col5SortAsc = (col5SortAsc)?false:true;
				break;
			case 6:
				ret = col6SortAsc = (col6SortAsc)?false:true;
				break;
			case 7:
				ret = col7SortAsc = (col7SortAsc)?false:true;
				break;

			default:
				break;
			}
        	return ret;
        }
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
                return false;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        
        public void setValueAt(Object value, int row, int col) {
            Object[] o = data.get(row);
            o[col] = value;
            // Normally, one should call fireTableCellUpdated() when 
            // a value is changed.  However, doing so in this demo
            // causes a problem with TableSorter.  The tableChanged()
            // call on TableSorter that results from calling
            // fireTableCellUpdated() causes the indices to be regenerated
            // when they shouldn't be.  Ideally, TableSorter should be
            // given a more intelligent tableChanged() implementation,
            // and then the following line can be uncommented.
            // fireTableCellUpdated(row, col);
        }
        
        public void removeEmptyRows() {
        	List<Object[]> objectsToRemove = new ArrayList<Object[]>();
        	for (Object[] o : data) {
        		if (o[9] == null || o[9] == "") {
        			objectsToRemove.add(o);
        		}
        	}
        	for (Object[] o : objectsToRemove) {
        		data.remove(o);
        	}
        	fireTableDataChanged();
        }
        
        public Vector<Object[]> getData() {
        	return data;
        }
    }

	/**
	 * This method initializes jScrollPaneLos	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneLos() {
		if (jScrollPaneLos == null) {
			jScrollPaneLos = new JScrollPane();
//			jScrollPaneLos.setBackground(tableColor);
//			jScrollPaneLos.setViewportView(getJTableLos());
		}
		return jScrollPaneLos;
	}

	/**
	 * This method initializes jScrollPaneChronologisch	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneChronologisch() {
		if (jScrollPaneChronologisch == null) {
			jScrollPaneChronologisch = new JScrollPane();
		}
		return jScrollPaneChronologisch;
	}

	private void reset() {
		jTabbedPane = null;
		jScrollPaneVast = null;
		jTableVast = null;
		jScrollPaneLos = null;
		jTableLos = null;
		jScrollPaneChronologisch = null;
		jTableChronologisch = null;
		jaartallen = new ArrayList<Integer>();
		jaartallenLos = new ArrayList<Integer>();
		jaartallenVast = new ArrayList<Integer>();
		colNaamWidth = 0;
		addColorsAndEmptyRows = true;
		inschrijvingen = null;
	}
	
	private class ColorRenderer extends JLabel implements TableCellRenderer {

		private static final long serialVersionUID = -8478858546170588624L;
		private SoortHuurderEnum soortHuurder;
		private List<Integer> tmpJaartallen;
	     
	     public ColorRenderer(SoortHuurderEnum soortHuurder)
	     {
	    	 this.soortHuurder = soortHuurder;
	    	 setJaartallenList();
	         setOpaque(true);
	     }
	     
	     private void setJaartallenList() {
	    	 if (soortHuurder == null) {
	    		 tmpJaartallen = jaartallen;
	    	 } else if (soortHuurder.equals(SoortHuurderEnum.VAST)) {
	    		 tmpJaartallen = jaartallenVast;
	    	 } else if (soortHuurder.equals(SoortHuurderEnum.LOS)) {
	    		 tmpJaartallen = jaartallenLos;
	    	 }
	     }
	     
	     public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
	        
	    	 setForeground(Color.BLACK);
	    	 setFont(font);

	         if (value != null) setText(value.toString());
	         String datum = (String)table.getModel().getValueAt(row, 7);
	         String[] data = new String[0];
	         if (datum != null) {
	        	 data = datum.split("-");
	         }
	         if (data.length == 3) {
		         int jaar = Integer.parseInt(data[2]);
		         if (tmpJaartallen.contains(jaar) && addColorsAndEmptyRows) {
		        	 setBackground(Color.LIGHT_GRAY);
		         } else {
		        	 setBackground(Color.WHITE);
		         }
	         } else {
	        	 setBackground(Color.WHITE);
	         }
	         
	         setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));
	         
	         return this;
	     }
	}

	public class ColumnHeaderListener extends MouseAdapter {
		
		InschijvingTableModel model;
		
		public ColumnHeaderListener(InschijvingTableModel model) {
			this.model = model;
		}
		
		public void mouseClicked(MouseEvent evt) { 
			JTable table = ((JTableHeader)evt.getSource()).getTable();
			TableColumnModel colModel = table.getColumnModel();
			// The index of the column whose header was clicked 
			int vColIndex = colModel.getColumnIndexAtX(evt.getX());
			
			// Return if not clicked on any column header
			if (vColIndex != -1 && addColorsAndEmptyRows) {
				addColorsAndEmptyRows = false;
				InschijvingTableModel model = (InschijvingTableModel)table.getModel();
				model.removeEmptyRows();
			}
			model.sortData(vColIndex);
		}
	}
}