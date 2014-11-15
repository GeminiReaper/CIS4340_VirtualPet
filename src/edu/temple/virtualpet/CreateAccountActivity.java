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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends Activity {

	Button btnCreateAccount;
	EditText username;
	EditText password;
	EditText email;
	EditText passwordRepeat;

	private Handler toastHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Toast toast = Toast.makeText(CreateAccountActivity.this,
					(String) message.obj, Toast.LENGTH_LONG);
			toast.show();
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
		username = (EditText) findViewById(R.id.txtUsername);
		password = (EditText) findViewById(R.id.txtPassword);
		email = (EditText) findViewById(R.id.txtEmail);
		passwordRepeat = (EditText) findViewById(R.id.txtPasswordRepeat);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Thread thread = new Thread() {
					@Override
					public void run() {
						if (Utility
								.isNetworkAvailable(CreateAccountActivity.this)) {
							// Create a new HttpClient and Post Header
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(
									"http://cis-linux2.temple.edu/~tuc28686/virtualpet/create_user.php");
							if (!username.getText().toString().isEmpty()
									|| !password.getText().toString().isEmpty()) {
								if(passwordRepeat.getText().toString()
										.equals(password.getText().toString())) {


								try {

									// Add your data

									List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
											2);
									nameValuePairs.add(new BasicNameValuePair(
											"username", username.getText()
													.toString()));
									nameValuePairs.add(new BasicNameValuePair(
											"password", password.getText()
													.toString()));
									nameValuePairs
											.add(new BasicNameValuePair(
													"email", email.getText()
															.toString()));
									httppost.setEntity(new UrlEncodedFormEntity(
											nameValuePairs));

									// Execute HTTP Post Request
									HttpResponse response = httpclient
											.execute(httppost);

									String responseJSON = EntityUtils
											.toString(response.getEntity());

									JSONObject jObject;

									try {
										jObject = new JSONObject(responseJSON);
										String result = jObject
												.getString("result");
										String message = jObject
												.getString("message");

										if (result.equals("success")) {

											Intent intent = new Intent(
													CreateAccountActivity.this,
													LoginActivity.class);
											// intent.putExtra(name, value);
											startActivity(intent);

										} else {

											if (message
													.startsWith("SQLSTATE[23000]:")) {
												message = "Username already exists";
											}
											Message resultMessage = Message
													.obtain();
											resultMessage.obj = result + ": "
													+ message;
											toastHandler
													.sendMessage(resultMessage);

										}

									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}}

								else {
									Message message = Message.obtain();
									message.obj = "Passwords do not match, please try again !";
									toastHandler.sendMessage(message);}
								}
							else{
								Message message = Message.obtain();
								message.obj = "Username and Password Cannot be empty";
								toastHandler.sendMessage(message);
								
							}
							}
							

						else {
							Message message = Message.obtain();
							message.obj = "No Network Connection";
							toastHandler.sendMessage(message);
						}
					}

				};

				thread.start();

			};
		});


	}
}
