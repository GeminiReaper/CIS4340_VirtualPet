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
import android.widget.Toast;

public class ManageAccountFragment extends Fragment {

	Button deleteAcct;
	Button createAcct;
	EditText userName;
	EditText email;
	EditText password;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_manage_account, container, false);

		/* Object Connections / declarations */
		deleteAcct = (Button) rootView.findViewById(R.id.btnDeleteAccount);
		createAcct = (Button) rootView.findViewById(R.id.btnUpdateAccount);
		userName = (EditText) rootView.findViewById(R.id.txtUsername);
		password = (EditText) rootView.findViewById(R.id.txtPassword);
		email = (EditText) rootView.findViewById(R.id.txtEmail);

		createAcct.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Thread updateUserInfo = new Thread() {

					public void run() {
						String noNetwork = "No Available Network";
						

						if(!(Utility.isNetworkAvailable(getActivity()))) {

							/* No Network Message to User */
							Message msg = Message.obtain();
							msg.obj = noNetwork;
							toastHandler.sendMessage(msg);

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
									Log.i("JSON: ", responseJSON);
									String result = jObject.getString("result");
									String message = jObject.getString("message");
									JSONObject data = jObject.getJSONObject("data");
									String username = data.getString("username");
									String email = data.getString("email");
									
									/* Check if the JSON string result returned success or failed */
									if (result.equals("success")) {
										Message resultMsg = Message.obtain();
										resultMsg.obj = "Account info changed successfully";
										toastHandler.sendMessage(resultMsg);
										
										Log.i("Result: ", "Success");

									} else if(result.equals("error")) {

										Message resultMsg = Message.obtain();
										resultMsg.obj = "Error Change wasn't made";
										toastHandler.sendMessage(resultMsg);
										
										Log.i("Result: ", "Error");

									}
								} catch(JSONException e) {
									e.printStackTrace();
								}




							} catch (Exception e) {
								e.printStackTrace();;
							} 




						}//end if 


					}//end run	
				};//end udateUserInfo Thread
				updateUserInfo.start();
			}//end onClick
		});//end of createOnClickListener





		return rootView;
	}

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
