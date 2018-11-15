package no.hvl.dat159.roomcontrol.MQTTPublish;

import org.eclipse.paho.client.mqttv3.*;

public class MQTTPubHeat extends MQTTPub {

	public MQTTPubHeat() {
		super("Heat", 1, "MQTT_Heat");
		connect();
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
