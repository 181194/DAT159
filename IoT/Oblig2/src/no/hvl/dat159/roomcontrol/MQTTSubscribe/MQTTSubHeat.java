package no.hvl.dat159.roomcontrol.MQTTSubscribe;

import no.hvl.dat159.roomcontrol.Heating;
import org.eclipse.paho.client.mqttv3.*;

public class MQTTSubHeat extends MQTTSub implements MqttCallback, Runnable {

	private Heating heating;

	public MQTTSubHeat(Heating heating) throws MqttException {
		super("Heat", "MQTT_Heat_SUB");
		this.heating = heating;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {
		String state = new String(message.getPayload());
		if (state.equals("ON"))
			heating.write(true);

		if (state.equals("OFF"))
			heating.write(false);
	}

	@Override
	public void run() {
		try {
			System.out.println("Connecting to broker: " + getBroker());
			connect();

			String topic = "Heat";
			int qos = 1;

			getClient().subscribe(topic, qos);
			System.out.println("Subscribed to topic: " + topic);
		} catch (MqttException e) {
			System.out.println("Disconnecting...");
			disconnect();
			e.printStackTrace();
		}
	}

}
