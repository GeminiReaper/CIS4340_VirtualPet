package edu.temple.virtualpet;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	PetFragment petFragment;
	InventoryFragment inventoryFragment;
	ManageAccountFragment manageAccountFragment;
	ActionBar.Tab petTab, inventoryTab, manageAccountTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);

		try {
			ActionBar actionBar = getActionBar();
		    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		    actionBar.setDisplayShowTitleEnabled(false);

			petTab = actionBar.newTab().setText("Pet")
					.setIcon(R.drawable.ic_action_favorite)
					.setTabListener(new TabListener<PetFragment>(this,"pet", PetFragment.class));
			inventoryTab = actionBar.newTab().setText("Inventory")
					.setIcon(R.drawable.ic_action_view_as_grid)
			        .setTabListener(new TabListener<InventoryFragment>(this,"inventory", InventoryFragment.class));
			manageAccountTab = actionBar.newTab().setText("Manage Account")
					.setIcon(R.drawable.ic_action_person)
					.setTabListener(new TabListener<ManageAccountFragment>(this,"inventory", ManageAccountFragment.class));

			actionBar.addTab(petTab);
			actionBar.addTab(inventoryTab);
			actionBar.addTab(manageAccountTab);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
