package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import be.camping.campingzwaenepoel.presentation.ui.panels.PersoonPanel;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;

public class ZoekPersonenResultatenDialog extends JFrame {

	private static final long serialVersionUID = -6876695867598569300L;
	private boolean returnValue = false;
	private List<PersoonDTO> personen;
	private Object[] columnNames = {"Naam", "Voornaam"};
	private PersoonPanel persoonPanel;
    private Font font = new Font("Times New Roman", Font.BOLD, 18);
    private JTable jTable;
    private JButton jBtnOk;
    private JPanel jPanel;
    private boolean showPhoto;
    private boolean showData;
    
	public ZoekPersonenResultatenDialog(List<PersoonDTO> personen, PersoonPanel persoonPanel, 
			boolean showPhoto, boolean showData) {
//	    super("ZoekPersonenResultatenDialog");
	    this.personen = personen;
	    this.persoonPanel = persoonPanel;
	    this.showPhoto = showPhoto;
	    initComponents();
	}

	public Object[] getColumnNames() {
		return columnNames;
	}

	public PersoonPanel getPersoonPanel() {
		return persoonPanel;
	}

	public void setPersoonPanel(PersoonPanel persoonPanel) {
		this.persoonPanel = persoonPanel;
	}

	public boolean isReturnValue() {
		return returnValue;
	}

	private void initComponents() {
	    // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    setLayout(new BorderLayout());

	    JScrollPane jScrollPane = new JScrollPane(getjTable());
		add(jScrollPane, BorderLayout.CENTER);
		add(getjPanel(), BorderLayout.SOUTH);
		pack();
	    
	    // Determine the new location of the window
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setLocation(x, y);
	    
		setVisible(true);
		setState(Frame.NORMAL);
		this.requestFocus();
	}

	public JTable getjTable() {
		if (jTable == null) {
	    	ZoekTableModel model = new ZoekTableModel(personen);
			jTable = new JTable(model);
			jTable.setFillsViewportHeight(true);
			jTable.setAutoCreateRowSorter(true);
			jTable.setGridColor(Color.GRAY);
			jTable.getTableHeader().setFont(font);
			jTable.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
			        if (e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			            Point p = e.getPoint();
			            int row = jTable.rowAtPoint(p);
			            PersoonDTO persoon = personen.get(row);
			            getJFrame().setVisible(false);
			            getJFrame().dispose();
			            getPersoonPanel().setPersoon(persoon);
			            if (showData) {
			            	getPersoonPanel().setDataInGUI(persoon, true, showPhoto);
			            }
			            returnValue = true;
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
			jBtnOk = new JButton("Geen van bovenstaande personen komt overeen.");
			jBtnOk.setSize(new Dimension(100, 28));
			jBtnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getJFrame().setVisible(false);		
					getJFrame().dispose();
				}
			});
		}
		return jBtnOk;
	}

    class ZoekTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private Object[] columnNames = getColumnNames();
        private Vector<Object[]> data = null;

        public ZoekTableModel(List<PersoonDTO> personen) {
        	data = new Vector<Object[]>();
        	for (PersoonDTO persoon : personen) {
        		Object[] o = {persoon.getNaam(), persoon.getVoornaam()};
        		data.add(o);
        	}
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
