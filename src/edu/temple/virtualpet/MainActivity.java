package edu.temple.virtualpet;

import java.util.Collection;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
	PetFragment petFragment;
	InventoryFragment inventoryFragment;
	ManageAccountFragment manageAccountFragment;
	ActionBar.Tab petTab, inventoryTab, manageAccountTab;
	
	private Handler toastHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			Toast toast = Toast.makeText(MainActivity.this, (String) message.obj,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);

		
		// read the intent and toast a message. "Fed nickname item".
		Intent intent = getIntent();
		String itemName = intent.getStringExtra(Constants.ITEM_NAME);
		if (itemName != null){
		//String petNickname = Constants.PET_NICKNAME;
		Message message = Message.obtain();
		message.obj = "Eating " + itemName + ", mmmmm!!";
		toastHandler.sendMessage(message);
		}
		
		
		try {
			ActionBar actionBar = getActionBar();
		    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		    actionBar.setDisplayShowTitleEnabled(true);

			petTab = actionBar.newTab().setText(R.string.title_pet)
					.setIcon(R.drawable.ic_action_favorite)
					.setTabListener(new TabListener<PetFragment>(this,"pet", PetFragment.class));
			inventoryTab = actionBar.newTab().setText(R.string.title_inventory)
					.setIcon(R.drawable.ic_action_view_as_grid)
			        .setTabListener(new TabListener<InventoryFragment>(this,"inventory", InventoryFragment.class));
			manageAccountTab = actionBar.newTab().setText(R.string.title_manage_account)
					.setIcon(R.drawable.ic_action_person)
					.setTabListener(new TabListener<ManageAccountFragment>(this,"manageAccount", ManageAccountFragment.class));

			actionBar.addTab(petTab);
			actionBar.addTab(inventoryTab);
			actionBar.addTab(manageAccountTab);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public class MusicManager {
		private static final String TAG = "MusicManager";
		public static final int MUSIC_PREVIOUS = -1;
		public static final int MUSIC_MENU = 0;
		public static final int MUSIC_GAME = 1;
		public static final int MUSIC_END_GAME = 2;

		private static HashMap players = new HashMap();
		private static int currentMusic = -1;
		private static int previousMusic = -1;

		public static float getMusicVolume(Context context) {
		String[] volumes = context.getResources().getStringArray(R.array.volume_values);
		String volumeString = PreferenceManager.getDefaultSharedPreferences(context).getString(
		context.getString(R.string.key_pref_music_volume), volumes[PREF_DEFAULT_MUSIC_VOLUME_ITEM]);
		return new Float(volumeString).floatValue();
		}

		public static void start(Context context, int music) {
		start(context, music, false);
		}

		public static void start(Context context, int music, boolean force) {
		if (!force && currentMusic > -1) {
		// already playing some music and not forced to change
		return;
		}
		if (music == MUSIC_PREVIOUS) {
		Log.d(TAG, "Using previous music [" + previousMusic + "]");
		music = previousMusic;
		}
		if (currentMusic == music) {
		// already playing this music
		return;
		}
		if (currentMusic != -1) {
		previousMusic = currentMusic;
		Log.d(TAG, "Previous music was [" + previousMusic + "]");
		// playing some other music, pause it and change
		pause();
		}
		currentMusic = music;
		Log.d(TAG, "Current music is now [" + currentMusic + "]");
		MediaPlayer mp = players.get(music);
		if (mp != null) {
		if (!mp.isPlaying()) {
		mp.start();
		}
		} else {
		if (music == MUSIC_MENU) {
		mp = MediaPlayer.create(context, R.raw.menu_music);
		} else if (music == MUSIC_GAME) {
		mp = MediaPlayer.create(context, R.raw.game_music);
		} else if (music == MUSIC_END_GAME) {
		mp = MediaPlayer.create(context, R.raw.end_game_music);
		} else {
		Log.e(TAG, "unsupported music number - " + music);
		return;
		}
		players.put(music, mp);
		float volume = getMusicVolume(context);
		Log.d(TAG, "Setting music volume to " + volume);
		mp.setVolume(volume, volume);
		if (mp == null) {
		Log.e(TAG, "player was not created successfully");
		} else {
		try {
		mp.setLooping(true);
		mp.start();
		} catch (Exception e) {
		Log.e(TAG, e.getMessage(), e);
		}
		}
		}
		}

		public static void pause() {
		Collection mps = players.values();
		for (MediaPlayer p : mps) {
		if (p.isPlaying()) {
		p.pause();
		}
		}
		// previousMusic should always be something valid
		if (currentMusic != -1) {
		previousMusic = currentMusic;
		Log.d(TAG, "Previous music was [" + previousMusic + "]");
		}
		currentMusic = -1;
		Log.d(TAG, "Current music is now [" + currentMusic + "]");
		}

		public static void updateVolumeFromPrefs(Context context) {
		try {
		float volume = getMusicVolume(context);
		Log.d(TAG, "Setting music volume to " + volume);
		Collection mps = players.values();
		for (MediaPlayer p : mps) {
		p.setVolume(volume, volume);
		}
		} catch (Exception e) {
		Log.e(TAG, e.getMessage(), e);
		}
		}

		public static void release() {
		Log.d(TAG, "Releasing media players");
		Collection mps = players.values();
		for (MediaPlayer mp : mps) {
		try {
		if (mp != null) {
		if (mp.isPlaying()) {
		mp.stop();
		}
		mp.release();
		}
		} catch (Exception e) {
		Log.e(TAG, e.getMessage(), e);
		}
		}
		mps.clear();
		if (currentMusic != -1) {
		previousMusic = currentMusic;
		Log.d(TAG, "Previous music was [" + previousMusic + "]");
		}
		currentMusic = -1;
		Log.d(TAG, "Current music is now [" + currentMusic + "]");
		}
		}
}
