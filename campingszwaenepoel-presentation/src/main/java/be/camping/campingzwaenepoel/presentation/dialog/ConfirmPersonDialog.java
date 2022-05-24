package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ConfirmPersonDialog extends JFrame {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean checkPerson = false;
	private String naam;
	private Date geboortedatum;
	
	  /** Construct the object including its GUI */
	  public ConfirmPersonDialog(final String naam, final Date date) {
	    super("ConfirmPersoonDialog");
	    this.naam = naam;
	    this.geboortedatum = date;
	    
		this.setVisible(true);
	    Container cp = getContentPane();
	    cp.setLayout(new GridLayout(2, 2));
	    cp.add(new JLabel("naam: "));
	    cp.add(new JTextField(naam));
	    cp.add(new JLabel("geboortedatum: "));
	    cp.add(new JTextField(date.toString()));
	    
	    // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	    // Determine the new location of the window
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setLocation(x, y);

//	    add(cp);
	    add(new JLabel("naam: "));
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
	
	    pack();
	  }
	  
	boolean okToQuit() {
	    String[] choices = {"JA", "NEE"};
	    int result = JOptionPane.showOptionDialog(this, "Wenst u "+naam+" (geboortedatum: "+geboortedatum+") in te schrijven?", "Waarschuwing", 
	    			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);

	      // Use of "null" as the Icon argument is contentious... the
	      // document says you can pass null, but it does seem to
	      // generate a lot of blather if you do, something about
	      // a NullPointerException :-) ...

	    if (result >= 0)
	      System.out.println("You clicked " + choices[result]);

	    switch(result) {
	    case -1:
	      return false;
	    case 0:  // save
	      // mainApp.doSave();
	      return true;
	    case 1:  // cancel
	      return false;
	    default:
	      throw new IllegalArgumentException("Unexpected return " + result);
	    }
	}
	
	  public boolean isSamePerson() {
		  return checkPerson;
	  }

}
