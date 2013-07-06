package de.tud.mobsen.invite2meet.objects;

public class Place {
	private boolean systemEntry;
	private int id;
	private String name;
	private String photoUri;
	private String timestamp;
	private double latitude;
	private double longitude;
	private int timesUsed;

	/**
	 * Creates a System Entry
	 * @param id the type of system entry
	 * @param name the name to be displayed
	 */
	public Place(int id, String name) {
		this.systemEntry = true;
		this.id = id;
		this.name = name;
		
		this.photoUri = null;
		this.timestamp = null;
		this.latitude = Double.MIN_VALUE;
		this.longitude = Double.MIN_VALUE;
		this.timesUsed = -1;
	}
	
	/**
	 * 
	 * @param name
	 * @param photoUri
	 * @param timestamp
	 * @param latitude
	 * @param longitude
	 */
	public Place(String name, String photoUri, String timestamp, double latitude, double longitude) {
		this.systemEntry = false;
		
		this.id = Integer.MIN_VALUE;
		this.name = name;
		this.photoUri = photoUri;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timesUsed = 0;
	}
	
	public Place(int id, String name, String photoUri, String timestamp, double latitude, double longitude, int timesUsed) {
		this.systemEntry = false;
		
		this.id = id;
		this.name = name;
		this.photoUri = photoUri;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timesUsed = timesUsed;
	}
	
	public boolean isSystemEntry(){
		return systemEntry;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUri() {
		return photoUri;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getTimesUsed() {
		return timesUsed;
	}

	public void setTimesUsed(int timesUsed) {
		this.timesUsed = timesUsed;
	}
	
	/**
	 * Increments timesUsed by 1
	 */
	public void used() {
		timesUsed++;
	}

	public String getDisplayText() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Place [systemEntry=" + systemEntry + ", id=" + id + ", name="
				+ name + ", photoUri=" + photoUri + ", timestamp=" + timestamp
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", timesUsed=" + timesUsed + "]";
	}

}
