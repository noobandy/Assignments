package in.anandm.android.apps.thoughts.ui;

import in.anandm.android.apps.thoughts.provider.UserThoughtsContract;
import in.anandm.thoughts.R;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class ThoughtListActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thought_list);
		fillData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thought_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			Intent addThoughtIntent = new Intent(this, ThoughtActivity.class);
			startActivity(addThoughtIntent);
			break;

		default:
			break;
		}
		
		return true;
	}

	private void fillData() {

		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] {
				UserThoughtsContract.Thought.COLUMN_THOUGHT,
				UserThoughtsContract.Thought.COLUMN_AUTHOR };
		// Fields on the UI to which we map
		int[] to = new int[] { android.R.id.text1,
				android.R.id.text2 };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, from,
				to, 0);
		setListAdapter(adapter);
	}

	// loader manager contract
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { UserThoughtsContract.Thought.COLUMN_ID,
				UserThoughtsContract.Thought.COLUMN_THOUGHT,
				UserThoughtsContract.Thought.COLUMN_AUTHOR };
		CursorLoader cursorLoader = new CursorLoader(this,
				UserThoughtsContract.Thought.CONTENT_URI, projection, null,
				null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
	}

}
