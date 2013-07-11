package de.tud.mobsen.invite2meet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import de.tud.mobsen.invite2meet.db.PlacesDbHelper;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewPlaceFragment extends DialogFragment {

	private final static String tag = "NewPlaceFragment";
	private final static int CAPTURE_IMAGE_REQUEST_CODE = 100;
	
	private ProgressDialog progressDialog;	
	
	private LocationManager locationManager;
	private LocationListener locListener;
	
	private TextView txtLatitude;
	private TextView txtLongitude;
	private Button btnSave;
	private ImageView picture;
	private EditText nameTextField;
	
	private File imageFile = null;
	
	private String photoUri;
	private double longitude;
	private double latitude;
	private String name;
	private String timestamp;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_new_place, container, false);
		
		getDialog().setTitle("Add Place"); //TODO: id
		
		//Textview latitude
		txtLatitude = (TextView) fragView.findViewById(R.id.newplace_lat);
		
		//Textview longitude
		txtLongitude = (TextView) fragView.findViewById(R.id.newplace_long);
		
		//EditText
		nameTextField = (EditText) fragView.findViewById(R.id.newplace_name);
		
		//Save button
		btnSave = (Button) fragView.findViewById(R.id.newplace_save);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSaveNewPlace();
			}
		});
		
		//picture field
		picture = (ImageView) fragView.findViewById(R.id.newplace_picture);
		picture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
		
		//start getting location
		getLocation();
		
		return fragView;
	}
	
	/**
	 * @author Based on: http://developer.android.com/guide/topics/media/camera.html
	 */
	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		File file = getOutputMediaUri(); // create a file to save the image
		imageFile = file;
		Uri imageFileUri = Uri.fromFile(imageFile);
		Log.i(tag, imageFileUri.toString());
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri); // set the image file name
		startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	/**
	 * @author Based on: http://developer.android.com/guide/topics/media/camera.html
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
	        if (resultCode == Activity.RESULT_OK) {
	        	saveAndShowImage();
	        	
	        } else if (resultCode == Activity.RESULT_CANCELED) {
	        	//Toast.makeText(getActivity(), "User cancelled action", Toast.LENGTH_SHORT).show();
	        	
	        } else {
	        	Toast.makeText(getActivity(), "Could not take image", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	/**
	 * @author Based on: http://stackoverflow.com/questions/4181774/show-image-view-from-file-path-in-android
	 * @author Based on: http://stackoverflow.com/questions/16060143/android-take-photo-and-resize-it-before-saving-on-sd-card
	 * @param view
	 */
	private void saveAndShowImage(){
		if(imageFile.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		    
		    boolean deleted = imageFile.delete();
		    Log.i(tag, "Deleted: " + deleted);
		    
		    Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 300, 300, false); //TODO: SIZE!!!
		    
		    picture.setImageBitmap(resized);
		    
		    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
            
            //File f = new File(Environment.getExternalStorageDirectory() + File.separator + "Imagename.jpg");
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constants.APPNAME);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMAN).format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
            
            imageFile = mediaFile;
            
            try {
            	mediaFile.createNewFile();
				FileOutputStream fo = new FileOutputStream(mediaFile);
	            fo.write(bytes.toByteArray());
	            fo.close();
			} catch (IOException e) {
				Toast.makeText(getActivity(), "Could not load image: I/O exception", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getActivity(), "Could not load image: not found", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Create a File for saving an image or video
	 * @author Based on: http://developer.android.com/guide/topics/media/camera.html
	 * @param type
	 * @return
	 */
	private static File getOutputMediaUri(){
	    // TODO: To be safe, you should check that the SDCard is mounted using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constants.APPNAME);
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d(Constants.APPNAME, "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMAN).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
	    return mediaFile;
	}
	
	/**
	 * @author Based on: Wireless Sensor Networks Lab, WiFun6
	 * @author Based on: http://stackoverflow.com/questions/7979230/how-to-read-location-only-once-with-locationmanager-gps-and-network-provider-a
	 */
	private void getLocation() {
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		locListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				newLocation(location);
			}

			@Override
			public void onProviderDisabled(String provider) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locListener);
		//String locationProvider = LocationManager.NETWORK_PROVIDER;
		//locationManager.requestSingleUpdate(locationProvider, locListener, );
		//return locationManager.getLastKnownLocation(locationProvider);
		Toast.makeText(getActivity(), "Waiting for location...", Toast.LENGTH_SHORT).show();
	}
	
	private void newLocation(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
		txtLatitude.setText(Double.toString(location.getLatitude()));
		txtLongitude.setText(Double.toString(location.getLongitude()));
		
		//TODO: warten, bis beste Lokation gefunden wurde
	}
	
	/**
	 * @author based on: wsn
	 */
	public void onSaveNewPlace() {
		Log.i(tag, "SAVE");

		//@author based on: wsn
		SimpleDateFormat dateFormat = new SimpleDateFormat(PlacesDbHelper.DATE_FORMAT, Locale.GERMANY);
		Date date = new Date();
		
		timestamp = dateFormat.format(date);
		name = nameTextField.getText().toString();
		photoUri = imageFile.getAbsolutePath();

		if(isPlaceValid()) {
			//@author based on: wsn
			progressDialog = ProgressDialog.show(getActivity(), "Saving to database", "Please wait...", true);
			new SavePlaceToDatabase().execute();
			locationManager.removeUpdates(locListener);	
			
		} else {
			Toast.makeText(getActivity(), "Check name, photo, location", Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean isPlaceValid() {
		
		//TODO:
		Toast.makeText(getActivity(), "USING FAKE LOCATION", Toast.LENGTH_LONG).show();
		
		return ((name != null) &&
				(!name.isEmpty()) &&
				//(latitude > Double.MIN_VALUE) &&
				//(longitude > Double.MIN_VALUE) &&
				(photoUri != null) &&
				(!photoUri.isEmpty()));
	}
	
	/**
	 * Member class which is used for a background database save operation of
	 * the current Wi-Fi scan results.
	 * @author based on: wsn
	 */
	private class SavePlaceToDatabase extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... arg0) {
			PlacesDbHelper databaseHelper = new PlacesDbHelper(getActivity());
			SQLiteDatabase database = databaseHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(PlacesDbHelper.KEY_NAME, name);
			values.put(PlacesDbHelper.KEY_TIMESTAMP, timestamp);
			values.put(PlacesDbHelper.KEY_TIMES_USED, 0);
			values.put(PlacesDbHelper.KEY_PHOTO_URI, photoUri);
			values.put(PlacesDbHelper.KEY_LONGITUDE, longitude);
			values.put(PlacesDbHelper.KEY_LATITUDE, latitude);
			
			long rowId = database.insert(PlacesDbHelper.TABLE_NAME, null, values);

			Log.i(tag, "RowId: " + Long.toString(rowId));
			
			
			Cursor tmp = database.rawQuery("SELECT COUNT(*) FROM " + PlacesDbHelper.TABLE_NAME, null);
			Log.i(tag, "COUNT NACH EINFUEGEN: " + tmp.getCount());
			
			
			
			database.close();
			
			if(rowId < 0) {
				return "Could not save place";
			} else {
				return "Place successfully saved";
			}
		}
		
		@Override
		protected void onPostExecute(String message) {
			progressDialog.dismiss();
			getDialog().dismiss();
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}
}
