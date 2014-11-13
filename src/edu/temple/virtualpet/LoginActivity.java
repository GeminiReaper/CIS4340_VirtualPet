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
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends Activity {
	
	Button btnCreateAccountAsk;
	Button btnLogin;
	EditText username;
	EditText password;
	
	private Handler toastHandler = new Handler(new Handler.Callback(){
		@Override
		public boolean handleMessage(Message message){
			Toast toast = Toast.makeText(LoginActivity.this, (String)message.obj, Toast.LENGTH_LONG);
			toast.show();
			return false;
		}
	});
		
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btnCreateAccountAsk = (Button)findViewById(R.id.btnCreateAccountAsk);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		username = (EditText)findViewById(R.id.txtUsername);
		password = (EditText)findViewById(R.id.txtPassword);
		
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			Thread thread = new Thread(){
					@Override
					public void run(){
						if (Utility.isNetworkAvailable(LoginActivity.this)){
						    // Create a new HttpClient and Post Header
						    HttpClient httpclient = new DefaultHttpClient();
						    HttpPost httppost = new HttpPost("http://cis-linux2.temple.edu/~tuc28686/virtualpet/login.php");

						    try {
						    	
						 // Add your data
						        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
						        nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
						        nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
						        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

						        // Execute HTTP Post Request
						        HttpResponse response = httpclient.execute(httppost);
						        
						        String responseJSON = EntityUtils.toString(response.getEntity());
						        
						        JSONObject jObject;
						        
								try {
									jObject = new JSONObject(responseJSON);
									Log.i("JSON", responseJSON);
									String result = jObject.getString("result");
									String message = jObject.getString("message");
									JSONObject data = jObject.getJSONObject("data");
									String userID = data.getString("userId");
									String strUsername = data.getString("username");
									String email = data.getString("email");
									
		
									if (result.equals("success")){
										Message loginMessage = Message.obtain();
										loginMessage.obj = "Login Successful!";
										toastHandler.sendMessage(loginMessage);
										Intent intent = new Intent(LoginActivity.this, PetActivity.class);
										intent.putExtra(Constants.USERNAME, strUsername);
										intent.putExtra(Constants.EMAIL, email);
										intent.putExtra(Constants.USER_ID, userID);
										startActivity(intent);
										
										
									}
									else{
										
										
										Message resultMessage = Message.obtain();
										resultMessage.obj = result + ": " + message;
										toastHandler.sendMessage(resultMessage);
										
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								

						        
						    } catch (ClientProtocolException e) {
						        // TODO Auto-generated catch block
						    } catch (IOException e) {
						        // TODO Auto-generated catch block
						    }

							
						}
						
						else{
							Message message = Message.obtain();
							message.obj = "No Network Connection";
							toastHandler.sendMessage(message);
					}					
					}
				};
				
				thread.start();
				
				
			};
		});
		
		btnCreateAccountAsk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
				startActivity(intent);
			}
		});
	}
	}

	

