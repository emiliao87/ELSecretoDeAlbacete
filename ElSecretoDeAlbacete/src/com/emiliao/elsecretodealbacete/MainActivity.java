package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btJugar;
	private Button btSalir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		btJugar = (Button) findViewById(R.id.btJugar);
		btJugar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				lanzarJugar(null);				
			}
		});
		
		btSalir = (Button) findViewById(R.id.btSalir);
		btSalir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void lanzarJugar(View view){
		Intent i = new Intent(this,Login.class);
		startActivity(i);
		
	}
	
	

}
