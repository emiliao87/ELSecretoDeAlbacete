package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity{ /*implements OnClickListener{*/
	
	private Button btCancelar, btAceptar, btRegistro;
	private View view;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btAceptar = (Button) findViewById(R.id.btAceptar);
		btRegistro = (Button) findViewById(R.id.btRegistro);
		
		/*btAceptar.setOnClickListener(this);
		btCancelar.setOnClickListener(this);
		btRegistro.setOnClickListener(this);*/
		
		btAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				lanzarAceptar(null);
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
		finish();
		startActivity(i);
	}
	
	public void lanzarRegistro(View view){
		Intent i = new Intent(this, RegistroActivity.class);
		startActivity(i);
	}
	}

	/*@Override
	public void onClick(View v) {
		Intent i;
		switch(view.getId()){
		case R.id.btAceptarReg:
			i = new Intent(this, JuegoActivity.class);
			finish();
			startActivity(i);
			break;
		case R.id.btCancelarReg:
			finish();
			break;
		case R.id.btRegistro:
			i = new Intent(this, RegistroActivity.class);
			finish();
			startActivity(i);
			break;
		}
		
	}*/
