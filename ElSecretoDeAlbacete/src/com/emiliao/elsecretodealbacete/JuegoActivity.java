package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JuegoActivity extends Activity{
	
	private Button btMaps;
	private Button btSeguir;
	private TextView lbAcertijo;
	private String[] result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		
		btMaps = (Button) findViewById(R.id.btMaps);
		btSeguir = (Button) findViewById(R.id.btSeguir);
		lbAcertijo = (TextView) findViewById(R.id.lbAcertijo);
		
		btMaps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				lanzarMaps(null);
			}
		});
		
		btSeguir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.google.zxing.client.android.SCAN");
				i.putExtra("SCAN MODE", "QR_CODE_MODE");
				startActivityForResult(i,0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juego, menu); // Me falta crear el menú para meter las opciones pensadas.
		return true;
	}
	
	public void lanzarMaps(View view){
		Intent i = new Intent(this, MapsActivity.class);
		startActivity(i);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	 	   if (requestCode == 0) {
	 	      if (resultCode == RESULT_OK) {
	 	         String contents = intent.getStringExtra("SCAN_RESULT");
	 	         String r="";
	 	         /**
	 	          * Divide el string resultado en campos determinados por "/", y cada
	 	          * campo lo mete en una posción del vector "result".
	 	          */
	 	         result = contents.split("/");
	 	         for(int i=0;i<result.length;i++){
	 	        	 r = r + result[i] + "\n";
	 	         }
	 	         lbAcertijo.setText(r); //Poner el contenido del QR en el texto del acertijo (temporal de prueba).
	 	         // Handle successful scan
	 	      } else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	      }
	 	   }
	 	}
	
}
