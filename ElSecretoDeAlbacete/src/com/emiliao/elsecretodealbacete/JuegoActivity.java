package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JuegoActivity extends Activity{
	
	private Button btMaps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		
		btMaps = (Button) findViewById(R.id.btMaps);
		
		btMaps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				lanzarMaps(null);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juego, menu); // Me falta crearlo para meter las opciones pensadas.
		return true;
	}
	
	public void lanzarMaps(View view){
		Intent i = new Intent(this, MapsActivity.class);
		
		startActivity(i);
	}
	
}
