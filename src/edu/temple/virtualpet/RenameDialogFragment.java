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
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RenameDialogFragment extends DialogFragment {

	EditText txtNickname;
	Button btnUpdate;
	
	   static RenameDialogFragment newInstance() {
	        return new RenameDialogFragment();
	    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            
        View view = inflater.inflate(R.layout.fragment_rename_dialog, container, false);
        txtNickname = (EditText) view.findViewById(R.id.txtNickname);
        getDialog().setTitle("Rename Pet");
        
        
        
        btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				//http post to update nickname
				
				Thread thread = new Thread() {
					@Override
					public void run() {
						if (Utility
								.isNetworkAvailable(getActivity())) {
							// Create a new HttpClient and Post Header
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(
									"http://cis-linux2.temple.edu/~tuc28686/virtualpet/rename_pet.php");

							// Add your nickname
									List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
											2);
									nameValuePairs.add(new BasicNameValuePair(
											"nickname", txtNickname.getText()
													.toString()));
									try {
										httppost.setEntity(new UrlEncodedFormEntity(
												nameValuePairs));

										// Execute HTTP Post Request
										HttpResponse response = httpclient
												.execute(httppost);
										
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

						}
									else {
							Message message = Message.obtain();
							message.obj = "No Network Connection";
							//toastHandler.sendMessage(message);
						}
					

				}
				};
				
				thread.start();
			}
        });
		return view;

}
}