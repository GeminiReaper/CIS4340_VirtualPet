package edu.temple.virtualpet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAccountFragment extends Fragment {

	Button deleteAcct;
	Button updateAcct;
	TextView userName;
	EditText currentEmail;
	EditText currentPassword;
	EditText newEmailTF;
	EditText newPasswordTF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_manage_account, container, false);

		/* Object Connections / declarations */
		deleteAcct = (Button) rootView.findViewById(R.id.btnDeleteAccount);
		updateAcct = (Button) rootView.findViewById(R.id.btnUpdateAccount);
		userName = (TextView) rootView.findViewById(R.id.lblUsername);
		newEmailTF = (EditText) rootView.findViewById(R.id.newEmailTF);
		newPasswordTF = (EditText) rootView.findViewById(R.id.newPasswordTF);

		userName.setText(UserData.getInstance().getUsername());

		final Handler clear = new Handler();

		final Runnable run = new Runnable() {
			public void run() {
				newEmailTF.setText("");
				newPasswordTF.setText("");
			}
		};


		updateAcct.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Thread updateUserInfo = new Thread() {

					public void run() {

						if(Utility.isNetworkAvailable(getActivity())) {

							HttpClient httpclient = new DefaultHttpClient();
							String userId = UserData.getInstance().getUserId();
							String url = Constants.SERVER + "update_user.php";
							HttpPost httppost = new HttpPost(url);

							try {

								//data
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
								nameValuePairs.add(new BasicNameValuePair(
										"userId", userId
										.toString()));
								nameValuePairs.add(new BasicNameValuePair(
										"password", newPasswordTF.getText()
										.toString()));
								nameValuePairs
								.add(new BasicNameValuePair(
										"email", newEmailTF.getText()
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
									Log.i("JSON: ", responseJSON);
									String result = jObject.getString("result");
									String message = jObject.getString("message");

									/* Check if the JSON string result returned success or failed */
									if (result.equals("success")) {
										Message resultMsg = Message.obtain();
										resultMsg.obj = "Result: " + message;
										toastHandler.sendMessage(resultMsg);

										Log.i("Result: ", result);

									} else if(result.equals("error")) {

										Message resultMsg = Message.obtain();
										resultMsg.obj = "Result: " + message;
										toastHandler.sendMessage(resultMsg);

										Log.i("Result: ", result);

									}
								} catch(JSONException e) {
									e.printStackTrace();
								}

								clear.post(run);

							} catch (Exception e) {
								e.printStackTrace();;
							} 

						}//end if 
						else {
							String noNetwork = "No Available Network";
							Message msg = Message.obtain();

							/* No Network Message to User */
							msg.obj = noNetwork;
							toastHandler.sendMessage(msg);
						}


					}//end run	
				};//end udateUserInfo Thread

				updateUserInfo.start();

			}//end onClick
		});//end of createOnClickListener

		deleteAcct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Thread deleteAcct = new Thread() {
					public void run() {
						if(Utility.isNetworkAvailable(getActivity())) {

							HttpClient httpclient = new DefaultHttpClient();
							String userId = UserData.getInstance().getUserId();
							String url = Constants.SERVER + "delete_user.php";
							HttpPost httppost = new HttpPost(url);

							try {

								//data
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
								nameValuePairs.add(new BasicNameValuePair(
										"userId", userId
										.toString()));
								// Execute HTTP Post Request
								HttpResponse response = httpclient
										.execute(httppost);

								String responseJSON = EntityUtils
										.toString(response.getEntity());

								JSONObject jObject;


								try {
									jObject = new JSONObject(responseJSON);
									Log.i("JSON: ", responseJSON);
									String result = jObject.getString("result");
									String message = jObject.getString("message");

									/* Check if the JSON string result returned success or failed */
									if (result.equals("success")) {
										Message resultMsg = Message.obtain();
										resultMsg.obj = "Result: " + message;
										toastHandler.sendMessage(resultMsg);

										Log.i("Result: ", result);

									} else if(result.equals("error")) {

										Message resultMsg = Message.obtain();
										resultMsg.obj = "Result: " + message;
										toastHandler.sendMessage(resultMsg);

										Log.i("Result: ", result);

									}
								} catch(JSONException e) {
									e.printStackTrace();
								}

								clear.post(run);

							} catch (Exception e) {
								e.printStackTrace();;
							} 

							Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
							startActivity(loginIntent);
						}//end if

						else {
							String noNetwork = "No Available Network";
							Message msg = Message.obtain();

							/* No Network Message to User */
							msg.obj = noNetwork;
							toastHandler.sendMessage(msg);
						}
					}
				};//end of deleteAcct Thread
				deleteAcct.start();
			}//end of delete's onClick
		});//end of delete Button ActionListener





		return rootView;
	}//end of onCreateView



	private Handler toastHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Toast toast = Toast.makeText(getActivity(), (String) message.obj,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			return false;
		}
	});
}
