package be.camping.campingzwaenepoel.presentation.autocomplete;

import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class Java2sAutoComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994575632445882107L;

	public class AutoTextFieldEditor extends BasicComboBoxEditor {

		public Java2sAutoTextField getAutoTextFieldEditor() {
			return (Java2sAutoTextField) editor;
		}

		AutoTextFieldEditor(java.util.List<String> list) {
			editor = new Java2sAutoTextField(list, Java2sAutoComboBox.this);
		}

		public void deselect() {
			String s = editor.getText();
			int lengte = s.length();
			editor.setSelectionStart(lengte);
			editor.setSelectionEnd(lengte);
		}
	}

	public Java2sAutoComboBox(java.util.List<String> list) {
		isFired = false;
		autoTextFieldEditor = new AutoTextFieldEditor(list);
		setEditable(true);
		setModel(new DefaultComboBoxModel(list.toArray()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2044436981327049404L;

			@Override
			protected void fireContentsChanged(Object obj, int i, int j) {
				if (!isFired)
					super.fireContentsChanged(obj, i, j);
			}

		});
		setEditor(autoTextFieldEditor);
	}

	public boolean isCaseSensitive() {
		return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
	}

	public void setCaseSensitive(boolean flag) {
		autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
	}

	public boolean isStrict() {
		return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
	}

	public void setStrict(boolean flag) {
		autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
	}

	public java.util.List<String> getDataList() {
		return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
	}

	public void setDataList(java.util.List<String> list) {
		autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
		setModel(new DefaultComboBoxModel(list.toArray()));
	}

	public void setSelectedValue(Object obj) {
		if (isFired) {
			return;
		} else {
			isFired = true;
			setSelectedItem(obj);
			fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder, 1));
			isFired = false;
			return;
		}
	}

	@Override
	protected void fireActionEvent() {
		if (!isFired)
			super.fireActionEvent();
	}

	public AutoTextFieldEditor getAutoTextFieldEditor() {
		return autoTextFieldEditor;
	}

	public void addEditorFocusListener(FocusListener focusListener) {
		getAutoTextFieldEditor().getAutoTextFieldEditor().addFocusListener(focusListener);
	}

	public void deselect() {
		getAutoTextFieldEditor().deselect();
	}

	private final AutoTextFieldEditor autoTextFieldEditor;

	private boolean isFired;
}
