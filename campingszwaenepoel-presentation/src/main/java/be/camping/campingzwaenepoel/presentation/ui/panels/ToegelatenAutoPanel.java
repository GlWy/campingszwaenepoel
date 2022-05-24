package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.tablemodel.MultiLineCellRenderer;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ToegelatenAutoPanel extends JPanel implements PanelInterface, ActionListener {

    @Autowired
    private StandplaatsService standplaatsService;

    private static final long serialVersionUID = 1L;

    private Controller controller;

	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
    private JButton jBtnSave;

    private Font font = new Font("Times New Roman", Font.BOLD, 18);
    
    private ToegelatenAutoTableModel model;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * This method initializes VASE	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
		}
		return jScrollPane;
	}
	
	private void setJScrollPane(JScrollPane jScrollPane) {
		this.jScrollPane = jScrollPane;
	}
	
	private JTable getJTable() {
		if (jTable == null) {
			resetJTable();
		}
	    return jTable;
	}
	
	private void resetJTable() {
        //Create a table with a sorter.
		model = new ToegelatenAutoTableModel();
        jTable = new JTable(model);
//      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        jTable.setFillsViewportHeight(true);
        jTable.setAutoCreateRowSorter(true);
        jTable.setRowHeight(60);
//		jTableVast.setBackground(tableColor);
        jTable.setForeground(Color.BLACK);
        jTable.setFont(font);
        jTable.getTableHeader().setFont(font);
        jTable.setGridColor(Color.GRAY);
        jTable.setDefaultRenderer(String.class, new MultiLineCellRenderer());
        initColumnSizes(jTable);
		/**
		 * TODO: als tekst in cel te groot, automatisch volgende lijn
		 */
	}

	   /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table) {
    	
/*		int typeSize = table.getColumnModel().getColumn(0).getWidth();
		int naamSize = table.getColumnModel().getColumn(1).getWidth();
		int nrplaatSize = table.getColumnModel().getColumn(2).getWidth();
		int merkSize = table.getColumnModel().getColumn(3).getWidth();
		int stickerSize = table.getColumnModel().getColumn(4).getWidth();
		int opmSize = table.getColumnModel().getColumn(5).getWidth();*/
		
		final int typeSizeVar = 100;
		final int nrplaatSizeVar = 40;
//		final int merkSizeVar = 50;
		final int stickerSizeVar = 30;
		
		TableColumn colType = table.getColumnModel().getColumn(0);
		colType.setPreferredWidth(typeSizeVar);
		TableColumn colNaam = table.getColumnModel().getColumn(1);
		colNaam.setPreferredWidth(200);
		TableColumn colOpm = table.getColumnModel().getColumn(5);
		colOpm.setPreferredWidth(300);
		TableColumn colNrplaat = table.getColumnModel().getColumn(2);
		colNrplaat.setPreferredWidth(nrplaatSizeVar);
//		TableColumn colMerk = table.getColumnModel().getColumn(3);
//		colMerk.setPreferredWidth(merkSizeVar);
		TableColumn colSticker = table.getColumnModel().getColumn(4);
		colSticker.setPreferredWidth(stickerSizeVar);
/*		TableColumn colOpm = table.getColumnModel().getColumn(5);
		
		colOpm.setPreferredWidth(opmSize + (typeSize - typeSizeVar)/2 + (nrplaatSize - nrplaatSizeVar)/2 + (merkSize - merkSizeVar)/2
										 + (stickerSize - stickerSizeVar)/2);
		colNaam.setPreferredWidth(naamSize + (typeSize - typeSizeVar)/2 + (nrplaatSize - nrplaatSizeVar)/2 + (merkSize - merkSizeVar)/2
										   + (stickerSize - stickerSizeVar)/2);*/
    }

	public boolean checkData() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean checkDataChanged() {
		return false;
	}

	public boolean checkDataForParent() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object getDataFromGUI() {
		return null;
	}

	public void save() {}

	public void setDataInGUI() {
        this.removeAll();
        this.setLayout(new BorderLayout());

        //Create the scroll pane and add the table to it.
        resetJTable();
		setJScrollPane(new JScrollPane(getJTable()));

        //Add the scroll pane to this panel.
		this.add(getJScrollPane(), BorderLayout.CENTER);

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
		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK, 2));
	}

    private String[] getColumnNames() {
        String[] columnNames = {"TYPE", "NAAM", "NR.PLAAT", "MERK", "STICKER", "OPMERKING"};
        return columnNames;
    }

    private Vector<Object[]> getColumnData() {
    	Set<InschrijvingPersoonDTO> personen = getInschrijvingPersonen();
    	List<InschrijvingPersoonDTO> sortedPersonen = sortInschrijvingPersonen(personen);
    	Vector<Object[]> data = new Vector<Object[]>();
    	for (InschrijvingPersoonDTO inschrijvingPersoon : sortedPersonen) {
    		PersoonDTO persoon = inschrijvingPersoon.getPersoon();
    		for (WagenDTO wagen : persoon.getWagens()) {
				Object[] o = new Object[6];
				o[0] = inschrijvingPersoon.getHuurdersPositie().getTranslationKey();
				o[1] = persoon.getNaam() + " " + persoon.getVoornaam();
				o[2] = wagen.getNummerplaat();
				o[3] = (StringUtils.isEmpty(wagen.getMerk())?"":wagen.getMerk());
				o[4] = (StringUtils.isEmpty(wagen.getSticker())?"":wagen.getSticker());
				o[5] = (StringUtils.isEmpty(wagen.getOpmerking())?"":wagen.getOpmerking());
				data.add(o);
    		}
    	}
   	
    	return data;
    }
    
    /**
     * TODO : 	deze methode moet aangepast worden als volgt:
     * 			- wagens van personen (LOS) moeten hier niet in
     * 			- wagens van personen (VAST) moeten hier in blijven (ongeacht datum)
     * --> dit kan slechts nadat de many-to-many tussen StandplaatsDatum.persoonID en Persoon in orde is
     */
    public Set<InschrijvingPersoonDTO> getInschrijvingPersonen() {
    	Set<InschrijvingPersoonDTO> inschrijvingPersonen = new HashSet<InschrijvingPersoonDTO>();
    	int standplaatsId = getController().getStandplaatsDTO().getId();
    	StandplaatsDTO standplaats = standplaatsService.getStandplaatsMetInschrijvingenEnPersonen(standplaatsId);
    	List<Integer>persoonIds = new ArrayList<Integer>(); 
    	
    	for (InschrijvingDTO inschrijving : standplaats.getInschrijvingen()) {
    		if (!inschrijving.getSoorthuurder().equals(SoortHuurderEnum.VAST)) {
    			continue;
    		}
    		for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
    			PersoonDTO persoon = inschrijvingPersoon.getPersoon();
    			if (persoon.getWagens().size() > 0) {
    				if (!persoonIds.contains(persoon.getId())) {
	    				inschrijvingPersonen.add(inschrijvingPersoon);
	    				persoonIds.add(persoon.getId());
	    			}
    			}
    		}
    	}
    	return inschrijvingPersonen;
    }
    
    private List<InschrijvingPersoonDTO> sortInschrijvingPersonen(Set<InschrijvingPersoonDTO> personen) {
    	List<InschrijvingPersoonDTO> sortedInschrijvingPersonen = new ArrayList<InschrijvingPersoonDTO>();
    	for (InschrijvingPersoonDTO inschrijvingPersoon : personen) {
//    	for (PersoonDTO persoon : personen) {
    		boolean added = false;
    		PersoonDTO persoon = inschrijvingPersoon.getPersoon();
    		for (InschrijvingPersoonDTO sortedPersoon : sortedInschrijvingPersonen) {
    			int sortNaam = persoon.getNaam().compareToIgnoreCase(sortedPersoon.getPersoon().getNaam()); 
    			if (sortNaam < 0) {
    				int index = sortedInschrijvingPersonen.indexOf(sortedPersoon);
    				if (index > 0) {
    					sortedInschrijvingPersonen.add(--index, inschrijvingPersoon);
    				} else {
    					sortedInschrijvingPersonen.add(0, inschrijvingPersoon);
    				}
    				added = true;
    				break;
    			} else if (sortNaam == 0) {
    				int sortVoorNaam = persoon.getVoornaam().compareToIgnoreCase(sortedPersoon.getPersoon().getVoornaam());
        			if (sortVoorNaam <= 0) {
        				int index = sortedInschrijvingPersonen.indexOf(sortedPersoon);
        				if (index > 0) {
        					sortedInschrijvingPersonen.add(--index, inschrijvingPersoon);
        				} else {
        					sortedInschrijvingPersonen.add(0, inschrijvingPersoon);
        				}
        				added = true;
        				break;
        			}
    			}
    		}
        	if (!added) {
        		sortedInschrijvingPersonen.add(inschrijvingPersoon);
        	}
    	}
    	return sortedInschrijvingPersonen;
    }

    class ToegelatenAutoTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames = getColumnNames();
        private Vector<Object[]> data = getColumnData();

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

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
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
        
        private boolean DEBUG = false;
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

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

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                Object[] o = data.get(i);
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + o[j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jBtnSave) {
			save();
		} 
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
	    	jBtnSave.doClick();
	    }
	}

}
