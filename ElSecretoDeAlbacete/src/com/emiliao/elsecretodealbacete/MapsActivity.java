package com.emiliao.elsecretodealbacete;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

	private GoogleMap mapa = null;
	private int vista = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		
		mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.gMaps)).getMap();
		mostrarMarcador(40.431269, -3.703187);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}
	
	private void alternarVista(){
		vista = (vista + 1)%4;
		
		switch(vista){
			case 0:
				mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	            break;
	        case 1:
	            mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	            break;
	        case 2:
	            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	            break;
	        case 3:
	            mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	            break;
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{	
		switch(item.getItemId())
		{
			case R.id.menu_vista:
				alternarVista();
				break;
			case R.id.menu_mover:
				//Centramos el mapa en España
				CameraUpdate camUpd1 = 
					CameraUpdateFactory.newLatLng(new LatLng(40.41, -3.69));
				mapa.moveCamera(camUpd1);
				break;
			case R.id.menu_animar:
				//Centramos el mapa en España y con nivel de zoom 5
				CameraUpdate camUpd2 = 
					CameraUpdateFactory.newLatLngZoom(new LatLng(40.41, -3.69), 5F);
				mapa.animateCamera(camUpd2);
				break;
			case R.id.menu_3d:
				LatLng madrid = new LatLng(40.417325, -3.683081);
				CameraPosition camPos = new CameraPosition.Builder()
					    .target(madrid)   //Centramos el mapa en Madrid
					    .zoom(19)         //Establecemos el zoom en 19
					    .bearing(45)      //Establecemos la orientación con el noreste arriba
					    .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
					    .build();

				CameraUpdate camUpd3 = 
						CameraUpdateFactory.newCameraPosition(camPos);

				mapa.animateCamera(camUpd3);
				break;
			case R.id.menu_posicion:
				CameraPosition camPos2 = mapa.getCameraPosition();
				LatLng pos = camPos2.target;
				Toast.makeText(MapsActivity.this, 
						"Lat: " + pos.latitude + " - Lng: " + pos.longitude, 
						Toast.LENGTH_LONG).show();
				break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void mostrarMarcador(double lat, double lng)
	{
	    mapa.addMarker(new MarkerOptions()
	        .position(new LatLng(lat, lng))
	        .title("Pais: España")
	        .snippet("Marcador ubicado en Madrid"));
	}

}
