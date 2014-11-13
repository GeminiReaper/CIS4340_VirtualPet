package edu.temple.virtualpet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ManageAccountActivity extends Activity {

	Button btnPet;
	Button btnManageAccount;
	Button btnInventory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_account);
		
		btnPet = (Button)findViewById(R.id.btnPet);
		btnManageAccount = (Button)findViewById(R.id.btnManageAccount);
		btnInventory = (Button)findViewById(R.id.btnInventory);
 

	
	
	btnPet.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(ManageAccountActivity.this, PetActivity.class);
			startActivity(intent);
		
		
		}
	});
	btnManageAccount.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(ManageAccountActivity.this, ManageAccountActivity.class);
			startActivity(intent);
		
		
		}
	});
	btnInventory.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(ManageAccountActivity.this, InventoryActivity.class);
			startActivity(intent);
		
		
		}
	});
	
	}
}
