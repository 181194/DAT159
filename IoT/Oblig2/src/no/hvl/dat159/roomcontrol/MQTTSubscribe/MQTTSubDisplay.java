package no.hvl.dat159.roomcontrol.MQTTSubscribe;

import no.hvl.dat159.roomcontrol.Display;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Timestamp;

public class MQTTSubDisplay extends MQTTSub implements Runnable {

	private Display display;

	public MQTTSubDisplay(Display display) throws MqttException {
		super("Temp", "MQTT_Display_SUB");
		this.display = display;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		String tmp = "Time:\t" + time +
				"  Topic:\t" + topic +
				"  Message:\t" + new String(message.getPayload()) +
				"  QoS:\t" + message.getQos();

		display.write(tmp);
	}
}