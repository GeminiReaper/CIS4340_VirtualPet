package edu.temple.virtualpet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
	PetFragment petFragment;
	InventoryFragment inventoryFragment;
	ManageAccountFragment manageAccountFragment;
	//creditPage creditPage;
	ActionBar.Tab petTab, inventoryTab, manageAccountTab;
	MediaPlayer mediaPlayer;
	
	private Handler toastHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Toast toast = Toast.makeText(MainActivity.this, (String) message.obj,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			return false;
		}
	});
	
	@Override
	public void onBackPressed() {
		//Do nothing.
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);

		
		// read the intent and toast a message. "Fed nickname item".
		Intent intent = getIntent();
		String itemName = intent.getStringExtra(Constants.ITEM_NAME);
		if (itemName != null){
		//String petNickname = Constants.PET_NICKNAME;
		Message message = Message.obtain();
		message.obj = "Eating " + itemName + ", mmmmm!!";
		toastHandler.sendMessage(message);
		}
		
		
		try {
			ActionBar actionBar = getActionBar();
		    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		    actionBar.setDisplayShowTitleEnabled(true);

			petTab = actionBar.newTab().setText(R.string.title_pet)
					.setIcon(R.drawable.ic_action_favorite)
					.setTabListener(new TabListener<PetFragment>(this,"pet", PetFragment.class));
			inventoryTab = actionBar.newTab().setText(R.string.title_inventory)
					.setIcon(R.drawable.ic_action_view_as_grid)
			        .setTabListener(new TabListener<InventoryFragment>(this,"inventory", InventoryFragment.class));
			manageAccountTab = actionBar.newTab().setText(R.string.title_manage_account)
					.setIcon(R.drawable.ic_action_person)
					.setTabListener(new TabListener<ManageAccountFragment>(this,"manageAccount", ManageAccountFragment.class));
			//creditPage = actionBar.newTab().SetText("Credits")
			//		.setIcon(R.drawable.ic_action_person)

			actionBar.addTab(petTab);
			actionBar.addTab(inventoryTab);
			actionBar.addTab(manageAccountTab);
		//	actionBar.addTab(creditPage)
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
