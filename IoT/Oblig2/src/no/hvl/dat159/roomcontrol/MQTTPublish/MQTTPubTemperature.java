package no.hvl.dat159.roomcontrol.MQTTPublish;

import no.hvl.dat159.roomcontrol.TemperatureSensor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTPubTemperature extends MQTTPub implements Runnable {

	private TemperatureSensor sensor;

	public MQTTPubTemperature(TemperatureSensor sensor) {
		super("Temp", 1, "MQTT_Temp");
		this.sensor = sensor;
	}

	private void publish() throws MqttException, InterruptedException {
		while (true) {

			String temp = String.valueOf(sensor.read());

			System.out.println("Publishing message: " + temp);

			MqttMessage message = new MqttMessage(temp.getBytes());
			message.setQos(getQos());

			getPublisherClient().publish(getTopic(), message);

			Thread.sleep(10000);
		}
	}

	@Override
	public void run() {
		try {
			connect();
			System.out.println("Sensor publisher running");
			publish();
			disconnect();
			System.out.println("Sensor publisher stopping");
		} catch (InterruptedException | MqttException e) {
			System.out.println("Sensor publisher: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
