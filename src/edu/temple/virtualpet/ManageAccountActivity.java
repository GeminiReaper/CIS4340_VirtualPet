package edu.temple.virtualpet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class ManageAccountActivity extends Activity {
	
	Button deleteAcct;
	Button createAcct;
	EditText userName;
	EditText email;
	EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_account);
		
		deleteAcct = (Button) findViewById(R.id.btnDeleteAccount);
		createAcct = (Button) findViewById(R.id.btnCreateAccount);
		userName = (EditText) findViewById(R.id.txtUsername);
		password = (EditText) findViewById(R.id.txtPassword);
		email = (EditText) findViewById(R.id.txtEmail);
		
	}//end onCreate
}//end class ManageAccountActivity
