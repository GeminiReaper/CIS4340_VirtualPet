package edu.temple.virtualpet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ManageAccountFragment extends Fragment {
	
	Button deleteAcct;
	Button createAcct;
	EditText userName;
	EditText email;
	EditText password;


	Button btnPet;
	Button btnManageAccount;
	Button btnInventory;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_account, container, false);

		deleteAcct = (Button) rootView.findViewById(R.id.btnDeleteAccount);
		createAcct = (Button) rootView.findViewById(R.id.btnCreateAccount);
		userName = (EditText) rootView.findViewById(R.id.txtUsername);
		password = (EditText) rootView.findViewById(R.id.txtPassword);
		email = (EditText) rootView.findViewById(R.id.txtEmail);
        
        return rootView;
        
        
    }
	
	
}
