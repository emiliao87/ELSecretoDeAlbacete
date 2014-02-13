package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends Activity {
	
	private Button btAceptar, btCancelar;
	private EditText Nombre, Apellido, password1, password2, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		
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
					//Acepta el registro.
					lanzarAceptar(null);
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(),
				                    "Las contraseñas no coinciden.", Toast.LENGTH_SHORT);
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

}
