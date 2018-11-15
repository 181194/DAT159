package no.hvl.dat159.roomcontrol.MQTTSubscribe;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

abstract class MQTTSub implements MqttCallback, Runnable {

	private String message;
	private String topic;
	private int qos;
	private String broker = "tcp://m23.cloudmqtt.com:11264";
	private String clientId;
	private String username = "gfymzmjf";
	private String password = "BE_Bv2oANPIT";

	private MqttClient client;

	public MQTTSub(String topic, String clientId) throws MqttException {
		qos = 1;
		this.topic = topic;
		this.clientId = clientId;
	}

	public void connect() {
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		connOpts.setUserName(username);
		connOpts.setPassword(password.toCharArray());

		try {
			client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
			client.setCallback(this);
			client.connect(connOpts);
		} catch (MqttException e) {
			e.printStackTrace();
		}

		System.out.println("Connected");
	}

	public void disconnect() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see MqttCallback#connectionLost(Throwable)
	 */
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost because: " + cause);
		System.exit(1);

	}

	/**
	 * @see MqttCallback#messageArrived(String, MqttMessage)
	 */
	public abstract void messageArrived(String topic, MqttMessage message) throws Exception;

	/**
	 * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		try {
			System.out.println("Connecting to broker: " + getBroker());
			connect();

			getClient().subscribe(topic, qos);
			System.out.println("Subscribed to topic: " + topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public MqttClient getClient() {
		return client;
	}

	public String getBroker() {
		return broker;
	}
}
