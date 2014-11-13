package edu.temple.virtualpet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
	
	public void postData() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("cis-linux2.temple.edu/~tuc28686/virtualpet/update_user.php");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
	
	Handler toastMsg = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			Toast.makeText(ManageAccountActivity.this, msg.toString(), Toast.LENGTH_SHORT);
			return false;
		}
	});//end of toastMsg handler
}//end class ManageAccountActivity
