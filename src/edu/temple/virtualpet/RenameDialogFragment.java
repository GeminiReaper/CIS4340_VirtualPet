package edu.temple.virtualpet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RenameDialogFragment extends DialogFragment {

	TextView txtPetNickname;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_rename_dialog, container,
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
