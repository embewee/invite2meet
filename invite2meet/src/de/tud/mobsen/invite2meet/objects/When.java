package de.tud.mobsen.invite2meet.objects;

public class When {
	private String displayText;
	private String key;
	
	public When(String key, String displayText) {
		this.key = key;
		this.displayText = displayText;
	}

	public String getDisplayText() {
		return displayText;
	}

	public String getKey() {
		return key;
	}	
}
