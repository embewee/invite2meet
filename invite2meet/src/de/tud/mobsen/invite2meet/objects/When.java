package de.tud.mobsen.invite2meet.objects;

public class When {
	private String displayText;
	private String key;
	private int offsetInMinutes;
	
	public When(String key, String displayText, int offsetInMinutes) {
		this.key = key;
		this.displayText = displayText;
	}

	public String getDisplayText() {
		return displayText;
	}

	public String getKey() {
		return key;
	}
	
	public int getOffsetInMinutes() {
		return offsetInMinutes;
	}
}
