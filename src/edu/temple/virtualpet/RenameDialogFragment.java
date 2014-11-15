package edu.temple.virtualpet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class RenameDialogFragment extends DialogFragment {

	EditText txtNickname;
	
	   static RenameDialogFragment newInstance() {
	        return new RenameDialogFragment();
	    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            
        View view = inflater.inflate(R.layout.fragment_rename_dialog, container, false);
        txtNickname = (EditText) view.findViewById(R.id.txtNickname);
        getDialog().setTitle("Rename Pet");

        return view;
    }
	
}
