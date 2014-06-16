package com.emiliao.elsecretodealbacete;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity{ /*implements OnClickListener{*/
	
	private Button btCancelar, btAceptar, btRegistro;
	private View view;
	private EditText usu, pass;
	private ProgressBar pb;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btAceptar = (Button) findViewById(R.id.btAceptar);
		btRegistro = (Button) findViewById(R.id.btRegistro);
		
		usu = (EditText) findViewById(R.id.txtLoginUsuario);
		pass = (EditText) findViewById(R.id.txtPassword);
		
		pb = (ProgressBar) findViewById(R.id.progressLogin);
		
		btAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
					WSObtener tarea = new WSObtener();
										
					tarea.execute(usu.getText().toString(), pass.getText().toString());
					pb.setVisibility(0);
			}
		});
		
		btCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish(); //Uso finish para matar la actividad actual y volver a la anterior.
			}
		});
		
		btRegistro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lanzarRegistro(null);				
			}
		});
	}
	
	public void lanzarAceptar(View view){
		Intent i = new Intent(this, JuegoActivity.class);
		pb.setVisibility(4);
		finish();
		startActivity(i);
	}
	
	public void lanzarRegistro(View view){
		Intent i = new Intent(this, RegistroActivity.class);
		finish();
		startActivity(i);
	}
	
	//Tarea Asíncrona para llamar al WS de consulta en segundo plano
		private class WSObtener extends AsyncTask<String,Integer,Boolean> {
			
			private String id;
			private String _id;
			private String usuario;
			private String _usuario;
			private String password;
			private String _password;
			 
		    protected Boolean doInBackground(String... params) {
		    	
		    	boolean resul = true;
		    	
		    	HttpClient httpClient = new DefaultHttpClient();
		    	
				_id = MD5.hash(params[0]);
				_password = MD5.hash(params[1]);
				_usuario = params[0];
				
				HttpGet del = 
						new HttpGet("http://elsecretode.somee.com/Api/Personas/Persona/" + _id);
				
				del.setHeader("content-type", "application/json");
				
				try
		        {			
		        	HttpResponse resp = httpClient.execute(del);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	JSONObject respJSON = new JSONObject(respStr);
		        	
		        	id = respJSON.getString("Id");
		        	usuario = respJSON.getString("Usuario");
		        	password = respJSON.getString("Password");
		        }
		        catch(Exception ex)
		        {
		        	//Toast.makeText(getApplicationContext(), "!El usuario no existe¡", Toast.LENGTH_SHORT).show();
		        	//Log.e("ServicioRest","Error!", ex);
		        	resul = false;
		        }
		 
		        return resul;
		    }
		    
		    protected void onPostExecute(Boolean result) {
		    	
		    	if (result)
		    	{
		    		if(usuario.equals(_usuario) && password.equals(_password)){
		    			lanzarAceptar(null);
		    		}
		    		else{
		    			pb.setVisibility(4);
		    			Toast.makeText(getApplicationContext(), "¡Usuario y contraseña no coinciden!", Toast.LENGTH_SHORT).show();
		    			usu.setText(_usuario);
		    			pass.setHint("Contraseña");
		    		}
		    	}
		    }
		}
}
