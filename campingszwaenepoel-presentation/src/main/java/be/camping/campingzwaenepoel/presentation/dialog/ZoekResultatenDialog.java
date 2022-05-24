package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.presentation.Controller;

public class ZoekResultatenDialog extends JFrame {

	private static final long serialVersionUID = -6876695867598569300L;
	private boolean returnValue = false;
	private Vector<Object[]> list;
	private Object[] columnNames;
    private Font font = new Font("Times New Roman", Font.BOLD, 18);
    private JTable jTable;
    private int eventNumber = 0;
    private String standplaats;
    private String naam;
    private String voornaam;
    private JButton jBtnOk;
    private JPanel jPanel;
    private Controller controller;
    

    public ZoekResultatenDialog() {
    	
    }
    
	public ZoekResultatenDialog(Vector<Object[]> list, Object[] columnNames, Controller controller) {
	    super("ZoekResultatenDialog");
	    this.list = list;
	    this.columnNames = columnNames;
	    this.controller = controller;
	    initComponents();
	}
	
	public Vector<Object[]> getList() {
		return list;
	}

	public void setList(Vector<Object[]> list) {
		this.list = list;
	}

	public Object[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Object[] columnNames) {
		this.columnNames = columnNames;
	}

	public int getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(int eventNumber) {
		this.eventNumber = eventNumber;
	}

	public boolean isReturnValue() {
		return returnValue;
	}

	public String getStandplaats() {
		return standplaats;
	}

	public void setStandplaats(String standplaats) {
		this.standplaats = standplaats;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	private void initComponents() {
	    
	    // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    setSize(new Dimension(500, 600));
	    setLayout(new BorderLayout());
	    
	    // Determine the new location of the window
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setLocation(x, y);
	    
		JScrollPane jScrollPane = new JScrollPane(getjTable());
		add(jScrollPane, BorderLayout.CENTER);
		add(getjPanel(), BorderLayout.SOUTH);

		setVisible(true);
	    pack();
	}

	public JTable getjTable() {
		if (jTable == null) {
	    	ZoekTableModel model = new ZoekTableModel(list);
			jTable = new JTable(model);
			jTable.setFillsViewportHeight(true);
			jTable.setAutoCreateRowSorter(true);
			jTable.setGridColor(Color.GRAY);
			jTable.getTableHeader().setFont(font);
			jTable.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e)
			    {
			        if (e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
			        {
			            Point p = e.getPoint();
			            int row = jTable.rowAtPoint(p); 
			            int column = jTable.columnAtPoint(p);
			            handleDoubleClickCell(row, column, jTable);
			            getJFrame().setVisible(false);
			            getJFrame().dispose();
			        }
			    }
			});
		}
		return jTable;
	}
	
	private JFrame getJFrame() {
		return this;
	}

	public JPanel getjPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getjBtnOk());
		}
		return jPanel;
	}

	public JButton getjBtnOk() {
		if (jBtnOk == null) {
			jBtnOk = new JButton("OK");
			jBtnOk.setSize(new Dimension(100, 28));
			jBtnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getJFrame().setVisible(false);				}
			});
		}
		return jBtnOk;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	private void handleDoubleClickCell(int row, int column, JTable jTable) {
        ZoekTableModel model = (ZoekTableModel)jTable.getModel();
        Object o = model.getValueAt(row, 0);
        int tab = 4;
        for (Object oColumn : columnNames) {
        	if (Constant.SALDO.equals(oColumn)) {
        		tab = 6;
        		break;
        	}
        }
        getController().setStandplaatsEnTabVoorZoek((String)o, tab);
	}
	
/*	private void handleDoubleClickCell(int row, int column, JTable jTable) {
        String columnname = jTable.getColumnName(column);
        ZoekTableModel model = (ZoekTableModel)jTable.getModel();
		if (Constant.STANDPLAATS.equals(columnname)) {
			setEventNumber(1);
			setStandplaats((String)model.getValueAt(row, column));
		} else if (Constant.NAAM.equals(columnname) || Constant.VOORNAAM.equals(columnname)) {
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridLayout(1, 2));

			JRadioButton rbtnInschrijving = new JRadioButton("Inschrijving");
			rbtnInschrijving.setActionCommand("inschrijving");
			rbtnInschrijving.setFont(font);

			JRadioButton rbtnPersoon = new JRadioButton("Persoon");
			rbtnPersoon.setActionCommand("persoon");
			rbtnPersoon.setFont(font);
			
			ButtonGroup group = new ButtonGroup();
			group.add(rbtnInschrijving);
			group.add(rbtnPersoon);

			jPanel.add(rbtnInschrijving);
			jPanel.add(rbtnPersoon);

			Object[] array = {jPanel};
			int result = JOptionPane.showConfirmDialog(null,array,"",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == 0) {
				if (rbtnInschrijving.isSelected()) {
					setEventNumber(2);
				} else if (rbtnPersoon.isSelected()) {
					setEventNumber(3);
					column--;
				}
				setNaam((String)model.getValueAt(row, column));
				setVoornaam((String)model.getValueAt(row, column+1));
			}
		}
	}*/
	
    class ZoekTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private Object[] columnNames = getColumnNames();
        private Vector<Object[]> data = null;

        public ZoekTableModel(Vector<Object[]> list) {
        	data = list;
        }
        
        public Vector<Object[]> getData() {
			return data;
		}

		public void setData(Vector<Object[]> data) {
			this.data = data;
		}

		public void setColumnData(Vector<Object[]> list) {
        	this.data = list;
        }
        	        
    	public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return (String)columnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	Object[] o = data.get(row);
        	Object obj = o[col];
        	if (obj == null) obj = "";
            return obj;
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
                for (int j=0; j < numCols; j++) {
                	Object[] o = data.get(i);
                    System.out.print("  " + o[j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

}
