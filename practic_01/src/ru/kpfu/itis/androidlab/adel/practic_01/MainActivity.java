package ru.kpfu.itis.androidlab.adel.practic_01;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener {

	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private double latitude;
	private double longitude;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private final String TAG = "myLogs";

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		buildGoogleApiClient();
		onConnected(null);

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		if (map == null) {
			finish();
			return;
		}
		init();
	}

	private void init() {
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {
				Log.d(TAG, "onMapClick: " + latLng.latitude + ","
						+ latLng.longitude);
			}
		});

		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng) {
				Log.d(TAG, "onMapLongClick: " + latLng.latitude + ","
						+ latLng.longitude);
			}
		});

		map.setOnCameraChangeListener(new OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition camera) {
				Log.d(TAG, "onCameraChange: " + camera.target.latitude + ","
						+ camera.target.longitude);
			}
		});
	}

	public void onClickTest(View view) {
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {
			latitude = mLastLocation.getLatitude();
			longitude = mLastLocation.getLongitude();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}
}
