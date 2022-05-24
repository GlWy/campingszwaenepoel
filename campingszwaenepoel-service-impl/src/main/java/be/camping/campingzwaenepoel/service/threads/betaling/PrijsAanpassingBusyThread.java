package be.camping.campingzwaenepoel.service.threads.betaling;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;

import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.foto.FotoService;
import be.camping.campingzwaenepoel.service.impl.FotoServiceImpl;

public class PrijsAanpassingBusyThread implements Runnable {

	private Thread t;
	private List<JComponent> components;
	private Color colorBusy = Color.blue;
	
	private StandplaatsService standplaatsService;

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}

	public PrijsAanpassingBusyThread(List<JComponent> components, StandplaatsService standplaatsService) {
		super();
		this.components = components;
		this.standplaatsService = standplaatsService;
		t = new Thread(this);
	    t.start();
	}
	
	@Override
	public void run() {
		System.err.println("Enter busy thread");
		while (getStandplaatsService().isResetBetalingBusy()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < components.size(); i++) {
			JComponent jComponent = components.get(i);
			jComponent.setEnabled(true);
		}
		System.err.println("Quit busy thread");
	}

}
