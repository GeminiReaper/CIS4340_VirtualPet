package edu.temple.virtualpet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NfcGiveActivity extends Activity implements
		CreateNdefMessageCallback {
	NfcAdapter mNfcAdapter;

	Item item;
	TextView lblName;
	TextView lblDescription;
	Button btnOk;
	ImageView imgUrl;
	
	private Handler toastHandler = new Handler(new Handler.Callback(){
		@Override
		public boolean handleMessage(Message message){
			Toast toast = Toast.makeText(NfcGiveActivity.this, (String)message.obj, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			return false;
		}
	});

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_give_nfc);
		Intent intent = getIntent();
		byte[] data = intent.getByteArrayExtra(Constants.ITEM);
		ByteArrayInputStream b = new ByteArrayInputStream(data);
		ObjectInputStream o;
		try {
			o = new ObjectInputStream(b);

			item = (Item) o.readObject();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// TextView textView = (TextView) findViewById(R.id.textView);
		// Check for available NFC Adapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}
		// Register callback
		mNfcAdapter.setNdefPushMessageCallback(this, this);
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o;
		try {
			o = new ObjectOutputStream(b);
			o.writeObject(item);
		} catch (IOException e) {

			e.printStackTrace();
		}
		byte[] data = b.toByteArray();
		NdefMessage msg = new NdefMessage(
				new NdefRecord[] {
						NdefRecord.createMime(
								"application/edu.temple.virtualpet", data)

						,
						NdefRecord
								.createApplicationRecord("edu.temple.virtualpet") });
		removeItem();
		return msg;
	}

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	void processIntent(Intent intent) {

		lblName = (TextView) findViewById(R.id.lblName);
		lblDescription = (TextView) findViewById(R.id.lblDescription);
		btnOk = (Button) findViewById(R.id.btnOk);
		imgUrl = (ImageView) findViewById(R.id.imgUrl);

		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		byte[] data = msg.getRecords()[0].getPayload();
		ByteArrayInputStream b = new ByteArrayInputStream(data);
		ObjectInputStream o;
		try {
			o = new ObjectInputStream(b);
			item = (Item) o.readObject();
			addItem();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void addItem() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (Utility.isNetworkAvailable(NfcGiveActivity.this)) {
					// Create a new HttpClient and Post Header
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cis-linux2.temple.edu/~tuc28686/virtualpet/add_item.php");

					try {

						// Add your data
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								2);
						nameValuePairs.add(new BasicNameValuePair("itemId",
								item.getItemId()));
						nameValuePairs.add(new BasicNameValuePair("userId",
								UserData.getInstance().getUserId()));
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));

						// Execute HTTP Post Request
						HttpResponse response = httpclient.execute(httppost);

						String responseJSON = EntityUtils.toString(response
								.getEntity());

						JSONObject jObject;

						jObject = new JSONObject(responseJSON);
						Log.i("JSON", responseJSON);
						String result = jObject.getString("result");
						String message = jObject.getString("message");

						if (result.equals("success")) {
							Message msg = Message.obtain();
							msg.obj = "Item Added!";
							toastHandler.sendMessage(msg);

						} else {
							Message msg = Message.obtain();
							msg.obj = result + ": " + message;
							toastHandler.sendMessage(msg);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
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
	}
	
	private void removeItem() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (Utility.isNetworkAvailable(NfcGiveActivity.this)) {
					// Create a new HttpClient and Post Header
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cis-linux2.temple.edu/~tuc28686/virtualpet/remove_item.php");

					try {

						// Add your data
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								1);
						nameValuePairs.add(new BasicNameValuePair("inventoryId",
								item.getInventoryId()));
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));

						// Execute HTTP Post Request
						HttpResponse response = httpclient.execute(httppost);

						String responseJSON = EntityUtils.toString(response
								.getEntity());

						JSONObject jObject;

						jObject = new JSONObject(responseJSON);
						Log.i("JSON", responseJSON);
						String result = jObject.getString("result");
						String message = jObject.getString("message");

						if (result.equals("success")) {
							Message msg = Message.obtain();
							msg.obj = "Item Added!";
							toastHandler.sendMessage(msg);
						} else {
							Message msg = Message.obtain();
							msg.obj = result + ": " + message;
							toastHandler.sendMessage(msg);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
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
	}
}