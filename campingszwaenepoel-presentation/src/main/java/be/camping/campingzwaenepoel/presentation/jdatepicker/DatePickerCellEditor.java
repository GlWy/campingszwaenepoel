package be.camping.campingzwaenepoel.presentation.jdatepicker;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * A CellEditor using a JXDatePicker as editor component.<p>
 * 
 * NOTE: this class will be moved!
 * 
 * @author Richard Osbald
 * @author Jeanette Winzenburg
 */
public class DatePickerCellEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {
	
//	protected JXDatePicker xdatePicker = new JXDatePicker();
	protected JDatePicker datePicker;

	protected DateFormat dateFormat;
	
	protected int clickCountToStart = 2;
	
	private ActionListener pickerActionListener;
	
	protected boolean ignoreAction;
	
	private static Logger logger = Logger.getLogger(DatePickerCellEditor.class
	        .getName());
	
	private static final long serialVersionUID = -1L;
	
	/**
	 * Instantiates a editor with the default dateFormat.
	 * 
	 * PENDING: always override default from DatePicker?
	 *
	 */
	public DatePickerCellEditor() {
	    this(null);
	}
	
	/**
	 * Instantiates an editor with the given dateFormat. If
	 * null, the datePickers default is used.
	 * 
	 * @param dateFormat
	 */
	public DatePickerCellEditor(DateFormat dateFormat) {
	    // JW: the copy is used to synchronize .. can 
	    // we use something else?

	    datePicker = JDateComponentFactory.createJDatePicker();
	    this.dateFormat = dateFormat;
//	    datePicker = new JXDatePicker();
	    // default border crushes the editor/combo
//	    datePicker.getEditor().setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1)); 
	    // should be fixed by j2se 6.0
//	    datePicker.setFont(UIManager.getDefaults().getFont("TextField.font")); 
/*	    if (dateFormat != null) {
	        datePicker.setFormats(dateFormat);
	    }*/
	    datePicker.addActionListener(getPickerActionListener());
	}
	
	//-------------------- CellEditor
	
	/**
	 * Returns the pickers date. 
	 * 
	 * Note: the date is only meaningful after a stopEditing and 
	 *   before the next call to getTableCellEditorComponent.
	 */
	public Date getCellEditorValue() {
		return datePicker.getTime();
//	    return datePicker.getDate();
	}
	
	@Override
	public boolean isCellEditable(EventObject anEvent) {
	    if (anEvent instanceof MouseEvent) {
	        return ((MouseEvent) anEvent).getClickCount() >= getClickCountToStart();
	    }
	    return super.isCellEditable(anEvent);
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * 
	 * Overridden to commit pending edits. If commit successful, returns super,
	 * else returns false.
	 * 
	 * 
	 */
	@Override
	public boolean stopCellEditing() {
	    ignoreAction = true;
	    boolean canCommit = commitChange();
	    ignoreAction = false;
	    if (canCommit) {
	        return super.stopCellEditing();
	    }
	    return false;
	}
	
	/**
	 * Specifies the number of clicks needed to start editing.
	 * 
	 * @param count an int specifying the number of clicks needed to start
	 *        editing
	 * @see #getClickCountToStart
	 */
	public void setClickCountToStart(int count) {
	    clickCountToStart = count;
	}
	
	/**
	 * Returns the number of clicks needed to start editing.
	 *
	 * @return the number of clicks needed to start editing
	 */
	public int getClickCountToStart() {
	    return clickCountToStart;
	}
	
	
	//------------------------ TableCellEditor   
	
	public Component getTableCellEditorComponent(JTable table, Object value,
	        boolean isSelected, int row, int column) {
	    // PENDING JW: can remove the ignore flags here?
	    // the picker learnde to behave ...
	    ignoreAction = true;
        if (!StringUtils.isEmpty((String)value)) {
            Date date = getValueAsDate(value);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            datePicker.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        }
//	    datePicker.setDate(getValueAsDate(value));
	    // todo how to..
	    // SwingUtilities.invokeLater(new Runnable() {
	    // public void run() {
	    // datePicker.getEditor().selectAll();
	    // }
	    // });
	    ignoreAction = false;
	    return (Component)datePicker;
	}
	
	//-------------------------  TreeCellEditor
	
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
	    // PENDING JW: can remove the ignore flags here?
	    // the picker learnde to behave ...
	    ignoreAction = true;
        if (!StringUtils.isEmpty((String)value)) {
            Date date = getValueAsDate(value);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            datePicker.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        }
//	    datePicker.setDate(getValueAsDate(value));
	    // todo how to..
	    // SwingUtilities.invokeLater(new Runnable() {
	    // public void run() {
	    // datePicker.getEditor().selectAll();
	    // }
	    // });
	    ignoreAction = false;
	    return (Component)datePicker;
	}
	
	//-------------------- helpers    
	
	/**
	 * Returns the given value as Date.
	 * 
	 * PENDING: abstract into something pluggable (like StringValue 
	 *   in ComponentProvider?)
	 *   
	 * @param value the value to map as Date
	 * @return the value as Date or null, if not successful.
	 * 
	 */
	protected Date getValueAsDate(Object value) {
	    if (isEmpty(value)) return null;
	    if (value instanceof Date) {
	        return (Date) value;
	    } 
	    if (value instanceof Long) {
	        return new Date((Long) value);
	    }
	    if (value instanceof String) {
	        try {
	            // JW: why was the parsing synchronized?
	//          synchronized (dateFormat) {
	//          datePicker.setDate(dateFormat.parse((String) value));
	//      }
	            return dateFormat.parse((String) value);
	        } catch (ParseException e) {
	            handleParseException(e);
	        }
	    }
	    if (value instanceof DefaultMutableTreeNode) {
	        return getValueAsDate(((DefaultMutableTreeNode) value).getUserObject());
	    }
	    if (value instanceof AbstractMutableTreeTableNode) {
	        return getValueAsDate(((AbstractMutableTreeTableNode) value).getUserObject());
	    }
	    return null;
	}
	
	/**
	 * @param e
	 */
	protected void handleParseException(ParseException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e.getMessage());
	}
	
	protected boolean isEmpty(Object value) {
	    return value == null || value instanceof String
	            && ((String) value).length() == 0;
	}
	
	//--------------- picker specifics    
	/**
	 * Commits any pending edits and returns a boolean indicating whether the
	 * commit was successful.
	 * 
	 * @return true if the edit was valid, false otherwise.
	 */
	protected boolean commitChange() {
	    try {
//	        datePicker.commitEdit();
	        fireEditingStopped();
	        return true;
	    } catch (Exception e) {
	    }
	    return true;
	}
	
	/**
	 * 
	 * @return the DatePicker's formats.
	 * 
	 * @see org.jdesktop.swingx.JXDatePicker#getFormats().
	 */
/*	public DateFormat[] getFormats() {
	    return datePicker.getFormats();
	}*/
	
	/**
	 * 
	 * @param formats the formats to use in the datepicker.
	 * 
	 * @see org.jdesktop.swingx.JXDatePicker#setFormats(DateFormat...)).
	 * 
	 */
/*	public void setFormats(DateFormat... formats) {
	    datePicker.setFormats(formats);
	}*/
	/**
	 * Returns the ActionListener to add to the datePicker.
	 * 
	 * @return the action listener to listen for datePicker's
	 *    action events.
	 */
	protected ActionListener getPickerActionListener() {
	    if (pickerActionListener == null) {
	        pickerActionListener = createPickerActionListener();
	    }
	    return pickerActionListener;
	}
	
	/**
	 * Creates and returns the ActionListener for the Picker.
	 * @return the ActionListener to listen for Picker's action events.
	 */
	protected ActionListener createPickerActionListener() {
	    ActionListener l = new ActionListener() {
	        public void actionPerformed(final ActionEvent e) {
	            // avoid duplicate trigger from
	            // commit in stopCellEditing
	            if (ignoreAction)
	                return;
	            // still need to invoke .. hmm 
	            // no ... with the table cooperating the
	            // invoke is contra-productive!
	            terminateEdit(e);                         
	        }
	
	        /**
	         * @param e
	         */
	        private void terminateEdit(final ActionEvent e) {
/*	            if ((e != null)
	                    && (JXDatePicker.COMMIT_KEY.equals(e.getActionCommand()))) {*/
	        	System.err.println(e.getActionCommand());
	        	if (e != null) {
	                stopCellEditing();
	            } else {
	                cancelCellEditing();
	            }
	        }
	    };
	    return l;
	}

}