package be.camping.campingzwaenepoel.presentation.listeners;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class RequestFocusListener implements AncestorListener {

	@Override
	public void ancestorAdded(AncestorEvent event) {
		if (event.getComponent() instanceof JTextField) {
			JTextField component = (JTextField)event.getComponent();
			component.requestFocusInWindow();
			component.selectAll();
			component.removeAncestorListener(this);
		} else {
			JComponent component = (JComponent)event.getComponent();
			component.requestFocusInWindow();
			component.removeAncestorListener(this);
		}
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}

