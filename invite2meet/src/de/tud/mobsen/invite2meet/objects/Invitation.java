package de.tud.mobsen.invite2meet.objects;

import android.graphics.Bitmap;

public class Invitation {

	private String inviterName;
	//private Location inviterLocation;
	private double inviterLatitude;
	private double inviterLongitude;
	
	private long sendingTime;

	private String inviteeName;

	private int meetingOffsetMinutes;
	private String meetingPlaceName;
	//private Location meetingPlaceLocation;
	private double meetingPlaceLatitude;
	private double meetingPlaceLongitude;
	private Bitmap meetingPlaceImage;

	public Invitation() {
		inviterLatitude = Double.MIN_VALUE;
		inviterLongitude = Double.MIN_VALUE;
		meetingPlaceLatitude = Double.MIN_VALUE;
		meetingPlaceLongitude = Double.MIN_VALUE;
		
		sendingTime = Long.MIN_VALUE;
		meetingOffsetMinutes = -1;
	}

	public String getInviterName() {
		return inviterName;
	}

	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}

	public long getSendingTime() {
		return sendingTime;
	}

	public void setSendingTime(long creationTime) {
		this.sendingTime = creationTime;
	}

	public String getInviteeName() {
		return inviteeName;
	}

	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}

	public int getMeetingOffsetMinutes() {
		return meetingOffsetMinutes;
	}

	public void setMeetingOffsetMinutes(int meetingOffsetMinutes) {
		this.meetingOffsetMinutes = meetingOffsetMinutes;
	}

	public String getMeetingPlaceName() {
		return meetingPlaceName;
	}

	public void setMeetingPlaceName(String meetingPlaceName) {
		this.meetingPlaceName = meetingPlaceName;
	}

	public Bitmap getMeetingPlaceImage() {
		return meetingPlaceImage;
	}

	public void setMeetingPlaceImage(Bitmap meetingPlaceImage) {
		this.meetingPlaceImage = meetingPlaceImage;
	}
	
	public double getInviterLatitude() {
		return inviterLatitude;
	}

	public void setInviterLatitude(double inviterLatitude) {
		this.inviterLatitude = inviterLatitude;
	}

	public double getInviterLongitude() {
		return inviterLongitude;
	}

	public void setInviterLongitude(double inviterLongitude) {
		this.inviterLongitude = inviterLongitude;
	}

	public double getMeetingPlaceLatitude() {
		return meetingPlaceLatitude;
	}

	public void setMeetingPlaceLatitude(double meetingPlaceLatitude) {
		this.meetingPlaceLatitude = meetingPlaceLatitude;
	}

	public double getMeetingPlaceLongitude() {
		return meetingPlaceLongitude;
	}

	public void setMeetingPlaceLongitude(double meetingPlaceLongitude) {
		this.meetingPlaceLongitude = meetingPlaceLongitude;
	}

	public boolean isValid() {
		boolean valid = (inviterName != null) 
				&& (inviterLatitude > Double.MIN_VALUE)
				&& (inviterLongitude > Double.MIN_VALUE)
				&& (sendingTime > Long.MIN_VALUE) && (inviteeName != null)
				&& (meetingOffsetMinutes >= 0) && (meetingPlaceName != null)
				&& (meetingPlaceLatitude > Double.MIN_VALUE)
				&& (meetingPlaceLongitude > Double.MIN_VALUE)
				&& (meetingPlaceImage != null);
		return valid;
	}

	@Override
	public String toString() {
		return "Invitation [inviterName=" + inviterName + ", inviterLatitude="
				+ inviterLatitude + ", inviterLongitude=" + inviterLongitude
				+ ", sendingTime=" + sendingTime + ", inviteeName="
				+ inviteeName + ", meetingOffsetMinutes="
				+ meetingOffsetMinutes + ", meetingPlaceName="
				+ meetingPlaceName + ", meetingPlaceLatitude="
				+ meetingPlaceLatitude + ", meetingPlaceLongitude="
				+ meetingPlaceLongitude + ", meetingPlaceImage="
				+ meetingPlaceImage + "]";
	}

	
}
