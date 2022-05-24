/*
 * ConfirmDialog.java
 *
 * Created on __DATE__, __TIME__
 */

package be.camping.campingzwaenepoel.presentation.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import be.camping.campingzwaenepoel.presentation.util.ScreenUtils;


/**
 *
 * @author  __USER__
 */
public class ConfirmDialog extends javax.swing.JDialog {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfirmDialog(JFrame parent) {
	
	    super(parent, "About Dialog", true);
	
	    Box b = Box.createVerticalBox();
	    b.add(Box.createGlue());
	    b.add(new JLabel("Gelieve een standplaats te kiezen."));
	    b.add(Box.createGlue());
	    getContentPane().add(b, "Center");
	
	    JPanel p2 = new JPanel();
	    JButton ok = new JButton("Ok");
	    p2.add(ok);
	    getContentPane().add(p2, "South");
	
	    ok.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	        setVisible(false);
	      }
	    });

	    Dimension screenSize = ScreenUtils.getScreensize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    Dimension dim = new Dimension(250, 150);
	    setLocation(screenWidth / 2 - (int)dim.getWidth() / 2, screenHeight / 2 - (int)dim.getHeight() / 2);
	    setSize((int)dim.getWidth(), (int)dim.getHeight());
    }
}