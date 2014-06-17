package in.anandm.android.apps.listviewsample;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

		ArrayList<String> entries = new ArrayList<String>(
				Arrays.asList(PERSONS));

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, entries);

		setListAdapter(adapter);

		getListView().setOnItemClickListener(
				new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						//remove item
						adapter.remove(adapter.getItem(position));
						//notify data set changed
						adapter.notifyDataSetChanged();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_reset:
			//clear data
			adapter.clear();
			//notify data set changed
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
			// clear text field
			personNameText.setText("");
			// notify data has changed
			adapter.notifyDataSetChanged();
		}

	}
}
