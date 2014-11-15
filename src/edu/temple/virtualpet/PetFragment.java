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

import com.squareup.picasso.Picasso;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PetFragment extends Fragment {
	TextView txtPetNickname;
	TextView txtPetName;
	TextView txtDescription;
	ImageView imgPet;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pet, container,
				false);
		txtPetNickname = (TextView) rootView.findViewById(R.id.txtPetNickname);
		txtPetName = (TextView) rootView.findViewById(R.id.txtPetName);
		txtDescription = (TextView) rootView.findViewById(R.id.txtDescription);
		imgPet = (ImageView) rootView.findViewById(R.id.imgPet);

		Thread thread = new Thread() {
			@Override
			public void run() {
				if (Utility
						.isNetworkAvailable(getActivity())) {
					// Create a new HttpClient and Post Header
					HttpClient httpclient = new DefaultHttpClient();
					String petID = UserData.getInstance().getUserPetId();
					//String petID = "1";
					String url = "http://cis-linux2.temple.edu/~tuc28686/virtualpet/get_pet.php?userId=?userPetId=" + petID;
					HttpGet httpget = new HttpGet(url);

					try {
						// Execute HTTP Post Request
						HttpResponse response = httpclient
								.execute(httpget);

						String responseJSON = EntityUtils
								.toString(response.getEntity());

						JSONObject jObject;
						JSONObject data;
						try {
							jObject = new JSONObject(responseJSON);
							data = jObject.getJSONObject("data");
							
							String name = data.getString("name");
							String description = data.getString("description");
							String nickname = data.getString("nickname");
							String imageURL = data.getString("imageURL");

							Picasso.with(getActivity()).load(imageURL).into(imgPet);
							txtPetNickname.setText(nickname);
							txtPetName.setText(name);
							txtDescription.setText(description);
							
							

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();		
						}
						
					}
						
						catch (Exception e){

						
							Message message = Message.obtain();
							message.obj = "No Network Connection";
							//toastHandler.sendMessage(message);
						
						}
				}
			
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
