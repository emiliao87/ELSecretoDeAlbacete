package com.emiliao.elsecretodealbacete;

import android.app.Activity;
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
		
		btAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(password1.getText().equals(password2.getText())){
					//Acepta el registro.
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(),
				                    "Las contraseñas no coinciden", Toast.LENGTH_SHORT);
				    toast.show();
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

}
