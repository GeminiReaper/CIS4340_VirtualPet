package edu.temple.virtualpet;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		
		createAcct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Thread updateUserInfo = new Thread() {
					
					public void run() {
						try {
							String noNetwork = "No Available Network";
							Message msg = Message.obtain();
							if(!(Utility.isNetworkAvailable(ManageAccountActivity.this))) {
								msg.obj = noNetwork;
								toastMsg.sendMessage(msg);
							} 
						
						} catch (Exception e) {
							e.printStackTrace();;
						}
						
						
						
					}//end run
					
				};//end udateUserInfo Thread
				
				updateUserInfo.start();
			}
			
		});//end of createOnClickListener
		
	}//end onCreate
	
	Handler toastMsg = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			Toast.makeText(ManageAccountActivity.this, msg.toString(), Toast.LENGTH_SHORT);
			return false;
		}
	});//end of toastMsg handler
}//end class ManageAccountActivity
