package no.hvl.dat159.roomcontrol;

public class TemperatureSensor implements Runnable{

	private Room room;

	public TemperatureSensor(Room room) {

		this.room = room;
	}

	@Override
	public void run() {
		//MQTTPubTemperature mqtt = new MQTTPubTemperature(this);
		//mqtt.run();
	}

	public double read() {

		return room.sense();
	}

}
