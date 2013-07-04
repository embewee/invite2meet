package de.tud.mobsen.invite2meet.objects;

public class Friend {
	
	private int id;
	private String name;
	
	public Friend(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	

}
