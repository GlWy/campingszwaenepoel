package be.camping.campingzwaenepoel.presentation.dialog.fotochooser;

import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.dialog.ConfirmDialog;

public class FotoChooser extends JPanel implements ActionListener {

	private JTextArea fotobestanden;
    private JFileChooser fc;
    private File[] files;
    private JButton openButton, saveButton, saveMainImageButton, deleteButton;
    private Java2sAutoComboBox cboBox;
    private ArrayList<String> list = new ArrayList<String>();
    private String outputDir;
    private Controller controller;

    public FotoChooser() {
        super(new BorderLayout());

        //Create the log first, because the action listener
        //needs to refer to it.
        fotobestanden = new JTextArea(5,20);
        fotobestanden.setMargin(new Insets(5,5,5,5));
        fotobestanden.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(fotobestanden);
                
        cboBox = new Java2sAutoComboBox(list);

        openButton = new JButton("Selecteer de foto's...");
        openButton.addActionListener(this);
        
        saveButton = new JButton("Opslaan");
        saveButton.addActionListener(this);

        deleteButton = new JButton("Verwijderen");
        deleteButton.addActionListener(this);
        
        saveMainImageButton = new JButton("Opslaan hoofd foto");
        saveMainImageButton.addActionListener(this);
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(cboBox);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveMainImageButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.WEST);
        add(logScrollPane, BorderLayout.CENTER);
        
        setBorder(new LineBorder(Color.BLACK, 2));
    }

    public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
		cboBox.setDataList(list);
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openButton) {
	        //Set up the file chooser.
	        if (fc == null) {
	            fc = new JFileChooser();
	
		    //Add a custom file filter and disable the default
		    //(Accept All) file filter.
	            fc.addChoosableFileFilter(new ImageFilter());
	            fc.setAcceptAllFileFilterUsed(false);
	
		    //Add custom icons for file types.
//	            fc.setFileView(new ImageFileView());
	
		    //Add the preview pane.
	            fc.setAccessory(new ImagePreview(fc));
	            
	        // select multiple files
	            fc.setMultiSelectionEnabled(true);
	            
	            
	        }
	
	        //Show it.
	        int returnVal = fc.showDialog(FotoChooser.this, "Kies bestand");
	
	        //Process the results.
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	files = fc.getSelectedFiles();
	        	for (File file : files) {
					String newline = "\n";
					fotobestanden.append(file.getName() + newline);
	        	}
	//            File file = fc.getSelectedFile();
	//            log.append("Attaching file: " + file.getName() + "." + newline);
	        } else {
	//            log.append("Attachment cancelled by user." + newline);
	        }
	        fotobestanden.setCaretPosition(fotobestanden.getDocument().getLength());
	
	        //Reset the file chooser for the next time it's shown.
	//        fc.setSelectedFile(null);
	        fc.setSelectedFiles(null);
        } else if (e.getSource() == saveButton) {
        	if (cboBox.getSelectedItem().equals("")) {
        		JDialog f = new ConfirmDialog(new JFrame());
        	    f.setVisible(true);
	        } else {
	        	boolean copied = true;
	        	String outDir = getOutputDir() + "/standplaats/";
	        	File outputDir = new File(outDir);
	        	if (!outputDir.exists()) {
	        		outputDir.mkdir();
	        	}
	        	outDir += cboBox.getSelectedItem();
	        	outputDir = new File(outDir);
	        	if (!outputDir.exists()) {
	        		outputDir.mkdir();
	        	}
	        	outDir += "/foto/";
	        	outputDir = new File(outDir);
	        	if (!outputDir.exists()) {
	        		outputDir.mkdir();
	        	}
	        	String grootDir = outDir + "/groot/";
	        	File outputDirOrig = new File(grootDir);
	        	if (!outputDirOrig.exists()) {
	        		outputDirOrig.mkdir();
	        	}
	    		String origDir = outDir + "/origineel/";
	        	File origFile = new File(origDir);
	        	if (!origFile.exists()) {
	        		origFile.mkdir();
	        	}
	        	
	        	BufferedInputStream bufferedinputstream = null;
	        	BufferedOutputStream bufferedoutputstream = null;

	    		for (File file : files) {
	            	try
	            	{
	            		String dest = outDir + file.getName();
	            		scale(file.getAbsolutePath(), 380, 230, dest);

	            		dest = grootDir + file.getName();
	            		scale(file.getAbsolutePath(), 933, 700, dest);

	            		File outputFileOrig = new File(origDir + file.getName());
	            	    bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
	            	    bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(outputFileOrig));
	            	    byte abyte0[] = new byte[8192];
	            	    int i;
	            	    while((i = bufferedinputstream.read(abyte0)) != -1) 
	            		bufferedoutputstream.write(abyte0, 0, i);
	            	
	            	} catch (IOException ioe) {
	            		copied = false;
						ioe.printStackTrace();
	            	}
	        	    if (copied) {
	        	    	files = null;
	                	fotobestanden.setText("");
	        	    }
	    		}
        	}
        } else if (e.getSource() == saveMainImageButton) {
        	if (cboBox.getSelectedItem().equals("")) {
        		JDialog f = new ConfirmDialog(new JFrame());
        	    f.setVisible(true);
	        } else {
	        	boolean copied = true;
	        	String outDir = getOutputDir() + "/standplaats/";
	        	File outputDir = new File(outDir);
	        	if (!outputDir.exists()) {
	        		outputDir.mkdir();
	        	}
	        	outDir += cboBox.getSelectedItem();
	        	outputDir = new File(outDir);
	        	if (!outputDir.exists()) {
	        		outputDir.mkdir();
	        	}
	        	outDir += "/hoofdfoto/";
	        	outputDir = new File(outDir);
	        	if (!outputDir.exists()) {
	        		outputDir.mkdir();
	        	}
	        	String grootDir = outDir + "/groot/";
	        	File grootFile = new File(grootDir);
	        	if (!grootFile.exists()) {
	        		grootFile.mkdir();
	        	}
	    		String origDir = outDir + "/origineel/";
	        	File origFile = new File(origDir);
	        	if (!origFile.exists()) {
	        		origFile.mkdir();
	        	}

	        	BufferedInputStream bufferedinputstream = null;
	        	BufferedOutputStream bufferedoutputstream = null;

	        	if (files.length > 0) {
	        		File file = files[0];
	            	try
	            	{
	            		String dest = outDir + file.getName();
	            		scale(file.getAbsolutePath(), 138, 120, dest);

	            		dest = grootDir + file.getName();
	            		scale(file.getAbsolutePath(), 933, 700, dest);

	            		File outputFileOrig = new File(origDir + File.separator + file.getName());
	            	    bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
	            	    bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(outputFileOrig));
	            	    byte abyte0[] = new byte[8192];
	            	    int i;
	            	    while((i = bufferedinputstream.read(abyte0)) != -1) 
	            		bufferedoutputstream.write(abyte0, 0, i);
	            	    
	            	    if (cboBox.getSelectedItem().equals(getController().getHeader().getjComboBoxGroundNumber().getSelectedItem())) {
	            	    	getController().resetHoofdfoto();
	            	    }
	            	
	            	} catch (IOException ioe) {
	            		copied = false;
						ioe.printStackTrace();
	            	}
	        	    if (copied) {
	        	    	files = null;
	                	fotobestanden.setText("");
	        	    }
	    		}
        	}
        } else if (e.getSource() == deleteButton) {
        	if (files != null) {
	    		for (File file : files) {
	    			file.delete();
	    		}
		    	files = null;
	        	fotobestanden.setText("");
        	}
        }
    }

	public static void scale(String src, int width, int height, String dest) throws IOException {
		BufferedImage bsrc = ImageIO.read(new File(src));
		BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bdest.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
		g.drawRenderedImage(bsrc,at);
		ImageIO.write(bdest,"JPG",new File(dest));
	}
}
