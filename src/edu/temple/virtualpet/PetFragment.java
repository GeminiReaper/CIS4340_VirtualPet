package edu.temple.virtualpet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DialogFragment;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PetFragment extends Fragment {
	TextView txtPetNickname;
	TextView txtPetName;
	TextView txtDescription;
	ImageView imgPet;

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

	private Handler petHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Pet pet = (Pet) message.obj;
			Picasso.with(getActivity()).load(pet.getImageUrl()).into(imgPet);
			
			Intent intent = getActivity().getIntent();
			Bundle extras = intent.getExtras();
			if(extras != null && extras.containsKey("nickname")){
			
				String nickname = extras.getString("nickname");
				txtPetNickname.setText(nickname);
			}
			else {
			txtPetNickname.setText(pet.getNickname());
			}
			txtPetName.setText(pet.getName());
			txtDescription.setText(pet.getDescription());
			return false;
		}
	});

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
				if (Utility.isNetworkAvailable(getActivity())) {
					// Create a new HttpClient and Post Header
					HttpClient httpclient = new DefaultHttpClient();
					String userId = UserData.getInstance().getUserId();
					String url = Constants.SERVER + "get_pet.php?userId="
							+ userId;
					HttpGet httpget = new HttpGet(url);

					try {
						// Execute HTTP Post Request
						HttpResponse response = httpclient.execute(httpget);

						String responseJSON = EntityUtils.toString(response
								.getEntity());
						Log.i("JSON", responseJSON);
						JSONObject jObject;
						JSONObject data;
						try {
							jObject = new JSONObject(responseJSON);
							data = jObject.getJSONObject("data");

							String name = data.getString("name");
							String description = data.getString("description");
							String nickname = data.getString("nickname");
							String imageURL = data.getString("imageURL");
							String userPetId = data.getString("userPetId");

							UserData.getInstance().setUserPetId(userPetId);
							UserData.getInstance().setPetNickname(nickname);

							Message msg = Message.obtain();
							msg.obj = new Pet(userPetId, name, nickname,
									description, imageURL);
							petHandler.sendMessage(msg);

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					catch (Exception e) {

						Message message = Message.obtain();
						message.obj = "No Network Connection";
						toastHandler.sendMessage(message);

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
