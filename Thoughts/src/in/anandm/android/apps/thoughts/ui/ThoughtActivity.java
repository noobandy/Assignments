package in.anandm.android.apps.thoughts.ui;

import in.anandm.android.apps.thoughts.provider.UserThoughtsContract;
import in.anandm.android.apps.thoughts.util.SecurityContextHolder;
import in.anandm.android.utils.validator.BindingErrorRenderer;
import in.anandm.android.utils.validator.BindingResults;
import in.anandm.android.utils.validator.TextViewBindingErrorRenderer;
import in.anandm.android.utils.validator.ValidationUtils;
import in.anandm.thoughts.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThoughtActivity extends Activity {

	private EditText thoughtInput;
	private TextView authorTextView;
	private TextView lastModifiedTextView;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thought);
		initializeUI();
		Intent currentIntent = getIntent();
		
		if(currentIntent == null || currentIntent.getLongExtra("thoughtId", 0L) != 0L){
			//create new thought
			authorTextView.setText(SecurityContextHolder.getAuthenticatedUser());
			lastModifiedTextView.setText(dateFormat.format(new Date()));
			
		}else{
			// edit existing thought
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thought, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			BindingErrorRenderer bindingErrorRenderer = new TextViewBindingErrorRenderer();

			BindingResults results = new BindingResults(bindingErrorRenderer,
					this);

			ValidationUtils.rejectIfEmptyOrWhitespace(results, thoughtInput,
					"Some thought is required");

			if (results.isValid()) {
				ContentValues values = new ContentValues();
				values.put(UserThoughtsContract.Thought.COLUMN_AUTHOR,
						SecurityContextHolder.getAuthenticatedUser());
				values.put(UserThoughtsContract.Thought.COLUMN_LAST_MODIFIED,
						System.currentTimeMillis());
				values.put(UserThoughtsContract.Thought.COLUMN_MODIFIED_BY,
						SecurityContextHolder.getAuthenticatedUser());
				values.put(UserThoughtsContract.Thought.COLUMN_THOUGHT,
						thoughtInput.getText().toString());

				getContentResolver().insert(
						UserThoughtsContract.Thought.CONTENT_URI, values);
				Toast message = Toast.makeText(this, "Thought saved",
						Toast.LENGTH_SHORT);
				message.show();
			}

			break;

		default:
			break;
		}
		return true;
	}

	private void initializeUI() {
		thoughtInput = (EditText) findViewById(R.id.inputThought);
		authorTextView = (TextView) findViewById(R.id.textViewAuthor);
		lastModifiedTextView = (TextView) findViewById(R.id.textViewLastModified);

	}
}
