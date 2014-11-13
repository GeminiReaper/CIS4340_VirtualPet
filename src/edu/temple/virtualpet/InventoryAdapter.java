package edu.temple.virtualpet;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InventoryAdapter extends ArrayAdapter<Item> {
	
	public InventoryAdapter(Context context, List<Item> items) {
		super(context, R.layout.item_view, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		if (itemView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			itemView = layoutInflater.inflate(R.layout.item_view,
					parent, false);
		}

		Item item = getItem(position);

		ImageView itemImage = (ImageView) itemView
				.findViewById(R.id.item_image);

		TextView itemName = (TextView) itemView
				.findViewById(R.id.item_txtName);
		itemName.setText(item.getName());

		TextView itemDescription = (TextView) itemView
				.findViewById(R.id.image_txtDescription);
		itemDescription.setText(item.getDescription());
		return itemView;
	}
}
