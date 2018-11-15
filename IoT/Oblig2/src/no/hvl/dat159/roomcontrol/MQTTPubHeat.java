package no.hvl.dat159.roomcontrol;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTPubHeat extends MQTTPub {

	public MQTTPubHeat() {
		super("Heat", 1, "MQTT_Heat");
	}

	public void publish(String heatState) {
		System.out.println("Publishing message: " + heatState);

		MqttMessage message = new MqttMessage(heatState.getBytes());

		message.setQos(getQos());
		try {
			getPublisherClient().publish(getTopic(), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
