package com.emiliao.elsecretodealbacete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity{
	
	private Button btCancelar; 

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//lanzarCancelar(null);
				finish(); //Uso finish para matar la actividad actual y volver a la anterior.
			}
		});
	}
	
//	public void lanzarCancelar(View view){
//		Intent i = new Intent(this, MainActivity.class);
//		finish();
//		startActivity(i);
//	}
}
