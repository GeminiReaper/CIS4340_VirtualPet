package edu.temple.virtualpet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CreateAccountActivity extends Activity {
	
	Button btnLoginAsk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		btnLoginAsk = (Button)findViewById(R.id.btnLoginAsk);
		
		btnLoginAsk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
	}
}
