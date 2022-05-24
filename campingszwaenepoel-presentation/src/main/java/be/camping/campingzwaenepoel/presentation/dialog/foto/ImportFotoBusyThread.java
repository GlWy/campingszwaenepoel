package be.camping.campingzwaenepoel.presentation.dialog.foto;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;

import be.camping.campingzwaenepoel.service.foto.FotoService;
import be.camping.campingzwaenepoel.service.impl.FotoServiceImpl;

public class ImportFotoBusyThread implements Runnable {

	private Thread t;
	private List<JComponent> components;
	private Color colorBusy = Color.blue;
	
	private FotoService fotoService = new FotoServiceImpl();
	
	public FotoService getFotoService() {
		return fotoService;
	}

	public void setFotoService(FotoService fotoService) {
		this.fotoService = fotoService;
	}

	public ImportFotoBusyThread(List<JComponent> components) {
		super();
		this.components = components;
		t = new Thread(this);
	    t.start();
	}
	
	@Override
	public void run() {
		for (JComponent jComponent : components) {
			jComponent.setForeground(colorBusy);
		}
		while (getFotoService().isImportBusy()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (JComponent jComponent : components) {
			jComponent.setForeground(null);
		}
		JComponent jComponent = components.get(components.size() - 1);
		jComponent.setEnabled(true);
	}

}
