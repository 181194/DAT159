package no.hvl.dat159.roomcontrol.MQTTPublish;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

abstract class MQTTPub {

	private String topic;
	private int qos;
	private String broker = "tcp://m23.cloudmqtt.com:11264";
	private String clientId;
	private String username = "gfymzmjf";
	private String password = "BE_Bv2oANPIT";

	private MqttClient publisherClient;

	public MQTTPub(String topic, int qos, String clientId) {
		this.topic = topic;
		this.qos = qos;
		this.clientId = clientId;
	}

	public void connect() {

		MemoryPersistence persistence = new MemoryPersistence();

		try {
			publisherClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(username);
			connOpts.setPassword(password.toCharArray());
			System.out.println("Connecting to broker: " + broker);
			publisherClient.connect(connOpts);
			System.out.println("Connected");

		} catch (MqttException e) {
			System.out.println("reason " + e.getReasonCode());
			System.out.println("msg " + e.getMessage());
			System.out.println("loc " + e.getLocalizedMessage());
			System.out.println("cause " + e.getCause());
			System.out.println("excep " + e);
			e.printStackTrace();
		}
	}

	public void disconnect() throws MqttException {

		publisherClient.disconnect();

	}

	MqttClient getPublisherClient() {
		return publisherClient;
	}

	public String getTopic() {
		return topic;
	}

	public int getQos() {
		return qos;
	}
}
