/*
 * MainFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package be.camping.campingzwaenepoel.presentation.ui;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import be.camping.campingzwaenepoel.common.constants.FotoConstant;
//import be.camping.campingzwaenepoel.eid.EidUtils;

/**
 *
 * @author  __USER__
 */
public class MainFrame extends javax.swing.JFrame implements WindowListener, Runnable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MainFrame.class);

	@SuppressWarnings("static-access")
	public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        this.addWindowListener(this);

        setVisible(true);
        setState(Frame.NORMAL);
    }

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		logger.info("program closed");
	}

	@Override
	public void windowClosing(WindowEvent e) {
//		EidUtils.setRelease(true);
//		EidUtils.releaseSDK();
		FotoConstant.setQuitProgram(true);
		while (FotoConstant.isImportBusy()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}