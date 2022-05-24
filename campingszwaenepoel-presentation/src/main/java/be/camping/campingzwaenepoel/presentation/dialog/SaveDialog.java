package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import org.apache.commons.lang.StringUtils;

public class SaveDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	boolean unsavedChanges = false;
	private String message;
	private int option = -1;

	  /** Construct the object including its GUI */
	public SaveDialog() {
	    super("SaveDialog");
	    initComponents();
	}

	public SaveDialog(String message) {
	    super("SaveDialog");
	    this.message = message;
	    initComponents();
	}

	public SaveDialog(String message, int option) {
	    super("SaveDialog");
	    this.message = message;
	    this.option = option;
	    initComponents();
	}
	  
	private void initComponents() {
		this.setVisible(true);
	    Container cp = getContentPane();
	    cp.setLayout(new FlowLayout());
	    
	    // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    // Determine the new location of the window
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setLocation(x, y);

	    unsavedChanges = okToQuit();
        if (unsavedChanges) {
			setVisible(false);
			dispose();
        }
        
	    addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        setVisible(false);
	        dispose();
	        System.exit(0);
	      }
	    });

	    pack();
	}

	boolean okToQuit() {
		String[] choices = {"JA", "NEE"};
	    if (StringUtils.isEmpty(message)) {
	    	message = "Wenst u uw wijzigingen op te slaan?";
	    }
	    if (option == -1) {
	    	option = JOptionPane.YES_NO_CANCEL_OPTION;
	    } else if (option == 0) {
	    	choices[0] = "YES";
	    	choices[1] = "NO";
	    }
	    int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", 
	    		option, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
	    
	      // Use of "null" as the Icon argument is contentious... the
	      // document says you can pass null, but it does seem to
	      // generate a lot of blather if you do, something about
	      // a NullPointerException :-) ...

	    switch(result) {
		    case -1:
		    	return false;
		    case 0:  // save
		    	return true;
		    case 1:  // cancel
		    	return false;
		    default:
		    	throw new IllegalArgumentException("Unexpected return " + result);
	    }
	}
	
	public boolean isUnsavedChanges() {
		return unsavedChanges;
	}

}  //  @jve:decl-index=0:visual-constraint="87,63"