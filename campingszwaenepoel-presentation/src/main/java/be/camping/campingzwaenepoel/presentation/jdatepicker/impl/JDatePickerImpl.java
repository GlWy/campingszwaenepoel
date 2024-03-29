/**
Copyright 2004 Juan Heyns. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Juan Heyns.
 */
package be.camping.campingzwaenepoel.presentation.jdatepicker.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import be.camping.campingzwaenepoel.presentation.jdatepicker.DateModel;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePanel;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;

public class JDatePickerImpl extends JPanel implements JDatePicker {

	private static final long serialVersionUID = 2814777654384974503L;

	private Popup popup;
	private final JFormattedTextField formattedTextField;
	private final JButton button;

	private final JDatePanelImpl datePanel;
	private final InternalEventHandler internalEventHandler;

	public JDatePickerImpl(JDatePanelImpl dateInstantPanel) {
		this(dateInstantPanel, null);
	}

	/**
	 * You are able to set the format of the date being displayed on the label.
	 * Formatting is described at:
	 * 
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
	 * 
	 * @param datePanel
	 * @param formatter
	 */
	public JDatePickerImpl(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
		this.datePanel = datePanel;

		// Initialise Variables
		popup = null;
		datePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		internalEventHandler = new InternalEventHandler();

		// Create Layout
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		// Create and Add Components
		// Add and Configure TextField
		formattedTextField = new JFormattedTextField((formatter != null) ? formatter : createDefaultFormatter());
		DateModel<?> model = datePanel.getModel();
		setTextFieldValue(formattedTextField, model.getYear(), model.getMonth(), model.getDay());
		formattedTextField.setEditable(false);
		add(formattedTextField);
		layout.putConstraint(SpringLayout.WEST, formattedTextField, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, formattedTextField);

		// Add and Configure Button
		button = new JButton("...");
		button.setFocusable(true);
		add(button);
		layout.putConstraint(SpringLayout.WEST, button, 1, SpringLayout.EAST, formattedTextField);
		layout.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, button);
		layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, button);

		// Do layout formatting
		int h = (int) button.getPreferredSize().getHeight();
		int w = (int) datePanel.getPreferredSize().getWidth();
		button.setPreferredSize(new Dimension(h, h));
		formattedTextField.setPreferredSize(new Dimension(w - h - 1, h));

		// Add event listeners
		addHierarchyBoundsListener(internalEventHandler);
		// TODOaddAncestorListener(listener)
		button.addActionListener(internalEventHandler);
		formattedTextField.addPropertyChangeListener("value", internalEventHandler);
		datePanel.addActionListener(internalEventHandler);
		datePanel.getModel().addChangeListener(internalEventHandler);
	}

	protected JFormattedTextField.AbstractFormatter createDefaultFormatter() {
		return new DateComponentFormatter();
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		datePanel.addActionListener(actionListener);
	}

	@Override
	public void removeActionListener(ActionListener actionListener) {
		datePanel.removeActionListener(actionListener);
	}

	@Override
	public void setI18nStrings(Properties i18nStrings) {
		datePanel.setI18nStrings(i18nStrings);
	}

	@Override
	public Properties getI18nStrings() {
		return datePanel.getI18nStrings();
	}

	@Override
	public DateModel<?> getModel() {
		return datePanel.getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.jdatepicker.JDatePicker#setTextEditable(boolean)
	 */
	@Override
	public void setTextEditable(boolean editable) {
		formattedTextField.setEditable(editable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.jdatepicker.JDatePicker#isTextEditable()
	 */
	@Override
	public boolean isTextEditable() {
		return formattedTextField.isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.jdatepicker.JDatePicker#setButtonFocusable(boolean)
	 */
	@Override
	public void setButtonFocusable(boolean focusable) {
		button.setFocusable(focusable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.jdatepicker.JDatePicker#getButtonFocusable()
	 */
	@Override
	public boolean getButtonFocusable() {
		return button.isFocusable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.jdatepicker.JDatePicker#getJDateInstantPanel()
	 */
	public JDatePanel getJDateInstantPanel() {
		return datePanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.jdatepicker.JDatePicker#getJFormattedTextField()
	 */
	@Override
	public JFormattedTextField getJFormattedTextField() {
		return formattedTextField;
	}

	public JFormattedTextField getFormattedTextField() {
		return formattedTextField;
	}

	/**
	 * Called internally to popup the dates.
	 */
	private void showPopup() {
		if (popup == null) {
			PopupFactory fac = new PopupFactory();
			Point xy = getLocationOnScreen();
			datePanel.setVisible(true);
			popup = fac.getPopup(this, datePanel, (int) xy.getX(), (int) (xy.getY() + this.getHeight()));
			popup.show();
		}
	}

	/**
	 * Called internally to hide the popup dates.
	 */
	private void hidePopup() {
		if (popup != null) {
			popup.hide();
			popup = null;
		}
	}

	/**
	 * This internal class hides the public event methods from the outside
	 */
	private class InternalEventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener,
			PropertyChangeListener {

		@Override
		public void ancestorMoved(HierarchyEvent arg0) {
			hidePopup();
		}

		@Override
		public void ancestorResized(HierarchyEvent arg0) {
			hidePopup();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == button) {
				if (popup == null) {
					showPopup();
				} else {
					hidePopup();
				}
			} else if (arg0.getSource() == datePanel) {
				hidePopup();
			}
		}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource() == datePanel.getModel()) {
				DateModel<?> model = datePanel.getModel();
				setTextFieldValue(formattedTextField, model.getYear(), model.getMonth(), model.getDay());
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (formattedTextField.isEditable() && formattedTextField.getValue() != null) {
				Calendar value = (Calendar) formattedTextField.getValue();
				datePanel.getModel().setDate(value.get(Calendar.YEAR), value.get(Calendar.MONTH),
						value.get(Calendar.DATE));
			}
		}

	}

	@Override
	public boolean isDoubleClickAction() {
		return datePanel.isDoubleClickAction();
	}

	@Override
	public boolean isShowYearButtons() {
		return datePanel.isShowYearButtons();
	}

	@Override
	public void setDoubleClickAction(boolean doubleClickAction) {
		datePanel.setDoubleClickAction(doubleClickAction);
	}

	@Override
	public void setShowYearButtons(boolean showYearButtons) {
		datePanel.setShowYearButtons(showYearButtons);
	}

	private void setTextFieldValue(JFormattedTextField textField, int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		textField.setValue(calendar);
	}

	@Override
	public void clearTextField() {
		formattedTextField.setValue(null);
	}

	public void formatTextfield(DateFormat format) {

	}

	@Override
	public Date getTime() {
		String sDate = getModel().getDay() + "-" + (getModel().getMonth() + 1) + "-" + getModel().getYear();
		sDate = sDate.replace("/", "-");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("date not correctly parsed");
		}
		return date;
	}

	@Override
	public void resetTextFieldToDefaultValue() {
		getModel().setDate(2014, 6, 1);
		clearTextField();
	}

}
