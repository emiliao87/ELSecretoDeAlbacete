package com.emiliao.elsecretodealbacete;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends Activity {
	
	private Button btAceptar, btCancelar;
	private EditText usuario, password1, password2, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		
		usuario = (EditText) findViewById(R.id.txtUsuario);
		email = (EditText) findViewById(R.id.txtEmail);
		
		password1 = (EditText) findViewById(R.id.txtPassword1);
		password2 = (EditText) findViewById(R.id.txtPassword2);
		
		btAceptar = (Button) findViewById(R.id.btAceptarReg);
		btCancelar = (Button) findViewById(R.id.btCancelarReg);
		
		/**
		 * Método del botón Aceptar, que pasa otra vez a la Actvity Login si los datos
		 * de registro son correctos y se registran exitósamente.
		 * 
		 */
		btAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(password1.getText().toString().equals(password2.getText().toString())){
					
					WSInsertar tarea = new WSInsertar();
					tarea.execute(usuario.getText().toString(),	email.getText().toString(),	password1.getText().toString());
					
					//Acepta el registro.
					lanzarAceptar(null);
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(),
				                    "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT);
				    toast.show();
				}
			}
		});
		
		/**
		 * Cierra la actividad actual.
		 */
		btCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}
	
	public void lanzarAceptar(View view){
		Intent i = new Intent(this, LoginActivity.class);
		finish();
		startActivity(i);
	}
	
	//Tarea Asíncrona para llamar al WS de inserción en segundo plano
		private class WSInsertar extends AsyncTask<String,Integer,Boolean> {
			
		    protected Boolean doInBackground(String... params) {
		    	
		    	boolean resul = true;
		    	
		    	HttpClient httpClient = new DefaultHttpClient();
		        
		    	//Cambiar segun el web service
				HttpPost post = new HttpPost("http://ElSecretoDeAlbacete.somee.com/Api/Usuarios/Usuario");
				post.setHeader("content-type", "application/json");
				
				try
		        {
					//Construimos el objeto usuario en formato JSON
					JSONObject dato = new JSONObject();
					
					dato.put("usuario", params[0]);
					dato.put("email", params[1]);
					dato.put("password", params[2]);
					
					StringEntity entity = new StringEntity(dato.toString());
					post.setEntity(entity);
					
		        	HttpResponse resp = httpClient.execute(post);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	if(!respStr.equals("true"))
		        		resul = false;
		        }
		        catch(Exception ex)
		        {
		        	Log.e("ServicioRest","Error!", ex);
		        	resul = false;
		        }
		 
		        return resul;
		    }
		    
		    protected void onPostExecute(Boolean result) {
		    	
		    	if (result)
		    	{
		    		Toast insertado = Toast.makeText(getApplicationContext(), "Insertado", Toast.LENGTH_SHORT);
		    		insertado.show();
		    	}
		    }
		}

}
