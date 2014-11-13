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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InventoryActivity extends Activity {

	private List<Item> items = new ArrayList<Item>();
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			items = (ArrayList<Item>) msg.obj;
			populateInventoryListView();
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		fetchInventory();
	}

	private void populateInventoryListView() {
		ArrayAdapter<Item> adapter = new InventoryAdapter(this,items);
		ListView inventoryListView = (ListView) findViewById(R.id.inventoryListView);
		inventoryListView.setAdapter(adapter);
	}

	private void fetchInventory() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (Utility.isNetworkAvailable(InventoryActivity.this)) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(Constants.SERVER
							+ "get_inventory.php");

					try {

						// Add your data
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								2);
						nameValuePairs
								.add(new BasicNameValuePair("userId", ""));
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));

						// Execute HTTP Post Request
						HttpResponse response = httpclient.execute(httppost);

						String responseJSON = EntityUtils.toString(response
								.getEntity());

						JSONObject jObject;

						try {
							jObject = new JSONObject(responseJSON);
							String result = jObject.getString("result");
							String message = jObject.getString("message");

							if (result.equals("success")) {
								List<Item> items = new ArrayList<Item>();
								JSONArray jsonItems = jObject
										.getJSONArray("item");
								for (int i = 0; i < jsonItems.length(); i++) {
									JSONObject jsonItem = (JSONObject) jsonItems
											.get(i);
									// get id as well.
									items.add(new Item(jsonItem
											.getString("name"), jsonItem
											.getString("description"), jsonItem
											.getString("imageURL")));
								}
								Message msg = Message.obtain();
								msg.obj = items;
								handler.sendMessage(msg);
							} else {
								// toast!
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

					} catch (ClientProtocolException e) {
						e.printStackTrace();

					} catch (IOException e) {
						e.printStackTrace();

					}
				}
			}
		};
		thread.start();
	}


}
