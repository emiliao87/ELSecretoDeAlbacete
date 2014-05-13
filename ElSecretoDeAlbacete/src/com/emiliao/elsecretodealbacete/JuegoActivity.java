package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JuegoActivity extends Activity{
	
	private Button btMaps;
	private Button btSeguir;
	private TextView lbAcertijo;
	private String[] result;
	private Intent maps = null;
	
	private String dir_actual, dir_destino;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		
		btMaps = (Button) findViewById(R.id.btMaps);
		btSeguir = (Button) findViewById(R.id.btSeguir);
		lbAcertijo = (TextView) findViewById(R.id.lbAcertijo);
		
		btMaps.setEnabled(false);
		
		btMaps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				lanzarMaps(null);
			}
		});
		
		btSeguir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				Intent i = new Intent("com.google.zxing.client.android.SCAN");
				i.putExtra("SCAN MODE", "QR_CODE_MODE");
				startActivityForResult(i,0);
				}
				catch(Exception e){
					Intent i = new Intent(android.content.Intent.ACTION_VIEW);
					i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.zxing.client.android&hl=es"));
					Toast.makeText(getApplicationContext(), "¡Necesitas un escaner QR para usar esta opción!", Toast.LENGTH_LONG).show();
					startActivity(i);
				}
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
		//Intent i = new Intent(this, MapsActivity.class);
		//startActivity(i);
		
		startActivity(maps);
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
	 	         
	 	         dir_actual = result[1];
	 	         dir_destino = result[2];
	 	    	 	         
	 	         lbAcertijo.setText(r); //Poner el contenido del QR en el texto del acertijo (temporal de prueba).
	 	         // Handle successful scan
	 	         
	 	         btMaps.setEnabled(true);
	 	         
	 	         /*Formato QRcode: NºQR/dir_actual/dir_destino/Acertijo/(Nº de QR en el primer QR)
	 	         				resul[0]/resul[1]/resul[2]/resul[3]/resul[4]  */
	 	         maps = new Intent(Intent.ACTION_VIEW,
	 	         Uri.parse("http://maps.google.com/maps?" 
	 	         + "saddr=" + dir_actual + "&daddr=" + dir_destino
	 	         + "&hl=es&sspn=0.001241,0.002642&geocode=FVnbUgId963j_w%3BFcnXUgIdharj_w&t=h&dirflg=w&mra=ls&z=19" ));

	 	         maps.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
	 	        		
	 	         startActivity(maps);
	 	        		
	 	      } else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	      }
	 	   }
	 	}
}
