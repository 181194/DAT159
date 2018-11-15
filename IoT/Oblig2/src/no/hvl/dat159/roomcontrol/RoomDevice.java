package no.hvl.dat159.roomcontrol;

import org.eclipse.paho.client.mqttv3.MqttException;

public class RoomDevice {

	public static void main(String[] args) {
		
		Room room = new Room(20);

		try {
			TemperatureSensor sensor = new TemperatureSensor(room);
			MQTTPubTemperature pubSensor = new MQTTPubTemperature(sensor);

			Heating heater = new Heating(room);
			MQTTSubHeat subHeat = new MQTTSubHeat(heater);

			Thread tempPublisher = new Thread(pubSensor);
			Thread heatSubsrciber = new Thread(subHeat);

			tempPublisher.start();
			heatSubsrciber.start();

			tempPublisher.join();
			heatSubsrciber.join();
		} catch (Exception ex) {
			
			System.out.println("RoomDevice: " + ex.getMessage());
			ex.printStackTrace();
		}
		


	}

}
