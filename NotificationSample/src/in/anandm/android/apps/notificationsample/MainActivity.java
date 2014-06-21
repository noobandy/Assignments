package in.anandm.android.apps.notificationsample;

import java.io.IOException;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private MediaPlayer audioPlayer;
	private Button buttonPlay;
	private Button buttonPause;
	private Button buttonRepeat;
	private Button buttonStop;
	private NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// MusicStore.loadTracks();
		audioPlayer = MediaPlayer.create(this, R.raw.one);
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		initializeUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initializeUI() {
		buttonPlay = (Button) findViewById(R.id.buttonPlay);

		buttonPlay.setOnClickListener(new PlayClickListener());

		buttonPause = (Button) findViewById(R.id.buttonPause);

		buttonPause.setOnClickListener(new PauseClickListener());

		buttonRepeat = (Button) findViewById(R.id.buttonRepeat);

		buttonRepeat.setOnClickListener(new RepeatClickListener());

		buttonStop = (Button) findViewById(R.id.buttonStop);

		buttonStop.setOnClickListener(new StopClickListener());
	}

	@Override
	protected void onPause() {
		super.onPause();
		audioPlayer.release();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		audioPlayer = MediaPlayer.create(this, R.raw.one);

	}

	@Override
	protected void onResume() {
		super.onResume();
		audioPlayer = MediaPlayer.create(this, R.raw.one);

	}

	@Override
	protected void onStop() {
		super.onStop();
		audioPlayer.release();

	}

	private class PlayClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (!audioPlayer.isPlaying()) {
				audioPlayer.start();
			}

			showNotification("Play", "Song is playing");
			toggleControls();
		}
	}

	private class PauseClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (audioPlayer.isPlaying()) {
				audioPlayer.pause();
			}
			showNotification("Pause", "Song is Paused");
			toggleControls();
		}
	}

	private class RepeatClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (!audioPlayer.isLooping()) {
				audioPlayer.setLooping(true);
			} else {
				audioPlayer.setLooping(false);
			}
			toggleControls();
		}
	}

	private class StopClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (audioPlayer.isPlaying()) {
				audioPlayer.pause();
				audioPlayer.stop();
				// prepare for next play back
				try {
					audioPlayer.prepare();
					audioPlayer.seekTo(1);
				} catch (IllegalStateException e) {
					Log.e(TAG, "failed to prepare");
				} catch (IOException e) {
					Log.e(TAG, "failed to prepare");
				}
				toggleControls();
			}
		}
	}

	private void toggleControls() {

		if (audioPlayer.isLooping()) {
			buttonRepeat.setEnabled(false);
		} else {
			if (!buttonRepeat.isEnabled()) {
				buttonRepeat.setEnabled(true);
			}
		}

		if (audioPlayer.isPlaying()) {

			buttonPlay.setEnabled(false);

			if (!buttonPause.isEnabled()) {
				buttonPause.setEnabled(true);
			}
			if (!buttonStop.isEnabled()) {
				buttonStop.setEnabled(true);
			}

		} else {

			if (!buttonPlay.isEnabled()) {
				buttonPlay.setEnabled(true);
			}

			if (buttonPause.isEnabled()) {
				buttonPause.setEnabled(false);
			}
			if (buttonStop.isEnabled()) {
				buttonStop.setEnabled(false);
			}

		}
	}

	private void showNotification(String title, String text) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setAutoCancel(true);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle(title);
		builder.setContentText(text);
		notificationManager.notify(TAG, 1, builder.build());
	}
}
