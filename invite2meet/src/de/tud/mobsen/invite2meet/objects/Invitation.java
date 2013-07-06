package de.tud.mobsen.invite2meet.objects;

import java.security.Timestamp;

import android.graphics.Picture;
import android.location.Location;

public class Invitation {

	private String inviterName;
	private Location inviterLocation;

	private Timestamp creationTime;

	private String inviteeName;

	private int meetingOffsetMinutes;
	private String meetingPlaceName;
	private Location meetingPlaceLocation;
	private Picture meetingPlaceImage;

	public Invitation() {
		meetingOffsetMinutes = -1;
	}

	public String getInviterName() {
		return inviterName;
	}

	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}

	public Location getInviterLocation() {
		return inviterLocation;
	}

	public void setInviterLocation(Location inviterLocation) {
		this.inviterLocation = inviterLocation;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
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

	public Location getMeetingPlaceLocation() {
		return meetingPlaceLocation;
	}

	public void setMeetingPlaceLocation(Location meetingPlaceLocation) {
		this.meetingPlaceLocation = meetingPlaceLocation;
	}

	public Picture getMeetingPlaceImage() {
		return meetingPlaceImage;
	}

	public void setMeetingPlaceImage(Picture meetingPlaceImage) {
		this.meetingPlaceImage = meetingPlaceImage;
	}

	public boolean isValid() {
		boolean valid = (inviterName != null) && (inviterLocation != null)
				&& (creationTime != null) && (inviteeName != null)
				&& (meetingOffsetMinutes >= 0) && (meetingPlaceName != null)
				&& (meetingPlaceLocation != null)
				&& (meetingPlaceImage != null);
		return valid;
	}

}
