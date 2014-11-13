package edu.temple.virtualpet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PetActivity extends Activity {
	Button btnPet;
	Button btnManageAccount;
	Button btnInventory;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet);
		
		btnPet = (Button)findViewById(R.id.btnPet);
		btnManageAccount = (Button)findViewById(R.id.btnManageAccount);
		btnInventory = (Button)findViewById(R.id.btnInventory);
 

	
	
	btnPet.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(PetActivity.this, PetActivity.class);
			startActivity(intent);
		
		
		}
	});
	
	
	btnManageAccount.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(PetActivity.this, ManageAccountActivity.class);
			startActivity(intent);
		
		
		}
	});
	
	btnInventory.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(PetActivity.this, InventoryActivity.class);
			startActivity(intent);
		
		
		}
	});
	
}
	
}

