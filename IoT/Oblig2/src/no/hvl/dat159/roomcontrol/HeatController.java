package no.hvl.dat159.roomcontrol;

import no.hvl.dat159.roomcontrol.MQTTPublish.MQTTPubHeat;
import no.hvl.dat159.roomcontrol.MQTTSubscribe.MQTTSubTemperature;

public class HeatController {

	public static void main(String[] args) {

		try {
			MQTTPubHeat pubHeat = new MQTTPubHeat();

			MQTTSubTemperature subTemp = new MQTTSubTemperature(pubHeat);

			Thread sensor_t = new Thread(subTemp);

			sensor_t.start();
			
			sensor_t.join();
			
		} catch (Exception ex) {
			System.out.println("HeatController: " + ex.getMessage());
			ex.printStackTrace();
		}
		


	}

}
