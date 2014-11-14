package edu.temple.virtualpet;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PetFragment extends Fragment {
	TextView txtPetNickname;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pet, container,
				false);
		txtPetNickname = (TextView) rootView.findViewById(R.id.txtPetNickname);
		
		txtPetNickname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
	
			}
		});
		return rootView;
	}
}
