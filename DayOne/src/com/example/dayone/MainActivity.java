package com.example.dayone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button clickMeNutton;
	private TextView clickButtonText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickMeNutton = (Button) findViewById(R.id.button);
		clickButtonText = (TextView) findViewById(R.id.buttonClickText);

		clickButtonText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// assignment one
				changeText("Clicked");
				// assignment two
				makeToast("Clicked");
			}
		});

		clickMeNutton.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				// assignment one
				changeText("Long Clicked");
				// assignment two
				makeToast("Long Clicked");
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void changeText(String text) {
		clickButtonText.setText(text);
	}

	private void makeToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
