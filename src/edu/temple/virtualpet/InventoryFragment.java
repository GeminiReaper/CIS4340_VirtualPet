package edu.temple.virtualpet;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

public class InventoryFragment extends ListFragment {
	private List<Item> items = new ArrayList<Item>();
	
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			items = (ArrayList<Item>) msg.obj;
			populateInventoryListView();
			return false;
		}
	});
	
	private void populateInventoryListView() {
		ArrayAdapter<Item> adapter = new InventoryAdapter(getActivity(), items);
		setListAdapter(adapter);
	}

	private void fetchInventory() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (Utility.isNetworkAvailable(getActivity())) {
					HttpClient httpclient = new DefaultHttpClient();
					String url = Constants.SERVER + "get_inventory.php?userId="
							+ UserData.getInstance().getUserId();
					HttpGet httpGet = new HttpGet(url);

					try {

						HttpResponse response = httpclient.execute(httpGet);

						String responseJSON = EntityUtils.toString(response
								.getEntity());

						JSONObject jObject;

						try {
							jObject = new JSONObject(responseJSON);
							String result = jObject.getString("result");
							//String message = jObject.getString("message");

							if (result.equals("success")) {
								List<Item> items = new ArrayList<Item>();
								JSONArray jsonItems = jObject
										.getJSONArray("items");
								for (int i = 0; i < jsonItems.length(); i++) {
									JSONObject jsonItem = (JSONObject) jsonItems
											.get(i);
						
									items.add(new Item(jsonItem
											.getString("itemId"), jsonItem
											.getString("inventoryId"), jsonItem
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
	
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
		fetchInventory();
	    super.onActivityCreated(savedInstanceState);
	  }
	  @Override
	  public void onListItemClick ( ListView listview, View view, int position, long id){
		  
		  PopupMenu popup = new PopupMenu(getActivity(), view);
		  popup.getMenuInflater().inflate(R.layout.popup_menu, popup.getMenu());
		  popup.show();
		  /*
		  Item item = items.get(position);
		  try{
			  
		  ByteArrayOutputStream b = new ByteArrayOutputStream();
	        ObjectOutputStream o = new ObjectOutputStream(b);
	        o.writeObject(item);
	       byte [] data = b.toByteArray();
		  Intent intent = new Intent(getActivity(),NfcGiveActivity.class);
		  intent.putExtra(Constants.ITEM, data);
		  startActivity(intent);
		  
		  }
		  catch(IOException ex){
		        ex.printStackTrace();
		    }
		    */
		  }
		 
	  }

