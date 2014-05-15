package com.emiliao.elsecretodealbacete;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
	 	         	         
	 	         //lbAcertijo.setText(r); //Poner el contenido del QR en el texto del acertijo (temporal de prueba).
	 	         // Handle successful scan
	 	         
	 	        WSObtener tarea = new WSObtener();
				tarea.execute(result[0].toString());
	 	         
	 	         btMaps.setEnabled(true);
	 	         
	 	         /*Formato QRcode: NºQR/dir_actual
	 	         				resul[0]/resul[1]  
	 	         maps = new Intent(Intent.ACTION_VIEW,
	 	         Uri.parse("http://maps.google.com/maps?" 
	 	         + "saddr=" + dir_actual + "&daddr=" + dir_destino
	 	         + "&hl=es&sspn=0.001241,0.002642&geocode=FVnbUgId963j_w%3BFcnXUgIdharj_w&t=h&dirflg=w&mra=ls&z=19" ));

	 	         maps.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
	 	        		
	 	         startActivity(maps);*/
	 	        		
	 	      } else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	      }
	 	   }
	 	}
	
	//Tarea Asíncrona para llamar al WS de consulta en segundo plano
			private class WSObtener extends AsyncTask<String,Integer,Boolean> {
				
				private String id;
				private Double lat;
				private Double lon;
				private String acertijo;
				private int nEti;
				 
			    protected Boolean doInBackground(String... params) {
			    	
			    	boolean resul = true;
			    	id = params[0];
			    	
			    	HttpClient httpClient = new DefaultHttpClient();
					
					HttpGet del = 
							new HttpGet("http://elsecreto.somee.com/Api/Etiquetas/Etiqueta/" + id);
					
					del.setHeader("content-type", "application/json");
					
					try
			        {			
			        	HttpResponse resp = httpClient.execute(del);
			        	String respStr = EntityUtils.toString(resp.getEntity());
			        	
			        	JSONObject respJSON = new JSONObject(respStr);
			        	
			        	lat = respJSON.getDouble("Latitud");
			        	lon = respJSON.getDouble("Longitud");
			        	acertijo = respJSON.getString("Acertijo");
			        	nEti = respJSON.getInt("nEtiquetas");
			        }
			        catch(Exception ex)
			        {
			        	Toast.makeText(getApplicationContext(), "Fallo en la llamada", Toast.LENGTH_SHORT).show();
			        	//Log.e("ServicioRest","Error!", ex);
			        	resul = false;
			        }
			 
			        return resul;
			    }
			    
			    protected void onPostExecute(Boolean result) {
			    	
			    	if (result)
			    	{
			    		/*Formato QRcode: NºQR/dir_actual/dir_destino/Acertijo/(Nº de QR en el primer QR)
	         				resul[0]/resul[1]/resul[2]/resul[3]/resul[4]  */
				         maps = new Intent(Intent.ACTION_VIEW,				         
				         Uri.parse("https://www.google.com/maps/dir/"+dir_actual+"/"+lat+","+lon+"/@"+dir_actual+",17z/data=!3m1!4b1!4m4!4m3!1m0!1m0!3e2?hl=es" ));
			
				         //Sirve para antiguo maps, ya que son data Uri distintos a partir de la versión 7.0 .
				         /*Uri.parse("http://maps.google.com/maps?" 
				         + "saddr=" + dir_actual + "&daddr=" + lat.toString() + "," + lon.toString()
				         + "&hl=es&sspn=0.001241,0.002642&geocode=FVnbUgId963j_w%3BFcnXUgIdharj_w&t=h&dirflg=w&mra=ls&z=19" ));*/
				         
				         maps.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
				        		
				         lbAcertijo.setText(acertijo+"\n"+lat+"\n"+lon+"\n"+nEti);
				         startActivity(maps);
			    		
			    	}
			    }
			}
}
