package in.anandm.android.apps.thoughts.ui;

import in.anandm.android.apps.thoughts.provider.UserThoughtsContract;
import in.anandm.android.apps.thoughts.util.SecurityContextHolder;
import in.anandm.android.utils.validator.BindingErrorRenderer;
import in.anandm.android.utils.validator.BindingResults;
import in.anandm.android.utils.validator.TextViewBindingErrorRenderer;
import in.anandm.android.utils.validator.ValidationUtils;
import in.anandm.thoughts.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThoughtActivity extends Activity {

	private EditText thoughtInput;
	private TextView authorTextView;
	private TextView lastModifiedTextView;
	private MenuItem saveMenuItem;
	private MenuItem discardMenuItem;
	
	private DateFormat dateFormat = SimpleDateFormat.getDateInstance();
	
	private Long currentThoughtId;
	private boolean enableActions = false;
	

	private static final int AUTHOR_COLUMN_ID = 0;
	private static final int THOUGHT_COLUMN_ID = 1;
	private static final int LAST_MODIFIED_COLUMN_ID = 2;

	private static final String[] PROJECTIONS = {
			UserThoughtsContract.Thought.COLUMN_AUTHOR,
			UserThoughtsContract.Thought.COLUMN_THOUGHT,
			UserThoughtsContract.Thought.COLUMN_LAST_MODIFIED };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thought);
		initializeUI();
		Intent currentIntent = getIntent();

		if (currentIntent == null
				|| currentIntent.getLongExtra("thoughtId", 0L) == 0L) {

			//enable actions 
			
			enableActions = true;
			
			currentThoughtId = null;
			// create new thought
			authorTextView
					.setText(SecurityContextHolder.getAuthenticatedUser());
			lastModifiedTextView.setText(dateFormat.format(new Date()));

		} else {
			// edit existing thought
			currentThoughtId = currentIntent.getLongExtra("thoughtId", 0L);

			Uri thoughtUri = Uri.parse(UserThoughtsContract.Thought.CONTENT_URI
					+ "/" + currentThoughtId);

			Cursor cursor = getContentResolver().query(thoughtUri, PROJECTIONS,
					null, null, null);

			if (cursor.moveToFirst()) {
				String author = cursor.getString(AUTHOR_COLUMN_ID);
				long lastModified = cursor.getLong(LAST_MODIFIED_COLUMN_ID);
				String thought = cursor.getString(THOUGHT_COLUMN_ID);

				authorTextView.setText(author);
				lastModifiedTextView.setText(dateFormat.format(new Date(
						lastModified)));
				thoughtInput.setText(thought);
				
				if(author.equals(SecurityContextHolder.getAuthenticatedUser())){
					enableActions = true;
				}
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thought, menu);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		saveMenuItem = menu.findItem(R.id.menu_save);
		discardMenuItem = menu.findItem(R.id.menu_remove);
		
		if(enableActions){
			saveMenuItem.setVisible(true);
			discardMenuItem.setVisible(true);
		}

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

				if (currentThoughtId == null) {
					getContentResolver().insert(
							UserThoughtsContract.Thought.CONTENT_URI, values);
				} else {
					getContentResolver().update(
							Uri.parse(UserThoughtsContract.Thought.CONTENT_URI
									+ "/" + currentThoughtId), values, null,
							null);
				}

				Toast message = Toast.makeText(this, "Thought saved",
						Toast.LENGTH_SHORT);
				message.show();
			}

			break;
		case R.id.menu_remove:
			if(currentThoughtId != null){
				//delete current thought
				getContentResolver().delete(Uri.parse(UserThoughtsContract.Thought.CONTENT_URI+"/"+currentThoughtId), null, null);	
			}
			
			NavUtils.navigateUpFromSameTask(this);

			Toast message = Toast.makeText(this, "Thought deleted",
					Toast.LENGTH_SHORT);
			message.show();
			break;	
		case android.R.id.home:
			 NavUtils.navigateUpFromSameTask(this);
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
