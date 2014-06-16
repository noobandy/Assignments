package in.anandm.android.apps.listviewsample;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ListActivity {

	private Button addPersonButton;
	private EditText personNameText;
	private static final String[] PERSONS = { "Amit Tiwari", "Anand Mohan",
		"Vijay Kumar Chauhan", "Nilesh Patil", "Onkar Sutar",
		"Sunil Rajak", "Mr. Bean", "Ninza Coder", "Android", "QWERTY" };

	private ArrayAdapter<String> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeUI();

		adapter = new MyArrayAdapter(this,PERSONS);

		setListAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initializeUI() {
		addPersonButton = (Button) findViewById(R.id.addPerson);

		personNameText = (EditText) findViewById(R.id.personName);

		addPersonButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addPerson();

			}
		});
	}

	private void addPerson() {
		String personName = personNameText.getText().toString();

		if ("".equals(personName.toString().trim())) {
			personNameText
			.setError("Person name can not be empty or whitspace");
		} else {
			// add person
			adapter.add(personName);
			// notify data has changed
			adapter.notifyDataSetChanged();
		}

	}
}
