package no.hvl.dat159.roomcontrol;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTSubTemperature extends MQTTSub {

	private MQTTPubHeat pubHeating;

	public MQTTSubTemperature(MQTTPubHeat pubHeating) throws MqttException {
		super("Temp", "MQTT_Temperature_SUB");
		this.pubHeating = pubHeating;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {
		try {
			double temp = Double.parseDouble(new String(message.getPayload()));
			pubHeating.publish(temp < 20 ? "ON" : "OFF");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
