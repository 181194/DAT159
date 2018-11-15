package no.hvl.dat159.roomcontrol;

import no.hvl.dat159.roomcontrol.MQTTSubscribe.MQTTSubDisplay;

public class Display {

	public void write(String message) {
		System.out.println("DISPLAY: " + message);
	}

	public static void main (String[] args) {
		Display display = new Display();

		try {
			Thread t1 = new Thread(new MQTTSubDisplay(display));
			t1.start();
			t1.join();
		} catch (Exception e ) {
			System.out.println("Display: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
