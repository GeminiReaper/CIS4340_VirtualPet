package edu.temple.virtualpet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManageAccountFragment extends Fragment {
	
	Button deleteAcct;
	Button createAcct;
	EditText userName;
	EditText email;
	EditText password;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_account, container, false);
        
        /* Object Connections / declarations */
		deleteAcct = (Button) rootView.findViewById(R.id.btnDeleteAccount);
		createAcct = (Button) rootView.findViewById(R.id.btnUpdateAccount);
		userName = (EditText) rootView.findViewById(R.id.txtUsername);
		password = (EditText) rootView.findViewById(R.id.txtPassword);
		email = (EditText) rootView.findViewById(R.id.txtEmail);
        
		createAcct.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Thread updateUserInfo = new Thread() {
					
					public void run() {
						String noNetwork = "No Available Network";
						Message msg = Message.obtain();
						
						if(!(Utility.isNetworkAvailable(getActivity()))) {
							
							/* No Network Message to User */
							msg.obj = noNetwork;
							toastHandler.sendMessage(msg);
							
							HttpClient httpclient = new DefaultHttpClient();
							String userId = UserData.getInstance().getUserId();
							String url = Constants.SERVER + "update_user.php"
									+ userId;
							HttpPost httppost = new HttpPost(url);
							
							try {
								HttpResponse response = httpclient.execute(httppost);
								String responseJSON = EntityUtils.toString(
																response.getEntity());
								
								
								
								
							
							} catch (Exception e) {
								e.printStackTrace();;
							}

						}//end if 
						
						
					}//end run	
				};//end udateUserInfo Thread
				updateUserInfo.start();
			}//end onClick
		});//end of createOnClickListener
		
		
		
		
		
        return rootView;
    }
	
	private Handler toastHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Toast toast = Toast.makeText(getActivity(), (String) message.obj,
					Toast.LENGTH_LONG);
			
			toast.show();
			return false;
		}
	});
	
	
}
