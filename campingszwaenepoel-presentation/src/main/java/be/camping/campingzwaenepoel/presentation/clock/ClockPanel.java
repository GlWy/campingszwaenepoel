package be.camping.campingzwaenepoel.presentation.clock;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClockPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	Thread t,t1;
	private JLabel lblDate = null;
	private JLabel lblTime = null;
    Font labelFont = new Font("SansSerif", Font.BOLD, 36);

	public void run() {
		t1 = Thread.currentThread();
	    while(t1 == t){
	    	Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy");
	        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	        lblDate.setText(sdf.format(cal.getTime()));
	        lblTime.setText(sdfTime.format(cal.getTime()));
	        lblDate.repaint();
	        lblTime.repaint();
	        repaint();
	      try{
	        Thread.sleep(1000);    
	      }catch(InterruptedException e){}
	    }
	}

	/**
	 * This is the default constructor
	 */
	public ClockPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridLayout gridLayout = new GridLayout(2, 1);
		gridLayout.setColumns(1);
		gridLayout.setRows(2);
		this.setLayout(gridLayout);
		this.setSize(200, 120);
	    lblDate = new JLabel();
        lblDate.setFont(labelFont);
        lblDate.setHorizontalAlignment(JLabel.CENTER);
        lblTime = new JLabel();
        lblTime.setFont(labelFont);
        lblTime.setHorizontalAlignment(JLabel.CENTER);
		Calendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        lblDate.setText(sdf.format(cal.getTime()));
        lblTime.setText(sdfTime.format(cal.getTime()));
        this.add(lblDate);
        this.add(lblTime);
		t = new Thread(this);
	    t.start();
	}
	
}
