package edu.temple.virtualpet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RenameDialogFragment extends DialogFragment {

	EditText txtNickname;
	Button btnUpdate;

	private Handler toastHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Toast toast = Toast.makeText(getActivity(), (String) message.obj,
					Toast.LENGTH_LONG);
			toast.show();
			return false;
		}
	});

	private Handler dismissDialog = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			getDialog().dismiss();
			return false;
		}
	});

	static RenameDialogFragment newInstance() {
		return new RenameDialogFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_rename_dialog,
				container, false);
		btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
		txtNickname = (EditText) view.findViewById(R.id.txtNickname);
		getDialog().setTitle("Rename Pet");

		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// http post to update nickname

				Thread thread = new Thread() {
					@Override
					public void run() {
						if (Utility.isNetworkAvailable(getActivity())) {
							// Create a new HttpClient and Post Header
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(Constants.SERVER
									+ "rename_pet.php");

							// Add your nickname
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
									2);
							nameValuePairs.add(new BasicNameValuePair(
									"nickname", txtNickname.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"userPetId", UserData.getInstance()
											.getUserPetId()));
							try {
								httppost.setEntity(new UrlEncodedFormEntity(
										nameValuePairs));

								// Execute HTTP Post Request
								HttpResponse response = httpclient
										.execute(httppost);

								String responseJSON = EntityUtils
										.toString(response.getEntity());

								JSONObject jObject = new JSONObject(
										responseJSON);
								String result = jObject.getString("result");
								String message = jObject.getString("message");

								if (!(result.equals("success"))) {
									// toast
									Message resultMessage = Message.obtain();
									resultMessage.obj = result + ": " + message;
									toastHandler.sendMessage(resultMessage);
								} else {
									// call handler for closing
									Message resultMessage0 = Message.obtain();
									resultMessage0.obj = "";
									dismissDialog.sendMessage(resultMessage0);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							} catch (ClientProtocolException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else {
							Message message1 = Message.obtain();
							message1.obj = "No Network Connection";
							toastHandler.sendMessage(message1);
						}

					}
				};

				thread.start();
			}
		});
		return view;

	}
}