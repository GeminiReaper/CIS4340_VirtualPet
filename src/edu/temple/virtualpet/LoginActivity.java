package edu.temple.virtualpet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	Button btnCreateAccountAsk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btnCreateAccountAsk = (Button)findViewById(R.id.btnCreateAccountAsk);
		
		btnCreateAccountAsk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
				startActivity(intent);
			}
		});
	}
}
