package edu.temple.virtualpet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;



public class NfcGiveActivity extends Activity implements CreateNdefMessageCallback {NfcAdapter mNfcAdapter;

Item item;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_give_nfc);
	Intent intent = getIntent();
    byte [] data= intent.getByteArrayExtra(Constants.ITEM);
	 ByteArrayInputStream b = new ByteArrayInputStream(data);
     ObjectInputStream o;
	try {
		o = new ObjectInputStream(b);
	 
	 
     item = (Item) o.readObject();
	}
	catch(IOException ex){
        ex.printStackTrace();
    } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
    //TextView textView = (TextView) findViewById(R.id.textView);
    // Check for available NFC Adapter
    mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    if (mNfcAdapter == null) {
        Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
        finish();
        return;
    }
    // Register callback
    mNfcAdapter.setNdefPushMessageCallback(this, this);
}

@Override
public NdefMessage createNdefMessage(NfcEvent event) {
	 ByteArrayOutputStream b = new ByteArrayOutputStream();
     ObjectOutputStream o;
	try {
		o = new ObjectOutputStream(b);
		o.writeObject(item);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    byte [] data = b.toByteArray();
    NdefMessage msg = new NdefMessage(
    	    new NdefRecord[] {
    	        NdefRecord.createMime(
            		"application/edu.temple.virtualpet", data)
     
                    ,NdefRecord.createApplicationRecord("edu.temple.virtualpet")
    });
    return msg;
}

@Override
public void onResume() {
    super.onResume();
    // Check to see that the Activity started due to an Android Beam
    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
        processIntent(getIntent());
    }
}

@Override
public void onNewIntent(Intent intent) {
    // onResume gets called after this to handle the intent
    setIntent(intent);
}

/**
 * Parses the NDEF Message from the intent and prints to the TextView
 */
void processIntent(Intent intent) {
    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
            NfcAdapter.EXTRA_NDEF_MESSAGES);
    // only one message sent during the beam
    NdefMessage msg = (NdefMessage) rawMsgs[0];
    // record 0 contains the MIME type, record 1 is the AAR, if present
}
}