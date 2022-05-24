package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import be.camping.campingzwaenepoel.presentation.ui.panels.PersoonPanel;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;

public class ZoekPersonenKaartResultatenDialog extends JFrame {

	private static final long serialVersionUID = -6876695867598569300L;
	private boolean returnValue = false;
	private List<PersoonDTO> personen;
	private Object[] columnNames = {"Naam", "Voornaam", "geboortedatum"};
	private PersoonPanel persoonPanel;
    private Font font = new Font("Times New Roman", Font.BOLD, 18);
    private JTable jTable;
    private JButton jBtnOk;
    private JPanel jPanel;
    boolean checkPerson = false;
    private JFrame frame = this;
    private PersoonDTO persoonDTO;
    
	public ZoekPersonenKaartResultatenDialog(List<PersoonDTO> personen) {
	    this.personen = personen;
	    initComponents();
	}

	public PersoonDTO getPersoonDTO() {
		return persoonDTO;
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

//	    JScrollPane jScrollPane = new JScrollPane(getjTable());
//		add(jScrollPane, BorderLayout.CENTER);
//		add(getjPanel(), BorderLayout.SOUTH);
//		pack();
	    
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

		checkPerson = okToQuit();
	    if (checkPerson) {
			setVisible(false);
	//			System.exit(0);
	    }
	
	    addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        setVisible(false);
	        dispose();
	        System.exit(0);
	      }
	    });
	}

	boolean okToQuit() {
    	ZoekTableModel model = new ZoekTableModel(personen);
		jTable = new JTable(model);
    	final JScrollPane jScrollPane = new JScrollPane(jTable);
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
		            ZoekTableModel model = (ZoekTableModel)jTable.getModel();
		            persoonDTO = model.personen.get(row);
		            getJFrame().setVisible(false);
		            getJFrame().dispose();
		            returnValue = true;
		            frame.setVisible(false);
		            frame.dispose();
		            setVisible(false);
		            returnValue = true;
		            Window window = SwingUtilities.getWindowAncestor(jScrollPane);
		            window.dispose();
		        } else if (e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
		        	Point p = e.getPoint();
		            int row = jTable.rowAtPoint(p); 
		            ZoekTableModel model = (ZoekTableModel)jTable.getModel();
		            persoonDTO = model.personen.get(row);
		        }
		    }
		});
		
		Object[] array = {jScrollPane};
//		JDialog dialog = new JDialog(this);
//		dialog.add(new JScrollPane(jTable));
		Object[] options = {"Geen van de personen komt overeen"};
		JOptionPane.showOptionDialog(null, array, "", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//		JOptionPane.showConfirmDialog(null,array,"",JOptionPane., JOptionPane.WARNING_MESSAGE);
//		dialog.setVisible(true);
		return true;
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
			            returnValue = true;
			            Window window = SwingUtilities.getWindowAncestor(frame);
			            window.dispose();
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
        List<PersoonDTO> personen;

        public ZoekTableModel(List<PersoonDTO> personen) {
        	this.personen = personen;
        }
        
        public void setColumnData(List<PersoonDTO> personen) {
        	this.personen = personen;
        }
        	        
    	public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return personen.size();
        }

        public String getColumnName(int col) {
            return (String)columnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	PersoonDTO persoonDTO = personen.get(row);
        	Object obj = null;
        	switch (col) {
        	case 0: obj = persoonDTO.getNaam();
        		break;
        	case 1: obj = persoonDTO.getVoornaam();
        		break;
        	case 2:	obj = persoonDTO.getGeboortedatum();
        		break;
        	}
        	
        	if (obj == null) obj = "";
            return obj;
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
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

            PersoonDTO persoonDTO = personen.get(row);
        	switch (col) {
        	case 0: persoonDTO.setNaam((String) value);
        		break;
        	case 1: persoonDTO.setVoornaam((String) value);
        		break;
        	case 2:	persoonDTO.setGeboortedatum((Date) value);
        		break;
        	}

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

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                PersoonDTO persoonDTO = personen.get(i);
                System.out.print("  " + persoonDTO.getNaam() + " " + persoonDTO.getVoornaam());
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
}
