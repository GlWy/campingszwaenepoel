package be.camping.campingzwaenepoel.presentation.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Dimension dim;
	private Dimension calculatedDim;

    public ImagePanel(BufferedImage image) {
        this.image = image;
    }

    public ImagePanel(BufferedImage image, Dimension dim) {
        this.image = image;
        this.dim = dim;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = (dim == null)?getWidth():dim.width;
        int h = (dim == null)?getHeight():dim.height;
        int iw = image.getWidth();
        int ih = image.getHeight();
        double xScale = (double)w/iw;
        double yScale = (double)h/ih;
        double scale = Math.min(xScale, yScale);    // scale to fit
                       //Math.max(xScale, yScale);  // scale to fill
        int width = (int)(scale*iw);
        int height = (int)(scale*ih);
        int x = (w - width)/2;
        int y = (h - height)/2;
        g2.drawImage(image, x, y, width, height, this);
    }

    public Dimension calculateDimension() {
        int w = (dim == null)?getWidth():dim.width;
        int h = (dim == null)?getHeight():dim.height;
        int iw = image.getWidth();
        int ih = image.getHeight();
        double xScale = (double)w/iw;
        double yScale = (double)h/ih;
        double scale = Math.min(xScale, yScale);    // scale to fit
                       //Math.max(xScale, yScale);  // scale to fill
        int width = (int)(scale*iw);
        int height = (int)(scale*ih);
        return new Dimension(width, height);
    }
    
    public Dimension getDimension() {
    	return calculatedDim;
    }

}
