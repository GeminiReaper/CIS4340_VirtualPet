package edu.temple.virtualpet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class PetFragment extends Fragment {
	TextView txtPetNickname;
	TextView txtPetName;
	TextView txtDescription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pet, container,
				false);
		txtPetNickname = (TextView) rootView.findViewById(R.id.txtPetNickname);
		
		txtPetName = (TextView) rootView.findViewById(R.id.txtPetName);
		txtDescription = (TextView) rootView.findViewById(R.id.txtDescription);

		
		
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (Utility
						.isNetworkAvailable(getActivity())) {
					// Create a new HttpClient and Post Header
					HttpClient httpclient = new DefaultHttpClient();
					//String petID = UserData.getInstance().getUserPetId();
					String petID = "1";
					String url = "http://cis-linux2.temple.edu/~tuc28686/virtualpet/get_pet.php?userId=?userPetId=" + petID;
					HttpGet httpget = new HttpGet(url);
							
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

			
					}
					

				else {
					Message message = Message.obtain();
					message.obj = "No Network Connection";
					//toastHandler.sendMessage(message);
				}

		};

		thread.start();

		
		
		
		
		
		
		
		
		
		txtPetNickname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				    // Create the fragment and show it as a dialog.
				    DialogFragment newFragment = RenameDialogFragment.newInstance();
				    newFragment.show(getFragmentManager(), "dialog");
			}
		});
		
		
		return rootView;
	}
	
	
	
}
