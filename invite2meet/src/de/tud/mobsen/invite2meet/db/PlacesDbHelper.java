package de.tud.mobsen.invite2meet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author based on: wsn, WiFun5
 *
 */
public class PlacesDbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "invite2meet";
	private static final String TABLE_NAME = "places";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
	
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_TIMESTAMP = "timestamp";
	public static final String KEY_PHOTO_URI = "photouri";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "logitude";
	public static final String KEY_TIMES_USED = "timesused";
	
	public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " +
			KEY_TIMESTAMP + " TEXT, " + KEY_PHOTO_URI + " TEXT, " + KEY_TIMES_USED + " INTEGER, " + KEY_LATITUDE + " REAL, " + KEY_LONGITUDE + " REAL);";

	public PlacesDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
